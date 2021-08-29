package com.kagwisoftwares.inventory.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.kagwisoftwares.inventory.R;
import com.kagwisoftwares.inventory.db.Inventorydb;

public class DeleteStockDialog extends DialogFragment {

    private Bundle bundle;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().
                inflate(R.layout.fragment_delete_stock_dialog, new LinearLayout(getActivity()), false);

        TextView cancel = view.findViewById(R.id.cancelBtn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        TextView delete = view.findViewById(R.id.okBtn);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem();
            }
        });

        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.setContentView(view);
        return builder;
    }

    void deleteItem() {
        bundle = getArguments();
        Thread thread = new Thread() {
            @Override
            public void run() {
                int productId = Inventorydb.
                        getDatabase(getContext()).
                        dao().getProductIdByName(bundle.getString("STOCK_NAME"));
                Inventorydb.
                        getDatabase(getContext()).
                        dao().deleteProductById(productId);
                getActivity().finish();
            }
        };
        thread.start();
        dismiss();
    }
}