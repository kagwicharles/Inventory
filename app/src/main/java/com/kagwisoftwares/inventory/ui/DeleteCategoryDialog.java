package com.kagwisoftwares.inventory.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.kagwisoftwares.inventory.R;
import com.kagwisoftwares.inventory.db.Inventorydb;

public class DeleteCategoryDialog extends DialogFragment {

    private Bundle bundle;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().
                inflate(R.layout.fragment_delete_category_dialog, new LinearLayout(getActivity()), false);

        bundle = getArguments();

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
                deleteCategory(Integer.parseInt(bundle.getString("CATEGORY_ID", "0")));
            }
        });

        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.setContentView(view);
        return builder;
    }

    void deleteCategory(int categoryId) {
        Thread thread = new Thread() {

            @Override
            public void run() {
                Inventorydb.getDatabase(getContext()).
                        dao().deleteCategoryById(categoryId);
                Log.d("DELETED CATEGORY", String.valueOf(categoryId));
            }
        };
        thread.start();
        dismiss();
        getActivity().recreate();
    }

}