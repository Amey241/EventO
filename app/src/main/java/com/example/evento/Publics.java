package com.example.evento;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Publics extends AppCompatActivity {
    List<RowItemN> rowItems;
    CustomAdapterName adapter;
    ListView l1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publics);
        l1 = (ListView) findViewById(R.id.l1);
        rowItems = new ArrayList<RowItemN>();

        getData(SharedPreference.get("event_title"));

    }

    private void getData(String e_t1) {


        StringRequest request = new StringRequest(Request.Method.POST, Keys.URL.geteventname, new Response.Listener<String>() {
            //StringRequest request = new StringRequest(Method.POST, Keys.URL.LOGIN_URL, new Listener<String>() {
            @Override
            public void onResponse(String arg0) {

                Log.e("==>", arg0);
                JSONArray jsonArray = null;
                try {
                    JSONObject jsb = new JSONObject(arg0);
                    jsonArray = jsb.getJSONArray("events");
//                    Toast.makeText(Publics.this, "" + jsonArray.length(), Toast.LENGTH_LONG).show();
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            //JSONObject json = jsonArray.getJSONObject(i);

                            JSONObject s1 = new JSONObject(jsonArray.getString(i));
                            //Log.i("=>",s1);

                            RowItemN item = new RowItemN(s1.getString("name"), s1.getString("descr"), s1.getString("venue"),s1.getString("date"),s1.getString("exp"),s1.getString("upby"));

                            rowItems.add(item);
                        }

                    } else {
                        Toast.makeText(Publics.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                    adapter = new CustomAdapterName(Publics.this, rowItems);
                    l1.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
             /*   try {
                    JSONObject json = new JSONObject(arg0);

                    if(json.getString("success").equals("0")){
                        Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_LONG).show();
                    }else{
                        Intent i = new Intent(getApplicationContext(),DashBoard.class);
                        startActivity(i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError arg0) {

                Toast.makeText(getApplicationContext(), "Please check IP addess", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name",SharedPreference.get("event_title"));
                return params;
            }
        };

        AppController.getInstance().add(request);
    }
}
