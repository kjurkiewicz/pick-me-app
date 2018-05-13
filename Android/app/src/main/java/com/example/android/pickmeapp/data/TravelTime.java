package com.example.android.pickmeapp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TravelTime {

    @SerializedName("arrival_iana")
    @Expose
    private String arrivalIana;
    @SerializedName("arrival_name")
    @Expose
    private String arrivalName;
    @SerializedName("bufor_time")
    @Expose
    private Integer buforTime;
    @SerializedName("coordinates")
    @Expose
    private Coordinates coordinates;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("duration_in_traffic")
    @Expose
    private Integer durationInTraffic;
    @SerializedName("plane_arrival_date")
    @Expose
    private String planeArrivalDate;
    @SerializedName("plane_arrival_time")
    @Expose
    private String planeArrivalTime;
    @SerializedName("total_duration")
    @Expose
    private Integer totalDuration;

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

    public Integer getBuforTime() {
        return buforTime;
    }

    public void setBuforTime(Integer buforTime) {
        this.buforTime = buforTime;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getDurationInTraffic() {
        return durationInTraffic;
    }

    public void setDurationInTraffic(Integer durationInTraffic) {
        this.durationInTraffic = durationInTraffic;
    }

    public String getPlaneArrivalDate() {
        return planeArrivalDate;
    }

    public void setPlaneArrivalDate(String planeArrivalDate) {
        this.planeArrivalDate = planeArrivalDate;
    }

    public String getPlaneArrivalTime() {
        return planeArrivalTime;
    }

    public void setPlaneArrivalTime(String planeArrivalTime) {
        this.planeArrivalTime = planeArrivalTime;
    }

    public Integer getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(Integer totalDuration) {
        this.totalDuration = totalDuration;
    }

}