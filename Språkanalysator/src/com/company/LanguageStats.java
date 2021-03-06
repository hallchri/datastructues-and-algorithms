package com.company;

import java.io.DataOutput;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class LanguageStats {
    private HashMap<String, Language> languages;

    public void addLanguage(Language l) {
        languages.put(l.getLanguageLabel(), l);
    }



    public void guessLanguage(Language l) throws UnsupportedEncodingException {
        languages = new HashMap<>();
        HashMap<String, Double> firstDifferentMap = new HashMap<>();
        HashMap<String, Double> threeDifferentMap = new HashMap<>();
        HashMap<String, Double> singleDifferentMap = new HashMap<>();
        HashMap<String, Double> totalDifferenceMap = new HashMap<>();

        HashMap<Character, Double> languageSingleDistribution = new HashMap<>();
        HashMap<String, Double> languageThreeDistribution = new HashMap<>();
        HashMap<Character, Double> languageFirstDistribution = new HashMap<>();
        double totalDifference = 0;

        Language Svenska =  new Language("Svenska", new FileInput().readFile("src/Languages/Svenska.txt"));
        Language English =  new Language("English", new FileInput().readFile("src/Languages/English.txt"));
        Language Deutch =   new Language("Deutch", new FileInput().readFile("src/Languages/Deutch.txt"));
        Language Eesti =    new Language("Eesti", new FileInput().readFile("src/Languages/Eesti.txt"));
        Language Francais = new Language("Francais", new FileInput().readFile("src/Languages/Francais.txt"));
        Language Italiano = new Language("Italiano", new FileInput().readFile("src/Languages/Italiano.txt"));
        Language Norsk =    new Language("Norsk", new FileInput().readFile("src/Languages/Norsk.txt"));
        Language Suomi =    new Language("Suomi", new FileInput().readFile("src/Languages/Suomi.txt"));

        addLanguage(Svenska);
        addLanguage(English);
        addLanguage(Deutch);
        addLanguage(Eesti);
        addLanguage(Francais);
        addLanguage(Italiano);
        addLanguage(Norsk);
        addLanguage(Suomi);

        // Printar ut rubrikerna f??r tabellformatet (l??ngre ner i loopen s?? kommer sj??lva statistiken ut)
        System.out.format("%8s%8s%8s%8s%8s%15s", "", "A1", "A2", "A3", "TOTAL", "RANGORDNING" + "\n");

        for(String s : languages.keySet()) {
            double singleDifference = 0;
            double threeDifference = 0;
            double firstDifference = 0;
            BigDecimal bd;

            HashMap<Character, Double> singleChar = languages.get(s).calculateSingleCharDistribution();
            HashMap<String, Double> threeChar = languages.get(s).calculateThreeCharDistribution();
            HashMap<Character, Double> firstChar = languages.get(s).calculateFirstCharDistribution();
            HashMap<Character, Double> inputSingleChar = l.calculateSingleCharDistribution();
            HashMap<String, Double> inputThreeChar = l.calculateThreeCharDistribution();
            HashMap<Character, Double> inputFirstChar = l.calculateFirstCharDistribution();

            totalDifferenceMap.put(s, 0.0); // HashMap f??r att lagra den totala skillnaden f??r karakt??rer per spr??k


            //J??mf??relse av alla och totala antalet karakt??rer (singleCharDistribution)
            for(char key : singleChar.keySet()) {
                if(inputSingleChar.containsKey(key)) {
                    singleDifference = Math.pow((inputSingleChar.get(key) - singleChar.get(key)), 2);
                    languageSingleDistribution.put(key, singleDifference); // S??tt de nya v??rdena som j??mf??rdes i en ny hashmap
                }
            }

            //J??mf??relse av alla och totala antalet tre-kombinationer (threeCharDistribution)
            for(String key : threeChar.keySet()) {
                if(inputThreeChar.containsKey(key)) {
                    threeDifference = Math.pow((inputThreeChar.get(key) - threeChar.get(key)), 2);
                    languageThreeDistribution.put(key, threeDifference); // S??tt de nya v??rdena som j??mf??rdes i en ny hashmap
                }
            }

            //J??mf??relse av alla och totala antalet av f??rsta karakt??r (firstCharDistribution)
            for(char key : firstChar.keySet()) {
                if(inputFirstChar.containsKey(key)) {
                    firstDifference = Math.pow((inputFirstChar.get(key) - firstChar.get(key)), 2);
                    languageFirstDistribution.put(key, firstDifference); // S??tt de nya v??rdena som j??mf??rdes i en ny hashmap
                }
            }

            // S??tt in den totala skillnaden av karakt??rerna i en ny HashMap som vi sedan anv??nder
            // f??r att addera ihop hela summan i en annan HashMap
            double totalSingleDifference = languageSingleDistribution.values().stream().mapToDouble(Double::doubleValue).sum();
            double totalThreeDifference = languageThreeDistribution.values().stream().mapToDouble(Double::doubleValue).sum();
            double totalFirstDifference = languageFirstDistribution.values().stream().mapToDouble(Double::doubleValue).sum();
            totalDifference = totalSingleDifference + totalThreeDifference + totalFirstDifference;

            // Genom att manipulera BigDecimal-klassen kan vi s??kerst??lla att utr??kningarna mellan de
            // stora/l??nga decimala talen h??lls i skick
            BigDecimal bd1 = BigDecimal.valueOf(totalSingleDifference);
            bd1 = bd1.setScale(3, RoundingMode.HALF_UP);
            BigDecimal bd2 = BigDecimal.valueOf(totalThreeDifference);
            bd2 = bd2.setScale(3, RoundingMode.HALF_UP);
            BigDecimal bd3 = BigDecimal.valueOf(totalFirstDifference);
            bd3 = bd3.setScale(3, RoundingMode.HALF_UP);

            bd = BigDecimal.valueOf(totalDifference);
            bd = bd.setScale(3, RoundingMode.HALF_UP);

            // S??tt v??ra v??rden efter analys i v??ra HashMaps
            singleDifferentMap.put(s, bd1.doubleValue());
            threeDifferentMap.put(s, bd2.doubleValue());
            firstDifferentMap.put(s, bd3.doubleValue());
            totalDifferenceMap.put(s, bd.doubleValue());

            // H??r tar vi sen och utnyttjar v??r metod i MapUtil-klassen f??r att sortera v??ra HashMaps
            singleDifferentMap = (HashMap<String, Double>) new SortMaps().sortMapValue(singleDifferentMap);
            threeDifferentMap = (HashMap<String, Double>) new SortMaps().sortMapValue(threeDifferentMap);
            firstDifferentMap = (HashMap<String, Double>) new SortMaps().sortMapValue(firstDifferentMap);
            totalDifferenceMap = (HashMap<String, Double>) new SortMaps().sortMapValue(totalDifferenceMap);
        }

        int rankingOrder = 0;
        //Printa ut i ordningsf??ljd
        for (String s : totalDifferenceMap.keySet()) {
            rankingOrder++;
            System.out.format("%8s%8s%8s%8s%8s%5s", s, singleDifferentMap.get(s), threeDifferentMap.get(s), firstDifferentMap.get(s), totalDifferenceMap.get(s), rankingOrder + "\n");
        }

        // Ta reda p?? det spr??k som har den minsta totala skillnaden
        Map.Entry<String, Double> minTotal = null;
        for (Map.Entry<String, Double> entry : totalDifferenceMap.entrySet()) {
            if (minTotal == null || minTotal.getValue() > entry.getValue()) {
                minTotal = entry;
            }
        }
        System.out.println("Hmm, jag gissar att spr??ket ni skriver p?? ??r: " + minTotal.getKey());
    }
}
