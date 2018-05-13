from flask import Flask, jsonify, request
import datetime
import time
import requests
from flask_cors import CORS
app = Flask(__name__)
CORS(app)

APIKEY = ''

a_payload = {'apikey': APIKEY}
all_airports = requests.get('http://apigateway.ryanair.com/pub/v1/core/3/airports', params=a_payload)
all_airports_dict = all_airports.json()


def get_flight_info(number):
    r_payload = {'flight': number,
                 'apikey': APIKEY}
    rr = requests.get('http://apigateway.ryanair.com/pub/v1/flightinfo/2/flights', params=r_payload)
    body = rr.json()
    if not body['flightInfo']:
        print('!!! bad arrival airport for flight ', number)
    arrival_airport = body['flightInfo'][0]
    return arrival_airport

def get_flight_info_v3(number):
    r_payload = {'number': number,
                 'apikey': APIKEY}

    rr = requests.get('http://apigateway.ryanair.com/pub/v1/flightinfo/3/flights', params=r_payload)
    body = rr.json()
    arrival_airport = body['flights'][0]
    if not arrival_airport:
        print('bad arrival airport for flight ', number)
    return arrival_airport


@app.route('/airport_location')
def get_airport():
    number = request.args.get('flight_number')
    if not number:
        return jsonify({'message': 'did not provide flight number !!!'})

    flight_info = get_flight_info(number)

    coord = {}
    for airport in all_airports_dict:
        if airport['iataCode'] == flight_info['arrivalAirport']['iata']:
            coord = airport['coordinates']
            break

    response = {'coordinates': coord,
                'arrival_iana': flight_info['arrivalAirport']['iata'],
                'arrival_name': flight_info['arrivalAirport']['name']
                }

    return jsonify(response)


@app.route('/travel_time')
def travel_time():
    user_location = request.args.get('user_location')
    bufor_time = int(request.args.get('bufor_time'))  # in minutes

    number = request.args.get('flight_number')
    if not number:
        return jsonify({'message': 'did not provide flight number !!!'})

    flight_info = get_flight_info_v3(number)
    arrival_time = flight_info['arrivalTime']['estimated']
    today = datetime.datetime.today()
    only_day = datetime.datetime(today.year, today.month, today.day)
    delta_time = datetime.timedelta(hours=int(arrival_time[:2]), minutes=int(arrival_time[-2:]))
    arrival_time_datetime = only_day + delta_time
    arrival_time_timestamp = int(arrival_time_datetime.timestamp())

    arrival_airport_coord = {}
    for airport in all_airports_dict:
        if airport['iataCode'] == flight_info['arrivalAirport']['iataCode']:
            arrival_airport_coord = airport['coordinates']
            break

    arrival_airport_coord_gmaps = "{},{}".format(arrival_airport_coord['latitude'], arrival_airport_coord['longitude'])

    #  let's make more accurate_geo_point for WRO airport
    if flight_info['arrivalAirport']['iataCode'] == 'WRO':
        arrival_airport_coord_gmaps = '51.109973,16.880490'

    # let's hardcode user location for now
    if not user_location:
        user_location = '51.108671,17.102628'

    if arrival_time_timestamp < time.time():
        arrival_time_timestamp = 'now'

    payload = {'origins': user_location,
               'destinations': arrival_airport_coord_gmaps,
               'mode': 'driving',
               'units': 'metric',
               'language ': 'English',
               'traffic_model': 'pessimistic',
               'departure_time': arrival_time_timestamp,
               'key': ''}
    r = requests.get('https://maps.googleapis.com/maps/api/distancematrix/json', params=payload)
    debug_url = r.url
    body = r.json()
    print('body')
    print(body)
    distance = body['rows'][0]['elements'][0]['distance']['text']  # or value
    duration = body['rows'][0]['elements'][0]['duration']['value']//60  # or value
    duration_in_traffic = body['rows'][0]['elements'][0]['duration_in_traffic']['value']//60  # or value

    total_duration = int(duration_in_traffic) + int(bufor_time)

    arrival_time_minus_total_duration = arrival_time_datetime - datetime.timedelta(minutes=int(total_duration))

    response = {
        'distance': distance,
        'duration': duration,
        'duration_in_traffic': duration_in_traffic,
        'plane_arrival_date': arrival_time_datetime.strftime("%Y-%m-%d %H:%M"),
        'plane_arrival_time': arrival_time,
        'plane_arrival_scheduled': flight_info['arrivalTime']['scheduled'],
        'plane_arrival_estimated': flight_info['arrivalTime']['estimated'],
        'lastUpdated': flight_info['lastUpdated'],
        'message': flight_info['status']['message'],
        'bufor_time': bufor_time,
        'total_duration': total_duration,
        'coordinates': arrival_airport_coord,
        'arrival_iana': flight_info['arrivalAirport']['iataCode'],
        'arrival_name': flight_info['arrivalAirport']['name'],
        'arrival_time_minus_total_duration': arrival_time_minus_total_duration.strftime("%Y-%m-%d %H:%M")
    }
    return jsonify(response)


@app.route('/')
def hel():
    response = {'message': ' HELLO WORLD '}
    return jsonify(response)


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=80)
