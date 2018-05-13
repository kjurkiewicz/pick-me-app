package com.example.android.pickmeapp.data;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class LocationData implements Parcelable{
    public final static String TAG = LocationData.class.getSimpleName();
    public String FlightNumber;
    public double LocationLat;
    public double LocationLon;

    public double AirportLat;
    public double AirportLon;
    public String Date;

    public LocationData(){

    }

    protected LocationData(Parcel in) {
        FlightNumber = in.readString();
        LocationLat = in.readDouble();
        LocationLon = in.readDouble();
        AirportLat = in.readDouble();
        AirportLon = in.readDouble();
        long tmpDate = in.readLong();
        Date = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(FlightNumber);
        dest.writeDouble(LocationLat);
        dest.writeDouble(LocationLon);
        dest.writeDouble(AirportLat);
        dest.writeDouble(AirportLon);
        dest.writeString(Date);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<LocationData> CREATOR = new Parcelable.Creator<LocationData>() {
        @Override
        public LocationData createFromParcel(Parcel in) {
            return new LocationData(in);
        }

        @Override
        public LocationData[] newArray(int size) {
            return new LocationData[size];
        }
    };
}
