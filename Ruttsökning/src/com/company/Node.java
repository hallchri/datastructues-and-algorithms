package com.company;

import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;

public class Node {
    private String name;
    private double latitude;
    private double longitude;
    private Node previous = null;
    private ArrayList<Node> neighbours;

    public Node(String n, double la, double lo) {
        neighbours = new ArrayList<>();
        setName(n);
        setLatitude(la);
        setLongitude(lo);
    }

    public Node() {

    }

    public String getName() { return name; }

    public void setName(String city) { name = city; }

    public double getLatitude() { return latitude; }

    public void setLatitude(double lati) { latitude = lati; }

    public double getLongitude() { return longitude; }

    public void setLongitude(double longi) { longitude = longi; }

    public ArrayList<Node> getNeighbours() { return neighbours; }

    public void addNeighbour(Node nb) { neighbours.add(nb); }

    public double getF() {
        return calculateH(this) + calculateG(this);}

    public double calculateH(Node destination) {
        for(int i = 0; i < neighbours.size(); i++) {
            destination = neighbours.get(i);
        }
        double H = 0;
        double lat1 = this.getLatitude();
        double lon1 = this.getLongitude();
        double lat2 = destination.getLatitude();
        double lon2 = destination.getLongitude();
        H = getDistance(lat1, lon1, lat2, lon2);
        return H;
    }

    public double calculateG(Node source) {
        double G = 0;
        Node current = this;

        while(current != source) {
            double lat1 = current.getLatitude();
            double lon1 = current.getLongitude();
            double lat2 = current.previous.getLatitude();
            double lon2 = current.previous.getLongitude();
            G += getDistance(lat1, lon1, lat2, lon2);
            current = current.previous;
        }
        return G;
    }

    // Även "Haversine formula" - beräknar den raka
    // linjens distans mellan startpunkt till destination
    public double getDistance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371; // Jordens radie

        lat1 = lat1*Math.PI/180.0;
        lon1 = lon1*Math.PI/180.0; // Konvertera grader till radianer
        lat2 = lat2*Math.PI/180.0;
        lon2 = lon2*Math.PI/180.0;

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;
        double a = Math.pow(Math.sin(dLat/2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dLon/2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double km = R * c;

        return km;
    }

    public ArrayList<Node> getRoute(Node source, Node destination) {
        ArrayList<Node> candidates = new ArrayList<>();
        ArrayList<Node> visited = new ArrayList<>();
        Node current = source;
        boolean done = false;

        while(!done) {
            double minF = 0;
            Node next = null;

            for(int i = 0; i < current.getNeighbours().size(); i++) {
                if(!candidates.contains(current.getNeighbours().get(i)) && !visited.contains(current.getNeighbours().get(i))) {
                    candidates.add(current.getNeighbours().get(i));
                    current.getNeighbours().get(i).previous = current;
                }
            }

            for(int i = 0; i < candidates.size(); i++) {
                if(candidates.get(i) == destination) {
                    done = true;
                    break;
                } else {
                    double candidatesF = candidates.get(i).getF();
                    if(minF == 0 || minF > candidatesF) {
                        minF = candidatesF;
                        next = candidates.get(i);
                    }
                }
            }
            if(!done) {
                current = next;
                visited.add(current);
                candidates.remove(current);
            }
        }

        ArrayList<Node> route = new ArrayList<>();
        current = destination;

        while(current != source) {
            route.add(0, current);
            current = current.previous;
        }

        System.out.println("KORTASTE RUTTEN");
        System.out.println("-----------------");

        for(int i = 0; i < route.size(); i++) {
            System.out.println(i + 1 + ": " + route.get(i).getName());
        }

        return route;
    }

}
