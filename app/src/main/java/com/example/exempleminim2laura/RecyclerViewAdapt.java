package com.example.exempleminim2laura;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exempleminim2laura.Models.User;
import com.squareup.picasso.Picasso;

import java.util.List;


public class RecyclerViewAdapt extends RecyclerView.Adapter<RecyclerViewAdapt.MyViewHolder> {

    private List<User> listaUsers;
    private Context context;

    public RecyclerViewAdapt(Context ct, List<User> users) {
        context = ct;
        listaUsers = users;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.followerslist, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.usuario.setText(listaUsers.get(position).getLogin());
        Picasso.with(context).load(listaUsers.get(position).getAvatar_url()).into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return listaUsers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView avatar;
        TextView usuario;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.imageFollower);
            usuario = itemView.findViewById(R.id.FollowerName);
        }
    }
}
