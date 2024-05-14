package com.example.customerregistration;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Preview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        // Find TextViews and ImageView in the Preview layout
        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView phoneNumTextView = findViewById(R.id.phoneNumTextView);
        TextView emailtv = findViewById(R.id.emailtv);
        TextView DOBtv = findViewById(R.id.DOBtv);
        TextView proTypetv = findViewById(R.id.proTypetv);
        TextView gendertv = findViewById(R.id.gendertv);
        TextView prefAreatv = findViewById(R.id.prefAreatv);
        TextView cusTypetv = findViewById(R.id.cusTypetv);
        ImageView imageView = findViewById(R.id.imageView);

        // Retrieve field information from the intent
        Intent intent = getIntent();
        if (intent != null) {
            String fullName = intent.getStringExtra("fullName");
            String phoneNumber = intent.getStringExtra("phoneNumber");
            String email = intent.getStringExtra("email");
            String dateOfBirth = intent.getStringExtra("dateOfBirth");
            String spinnerValue = intent.getStringExtra("spinnerValue");
            String gender = intent.getStringExtra("gender");
            String customerType = intent.getStringExtra("customerType");

            // Extract information from CheckBoxes
            boolean isApartmentChecked = intent.getBooleanExtra("isApartmentChecked", false);
            boolean isVillaChecked = intent.getBooleanExtra("isVillaChecked", false);
            boolean isShareChecked = intent.getBooleanExtra("isShareChecked", false);

            // Set the retrieved information to the TextViews
            nameTextView.setText(fullName);
            phoneNumTextView.setText(phoneNumber);
            emailtv.setText(email);
            DOBtv.setText(dateOfBirth);
            gendertv.setText(gender);
            cusTypetv.setText(customerType);
            prefAreatv.setText(spinnerValue);

            // Build the property type string
            StringBuilder checkedTypes = new StringBuilder();
            if (isApartmentChecked) {
                checkedTypes.append("Apartment ");
            }
            if (isVillaChecked) {
                checkedTypes.append("Villa ");
            }
            if (isShareChecked) {
                checkedTypes.append("Share ");
            }
            proTypetv.setText(checkedTypes.toString().trim());

            // Set the image URI to the ImageView
            Uri imageUri = Uri.parse(intent.getStringExtra("imageUri"));
            imageView.setImageURI(imageUri);
        }
    }
}
