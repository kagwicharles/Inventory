package com.kagwisoftwares.inventory.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.kagwisoftwares.inventory.R;
import com.kagwisoftwares.inventory.entities.Phone;

import java.util.ArrayList;
import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.ViewHolder> {

    private ArrayList<Phone> phones;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView productName, productTotal;
        private final ImageView productIcon, arrowRight;

        public ViewHolder(View view) {
            super(view);
            productName = (TextView) view.findViewById(R.id.txtproductName);
            productIcon = (ImageView) view.findViewById(R.id.imgproductIcon);
            arrowRight = (ImageView) view.findViewById(R.id.imgarrowRight);
            productTotal = (TextView) view.findViewById(R.id.txtproductTotal);
        }

        public TextView getProductName() {
            return productName;
        }

        public ImageView getProductIcon() {
            return productIcon;
        }

        public ImageView getArrowRight() {
            return arrowRight;
        }

        public TextView getProductTotal() {
            return productTotal;
        }

    }

    public PhoneAdapter(ArrayList<Phone> phones) {
        this.phones = phones;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getProductName().setText(phones.get(position).getPhoneCategory());
    }

    @Override
    public int getItemCount() {
        return phones.size();
    }
}
