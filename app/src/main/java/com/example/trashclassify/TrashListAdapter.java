package com.example.trashclassify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.trashclassify.model.Trash;

import java.util.ArrayList;

public class TrashListAdapter extends ArrayAdapter<Trash> {


    private int resourceId;
    private Context context;

    public TrashListAdapter(@NonNull Context context, int resource, ArrayList<Trash> trashes) {
        super(context, resource, trashes);
        this.context = context;
        this.resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Trash trash = getItem(position);
        ViewHolder viewHolder;
        View view ;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.trashName = view.findViewById(R.id.trash_name);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        if (trash != null) {
            viewHolder.trashName.setText(trash.getName());
            switch (trash.getType()) {
                case recyclable:
                    viewHolder.trashName.setTextColor(ContextCompat.getColor(context, R.color.recyclable));
                    break;
                case harmful:
                    viewHolder.trashName.setTextColor(ContextCompat.getColor(context, R.color.harmful));
                    break;
                case dry:
                    viewHolder.trashName.setTextColor(ContextCompat.getColor(context, R.color.dry));
                    break;
                case wet:
                    viewHolder.trashName.setTextColor(ContextCompat.getColor(context, R.color.wet));
                    break;
                case bulky:
                    viewHolder.trashName.setTextColor(ContextCompat.getColor(context, R.color.bulky));
                    break;
            }
        }
        return view;
    }

    class ViewHolder {
        TextView trashName;
    }
}
