package com.example.exempleminim2laura;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exempleminim2laura.Models.Repos;

import java.util.List;


public class RecyclerViewAdapt extends RecyclerView.Adapter<RecyclerViewAdapt.ViewHolder> {

    private List<Repos> repos;
    Context context;

    public RecyclerViewAdapt(Context context, List<Repos> repos) {
        this.repos = repos;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView lenguage;

        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.RepoName);
            lenguage = v.findViewById(R.id.Lenguage);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(context);
        View v =
                inflater.inflate(R.layout.reposlist, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Repos repo = repos.get(position);
        holder.name.setText(repo.getName());
        holder.lenguage.setText(repo.getLenguage());
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

}
