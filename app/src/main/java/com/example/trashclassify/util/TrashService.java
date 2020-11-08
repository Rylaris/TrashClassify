package com.example.trashclassify.util;

import android.content.res.AssetManager;
import android.util.Log;

import com.example.trashclassify.model.Trash;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TrashService {
    private ArrayList<Trash> trashes;
    private static final String TAG = "TrashService";
    private static final String CSVPath = "garbage.csv";

    public ArrayList<Trash> readCSV(AssetManager manager) {
        Log.d(TAG, "readCSV: Read CSV begin");
        trashes = new ArrayList<>();
        Scanner scanner;
        InputStream inputStream;
        try {
            inputStream = manager.open(CSVPath);
            scanner = new Scanner(inputStream, "UTF-8");
            while (scanner.hasNext()) {
                String sourceString = scanner.nextLine();
                String[] sub = sourceString.split(",");
                String name = sub[0];
                int i;
                Trash.TrashType type;
                try {
                    i = Integer.parseInt(sub[1]);
                } catch (NumberFormatException e) {
                    Log.d(TAG, "readCSV: Unknown trash: " + sourceString);
                    i = 0;
                }
                if (i == Trash.TrashType.recyclable.value) {
                    type = Trash.TrashType.recyclable;
                } else if (i == Trash.TrashType.harmful.value) {
                    type = Trash.TrashType.harmful;
                } else if (i == Trash.TrashType.wet.value) {
                    type = Trash.TrashType.wet;
                } else if (i == Trash.TrashType.dry.value) {
                    type = Trash.TrashType.dry;
                } else if (i == Trash.TrashType.bulky.value){
                    type = Trash.TrashType.bulky;
                } else {
                    type = Trash.TrashType.unknown;
                }
                Trash newItem = new Trash(name, type);
                trashes.add(newItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "readCSV: Read CSV finish");
        return trashes;
    }

    public ArrayList<Trash> classifyTrash(ArrayList<Trash> dataSet, Trash.TrashType type) {
        ArrayList<Trash> result = new ArrayList<>();
        for (Trash trash : dataSet) {
            if (trash.getType() == type) {
                result.add(trash);
            }
        }
        return result;
    }

    public ArrayList<Trash> search(ArrayList<Trash> dataSet, String keywords) {
        ArrayList<Trash> result = new ArrayList<>();
        char[] chars = keywords.toLowerCase().toCharArray();
        StringBuilder temp = new StringBuilder("^\\w*");
        for (char ch : chars) {
            temp.append(ch);
            temp.append("\\w*");
        }
        temp.append("$");
        String pattern = temp.toString();
        for (Trash trash : dataSet) {
            if (Pattern.matches(pattern, trash.getName().toLowerCase()) ||
                    Pattern.matches(pattern, Util.ChineseToPinYin(trash.getName()).toLowerCase())) {
                result.add(trash);
            }
        }
        return result;
    }
}
