package projects.practice.jewelleryappintegrated.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import projects.practice.jewelleryappintegrated.R;
import projects.practice.jewelleryappintegrated.adapters.CartProductAdapter;
import projects.practice.jewelleryappintegrated.entity.CartProduct;
import projects.practice.jewelleryappintegrated.entity.Category;
import projects.practice.jewelleryappintegrated.entity.Customer;
import projects.practice.jewelleryappintegrated.util.RefreshComponent;
import projects.practice.jewelleryappintegrated.util.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewCartActivity extends AppCompatActivity  implements RefreshComponent {
    Toolbar toolbar;
    Customer loggedCustomer;
    RecyclerView recyclerView;
    List<CartProduct> cartproducts;
    CartProductAdapter cartProductAdapter;
    TextView totalprice;
    Button checkout ,closecart ,clrearcart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);
        toolbar = findViewById(R.id.toolbarAfterLogin);
        setSupportActionBar(toolbar);
        totalprice =findViewById(R.id.totalprice);
        checkout =findViewById(R.id.checkout);
        closecart =findViewById(R.id.closecart);
        clrearcart =findViewById(R.id.clrearcart);
        loggedCustomer = (Customer) getIntent().getSerializableExtra("loggedCustomer");
        recyclerView = findViewById(R.id.cartrecyclerview);
        cartproducts = new ArrayList<>();
        cartProductAdapter = new CartProductAdapter(ViewCartActivity.this, cartproducts , this ,loggedCustomer.getCustomer_id() ,loggedCustomer);
        recyclerView.setAdapter(cartProductAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewCartActivity.this));

        closecart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ViewCartActivity.this,AfterLoginHomeActivity.class);
                intent.putExtra("loggedCustomer",loggedCustomer);
                startActivity(intent);
                finish();
            }
        });

        clrearcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitClient.getInstance().getApi().deleteCustomerCart(loggedCustomer.getCustomer_id()).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        getAllCartProducts(loggedCustomer.getCustomer_id());
                        Intent intent =new Intent(ViewCartActivity.this,AfterLoginHomeActivity.class);
                        intent.putExtra("loggedCustomer",loggedCustomer);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start check out activity
                int chckamt = Integer.valueOf(totalprice.getText().toString());
                if(chckamt > 0){
                    Intent intent = new Intent(ViewCartActivity.this, CheckOutActivity.class);
                    intent.putExtra("loggedCustomer",loggedCustomer);
                    intent.putExtra("chckamt",chckamt);
                    intent.putExtra("cartproducts", (Serializable) cartproducts);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(ViewCartActivity.this, "add products into your cart", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
//        loggedCustomer = (customerPojo) getIntent().getSerializableExtra("loggedCustomer");
        getAllCartProducts(loggedCustomer.getCustomer_id());
        cartTotalPrice();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.afterloginmenu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if(item.getTitle().equals("About Us")){
            Intent intent = new Intent(ViewCartActivity.this, AboutUsActivity.class);
            intent.putExtra("loggedCustomer",loggedCustomer);
            startActivity(intent);
//            finish();

        } else if (item.getTitle().equals("Orders")) {
            Toast.makeText(ViewCartActivity.this, "first login", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ViewCartActivity.this, OrdersActivity.class);
            intent.putExtra("loggedCustomer",loggedCustomer);
            startActivity(intent);
            //            finish();


        } else if (item.getTitle().equals("View Cart")) {

            Intent intent = new Intent(ViewCartActivity.this, ViewCartActivity.class);
            intent.putExtra("loggedCustomer",loggedCustomer);
            startActivity(intent);
            //            finish();


        } else if (item.getTitle().equals("Profile")) {
            //profile page
            Intent intent = new Intent(ViewCartActivity.this, ProfileActivity.class);
            intent.putExtra("loggedCustomer",loggedCustomer);
            startActivity(intent);
            //            finish();


        }else if (item.getTitle().equals("Logout")) {
            Toast.makeText(ViewCartActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
            getSharedPreferences("jewelleryshop", MODE_PRIVATE).edit()
                    .putBoolean("login_status", false).apply();
            startActivity(new Intent(ViewCartActivity.this,BeforeLoginHomeActivity.class));
//            finish();

        } else if (item.getTitle().equals("Home")) {
            //home
            Intent intent = new Intent(ViewCartActivity.this, AfterLoginHomeActivity.class);
            startActivity(intent);
//            finish();

        }
        return super.onOptionsItemSelected(item);

    }

    private void getAllCartProducts(int customer_id) {
        cartproducts.clear();
        RetrofitClient.getInstance().getApi().getAllCartProductsByCid(loggedCustomer.getCustomer_id()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().getAsJsonObject().get("status").getAsString().equals("success")){
                    JsonArray jsonArray = response.body().getAsJsonObject().get("data").getAsJsonArray();
                    for (JsonElement element :jsonArray) {
                        CartProduct myproduct = new CartProduct();
                        Log.d("mylogs", "onResponse: "+element.getAsJsonObject().get("jewellery_name").getAsString());
                        myproduct.setJewellery_name(element.getAsJsonObject().get("jewellery_name").getAsString());
                        myproduct.setCustomer_id(loggedCustomer.getCustomer_id());
                        myproduct.setJewellery_id(element.getAsJsonObject().get("jewellery_id").getAsInt());
                        myproduct.setPrice(element.getAsJsonObject().get("price").getAsDouble());
                        myproduct.setQty(element.getAsJsonObject().get("qty").getAsInt());
                        //get image and add to src
                        //code here//
                        cartproducts.add(myproduct);
                    }
                    cartProductAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("mylogs", "onFailure: ");
            }
        });


    }

    public  void cartTotalPrice(){
        RetrofitClient.getInstance().getApi().totalcartprice(loggedCustomer.getCustomer_id()).enqueue(new Callback<JsonObject>(){
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().getAsJsonObject().get("status").getAsString().equals("success")){
                    JsonArray jsonArray = response.body().getAsJsonObject().get("data").getAsJsonArray();
                    if(jsonArray.size()>0){
                        totalprice.setText(jsonArray.get(0).getAsJsonObject().get("sum").getAsString());
                    }else {
                        totalprice.setText("0000000");
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
            }
        });

    }
    @Override
    public void referesh1(int cid) {
        getAllCartProducts(cid);
    }



    @Override
    public void refereshTextView() {
        cartTotalPrice();
    }


}