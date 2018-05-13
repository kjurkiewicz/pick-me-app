package com.example.android.pickmeapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.android.pickmeapp.data.Coordinates;
import com.example.android.pickmeapp.data.Location;
import com.example.android.pickmeapp.data.LocationData;
import com.example.android.pickmeapp.data.TravelTime;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.w3c.dom.Text;

import java.io.IOException;
import java.time.Instant;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.google.android.gms.common.images.ImageManager.PRIORITY_HIGH;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final int FINE_LOCATION_PERMISSION_REQUEST = 1;
    private GoogleMap mMap;
    private LocationData mLocationData;
    Retrofit mRetrofit;
    Backend mService;
    @BindView(R.id.time_to_departue)
    TextView mTextViewDepartue;
    @BindView(R.id.arrival_time)
    TextView mPlaneArrivalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        LocationData locationData = getIntent().getParcelableExtra(LocationData.TAG);
        if (locationData == null) {
            throw new IllegalArgumentException("No data passed");
        }


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(1, TimeUnit.SECONDS);
        httpClient.writeTimeout(1, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://10.48.120.158/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        mService = mRetrofit.create(Backend.class);
        mLocationData = locationData;
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(true);

        mService.locationAirport(mLocationData.FlightNumber).enqueue(new Callback<Location>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
                try {
                    Location x = response.body();
                    setPosition(x);
                }catch (Exception e){
                    onFailure(call, null);
                }
            }

            @Override
            public void onFailure(Call<Location> call, Throwable t) {
                Location x = new Location();
                x.setArrivalIana("WRO");
                x.setArrivalName("Wroclaw");
                x.setCoordinates(new Coordinates());
                x.getCoordinates().setLatitude(51.1027);
                x.getCoordinates().setLongitude(16.8858);
                setPosition(x);
            }
        });
        timeFetch();
        // Add a marker in Sydney and move the camera
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    FINE_LOCATION_PERMISSION_REQUEST);
        }
        /*Thread thread = new Thread(new Runnable()
        {
            int lastMinute;
            int currentMinute;
            @Override
            public void run()
            {
                lastMinute = currentMinute;
                while (true)
                {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    currentMinute = calendar.get(Calendar.MINUTE);
                    if (currentMinute != lastMinute) {
                        lastMinute = currentMinute;
                        timeFetch();
                    }
                }
            }
        });
        thread.run();*/
    }

    protected void timeFetch() {
        mService.travelTime(mLocationData.FlightNumber,10,mLocationData.LocationLat+","+mLocationData.LocationLon)
                .enqueue(new Callback<TravelTime>() {
                    @Override
                    public void onResponse(Call<TravelTime> call, Response<TravelTime> response) {
                        try {
                            setTime(response.body());
                        }catch (Exception e){
                            onFailure(call, null);
                        }
                    }

                    @Override
                    public void onFailure(Call<TravelTime  > call, Throwable t) {
                        TravelTime x = new TravelTime();
                        x.setArrivalIana("WRO");
                        x.setArrivalName("Wroclaw");
                        x.setCoordinates(new Coordinates());
                        x.getCoordinates().setLatitude(51.1027);
                        x.getCoordinates().setLongitude(16.8858);
                        x.setBuforTime(10);
                        x.setDistance("13.4 km");
                        x.setDuration(28);
                        x.setDurationInTraffic(30);
                        x.setTotalDuration(40);
                        x.setPlaneArrivalDate("2018-05-13 22:40");
                        x.setPlaneArrivalTime("23:35");
                        setTime(x);
                    }
                });
    }

    @SuppressLint("DefaultLocale")
    private void setTime(TravelTime x) {

        mTextViewDepartue.setText(String.format("%d min", x.getTotalDuration()));
        mPlaneArrivalTime.setText(x.getPlaneArrivalDate());
        try {
            DateTimeFormatter dtf =  DateTimeFormat.forPattern("dd MM yyyy HH:mm[:ss]");
            DateTimeFormatter dtf2 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
            String date = (x.getPlaneArrivalDate().replaceAll(".*,", "").replace("May", "05").replace(
                    "GMT",""
            ).trim());

            DateTime st;
            try{
                st = DateTime.parse(date,dtf2);
            }catch (Exception e)
            {
                st = DateTime.now();
                st.plusHours(-st.getHourOfDay());
                st.plusMinutes(-st.getMinuteOfHour());
                st.plusHours(13);
                st.plusMinutes(45);
            }
            DateTime now = DateTime.now();
            int min = Minutes.minutesBetween(now, st).getMinutes();
            generateNotification(this, min);
        }
        catch (Exception e){
            //throw  e;
        }
    }

    protected void setPosition(Location x) {
        com.google.maps.model.LatLng origin = new com.google.maps.model.LatLng(mLocationData.LocationLat, mLocationData.LocationLon);
        com.google.maps.model.LatLng destination = new com.google.maps.model.LatLng(x.getCoordinates().getLatitude(),
                x.getCoordinates().getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(origin.lat,origin.lng),12.0f));

        DateTime now = new DateTime();
        try {
            DirectionsResult result = DirectionsApi.newRequest(getGeoContext()).mode(TravelMode.DRIVING)
                    .origin(origin)
                    .destination(destination)
                    .departureTime(now).await();
            addMarkersToMap(result,mMap);
            addPolyline(result,mMap);
        } catch (ApiException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private GeoApiContext getGeoContext() {
        GeoApiContext geoApiContext = new GeoApiContext();
        return geoApiContext.setQueryRateLimit(3).setApiKey(getResources().getString(R.string.dir_key)
        ).setConnectTimeout(1, TimeUnit.SECONDS).setReadTimeout(1, TimeUnit.SECONDS)
                .setWriteTimeout(1, TimeUnit.SECONDS);
    }

    private void addMarkersToMap(DirectionsResult results, GoogleMap mMap) {
        mMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[0].legs[0].startLocation.lat, results.routes[0].legs[0].startLocation.lng)).title(results.routes[0].legs[0].startAddress));
        mMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[0].legs[0].endLocation.lat, results.routes[0].legs[0].endLocation.lng)).title(results.routes[0].legs[0].startAddress)
                .snippet(getEndLocationTitle(results)));
    }

    private void addPolyline(DirectionsResult results, GoogleMap mMap) {
        List<LatLng> decodedPath = PolyUtil.decode(results.routes[0].overviewPolyline.getEncodedPath());
        mMap.addPolyline(new PolylineOptions().addAll(decodedPath));
    }

    private String getEndLocationTitle(DirectionsResult results) {
        return "Time :" + results.routes[0].legs[0].duration.humanReadable + " Distance :"
                + results.routes[0].legs[0].distance.humanReadable;
    }

    private static void generateNotification(Context context, int timeLeft){
        Intent notificationIntent = new Intent(context, MapsActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentIntent(intent)
                .setPriority(PRIORITY_HIGH) //private static final PRIORITY_HIGH = 5;
                .setContentText("Plane will arrive in "+ timeLeft +" min")
                .setAutoCancel(false)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
    }
}
