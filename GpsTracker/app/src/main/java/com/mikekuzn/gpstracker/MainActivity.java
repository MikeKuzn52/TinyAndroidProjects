package com.mikekuzn.gpstracker;

import android.Manifest;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvDistance, tvVelocity, tvLatitude, tvLongitude;
    ProgressBar progressBar;
    private Permission permission;
    ULogic uLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvDistance = findViewById(R.id.distance);
        tvVelocity = findViewById(R.id.velocity);
        tvLatitude = findViewById(R.id.latitude);
        tvLongitude = findViewById(R.id.longitude);
        progressBar = findViewById(R.id.progressBar);
        uLogic = ULogic.getInstance(this, new UiInterface.CallBack() {
            @Override
            public void proc(UiInterface.UiData uiData) {
                tvDistance.setText(String.valueOf(uiData.distance));
                tvVelocity.setText(String.valueOf(uiData.velocity));
                tvLatitude.setText(String.valueOf(uiData.latitude));
                tvLongitude.setText(String.valueOf(uiData.longitude));
                progressBar.setMax(uiData.distanceTo);
                progressBar.setProgress(uiData.distance);
            }
        });
        permission = new Permission(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION});
        if (permission.ask()) {
            uLogic.init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i("MikeKuzn", "onRequestPermissionsResult");
        boolean Ok = true;
        if (requestCode == Permission.PERMISSION_REQUEST_CODE) {
            if (permission.checkPermission()) {
                uLogic.init();
            }
        }
    }

    public void showAlertDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alertTitle);
        ConstraintLayout cl = (ConstraintLayout) getLayoutInflater().inflate(R.layout.distance_dialog, null);
        builder.setPositiveButton(R.string.alertButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText sD = ((AlertDialog) dialogInterface).findViewById(R.id.setDistance);
                if (sD != null && !sD.getText().toString().equals("")) {
                    String s = sD.getText().toString();
                    uLogic.setDistanceTo(Integer.parseInt(s));
                }
            }
        });
        builder.setView(cl);
        builder.show();
    }
}