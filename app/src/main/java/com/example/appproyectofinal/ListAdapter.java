package com.example.appproyectofinal;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ListElement> Waifus;
    private LayoutInflater winflater;
    private Context context;
    private Activity activity;
    final public int REQUEST_CALL_PHONE = 10;
    final public int REQUEST_CAMERA = 11;

    public ListAdapter(List<ListElement> waifuList, Context context, Activity activity) {
        this.winflater = LayoutInflater.from(context);
        this.context = context;
        this.Waifus = waifuList;
        this.activity = activity;
    }

    @Override
    public int getItemCount() {
        return Waifus.size();
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = winflater.inflate(R.layout.list_element, null);
        return new ListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position) {
        holder.bindData(Waifus.get(position));
    }

    public void setItems(List<ListElement> waifus) {
        Waifus = waifus;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView name;
        Switch status;

        ViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            name = itemView.findViewById(R.id.nameTextView);
            status = itemView.findViewById(R.id.statusSwitch);
        }

        void bindData(final ListElement waifu) {
            //iconImage.setImageDrawable(waifu.getImg());
            name.setText(waifu.getName());
            status.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    int p = ActivityCompat.checkSelfPermission(context, waifu.getPermission());
                    if (p == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(context, "This permission is already given", Toast.LENGTH_SHORT).show();
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            switch (waifu.getPermission()) {
                                case Manifest.permission.CALL_PHONE:
                                    activity.requestPermissions(new String[]{waifu.getPermission()}, REQUEST_CALL_PHONE);
                                    return;
                                case Manifest.permission.CAMERA:
                                    activity.requestPermissions(new String[]{waifu.getPermission()}, REQUEST_CAMERA);
                                    return;
                            }
                        }
                    }
                }
            });
        }
    }
}
