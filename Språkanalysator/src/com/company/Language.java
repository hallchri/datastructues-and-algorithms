package com.company;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

public class Language {
    private String content = "";
    private String languageLabel = "";
    private HashMap<Character, Double> singleCharMap;
    private HashMap<String, Double> threeCharMap;
    private HashMap<Character, Double> firstCharMap;

    public Language(String lName, String text) {
        setLanguageLabel(lName);
        setLanguageContent(text);
    }

    public void setLanguageLabel(String l) {languageLabel = l;}
    public void setLanguageContent(String text) {content = text;}
    public String getLanguageLabel() {return languageLabel;}
    public String getLanguageText() {return content;}

    public HashMap<Character, Double> calculateSingleCharDistribution() {
        singleCharMap = new HashMap<>();
        singleCharMap = singleCharDistribution(getLanguageText());;
        return singleCharMap;
    }

    public HashMap<String, Double> calculateThreeCharDistribution() {
        threeCharMap = new HashMap<>();
        threeCharMap = threeCharDistribution(getLanguageText());
        return threeCharMap;
    }

    public HashMap<Character, Double> calculateFirstCharDistribution() {
        firstCharMap = new HashMap<>();
        firstCharMap = firstCharDistribution(getLanguageText());
        return firstCharMap;
    }

    public HashMap<Character, Double> singleCharDistribution(String text) {
        text = text.replaceAll("\\s", "");
        singleCharMap = new HashMap<>();

        char[] textArray = text.toCharArray(); // sätt alla karaktärer i en array of characters
        int totalCharacters = text.length();
        double occasion = 0;

        // För varenda karaktär vi påträffar sätter vi in den som nyckel i vår nya hashmap,
        // och vi inkrementerar dess värde för påträffande
        for(char c : textArray) {
            if(singleCharMap.containsKey(c)) {
                occasion = singleCharMap.get(c)+1;
                singleCharMap.put(c, occasion);
            } else {
                singleCharMap.put(c, 1.0);
            }
        }

        // Räkna hur många procent tecknen består av i texten
        for(char c : singleCharMap.keySet()) {
            double singleCharPercent = singleCharMap.get(c) / totalCharacters;
            BigDecimal bd = BigDecimal.valueOf(singleCharPercent);
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            singleCharMap.replace(c, bd.doubleValue());
        }

        return singleCharMap;
    }

    public HashMap<String, Double> threeCharDistribution(String text) {
        text = text.replaceAll("\\s", "");
        threeCharMap = new HashMap<>();
        String threeChars = "";
        int totalStrings = 0;
        double occasion = 0;

        for(int i = 0; i < text.length()-2; i++) {
            int thirdChar = 3;
            threeChars = text.substring(i, thirdChar + i);
            if(threeCharMap.containsKey(threeChars)) {
                occasion = threeCharMap.get(threeChars)+1;
                threeCharMap.put(threeChars, occasion);
            } else {
                threeCharMap.put(threeChars, 1.0);
            }
        }

        // Totala antalet värden / påträffningar
        for(double d : threeCharMap.values()) {
            totalStrings += d;
        }

        for(String s : threeCharMap.keySet()) {
            double threeCharPercent = threeCharMap.get(s) / totalStrings;
            BigDecimal bd = BigDecimal.valueOf(threeCharPercent);
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            threeCharMap.replace(s, bd.doubleValue());
        }

        return threeCharMap;
    }

    public HashMap<Character, Double> firstCharDistribution(String text) {
        firstCharMap = new HashMap<>();
        double occasion = 0;
        int totalCharacters = 0;

        text = text.trim().replaceAll(" +", " ");
        String[] firstChar = text.split(" ");

        // För varenda karaktär vi påträffar sätter vi in den som nyckel i vår nya hashmap,
        // och vi inkrementerar dess värde för påträffande
        for(int i = 0; i < firstChar.length; i++) {
            String d = firstChar[i];
            if(firstCharMap.containsKey(d.charAt(0))) {
                occasion = firstCharMap.get(d.charAt(0))+1;
                firstCharMap.put(d.charAt(0), occasion);
            } else {
                firstCharMap.put(d.charAt(0), 1.0);
            }
        }

        // Totala antalet värden / påträffningar
        for(double d : firstCharMap.values()) {
            totalCharacters += d;
        }

        // Räkna hur många procent tecknen består av i texten
        for(char c : firstCharMap.keySet()) {
            double singleCharPercent = firstCharMap.get(c) / totalCharacters;
            BigDecimal bd = BigDecimal.valueOf(singleCharPercent);
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            firstCharMap.replace(c, bd.doubleValue());
            //System.out.println(c + " | " + firstCharMap.get(c) + " / " + totalCharacters + " = " + bd.doubleValue());
        }

        return firstCharMap;
    }

}
