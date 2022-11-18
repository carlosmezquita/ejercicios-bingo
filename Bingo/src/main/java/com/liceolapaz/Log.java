package com.liceolapaz;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    public static void printLog(String msg) {
        try {
            FileWriter fw = new FileWriter("Bingo.log", true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(getTimestamp() + msg+"\n");
            bw.flush();
            fw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static String getTimestamp(){
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        return "[" + df.format(new Date()) + "]: ";
    }
}
