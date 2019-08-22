package com.ahmedco.view.adapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.ahmedco.model.DataModel;
import com.ahmedco.networking.ItemsViewModel;
import com.ahmed.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class ListMenuAdapter extends RecyclerView.Adapter<ListMenuAdapter.MyViewHolder> {

    private Context context;
    private List<DataModel> person;
    private ArrayList<DataModel> menuPerson = new ArrayList<>();
    private static Activity activity;
    private TextView txtTotalPrice;

      /****
     * Constructor Adapter
     * @param activity
     * @param person
     */
    public ListMenuAdapter(Activity activity, List<DataModel> person) {
        // this.person =  person;
        this.context = activity;
        this.activity = activity;
        this.person = ItemsViewModel.getInstance().getAllData().getValue();
    }

     /***
     * initialization text from Mainactivity to put in it a price value.
     * @param activity is object from MainActivity.
     */

    public void makeTotalPriceText(Activity activity) {
        txtTotalPrice = (TextView)activity.findViewById(R.id.total_txt);
        calculatTotalPrice(person);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout .............
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder v = new MyViewHolder(root); // pass the view to View Holder
        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.name.setText(person.get(position).getTitle().toString());
        holder.price.setText(person.get(position).getPrice());
        Picasso.get().load(String.valueOf(person.get(position).getThumbnail())).into(holder.img1);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View view) {
               preventPressMenu(position);
            }
        });
     }

     /**
     * These function use to prevent add new items to menu
     * @param pos is index item
     */

    public void preventPressMenu(int pos) {
        if (ItemsViewModel.getInstance().CheckViewMenu != true){
            openDialogWindow(person.get(pos));
        } else {
            Toast.makeText(context, R.string.choice_over, Toast.LENGTH_LONG).show();
        }
    }

    public void openDialogWindow(final DataModel p){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        //String[] some_array = getResources().getStringArray(R.array.your_string_array)
        alertDialogBuilder.setMessage(R.string.add_to_menu);
        alertDialogBuilder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            // Add items inside menu
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
                menuPerson.add(p);
                Toast.makeText(context,R.string.repeating_element, Toast.LENGTH_LONG).show();
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i){

            }
        });
        alertDialogBuilder.setNeutralButton(R.string.show_menu, new DialogInterface.OnClickListener(){
          // Show menu
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                person = menuPerson;
                ItemsViewModel.getInstance().setMenu(person);
                makeTotalPriceText(activity);
                notifyDataSetChanged();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

     @Override
     public int getItemCount(){
        return person.size();
     }

     /**
     * @param List_ used to get price from each item inside List.
     * @return Total price
     */

     private void calculatTotalPrice(List<DataModel>List_){
        double Total =0;
        for (int i=0; i<List_.size();  i++){
            Total += Double.parseDouble(List_.get(i).getPrice().toString());
        }
        txtTotalPrice.setText("Total price is "+ Total+ " €");
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, txt;
        ImageView img1;

        public MyViewHolder(View itemView){
            super(itemView);
            price = (TextView) itemView.findViewById(R.id.price);
            img1 = (ImageView) itemView.findViewById(R.id.iv);
            name = (TextView) itemView.findViewById(R.id.name);
            txt = (TextView) itemView.findViewById(R.id.city);
        }
    }
}