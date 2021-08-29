package com.kagwisoftwares.inventory.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kagwisoftwares.inventory.R;
import com.kagwisoftwares.inventory.models.ItemModel;

import java.util.ArrayList;

public class DashAdapter extends RecyclerView.Adapter<DashAdapter.ViewHolder> {

    private ArrayList<ItemModel> products;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemName, itemTotal;

        public ViewHolder(View view) {
            super(view);
            itemName = (TextView) view.findViewById(R.id.txtitemName);
            itemTotal = (TextView) view.findViewById(R.id.txtitemTotal);
        }

        public TextView getItemName() {
            return itemName;
        }

        public TextView getItemTotal() {
            return itemTotal;
        }
    }

    public DashAdapter(ArrayList<ItemModel> products) {
        this.products = products;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.dash_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getItemName().setText(products.get(position).getItemName());
        viewHolder.getItemTotal().setText(String.valueOf(products.get(position).getItemTotal()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
