package com.example.proiect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proiect.model.UserDTO;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<UserDTO> userList;
    private OnUserDeleteListener deleteListener;

    public interface OnUserDeleteListener {
        void onUserDelete(UserDTO user);
    }

    public UserAdapter(List<UserDTO> userList, OnUserDeleteListener deleteListener) {
        this.userList = userList;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserDTO user = userList.get(position);
        if (user != null) {
            holder.tvUserName.setText((user.getName() != null ? user.getName() : "") + " " + (user.getLastName() != null ? user.getLastName() : ""));
            holder.tvUserEmail.setText(user.getEmail() != null ? user.getEmail() : "N/A");
            holder.tvUserRole.setText(user.getRole() != null ? user.getRole() : "N/A");

            holder.btnDeleteUser.setOnClickListener(v -> deleteListener.onUserDelete(user));
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName, tvUserEmail, tvUserRole;
        ImageButton btnDeleteUser;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvUserEmail = itemView.findViewById(R.id.tvUserEmail);
            tvUserRole = itemView.findViewById(R.id.tvUserRole);
            btnDeleteUser = itemView.findViewById(R.id.btnDeleteUser);
        }
    }
}
