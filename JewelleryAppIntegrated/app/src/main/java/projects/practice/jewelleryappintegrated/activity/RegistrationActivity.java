package projects.practice.jewelleryappintegrated.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import projects.practice.jewelleryappintegrated.R;
import projects.practice.jewelleryappintegrated.entity.Customer;
import projects.practice.jewelleryappintegrated.util.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    private EditText editFirstName;
    private EditText editLastName;

    private EditText editEmail;
    private EditText editContact;
    private EditText editPassword;
    private EditText editConfirmPassword;
    private Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        editFirstName = findViewById(R.id.firstName);
        editLastName = findViewById(R.id.lastName);
        editEmail = findViewById(R.id.editEmail);
        editContact = findViewById(R.id.editContact);
        editPassword = findViewById(R.id.editPassword);
        editConfirmPassword = findViewById(R.id.editConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implement your registration logic here
                // For now, let's assume registration is successful
                // After registration, navigate back to MainActivity
                Customer customer = validateCustomer();
                if(customer!=null){
                    RetrofitClient.getInstance().getApi().registerCustomer(customer).enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if(response.body().getAsJsonObject().get("status").getAsString().equals("success"))
                            {

                                Toast.makeText(RegistrationActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent mainIntent = new Intent(RegistrationActivity.this, BeforeLoginHomeActivity.class);
                                startActivity(mainIntent);
                                finish(); // Close the registration activity
                            }
                        }
                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(RegistrationActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });



    }

    private Customer validateCustomer() {
        String password = editPassword.getText().toString();
        String confirmPassword = editConfirmPassword.getText().toString();
        if(password.equals(confirmPassword))
        {
            Customer customer = new Customer();
            customer.setFirstName(editFirstName.getText().toString());
            customer.setLastName(editLastName.getText().toString());
            customer.setPassword(password);
            customer.setEmail(editEmail.getText().toString());
            customer.setMobileNo(editContact.getText().toString());
            return customer;
        }
        else {
            Toast.makeText(this, "passwords do not match", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}