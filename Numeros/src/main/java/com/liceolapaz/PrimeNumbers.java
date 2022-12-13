package com.liceolapaz;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class PrimeNumbers implements Callable<ArrayList<Integer>> {
    private final ArrayList<Integer> nums;
    private final ArrayList<Integer> primeNumbers = new ArrayList<>();

    public PrimeNumbers(ArrayList<Integer> nums) {
        this.nums = nums;
    }

    @Override
    public ArrayList<Integer> call() {
        for (int num : nums) {
            if (isPrime(num)) {
                primeNumbers.add(num);
            }
        }
        System.out.println("Prime numbers: "+primeNumbers);
        return primeNumbers;
    }

    private boolean isPrime(int n){
        int i,m;
        m=n/2;
        if(n==0||n==1){
            return false;
        }else{
            for(i=2;i<=m;i++){
                if(n%i==0){
                    return false;
                }
            }
            return true;
        }
    }
}
