package com.kagwisoftwares.inventory.adapters;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kagwisoftwares.inventory.R;
import com.kagwisoftwares.inventory.db.entities.ProductAttribute;

import java.util.List;
import java.util.Random;

public class AttributesAdapter extends RecyclerView.Adapter<AttributesAdapter.ViewHolder> {

    private List<ProductAttribute> productAttributes;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemAttribute, itemProperty, itemIcon;

        public ViewHolder(View view) {
            super(view);
            itemAttribute = (TextView) view.findViewById(R.id.item_attribute);
            itemProperty = (TextView) view.findViewById(R.id.item_property);
            itemIcon = (TextView) view.findViewById(R.id.tvIcon);
        }

        public TextView getItemName() {
            return itemAttribute;
        }

        public TextView getItemTotal() {
            return itemProperty;
        }

        public TextView getItemIcon() { return itemIcon; }
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
        String attrName = productAttributes.get(position).getAttrName();
        viewHolder.getItemIcon().setText(attrName.substring(0, 1));
        ((GradientDrawable) viewHolder.getItemIcon().getBackground()).setColor(setIconBackground());
        viewHolder.getItemName().setText(attrName);
        viewHolder.getItemTotal().setText(productAttributes.get(position).getAttrProperty());
    }

    @Override
    public int getItemCount() {
        return productAttributes.size();
    }

    int setIconBackground() {
        Random mRandom = new Random();
        return Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
    }
}
