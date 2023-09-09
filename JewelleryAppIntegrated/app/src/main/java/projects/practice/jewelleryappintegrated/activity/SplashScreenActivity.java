package projects.practice.jewelleryappintegrated.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import projects.practice.jewelleryappintegrated.R;
import projects.practice.jewelleryappintegrated.entity.Customer;
import projects.practice.jewelleryappintegrated.util.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {

    private final long splashDuration = 2000; // in milliseconds
    private Customer loggedCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //code here
//                Intent intent = new Intent(SplashScreenActivity.this, BeforeLoginHomeActivity.class);
//                startActivity(intent);
//                finish();
                //
                try{
                    Thread.sleep(2000);
                    SharedPreferences sp = getSharedPreferences("jewelleryshop",MODE_PRIVATE);
                    boolean status = sp.getBoolean("login_status",false);

                    int cid = sp.getInt("cid",-1);
                    if(status && cid>0){
                        loggedCustomer = new Customer();
                        loggedCustomer.setCustomer_id(cid);

                        //

                        RetrofitClient.getInstance().getApi().loggedCustomer(loggedCustomer).enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                JsonArray array = response.body().getAsJsonObject().get("data").getAsJsonArray();

                                if (array.size() != 0) {

                                    JsonObject object = array.get(0).getAsJsonObject();
                                    //Log.e("custo",object.toString() );
                                    getSharedPreferences("jewelleryshop", MODE_PRIVATE).edit()
                                            .putInt("cid", object.get("customer_id").getAsInt()).apply();

                                    loggedCustomer.setEmail(object.get("email").getAsString());
                                    loggedCustomer.setPassword(object.get("password").getAsString());
                                    loggedCustomer.setFirstName(object.get("firstName").getAsString());
                                    loggedCustomer.setLastName(object.get("lastName").getAsString());
                                    loggedCustomer.setMobileNo(object.get("mobileNo").getAsString());
                                    loggedCustomer.setGender(object.get("gender").getAsString());
                                    loggedCustomer.setProfileImage(object.get("profileImage").getAsString());
                                    loggedCustomer.setCreatedDate(object.get("createdDate").getAsString());
                                    Intent tempIntent = new Intent(SplashScreenActivity.this, AfterLoginHomeActivity.class);
                                    tempIntent.putExtra("loggedCustomer",loggedCustomer);
                                    startActivity(tempIntent);
                                    finish();
                                } else
                                    Toast.makeText(SplashScreenActivity.this, "Invalid Credentials : will never occur", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                Log.e("done",t.toString());
                                Toast.makeText(SplashScreenActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            }
                        });

                        //

                    }else {
                        startActivity(new Intent(SplashScreenActivity.this , BeforeLoginHomeActivity.class));
                    }
                    finish();

                } catch (InterruptedException e){
                    throw new RuntimeException(e);
                }
            }
        }, splashDuration);
    }
}
