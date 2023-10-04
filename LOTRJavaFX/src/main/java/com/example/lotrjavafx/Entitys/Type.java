package com.example.lotrjavafx.Entitys;

public enum Type {
    ELFO("Elfo"),
    HUMANO("Humano"),
    HOBBIT("Hobbit"),
    ORCO("Orco"),
    TRASGO("Trasgo");
    private final String name;
    Type(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
