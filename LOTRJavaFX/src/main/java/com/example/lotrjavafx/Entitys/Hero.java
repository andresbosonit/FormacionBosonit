package com.example.lotrjavafx.Entitys;

public class Hero extends Character{

    public Hero(Type type, String name, int hp, int armorLevel){
        super(type,name,hp,armorLevel);
    }
    @Override
    public void calculateAttack() {
        int randomNum1 = (int) Math.floor(Math.random() * 101);
        int randomNum2 = (int) Math.floor(Math.random() * 101);
        this.setAttack(Math.max(randomNum1, randomNum2));
    }
}
