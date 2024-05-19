package com.example.customerregistration;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

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

            Button saveButton = findViewById(R.id.saveBtn);
            saveButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Preview.this);
                    builder.setMessage("Registered successfully");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Start the Display activity
                            Intent intent = new Intent(Preview.this, Display.class);
                            // Pass any data you need to the Display activity here
                            startActivity(intent);
                            // Finish the Preview activity
                            finish();
                        }
                    });
                    builder.show();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.exit) {
            showExitDialog();
            return true;
        }
        if (id == R.id.amh) {
            setLocale("am");
            return true;
        }
        if (id == R.id.eng) {
            setLocale("en");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Exit the app
                finishAffinity();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        recreate();
    }
}
