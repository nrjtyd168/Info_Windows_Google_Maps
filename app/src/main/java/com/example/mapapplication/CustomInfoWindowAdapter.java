package com.example.mapapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.Arrays;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private LayoutInflater inflater;

    public CustomInfoWindowAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = inflater.inflate(R.layout.custom_info_window, null);

        TextView titleTextView = view.findViewById(R.id.titleTextView);
        TextView snippetTextView = view.findViewById(R.id.snippetTextView);
        ImageView iconView = view.findViewById(R.id.imageView);

        titleTextView.setText(marker.getTitle());
        snippetTextView.setText(marker.getSnippet());

        String[] construction = {"Completed", "Halted", "Incomplete", "In-Progress"};

        String[] hospitals = {"Allergy and Immunology", "Anesthesiology", "Dermatology", "Diagnostic Radiology",
                "Emergency Medicine", "Family Medicine", "Internal Medicine", "Medical Genetics", "MultiSpeciality",
                "Neurology", "Obstetrics and Gynecology", "Ophthalmology", "Pathology", "Pediatrics", "Physical Medicine",
                "Rehabilitation", "Preventive Medicine", "Psychiatry", "Radiation Oncology", "Surgery", "Urology"};

        String[] restaurants = {"Veg", "Non-Veg", "Veg & Non-Veg"};

        String[] toilets = {"Gents", "Ladies", "Ladies & Gents"};

        if (marker.getSnippet().contains("Sunny"))
            iconView.setImageResource(R.drawable.sun);
        else if (marker.getSnippet().contains("Rainy"))
            iconView.setImageResource(R.drawable.rainyday);
        else if (marker.getSnippet().contains("Stormy"))
            iconView.setImageResource(R.drawable.strom);
        else if (marker.getSnippet().contains("Clear"))
            iconView.setImageResource(R.drawable.clearsky);
        else if (marker.getSnippet().contains("Cloudy"))
            iconView.setImageResource(R.drawable.cloudy);
        else if (Arrays.stream(construction).anyMatch(marker.getSnippet()::contains))
            iconView.setImageResource(R.drawable.warning);
        else if (Arrays.stream(hospitals).anyMatch(marker.getSnippet()::contains))
            iconView.setImageResource(R.drawable.medicine);
        else if (Arrays.stream(restaurants).anyMatch(marker.getSnippet()::contains))
            iconView.setImageResource(R.drawable.cutlery);
        else if (Arrays.stream(toilets).anyMatch(marker.getSnippet()::contains))
            iconView.setImageResource(R.drawable.toilet);
        else iconView.setImageResource(R.drawable.gallery);

        return view;
    }
}
