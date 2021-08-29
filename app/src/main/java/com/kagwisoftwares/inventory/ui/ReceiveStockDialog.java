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

public class ReceiveStockDialog extends DialogFragment {

    private EditText quantityReceived;
    private TextView error;
    private ExecutorService service;
    private Future<String> currentStock;
    private Bundle bundle;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_receive_stock_dialog, new LinearLayout(getActivity()), false);

        quantityReceived = view.findViewById(R.id.edt_stock_received);
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

        TextView receive = view.findViewById(R.id.okBtn);
        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValue();
                getActivity().recreate();
            }
        });

        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.setContentView(view);
        return builder;
    }

    void getValue() {
        String quantity = quantityReceived.getText().toString();
        if (quantity.equals("")) {
            error.setText("Enter quantity");
            error.setVisibility(View.VISIBLE);
            return;
        }
        int quantityToReceive = Integer.parseInt(quantity);
        if (quantityToReceive <= 0) {
            error.setText("No stock received");
            error.setVisibility(View.VISIBLE);
        } else {
            error.setVisibility(View.GONE);
            updateStock(bundle.getString("STOCK_NAME", "0"), quantityToReceive);
        }

    }

    void updateStock(String itemName, int quantityToReceive) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                int productId = Inventorydb.
                        getDatabase(getContext()).
                        dao().getProductIdByName(itemName);
                int newValue = 0;
                try {
                    newValue = Integer.parseInt(currentStock.get()) + quantityToReceive;
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