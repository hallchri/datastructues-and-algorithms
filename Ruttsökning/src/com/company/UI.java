package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class UI {
    private ArrayList<Node> cities = new ArrayList<>();
    public UI() {
        cmdInterface();
    }

    public void cmdInterface() {
        while(true) {
            cities = new CreateGraph().createGraph();
            Scanner input = new Scanner(System.in);
            new CreateGraph().showNodesAndLinks();
            System.out.println("Ange startpunkt: ");

            for(int i = 0; i < cities.size(); i++) {
                System.out.println(i + 1 + ": " + cities.get(i).getName());
            }

            int start = input.nextInt();
            start -= 1;
            System.out.println("Ni har valt " + cities.get(start).getName());

            System.out.println("Ange slutdestination: ");
            for(int i = 0; i < cities.size(); i++) {
                System.out.println(i + 1 + ": " + cities.get(i).getName());
            }

            int end = input.nextInt();
            end -= 1;
            System.out.println("Ni har valt " + cities.get(end).getName());

            new Node().getRoute(cities.get(start), cities.get(end));

            break;
        }
    }
}
