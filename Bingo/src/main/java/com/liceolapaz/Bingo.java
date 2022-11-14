package com.liceolapaz;

import java.util.ArrayList;
import java.util.Random;

public class Bingo implements Runnable {

    private ArrayList<Integer> totalNumbers;
    private ArrayList<Integer> numbers = new ArrayList<Integer>();
    private final int price = 2;
    private boolean bingo = false;
    private boolean line = false;

    public Bingo() {
        this.totalNumbers = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            totalNumbers.add(i);
        }
    }

    @Override
    public void run() {
        while (!bingo) {
            int number = totalNumbers.get(genNumber(totalNumbers.size() - 1));
            numbers.add(number);
        }

    }

    private int genNumber(int max){
        return new Random().nextInt(max);
    }
}
