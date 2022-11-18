package com.liceolapaz;

public class Main {

    public static void main(String[] args) {
        //Card card = new Card();
        //System.out.println(card);

        Bingo bingo = new Bingo(2);
        Player ales = new Player("Ales", 2, bingo);
        Player carlos = new Player("Carlos", 2, bingo);

        new Thread(ales).start();
        new Thread(carlos).start();
        new Thread(bingo).start();
    }
}
