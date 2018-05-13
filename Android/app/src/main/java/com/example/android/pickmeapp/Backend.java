package com.example.android.pickmeapp;

import com.example.android.pickmeapp.data.Coordinates;
import com.example.android.pickmeapp.data.Location;
import com.example.android.pickmeapp.data.LocationData;
import com.example.android.pickmeapp.data.TravelTime;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Backend {
    @GET("airport_location")
    Call<Location> locationAirport(@Query("flight_number") String flightNumber);

    @GET("travel_time")
    Call<TravelTime> travelTime(@Query("flight_number") String flightNumber, @Query("bufor_time") int time,
                                @Query("user_location") String userLocation);
}
