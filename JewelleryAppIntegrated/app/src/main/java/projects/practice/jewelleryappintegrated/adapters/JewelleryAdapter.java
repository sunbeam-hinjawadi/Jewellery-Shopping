package projects.practice.jewelleryappintegrated.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;

import java.util.List;

import projects.practice.jewelleryappintegrated.R;
import projects.practice.jewelleryappintegrated.activity.JewelleryProductDetailsActivity;
import projects.practice.jewelleryappintegrated.entity.Customer;
import projects.practice.jewelleryappintegrated.entity.JewelleryProduct;
import projects.practice.jewelleryappintegrated.util.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JewelleryAdapter extends RecyclerView.Adapter<JewelleryAdapter.ViewHolder> {

     Context context;
     List<JewelleryProduct> jewelleryList;
     Customer loggedcustomer;
    // JewelleryProduct jewelleryProduct;

    public JewelleryAdapter(Context context,List<JewelleryProduct> jewelleryList , Customer loggedcustomer) {
        this.context = context;
        this.jewelleryList = jewelleryList;
        this.loggedcustomer = loggedcustomer;
        Log.d("mylogs", "JewelleryAdapter: ");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.productscardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JewelleryProduct jewellery = jewelleryList.get(position);

        //holder.productImageView.setImageResource( jewellery.getJewellery_image() );

        holder.productNameTextView.setText(jewellery.getJewellery_name());
        holder.productPriceTextView.setText("Price: $" + jewellery.getJewellery_price());
        holder.productDescTextView.setText(jewellery.getJewellery_description());
        Log.d("mylogs", "onBindViewHolder: jewelleryadap"+ jewellery.getJewellery_image());
        Glide.with(context).load("http://192.168.216.23:8085/"+jewellery.getJewellery_image()).into(holder.productImageView);
        holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle adding the jewellery to the cart
                Log.d("mylogs", "added to cart");

                if(loggedcustomer !=null){
                    Log.d("mylogs", "added to cart of  " + loggedcustomer);
                    jewellery.setQty(1);
                    RetrofitClient.getInstance().getApi().addToCartItem(loggedcustomer.getCustomer_id(),jewellery).enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            Toast.makeText(context,"added succesfully",Toast.LENGTH_SHORT);
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {

                        }
                    });

                }
            }
        });



        holder.viewdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, JewelleryProductDetailsActivity.class));
                Intent intent = new Intent(context,JewelleryProductDetailsActivity.class);
                intent.putExtra("jewelleryProduct",jewellery);
                context.startActivity(intent);
            }
        });

//        holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Handle adding the jewellery to the cart
//                Toast.makeText(context,"added to cart",Toast.LENGTH_SHORT);
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return jewelleryList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView productImageView;
        public TextView productNameTextView;
        public TextView productPriceTextView;
        public TextView productDescTextView;
        public Button addToCartButton, viewdetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.productImageView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
            productDescTextView = itemView.findViewById(R.id.productDescTextView);
            addToCartButton = itemView.findViewById(R.id.addToCartButton);
            viewdetails = itemView.findViewById(R.id.viewdetails);

        }
    }
}
