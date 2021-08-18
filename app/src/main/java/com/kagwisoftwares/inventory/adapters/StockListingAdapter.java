package com.kagwisoftwares.inventory.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kagwisoftwares.inventory.R;
import com.kagwisoftwares.inventory.entities.ProductItem;

import java.util.List;

public class StockListingAdapter extends RecyclerView.Adapter<StockListingAdapter.ViewHolder> {

    private final List<ProductItem> stockItems;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView stockItem, stockTotal;

        public ViewHolder(View view) {
            super(view);
            stockItem = (TextView) view.findViewById(R.id.subCategory);
            stockTotal = (TextView) view.findViewById(R.id.stockTotal);
        }

        public TextView getStockItem() {
            return stockItem;
        }

        public TextView getStockTotal() {
            return stockTotal;
        }

    }

    public StockListingAdapter(List<ProductItem> stockItems) {
        this.stockItems = stockItems;
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
        viewHolder.getStockTotal().setText("");
    }

    @Override
    public int getItemCount() {
        return stockItems.size();
    }

    public Bitmap setImage(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
}