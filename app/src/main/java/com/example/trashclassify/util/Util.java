package com.example.trashclassify.util;

import android.content.res.AssetManager;
import android.util.Log;

import com.example.trashclassify.model.Trash;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;


public class Util {

    private static final String TAG = "TrashUtil";

    /**
     * 读取保存在CSV文件中的垃圾分类信息
     *
     * @param manager 素材管理者
     * @return 返回一个数组包含所有垃圾
     */
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

    /**
     * 对给定数据集的垃圾按给定类别进行分类
     *
     * @param trashes 数据集
     * @param type 类别
     * @return 分类后的垃圾
     */
    public static ArrayList<Trash> classifyTrash(ArrayList<Trash> trashes, Trash.TrashType type) {
        ArrayList<Trash> result = new ArrayList<>();
        for (Trash trash : trashes) {
            if (trash.getType() == type) {
                result.add(trash);
            }
        }
        return result;
    }

    /**
     * 将给定的字符串转换为拼音字符串
     *
     * 举例：
     * 字符串 -> zifuchuan
     * 卡拉OK -> kalaOK
     *
     * @param str 原字符串
     * @return 拼音字符串
     */
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
