package com.liceolapaz;

import java.text.Format;
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
    private int nBola = 1;
    private final int prize;
    private final String separator = "============================================\n";

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

        //Para que los jugadores se impriman con tiempo
        try {
            sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //Log
        String msg = separator;
        msg += "EMPEZANDO BINGO\n";
        msg += separator;
        System.out.println(msg);
        Log.printLog(msg);

        //Se ejecuta hasta que alguien canta bingo
        while (!bingo) {

            if (totalNumbers.isEmpty()) {
                System.exit(2);
            }

            //Genera un número entre 1 y el total de números restantes para devolver esa posición sin repetir números
            int number = genNumber(0, totalNumbers.size() - 1);
            actualBall = totalNumbers.get(number);
            bola = true;

            msg = nBola + ". BOLA: " + actualBall;
            System.out.println(msg);
            Log.printLog(msg);
            nBola++;

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
        checkNumber(card, name);
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
    private void checkNumber(Card card, String name) {

        if (card.getCrossed().contains(actualBall)){
            return;
        }

        int[][] cardArray = card.getCardArray();

        //Se ejecuta por cada línea
        for (int[] row : cardArray) {

            //Comprueba si alguno de los números coincide con la bola que acaba de salir

            if (Arrays.stream(row).anyMatch(n -> n == actualBall)){

            //Aumenta las coincidencias si coincide
             card.addCoincidence(actualBall);

            final String msg = name + " tacha el " + actualBall + ", " + card.getCrossed().size() + " tachadas en el cartón";
            System.out.println(msg);
            Log.printLog(msg);
            return;
            }
        }
    }

    //Declara el ganador de la líena
    private synchronized void madeLine(String name) {

        String msg = "[" + name + "]: ¡LÍNEA!";
        System.out.println(msg);
        Log.printLog(msg);

        if (!line) {
            line = true;
            msg = name + " se ha llevado la línea";
            System.out.println(msg);
            Log.printLog(msg);

        }
    }

    //Declara el ganador del bingo
    public synchronized void madeBingo(String name, Card card){

        StringBuilder msg = new StringBuilder("[" + name + "]: ¡BINGO!");
        System.out.println(msg);
        Log.printLog(msg.toString());

        if (!bingo) {
            bingo = true;
            msg = new StringBuilder("El ganador del bingo ha sido " + name);
            msg.append("\nPremio ganado: ").append(prize).append("€\n\n");
            msg.append(separator);
            msg.append("CARTÓN GANADOR\n");
            msg.append(separator).append("\n");
            msg.append(card.toString()).append("\n\n");
            msg.append(separator);
            msg.append("NÚMEROS SALIDOS\n");
            msg.append(separator);
            System.out.println(msg);
            Log.printLog(msg.toString());

            msg = new StringBuilder("|| ");
            int i = 0;

            for (int numero : gottenNumbers) {

                if (i == 15) {
                    msg.append("\n|| ");
                    i = 0;
                }
                msg.append(String.format("%02d", numero)).append(" || ");
                i++;
            }
            msg.append("\n\n").append(separator);
            msg.append("FIN DE BINGO\n");
            msg.append(separator);
            System.out.println(msg);
            Log.printLog(msg.toString());
            System.exit(0);

        }
    }

    //Generador de número aleatorio
    private int genNumber(int min, int max){
        return new Random().nextInt(min, max + 1);
    }
}
