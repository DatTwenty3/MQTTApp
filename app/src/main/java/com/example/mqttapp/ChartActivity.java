package com.example.mqttapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Random;

public class ChartActivity extends AppCompatActivity {

    private LineGraphSeries<DataPoint> series;
    private static final Random RANDOM = new Random();
    private int lastX = 0;

    //MQTT
    MqttAndroidClient client;

    int Temp;
    int AirHumi;
    int SoilMois;

    String dataSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        //GRAPH
        GraphView graph = (GraphView) findViewById(R.id.graph);
        //DATA
        series = new LineGraphSeries<DataPoint>();
        graph.addSeries(series);
        //CUSTOMIZE A LITTLE BIT VIEWPOINT
        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(0);
        viewport.setMaxY(100);
        viewport.setScrollable(true);
        viewport.scrollToEnd();

        //MQTT

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

                if(topic.equals("Temp")) {
                    dataSensor = message + "";
                    Temp = Integer.valueOf(dataSensor);
                }

                if(topic.equals("AirHumi")) {
                    AirHumi = Integer.valueOf(dataSensor);
                }

                if(topic.equals("SoilMois")) {
                    SoilMois = Integer.valueOf(dataSensor);
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

                    ////////////////////////////SUBSCRICE TOPIC
                    String topicTem = "Temp";
                    String topicAH = "AirHumi";
                    String topicSM = "SoilMois";

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

                    ///////////////////////////////////////////////////////////////////////////
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d("MQTT", "onFailure");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <100; i++){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addEntry();
                        }
                    });

                    try {
                        Thread.sleep(10000);
                    }catch (InterruptedException e){

                    }

                    }
            }
        }).start();
    }

    private void addEntry(){
        series.appendData(new DataPoint(lastX++, Temp), true, 10);

    }
}