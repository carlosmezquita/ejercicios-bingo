package com.liceolapaz;

import java.util.ArrayList;

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
        for (int i = 0; i < credit/2; i++) {
            cards.add(new Card().getCardArray());
        }
        return cards;
    }

    @Override
    public void run() {

    }
}
