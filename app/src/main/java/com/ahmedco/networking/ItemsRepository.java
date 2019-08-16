package com.ahmedco.networking;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;


import com.ahmedco.model.DataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ItemsRepository extends ViewModel{

    private static ItemsRepository newRepository;

    private static MutableLiveData<List<DataModel>>allData = new MutableLiveData<>();
    public static  boolean CheckViewMenu = false;

    public static ItemsRepository getInstance(){
        if (newRepository == null){
            newRepository = new ItemsRepository();
        }
        return newRepository;
    }

    public MutableLiveData<List<DataModel>>getAllData(){
        return allData;
    }

    public void setMenu(List<DataModel>new_items) {
        CheckViewMenu = true;
        allData.setValue(new_items);
    }

    public ItemsRepository(){
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<String> call = api.getString();
        getData(call);
       // Log.i("Trace","Okkk");
    }
    private void getData(Call<String>call){
        call.enqueue(new Callback<String>(){
            @Override
            public void onResponse(Call<String>call,Response<String>response) {
                if(response.isSuccessful()){
                  String jsonresponse = response.body().toString();
                   writeGson(jsonresponse);
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                allData.setValue(null);
            }
        });
    }
    private void writeGson(String response){
        JSONArray json = null;
        ArrayList<DataModel> retroModelArrayList = new ArrayList<>();
        try{
            json = new JSONArray(response);
            for(int i=0; i<json.length(); i++){
                JSONObject e = json.getJSONObject(i);
                String n =   e.getString("title");
                DataModel retroModel = new DataModel();
                retroModel.setRef(e.getString("ref"));
                retroModel.setDescription(e.getString("description"));
                retroModel.setTitle(e.getString("title"));
                retroModel.setPrice(e.getString("price"));
                retroModel.setThumbnail(e.getString("thumbnail"));
                retroModelArrayList.add(retroModel);
            }
            allData.setValue(retroModelArrayList);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}
