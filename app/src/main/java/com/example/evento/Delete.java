package com.example.evento;

import static com.example.evento.LoginActivity.loggedInUser;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mindscript-sagar on 07-03-2017.
 */
public class Delete extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{
    List<RowItem> rowItems;
    RecyclerView recyclerView;
    CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

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

        rowItems = new ArrayList<RowItem>();
        recyclerView = findViewById(R.id.recyclerview_delete_cards);

        adapter = new CustomAdapter(Delete.this, rowItems);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        Search();
        adapter.setOnItemClickListener((view, position) -> {
            // Retrieve clicked item directly from the adapter
            RowItem clickedItem = rowItems.get(position);
            String pos = clickedItem.getName();
            SharedPreference.save("pos", pos);
            delete();
        });

    }

    private void delete() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Delete.this);

        // Setting Dialog Title
        alertDialog.setTitle("Confirm Delete...");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want delete this?");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.delete);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {


                DeleteRec(SharedPreference.get("pos"));
                // Write your code here to invoke YES event
                Toast.makeText(getApplicationContext(), "YES", Toast.LENGTH_SHORT).show();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                Toast.makeText(getApplicationContext(), "NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private void DeleteRec(final String pos1) {



        StringRequest request = new StringRequest(Request.Method.POST, Keys.URL.delete, new Response.Listener<String>() {
            //StringRequest request = new StringRequest(Method.POST, Keys.URL.LOGIN_URL, new Listener<String>() {
            @Override
            public void onResponse(String arg0) {

                try {
                    JSONObject json = new JSONObject(arg0);

                    if(json.getString("success").equals("0")){
                        Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_LONG).show();
                    }else{
                        Intent i = new Intent(getApplicationContext(),ShowEventList.class);
                        startActivity(i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError arg0) {

                Toast.makeText(getApplicationContext(), "Please check IP addess", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", pos1);
                return params;
            }
        };

        AppController.getInstance().add(request);
    }

    public void Search() {
        class GetDataClass extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Delete.this, "Fetching...", null, true, false);
            }


            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();


                RequestClass ruc = new RequestClass();
                String result = ruc.sendPostRequest(Keys.URL.getlist, data);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.d("String get from url", s);
                String sss = "";
                loading.dismiss();
                JSONArray jsonArray = null;
                try {
                    JSONObject jsb = new JSONObject(s);
                    jsonArray = jsb.getJSONArray("name");
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            //JSONObject json = jsonArray.getJSONObject(i);

                            String s1 = jsonArray.getString(i);
                            Log.i("=>",s1);
                            RowItem item = new RowItem(s1,"","");

                            rowItems.add(item);
                        }

                    } else {
                        Toast.makeText(Delete.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        GetDataClass ulc = new GetDataClass();
        ulc.execute();


    }

    protected int getLayoutResourceId() {
        return R.layout.activity_delete;
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
