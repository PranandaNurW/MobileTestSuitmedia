package com.example.mobiletest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    Context context;
    public ArrayList<String> nama;
    private ArrayList<String> email;
    private ArrayList<String> image;
    private ClickUser mclickUser;

    public RecyclerAdapter(Context context, ArrayList<String> nama, ArrayList<String> email, ArrayList<String> image, ClickUser clickUser){
        this.context = context;
        this.nama = nama;
        this.email = email;
        this.image = image;
        this.mclickUser = clickUser;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_user, parent, false);
        return new ViewHolder(v, mclickUser);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvNama.setText(nama.get(position));
        holder.tvEmail.setText(email.get(position));
        Picasso.get().load(image.get(position)).transform(new CropCircleTransformation()).into(holder.ivAvatar);
    }

    @Override
    public int getItemCount() {
        return nama.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvNama, tvEmail;
        ImageView ivAvatar;
        ClickUser nclickUser;

        public ViewHolder(@NonNull View itemView, ClickUser clickUser) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            tvNama = itemView.findViewById(R.id.tvUsername);
            tvEmail = itemView.findViewById(R.id.tvEmail);

            this.nclickUser = clickUser;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            nclickUser.OnUserClick(getAdapterPosition());
        }
    }

    public interface ClickUser {
        void OnUserClick(int position);
    }

}
