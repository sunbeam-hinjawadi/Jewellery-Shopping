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

public class BeforeLoginHomeActivity extends AppCompatActivity {
    Toolbar toolbar ;
    private  Customer loggedCustomer;
    RecyclerView recyclerView;

    CategoryAdapter categoryAdapter;
    List<Category> categoryList;

    private ViewPager2 viewPager;
    private ImageSliderAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_login_home);
        toolbar = findViewById(R.id.beftoolbar);
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


        //getting logged user - here not needed
       // loggedCustomer = (Customer) getIntent().getSerializableExtra("loggedCustomer");

        recyclerView = findViewById(R.id.befrecyclerView);
        categoryList = new ArrayList<>();
       // getAllCategories();
        categoryAdapter = new CategoryAdapter(BeforeLoginHomeActivity.this, categoryList ,loggedCustomer);
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(BeforeLoginHomeActivity.this));

        //fill the category list//
        // Your additional initialization code here
    }

    @Override
    protected void onResume() {
        super.onResume();
        categoryList.clear();
        getAllCategories();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.beforeloginmenu, menu);
        return true;
    }

    //filling menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if(item.getTitle().equals("About Us")){
            Toast.makeText(BeforeLoginHomeActivity.this, "about us page", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(BeforeLoginHomeActivity.this, AboutUsActivity.class);
            startActivity(intent);
//            finish();

        } else if (item.getTitle().equals("Orders")) {
            Toast.makeText(BeforeLoginHomeActivity.this, "first login", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(BeforeLoginHomeActivity.this, LoginActivity.class);
//            startActivity(intent);
//            finish();


        } else if (item.getTitle().equals("View Cart")) {

            Toast.makeText(BeforeLoginHomeActivity.this, "first login", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(BeforeLoginHomeActivity.this, LoginActivity.class);
//            startActivity(intent);
//            finish();


        } else if (item.getTitle().equals("Profile")) {
            //profile page
            Toast.makeText(BeforeLoginHomeActivity.this, "first login", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(BeforeLoginHomeActivity.this, LoginActivity.class);
//            startActivity(intent);
//            finish();


        }else if (item.getTitle().equals("Login")) {
            startActivity(new Intent(BeforeLoginHomeActivity.this,LoginActivity.class));
//            finish();

        } else if (item.getTitle().equals("Home")) {
            //home
            Intent intent = new Intent(BeforeLoginHomeActivity.this, BeforeLoginHomeActivity.class);
            startActivity(intent);
//            finish();

        }
        return super.onOptionsItemSelected(item);

    }

    //

    //getting categories from server
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
                        //set image
                        Log.d("mylogs", "onResponse: before login " +cat);

                        categoryList.add(cat);
                        Log.d("mylogs", "onResponse: before login "+categoryList.size() );
                    }
                    categoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(BeforeLoginHomeActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}