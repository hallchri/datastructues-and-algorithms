package com.company;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class FileInput {
    public static String readFile(String path) throws UnsupportedEncodingException {
        String text = "";
        StringBuilder s = new StringBuilder(text);
        int value = 0;
        char c = 0;

        try {
            FileInputStream fInput = new FileInputStream(path);
            int tempChar;

            while ((tempChar = fInput.read()) != -1) {
                text += String.valueOf((char) tempChar).toLowerCase();
                //Loopa genom textens längd, tar därifrån karaktären från textens plats (indexen)
                for(int i = 0; i < text.length(); i++) {
                    c = text.charAt(i);
                    value = c;
                }
                //Checkar och filtrerar ut alla onödiga tecken (Enligt tecknens decimala tal i ISO-8859-4 format)
                if(value == 32 || value >= 65 && value <= 90 || value >= 97 && value <= 122 || value >= 161 && value <= 163 || value >= 165 && value <= 166 || value >= 169 && value <= 172 || value == 174 || value == 177 || value == 179 || value >= 181 && value <= 183 || value >= 185 && value <= 214 || value >= 216 && value <= 246 || value >= 248 && value <= 254) {
                    s.append(c); // Vi bygger upp vår sträng med de filtrerade karaktärerna med hjälp av klassen StringBuilder
                }
            }
        }
        catch (FileNotFoundException ex) {
            System.out.println("Kunde inte hitta filen: " + ex.getMessage());
        }
        catch (IOException ex) {
            System.out.println("IOException: " + ex.getMessage());
        }
        return s.toString();
    }
}
