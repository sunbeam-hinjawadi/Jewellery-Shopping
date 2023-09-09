package projects.practice.jewelleryappintegrated.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import projects.practice.jewelleryappintegrated.R;
import projects.practice.jewelleryappintegrated.adapters.CategoryAdapter;
import projects.practice.jewelleryappintegrated.adapters.ImageSliderAdapter;
import projects.practice.jewelleryappintegrated.entity.Category;
import projects.practice.jewelleryappintegrated.entity.Customer;
import projects.practice.jewelleryappintegrated.util.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AfterLoginHomeActivity extends AppCompatActivity {
    Toolbar toolbar;
    Customer loggedCustomer;
    RecyclerView recyclerView;

    CategoryAdapter categoryAdapter;
    List<Category> categoryList;

    private ViewPager2 viewPager;
    private ImageSliderAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login_home);


        toolbar = findViewById(R.id.toolbarAfterLogin);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.viewPager);

        List<String> imageUrls = new ArrayList<>();
        imageUrls.add("https://t3.ftcdn.net/jpg/05/20/35/22/240_F_520352262_4ld1sm00t9pIYkJP1uThYHNGPPi3E2vf.jpg");
        imageUrls.add("https://as2.ftcdn.net/v2/jpg/01/26/55/55/1000_F_126555509_DFSRsp2UT1rUFKlbZNcyMBj7f8tussVv.jpg");
        imageUrls.add("https://as1.ftcdn.net/v2/jpg/01/26/55/54/1000_F_126555468_TTkMBBbsK4GfVGwg1uLVeBZ0V1L1KPRd.jpg");
        imageUrls.add("https://as2.ftcdn.net/v2/jpg/01/34/03/49/1000_F_134034978_ZZodn8JdvT6jFV2o7ZzslBCO1jA4JsMi.jpg");
        imageUrls.add("https://as2.ftcdn.net/v2/jpg/05/79/13/69/1000_F_579136942_Bj9ReQ4A7CDNZaWU03vkrHeiHzBeXILe.jpg");
        imageUrls.add("https://as1.ftcdn.net/v2/jpg/05/79/13/70/1000_F_579137034_NUfDZBYXFnANqFATDi89R8ct5hS3ZBTP.jpg");

        adapter = new ImageSliderAdapter(this, imageUrls);
        viewPager.setAdapter(adapter);

        Intent intent = getIntent();
        loggedCustomer = (Customer) intent.getSerializableExtra("loggedCustomer");

        recyclerView = findViewById(R.id.afrecyclerview);
        categoryList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(AfterLoginHomeActivity.this, categoryList ,loggedCustomer);
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AfterLoginHomeActivity.this));
        //fill the category list//
       // getAllCategories();

        // Your additional initialization code here
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllCategories();
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
            Intent intent = new Intent(AfterLoginHomeActivity.this, AboutUsActivity.class);
            intent.putExtra("loggedCustomer",loggedCustomer);
            startActivity(intent);
//            finish();

        } else if (item.getTitle().equals("Orders")) {
            Intent intent = new Intent(AfterLoginHomeActivity.this, OrdersActivity.class);
            intent.putExtra("loggedCustomer",loggedCustomer);
            startActivity(intent);
            //            finish();


        } else if (item.getTitle().equals("View Cart")) {

            Intent intent = new Intent(AfterLoginHomeActivity.this, ViewCartActivity.class);
            intent.putExtra("loggedCustomer",loggedCustomer);
            startActivity(intent);
//                       finish();


        } else if (item.getTitle().equals("Profile")) {
            //profile page
            Intent intent = new Intent(AfterLoginHomeActivity.this, ProfileActivity.class);
            intent.putExtra("loggedCustomer",loggedCustomer);
            startActivity(intent);
            //            finish();


        }else if (item.getTitle().equals("Logout")) {
            getSharedPreferences("jewelleryshop", MODE_PRIVATE).edit()
                    .putBoolean("login_status", false).apply();
            startActivity(new Intent(AfterLoginHomeActivity.this,BeforeLoginHomeActivity.class));
            finish();

        } else if (item.getTitle().equals("Home")) {
            //home
            Intent intent = new Intent(AfterLoginHomeActivity.this, AfterLoginHomeActivity.class);
            startActivity(intent);
           finish();

        }
        return super.onOptionsItemSelected(item);

    }

    private void getAllCategories() {
        categoryList.clear();
        RetrofitClient.getInstance().getApi().getAllCategories().enqueue(new Callback<JsonObject>(){

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().getAsJsonObject().get("status").getAsString().equals("success")){
                    JsonArray jsonArray = response.body().getAsJsonObject().get("data").getAsJsonArray();
                    for (JsonElement element :jsonArray) {
                        Category cat = new Category();
                        cat.setCategory_name(element.getAsJsonObject().get("category_name").getAsString());
                        cat.setCategory_description(element.getAsJsonObject().get("category_description").getAsString());
                        cat.setCategory_id(element.getAsJsonObject().get("category_id").getAsInt());

                        categoryList.add(cat);
                    }
                    categoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(AfterLoginHomeActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.d("mylogs", "onFailure: category fetch");
            }
        });
    }
}