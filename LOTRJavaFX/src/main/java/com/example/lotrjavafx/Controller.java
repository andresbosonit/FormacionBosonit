package com.example.lotrjavafx;

import com.example.lotrjavafx.Entitys.Beast;
import com.example.lotrjavafx.Entitys.Character;
import com.example.lotrjavafx.Entitys.Game;
import com.example.lotrjavafx.Entitys.Hero;
import com.example.lotrjavafx.Entitys.Type;
import javafx.scene.control.*;



public class Controller {
    Character character;

    public Controller() {
        this.character = new Character();
    }

    public Hero createHero(String name, Type type, String hp, String armorLevel){
        return (Hero) this.character.createCharacter(name, type, hp, armorLevel);
    }
    public Beast createBeast(String name, Type type, String hp, String armorLevel){
        return (Beast) this.character.createCharacter(name, type, hp, armorLevel);
    }
    public ListView<Hero> addHero(Hero hero, ListView<Hero> heroList) {
        heroList.getItems().add(hero);
        return heroList;
    }
    public ListView<Beast> addBeast(Beast beast, ListView<Beast> beastList) {
        beastList.getItems().add(beast);
        return beastList;
    }

    public ListView<Hero> deleteHero(Hero selectedHero, ListView<Hero> heroList) {
        heroList.getItems().remove(selectedHero);
        return heroList;
    }

    public ListView<Beast> deleteBeast(Beast selectedBeast, ListView<Beast> beastList) {
        beastList.getItems().remove(selectedBeast);
        return beastList;
    }

    public ListView<Hero> upHero(Hero selectedHero, ListView<Hero> heroList, int index) {
        heroList.getItems().remove(selectedHero);
        heroList.getItems().add(index - 1, selectedHero);
        heroList.getSelectionModel().select(index - 1);
        return heroList;
    }

    public ListView<Beast> upBeast(Beast selectedBeast, ListView<Beast> beastList, int index) {
        beastList.getItems().remove(selectedBeast);
        beastList.getItems().add(index - 1, selectedBeast);
        beastList.getSelectionModel().select(index - 1);
        return beastList;
    }

    public ListView<Hero> downHero(Hero selectedHero, ListView<Hero> heroList, int index) {
        heroList.getItems().remove(selectedHero);
        heroList.getItems().add(index + 1, selectedHero);
        heroList.getSelectionModel().select(index + 1);
        return heroList;
    }

    public ListView<Beast> downBeast(Beast selectedBeast, ListView<Beast> beastList, int index) {
        beastList.getItems().remove(selectedBeast);
        beastList.getItems().add(index + 1, selectedBeast);
        beastList.getSelectionModel().select(index + 1);
        return beastList;
    }

    public String fight(ListView<Hero> heroList, ListView<Beast> beastList) {
        Game game = new Game(heroList.getItems(), beastList.getItems());
        return game.startGame();
    }
}
