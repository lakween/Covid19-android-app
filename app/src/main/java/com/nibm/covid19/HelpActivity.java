package com.nibm.covid19;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HelpActivity extends AppCompatActivity {

    FloatingActionButton floatingAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        floatingAB = findViewById(R.id.fab);
        floatingAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWhatsApp();
            }
        });

    }
    private void openWhatsApp() {

        String number = "94715375179";
        try {
            Intent sendWA = new Intent("android.intent.action.Main");
            sendWA.setAction(Intent.ACTION_SEND);
            sendWA.setType("text/palain");
            sendWA.putExtra(Intent.EXTRA_TEXT, "Hello, Doctor I'm Not Feeling Well");
            sendWA.putExtra("jid",number+"@s.whatsapp.net");
            sendWA.setPackage("com.whatsapp");
            startActivity(sendWA);



        }catch (Exception e) {
            Toast.makeText(this, "Please install whatsapp form playstore", Toast.LENGTH_SHORT).show();

        }
    }
}
