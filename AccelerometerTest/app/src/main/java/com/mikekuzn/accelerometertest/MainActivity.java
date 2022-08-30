package com.mikekuzn.accelerometertest;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    SensorManager sensorManager;
    Sensor sensor;
    boolean isPresent = false;
    TextView X,Y,Z;
    ImageView circle;
    Point size = new Point();
    final float val = 3;
    final int num = 20;
    float[] x = new float[num];
    float[] y = new float[num];
    float[] z = new float[num];

    @SuppressLint("ServiceCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) MainActivity.this.getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        isPresent = sensors.size() > 0;
        if (!isPresent) {
            Toast.makeText(this, "No found sensor", Toast.LENGTH_LONG).show();
            return;
        }
        sensor = sensors.get(0);
        X = findViewById(R.id.X);
        Y = findViewById(R.id.Y);
        Z = findViewById(R.id.Z);
        circle = findViewById(R.id.circle);
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        size.x -= 65;size.y -= 220;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isPresent) {
            sensorManager.unregisterListener(listener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MikeKuzn", "onResume");
        if (isPresent) {
            Log.i("MikeKuzn", "onResume");
            sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    final SensorEventListener listener = new SensorEventListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            x[0] = sensorEvent.values[0];
            y[0] = -sensorEvent.values[1];
            z[0] = sensorEvent.values[2];

            x = filter(x);
            y = filter(y);
            z = filter(z);
            Log.d("MikeKuzn", "N=" + MainActivity.this.x.length + " " + MainActivity.this.x[0] + " " + MainActivity.this.x[1] + " " + MainActivity.this.x[2] + " " + MainActivity.this.x[3] + " " + MainActivity.this.x[4] + " " + MainActivity.this.x[5]);
            X.setText("X=" + x[0]);
            Y.setText("Y=" + y[0]);
            Z.setText("Z=" + z[0]);
            circle.setTranslationX(size.x / val * x[0] / 2 + (size.x >> 1));
            circle.setTranslationY(size.y / val * y[0] / 2 + (size.y >> 1));
            Log.d("MikeKuzn", "x="+size.x / val * x[0] / 2 + (size.x >> 1)+" y="+size.y / val * y[0] / 2 + (size.y >> 1));
        }

        float[] filter(float os[]) {
            os[0] = Math.min(os[0], val);
            os[0] = Math.max(os[0], -val);
           float summa = 0;
           for (int i = os.length - 2; i >= 0; i--) {
                summa += os[i + 1];
                //Log.d("MikeKuzn", "i=" + i +" " + os[i + 1] + "=" + os[i]);
                os[i + 1] = os[i];
            }
            summa += os[0];
            float new_ = summa / (os.length);
            os[0] = new_;
            //Log.d("MikeKuzn", "sum=" + summa + " N=" + os.length + " " + os[0] + " " + os[1] + " " + os[2] + " " + os[3] + " " + os[4] + " " + os[5]);
            return os;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
}