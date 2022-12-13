package com.liceolapaz;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ArrayList<Integer> numbers = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter number at position ["+(i+1)+"]: ");
            numbers.add(scanner.nextInt());
        }

        System.out.println("Numbers added: "+numbers);


        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<ArrayList<Integer>> primeNumbers = executor.submit(new PrimeNumbers(numbers));
        Future<Integer> sum = executor.submit(new SumNumbers(primeNumbers.get()));
        new Thread(new DivisorOfThree(sum.get())).start();
        executor.shutdown();
    }
}
