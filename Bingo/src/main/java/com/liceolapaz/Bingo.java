package com.liceolapaz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Bingo implements Runnable {

    private ArrayList<Integer> totalNumbers;
    private ArrayList<Integer> gottenNumbers = new ArrayList<Integer>();
    private final int price = 2;
    private boolean bingo = false;
    private boolean line = false;
    private int actualBall;

    public Bingo() {
        this.totalNumbers = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            totalNumbers.add(i);
        }
    }

    public boolean isBingo() {
        return bingo;
    }

    @Override
    public void run() {
        while (!bingo) {
            int number = genNumber(totalNumbers.size() - 1);
            actualBall = totalNumbers.get(number);
            totalNumbers.remove(number);
            gottenNumbers.add(actualBall);

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public boolean play(ArrayList<int[][]> cards){
        for (int[][] card : cards) {
            if (checkCard(card)) {
                //return true;
            }
        }
        return false;
    }

    private boolean checkCard(int[][] card) {
        for (int[] row : card) {
            if (Arrays.stream(row).anyMatch(n -> n == actualBall)){
             return true;
            }
        }
        return false;
    }

    private int genNumber(int max){
        return new Random().nextInt(max);
    }
}
