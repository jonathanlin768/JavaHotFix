package com.windypath;

public class MyClass {
    private static final int[] numbers = {1, 2, 3, 4};

    public static int getNumber() {
        int index = getIndex();
        if (index >= 0 && index < numbers.length) {
            return numbers[index];
        }
        return -1; // 或者抛出异常
    }

    private static int getIndex() {
        return 0;
    }
}