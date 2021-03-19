package com.youssef.fixit.Invoice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.squareup.picasso.Picasso;
import com.youssef.fixit.Models.Data.RetrofitClient;
import com.youssef.fixit.Models.Bids.CreateBid;
import com.youssef.fixit.databinding.ActivityInvoiceBinding;

import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoiceActivity extends AppCompatActivity {
    ActivityInvoiceBinding binding;
    Uri image_uri;
    int client_id;
    String Currency;
    String Title,description,TotalPrice,Discount,StringBalce;
    private  SweetAlertDialog loadingdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInvoiceBinding.inflate(getLayoutInflater());
        client_id=getIntent().getIntExtra("client_id",0);
        setContentView(binding.getRoot());
        UpladeImage();
        InitloadingDialog();
        GetCurrency();
        SendInvice();
    }

    private void InitView() {
    }

    void SendInvice(){
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Title=binding.edTitle.getText().toString();
                 description=binding.edDescription.getText().toString();
                 TotalPrice=binding.edTotalPrice.getText().toString();
                 Discount=binding.edDiscount.getText().toString();
                if(Title.isEmpty()){
                    Toast.makeText(InvoiceActivity.this, "Title is require", Toast.LENGTH_SHORT).show();
                    binding.edTitle.requestFocus();
                    return;
                }
                if(description.isEmpty()){
                    Toast.makeText(InvoiceActivity.this, "Description is require", Toast.LENGTH_SHORT).show();
                    binding.edDescription.requestFocus();
                    return;
                }
                if(TotalPrice.isEmpty()){
                    Toast.makeText(InvoiceActivity.this, "Total Price is require", Toast.LENGTH_SHORT).show();
                    binding.edTotalPrice.requestFocus();
                    return;
                }
                if(Discount.isEmpty()){
                    Toast.makeText(InvoiceActivity.this, "Discount Price is require", Toast.LENGTH_SHORT).show();
                    binding.edDiscount.requestFocus();
                    return;
                }
                if(Currency.isEmpty()){
                    Toast.makeText(InvoiceActivity.this, "Please Select Currency", Toast.LENGTH_SHORT).show();
                    return;
                }
                Double aDoubleTotalPrice=Double.valueOf(TotalPrice);
                Double DoubleDescont=Double.valueOf(Discount);
                Double Balace=aDoubleTotalPrice-((DoubleDescont * aDoubleTotalPrice)/100);
                 StringBalce=Balace+"";
                binding.edBalance.setText(StringBalce);
                binding.edBalance.setText(Balace+"");
                
                loadingdialog.show();
                int discount2=Integer.parseInt(Discount);
                RetrofitClient.getInstance().SendInvoice(Title,description,TotalPrice,discount2,client_id,StringBalce,Currency).enqueue(new Callback<CreateBid>() {
                    @Override
                    public void onResponse(Call<CreateBid> call, Response<CreateBid> response) {
                        loadingdialog.dismiss();
                        if(response.isSuccessful()){
                            if(response.body()!=null){
                                if(response.body().getData()!=null) {
                                    if (response.body().getData() != null) {
                                        new SweetAlertDialog(InvoiceActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                                .setTitleText("Sent")
                                                .show();
                                    }
                                }
                                else {
                                    Toast.makeText(InvoiceActivity.this,response.body().getErrors()+ "", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(InvoiceActivity.this, response.body().getErrors(), Toast.LENGTH_SHORT).show();
                            }

                        }
                        else {
                            Toast.makeText(InvoiceActivity.this, response.body().getErrors()+"", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CreateBid> call, Throwable t) {
                        loadingdialog.dismiss();
                        Toast.makeText(InvoiceActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        binding.edDiscount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String  TotalPrice2=binding.edTotalPrice.getText().toString();
                if(charSequence.length()>0){
                    String Discount2=binding.edDiscount.getText().toString();
                    Double aDoubleTotalPrice=Double.valueOf(TotalPrice2);
                    Double DoubleDescont=Double.valueOf(Discount2);
                    Double Balace=aDoubleTotalPrice-((DoubleDescont * aDoubleTotalPrice)/100);
                    StringBalce=Balace+"";
                    binding.edBalance.setText(StringBalce);
                    Toast.makeText(InvoiceActivity.this,"*"+DoubleDescont, Toast.LENGTH_SHORT).show();
                }
                else {
                    binding.edBalance.setText(TotalPrice2);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.edTotalPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.edDiscount.setText("0");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    void GetCurrency(){
        binding.sppCurrencyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Currency=adapterView.getAdapter().getItem(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    //uploade image
    private void UpladeImage() {
        binding.addAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission() == true) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, 505);
                } else {
                    requestPermission();
                }
            }
        });

        binding.imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_uri = null;
                binding.rvImage.setVisibility(View.GONE);
            }
        });
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(InvoiceActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                507);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 507:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(InvoiceActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, 505);
                    // main logic
                } else {
                    Toast.makeText(InvoiceActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(InvoiceActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(InvoiceActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(InvoiceActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 505 && resultCode == RESULT_OK && data != null) {
            image_uri = data.getData();
            binding.rvImage.setVisibility(View.VISIBLE);
            Picasso.get().load(image_uri).into(binding.image);
            try {
                InputStream is = getContentResolver().openInputStream(data.getData());

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    private void InitloadingDialog() {
        loadingdialog = new SweetAlertDialog(InvoiceActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        loadingdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        loadingdialog.setTitleText("Loading");
        loadingdialog.setCancelable(false);

    }
}