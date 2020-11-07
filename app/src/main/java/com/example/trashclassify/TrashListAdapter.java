package com.example.trashclassify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trashclassify.model.Trash;

import java.util.ArrayList;

public class TrashListAdapter extends ArrayAdapter<Trash> {


    private int resourceId;

    public TrashListAdapter(@NonNull Context context, int resource, ArrayList<Trash> trashes) {
        super(context, resource, trashes);
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
        }
        return view;
    }

    class ViewHolder {
        TextView trashName;
    }
}
