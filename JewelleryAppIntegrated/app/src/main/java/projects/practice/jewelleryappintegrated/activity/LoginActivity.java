package projects.practice.jewelleryappintegrated.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import projects.practice.jewelleryappintegrated.R;
import projects.practice.jewelleryappintegrated.entity.Customer;
import projects.practice.jewelleryappintegrated.util.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText editEmail;
    private EditText editPassword;
    private Button btnLogin;
    private Button btnRegister;
    CheckBox checkBoxRememberMe;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        checkBoxRememberMe = findViewById(R.id.checkBoxRememberMe);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the registration activity
                Intent registrationIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(registrationIntent);
            }
        });

        //

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Customer customer = new Customer();
                customer.setEmail(editEmail.getText().toString());
                customer.setPassword(editPassword.getText().toString());

//                if (checkBoxRememberMe.isChecked())


                RetrofitClient.getInstance().getApi().loginCustomer(customer).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonArray array = response.body().getAsJsonObject().get("data").getAsJsonArray();

                        if (array.size() != 0) {

                            JsonObject object = array.get(0).getAsJsonObject();
                            //Log.e("custo",object.toString() );
                            getSharedPreferences("projectdemo", MODE_PRIVATE).edit()
                                    .putInt("id", object.get("customer_id").getAsInt()).apply();

                            customer.setCustomer_id(object.get("customer_id").getAsInt());
                            customer.setFirstName(object.get("firstName").getAsString());
                            customer.setLastName(object.get("lastName").getAsString());
                            customer.setMobileNo(object.get("mobileNo").getAsString());
                            customer.setGender(object.get("gender").getAsString());
                            customer.setProfileImage(object.get("profileImage").getAsString());
                            customer.setCreatedDate(object.get("createdDate").getAsString());
                            Intent tempIntent = new Intent(LoginActivity.this, AfterLoginHomeActivity.class);
                            tempIntent.putExtra("loggedCustomer",customer);
                            if(checkBoxRememberMe.isChecked()){
                                getSharedPreferences("jewelleryshop", MODE_PRIVATE).edit().putBoolean("login_status", true).apply();
                                getSharedPreferences("jewelleryshop", MODE_PRIVATE).edit().putInt("cid", customer.getCustomer_id()).apply();
                            }
                            startActivity(tempIntent);
                            finish();
                        } else
                            Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.e("done",t.toString());
                        Toast.makeText(LoginActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        //

    }
}