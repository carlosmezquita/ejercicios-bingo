package com.liceolapaz;

import java.util.ArrayList;
import java.util.Random;

public class Player implements Runnable {

    private ArrayList<int[][]> cards;
    private int credit;
    private String name;
    private Bingo bingo;

    public Player(ArrayList<Card> card, String name, int credit, Bingo bingo) {
        this.cards = getCard();
        this.credit = credit;
        this.name = name;
        this.bingo = bingo;
    }

    private ArrayList<int[][]> getCard() {
        ArrayList<int[][]> cards = new ArrayList<>();
        int cardsBought = new Random().nextInt(1, credit/2 + 1);
        for (int i = 0; i < cardsBought; i++) {
            cards.add(new Card().getCardArray());
        }
        return cards;
    }

    @Override
    public void run() {
        while (!bingo.isBingo()){
            if(bingo.play(cards)){

            }
        }

    }
}
