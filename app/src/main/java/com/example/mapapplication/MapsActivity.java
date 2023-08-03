package com.example.mapapplication;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mapapplication.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Chip mWeather, mConstruction, mHospitals, mRestaurants, mToilets;
    private MaterialButton mAdd;
    List<Marker> weather = new ArrayList<>();
    List<Marker> construction = new ArrayList<>();
    List<Marker> restaurants = new ArrayList<>();
    List<Marker> hospitals = new ArrayList<>();
    List<Marker> toilets = new ArrayList<>();
    Boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://your_ip_address:port_number/update_map.php";

        mWeather = findViewById(R.id.weather);
        mConstruction = findViewById(R.id.construction);
        mHospitals = findViewById(R.id.hospitals);
        mRestaurants = findViewById(R.id.restaurants);
        mToilets = findViewById(R.id.toilets);
        mAdd = findViewById(R.id.matButton);

        mWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWeather.isChecked()) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new com.android.volley.Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    String[] status = response.split("~");
                                    if (status[0].equals("success")) {
                                        String[] data = status[1].split(":");
                                        BitmapDescriptor markerIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
                                        for (int i = 0; i < data.length; i++) {
                                            String[] content = data[i].split("`");
                                            LatLng mCurLoc = new LatLng(Double.parseDouble(content[4]), Double.parseDouble(content[5]));
                                            Marker marker = mMap.addMarker(new MarkerOptions()
                                                    .position(mCurLoc)
                                                    .icon(markerIcon)
                                                    .title(content[1] + " °C")
                                                    .snippet(content[2] + "\n" + content[3]));
                                            weather.add(marker);
                                            marker.showInfoWindow();
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Received Unexpected Data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        protected Map<String, String> getParams() {
                            Map<String, String> paramV = new HashMap<>();
                            paramV.put("show", "SELECT * FROM tags WHERE type = 'Weather'");
                            return paramV;
                        }
                    };
                    queue.add(stringRequest);
                } else {
                    for (int i = 0; i < weather.size(); i++) {
                        weather.get(i).remove();
                    }
                }
            }
        });

        mConstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mConstruction.isChecked()) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new com.android.volley.Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    String[] status = response.split("~");
                                    if (status[0].equals("success")) {
                                        String[] data = status[1].split(":");
                                        BitmapDescriptor markerIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
                                        for (int i = 0; i < data.length; i++) {
                                            String[] content = data[i].split("`");
                                            LatLng mCurLoc = new LatLng(Double.parseDouble(content[4]), Double.parseDouble(content[5]));
                                            Marker marker = mMap.addMarker(new MarkerOptions()
                                                    .position(mCurLoc)
                                                    .icon(markerIcon)
                                                    .title(content[1])
                                                    .snippet(content[2] + "\n" + content[3]));
                                            construction.add(marker);
                                            marker.showInfoWindow();
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Received Unexpected Data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        protected Map<String, String> getParams() {
                            Map<String, String> paramV = new HashMap<>();
                            paramV.put("show", "SELECT * FROM tags WHERE type = 'Construction'");
                            return paramV;
                        }
                    };
                    queue.add(stringRequest);

                } else {
                    for (int i = 0; i < construction.size(); i++) {
                        construction.get(i).remove();
                    }
                }
            }
        });

        mRestaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRestaurants.isChecked()) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new com.android.volley.Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    String[] status = response.split("~");
                                    if (status[0].equals("success")) {
                                        String[] data = status[1].split(":");
                                        BitmapDescriptor markerIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
                                        for (int i = 0; i < data.length; i++) {
                                            String[] content = data[i].split("`");
                                            LatLng mCurLoc = new LatLng(Double.parseDouble(content[4]), Double.parseDouble(content[5]));
                                            Marker marker = mMap.addMarker(new MarkerOptions()
                                                    .position(mCurLoc)
                                                    .icon(markerIcon)
                                                    .title(content[1])
                                                    .snippet(content[2] + "\n" + content[3]));
                                            restaurants.add(marker);
                                            marker.showInfoWindow();
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Received Unexpected Data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        protected Map<String, String> getParams() {
                            Map<String, String> paramV = new HashMap<>();
                            paramV.put("show", "SELECT * FROM tags WHERE type = 'Restaurants'");
                            return paramV;
                        }
                    };
                    queue.add(stringRequest);

                } else {
                    for (int i = 0; i < restaurants.size(); i++) {
                        restaurants.get(i).remove();
                    }
                }
            }
        });

        mHospitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHospitals.isChecked()) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new com.android.volley.Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    String[] status = response.split("~");
                                    if (status[0].equals("success")) {
                                        String[] data = status[1].split(":");
                                        BitmapDescriptor markerIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA);
                                        for (int i = 0; i < data.length; i++) {
                                            String[] content = data[i].split("`");
                                            LatLng mCurLoc = new LatLng(Double.parseDouble(content[4]), Double.parseDouble(content[5]));
                                            Marker marker = mMap.addMarker(new MarkerOptions()
                                                    .position(mCurLoc)
                                                    .icon(markerIcon)
                                                    .title(content[1])
                                                    .snippet(content[2] + "\n" + content[3]));
                                            hospitals.add(marker);
                                            marker.showInfoWindow();
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Received Unexpected Data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        protected Map<String, String> getParams() {
                            Map<String, String> paramV = new HashMap<>();
                            paramV.put("show", "SELECT * FROM tags WHERE type = 'Hospitals'");
                            return paramV;
                        }
                    };
                    queue.add(stringRequest);

                } else {
                    for (int i = 0; i < hospitals.size(); i++) {
                        hospitals.get(i).remove();
                    }
                }
            }
        });

        mToilets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mToilets.isChecked()) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new com.android.volley.Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    String[] status = response.split("~");
                                    if (status[0].equals("success")) {
                                        String[] data = status[1].split(":");
                                        BitmapDescriptor markerIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
                                        for (int i = 0; i < data.length; i++) {
                                            String[] content = data[i].split("`");
                                            LatLng mCurLoc = new LatLng(Double.parseDouble(content[4]), Double.parseDouble(content[5]));
                                            Marker marker = mMap.addMarker(new MarkerOptions()
                                                    .position(mCurLoc)
                                                    .icon(markerIcon)
                                                    .title(content[1])
                                                    .snippet(content[2] + "\n" + content[3]));
                                            toilets.add(marker);
                                            marker.showInfoWindow();
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Received Unexpected Data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        protected Map<String, String> getParams() {
                            Map<String, String> paramV = new HashMap<>();
                            paramV.put("show", "SELECT * FROM tags WHERE type = 'Toilets'");
                            return paramV;
                        }
                    };
                    queue.add(stringRequest);

                } else {
                    for (int i = 0; i < toilets.size(); i++) {
                        toilets.get(i).remove();
                    }
                }
            }
        });

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = true;
                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        if (flag == true) {
                            Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title(latLng.toString()));
                            openDialog(latLng, "Add");
                            marker.remove();
                            flag = false;
                        }
                    }
                });
            }
        });
    }

    public void openDialog(LatLng latLng, String act) {
        Bundle coordinates = new Bundle();
        String latlng = latLng.toString().substring(latLng.toString().indexOf("(") + 1, latLng.toString().indexOf(")"));
        coordinates.putString("coordinates", latlng + "," + act);

        AddDialog addDialog = new AddDialog();
        addDialog.setArguments(coordinates);
        addDialog.show(getSupportFragmentManager(), "AddDialog");
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Enable Location Icon
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationClickListener(this);

        // Custom Info Window
        LayoutInflater inflater = LayoutInflater.from(this);
        CustomInfoWindowAdapter infoWindowAdapter = new CustomInfoWindowAdapter(inflater);
        mMap.setInfoWindowAdapter(infoWindowAdapter);

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                LatLng latLng = marker.getPosition();
                openDialog(latLng, "Edit");
            }
        });

        // Add a marker in NRSC and move the camera
        LatLng coordinate = new LatLng(17.522245, 78.444768);
        mMap.addMarker(new MarkerOptions()
                .position(coordinate)
                .title("NRSC")
                .snippet("Hyderabad"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinate));
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        LatLng mCurLoc = new LatLng(location.getLatitude(), location.getLongitude());
        Marker melbourne = mMap.addMarker(new MarkerOptions()
                .position(mCurLoc)
                .title("Me")
                .snippet("Current Location"));
        melbourne.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mCurLoc));
    }

}