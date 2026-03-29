package com.example.evento;

import static com.example.evento.LoginActivity.loggedInUser;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatActivity;
import com.example.evento.DatePickerFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateEvent extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener  {

    EditText event_title,venue,desc,date,exp;
    Button submit;
    EditText evn_pass;
    ProgressBar pBar;
    Spinner type;
    String selectedItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

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

        type = (Spinner)findViewById(R.id.type1);
        date = (EditText)findViewById(R.id.add_date);
        venue = (EditText)findViewById(R.id.add_loc);
        desc = (EditText)findViewById(R.id.add_desc);
        exp = (EditText)findViewById(R.id.add_expense);
        event_title= (EditText)findViewById(R.id.event_name);
        evn_pass= (EditText)findViewById(R.id.evn_pass);
        submit= (Button)findViewById(R.id.submit);
        pBar= (ProgressBar)findViewById(R.id.progress);

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItem = adapterView.getItemAtPosition(i).toString();
                if(selectedItem.equals("Private")){
                    evn_pass.setVisibility(View.VISIBLE);
                }
                else {
                    evn_pass.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(event_title.getText().toString().isEmpty() || venue.getText().toString().isEmpty()||desc.getText().toString().isEmpty()||date.getText().toString().isEmpty()||exp.getText().toString().isEmpty()) {
                    Toast.makeText(CreateEvent.this, "Please Fll All Fields!", Toast.LENGTH_SHORT).show();
                } else {
                    GetData(event_title.getText().toString(),venue.getText().toString(),desc.getText().toString(),date.getText().toString(),exp.getText().toString());
                }
            }
        });

    }
    public void GetData(final String name1, final String venue1, final String desc1, final String date1, final String exp1) {
        pBar.setVisibility(View.VISIBLE);

        //Log.d("s",Keys.URL.LOGIN_URL);
        StringRequest request = new StringRequest(Request.Method.POST, com.example.evento.Keys.URL.createevent, new Response.Listener<String>() {
            //StringRequest request = new StringRequest(Method.POST, Keys.URL.LOGIN_URL, new Listener<String>() {
            @Override
            public void onResponse(String arg0) {
                Log.i("==>", arg0);
                pBar.setVisibility(View.GONE);

                try {

                    JSONObject json = new JSONObject(arg0);

                    if (json.getString("success").equals("0")) {
                        Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_LONG).show();
                        Intent i = new Intent(CreateEvent.this, com.example.evento.ShowEventList.class);
                        startActivity(i);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError arg0) {
                pBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Please check IP addess", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<String, String>();
                data.put("name", name1);
                data.put("venue", venue1);
                data.put("descr", desc1);
                data.put("date", date1);
                data.put("exp", exp1);
                data.put("type",selectedItem);
                data.put("pass",evn_pass.getText().toString());
                data.put("upby", com.example.evento.SharedPreference.get("username"));
                return data;
            }
        };

        AppController.getInstance().add(request);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_create_event;
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
