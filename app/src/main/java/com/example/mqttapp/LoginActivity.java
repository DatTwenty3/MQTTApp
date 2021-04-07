package com.example.mqttapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.widget.Toast;

import com.ramotion.circlemenu.CircleMenuView;

import java.util.concurrent.Executor;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final CircleMenuView circleMenu = findViewById(R.id.circleMenu);

        circleMenu.setEventListener(new CircleMenuView.EventListener(){

            @Override
            public void onButtonClickAnimationEnd(@NonNull CircleMenuView view, int buttonIndex) {
                super.onButtonClickAnimationEnd(view, buttonIndex);
                switch (buttonIndex) {
                    case 0:
                        Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intentMain);
                        break;

                    case 1:
                        Intent intentInfo = new Intent(LoginActivity.this, InfoActivity.class);
                        startActivity(intentInfo);
                        break;

                    case 2:
                        Intent intentMap = new Intent(LoginActivity.this, MapActivity.class);
                        startActivity(intentMap);
                        break;
                    case 3:
                        Intent intentChart = new Intent(LoginActivity.this, ChartActivity.class);
                        startActivity(intentChart);
                        break;
                }
            }

        });

        //FINGER PRINT
        /*Executor executor= ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                //Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
                onBackPressed();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(LoginActivity.this, "Login Success!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(LoginActivity.this, "Login Failed! Please try again. ", Toast.LENGTH_LONG).show();
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("LOGIN TO LEDAT")
                //.setSubtitle("Use Finger to login")
                .setNegativeButtonText("Cancel")
                .build();
        biometricPrompt.authenticate(promptInfo);*/

    }
}