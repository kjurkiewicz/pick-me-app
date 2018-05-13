package com.example.android.pickmeapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.pickmeapp.data.LocationData;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.Date) EditText mEditText;
    @BindView(R.id.Location) EditText mLocationText;
    @BindView(R.id.FlightNumber) EditText mFlightNumber;
    @BindView(R.id.buttonShowRoute) Button mShowRoute;
    Calendar myCalendar = Calendar.getInstance();
    LocationData mLocationData = new LocationData();
    int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        setupDatePicker();
        setupLocationPicker();

        mShowRoute.setOnClickListener(view -> {
            Intent intent = new Intent(this, MapsActivity.class);
            mLocationData.FlightNumber = mFlightNumber.getText().toString();
            mLocationData.Date = mEditText.toString();
            intent.putExtra(LocationData.TAG, mLocationData);
            startActivity(intent);
        });
    }
    private void setupLocationPicker() {
        mLocationText.setOnClickListener(view -> {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            try {
                startActivityForResult(builder.build(MainActivity.this),PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this,data);
                ///place.getLatLng().latitude
                mLocationData.LocationLon = place.getLatLng().longitude;
                mLocationData.LocationLat = place.getLatLng().latitude;
                mLocationData.AirportLat  = 51.127581;
                mLocationData.AirportLon  = 16.784293;
                String toastMsg = String.format("Place: %s", place.getAddress());
                mLocationText.setText(toastMsg);
            }
        }
    }

    private void setupDatePicker() {
        final DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "MM/dd/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            mEditText.setText(sdf.format(myCalendar.getTime()));
        };
        mEditText.setOnClickListener(v -> new DatePickerDialog(MainActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());
    }

}
