package com.mikekuzn.recyclerdemo;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import com.mikekuzn.recyclerdemo.databinding.ActivityMainBinding;
import android.view.ViewGroup;
import android.view.ViewParent;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    String[] items = new String[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        for (int i = 0; i < items.length; i++) {
            items[i] = "Item " + i;
        }
        Adapter adapter = new CustomAdapter(items);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ViewParent parent = binding.recyclerView.getParent();
        androidx.recyclerview.widget.RecyclerView newRecycler = null;
        if (parent instanceof ViewGroup) {
            ViewGroup group = (ViewGroup)parent;
            try {
                newRecycler = binding.recyclerView
                        .getClass()
                        .getDeclaredConstructor(Context.class)
                        .newInstance((Context) this);
                Log.d("RecyclerDemo", "newInstance=" + newRecycler + " base=" + binding.recyclerView);
            } catch (Exception e) {Log.e("RecyclerDemo", "newInstance Exception " + e);}
            if (newRecycler != null) {
                newRecycler.setAdapter(new CustomAdapter(items));
                newRecycler.setLayoutManager(new LinearLayoutManager(this));
                newRecycler.setLayoutParams(binding.recyclerView.getLayoutParams());
                group.addView(newRecycler);
            }
        }
    }
}