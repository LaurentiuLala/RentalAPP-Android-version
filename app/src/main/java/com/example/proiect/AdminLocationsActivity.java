package com.example.proiect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proiect.model.LocatieDTO;
import com.example.proiect.network.RetrofitClient;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminLocationsActivity extends AppCompatActivity {
    private RecyclerView rvLocations;
    private LocationAdapter adapter;
    private List<LocatieDTO> locationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_locations);

        Toolbar toolbar = findViewById(R.id.toolbarLocations);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        rvLocations = findViewById(R.id.rvLocations);
        if (rvLocations != null) {
            rvLocations.setLayoutManager(new LinearLayoutManager(this));
            adapter = new LocationAdapter(locationList, new LocationAdapter.OnLocationActionListener() {
                @Override
                public void onEdit(LocatieDTO location) {
                    showLocationDialog(location);
                }

                @Override
                public void onDelete(LocatieDTO location) {
                    showDeleteConfirmation(location);
                }
            });
            rvLocations.setAdapter(adapter);
        }

        View fab = findViewById(R.id.fabAddLocation);
        if (fab != null) {
            fab.setOnClickListener(v -> showLocationDialog(null));
        }

        loadLocations();
    }

    private void loadLocations() {
        RetrofitClient.getApiService(this).getAllLocatii().enqueue(new Callback<List<LocatieDTO>>() {
            @Override
            public void onResponse(Call<List<LocatieDTO>> call, Response<List<LocatieDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    locationList.clear();
                    locationList.addAll(response.body());
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<LocatieDTO>> call, Throwable t) {
                Toast.makeText(AdminLocationsActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showLocationDialog(LocatieDTO location) {
        View view = getLayoutInflater().inflate(R.layout.dialog_location, null);
        if (view == null) return;

        EditText etOras = view.findViewById(R.id.etDialogOras);
        EditText etStrada = view.findViewById(R.id.etDialogStrada);
        EditText etNumar = view.findViewById(R.id.etDialogNumar);

        if (location != null) {
            etOras.setText(location.getOras());
            etStrada.setText(location.getStrada());
            etNumar.setText(location.getNumar());
        }

        new AlertDialog.Builder(this)
                .setTitle(location == null ? "Add Location" : "Edit Location")
                .setView(view)
                .setPositiveButton("Save", (dialog, which) -> {
                    String oras = etOras.getText().toString().trim();
                    String strada = etStrada.getText().toString().trim();
                    String numar = etNumar.getText().toString().trim();

                    if (oras.isEmpty() || strada.isEmpty() || numar.isEmpty()) {
                        Toast.makeText(this, "All fields required", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    LocatieDTO dto = new LocatieDTO();
                    dto.setOras(oras);
                    dto.setStrada(strada);
                    dto.setNumar(numar);

                    if (location == null) {
                        createLocation(dto);
                    } else {
                        updateLocation(location.getId(), dto);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void createLocation(LocatieDTO dto) {
        RetrofitClient.getApiService(this).createLocatie(dto).enqueue(new Callback<LocatieDTO>() {
            @Override
            public void onResponse(Call<LocatieDTO> call, Response<LocatieDTO> response) {
                if (response.isSuccessful()) {
                    loadLocations();
                }
            }

            @Override
            public void onFailure(Call<LocatieDTO> call, Throwable t) {}
        });
    }

    private void updateLocation(Long id, LocatieDTO dto) {
        RetrofitClient.getApiService(this).updateLocatie(id, dto).enqueue(new Callback<LocatieDTO>() {
            @Override
            public void onResponse(Call<LocatieDTO> call, Response<LocatieDTO> response) {
                if (response.isSuccessful()) {
                    loadLocations();
                }
            }

            @Override
            public void onFailure(Call<LocatieDTO> call, Throwable t) {}
        });
    }

    private void showDeleteConfirmation(LocatieDTO location) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Location")
                .setMessage("Are you sure you want to delete " + location.getOras() + "?")
                .setPositiveButton("Delete", (dialog, which) -> deleteLocation(location.getId()))
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteLocation(Long id) {
        RetrofitClient.getApiService(this).deleteLocatie(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    loadLocations();
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
