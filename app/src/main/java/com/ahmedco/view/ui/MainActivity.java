package com.ahmedco.view.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmed.R;
import com.ahmedco.model.DataModel;
import com.ahmedco.networking.ItemsViewModel;
import com.ahmedco.view.adapter.ListMenuAdapter;
import com.ahmedco.view.adapter.OnItemClicked;

import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity implements OnItemClicked {

    private RecyclerView recyclerView;
    private ItemsViewModel itemsViewModel;
    private List<DataModel> brueggerSelected;
    private TextView txtTotalPrice;
    private ListMenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemsViewModel = ViewModelProviders.of(this).get(ItemsViewModel.class);
        txtTotalPrice = (TextView)this.findViewById(R.id.total_txt);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        // set a LinearLayoutManager with default vertical orientation ...........................
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ListMenuAdapter(this, itemsViewModel);
        recyclerView.setAdapter(adapter);
        itemsViewModel.loadDataFromApi();
        itemsViewModel.getAllData().observe(this, new Observer<List<DataModel>>() {
            @Override
            public void onChanged(@Nullable List<DataModel>dataModels) {
                adapter.setM(dataModels);
            }
        });
    }
    @Override
    public void onItemClicked(DataModel currentBruegger){
        String getStateText = String.valueOf(txtTotalPrice.getText());
        if( getStateText.contains("Menu")){
            openDialogWindow(currentBruegger);
        }else{
            Toast.makeText(MainActivity.this, R.string.choice_over, Toast.LENGTH_LONG).show();
        }
        //Log.v("pri","-- "+currentBruegger.getPrice());
    }

    public void openDialogWindow(final DataModel selectedItem) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        //String[] some_array = getResources().getStringArray(R.array.your_string_array)
        alertDialogBuilder.setMessage(R.string.add_to_menu);
        alertDialogBuilder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            // Add items inside menu .
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //menuPerson.add(item);
                itemsViewModel.addItemToSelectMenu(Collections.singletonList(selectedItem));
                Toast.makeText(MainActivity.this, R.string.repeating_element, Toast.LENGTH_LONG).show();
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialogBuilder.setNeutralButton(R.string.show_menu, new DialogInterface.OnClickListener(){
            // Show menu
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                brueggerSelected = itemsViewModel.getMenuPersonSelected().getValue();
                if (brueggerSelected != null){
                    makeTotalPriceText(MainActivity.this);
                    adapter.setM(brueggerSelected);
                } else {
                    Toast.makeText(MainActivity.this, R.string.not_choice, Toast.LENGTH_LONG).show();
                }
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void makeTotalPriceText(Activity activity) {
        calculatTotalPrice(brueggerSelected);
    }

    private void calculatTotalPrice(List<DataModel> List_) {
        double Total = 0;
        for (int i = 0; i < List_.size(); i++) {
            Total += Double.parseDouble(List_.get(i).getPrice().toString());
        }
        txtTotalPrice.setText("Total price is " + Total + " €");
    }

}
