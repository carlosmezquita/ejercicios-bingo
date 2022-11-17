package com.liceolapaz;

import java.util.*;

public class Card {
    private static final int ROWS = 3;
    private static final int COLUMNS = 9;
    private static final int NUMS_PER_ROW = 5;
    private int[][] cardArray;

    public Card() {
        this.cardArray = new int[ROWS][COLUMNS];
        generateCardValues();
    }

    /*              Método generación valores del cartón del Bingo
    *
    *   El programa genera una lista con las columnas que tiene cada fila y después
    *   los mezcla. Los 5 primeros valores son los correspondientes a los índices de
    *   las columnas que serán ocupadas. Luego genera un valor correspondiente a la
    *   decena de la columna en la que se encuentra y por último ordena la respectiva columna.
    *
    * */
    private void generateCardValues(){
        List<Integer> rowsNums = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        //Hacer una lista del tamaño de las columnas
        for (int j = 0; j < COLUMNS; j++) {
            rowsNums.add(j);
        }

        for (int i = 0; i < ROWS; i++) {
            //Mezclar el orden de la lista
            Collections.shuffle(rowsNums);
            // Rellenar cada fila con 5 valores
            for (int j = 0; j < NUMS_PER_ROW; j++) {
                int randomIndex = rowsNums.get(j);

                int randomValue = getRandomForColumn(randomIndex);
                //Comprobar que el número aleatorio no sea repetido
                while(values.contains(randomValue)){
                    randomValue = getRandomForColumn(randomIndex);
                }
                values.add(randomValue);
                cardArray[i][randomIndex] = randomValue;
            }
        }
        //Ordenar cada una de las columnas
        List<Integer> emptyColumns = new ArrayList<>();
        List<Integer> fullColumns = new ArrayList<>();
        for (int i = 0; i < COLUMNS; i++) {
            List<Integer> cellsRow = new ArrayList<>();
            for (int j = 0; j < ROWS; j++) {
                if (cardArray[j][i] != 0){
                    cellsRow.add(cardArray[j][i]);
                }
            }
            Collections.sort(cellsRow);
            int k=0;
            for (int j = 0; j < ROWS; j++) {
                if (cardArray[j][i] != 0){
                    cardArray[j][i] = cellsRow.get(k);
                    k++;
                }
            }
            //Si hay alguna columna vacía almacenarla
            if (k == 0){
                emptyColumns.add(i);
            }
            //Si hay alguna columna llena
            if (k == 3){
                fullColumns.add(i);
            }
        }
        for (int i = 0; i < emptyColumns.size(); i++) {
            int emptyColumn = emptyColumns.get(i);
            int fullColumn = fullColumns.get(i);
            int randomNum = getRandom(1, ROWS+1);
            //Primero quitamos de la columna completa
            cardArray[randomNum][fullColumn] = 0;
            //Luego colocamos un número aleatorio en la columna vacía
            cardArray[randomNum][emptyColumn] = getRandomForColumn(emptyColumn);
        }
    }
    private int getRandomForColumn(int index){
        int min = index == 0 ? 1 : index*10;
        int max = index != 8 ? index*10 + 10 : index*10 + 20;
        return getRandom(min, max);
    }
    private int getRandom(int min, int max){
        return (int) (Math.random() * (max - min) + min);
    }
    public int[][] getCardArray() {
        return cardArray;
    }

    @Override
    public String toString() {
        StringBuilder layout = new StringBuilder();
        for (int[] ints : cardArray) {
            StringBuilder row = new StringBuilder();
            for (int anInt : ints) {
                row.append(" [").append(anInt).append("]");
            }
            layout.append("\n").append(row);
        }
        return "Card: " +
                layout;
    }

    @Override
    public boolean equals(Object object) {
        //TODO: El método equals
        return false;
    }
}
