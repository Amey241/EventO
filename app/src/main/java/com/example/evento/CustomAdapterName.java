package com.example.evento;

import static com.example.evento.LoginActivity.loggedInUserEmail;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CustomAdapterName extends BaseAdapter {
    RowItemN row_pos;
    Context context;
    List<RowItemN> rowItems;
    static String phoneNumber = SharedPreference.get("phone");
    CustomAdapterName(Context context, List<RowItemN> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int i) {
        return rowItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return rowItems.indexOf(getItem(i));
    }

    private class Holder {
        TextView cu_t1,c_de,c_ve,c_dat,c_exp,c_up;
        Button btnSendEmail;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        Holder holder;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_new_list, null);
            holder = new Holder();
            if (i % 2 == 1) {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }
            holder.cu_t1 = (TextView) convertView.findViewById(R.id.cu_t1);
            holder.c_de = (TextView) convertView.findViewById(R.id.cu_des);
            holder.c_ve = (TextView) convertView.findViewById(R.id.cu_v1);
            holder.c_dat = (TextView) convertView.findViewById(R.id.cu_date);
            holder.c_exp = (TextView) convertView.findViewById(R.id.cu_exp);
            holder.c_up = (TextView) convertView.findViewById(R.id.cu_create);
            holder.btnSendEmail = convertView.findViewById(R.id.invite_button);

            // Inside getView() method of CustomAdapterName class

            holder.btnSendEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EmailSender.setNameVenue(rowItems.get(i).getName(), rowItems.get(i).getVenue());
                    EmailSender.sendEmail(context,"INTERESTED", loggedInUserEmail);
                    holder.btnSendEmail.setEnabled(false);
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        row_pos = rowItems.get(i);

        holder.cu_t1.setText(row_pos.getName());
        holder.c_de.setText(row_pos.getDescr());
        holder.c_ve.setText(row_pos.getVenue());
        holder.c_dat.setText(row_pos.getDate());
        holder.c_exp.setText(row_pos.getExp());
        holder.c_up.setText(row_pos.getUpby());
        return convertView;
    }
}