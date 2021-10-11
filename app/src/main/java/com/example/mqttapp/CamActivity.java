package com.example.mqttapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.marcoscg.ipcamview.IPCamView;

public class CamActivity extends AppCompatActivity {

    Button buttonStartCamIP;
    EditText textURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam);

        buttonStartCamIP = findViewById(R.id.buttonStartCamIP);
        textURL = findViewById(R.id.textURL);
        IPCamView IPCam = findViewById(R.id.ip_cam_view);

        buttonStartCamIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = textURL.getText().toString();
                if (link != null){
                    IPCam.setUrl(link);
                    IPCam.setInterval(5);
                    IPCam.start();
                }
            }
        });

    }
}