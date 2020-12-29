package com.example.movietrailer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movietrailer.R;
import com.example.movietrailer.model.Cast;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {
    private List<Cast> castList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cast cast = castList.get(position);
        holder.tv_cast_role.setText(cast.getCharacter());
        holder.tv_cast_name.setText(cast.getOriginalName());
        if (cast.getProfilePath() == null){
            Glide.with(holder.itemView.getContext())
                    .load("https://www.bathcollege.ac.uk/wp-content/uploads/2018/05/default-avatar.png")
                    .centerCrop()
                    .into(holder.circleImageView);
        }
        else {
            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500" + cast.getProfilePath())
                    .centerCrop()
                    .into(holder.circleImageView);
        }

    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView tv_cast_role, tv_cast_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.img_cast);
            tv_cast_name = itemView.findViewById(R.id.tv_cast_name);
            tv_cast_role = itemView.findViewById(R.id.tv_cast_role);
        }
    }

    public void setData(List<Cast> listData){
        this.castList = listData;
        notifyDataSetChanged();
    }
}
