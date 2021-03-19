package com.youssef.fixit.Auth;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.youssef.fixit.Models.Data.RealPathUtil;
import com.youssef.fixit.Models.Data.RetrofitClient;
import com.youssef.fixit.Models.Cities.Cites;
import com.youssef.fixit.Models.Cities.Datum;
import com.youssef.fixit.Models.Register.Register;
import com.youssef.fixit.R;
import com.youssef.fixit.MainActivity.MainActivity;
import com.youssef.fixit.databinding.FragmentRegisterBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {
    FragmentRegisterBinding binding;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    int Country_id, Area_id, City_id, Category_id;
    String type = "";
    private ProgressDialog dialog;
    List<String> Countery = new ArrayList<>();
    List<String> Cities = new ArrayList<>();
    List<String> Area = new ArrayList<>();
    List<String> Type = new ArrayList<>();
    Uri image_uri;
    byte[] imageBytes;
    public Bitmap bitmap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        IntialSharedPreferences();
        InitDialog();
        InitUplodeIame();
        GetCountry();
        GetType();
        Register();
    }

    private void GetCountry() {
        dialog.show();
        Countery.add("select Country");
        Cities.add("select Province");
        Area.add("select City");
        RetrofitClient.getInstance().GetCountry().enqueue(new Callback<Cites>() {
            @Override
            public void onResponse(Call<Cites> call, Response<Cites> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getData() != null) {
                            final List<Datum> Country1 = response.body().getData();
                            List<String> Country2 = new ArrayList<>();
                            for (int i = 0; i < Country1.size(); i++) {
                                Countery.add(Country1.get(i).getName());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                    (getContext(), android.R.layout.simple_spinner_item, Countery);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            binding.sppCuntery.setAdapter(adapter);
                            binding.sppCuntery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    if (i != 0) {
                                        Country_id = Country1.get(i - 1).getId();
                                        binding.ltCity.setVisibility(View.VISIBLE);
                                        GetCity(Country_id);
                                    } else if (i == 0) {
                                        Country_id = 0;
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Cites> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage() + "", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    private void GetCity(int id) {
        dialog.show();
        RetrofitClient.getInstance().GetCity(id).enqueue(new Callback<Cites>() {
            @Override
            public void onResponse(Call<Cites> call, Response<Cites> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getData() != null) {
                            final List<Datum> City1 = response.body().getData();
                            List<String> City2 = new ArrayList<>();
                            for (int i = 0; i < City1.size(); i++) {
                                Cities.add(City1.get(i).getName());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                    (getContext(), android.R.layout.simple_spinner_item, Cities);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            binding.sppCity.setAdapter(adapter);
                            binding.sppCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    if (i != 0) {
                                        City_id = City1.get(i - 1).getId();
                                        binding.ltArea.setVisibility(View.VISIBLE);
                                        GetArea(City_id);
                                    } else if (i == 0) {
                                        City_id = 0;
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Cites> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage() + "", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    private void GetArea(int id) {
        dialog.show();
        RetrofitClient.getInstance().GetArea(id).enqueue(new Callback<Cites>() {
            @Override
            public void onResponse(Call<Cites> call, Response<Cites> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getData() != null) {
                            final List<Datum> Area1 = response.body().getData();
                            List<String> Area2 = new ArrayList<>();
                            for (int i = 0; i < Area1.size(); i++) {
                                Area.add(Area1.get(i).getName());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                    (getContext(), android.R.layout.simple_spinner_item, Area);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            binding.sppArea.setAdapter(adapter);
                            binding.sppArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    if (i != 0) {
                                        Area_id = Area1.get(i - 1).getId();
                                        //GetArea(City_id);
                                    } else if (i == 0) {
                                        Area_id = 0;
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Cites> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage() + "", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    private void GetType() {
        Type.add("Select Type");
        Type.add("client");
        Type.add("professional");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_spinner_item, Type);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.sppType.setAdapter(adapter);
        binding.sppType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    if (i == 2) {
                        type = "profession";
                        binding.ltCategory.setVisibility(View.VISIBLE);
                        GetCategory();
                    } else if (i == 1) {
                        type = "client";
                        binding.ltCategory.setVisibility(View.GONE);
                    }
                } else if (i == 0) {
                    binding.ltCategory.setVisibility(View.GONE);
                    type = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void GetCategory() {
        List<String> Categories = new ArrayList<>();
        Categories.add("Choose Category");
        dialog.show();
        RetrofitClient.getInstance().GetCategory().enqueue(new Callback<com.youssef.fixit.Models.Category.Category>() {
            @Override
            public void onResponse(Call<com.youssef.fixit.Models.Category.Category> call, Response<com.youssef.fixit.Models.Category.Category> response) {
                dialog.dismiss();
                if (response.isSuccessful() && response.body().getData() != null) {
                    if (response.body().getData().size() > 0) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            com.youssef.fixit.Models.Category.Datum datum = response.body().getData().get(i);
                            Categories.add(datum.getTitle());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                (getContext(), android.R.layout.simple_spinner_item, Categories);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.sppCategory.setAdapter(adapter);
                        binding.sppCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i > 0) {
                                   /* if (response.body().getData().get(i - 1).getSubCategoryCount() > 0) {
                                        List<SubCategory> sub_categories_list = response.body().getData().get(i - 1).getSubCategory();
                                        binding.ltSubCategory1.setVisibility(View.VISIBLE);
                                        List<String> sub_categories = new ArrayList<>();
                                        sub_categories.add("Select SubCategory");
                                        for (int ii = 0; ii < sub_categories_list.size(); ii++) {
                                            sub_categories.add(sub_categories_list.get(ii).getTitle());
                                        }
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                                (getContext(), android.R.layout.simple_spinner_item, sub_categories);

                                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        binding.sppSubCategory1.setAdapter(adapter);
                                        binding.sppSubCategory1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                if (i > 0) {
                                                    Category_id = sub_categories_list.get(i - 1).getId();
                                                }
                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {

                                            }
                                        });
                                    } else {*/
                                        Category_id = response.body().getData().get(i - 1).getId();
                                        binding.ltSubCategory1.setVisibility(View.GONE);
                                  //  }
                                } else {
                                    binding.ltSubCategory1.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<com.youssef.fixit.Models.Category.Category> call, Throwable t) {

            }
        });
    }

    private void Register() {
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation();
            }
        });
    }

    private void Validation() {
        binding.ltName.setBackground(getContext().getDrawable(R.drawable.stroke_rosasy));
        binding.ltEmail.setBackground(getContext().getDrawable(R.drawable.stroke_rosasy));
        binding.ltPhone.setBackground(getContext().getDrawable(R.drawable.stroke_rosasy));
        binding.ltCuntery.setBackground(getContext().getDrawable(R.drawable.stroke_rosasy));
        binding.ltCity.setBackground(getContext().getDrawable(R.drawable.stroke_rosasy));
        binding.ltArea.setBackground(getContext().getDrawable(R.drawable.stroke_rosasy));
        binding.ltType.setBackground(getContext().getDrawable(R.drawable.stroke_rosasy));
        binding.ltCategory.setBackground(getContext().getDrawable(R.drawable.stroke_rosasy));
        binding.ltSubCategory1.setBackground(getContext().getDrawable(R.drawable.stroke_rosasy));
        binding.ltPassword.setBackground(getContext().getDrawable(R.drawable.stroke_rosasy));
        binding.ltConfirmPassword.setBackground(getContext().getDrawable(R.drawable.stroke_rosasy));

        String name, email, password, ConfirmPassword, phone;
        name = binding.etNameSingup.getText().toString();
        email = binding.etMailSingup.getText().toString();
        password = binding.etPasswordSingup.getText().toString();
        ConfirmPassword = binding.etConfirmPassword.getText().toString();
        phone = binding.etPhone.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(getContext(), "Name is Required!", Toast.LENGTH_SHORT).show();
            binding.etNameSingup.requestFocus();
            binding.ltName.setBackground(getContext().getDrawable(R.drawable.stroke_read));
            return;
        }
        if (email.isEmpty()) {
            Toast.makeText(getContext(), "Email is Required!", Toast.LENGTH_SHORT).show();
            binding.etMailSingup.requestFocus();
            binding.ltEmail.setBackground(getContext().getDrawable(R.drawable.stroke_read));
            return;
        }


        if (password.isEmpty()) {
            Toast.makeText(getContext(), "password is Required!", Toast.LENGTH_SHORT).show();
            binding.etPasswordSingup.requestFocus();
            binding.ltPassword.setBackground(getContext().getDrawable(R.drawable.stroke_read));
            return;
        }


        if (!ConfirmPassword.equals(password)) {
            Toast.makeText(getContext(), "password dont match", Toast.LENGTH_SHORT).show();
            binding.etConfirmPassword.requestFocus();
            binding.ltConfirmPassword.setBackground(getContext().getDrawable(R.drawable.stroke_read));
            return;
        }

        if (Country_id == 0) {
            Toast.makeText(getContext(), "Select Country is Required!", Toast.LENGTH_SHORT).show();
            binding.ltCuntery.setBackground(getContext().getDrawable(R.drawable.stroke_read));
            return;
        }

        if (City_id == 0) {
            Toast.makeText(getContext(), "Select City is Required!", Toast.LENGTH_SHORT).show();
            binding.ltCity.setBackground(getContext().getDrawable(R.drawable.stroke_read));
            return;
        }

        if (Area_id == 0) {
            Toast.makeText(getContext(), "Select Area is Required!", Toast.LENGTH_SHORT).show();
            binding.ltArea.setBackground(getContext().getDrawable(R.drawable.stroke_read));
            return;
        }

        if (phone.isEmpty()) {
            Toast.makeText(getContext(), "phone is Required", Toast.LENGTH_SHORT).show();
            binding.etPhone.requestFocus();
            binding.ltPhone.setBackground(getContext().getDrawable(R.drawable.stroke_read));
            return;
        }


        if (type.isEmpty()) {
            Toast.makeText(getContext(), "Select type is Required!", Toast.LENGTH_SHORT).show();
            binding.ltType.setBackground(getContext().getDrawable(R.drawable.stroke_read));
            return;
        }
        if (image_uri == null) {
            Toast.makeText(getContext(), "Select Image is Required!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (type.equals("profession") && Category_id == 0) {
            Toast.makeText(getContext(), "Select Category is Required!", Toast.LENGTH_SHORT).show();
            if (binding.sppSubCategory1.getVisibility() == View.VISIBLE) {
                binding.ltSubCategory1.setBackground(getContext().getDrawable(R.drawable.stroke_read));
            } else {
                binding.ltCategory.setBackground(getContext().getDrawable(R.drawable.stroke_read));
            }
            return;
        }
        dialog.show();

        File file = new File(RealPathUtil.getRealPath(getContext(), image_uri));
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image_file", file.getName(), requestFile);

        RetrofitClient.getInstance().Register(body, name, email, password, ConfirmPassword, Area_id, phone, type, Category_id).enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                dialog.dismiss();
                if (response.isSuccessful() && response.code() == 200) {
                    if (response.body().getData() != null) {
                        MainActivity.MyToken = response.body().getData().getToken();
                        List<String> roles = response.body().getData().getRoles();
                        MainActivity.MyRole = roles.get(0);
                        MainActivity.My_ID=response.body().getData().getId();
                        editor.putString("token", response.body().getData().getToken());
                        editor.putString("role", roles.get(0));
                        editor.putInt("my_id", response.body().getData().getId());
                        editor.commit();
                        getActivity().startActivity(new Intent(getContext(), MainActivity.class));
                        getActivity().finish();
                    } else {
                        for (int i=0;i<response.body().getErrors().size();i++){
                            Toast.makeText(getContext(), response.body().getErrors().get(i), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Log.e("hesham", response.message() + " " + response.toString());
                    Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getContext(), t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void InitDialog() {
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("please wait...");
        dialog.setTitle("Sign up");
        dialog.setCancelable(false);
    }

    private void InitUplodeIame() {
        binding.uplodeImageRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPermission()==true){
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, 505);
                }
                else {
                    requestPermission();
                }
            }
        });
    }
    private void requestPermission() {

        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                507);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 507:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, 505);
                    // main logic
                } else {
                    Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
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
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 505 && resultCode == getActivity().RESULT_OK && data != null) {
            image_uri = data.getData();
            Picasso.get().load(image_uri).into(binding.uplodeImageRegister);
            //  Imaetostring();
            try {
                InputStream is = getActivity().getContentResolver().openInputStream(data.getData());
                imageBytes = getBytes(is);
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), image_uri);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }

        return byteBuff.toByteArray();
    }

    public void IntialSharedPreferences() {
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = preferences.edit();
    }
}