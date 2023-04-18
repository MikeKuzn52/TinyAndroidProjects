package com.mikekuzn.recyclerdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import com.mikekuzn.recyclerdemo.databinding.ActivityMainBinding;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NotifyDataSetChanged")
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    List<String> itemsList = new ArrayList<>();
    boolean startEndItem = false;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setItems(100);
        adapter = new CustomAdapter(itemsList);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AdaptRecycler.Invoke(binding.recyclerView);
    }

    public void clickAddItem(View view) {
        itemsList.add(startEndItem ? 0 : itemsList.size(), "NewItem");
        adapter.notifyDataSetChanged();
        startEndItem = !startEndItem;
    }

    public void clickDelItem(View view) {
        startEndItem = !startEndItem;
        if (itemsList.size() != 0) {
            itemsList.remove(startEndItem ? 0 : itemsList.size() - 1);
            adapter.notifyDataSetChanged();
        }
    }
    public void clickSet10Items(View view) {
        setItems(10);
        adapter.notifyDataSetChanged();
    }
    public void clickSet100Items(View view) {
        setItems(100);
        adapter.notifyDataSetChanged();
    }

    void setItems(int size) {
        itemsList.clear();
        for (int i = 0; i < size; i++) {
            itemsList.add("Item " + i);
        }
    }
}