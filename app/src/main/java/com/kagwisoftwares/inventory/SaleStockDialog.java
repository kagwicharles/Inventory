package com.kagwisoftwares.inventory;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.DialogFragment;

public class SaleStockDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_sale_dialog, new LinearLayout(getActivity()), false);

        Button cancel = view.findViewById(R.id.cancelBtn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        Button sell = view.findViewById(R.id.sellBtn);
        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.setContentView(view);
        return builder;
    }
}
