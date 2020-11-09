package com.example.trashclassify.util;

import android.content.res.AssetManager;
import android.util.Log;

import com.example.trashclassify.model.Trash;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.*;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Util {

    private static final String TAG = "TrashUtil";

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

    public static ArrayList<Trash> classifyTrash(ArrayList<Trash> trashes, Trash.TrashType type) {
        ArrayList<Trash> result = new ArrayList<>();
        for (Trash trash : trashes) {
            if (trash.getType() == type) {
                result.add(trash);
            }
        }
        return result;
    }

    public static String ChineseToPinYin(String str) {
        HanyuPinyinOutputFormat outputF = new HanyuPinyinOutputFormat();
        outputF.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        outputF.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        char[] chars = str.toCharArray();
        StringBuilder result = new StringBuilder();
        for (char ch : chars) {
            String[] strs = new String[0];
            try {
                strs = PinyinHelper.toHanyuPinyinStringArray(ch, outputF);
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                badHanyuPinyinOutputFormatCombination.printStackTrace();
            }
            if (strs == null) {
                result.append(ch);
            } else {
                result.append(strs[0]);
            }
        }
        return result.toString();
    }

}
