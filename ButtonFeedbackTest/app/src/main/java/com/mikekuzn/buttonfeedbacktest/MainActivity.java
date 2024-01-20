package com.mikekuzn.buttonfeedbacktest;

import android.annotation.SuppressLint;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    final int TEST_STREAM_TYPE = AudioManager.STREAM_MUSIC;
    final int TEST_SR = 22050; //This is from an example I found online.
    final int TEST_CONF = AudioFormat.CHANNEL_OUT_MONO;
    final int TEST_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    final int TEST_MODE = AudioTrack.MODE_STATIC; //I need static mode.
    AudioTrack track = null;
    private static final int multiplier =  0x3000 / 127;

    GraphView graphView;
    Boolean pressed = false;
    private static final int BUTTONS = 10;
    private static final short[][] data = new short[BUTTONS][];//[22050/10*2]; //200 milli seconds long

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        graphView = findViewById(R.id.graphView);
        // **** Adding buttons *****
        GridLayout buttonsLayout = findViewById(R.id.buttonsLayout);
        for (int i = 0; i < BUTTONS; i++) {
            Button newButton = new Button(this);
            newButton.setText("Run" + (i+1));
            newButton.setTag(i);
            buttonsLayout.addView(newButton);
            newButton.setOnClickListener(v -> {
                pressed = false;
                Button button = (Button)v;
                int num = (int)button.getTag();
                //play(data[num]);
            });
            newButton.setOnTouchListener((v, event) -> {
                if (!pressed) {
                    pressed = true;
                    Button button = (Button) v;
                    int num = (int)button.getTag();
                    play(data[num]);
                }
                return false;
            });
        }
        // *****
        data[0] = new short[377];
        data[1] = new short[377];
        byte k1 = 0;
        for (int i = 0; i < data[0].length; ++i) {
            data[0][i] = (short)( k1 * multiplier);
            data[1][i] = (short)(-k1 * multiplier);
            if ((i < 227 || i > 248 || i == 230) && i > 100) k1++;
        }
        // 22050 - 1 sec
        // 1102 - 140 Hz 7 Periods (22050/140 * 7)
        // 617 - 250 Hz 7 Periods (22050/250 * 7)
        // 515 - 300 Hz 7 Periods (22050/300 * 7)
        data[2] = makeButtonSin(1102, 40);
        data[3] = makeButtonSin(1102, 25);
        data[4] = makeButtonSin(617, 40);
        data[5] = makeButtonSin(617, 25);
        data[6] = makeButtonSin(515,40);
        data[7] = makeButtonSin(515,25);
        data[8] = makeButtonSinK(515,40);
        data[9] = makeButtonSinK(515,25);
        for (int i = 10; i < BUTTONS; ++i) {
            data[i] = new short[22050 * 2]; //2 seconds long];
            for (int j = 0; j < data[i].length; ++j) {
                data[i][j] = (short) (new Random().nextInt()); //Create some random noise to listen to.
            }
        }
    }

    short[] makeButtonSin(int size, int steepness) {
        short[] data = new short[size];
        float divider = (float)(0.0225 * size);
        float k = (float)(steepness * 1.5);
        float kInc = (float)((127-k)/(size/8.5));
        float kDec1 = (float)((127-steepness)/(size/3.4-size/8.5));
        float kDec2 = (float)(steepness/(size - size/3.4));
        for (int i = 0; i < data.length; ++i) {
            data[i] = (short) (Math.sin(i/divider) * k * multiplier);
            if (i < size/8.5) k+=kInc; else {if (i < size/3.4)k-=kDec1;else k-=kDec2;}
        }
        return data;
    }
    short[] makeButtonSinK(int size, int steepness) {
        short[] data = new short[size];
        float divider = (float)(0.0225 * size);
        float k = (float)(steepness * 1.5);
        float kInc = (float)((127-k)/(size/8.5));
        float kDec1 = (float)((127-steepness)/(size/3.4-size/8.5));
        float kDec2 = (float)(steepness/(size - size/3.4));
        for (int i = 0; i < data.length; ++i) {
            data[i] = (short) (/*Math.sin(i/divider) * */k * multiplier * 2 - 0x3000);
            if (i < size/8.5) k+=kInc; else {if (i < size/3.4)k-=kDec1;else k-=kDec2;}
        }
        return data;
    }

    void play(short[] data){
        if (data == null) return;
        if (data.length == 0) return;
        graphView.SetValues(data);

        try {
            if (track != null) {
                track.stop();
                track.release();
                track = null;
            }
            track = new AudioTrack(TEST_STREAM_TYPE, TEST_SR, TEST_CONF, TEST_FORMAT, data.length * 2, TEST_MODE);
            track.write(data, 0, data.length);
            track.play();
        } catch (Exception e) {

        }
    }

    boolean Visible = false;
    public void onGraphVisible(View view) {
        Button GraphVisibleBtn = findViewById(R.id.GraphVisibleBtn);
        ImageView GraphVisible = findViewById(R.id.GraphVisible);
        Visible=!Visible;
        GraphVisibleBtn.setText(Visible ? "Close graph" : "Show graph");
        GraphVisible.setVisibility(Visible ? View.INVISIBLE : View.VISIBLE);
    }
}