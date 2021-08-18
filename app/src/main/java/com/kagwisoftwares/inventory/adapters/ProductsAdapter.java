package com.kagwisoftwares.inventory.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.internal.ViewOverlayImpl;
import com.kagwisoftwares.inventory.MainActivity;
import com.kagwisoftwares.inventory.R;
import com.kagwisoftwares.inventory.StockListingActivity;
import com.kagwisoftwares.inventory.db.Inventorydb;
import com.kagwisoftwares.inventory.entities.Category;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private List<Category> categories;
    private Activity activity;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView categoryName, categoryTotal;
        private final ImageView categoryIcon, arrowRight;

        public ViewHolder(View view) {
            super(view);
            categoryName = (TextView) view.findViewById(R.id.txtproductName);
            categoryIcon = (ImageView) view.findViewById(R.id.imgproductIcon);
            arrowRight = (ImageView) view.findViewById(R.id.imgarrowRight);
            categoryTotal = (TextView) view.findViewById(R.id.txtproductTotal);
        }

        public TextView getCategoryName() {
            return categoryName;
        }

        public ImageView getCategoryIcon() {
            return categoryIcon;
        }

        public ImageView getArrowRight() {
            return arrowRight;
        }

        public TextView getCategoryTotal() {
            return categoryTotal;
        }

    }

    public ProductsAdapter(List<Category> categories, Activity activity) {
        this.categories = categories;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getCategoryName().setText(categories.get(position).getCategory_name());
        viewHolder.getCategoryIcon().setImageBitmap(setImage(categories.get(position).getCategory_image()));
        viewHolder.getArrowRight().setImageResource(R.drawable.ic_line_graph);
        viewHolder.getCategoryTotal().setText(String.valueOf(100));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        int itemId = Inventorydb.
                                getDatabase(view.getContext().getApplicationContext()).dao().getCategory(viewHolder.getCategoryName().getText().toString());
                        Intent intent = new Intent(view.getContext(), StockListingActivity.class);
                        intent.putExtra("CATEGORY_ID", itemId);
                        view.getContext().startActivity(intent);
                    }
                };
                thread.start();
            }
        });
    }

    void getItemId() {

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public Bitmap setImage(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
}
