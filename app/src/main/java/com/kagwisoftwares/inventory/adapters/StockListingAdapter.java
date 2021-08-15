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
import com.kagwisoftwares.inventory.entities.Phone;
import com.kagwisoftwares.inventory.models.StockCategoriesModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class StockListingAdapter extends RecyclerView.Adapter<StockListingAdapter.ViewHolder> {

    private final List<Phone> stockItems;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView stockItem, stockTotal, stockIn, stockOut, dateModified;

        public ViewHolder(View view) {
            super(view);
            stockItem = (TextView) view.findViewById(R.id.subCategory);
            stockTotal = (TextView) view.findViewById(R.id.totalItems);
            stockIn = (TextView) view.findViewById(R.id.totalIn);
            stockOut = (TextView) view.findViewById(R.id.totalOut);
            dateModified = (TextView) view.findViewById(R.id.dateModified);
        }

        public TextView getStockItem() {
            return stockItem;
        }

        public TextView getStockTotal() {
            return stockTotal;
        }

        public TextView getStockIn() {
            return stockIn;
        }

        public TextView getStockOut() {
            return stockOut;
        }

        public TextView getDateModified() {
            return dateModified;
        }

    }

    public StockListingAdapter(List<Phone> stockItems) {
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
        DateFormat df = new SimpleDateFormat("MM/dd/yy");
        Log.d("STOCK SIZE ", String.valueOf(stockSize));

        viewHolder.getStockItem().setText(stockItems.get(position).getPhoneCategory());
        viewHolder.getStockTotal().setText(String.valueOf(stockSize));
        viewHolder.getDateModified().setText("");
        viewHolder.getStockIn().setText("");
        viewHolder.getStockOut().setText("");

    }

    @Override
    public int getItemCount() {
        return stockItems.size();
    }

    public Bitmap setImage(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
}