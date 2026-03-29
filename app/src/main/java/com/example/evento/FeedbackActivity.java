package com.example.evento;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class FeedbackActivity extends AppCompatActivity {

    EditText etName, etEmail, etFeedback;
    RadioGroup ratingGroup;
    Button btnSubmit;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        // Initialize UI Components
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etFeedback = findViewById(R.id.etFeedback);
        ratingGroup = findViewById(R.id.ratingGroup);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Initialize Navigation Drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        // Handle Toggle Button for Drawer
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Ensure Action Bar is not null
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Log.d("Navigation", "Menu item clicked: " + id);

                if (id == R.id.nav_feedback) {
                    // Already in FeedbackActivity, do nothing
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });

        // Submit Button Logic
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String feedback = etFeedback.getText().toString().trim();

                int selectedId = ratingGroup.getCheckedRadioButtonId();
                String rating;

                if (selectedId == -1) {
                    rating = "Not Rated";
                } else {
                    RadioButton selectedRating = findViewById(selectedId);
                    rating = selectedRating.getText().toString();
                }

                if (name.isEmpty() || email.isEmpty() || feedback.isEmpty()) {
                    Toast.makeText(FeedbackActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    String message = "Thank you, " + name + "!\nYour Rating: " + rating + "\nFeedback: " + feedback;
                    Toast.makeText(FeedbackActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Handle Navigation Drawer Toggle
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}