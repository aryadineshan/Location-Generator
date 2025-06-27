package com.example.locationgenerator;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText addressField;
    Button geocodeButton, showMapButton, reverseGeocodeButton;
    TextView resultText;

    double latitude = 0.0;
    double longitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addressField = findViewById(R.id.addressField);
        geocodeButton = findViewById(R.id.geocodeButton);
        showMapButton = findViewById(R.id.showMapButton);

        resultText = findViewById(R.id.resultText);

        geocodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = addressField.getText().toString();
                if (address.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter an address", Toast.LENGTH_SHORT).show();
                    return;
                }

                Geocoder geocoder = new Geocoder(MainActivity.this);
                try {
                    List<Address> addresses = geocoder.getFromLocationName(address, 1);
                    if (!addresses.isEmpty()) {
                        latitude = addresses.get(0).getLatitude();
                        longitude = addresses.get(0).getLongitude();
                        resultText.setText("Latitude: " + latitude + "\nLongitude: " + longitude);
                    } else {
                        resultText.setText("No Address Found");
                    }
                } catch (IOException e) {
                    resultText.setText("Geocoder not available");
                }
            }
        });

        showMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (latitude == 0.0 && longitude == 0.0) {
                    Toast.makeText(MainActivity.this, "Please geocode first", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Use Google Maps HTTP URL
                String uri = "https://www.google.com/maps/search/?api=1&query=" + latitude + "," + longitude;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });


    }
}
