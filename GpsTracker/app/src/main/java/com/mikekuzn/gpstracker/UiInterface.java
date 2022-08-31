package com.mikekuzn.gpstracker;

public interface UiInterface {
    class UiData{
        float velocity = 0f;
        int distance = 0;
        double latitude, longitude;
        int distanceTo;
    }
    interface CallBack {
        void proc(UiData uiData);
    }
    void init();
    void setDistanceTo(int distanceTo);
}
