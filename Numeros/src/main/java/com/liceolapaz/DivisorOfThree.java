package com.liceolapaz;

public class DivisorOfThree implements Runnable {
    private final int sum;

    public DivisorOfThree(int sum) {
        this.sum = sum;
    }

    @Override
    public void run() {
        System.out.println("Is the sum of prime numbers a divisor of three? "+ (sum % 3 == 0));
    }
}
