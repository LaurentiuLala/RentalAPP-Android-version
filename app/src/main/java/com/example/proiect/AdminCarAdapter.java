package com.example.proiect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.proiect.model.MasinaDTO;
import java.util.List;

public class AdminCarAdapter extends RecyclerView.Adapter<AdminCarAdapter.AdminCarViewHolder> {
    private List<MasinaDTO> carList;
    private OnCarActionListener listener;

    public interface OnCarActionListener {
        void onEdit(MasinaDTO car);
        void onDelete(MasinaDTO car);
    }

    public AdminCarAdapter(List<MasinaDTO> carList, OnCarActionListener listener) {
        this.carList = carList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdminCarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_car, parent, false);
        return new AdminCarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminCarViewHolder holder, int position) {
        MasinaDTO car = carList.get(position);
        if (car != null) {
            holder.tvAdminCarName.setText((car.getMarca() != null ? car.getMarca() : "") + " " + (car.getModel() != null ? car.getModel() : ""));
            holder.tvAdminCarDetails.setText(car.getPretPeZi() + " EUR/day - " + (car.getLocatieDescriere() != null ? car.getLocatieDescriere() : "No Location"));

            if (car.getImages() != null && !car.getImages().isEmpty()) {
                String imageUrl = "http://10.0.2.2:8080" + car.getImages().get(0);
                Glide.with(holder.itemView.getContext())
                        .load(imageUrl)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .into(holder.ivAdminCar);
            } else {
                holder.ivAdminCar.setImageResource(R.drawable.ic_launcher_background);
            }

            holder.btnEditCar.setOnClickListener(v -> listener.onEdit(car));
            holder.btnDeleteCar.setOnClickListener(v -> listener.onDelete(car));
        }
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    static class AdminCarViewHolder extends RecyclerView.ViewHolder {
        TextView tvAdminCarName, tvAdminCarDetails;
        ImageView ivAdminCar;
        ImageButton btnEditCar, btnDeleteCar;

        public AdminCarViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAdminCarName = itemView.findViewById(R.id.tvAdminCarName);
            tvAdminCarDetails = itemView.findViewById(R.id.tvAdminCarDetails);
            ivAdminCar = itemView.findViewById(R.id.ivAdminCar);
            btnEditCar = itemView.findViewById(R.id.btnEditCar);
            btnDeleteCar = itemView.findViewById(R.id.btnDeleteCar);
        }
    }
}
