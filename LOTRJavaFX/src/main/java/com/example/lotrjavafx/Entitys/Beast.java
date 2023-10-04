package com.example.lotrjavafx.Entitys;

public class Beast extends Character{

    public Beast(Type type, String name, int hp, int armorLevel){
        super(type,name,hp,armorLevel);
    }
    @Override
    public void calculateAttack() {
        this.setAttack((int) Math.floor(Math.random() * 91));
    }
}
