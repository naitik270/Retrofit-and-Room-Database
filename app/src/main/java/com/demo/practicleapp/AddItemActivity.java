package com.demo.practicleapp;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.demo.practicleapp.ApiClasses.ApiPostViewModel;
import com.demo.practicleapp.db.Post;
import com.demo.practicleapp.db.PostViewModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AddItemActivity extends AppCompatActivity {

    Button btn_select_image, btn_save;
    private static final int REQUEST_GALLERY_CODE = 200;
    private Uri uri;
    SquareImageView iv_image;
    File file;
    Bitmap bmp;
    EditText edt_price, edt_title;
    TextView txt_category_name;
    CheckBox ckb_in_stock;


    String itemTitle, itemCategory, itemImagePath;
    Double itemPrice = 0.0;
    boolean inStockVal;

    private PostViewModel postViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);


        iv_image = findViewById(R.id.iv_image);
        edt_price = findViewById(R.id.edt_price);
        txt_category_name = findViewById(R.id.txt_category_name);
        edt_title = findViewById(R.id.edt_title);
        btn_select_image = findViewById(R.id.btn_select_image);
        ckb_in_stock = findViewById(R.id.ckb_in_stock);
        btn_save = findViewById(R.id.btn_save);

        btn_select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
                openGalleryIntent.setType("image/*");
                startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
            }
        });

        txt_category_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectCategory();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemTitle = edt_title.getText().toString();
                itemCategory = txt_category_name.getText().toString();
                itemPrice = Double.valueOf(edt_price.getText().toString());
                inStockVal = ckb_in_stock.isChecked();

                if (TextUtils.isEmpty(itemTitle)) {
                    edt_title.setError("Please enter Title Name");
                } else if (TextUtils.isEmpty(itemCategory)) {
                    txt_category_name.setError("Select Category");
                } else {
                    // calling method to add data to Realm database..
                    addRecordDialog(postViewModel);
                    Toast.makeText(AddItemActivity.this, "Course added to database..", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    void selectCategory() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddItemActivity.this);
        List<String> lstBrandName = new ArrayList<>();
        lstBrandName.add("Food");
        lstBrandName.add("Grocery");
        lstBrandName.add("Clothes");
        lstBrandName.add("Kitchen");
        lstBrandName.add("Dairy");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.row_dialog_label, lstBrandName);

        builder.setAdapter(dataAdapter, (dialog, which) -> {
            txt_category_name.setText(lstBrandName.get(which));
            txt_category_name.setTag(which);
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            uri = data.getData();
            String filePath = ClsGlobal.getRealPathFromURIPath(uri, AddItemActivity.this);
            file = new File(filePath);
            itemImagePath = filePath;
            Log.d("--path--", "filePath=" + filePath);
            try {
                bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            iv_image.setImageBitmap(bmp);
        }
    }

    void addRecordDialog(PostViewModel postViewModel) {
        postViewModel.savePost(new Post(itemTitle, itemCategory, itemImagePath, inStockVal, itemPrice));
    }

}