package projects.practice.jewelleryappintegrated.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import projects.practice.jewelleryappintegrated.R;
import projects.practice.jewelleryappintegrated.entity.Customer;
import projects.practice.jewelleryappintegrated.entity.JewelleryProduct;

public class JewelleryProductDetailsActivity extends AppCompatActivity {

    ImageView productImage;
    TextView productName,  productPrice,  productDescription ;
    Button closebtn;

    JewelleryProduct jewelleryProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jewellery_product_details);
        productImage = findViewById(R.id.productImage);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        productDescription = findViewById(R.id.productDescription);
        closebtn = findViewById(R.id.closebtn);
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        jewelleryProduct = (JewelleryProduct) intent.getSerializableExtra("jewelleryProduct");
        productName.setText(jewelleryProduct.getJewellery_name());
        productPrice.setText(String.valueOf(jewelleryProduct.getJewellery_price()));
        productDescription.setText(jewelleryProduct.getJewellery_description());

    }
}