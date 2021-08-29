package com.kagwisoftwares.inventory.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kagwisoftwares.inventory.R;
import com.kagwisoftwares.inventory.ui.StockListingActivity;
import com.kagwisoftwares.inventory.db.Inventorydb;
import com.kagwisoftwares.inventory.db.entities.Category;
import com.kagwisoftwares.inventory.utils.MyThread;
import com.kagwisoftwares.inventory.utils.ThreadCategoryId;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private List<Category> categories;
    private Activity activity;
    private ExecutorService service;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView categoryName, categoryTotal;
        private final ImageView categoryIcon;

        public ViewHolder(View view) {
            super(view);
            categoryName = (TextView) view.findViewById(R.id.txtproductName);
            categoryIcon = (ImageView) view.findViewById(R.id.imgproductIcon);
            categoryTotal = (TextView) view.findViewById(R.id.txtproductTotal);
        }

        public TextView getCategoryName() {
            return categoryName;
        }

        public ImageView getCategoryIcon() {
            return categoryIcon;
        }

        public TextView getCategoryTotal() {
            return categoryTotal;
        }

    }

    public ProductsAdapter(List<Category> categories, Activity activity) {
        this.categories = categories;
        this.activity = activity;
        service = Executors.newFixedThreadPool(3);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        String categoryName = categories.get(position).getCategory_name();
        Future<String> stockSize = service.submit(new MyThread(categoryName, activity.getApplicationContext()));
        viewHolder.getCategoryName().setText(categoryName);
        viewHolder.getCategoryIcon().setImageBitmap(setImage(categories.get(position).getCategory_image()));

        try {
            viewHolder.getCategoryTotal().setText(stockSize.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Future<String> categoryId = service.submit(new ThreadCategoryId(categoryName, activity.getApplicationContext()));
                        Intent intent = new Intent(view.getContext(), StockListingActivity.class);
                try {
                    intent.putExtra("CATEGORY_ID", categoryId.get());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public Bitmap setImage(byte[] byteArray) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bitmap;
    }
}
