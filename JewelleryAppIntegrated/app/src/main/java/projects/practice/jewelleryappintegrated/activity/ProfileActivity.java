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

public class ProfileActivity extends AppCompatActivity {
    TextView textFirstName,textLastName,textEmail,textPhoneNumber , textGender;
    ImageView profileImage;
    Button btnclose;

    Customer loggedCustomer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        textFirstName = findViewById(R.id.proffirstname);
        textLastName = findViewById(R.id.proflastname);
        textEmail = findViewById(R.id.profemail);
        textPhoneNumber = findViewById(R.id.profmobile);
        textGender = findViewById(R.id.profgender);
        btnclose = findViewById(R.id.profclose);
        Intent intent = getIntent();
        loggedCustomer = (Customer) intent.getSerializableExtra("loggedCustomer");
        textFirstName.setText(loggedCustomer.getFirstName());
        textLastName.setText(loggedCustomer.getLastName());
        textEmail.setText("Email: "+loggedCustomer.getEmail());
        textPhoneNumber.setText("Mobile: "+loggedCustomer.getMobileNo());
        textGender.setText("Gender : "+ loggedCustomer.getGender());


        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ProfileActivity.this,AfterLoginHomeActivity.class);
//                intent.putExtra("loggedCustomer",loggedCustomer);
//
                finish();
            }
        });
    }
}