package com.liceolapaz;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class SumNumbers implements Callable<Integer> {
    private final ArrayList<Integer> nums;
    private int sum;

    public SumNumbers(ArrayList<Integer> nums) {
        this.nums = nums;
    }

    @Override
    public Integer call() {
        for(int num : nums){
            sum += num;
        }
        System.out.println("The sum of prime numbers = "+sum);
        return sum;
    }
}
