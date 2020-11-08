package com.example.trashclassify;

import com.example.trashclassify.model.Trash;
import com.example.trashclassify.util.Util;

import java.util.regex.Pattern;

public class MainTest {
    public static void main(String[] args) {
        char[] chars = "帽".toLowerCase().toCharArray();
        StringBuilder temp = new StringBuilder("^");
        for (char ch : chars) {
            temp.append(ch);
            temp.append("\\w*");
        }
        temp.append("$");
        String pattern = temp.toString();
        Trash test = new Trash();
        test.setName("草帽");
        test.setType(Trash.TrashType.dry);
        if (Pattern.matches(pattern, test.getName().toLowerCase()) ||
                Pattern.matches(pattern, Util.ChineseToPinYin(test.getName()).toLowerCase())) {
            System.out.print(true);
        }
    }
}
