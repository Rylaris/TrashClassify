package com.example.trashclassify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.trashclassify.model.Trash;
import com.example.trashclassify.util.Util;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ArrayList<Trash> trashes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        trashes = Util.readCSV(getAssets());
        TrashListAdapter adapter = new TrashListAdapter(MainActivity.this, R.layout.trash_item, trashes);
        ListView trashList = findViewById(R.id.trash_list);
        trashList.setOnItemClickListener(this);
        trashList.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Trash trash = trashes.get(position);
        Toast.makeText(this, trash.toString(), Toast.LENGTH_LONG).show();
    }
}
