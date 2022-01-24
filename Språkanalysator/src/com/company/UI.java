package com.company;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class UI {
    public UI() throws UnsupportedEncodingException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Moro! Ciao! Hallå!");
        System.out.println("Jag är en språkanalysator, hej på dig :)");
        System.out.println("Skriv in en text på ett valfritt språk så försöker jag gissa det!");
        String userInput = scanner.nextLine();
        Language unknown = new Language("unknown", userInput.replaceAll("//s", ""));
        new LanguageStats().guessLanguage(unknown);
    }
}
