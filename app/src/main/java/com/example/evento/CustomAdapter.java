package com.example.evento;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private Context context;
    private List<RowItem> rowItems;
    private OnItemClickListener listener;

    public CustomAdapter(Context context, List<RowItem> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RowItem rowItem = rowItems.get(position);

        holder.title.setText(rowItem.getName());
        holder.type.setText(rowItem.getType());
        holder.pass.setText(rowItem.getPass());

        // Alternate row background color
        if (position % 2 == 0) {
            ViewCompat.setBackgroundTintList(holder.itemView, ColorStateList.valueOf(Color.parseColor("#FF00FF00")));
            holder.title.setTextColor(Color.parseColor("#FFFFFF"));
            holder.type.setTextColor(Color.parseColor("#000000"));
            holder.pass.setTextColor(Color.parseColor("#000000"));
        } else {
            ViewCompat.setBackgroundTintList(holder.itemView, ColorStateList.valueOf(Color.parseColor("#003049")));
            holder.title.setTextColor(Color.WHITE);
            holder.type.setTextColor(Color.WHITE);
            holder.pass.setTextColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView type;
        TextView pass;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            type = itemView.findViewById(R.id.type);
            pass = itemView.findViewById(R.id.pass);

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


