package com.example.demo;

import java.io.*;

public class IOExample {


    public static void main(String[] args) throws IOException {

        FileOutputStream fos = null;
        String str = "Updated text";
        FileInputStream fis = null;
        InputStreamReader isr = null;

        // запись в файл
        try {
            fos = new FileOutputStream("src/main/resources/text.txt");
            fos.write(str.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
           fos.close();
        }

        // чтение из файла
        try {
            fis = new FileInputStream("src/main/resources/text.txt");
            isr = new InputStreamReader(fis);
            while(isr.read()!=-1) {
                char ch = (char)isr.read();
                System.out.println(ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
