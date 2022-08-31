package com.mikekuzn.gpstracker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

public class ULogic implements UiInterface {

    private static final ULogic inst = new ULogic();
    private Activity activity;
    private CallBack callBack;
    UiData uiData = new UiData();
    private Location lastLocation;

    private ULogic(){}

    static ULogic getInstance(Activity activity, CallBack callBack) {
        inst.callBack = callBack;
        inst.activity = activity;
        return inst;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void init() {
        MyLocListener myLocListener = new MyLocListener();
        myLocListener.setLocListenerInterface(new LocListenerInterface() {
            @Override
            public void OnLocationChanged(Location location) {
                if (location.hasSpeed() && lastLocation != null) {
                    uiData.distance += lastLocation.distanceTo(location);
                    uiData.velocity = location.getSpeed();
                    uiData.latitude = location.getLatitude();
                    uiData.longitude = location.getLongitude();
                    callBack.proc(uiData);
                }
                lastLocation = location;
            }
        });
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2, 2, myLocListener);
        callBack.proc(uiData);
    }

    @Override
    public void setDistanceTo(int distanceTo) {
        uiData.distanceTo = distanceTo;
        callBack.proc(uiData);
    }
}
