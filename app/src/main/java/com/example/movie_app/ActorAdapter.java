package com.example.movie_app;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ActorAdapter extends RecyclerView.Adapter<ActorAdapter.ActorViewHolder> {

    private List<String> actorsList;

    public ActorAdapter(List<String> actorsList) {
        this.actorsList = actorsList;
    }

    @NonNull
    @Override
    public ActorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actor, parent, false);
        return new ActorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorViewHolder holder, int position) {
        String actorName = actorsList.get(position);
        holder.actorNameTextView.setText(actorName);
    }

    @Override
    public int getItemCount() {
        return actorsList.size();
    }

    public static class ActorViewHolder extends RecyclerView.ViewHolder {
        private TextView actorNameTextView;

        public ActorViewHolder(@NonNull View itemView) {
            super(itemView);
            actorNameTextView = itemView.findViewById(R.id.actorNameTextView);
        }
    }
}
