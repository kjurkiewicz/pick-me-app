<template>
  <div id = "maps">
    <div id = "pick-me-container">
    <div id = "search-bar">
        <div id = "inputs">
            <div class = "data-container">
                <div class = "data-name"> 
                    Flight number
                </div>
                <div class = "data-input"> 
                    <input id = "flightNumber" v-model="flightNumber" type = "text" class = "input"/>
                </div>

            </div>
            <div class = "data-container">
                <div class = "data-name"> 
                    Date
                </div>
                <div class = "data-input"> 
                    <input id = "date" v-model="flightDate" type = "date" class = "input">
                </div>

            </div>
                    <div class = "data-container">
                <div class = "data-name"> 
                    Localization
                </div>
                <div class = "data-input"> 
                    <gmap-autocomplete @place_changed="setPlace" placeholder='Current localization' class = "input" id = "localization" >
                    </gmap-autocomplete>
                </div>

            </div>
            <div class = "data-container">
                <div class = "data-name"> 
                    Telephone number
                </div>
                <div class = "data-input"> 
                    <input id = "tel-nr" v-model="phoneNumber" type="text" class = "input">
                </div>

            </div>
             <div class = "data-container">
                <div class = "data-name"> 
                    Remind me before
                </div>
                <div class = "data-input"> 
                    <input id = "remind" v-model="remindIn" type="number" class = "input">
                </div>

            </div>
        </div>
            <button @click=" sendData(); addMarker(); getRoute();" class = "search-button" >Search</button>
    </div>
    </div>
    <div>
        Your departure time: {{ departureTime }} min
    </div>
    <gmap-map
      :center="center"
      :zoom="12"
      style="width:100%;  height: 400px;"
      ref="map"
    >
      <gmap-marker
        :key="index"
        v-for="(m, index) in markers"
        :position="m.position"
        @click="center=m.position"
      ></gmap-marker>
    </gmap-map>

  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: "GoogleMap",
  data() {
    return {
      // default to Montreal to keep it simple
      // change this to whatever makes sense
      center: { lat: 45.508, lng: -73.587 },
      markers: [],
      places: [],
      currentPlace: null,
      flightNumber: '',
      phoneNumber: '',
      flightDate: '',
      destination: {
          lat: 51.109996,
          lng: 16.879974
      },
      destinationAPI: {
          lat: '',
          lng: ''
      },
      departureTime: 0,
      currentMarker: null,
      remindIn: 0,
      responseBackend: null
    };
  },

  mounted() {
    this.geolocate();
  },

  methods: {
    getRoute: function () {
      console.log('Jestem w getRoute')
      this.directionsService = new google.maps.DirectionsService()
      this.directionsDisplay = new google.maps.DirectionsRenderer()
      this.directionsDisplay.setMap(this.$refs.map.$mapObject)
      axios.get(`http://10.48.120.158/travel_time?flight_number=`+ this.flightNumber + 
                '&bufor_time=' + this.remindIn + '&user_location=' + this.currentMarker.lat + ',' + this.currentMarker.lng)
        .then(response => {
            this.responseBackend = response.data
            this.destinationAPI.lat = this.responseBackend.coordinates.latitude
            this.destinationAPI.lng = this.responseBackend.coordinates.longitude
            this.departureTime = this.responseBackend.total_duration 
        // JSON responses are automatically parsed.
            console.log('Udalo sie ale nie wiem jak ', this.responseBackend)
            console.log(`http://10.48.120.158/travel_time?flight_number=`+ this.flightNumber + 
                        '&bufor_time=' + this.remindIn + '&user_location=' + this.currentMarker.lat + ',' + this.currentMarker.lng)
            console.log('User location: ' + this.currentMarker.lat + ',' + this.currentMarker.lng)
        var vm = this
        vm.directionsService.route({
            origin: this.currentMarker, // Can be coord or also a search query
            destination: this.destinationAPI,
            travelMode: 'DRIVING'
        
        }, function (response, status) {
            if (status === 'OK') {
            vm.directionsDisplay.setDirections(response) // draws the polygon to the map
            } else {
            console.log('Directions request failed due to ' + status)
            }
        })       
        })

    },
    // receives a place object via the autocomplete component
    setPlace(place) {
      this.currentPlace = place;  
    },
    sendData(){
        console.log('flight: ', this.flightNumber);
        console.log('data: ', this.flightDate);
        console.log('phone number ', this.phoneNumber);
    },
    addMarker() {
      if (this.currentPlace) {
        const marker = {
          lat: this.currentPlace.geometry.location.lat(),
          lng: this.currentPlace.geometry.location.lng()
        };
        this.markers.push({ position: marker });
        this.currentMarker = marker;
        this.places.push(this.currentPlace);
        this.center = marker;
        this.currentPlace = null;
      }
    },
    geolocate: function() {
      navigator.geolocation.getCurrentPosition(position => {
        this.center = {
          lat: position.coords.latitude,
          lng: position.coords.longitude
        };
      });
    }
  }
};
</script>

<style scoped>
    #maps{
       margin: auto;
       width: 90%;
    }
    #search-bar{
       background-image:  linear-gradient(to top,#1e8adf,#2994e6);
       border-bottom-left-radius: 5px;
       border-bottom-right-radius: 5px;
       border-bottom-color:rgb(17, 119, 202);
       border-bottom-style: solid;
       border-bottom-width: 2px;
       border-left-style: solid;
       border-left-width: 2px;
       border-left-color: rgb(17, 119, 202);
       border-right-style: solid;
       border-right-width: 2px;
       border-right-color: rgb(17, 119, 202);
       border-top-left-radius: 5px;
       border-top-right-radius: 5px;
       border-top-color:rgb(17, 119, 202);
       border-top-style: solid;
       border-top-width:2px;
       margin: auto;
       margin-bottom: 5px;
       
    }
    .data-name{
        background-color: rgb(238, 245, 250);
       color: rgb(46, 46, 46);

    }
    .input{
        width:100%;
    }
    .inputs{
        margin: 10px;
    }
    .data-container{
       display: inline;
       margin: 5px;       
       float: left;
       width: 19%;
       border-top-left-radius: 3.23px;
       border-top-right-radius: 3.23px;
       border-top-style: solid;
       border-top-width:2px;
       box-sizing: border-box;
       border-top-color:rgb(238, 245, 250);;
    }
    .search-button{
        background-color: rgb(241, 201, 51);
        border-bottom-left-radius: 5px;
        border-bottom-right-radius: 5px;
        border-bottom-color:rgb(241, 201, 51);
        border-bottom-style: solid;
        border-bottom-width: 2px;
        border-left-style: solid;
        border-left-width: 2px;
        border-left-color: rgb(241, 201, 51);
        border-right-style: solid;
        border-right-width: 2px;
        border-right-color: rgb(241, 201, 51);
        border-top-left-radius: 5px;
        border-top-right-radius: 5px;
        border-top-color:rgb(241, 201, 51);
        border-top-style: solid;
        border-top-width:2px;
        width: 25%;
        margin: 5px;
        color: rgb(7, 53, 144);
        display: inline-block;
    }
</style>
