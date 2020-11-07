package com.example.trashclassify.model;

import androidx.annotation.NonNull;

public class Trash {
    /**
     * 1 (可回收垃圾)
     * 2 (有害垃圾)
     * 4 (湿垃圾)
     * 8 (干垃圾)
     * 16 (大件垃圾)
     */
    public enum TrashType {
        recyclable(1),
        harmful(2),
        wet(4),
        dry(8),
        bulky(16);

        public final int value;

        private TrashType(int value) {
            this.value = value;
        }

        @NonNull
        @Override
        public String toString() {
            String str = "未知垃圾";
            switch (value) {
                case 1:
                    str = "可回收垃圾";
                    break;
                case 2:
                    str = "有害垃圾";
                    break;
                case 4:
                    str = "湿垃圾";
                    break;
                case 8:
                    str = "干垃圾";
                    break;
                case 16:
                    str = "大件垃圾";
                    break;
            }
            return str;
        }
    }

    public String name;
    public TrashType type;

    public Trash() {}

    public Trash(String name, TrashType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TrashType getType() {
        return type;
    }

    public void setType(TrashType type) {
        this.type = type;
    }

    @NonNull
    @Override
    public String toString() {
        return name + "是" + type.toString();
    }
}
