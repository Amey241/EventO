package com.example.evento;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VendorAdapter extends RecyclerView.Adapter<VendorAdapter.ViewHolder> {

    private Context context;
    private List<VendorItem> vendorList;
    private OnItemClickListener listener;

    public VendorAdapter(Context context, List<VendorItem> vendorList) {
        this.context = context;
        this.vendorList = vendorList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_list, parent, false);
        // Remove the thumbnail_image_card ImageView
        ImageView thumbnailImageCard = view.findViewById(R.id.thumbnail_image_card);
        ((ViewGroup) thumbnailImageCard.getParent()).removeView(thumbnailImageCard);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VendorItem vendor = vendorList.get(position);
        holder.nameTextView.setText(vendor.getVendorName());
        holder.contactInfoTextView.setText(vendor.getContactInfo());

        // Alternating row colors
        if (position % 2 == 1) {
            ViewCompat.setBackgroundTintList(holder.itemView, ColorStateList.valueOf(Color.parseColor("#003049")));
            holder.nameTextView.setTextColor(Color.parseColor("#590D22"));
            holder.contactInfoTextView.setTextColor(Color.parseColor("#590D22"));
        } else {
            ViewCompat.setBackgroundTintList(holder.itemView, ColorStateList.valueOf(Color.TRANSPARENT));
            holder.nameTextView.setTextColor(Color.BLACK);
            holder.contactInfoTextView.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return vendorList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView contactInfoTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.title);
            contactInfoTextView = itemView.findViewById(R.id.type);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(v, position);
                        }
                    }
                }
            });
        }
    }
}
