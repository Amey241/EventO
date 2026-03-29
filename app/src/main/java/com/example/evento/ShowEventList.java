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
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowEventList extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener  {

    List<RowItem> rowItems;
    CustomAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event_list);

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

        rowItems = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview_event_cards);

        adapter = new CustomAdapter(ShowEventList.this, rowItems);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // GridLayoutManager with 2 columns


        adapter.setOnItemClickListener((view, position) -> {
            RowItem clickedItem = rowItems.get(position);
            String text = clickedItem.getType();
            String pass = clickedItem.getPass();
            String title = clickedItem.getName();

            if (text.equals("Public")) {
                SharedPreference.save("event_title", title);
                Intent intent = new Intent(ShowEventList.this, Publics.class);
                startActivity(intent);
            } else {
                SharedPreference.save("passkey", pass);
                SharedPreference.save("event_title", title);
                Intent intent = new Intent(ShowEventList.this, Privates.class);
                startActivity(intent);
            }
        });

        // Load event data
        getData();
    }

    private void getData() {
        StringRequest request = new StringRequest(Request.Method.POST, Keys.URL.eventlist,
                response -> {
                    Log.e("==>", response);
                    JSONArray jsonArray = null;
                    try {
                        JSONObject jsb = new JSONObject(response);
                        jsonArray = jsb.getJSONArray("events");
//                        Toast.makeText(ShowEventList.this, "" + jsonArray.length(), Toast.LENGTH_LONG).show();
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject s1 = new JSONObject(jsonArray.getString(i));
                                RowItem item = new RowItem(s1.getString("name"), s1.getString("type"), s1.getString("pass"));
                                rowItems.add(item);
                            }
                        } else {
                            Toast.makeText(ShowEventList.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(getApplicationContext(), "Please check IP address", Toast.LENGTH_LONG).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                return new HashMap<>();
            }
        };

        AppController.getInstance().add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_show_event_list;
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
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem userItem = menu.findItem(R.id.username_dis);
        userItem.setTitle(loggedInUser);
        return super.onPrepareOptionsMenu(menu);
    }

}
