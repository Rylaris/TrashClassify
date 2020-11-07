package com.example.trashclassify.model;

public class Trash {
    /**
     * 1 (可回收垃圾)
     * 2 (有害垃圾)
     * 4 (湿垃圾)
     * 8 (干垃圾)
     * 16 (大件垃圾)
     */
    enum TrashType {
        recyclable(1),
        harmful(2),
        wet(4),
        dry(8),
        bulky(16);

        public final int value;

        private TrashType(int value) {
            this.value = value;
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
}
