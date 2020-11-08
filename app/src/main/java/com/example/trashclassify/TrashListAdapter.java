package com.example.trashclassify;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.trashclassify.model.Trash;
import com.example.trashclassify.util.TrashService;

import java.util.ArrayList;

public class TrashListAdapter extends BaseAdapter implements Filterable {


    private int resourceId;
    private Context context;
    private Filter filter;
    private ArrayList<Trash> trashes;

    public ArrayList<Trash> getTrashes() {
        return trashes;
    }

    public void setTrashes(ArrayList<Trash> trashes) {
        this.trashes = trashes;
        this.tempTrashes = trashes;
        notifyDataSetChanged();
    }

    public ArrayList<Trash> getTempTrashes() {
        return tempTrashes;
    }

    public void setTempTrashes(ArrayList<Trash> tempTrashes) {
        this.tempTrashes = tempTrashes;
    }

    private ArrayList<Trash> tempTrashes;
    private TrashService service;
    private final static String TAG = "TrashListAdapter";

    public TrashListAdapter(@NonNull Context context, int resource, ArrayList<Trash> trashes, TrashService service) {
        this.context = context;
        this.resourceId = resource;
        this.trashes = trashes;
        this.service = service;
        this.tempTrashes = trashes;
    }

    @Override
    public int getCount() {
        return trashes == null ? 0 : trashes.size();
    }

    @Override
    public Object getItem(int position) {
        return trashes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Trash trash = (Trash) getItem(position);
        ViewHolder viewHolder;
        View view ;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(resourceId, parent, false);
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

    @Override
    public Filter getFilter() {
        if (null == filter) {
            filter = new MyFilter();
        }
        return filter;
    }

    class MyFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            ArrayList<Trash> newValues;
            String filterString = constraint.toString().trim()
                    .toLowerCase();
            Log.d(TAG, "performFiltering: FilterString: " + filterString);
            if (TextUtils.isEmpty(filterString)) {
                Log.d(TAG, "performFiltering: Empty Filter!");
                newValues = tempTrashes;
            } else {
                newValues = service.search(trashes, filterString);
            }
            Log.d(TAG, "performFiltering: result: " + newValues);
            results.values = newValues;
            results.count = newValues.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            trashes = (ArrayList<Trash>) results.values;

            if (results.count > 0) {
                Log.d(TAG, "publishResults: Data Set Changed");
                notifyDataSetChanged(); // 通知数据发生了改变
            } else {
                Log.d(TAG, "publishResults: Data Set Invalidated");
                notifyDataSetInvalidated(); // 通知数据失效
            }
        }
    }
}
