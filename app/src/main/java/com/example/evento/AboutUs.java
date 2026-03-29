package com.example.evento;

import static com.example.evento.LoginActivity.loggedInUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class AboutUs extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        invalidateOptionsMenu();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_about_us;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        int itemId = item.getItemId();
        if (itemId == R.id.menu_create_event) {
            intent = new Intent(this, CreateEvent.class);
            startActivity(intent);
        } else if (itemId == R.id.menu_delete_event) {
            intent = new Intent(this, Delete.class);
            startActivity(intent);
        } else if (itemId == R.id.menu_about_us) {
            intent = new Intent(this, AboutUs.class);
            startActivity(intent);
        } else if (itemId == R.id.menu_view_event) {
            intent = new Intent(this, ShowEventList.class);
            startActivity(intent);
        } else if (itemId == R.id.menu_vendor) {
            intent = new Intent(this, ShowVendorList.class);
            startActivity(intent);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem userItem = menu.findItem(R.id.username_dis);
        userItem.setTitle(loggedInUser);
        return super.onPrepareOptionsMenu(menu);
    }
}