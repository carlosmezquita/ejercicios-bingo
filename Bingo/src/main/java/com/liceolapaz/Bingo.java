package com.liceolapaz;

import java.util.ArrayList;

public class Bingo implements Runnable {

    private ArrayList<Integer> totalNumbers;
    private ArrayList<Integer> numbers = new ArrayList<Integer>();
    private final int price = 2;

    public Bingo() {
        this.totalNumbers = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            totalNumbers.add(i);
        }
    }

    @Override
    public void run() {

    }
}
