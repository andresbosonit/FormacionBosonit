package com.example.lotrjavafx.Entitys;

import java.util.Iterator;
import java.util.List;

public class Game {
    private int turno;
    private List<Hero> heroList;
    private List<Beast> beastList;
    private String resultado;
    public Game(List<Hero> heroList, List<Beast> beastList){
        this.turno = 1;
        this.heroList = heroList;
        this.beastList = beastList;
        this.resultado = "";
    }

    public String startGame(){
        while(!heroList.isEmpty() && !beastList.isEmpty()){
            this.turn();
        }
        this.resultado += "-----------Finalizo la partida!!!-----------\n";
        if(!heroList.isEmpty()){
            this.resultado += "Ha ganado el equipo de los heroes!!!. Siguen vivos los siguientes personajes\n";
            for(Hero hero: heroList){
                this.resultado += hero.getName() + " con " + hero.getHp() + " puntos de vida.\n";
            }
        }else if(!beastList.isEmpty()){
            this.resultado += "Ha ganado el equipo de las bestias!!!. Siguen vivos los siguientes personajes\n";
            for(Beast beast: beastList){
                this.resultado += beast.getName() + " con " + beast.getHp() + " puntos de vida.\n";
            }
        }else{
            this.resultado += "La partida ha terminado en empate, todos han muerto\n";
        }
        return this.resultado;
    }

    public void turn(){
        this.resultado += "Turno " + this.turno + ":\n";
        int fightNum = Math.min(heroList.size(),beastList.size());
        for(int i = 0; fightNum > i; i++){
            this.fight(this.heroList.get(i),this.beastList.get(i));
        }
        this.checkHealth(this.heroList);
        this.checkHealth(this.beastList);
        this.turno++;
    }

    public void fight(Hero hero, Beast beast){
        this.resultado += "Lucha entre " + hero.getName() + " (Vida=" + hero.getHp() + " Armadura=" + hero.getArmorLevel() +
                ") y " +  beast.getName() + " (Vida=" + beast.getHp() + " Armadura=" + beast.getArmorLevel() + ")\n";
        this.resultado += hero.doDamage(beast);
        this.resultado += beast.doDamage(hero);
    }

    public void checkHealth(List<? extends Character> characterList) {
        Iterator<? extends Character> iterator = characterList.iterator();
        while (iterator.hasNext()) {
            Character character = iterator.next();
            if (character.getHp() <= 0) {
                iterator.remove();
            }
        }
    }

}
