package com.example.trashclassify.util;

import android.content.res.AssetManager;
import android.util.Log;

import com.example.trashclassify.R;
import com.example.trashclassify.model.Trash;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Util {

    private static final String TAG = "Util";

    public static List<Trash> readCSV(AssetManager manager) {
        List<Trash> trashes = new ArrayList<>();
        Scanner scanner;
        InputStream inputStream;
        try {
            inputStream = manager.open("test.csv");
            scanner = new Scanner(inputStream, "UTF-8");
            scanner.nextLine();
            int a = 0;
            while (scanner.hasNext()) {
                String sourceString = scanner.nextLine();
                Log.d(TAG, "readCSV: Current content is " + sourceString);
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
                Log.d(TAG, "readCSV: new item info: " + newItem.toString());
                trashes.add(newItem);
            }
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        return trashes;
    }

}
