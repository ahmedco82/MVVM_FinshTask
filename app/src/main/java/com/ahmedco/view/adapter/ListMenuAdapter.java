package com.ahmedco.view.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmed.R;
import com.ahmedco.model.DataModel;
import com.ahmedco.networking.ItemsViewModel;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class ListMenuAdapter extends RecyclerView.Adapter<ListMenuAdapter.MyViewHolder>{



    private List<DataModel>Bruegger;
    private  Activity activity;
    private List<DataModel> checkViewSelectedMenu;
    private ItemsViewModel itemsViewModel;
    private final OnItemClicked onItemClicked;
    
     /****
     * Constructor Adapter
     * @param activity
     * @param itemsViewModel
     */

    public ListMenuAdapter(Activity activity , ItemsViewModel itemsViewModel) {
        this.activity = activity;
        this.itemsViewModel = itemsViewModel;
        onItemClicked = (OnItemClicked)activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout .............
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list_item, parent , false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder v = new MyViewHolder(root); // pass the view to View Holder
        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder , final int position) {
        holder.name.setText(Bruegger.get(position).getTitle().toString());
        holder.price.setText(Bruegger.get(position).getPrice());
        Picasso.get().load(String.valueOf(Bruegger.get(position).getThumbnail())).into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                // preventPressMenu(position);
                final DataModel dataModel = Bruegger.get(position);
                if(onItemClicked != null){
                    onItemClicked.onItemClicked(dataModel);
                }
            }
        });
    }

    public void setM(List<DataModel>list){
        checkViewSelectedMenu = itemsViewModel.getMenuPersonSelected().getValue();
        if(checkViewSelectedMenu == null){
            Bruegger = list;
        } else {
            Bruegger = itemsViewModel.getMenuPersonSelected().getValue();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return null != Bruegger ? Bruegger.size() : 0;
        // return Bruegger.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name , price , txt;
        ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);
            price = (TextView) itemView.findViewById(R.id.price);
            img = (ImageView) itemView.findViewById(R.id.iv);
            name = (TextView) itemView.findViewById(R.id.name);
            txt = (TextView) itemView.findViewById(R.id.city);
        }
    }
}

     /*
    public void preventPressMenu(int pos){
      // openDialogWindow(Bruegger.get(pos));
    }
      */
          /*
    private void calculatTotalPrice(List<DataModel> List_) {

        double Total = 0;
        for (int i = 0; i < List_.size(); i++) {
            Total += Double.parseDouble(List_.get(i).getPrice().toString());
        }
        txtTotalPrice.setText("Total price is " + Total + " €");
    }
      */
     /*
    public void openDialogWindow(final DataModel selectedItem) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        //String[] some_array = getResources().getStringArray(R.array.your_string_array)
        alertDialogBuilder.setMessage(R.string.add_to_menu);
        alertDialogBuilder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            // Add items inside menu .
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //menuPerson.add(item);
                itemsViewModel.addItemToSelectMenu(Collections.singletonList(selectedItem));
                Toast.makeText(activity, R.string.repeating_element, Toast.LENGTH_LONG).show();
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialogBuilder.setNeutralButton(R.string.show_menu, new DialogInterface.OnClickListener() {
            // Show menu
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Bruegger = itemsViewModel.getMenuPersonSelected().getValue();

                    if(checkViewSelectedMenu == null) {
                        makeTotalPriceText(activity);
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(activity, R.string.choice_over, Toast.LENGTH_LONG).show();
                    }
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
*/
