package com.ahmedco.view.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ahmed.R;
import com.ahmedco.model.DataModel;
import com.ahmedco.networking.ItemsViewModel;
import com.ahmedco.view.adapter.ListMenuAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemsViewModel itemsViewModel;
    private ListMenuAdapter listMenuAdapter;
    private ArrayList<DataModel> MenuArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemsViewModel = ViewModelProviders.of(this).get(ItemsViewModel.class);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        // set a LinearLayoutManager with default vertical orientation ...........................
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        final ListMenuAdapter adapter = new ListMenuAdapter(this, itemsViewModel);
        recyclerView.setAdapter(adapter);

        itemsViewModel.loadDataFromApi();

        itemsViewModel.getAllData().observe(this, new Observer<List<DataModel>>() {
            @Override
            public void onChanged(@Nullable List<DataModel> dataModels) {
                adapter.setM(dataModels);
            }
        });

    }
}




