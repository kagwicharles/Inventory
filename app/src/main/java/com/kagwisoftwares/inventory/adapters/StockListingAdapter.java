package com.kagwisoftwares.inventory.adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kagwisoftwares.inventory.R;
import com.kagwisoftwares.inventory.ui.ViewStockItemActivity;
import com.kagwisoftwares.inventory.db.entities.ProductItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StockListingAdapter extends RecyclerView.Adapter<StockListingAdapter.ViewHolder> {

    private final List<ProductItem> stockItems;
    private DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    private ExecutorService service;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView stockItem, stockTotal, stockUpdated;

        public ViewHolder(View view) {
            super(view);
            stockItem = view.findViewById(R.id.subCategory);
            stockTotal = view.findViewById(R.id.stockTotal);
            stockUpdated = view.findViewById(R.id.last_updated);
        }

        public TextView getStockItem() {
            return stockItem;
        }

        public TextView getStockTotal() {
            return stockTotal;
        }

        public TextView getStockUpdated() {
            return stockUpdated;
        }

    }

    public StockListingAdapter(List<ProductItem> stockItems) {
        this.stockItems = stockItems;
        service = Executors.newFixedThreadPool(3);
    }

    @Override
    public StockListingAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.stock_item, viewGroup, false);
        return new StockListingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StockListingAdapter.ViewHolder viewHolder, int position) {
        int stockSize = stockItems.size();
        Log.d("STOCK SIZE ", String.valueOf(stockSize));
        viewHolder.getStockItem().setText(stockItems.get(position).getItem_name());
        viewHolder.getStockTotal().setText(String.valueOf(stockItems.get(position).getItem_units()));
        viewHolder.getStockUpdated().setText(df.format(stockItems.get(position).getDate()));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ViewStockItemActivity.class);
                intent.putExtra("ITEM_NAME", viewHolder.getStockItem().getText().toString());
                intent.putExtra("ITEM_UPDATE", viewHolder.getStockUpdated().getText().toString());
                intent.putExtra("ITEM_STOCK", viewHolder.getStockTotal().getText().toString());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stockItems.size();
    }

    public Bitmap setImage(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
}