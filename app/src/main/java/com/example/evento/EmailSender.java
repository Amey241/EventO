package com.example.evento;

import static com.example.evento.LoginActivity.loggedInUser;
import static com.example.evento.LoginActivity.userPassword;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class EmailSender {
    private static String name;
    private static String venue;
    private static String message;
    private static String subject;

    public static void setNameVenue(String name, String venue) {
        EmailSender.name = name;
        EmailSender.venue = venue;
    }

    public static void sendEmail(Context context,String caller, String userEmail) {
        StringRequest r = new StringRequest(Request.Method.POST, Keys.URL.sendEmail,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Failed to send email", Toast.LENGTH_SHORT).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                 if(caller.equals("FORGOT")) {
                    subject = "Forgot Password";
                    message = "<h1>Dear Friend</h1>" +
                            "<p>Here is the password:</p>" +userPassword+
                            "<h3> Please Do not share this password!</h3>";
                } else if(caller.equals("INTERESTED")) {
                    subject = "Thank You for Choosing Us!";
                    message = "<h1>Dear Friend " + loggedInUser + ",</h1>" +
                            "<p>We are very glad that you are interested in the event " + name + ". " +
                            "We hope you enjoy the event and have a great time!</p>" +
                            "<h3>See you at, " + venue + "</h3>";
                }

                Map<String, String> params = new HashMap<>();
                params.put("to", userEmail);
                params.put("subject",subject);
                params.put("message", message);
                return params;
            }
        };

        r.setRetryPolicy(new DefaultRetryPolicy(
                500000, // Timeout duration in milliseconds (e.g., 500 seconds)
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, // Max number of retries (default is 1)
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT // Backoff multiplier (default is 1)
        ));


        // Add the r to the RequestQueue
        AppController.getInstance().add(r);
    }
}
