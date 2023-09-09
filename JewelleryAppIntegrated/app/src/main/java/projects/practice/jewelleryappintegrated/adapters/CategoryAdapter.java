package projects.practice.jewelleryappintegrated.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import projects.practice.jewelleryappintegrated.R;
import projects.practice.jewelleryappintegrated.activity.BeforeLoginHomeActivity;
import projects.practice.jewelleryappintegrated.activity.LoginActivity;
import projects.practice.jewelleryappintegrated.activity.ProductsActivity;
import projects.practice.jewelleryappintegrated.entity.Category;
import projects.practice.jewelleryappintegrated.entity.Customer;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

      private Context context;
    private List<Category> categoryList;
    private Customer loggedcustomer;

    public CategoryAdapter(Context context,List<Category> categoryList , Customer loggedcustomer) {
        this.context = context;
        this.categoryList = categoryList;
        this.loggedcustomer = loggedcustomer;

        if(loggedcustomer ==null){
            Log.d("mylogs", "CategoryAdapter: nulll");
        }else {
            Log.d("mylogs", "CategoryAdapter: "+ loggedcustomer);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.categorycardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categoryList.get(position);

        holder.categoryNameTextView.setText(category.getCategory_name());
        holder.categoryDescriptionTextView.setText(category.getCategory_description());
        holder.categoryNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductsActivity.class);
                intent.putExtra("category",category);
                intent.putExtra("loggedCustomer",loggedcustomer);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {

        public TextView categoryNameTextView;
        public TextView categoryDescriptionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryNameTextView = itemView.findViewById(R.id.categoryNameTextView);
            categoryDescriptionTextView = itemView.findViewById(R.id.categoryDescriptionTextView);

        }
    }
}