package projects.practice.jewelleryappintegrated.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import projects.practice.jewelleryappintegrated.R;
import projects.practice.jewelleryappintegrated.adapters.CategoryAdapter;
import projects.practice.jewelleryappintegrated.adapters.JewelleryAdapter;
import projects.practice.jewelleryappintegrated.entity.Category;
import projects.practice.jewelleryappintegrated.entity.Customer;
import projects.practice.jewelleryappintegrated.entity.JewelleryProduct;
import projects.practice.jewelleryappintegrated.util.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends AppCompatActivity {
    Toolbar toolbar;
    Customer loggedCustomer;
    RecyclerView recyclerView;
    JewelleryAdapter jewelleryAdapter;
    List<JewelleryProduct> jewelleryList;
    Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        toolbar = findViewById(R.id.prodtoolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        loggedCustomer = (Customer) intent.getSerializableExtra("loggedCustomer");//only after login
        if(loggedCustomer==null){
            Log.d("mylogs", "onCreate: products activity nullll");
        }else {
            Log.d("mylogs", "onCreate: products activity "+loggedCustomer);
        }
        category = (Category) intent.getSerializableExtra("category"); //only after login

        recyclerView = findViewById(R.id.prodrecyclerView);
        jewelleryList = new ArrayList<>();
        jewelleryAdapter = new JewelleryAdapter(ProductsActivity.this, jewelleryList , loggedCustomer);
        recyclerView.setAdapter(jewelleryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductsActivity.this));
        //fill the Jewellery List

    }

    @Override
    protected void onResume() {
        super.onResume();
//        Intent intent = getIntent();
//        loggedCustomer = (Customer) intent.getSerializableExtra("loggedCustomer"); //only after login
//        category = (Category) intent.getSerializableExtra("category");
//        Log.d("mylogs", "onResume: "+category.getCategory_id());
        jewelleryList.clear();
        getAllJwelleryProducts(category);

    }

    private void getAllJwelleryProducts(Category category) {
        jewelleryList.clear();
        Log.d("mylogs", "getAllJwelleryProducts: " +category);
        RetrofitClient.getInstance().getApi().getAllJewelleryProducts(category.getCategory_id()).enqueue(new Callback<JsonObject>(){

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().getAsJsonObject().get("status").getAsString().equals("success")){
                    JsonArray jsonArray = response.body().getAsJsonObject().get("data").getAsJsonArray();
                    Log.d("mylogs", "onResponse: products fetching");
                    for (JsonElement element :jsonArray) {
                        JewelleryProduct myproduct = new JewelleryProduct();
//                        myproduct.setJewellery_id(element.getAsJsonObject().get("jewellery_id").getAsInt());
                        Log.d("mylogs", "onResponse: "+myproduct.getJewellery_id());
                        myproduct.setJewellery_id(element.getAsJsonObject().get("jewellery_id").getAsInt());
                        myproduct.setJewellery_name(element.getAsJsonObject().get("jewellery_name").getAsString());
                        myproduct.setJewellery_description(element.getAsJsonObject().get("jewellery_description").getAsString());
                        myproduct.setJewellery_image(element.getAsJsonObject().get("jewellery_image").getAsString());
                        myproduct.setJewellery_price(element.getAsJsonObject().get("jewellery_price").getAsDouble());
                        myproduct.setStock_qty(element.getAsJsonObject().get("stock_qty").getAsInt());
                        jewelleryList.add(myproduct);
//                        Log.d("mylogs", "onResponse: "+myproduct.getJewellery_id());
//                        Log.d("mylogs", "onResponse: "+ myproduct);
                    }
                    Log.d("mylogs", "onResponse: "+jewelleryList.size());
                    jewelleryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("mylogs", "onFailure: products fetching");
                Toast.makeText(ProductsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}