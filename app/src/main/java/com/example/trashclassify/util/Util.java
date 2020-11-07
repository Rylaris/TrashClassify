package com.example.trashclassify.util;

import com.example.trashclassify.model.Trash;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Util {
    public static void readCSV(String path) {
        List<Trash> trashes = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        FileInputStream fileInputStream;
        Scanner scanner;


    }

}
