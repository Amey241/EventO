package com.example.evento;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText name, email, mobile, pass;
    Button reg;
    ProgressBar pBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        AppController.initialize(getApplicationContext());
        pBar = (ProgressBar) findViewById(R.id.progressBarLoading);
        name = (EditText) findViewById(R.id.fname);
        email = (EditText) findViewById(R.id.email);
        mobile = (EditText) findViewById(R.id.mob_no);
        pass = (EditText) findViewById(R.id.pass);

        reg = (Button) findViewById(R.id.register_btn);


        //Toast.makeText(getApplicationContext(),,Toast.LENGTH_LONG).show();

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                } else {
                    register(name.getText().toString(), email.getText().toString(), pass.getText().toString(), mobile.getText().toString());
                }
            }
        });


    }


    public void register(final String name, final String email, final String pass, final String mobilen) {
        pBar.setVisibility(View.VISIBLE);

        //Log.d("s",Keys.URL.LOGIN_URL);
        StringRequest request = new StringRequest(Request.Method.POST, Keys.URL.register, new Response.Listener<String>() {
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
                        Intent i = new Intent(Register.this, LoginActivity.class);
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
                data.put("name", name);
                data.put("email", email);
                data.put("pass", pass);
                data.put("phone", mobilen);
                return data;
            }
        };

        AppController.getInstance().add(request);
    }
}
