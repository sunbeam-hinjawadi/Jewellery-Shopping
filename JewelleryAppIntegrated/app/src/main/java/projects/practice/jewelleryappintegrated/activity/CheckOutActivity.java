package projects.practice.jewelleryappintegrated.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.List;

import projects.practice.jewelleryappintegrated.R;
import projects.practice.jewelleryappintegrated.entity.CartProduct;
import projects.practice.jewelleryappintegrated.entity.Customer;
import projects.practice.jewelleryappintegrated.util.OrderProduct;
import projects.practice.jewelleryappintegrated.util.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutActivity extends AppCompatActivity {

    Customer loggedCustomer;
    private TextView textViewBillInfo;
    private EditText EditTextFullName;

    private EditText EditTextAddress;
    private EditText EdittextCity;
    private EditText EdittextCountry;
    private TextView textviewPaymentInformation;
    private EditText EdittextCardNumber;
    private EditText EdittextExpiryMonth;
    private EditText EdittextExpiryYear;
    private EditText EdittextCVV;
    private TextView amount;
    private Button placeOrderButton;
    private  int chckamt;

    String fullname , address , city , country ;
    long cardnum , expmonth , expyear , cvv;
    List<CartProduct> cartproducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        textViewBillInfo = findViewById(R.id.textViewBillInfo);
        EditTextFullName = findViewById(R.id.EditTextFullName);
        EdittextCity = findViewById(R.id.EdittextCity);
        EdittextCountry = findViewById(R.id.EdittextCountry);
        textviewPaymentInformation = findViewById(R.id.textviewPaymentInformation);
        EdittextCardNumber = findViewById(R.id.EdittextCardNumber);
        EdittextExpiryMonth = findViewById(R.id.EdittextExpiryMonth);
        EdittextExpiryYear = findViewById(R.id.EdittextExpiryYear);
        placeOrderButton = findViewById(R.id.placeOrderBtn);
        EdittextCVV = findViewById(R.id.EdittextCVV);
        EditTextAddress = findViewById(R.id.EditTextAddress);
        amount = findViewById(R.id.amount);
        loggedCustomer = (Customer) getIntent().getSerializableExtra("loggedCustomer");
        cartproducts = (List<CartProduct>) getIntent().getSerializableExtra("cartproducts");

        chckamt = getIntent().getIntExtra("chckamt",0);
        amount.setText(""+chckamt);
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code for db
                //notification
                //cart  empty
                fullname = EditTextFullName.getText().toString();
                address = EditTextAddress.getText().toString();
                city = EdittextCity.getText().toString();
                country = EdittextCountry.getText().toString();

                if (fullname.equals("") || address.equals("") || city.equals("") || country.equals("") ||  EdittextCardNumber.getText().toString().equals("") || EdittextExpiryMonth.getText().toString().equals("") || EdittextExpiryYear.getText().toString().equals("") || EdittextCVV.getText().toString().equals("")){
                    Toast.makeText(CheckOutActivity.this, "Enter proper details", Toast.LENGTH_SHORT).show();

                }else {
                    cardnum = Integer.valueOf(EdittextCardNumber.getText().toString());
                    expmonth = Integer.valueOf(EdittextExpiryMonth.getText().toString());
                    expyear = Integer.valueOf(EdittextExpiryYear.getText().toString());
                    cvv = Integer.valueOf(EdittextCVV.getText().toString());
                    if(cartproducts.size()>0){
                        for (CartProduct cartProduct:cartproducts) {
                            OrderProduct orderProduct = new OrderProduct();
                            orderProduct.setJewellery_id(cartProduct.getJewellery_id());
                            orderProduct.setQty(cartProduct.getQty());
                            orderProduct.setOrder_total(cartProduct.getPrice());
                            RetrofitClient.getInstance().getApi().placeorder(loggedCustomer.getCustomer_id(),orderProduct).enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    Toast.makeText(CheckOutActivity.this, "Order placed SuccessFully for product " + cartProduct.getJewellery_name(), Toast.LENGTH_SHORT).show();
                                }
                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {

                                }
                            });
                        }
                        Intent intent = new Intent(CheckOutActivity.this,AfterLoginHomeActivity.class);
                        intent.putExtra("loggedCustomer",loggedCustomer);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(CheckOutActivity.this, "Add something", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CheckOutActivity.this,AfterLoginHomeActivity.class);
                        intent.putExtra("loggedCustomer",loggedCustomer);
                        startActivity(intent);
                        finish();
                    }
                }
                Intent tempIntent = new Intent(CheckOutActivity.this, AfterLoginHomeActivity.class);
                tempIntent.putExtra("loggedCustomer",loggedCustomer);
                startActivity(tempIntent);
                finish();
            }

        });
    }
}