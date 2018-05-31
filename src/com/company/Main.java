package com.company;


public class Main {

    public static void main(String[] args) {


        HashMap<Integer, String> people = new HashMap<>();

        people.add(1, "Tom Donald");
        people.add(412312, "John Brown");
        people.add(1221123, "Johann Sebastian Bach");
        people.add(123, "Johann Sebastian Bach");
        people.add(12, "Johann Sebastian Bach");
        people.add(2, "Johann Sebastian Bach");
        people.add(3, "Johann Sebastian Bach");
        people.add(4, "Johann Sebastian Bach");
        people.add(5, "Johann Sebastian Bach");

        System.out.println(people.getNumNodes());
        System.out.println(people.get(1));
        System.out.println(people.get(121));
        System.out.println(people.remove(1));
        System.out.println(people.isEmpty());
        System.out.println(people.getNumNodes());

    }
}




