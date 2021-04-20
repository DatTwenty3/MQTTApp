package com.example.mqttapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    //INTERFACE
    Switch swtDV1;
    Switch swtDV2;

    TextView txtTem;
    TextView txtAH;
    TextView txtSM;
    TextView txtPw;
    TextView txtSttDV1;
    TextView txtSttDV2;
    TextView txtCN;
    TextView txtpH;
    TextView txtWaTemp;
    TextView txtORP;


    //VOICE CONTROL
    ImageView imgMicCtrl;
    TextView txtMicCtrl;
    private static final int RECOGNIZER_RESULT = 1;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;

    MqttAndroidClient client;

    String status;
    String voiceCtrlMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MqttConnectOptions options = new MqttConnectOptions();

        //LOGIN MQTT
        options.setUserName("xrsvhbaz");
        options.setPassword("D6CFPuh9LfjP".toCharArray());

        //CONNECT MQTT
        String clientId = MqttClient.generateClientId();
        client =
                new MqttAndroidClient(this.getApplicationContext(), "tcp://driver.cloudmqtt.com:18805",
                        clientId);

        //HIEN THI MESSAGE KHI NHAN DANG KY TU TOPIC
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {

                if(topic.equals("Status")) {

                    status = message + "";

                    if(status.equals("SttDV1ON")){
                        txtSttDV1.setText("✅");
                    }
                    if(status.equals("SttDV2ON")){
                        txtSttDV2.setText("✅");
                    }
                    if(status.equals("SttDV1OFF")){
                        txtSttDV1.setText("❎");
                    }
                    if(status.equals("SttDV2OFF")){
                        txtSttDV2.setText("❎");
                    }
                }

                if(topic.equals("Temp")) {
                    txtTem.setText(message + "");
                }

                if(topic.equals("AirHumi")) {
                    txtAH.setText(message + "");
                }

                if(topic.equals("SoilMois")) {
                    txtSM.setText(message + "");
                }

                if(topic.equals("Power")) {
                    txtPw.setText(message + "");
                }

                if(topic.equals("pH")) {
                    txtpH.setText(message + "");
                }

                if(topic.equals("ORP")) {
                    txtORP.setText(message + "");
                }

                if(topic.equals("WaTemp")) {
                    txtWaTemp.setText(message + "");
                }

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
        ///////////////////////////////////////////////////////////////////////////

        //KET NOI VOI MQTT
        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d("MQTT", "onSuccess");

                    txtCN.setText("⚡ Connected");

                    ////////////////////////////SUBSCRICE TOPIC
                    String topicTem = "Temp";
                    String topicAH = "AirHumi";
                    String topicSM = "SoilMois";
                    String topicPW = "Power";
                    String topicStt = "Status";
                    String topicpH = "pH";
                    String topicORP = "ORP";
                    String topicWaTemp = "WaTemp";

                    int qos = 2;

                    try {
                        IMqttToken subToken = client.subscribe(topicTem, qos);
                        subToken.setActionCallback(new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                Log.d("MQTT","Subscrice " + topicTem + " Sucess!");
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken,
                                                  Throwable exception) {
                                Log.d("MQTT","Subscrice " + topicTem + " Failed!");

                            }
                        });
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }

                    try {
                        IMqttToken subToken = client.subscribe(topicAH, qos);
                        subToken.setActionCallback(new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                Log.d("MQTT","Subscrice " + topicAH + " Sucess!");
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken,
                                                  Throwable exception) {
                                Log.d("MQTT","Subscrice " + topicAH + " Failed!");

                            }
                        });
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }

                    try {
                        IMqttToken subToken = client.subscribe(topicSM, qos);
                        subToken.setActionCallback(new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                Log.d("MQTT","Subscrice " + topicSM + " Sucess!");
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken,
                                                  Throwable exception) {
                                Log.d("MQTT","Subscrice " + topicSM + " Failed!");

                            }
                        });
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }

                    try {
                        IMqttToken subToken = client.subscribe(topicPW, qos);
                        subToken.setActionCallback(new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                Log.d("MQTT","Subscrice " + topicPW + " Sucess!");
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken,
                                                  Throwable exception) {
                                Log.d("MQTT","Subscrice " + topicPW + " Failed!");

                            }
                        });
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }

                    try {
                        IMqttToken subToken = client.subscribe(topicStt, qos);
                        subToken.setActionCallback(new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                Log.d("MQTT","Subscrice " + topicStt + " Sucess!");
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken,
                                                  Throwable exception) {
                                Log.d("MQTT","Subscrice " + topicStt + " Failed!");

                            }
                        });
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }

                    try {
                        IMqttToken subToken = client.subscribe(topicpH, qos);
                        subToken.setActionCallback(new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                Log.d("MQTT","Subscrice " + topicpH + " Sucess!");
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken,
                                                  Throwable exception) {
                                Log.d("MQTT","Subscrice " + topicpH + " Failed!");

                            }
                        });
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }

                    try {
                        IMqttToken subToken = client.subscribe(topicORP, qos);
                        subToken.setActionCallback(new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                Log.d("MQTT","Subscrice " + topicORP + " Sucess!");
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken,
                                                  Throwable exception) {
                                Log.d("MQTT","Subscrice " + topicORP + " Failed!");

                            }
                        });
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }

                    try {
                        IMqttToken subToken = client.subscribe(topicWaTemp, qos);
                        subToken.setActionCallback(new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                Log.d("MQTT","Subscrice " + topicWaTemp + " Sucess!");
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken,
                                                  Throwable exception) {
                                Log.d("MQTT","Subscrice " + topicWaTemp + " Failed!");

                            }
                        });
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }

                    ///////////////////////////////////////////////////////////////////////////
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d("MQTT", "onFailure");
                    txtCN.setText("⚠ Not Connect");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

        //INTERFACE
        swtDV1 = (Switch) findViewById(R.id.switchDevice1);
        swtDV1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isCheckedDV1) {
                if (isCheckedDV1) {
                    pubMQTT("BUTTON","DV1On");
                }
                else{
                    pubMQTT("BUTTON","DV1Off");
                }
            }
        });

        swtDV2 = (Switch) findViewById(R.id.switchDevice2);
        swtDV2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isCheckedDV2) {
                if (isCheckedDV2) {
                    pubMQTT("BUTTON","DV2On");
                }
                else{
                    pubMQTT("BUTTON","DV2Off");
                }
            }
        });

        txtTem = (TextView) findViewById(R.id.textTemperature);
        txtAH = (TextView) findViewById(R.id.textAirHumi);
        txtSM = (TextView) findViewById(R.id.textSoilMoisture);
        txtPw = (TextView) findViewById(R.id.textPower);
        txtpH = (TextView) findViewById(R.id.textpH);
        txtWaTemp = (TextView) findViewById(R.id.textWaterTemperature);
        txtORP = (TextView) findViewById(R.id.textORP);

        txtSttDV1 = (TextView) findViewById(R.id.textStatusDV1);
        txtSttDV2 = (TextView) findViewById(R.id.textStatusDV2);

        txtCN = (TextView) findViewById(R.id.textLabelConnect);

        //VOICE CONTROL
        imgMicCtrl = (ImageView) findViewById(R.id.imageMicControl);
        txtMicCtrl = (TextView) findViewById(R.id.textLabelMicControl);

        imgMicCtrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent speachIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                speachIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                speachIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                speachIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speach to control!");

                try {
                    startActivityForResult(speachIntent,REQUEST_CODE_SPEECH_INPUT);
                }

                catch (Exception e){
                    //Toast.makeText(this,"" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    //HAM PUBLISH MQTT
    void pubMQTT(String topicPub, String contentPub){
        String topic = topicPub;
        String payload = contentPub;
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(topic, message);
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
    }

    //VOICE CONTROL

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch (requestCode){
            case REQUEST_CODE_SPEECH_INPUT:{
                if(null != data && resultCode == RESULT_OK){
                    ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    voiceCtrlMessage = matches.get(0);
                    txtMicCtrl.setText(voiceCtrlMessage);

                    if(voiceCtrlMessage.equals("turn on light")) {
                        swtDV1.setChecked(true);
                    }
                    if(voiceCtrlMessage.equals("turn off light")) {
                        swtDV1.setChecked(false);
                    }

                    if(voiceCtrlMessage.equals("turn on water pump")) {
                        swtDV2.setChecked(true);
                    }
                    if(voiceCtrlMessage.equals("turn off water pump")) {
                        swtDV2.setChecked(false);
                    }

                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}