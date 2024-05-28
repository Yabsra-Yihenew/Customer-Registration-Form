package com.example.customerregistration;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Display extends AppCompatActivity {

    private ArrayAdapter<String> adapter; // Adapter to bind data to the ListView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        // Get customer list from resources
        String[] customerList = getResources().getStringArray(R.array.customer_List);

        // Initialize the adapter with the customer list
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, customerList);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        // Enable multiple choice mode for the ListView
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                // Update the action mode title with the number of selected items
                int checkedCount = listView.getCheckedItemCount();
                mode.setTitle(checkedCount + " selected");
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Inflate the context menu when action mode is created
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.context_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // No preparation needed
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                // Handle context menu item clicks
                SparseBooleanArray selected = listView.getCheckedItemPositions();
                int itemId = item.getItemId();
                if (itemId == R.id.edit) {
                    editItems(selected); // Edit selected items
                    mode.finish(); // Close the action mode
                    return true;
                } else if (itemId == R.id.delete) {
                    deleteItems(selected); // Delete selected items
                    mode.finish(); // Close the action mode
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // No action needed when action mode is destroyed
            }
        });

        // Set OnClickListener for the add customer button
        Button addCustomerBtn = findViewById(R.id.addcustomerBtn);
        addCustomerBtn.setOnClickListener(v -> {
            // Start MainActivity to add a new customer
            Intent intent = new Intent(Display.this, MainActivity.class);
            startActivity(intent);
        });
    }

    // Method to edit selected items
    private void editItems(SparseBooleanArray selected) {
        StringBuilder names = new StringBuilder();
        // Loop through selected items and append their names
        for (int i = 0; i < selected.size(); i++) {
            if (selected.valueAt(i)) {
                String customerName = adapter.getItem(selected.keyAt(i));
                names.append(customerName).append("\n");
            }
        }
        // Display a toast with the names of the items being edited
        Toast.makeText(this, "Editing:\n" + names.toString(), Toast.LENGTH_LONG).show();
    }

    // Method to delete selected items
    private void deleteItems(SparseBooleanArray selected) {
        StringBuilder names = new StringBuilder();
        // Loop through selected items and append their names
        for (int i = 0; i < selected.size(); i++) {
            if (selected.valueAt(i)) {
                String customerName = adapter.getItem(selected.keyAt(i));
                names.append(customerName).append("\n");
            }
        }
        // Display a toast with the names of the items being deleted
        Toast.makeText(this, "Deleting:\n" + names.toString(), Toast.LENGTH_LONG).show();
    }
}
