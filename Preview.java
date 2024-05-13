package com.example.customerregistration;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Preview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        // Find TextViews in the Preview layout
        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView phoneNumTextView = findViewById(R.id.phoneNumTextView);
        TextView emailtv = findViewById(R.id.emailtv);
        TextView DOBtv = findViewById(R.id.DOBtv);
        TextView proTypetv = findViewById(R.id.proTypetv);

        // Retrieve field information from the intent
        Intent intent = getIntent();
        if (intent != null) {
            String fullName = intent.getStringExtra("fullName");
            String phoneNumber = intent.getStringExtra("phoneNumber");
            String email = intent.getStringExtra("email");
            String dateOfBirth = intent.getStringExtra("DOBtv");
            String spinnerValue = intent.getStringExtra("spinnerValue");
            TextView prefAreatv = findViewById(R.id.prefAreatv);


            // Extract information from CheckBoxes
            boolean isApartmentChecked = intent.getBooleanExtra("isApartmentChecked", false);
            boolean isVillaChecked = intent.getBooleanExtra("isVillaChecked", false);
            boolean isShareChecked = intent.getBooleanExtra("isShareChecked", false);

           
        }
    }
}

