package com.liceolapaz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Bingo implements Runnable {

    private ArrayList<Integer> totalNumbers;
    private ArrayList<Integer> gottenNumbers ;

    private final int price = 2;
    private boolean bingo = false;
    private boolean line = false;
    private int actualBall;
    private boolean bola = false;

    public Bingo() {
        //Añade los 99 números del bingo a un ArrayList
        this.totalNumbers = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            totalNumbers.add(i);
        }
        this.gottenNumbers = new ArrayList<Integer>();
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
            int number = genNumber(totalNumbers.size() - 1);
            actualBall = totalNumbers.get(number);
            bola = true;
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
    public boolean play(ArrayList<int[][]> cards, String name){

        //Si no hay una bola nueva no ejecuta nada
        if (!bola) {
            return false;
        }

        //Hace una comprobación por cartón
        for (int[][] card : cards) {

            //Solo se ejecuta si aún no se ha cantado línea
            if (!line){

                //Si alguien canta línea se ejecuta
                if (checkLine(card)){


                    //Bloque sincronizado para que aunque varios canten solo gane el más rápido
                    synchronized (this){
                        System.out.printf("[%s]: ¡LÍNEA!\n");
                         if (!line) {
                             line = true;
                             System.out.printf("%s se ha llevado la línea\n", name);
                         }
                    }
                }
            }
            if (checkBingo(card)) {
                return true;
            }
        }
        return false;
    }

    //Divide los cartones en líneas para comprobar si tiene línea
    private boolean checkLine(int[][] card) {
        //Cuenta las coincidencias
        int coincidences = 0;

        //Itera por cada línea
        for (int[] row : card) {

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
    private boolean checkBingo(int[][] card) {

        //Se ejecuta por cada línea
        for (int[] row : card) {

            //Comprueba si alguno de los números coincide con la bola que acaba de salir
            if (Arrays.stream(row).anyMatch(n -> n == actualBall)){
            //Devuelve true si un número coincide
             return true;
            }
        }
        return false;
    }

    //Declara el ganador
    public void toBingo(String name){
        System.out.printf("[%s]: ¡BINGO!\n", name);

        synchronized(this){
            if (!bingo) {
                bingo = true;
                System.out.printf("El ganador del bingo ha sigo %s\n", name);
            }
        }

    }

    //Generador de número aleatorio
    private int genNumber(int max){
        return new Random().nextInt(max);
    }
}
