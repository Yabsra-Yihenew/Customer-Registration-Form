package com.example.customerregistration;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private EditText dateButton;
    private ImageView chooseImgView; // ImageView for displaying selected image
    private static final int IMAGE_PICK_REQUEST = 1; // Image pick request code
    private EditText FnameEditTxt, editTextPhone, editTextTextEmailAddress, datePicker;
    private RadioGroup radioGroup, customerTypeRadio;
    private Spinner spinner;
    private CheckBox apartment, villa, share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize EditText and ImageView variables
        FnameEditTxt = findViewById(R.id.FnameEditTxt);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        datePicker = findViewById(R.id.datePicker);
        chooseImgView = findViewById(R.id.chooseImgView);
        spinner = findViewById(R.id.spinner);
        apartment = findViewById(R.id.apartment);
        villa = findViewById(R.id.villa);
        share = findViewById(R.id.share);
        radioGroup = findViewById(R.id.radioGroup);
        customerTypeRadio = findViewById(R.id.customerTypeRadio);

        // Initialize the date picker dialog
        initDatePicker();

        Button previewButton = findViewById(R.id.previewBtn);

        // Set OnClickListener to open Preview activity when the button is clicked
        previewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new Bundle to store data
                Bundle bundle = new Bundle();

                // Put data into the Bundle
                bundle.putString("fullName", FnameEditTxt.getText().toString());
                bundle.putString("phoneNumber", editTextPhone.getText().toString());
                bundle.putString("email", editTextTextEmailAddress.getText().toString());
                bundle.putString("dateOfBirth", datePicker.getText().toString());

                // Get selected radio button ID for gender and add to bundle
                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (selectedRadioButtonId != -1) {
                    RadioButton radioButton = findViewById(selectedRadioButtonId);
                    bundle.putString("gender", radioButton.getText().toString());
                }

                // Get selected radio button ID for customer type and add to bundle
                int selectedCustomerTypeId = customerTypeRadio.getCheckedRadioButtonId();
                if (selectedCustomerTypeId != -1) {
                    RadioButton radioButton = findViewById(selectedCustomerTypeId);
                    bundle.putString("customerType", radioButton.getText().toString());
                }

                // Get the selected spinner value and add to bundle
                String spinnerValue = spinner.getSelectedItem().toString();
                bundle.putString("spinnerValue", spinnerValue);

                // Add checkbox values to the bundle
                bundle.putBoolean("isApartmentChecked", apartment.isChecked());
                bundle.putBoolean("isVillaChecked", villa.isChecked());
                bundle.putBoolean("isShareChecked", share.isChecked());

                // Get the image URI and add to the bundle
                Uri imageUri = getImageUri();
                if (imageUri != null) {
                    bundle.putString("imageUri", imageUri.toString());
                }

                // Start the Preview activity and pass the Bundle
                Intent intent = new Intent(MainActivity.this, Preview.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        // Set OnClickListener for choosing image
        chooseImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImg(v);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Handle menu item selection
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

    // Show a dialog to confirm exiting the app
    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Exit the application
                finishAffinity();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Set the app's locale to the specified language
    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        recreate();
    }

    // Method to handle image selection
    public void chooseImg(View view) {
        // Create an intent to open the image gallery
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_PICK_REQUEST);
    }

    // Method to get the image URI from the ImageView
    private Uri getImageUri() {
        if (chooseImgView.getDrawable() != null) {
            return (Uri) chooseImgView.getTag();
        }
        return null;
    }

    // Override onActivityResult to handle the result of the image selection
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK_REQUEST && resultCode == RESULT_OK && data != null) {
            // Get the URI of the selected image
            Uri imageUri = data.getData();
            // Set the selected image URI to the ImageView
            chooseImgView.setImageURI(imageUri);
            // Set the tag of the ImageView to the image URI
            chooseImgView.setTag(imageUri);
        }
    }

    // Method to initialize the date picker
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                MainActivity.this.datePicker.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        // Set the maximum date to 2010
        cal.set(2010, Calendar.DECEMBER, 31);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
    }

    // Method to create a date string in the format "day/month/year"
    private String makeDateString(int day, int month, int year) {
        return day + "/" + month + "/" + year;
    }

    // Method to open the date picker dialog
    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
}
