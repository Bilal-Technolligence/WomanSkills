package com.example.womanskills;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class AddProducts extends AppCompatActivity {
    EditText title, price;
    ImageView img1, img2, img3;
    Spinner category, subcat;
    String cat = "";
    String sub = "";
    private Uri imagePath1, imagePath2, imagePath3;
    int count1 = 0;
    int count2 = 0;
    int count3 = 0;
    CardView save;
    String tit, pri;
    ProgressDialog progressDialog;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference reference = database.getReference("Products");
    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);
        title = findViewById(R.id.txtTitle);
        price = findViewById(R.id.txtDes);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        category = findViewById(R.id.spinner_category);
        subcat = findViewById(R.id.spinner_subcategory);
        save = findViewById(R.id.btn_save);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading please wait..... ");
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cat = (String) parent.getItemAtPosition(position);
                if (cat.equals("Handicraft")) {
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.product_handicraft, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subcat.setAdapter(adapter);
                }
                if (cat.equals("Cloth Sewing")) {
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.product_cloth, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subcat.setAdapter(adapter);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                cat = "Handicraft";
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.product_handicraft, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subcat.setAdapter(adapter);
            }
        });
        subcat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sub = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if (cat.equals("Handicraft")) {
                    sub = "Embroidery";
                   }
                if (cat.equals("Cloth Sewing")) {
                   sub = "T-Shirts";
                }
            }
        });
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 4);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tit = title.getText().toString();
                pri = price.getText().toString();
                if (tit.equals("")) {
                    title.setError("Enter Valid Title");
                    title.setFocusable(true);
                } else if (pri.equals("")) {
                    price.setError("Enter Valid Price");
                    price.setFocusable(true);
                }  else if (count1 == 0 || count2 ==0 ||count3 ==0) {
                    Snackbar.make(view, "Please Select All Image", Snackbar.LENGTH_LONG).show();
                } else {
                    progressDialog.show();
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + FirebaseDatabase.getInstance().getReference().child("Users").push().getKey());
                    storageReference.putFile(imagePath1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful()) ;
                            Uri downloadUri = uriTask.getResult();
                            String img1 = downloadUri.toString();
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + FirebaseDatabase.getInstance().getReference().child("Users").push().getKey());
                            storageReference.putFile(imagePath2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!uriTask.isSuccessful()) ;
                                    Uri downloadUri = uriTask.getResult();
                                    String img2 = downloadUri.toString();
                                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + FirebaseDatabase.getInstance().getReference().child("Users").push().getKey());
                                    storageReference.putFile(imagePath3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                            while (!uriTask.isSuccessful()) ;
                                            Uri downloadUri = uriTask.getResult();
                                            String img3 = downloadUri.toString();
                                            final String push = FirebaseDatabase.getInstance().getReference().child("Services").push().getKey();
                                            final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                            ProductAttr productAttr = new ProductAttr();
                                            productAttr.setId(push);
                                            productAttr.setUserId(uid);
                                            productAttr.setTitle(tit);
                                            productAttr.setPrice(pri);
                                            productAttr.setCategory(cat);
                                            productAttr.setSubcategory(sub);
                                            productAttr.setImage1(img1);
                                            productAttr.setImage2(img2);
                                            productAttr.setImage3(img3);
                                            reference.child(push).setValue(productAttr);

                                            Toast.makeText(getApplicationContext(), "Product Added", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                            finish();

                                        }
                                    });

                                }
                            });


                        }
                    });

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddProducts.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCode && resultCode == resultCode
                && data != null && data.getData() != null) {
            if (requestCode == 2) {
                imagePath1 = data.getData();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imagePath1);
                    img1.setImageBitmap(bitmap);
                    count1 = 1;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (requestCode == 3) {
                imagePath2 = data.getData();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imagePath2);
                    img2.setImageBitmap(bitmap);
                    count2 = 1;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (requestCode == 4) {
                imagePath3 = data.getData();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imagePath3);
                    img3.setImageBitmap(bitmap);
                    count3 = 1;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}