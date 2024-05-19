package com.example.customerregistration;

import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Display extends AppCompatActivity {

    private ActionMode actionMode;
    private int selectedItemPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        String[] customerList = getResources().getStringArray(R.array.customer_List);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, customerList);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (actionMode != null) {
                    return false;
                }
                selectedItemPosition = position;
                actionMode = startActionMode(actionModeCallback);
                view.setSelected(true);
                return true;
            }
        });

        Button addCustomerBtn = findViewById(R.id.addcustomerBtn);
        addCustomerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Display.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private final ActionMode.Callback actionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int itemId = item.getItemId();
            if (itemId == R.id.edit) {
                editItem(selectedItemPosition);
                mode.finish();
                return true;
            } else if (itemId == R.id.delete) {
                deleteItem(selectedItemPosition);
                mode.finish();
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
        }
    };

    private void editItem(int position) {
        Toast.makeText(this, "Edit item at position: " + position, Toast.LENGTH_LONG).show();
    }

    private void deleteItem(int position) {
        Toast.makeText(this, "Delete item at position: " + position, Toast.LENGTH_LONG).show();
    }
}
