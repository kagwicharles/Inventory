package com.kagwisoftwares.inventory.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.kagwisoftwares.inventory.R;
import com.kagwisoftwares.inventory.db.Inventorydb;
import com.kagwisoftwares.inventory.utils.ThreadProductTotalById;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SaleStockDialog extends DialogFragment {

    private EditText quantitySold;
    private TextView error;
    private ExecutorService service;
    private Future<String> currentStock;
    private Bundle bundle;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_sale_dialog, new LinearLayout(getActivity()), false);

        quantitySold = view.findViewById(R.id.edt_stock_sold);
        error = view.findViewById(R.id.error);
        error.setVisibility(View.GONE);

        bundle = getArguments();
        service = Executors.newFixedThreadPool(3);
        currentStock = service.submit(new ThreadProductTotalById(bundle.getString("STOCK_NAME", "0")
                , getContext()));

        TextView cancel = view.findViewById(R.id.cancelBtn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        TextView sell = view.findViewById(R.id.okBtn);
        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValue();
            }
        });

        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.setContentView(view);
        return builder;
    }

    void getValue() {
        String quantity = quantitySold.getText().toString();
        if (quantity.equals("")) {
            error.setText("Enter quantity");
            error.setVisibility(View.VISIBLE);
            return;
        }
        int quantityToSell = Integer.parseInt(quantity);
        try {
            if (Integer.parseInt(currentStock.get()) < quantityToSell) {
                error.setText("Low on Stock");
                error.setVisibility(View.VISIBLE);
            } else {
                error.setVisibility(View.GONE);
                updateStock(bundle.getString("STOCK_NAME", "0"), quantityToSell);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void updateStock(String itemName, int quantityToSell) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                int productId = Inventorydb.
                        getDatabase(getContext()).
                        dao().getProductIdByName(itemName);
                int newValue = 0;
                try {
                    newValue = Integer.parseInt(currentStock.get()) - quantityToSell;
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Inventorydb.
                        getDatabase(getContext()).
                        dao().updateStock(newValue, productId);
            }
        };
        thread.start();
        dismiss();
        getActivity().recreate();
    }
}
