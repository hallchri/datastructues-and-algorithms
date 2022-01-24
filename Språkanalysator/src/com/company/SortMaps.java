package com.company;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SortMaps {
    // Metod för att sortera våra HashMaps för att sedan få resultaten rätt utskrivna
    // i tabellformat
    public static <K, V extends Comparable<? super V>> Map<K, V> sortMapValue(Map<K, V> map) {
        List<Map.Entry<K, V>> valueList = new ArrayList<>(map.entrySet());
        valueList.sort(Map.Entry.comparingByValue());

        // OBS! LinkedHashMap för att kunna behålla den sorterade strukturen
        Map<K, V> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : valueList) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        // Returnera den sorterade Map:en
        return sortedMap;
    }
}
