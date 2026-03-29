package com.example.evento;

import static com.example.evento.LoginActivity.loggedInUser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowVendorList extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    List<VendorItem> vendorItems;
    VendorAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_vendor_list);

        invalidateOptionsMenu();
        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up navigation drawer
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerview_vendor_cards);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Initialize list and adapter
        vendorItems = new ArrayList<>();
        adapter = new VendorAdapter(this, vendorItems);
        recyclerView.setAdapter(adapter);

        // Retrieve vendors from server
        retrieveVendors();
    }

    private void retrieveVendors() {
        StringRequest request = new StringRequest(Request.Method.POST, Keys.URL.vendorList, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("vendors");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject vendorObject = jsonArray.getJSONObject(i);
                        String vendorName = vendorObject.getString("name");
                        String contactInfo = vendorObject.getString("contact_info");

                        VendorItem item = new VendorItem(vendorName, contactInfo);
                        vendorItems.add(item);
                    }
                    adapter.notifyDataSetChanged(); // Notify adapter of data change
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ShowVendorList.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ShowVendorList.this, "Error retrieving vendors: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().add(request);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_show_vendor_list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem userItem = menu.findItem(R.id.username_dis);
        userItem.setTitle(loggedInUser);
        return super.onPrepareOptionsMenu(menu);
    }
}
