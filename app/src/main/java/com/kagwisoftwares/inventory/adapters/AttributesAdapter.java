package com.kagwisoftwares.inventory.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kagwisoftwares.inventory.R;
import com.kagwisoftwares.inventory.entities.ProductAttribute;

import java.util.ArrayList;
import java.util.List;

public class AttributesAdapter extends RecyclerView.Adapter<AttributesAdapter.ViewHolder> {

    private List<ProductAttribute> productAttributes;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemAttribute, itemProperty;

        public ViewHolder(View view) {
            super(view);
            itemAttribute = (TextView) view.findViewById(R.id.item_attribute);
            itemProperty = (TextView) view.findViewById(R.id.item_property);
        }

        public TextView getItemName() {
            return itemAttribute;
        }

        public TextView getItemTotal() {
            return itemProperty;
        }
    }

    public AttributesAdapter(List<ProductAttribute> productAttributes) {
        this.productAttributes = productAttributes;
    }

    @Override
    public AttributesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.attribute_item, viewGroup, false);

        return new AttributesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AttributesAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.getItemName().setText(productAttributes.get(position).getAttrName());
        viewHolder.getItemTotal().setText(productAttributes.get(position).getAttrProperty());
    }

    @Override
    public int getItemCount() {
        return productAttributes.size();
    }
}
