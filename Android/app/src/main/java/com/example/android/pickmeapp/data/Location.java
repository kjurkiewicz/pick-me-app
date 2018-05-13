package com.example.android.pickmeapp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("arrival_iana")
    @Expose
    private String arrivalIana;
    @SerializedName("arrival_name")
    @Expose
    private String arrivalName;
    @SerializedName("coordinates")
    @Expose
    private Coordinates coordinates;

    public  Location(){

    }

    public String getArrivalIana() {
        return arrivalIana;
    }

    public void setArrivalIana(String arrivalIana) {
        this.arrivalIana = arrivalIana;
    }

    public String getArrivalName() {
        return arrivalName;
    }

    public void setArrivalName(String arrivalName) {
        this.arrivalName = arrivalName;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

}
