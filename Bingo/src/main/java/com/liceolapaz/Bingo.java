package com.liceolapaz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Bingo implements Runnable {

    private final ArrayList<Integer> totalNumbers;
    private final ArrayList<Integer> gottenNumbers ;

    private final int price;
    private boolean bingo = false;
    private boolean line = false;
    private int actualBall = -1;
    private boolean bola = false;
    private final int prize;

    public Bingo(int price) {
        //Añade los 99 números del bingo a un ArrayList
        this.totalNumbers = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            totalNumbers.add(i);
        }
        this.gottenNumbers = new ArrayList<>();
        this.price = price;
        this.prize = genNumber(50, 100);
    }

    public boolean isBingo() {
        return bingo;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public void run() {
        //Se ejecuta hasta que alguien canta bingo
        while (!bingo) {

            //Genera un número entre 1 y el total de números restantes para devolver esa posición sin repetir números
            int number = genNumber(0, totalNumbers.size() - 1);
            actualBall = totalNumbers.get(number);
            bola = true;
            System.out.printf("BOLA: %d\n", actualBall);
            totalNumbers.remove(number);

            //Retira ese número del ArrayList
            gottenNumbers.add(actualBall);

            //Tiempo entre rondas
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            bola = false;
        }

    }

    //Método por el que los jugadores comprueban sus cartones
    public void play(Card card, String name){

        //Si no hay una bola nueva no ejecuta nada
        if (!bola) {
            return;
        }

        //Solo se ejecuta si aún no se ha cantado línea
        if (!line){

            //Si alguien canta línea se ejecuta
            if (checkLine(card)){

                //Bloque sincronizado para que aunque varios canten solo gane el más rápido
                madeLine(name);
            }
        }
        checkBingo(card, name);
    }



    //Divide los cartones en líneas para comprobar si tiene línea
    private boolean checkLine(Card card) {
        //Cuenta las coincidencias
        int coincidences;

        int[][] cardArray = card.getCardArray();

        //Itera por cada línea
        for (int[] row : cardArray) {

            //Reinicia las coincidencias
            coincidences = 0;

            //Itera por cada número
            for (int number : row) {

                //Si un número coincide sube las coincidencias
                if (gottenNumbers.contains(number)) {
                    coincidences++;
                }
            }

            //Si hay 5 es línea
            if (coincidences == 5) {
                return true;
            }
        }
        return false;
    }

    //Cada cartón ejecuta este método para comprobar el bingo
    private void checkBingo(Card card, String name) {

        int[][] cardArray = card.getCardArray();

        //Se ejecuta por cada línea
        for (int[] row : cardArray) {

            //Comprueba si alguno de los números coincide con la bola que acaba de salir
            if (Arrays.stream(row).anyMatch(n -> n == actualBall)){

                System.out.printf("%s tacha el %d\n", name, actualBall);

            //Aumenta las coincidencias si coincide
             card.setCoincidences(card.getCoincidences() + 1);
             return;
            }
        }
    }

    //Declara el ganador de la líena
    private synchronized void madeLine(String name) {
        System.out.printf("[%s]: ¡LÍNEA!\n", name);
        if (!line) {
            line = true;
            System.out.printf("%s se ha llevado la línea\n", name);
        }
    }

    //Declara el ganador del bingo
    public synchronized void madeBingo(String name, Card card){

        System.out.printf("[%s]: ¡BINGO!\n", name);
        if (!bingo) {
            bingo = true;
            System.out.printf("El ganador del bingo ha sigo %s\n", name);
            System.out.printf("Premio ganado: %d€\n", prize);
            System.out.println("======================");
            System.out.println("CARTÓN GANADOR");
            System.out.println("======================");
            System.out.println(card.toString());
            System.out.println("======================");
            System.out.println("NÚMEROS SALIDOS");
            for (int numero : gottenNumbers) {
                System.out.print(numero + " || ");
            }
            System.out.println();
        }
    }

    //Generador de número aleatorio
    private int genNumber(int min, int max){
        return new Random().nextInt(min, max + 1);
    }
}
