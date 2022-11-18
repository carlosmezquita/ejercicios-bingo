package com.liceolapaz;

import java.util.ArrayList;
import java.util.Random;

public class Player implements Runnable {

    private ArrayList<int[][]> cards;
    private int credit;
    private final String name;
    private final Bingo bingo;
    private int numersMath = 0;

    public Player(String name, int credit, Bingo bingo) {
        this.cards = getCard();
        this.credit = credit;
        this.name = name;
        this.bingo = bingo;
    }

    //Calcula cuantos cartones puede comprar y compra de 1 a el máximo número de cartones que se pueda permitir
    private ArrayList<int[][]> getCard() {

        //ArrayList de todos los cartones
        ArrayList<int[][]> cards = new ArrayList<>();

        //Elige cuantos cartones compra en base el precio
        int cardsBought = new Random().nextInt(1, credit/bingo.getPrice() + 1);

        //Compra los cartones
        for (int i = 0; i < cardsBought; i++) {
            credit -= bingo.getPrice();
            cards.add(new Card().getCardArray());
        }
        return cards;
    }

    @Override
    public void run() {
        //Juega hasta que alguien canta bingo
        while (!bingo.isBingo()){

            //Si tiene 25 tachados es bingo
            if (numersMath == 25) {

                //Canta bingo aunque no gane
                bingo.toBingo(name);
            }
            if(bingo.play(cards, name)){
                numersMath++;
            }
        }

    }
}
