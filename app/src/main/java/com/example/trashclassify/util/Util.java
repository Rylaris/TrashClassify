package com.example.trashclassify.util;

import android.content.res.AssetManager;
import android.util.Log;

import com.example.trashclassify.model.Trash;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Util {

    private static final String TAG = "Util";

    public static ArrayList<Trash> readCSV(AssetManager manager) {
        Log.d(TAG, "readCSV: Read begin");
        ArrayList<Trash> trashes = new ArrayList<>();
        Scanner scanner;
        InputStream inputStream;
        try {
            inputStream = manager.open("garbage.csv");
            scanner = new Scanner(inputStream, "UTF-8");
            while (scanner.hasNext()) {
                String sourceString = scanner.nextLine();
                String[] sub = sourceString.split(",");
                String name = sub[0];
                int i = Integer.parseInt(sub[1]);
                Trash.TrashType type;
                if (i == Trash.TrashType.recyclable.value) {
                    type = Trash.TrashType.recyclable;
                } else if (i == Trash.TrashType.harmful.value) {
                    type = Trash.TrashType.harmful;
                } else if (i == Trash.TrashType.wet.value) {
                    type = Trash.TrashType.wet;
                } else if (i == Trash.TrashType.dry.value) {
                    type = Trash.TrashType.dry;
                } else {
                    type = Trash.TrashType.bulky;
                }
                Trash newItem = new Trash(name, type);
                trashes.add(newItem);
            }
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "readCSV: Read CSV finish");
        return trashes;
    }

}
