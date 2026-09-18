package com.example.proiect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proiect.model.LocatieDTO;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
    private List<LocatieDTO> locationList;
    private OnLocationActionListener listener;

    public interface OnLocationActionListener {
        void onEdit(LocatieDTO location);
        void onDelete(LocatieDTO location);
    }

    public LocationAdapter(List<LocatieDTO> locationList, OnLocationActionListener listener) {
        this.locationList = locationList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        LocatieDTO location = locationList.get(position);
        if (location != null) {
            holder.tvLocationOras.setText(location.getOras() != null ? location.getOras() : "N/A");
            String address = (location.getStrada() != null ? location.getStrada() : "") + " nr. " + (location.getNumar() != null ? location.getNumar() : "");
            holder.tvLocationAddress.setText(address.trim());

            holder.btnEditLocation.setOnClickListener(v -> listener.onEdit(location));
            holder.btnDeleteLocation.setOnClickListener(v -> listener.onDelete(location));
        }
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    static class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView tvLocationOras, tvLocationAddress;
        ImageButton btnEditLocation, btnDeleteLocation;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLocationOras = itemView.findViewById(R.id.tvLocationOras);
            tvLocationAddress = itemView.findViewById(R.id.tvLocationAddress);
            btnEditLocation = itemView.findViewById(R.id.btnEditLocation);
            btnDeleteLocation = itemView.findViewById(R.id.btnDeleteLocation);
        }
    }
}
