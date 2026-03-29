package com.example.evento;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Privates extends AppCompatActivity {
    EditText ed;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privates);

        ed = (EditText) findViewById(R.id.passkey);
        b1 = (Button) findViewById(R.id.submit);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SharedPreference.get("passkey").equals(ed.getText().toString())) {
                    Intent i = new Intent(Privates.this,ShowEvents.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(),"Your key is wrong",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
