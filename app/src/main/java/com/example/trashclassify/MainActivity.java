package com.example.trashclassify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trashclassify.model.Trash;
import com.example.trashclassify.util.TrashService;
import com.example.trashclassify.util.Util;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener, AdapterView.OnItemSelectedListener {

    TrashService service;
    TrashListAdapter adapter;

    SearchView searchView;
    ListView trashList;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        service = new TrashService();
        adapter = new TrashListAdapter(MainActivity.this, R.layout.trash_item, service.readCSV(getAssets()), service);
        trashList = findViewById(R.id.trash_list);
        trashList.setOnItemClickListener(this);
        trashList.setAdapter(adapter);

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);

        spinner = findViewById(R.id.trash_spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.trash_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Trash trash = (Trash) adapter.getItem(position);
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("这是什么垃圾");
        dialog.setMessage(trash.toString());
        dialog.show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (searchView != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
            }
            searchView.clearFocus();
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            adapter.getFilter().filter("");
        } else {
            adapter.getFilter().filter(newText);
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.about) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String[] array = getResources().getStringArray(R.array.trash_array);
        Trash.TrashType type = Trash.TrashType.unknown;
        switch (array[position]) {
            case "全部垃圾":
                adapter.setTrashes(Util.readCSV(getAssets()));
                adapter.getFilter().filter(searchView.getQuery());
                return;
            case "可回收垃圾":
                type = Trash.TrashType.recyclable;
                break;
            case "有害垃圾":
                type = Trash.TrashType.harmful;
                break;
            case "湿垃圾":
                type = Trash.TrashType.wet;
                break;
            case "干垃圾":
                type = Trash.TrashType.dry;
                break;
            case "大件垃圾":
                type = Trash.TrashType.bulky;
                break;
        }
        adapter.setTrashes(Util.classifyTrash(Util.readCSV(getAssets()), type));
        adapter.getFilter().filter(searchView.getQuery());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
