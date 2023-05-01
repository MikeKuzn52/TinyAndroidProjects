package com.mikekuzn.smstest;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

public class Permission {
    private boolean firstAsk = false;
    public static final int PERMISSION_REQUEST_CODE = 0x3296;
    private final Activity activity;
    private final String[] permissions;

    public Permission(Activity activity, String[] permissions) {
        Log.i("MikeKuzn", "Permission Constructor");
        this.activity = activity;
        this.permissions = permissions;
    }

    public boolean ask() {
        if (checkPermission()) {
            return true;
        } else {
            if (!firstAsk) {
                firstAsk = true;
                Log.i("MikeKuzn", "Ask and wait permissions");
                returnPermission();
            }
        }
        return false;
    }
    public boolean checkPermission() {
        boolean res = true;
        for (String curPer : permissions) {
            res &= ContextCompat.checkSelfPermission(activity, curPer) == PackageManager.PERMISSION_GRANTED;
            Log.i("MikeKuzn", "Permissions " + permissions + " = " + res);
        }
        return res;
    }

    private void returnPermission() {
        ActivityCompat.requestPermissions(activity, permissions, PERMISSION_REQUEST_CODE);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Log.i("MikeKuzn", "Permission killed");
    }
}
