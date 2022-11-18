package com.liceolapaz;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class Player implements Runnable {

    private final ArrayList<Card> cards;
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

        StringBuilder msg = new StringBuilder ("JUGADOR ").append( name.toUpperCase(Locale.ROOT)).append("\n");
        for (Card card : cards) {
            msg.append(card.toString());
        }
        System.out.println(msg);
        Log.printLog(msg.toString());


        //Juega hasta que alguien canta bingo
        while (!bingo.isBingo()){

            //Comprueba cada tarjeta
            for (Card card : cards) {

                //Si tiene 25 tachados es bingo

                try {
                    if (card.getCrossed().size() == 15) {

                        //Canta bingo aunque no gane
                        bingo.madeBingo(name, card);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }


                bingo.play(card, name);
            }

        }

    }
}
