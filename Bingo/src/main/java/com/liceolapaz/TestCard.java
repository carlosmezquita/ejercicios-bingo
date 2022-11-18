package com.liceolapaz;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestCard {
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            printLog("\n"+getTimestamp()+"  -  ID: "+i);
            Card card = new Card();
            printLog(card.toString());
        }
    }
    public static void printLog(String msg) {
        try {
            FileWriter fw = new FileWriter("test_card.log", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(msg+"\n");
            bw.flush();
            fw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static String getTimestamp(){
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss:SS");
        return "[" + df.format(new Date()) + "]";
    }
}
