package com.kagwisoftwares.inventory.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.kagwisoftwares.inventory.R;
import com.kagwisoftwares.inventory.db.entities.Category;
import com.kagwisoftwares.inventory.db.MyViewModel;

import java.io.ByteArrayOutputStream;

public class AddCategoryActivity extends AppCompatActivity {

    private ImageView imageView;
    private EditText editText;
    private TextInputLayout textInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Create Product");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = (ImageView) findViewById(R.id.imgbrandLogo);
        textInputLayout = (TextInputLayout) findViewById(R.id.inputLayoutBrand);
        editText = (EditText) findViewById(R.id.etbrandName);

        Button uploadImage = (Button) findViewById(R.id.uploadLogo);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchUploadAlert();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchUploadAlert();
            }
        });

        Button saveBrand = (Button) findViewById(R.id.btnSaveBrand);
        saveBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBrand();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        imageView.setImageBitmap(selectedImage);
                    }
                    break;

                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        if (selectedImage != null) {
                            imageView.setImageURI(selectedImage);
                        }
                    }
                    break;
            }
        }
    }

    void launchUploadAlert() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(AddCategoryActivity.this);
        builder.setTitle("Choose Logo");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    void addBrand() {
        int width = 100;
        int height = 100;

        Category category = new Category();
        String categoryName = editText.getText().toString();
        if (categoryName.equals("")) {
            textInputLayout.setError("Enter brand name");
            return;
        }
        category.setCategory_name(categoryName);

        try {
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
            category.setCategory_image(convertToByteArray(bitmap));
        } catch (ClassCastException e) {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
            return;
        }
        new MyViewModel(getApplication()).insertCategory(category);
        clear();
    }

    byte[] convertToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    void clear() {
        editText.setText("");
        imageView.setImageResource(R.drawable.ic_upload);
        Toast.makeText(this, "Brand added", Toast.LENGTH_SHORT).show();
    }

}

