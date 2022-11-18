package com.liceolapaz;

import java.util.ArrayList;
import java.util.Random;

public class Player implements Runnable {

    private ArrayList<Card> cards;
    private int credit;
    private final String name;
    private final Bingo bingo;

    public Player(String name, int credit, Bingo bingo) {
        this.credit = credit;
        this.name = name;
        this.bingo = bingo;
        this.cards = getCards();
    }

    //Calcula cuantos cartones puede comprar y compra de 1 a el máximo número de cartones que se pueda permitir
    private ArrayList<Card> getCards() {

        //ArrayList de todos los cartones
        ArrayList<Card> cards = new ArrayList<>();

        //Elige cuantos cartones compra en base el precio
        int cardsBought = new Random().nextInt(1, credit/bingo.getPrice() + 1);

        //Compra los cartones
        for (int i = 0; i < cardsBought; i++) {
            credit -= bingo.getPrice();
            cards.add(new Card());
        }
        return cards;
    }

    @Override
    public void run() {
        //Juega hasta que alguien canta bingo
        while (!bingo.isBingo()){

            //Si tiene 25 tachados es bingo
            for (Card card : cards) {
                if (card.getCoincidences() == 25) {

                    //Canta bingo aunque no gane
                    bingo.madeBingo(name, card);
                }

                bingo.play(card, name);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
