package projects.practice.jewelleryappintegrated.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;

import java.util.List;

import projects.practice.jewelleryappintegrated.R;
import projects.practice.jewelleryappintegrated.entity.CartProduct;
import projects.practice.jewelleryappintegrated.entity.Customer;
import projects.practice.jewelleryappintegrated.util.Quantity;
import projects.practice.jewelleryappintegrated.util.RefreshComponent;
import projects.practice.jewelleryappintegrated.util.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.ViewHolder> {

    Context context;
    private List<CartProduct> cartProducts;
    int q;
    RefreshComponent listener;
    int cid;
    Customer loggedcustomer;

    double tempchckprice;


    public CartProductAdapter(Context context, List<CartProduct> cartProducts , RefreshComponent listener ,int cid ,Customer loggedcustomer) {
        this.context =context;
        this.cartProducts = cartProducts;
        this.listener =listener;
        this.cid =cid;
        this.loggedcustomer=loggedcustomer;
        tempchckprice = 0;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cartproductcardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartProduct cartProduct = cartProducts.get(position);

        holder.productNameTextView.setText(cartProduct.getJewellery_name());
        holder.productPriceTextView.setText("Price: $" + (cartProduct.getPrice() / cartProduct.getQty()) ); //
        holder.productQuantityTextView.setText(String.valueOf(cartProduct.getQty()));

        double totalPrice = cartProduct.getPrice();//
        holder.totalPriceTextView.setText("Total: " + totalPrice);

//        holder.incrementButton.setOnClickListener(view -> {
//            int updatedQuantity = cartProduct.getQty() + 1;
//            cartProduct.setQty(updatedQuantity);
//            notifyDataSetChanged(); // Update the view to reflect the changes
//        });
//
//        holder.decrementButton.setOnClickListener(view -> {
//            int updatedQuantity = cartProduct.getQty() - 1;
//            if (updatedQuantity >= 0) {
//                cartProduct.setQty(updatedQuantity);
//                notifyDataSetChanged(); // Update the view to reflect the changes
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return cartProducts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView productNameTextView;
        public TextView productPriceTextView;
        public TextView productQuantityTextView;
        public TextView totalPriceTextView;
        public ImageButton incrementButton;
        public ImageButton decrementButton;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
            productQuantityTextView = itemView.findViewById(R.id.productQuantityTextView);
            totalPriceTextView = itemView.findViewById(R.id.totalPriceTextView);
            incrementButton = itemView.findViewById(R.id.incrementButton);
            decrementButton = itemView.findViewById(R.id.decrementButton);


            incrementButton.setOnClickListener(this);
            decrementButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            //listener.refresh();
            if(v.getId()==R.id.incrementButton)
            {
                CartProduct mycartProduct = cartProducts.get(getAdapterPosition());
                int jid = mycartProduct.getJewellery_id();
                q = Integer.valueOf(productQuantityTextView.getText().toString());
                q += 1;
                Quantity qty = new Quantity();
                qty.setCustomer_id(loggedcustomer.getCustomer_id());
                qty.setQuantity(q);
                qty.setPrice(mycartProduct.getPrice() + (mycartProduct.getPrice() / (q - 1) ) );

                RetrofitClient.getInstance().getApi().updateCart(jid,qty).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.d("mylogs", "onResponse: update cart");
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("mylogs", "onFailure: update cart");
                    }
                });

                productQuantityTextView.setText(""+q);
                listener.referesh1(loggedcustomer.getCustomer_id());
                listener.refereshTextView();

            }
            else if (v.getId()==R.id.decrementButton)
            {
                CartProduct mycartProduct = cartProducts.get(getAdapterPosition());
                int jid = mycartProduct.getJewellery_id();                Log.d("mylogs", "onClick: decrement "+loggedcustomer.getCustomer_id());
                q = Integer.valueOf(productQuantityTextView.getText().toString());
                q -= 1;
                Log.d("mylogs", "onClick: decrement " +q);
                Quantity qty2 = new Quantity();
                qty2.setQuantity(q);
                qty2.setCustomer_id(loggedcustomer.getCustomer_id());
                qty2.setPrice(mycartProduct.getPrice() - (mycartProduct.getPrice() / (q + 1) ) );

                if(q>=1)
                {
                    //cid qt2
                    RetrofitClient.getInstance().getApi().updateCart(jid,qty2).enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            Log.d("mylogs", "onResponse: update cart");
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Log.d("mylogs", "onFailure: update cart");
                        }
                    });
                    Log.d("mylogs", "onClick:  decrement  ---"+ q);
                    productQuantityTextView.setText(""+q);
                    listener.referesh1(loggedcustomer.getCustomer_id());
                    listener.refereshTextView();


                }
                else if(q<1)
                {
                     //implement api and
                   RetrofitClient.getInstance().getApi().deleteCartItem(jid,loggedcustomer).enqueue(new Callback<JsonObject>() {
                       @Override
                       public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                           Log.d("mylogs", "onResponse: deletecart item");
                       }

                       @Override
                       public void onFailure(Call<JsonObject> call, Throwable t) {

                       }
                   });
                    Log.d("mylogs", "onClick:  decrement  ---"+ q);
                    listener.referesh1(loggedcustomer.getCustomer_id());
                    listener.refereshTextView();

                }
            }
        }
    }
}

