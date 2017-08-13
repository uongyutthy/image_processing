package com.imageprocessing.utility;

public class KeyValueComboBox {

    private final int key;
    private final String value;

    public KeyValueComboBox(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public String toString() {
        return value;
    }

}
