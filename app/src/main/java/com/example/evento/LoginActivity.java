package com.example.evento;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class LoginActivity extends AppCompatActivity {
    Button login,sign_up;
    EditText username, password;
    ProgressBar pBar;
    TextView forgot_password;

  public static String loggedInUserEmail = SharedPreference.get("username");
  public static String loggedInUser = SharedPreference.get("name");
  public static String userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button) findViewById(R.id.login);
        sign_up = (Button) findViewById(R.id.sign_up);
        username = (EditText) findViewById(R.id.ed_user);
        password = (EditText) findViewById(R.id.ed_pass);
        pBar = (ProgressBar) findViewById(R.id.progressbar);
        forgot_password = (TextView) findViewById(R.id.forget);

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!username.getText().toString().equals("")) {
                    pBar.setVisibility(android.view.View.VISIBLE);
                    StringRequest request = new StringRequest(Request.Method.POST, Keys.URL.login, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String arg0) {
                            pBar.setVisibility(android.view.View.GONE);
                            try {
                                Log.e("json", arg0);
                                JSONObject json = new JSONObject(arg0);
                                if (json.getString("success").equals("0")) {
                                    Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_LONG).show();
                                    userPassword = json.getString("pass");
                                    if(!userPassword.isEmpty())
                                        EmailSender.sendEmail(LoginActivity.this, "FORGOT", username.getText().toString());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError arg0) {
                            pBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Please check IP address", Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("user", username.getText().toString());
                            params.put("pass", password.getText().toString());
                            return params;
                        }
                    };
                    AppController.getInstance().add(request);
                }
                else
                    Toast.makeText(LoginActivity.this, "Enter Username to get Email", Toast.LENGTH_LONG).show();
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,Register.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (username.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Fill username details", Toast.LENGTH_LONG).show();
                } else if (password.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Fill password details", Toast.LENGTH_LONG).show();
                } else {
                    login(username.getText().toString(), password.getText().toString());
                }

            }
        });

    }

    public void login(final String user, final String pass) {
        pBar.setVisibility(View.VISIBLE);

        //Log.d("s",Keys.URL.LOGIN_URL);
        StringRequest request = new StringRequest(Request.Method.POST, Keys.URL.login, new Response.Listener<String>() {
            //StringRequest request = new StringRequest(Method.POST, Keys.URL.LOGIN_URL, new Listener<String>() {
            @Override
            public void onResponse(String arg0) {
                pBar.setVisibility(View.GONE);

                try {
                    Log.e("json",arg0);
                    JSONObject json = new JSONObject(arg0);
                    if (json.getString("success").equals("0")) {
                        Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_LONG).show();
                        SharedPreference.save("username",json.getString("username"));
                        SharedPreference.save("userid",json.getString("userid"));
                        loggedInUser = json.getString("name");
                        loggedInUserEmail = json.getString("username");
                        Intent i = new Intent(LoginActivity.this, ShowEventList.class);
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("user", user);
                params.put("pass", pass);
                return params;
            }
        };

        AppController.getInstance().add(request);
    }

    /*public void onBackPressed() {
        CustomDialog.ExitAppDialog(Dashboard.this);
    }*/
}
