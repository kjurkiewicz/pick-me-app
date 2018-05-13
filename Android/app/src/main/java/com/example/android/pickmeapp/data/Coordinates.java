package com.example.android.pickmeapp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coordinates {

    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
/*
{
  "arrival_iana": "WRO",
  "arrival_name": "Wroclaw",
  "bufor_time": 10,
  "coordinates": {
    "latitude": 51.1027,
    "longitude": 16.8858
  },
  "distance": "13.4 km",
  "duration": 28,
  "duration_in_traffic": 31,
  "plane_arrival_date": "Sat, 12 May 2018 23:35:00 GMT",
  "total_duration": 41
}
 */

/*
{

        }
        }
        */