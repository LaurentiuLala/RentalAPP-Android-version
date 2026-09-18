package com.example.proiect;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.proiect.model.LocatieDTO;
import com.example.proiect.model.MasinaDTO;
import com.example.proiect.network.RetrofitClient;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminCarsActivity extends AppCompatActivity {
    private RecyclerView rvAdminCars;
    private AdminCarAdapter adapter;
    private List<MasinaDTO> carList = new ArrayList<>();
    private List<LocatieDTO> locationList = new ArrayList<>();
    private Uri selectedImageUri;
    private ImageView ivDialogPreview;

    private final ActivityResultLauncher<String> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    if (ivDialogPreview != null) {
                        ivDialogPreview.setImageURI(uri);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cars);

        Toolbar toolbar = findViewById(R.id.toolbarAdminCars);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        rvAdminCars = findViewById(R.id.rvAdminCars);
        if (rvAdminCars != null) {
            rvAdminCars.setLayoutManager(new LinearLayoutManager(this));
            adapter = new AdminCarAdapter(carList, new AdminCarAdapter.OnCarActionListener() {
                @Override
                public void onEdit(MasinaDTO car) {
                    showCarDialog(car);
                }

                @Override
                public void onDelete(MasinaDTO car) {
                    showDeleteConfirmation(car);
                }
            });
            rvAdminCars.setAdapter(adapter);
        }

        View fab = findViewById(R.id.fabAddCar);
        if (fab != null) {
            fab.setOnClickListener(v -> showCarDialog(null));
        }

        loadLocations();
        loadCars();
    }

    private void loadLocations() {
        RetrofitClient.getApiService(this).getAllLocatii().enqueue(new Callback<List<LocatieDTO>>() {
            @Override
            public void onResponse(Call<List<LocatieDTO>> call, Response<List<LocatieDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    locationList.clear();
                    locationList.addAll(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<LocatieDTO>> call, Throwable t) {}
        });
    }

    private void loadCars() {
        RetrofitClient.getApiService(this).getAllMasini().enqueue(new Callback<List<MasinaDTO>>() {
            @Override
            public void onResponse(Call<List<MasinaDTO>> call, Response<List<MasinaDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    carList.clear();
                    carList.addAll(response.body());
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MasinaDTO>> call, Throwable t) {}
        });
    }

    private void showCarDialog(MasinaDTO car) {
        View view = getLayoutInflater().inflate(R.layout.dialog_car, null);
        if (view == null) return;

        ivDialogPreview = view.findViewById(R.id.ivDialogCarPreview);
        view.findViewById(R.id.btnDialogSelectImage).setOnClickListener(v -> pickImageLauncher.launch("image/*"));

        EditText etMarca = view.findViewById(R.id.etDialogMarca);
        EditText etModel = view.findViewById(R.id.etDialogModel);
        EditText etAn = view.findViewById(R.id.etDialogAn);
        EditText etPret = view.findViewById(R.id.etDialogPret);
        CheckBox cbDisponibil = view.findViewById(R.id.cbDialogDisponibil);
        Spinner spinnerLocatie = view.findViewById(R.id.spinnerLocatie);

        selectedImageUri = null;

        List<String> locationNames = new ArrayList<>();
        for (LocatieDTO loc : locationList) {
            locationNames.add((loc.getOras() != null ? loc.getOras() : "") + ", " + (loc.getStrada() != null ? loc.getStrada() : ""));
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locationNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocatie.setAdapter(spinnerAdapter);

        if (car != null) {
            etMarca.setText(car.getMarca());
            etModel.setText(car.getModel());
            etAn.setText(String.valueOf(car.getAnFabricatie()));
            etPret.setText(String.valueOf(car.getPretPeZi()));
            cbDisponibil.setChecked(car.isDisponibil());
            
            if (car.getImages() != null && !car.getImages().isEmpty()) {
                Glide.with(this).load("http://10.0.2.2:8080" + car.getImages().get(0)).into(ivDialogPreview);
            }

            for (int i = 0; i < locationList.size(); i++) {
                if (locationList.get(i).getId().equals(car.getLocatieId())) {
                    spinnerLocatie.setSelection(i);
                    break;
                }
            }
        }

        new AlertDialog.Builder(this)
                .setTitle(car == null ? "Add Car" : "Edit Car")
                .setView(view)
                .setPositiveButton("Save", (dialog, which) -> {
                    String marca = etMarca.getText().toString().trim();
                    String model = etModel.getText().toString().trim();
                    String anStr = etAn.getText().toString().trim();
                    String pretStr = etPret.getText().toString().trim();

                    if (marca.isEmpty() || model.isEmpty() || anStr.isEmpty() || pretStr.isEmpty() || locationList.isEmpty()) {
                        Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    MasinaDTO dto = new MasinaDTO();
                    dto.setMarca(marca);
                    dto.setModel(model);
                    dto.setAnFabricatie(Integer.parseInt(anStr));
                    dto.setPretPeZi(Double.parseDouble(pretStr));
                    dto.setDisponibil(cbDisponibil.isChecked());
                    dto.setLocatieId(locationList.get(spinnerLocatie.getSelectedItemPosition()).getId());

                    if (car == null) {
                        createCar(dto);
                    } else {
                        updateCar(car.getId(), dto);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void createCar(MasinaDTO dto) {
        RetrofitClient.getApiService(this).createMasina(dto).enqueue(new Callback<MasinaDTO>() {
            @Override
            public void onResponse(Call<MasinaDTO> call, Response<MasinaDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (selectedImageUri != null) {
                        uploadImage(response.body().getId());
                    } else {
                        loadCars();
                    }
                }
            }

            @Override
            public void onFailure(Call<MasinaDTO> call, Throwable t) {}
        });
    }

    private void updateCar(Long id, MasinaDTO dto) {
        RetrofitClient.getApiService(this).updateMasina(id, dto).enqueue(new Callback<MasinaDTO>() {
            @Override
            public void onResponse(Call<MasinaDTO> call, Response<MasinaDTO> response) {
                if (response.isSuccessful()) {
                    if (selectedImageUri != null) {
                        uploadImage(id);
                    } else {
                        loadCars();
                    }
                }
            }

            @Override
            public void onFailure(Call<MasinaDTO> call, Throwable t) {}
        });
    }

    private void uploadImage(Long carId) {
        MultipartBody.Part body = getMultipartFromUri(selectedImageUri);
        if (body == null) return;

        RetrofitClient.getApiService(this).uploadImages(carId, Collections.singletonList(body)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                loadCars();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                loadCars();
            }
        });
    }

    private MultipartBody.Part getMultipartFromUri(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            File file = new File(getCacheDir(), "temp_image_" + System.currentTimeMillis() + ".jpg");
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            return MultipartBody.Part.createFormData("files", file.getName(), requestFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void showDeleteConfirmation(MasinaDTO car) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Car")
                .setMessage("Are you sure you want to delete " + car.getMarca() + " " + car.getModel() + "?")
                .setPositiveButton("Delete", (dialog, which) -> deleteCar(car.getId()))
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteCar(Long id) {
        RetrofitClient.getApiService(this).deleteMasina(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    loadCars();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {}
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
