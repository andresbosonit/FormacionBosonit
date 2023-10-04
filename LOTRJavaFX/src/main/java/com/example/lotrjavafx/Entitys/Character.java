package com.example.lotrjavafx.Entitys;

import lombok.Data;

import static com.example.lotrjavafx.Util.isPositiveInteger;

@Data
public class Character {
    private String name;
    private Type type;
    private int hp;
    private int armorLevel;
    private int attack;

    public Character(Type type, String name, int hp, int armorLevel){
        this.type = type;
        this.name = name;
        this.hp = hp;
        this.armorLevel = armorLevel;
    }

    public Character() {
    }

    public void calculateAttack() {}

    public String doDamage(Character character){
        String resultado = "";
        // Tiro el dado o dados
        this.calculateAttack();
        int antiguoNivelDeArmadura = character.getArmorLevel();
        if(this.type == Type.ORCO){
            // Desprecio los decimales para trabajar siempre con enteros
            int reduccion = (int) (character.getArmorLevel() * 0.1);
            int nuevoNivelDeArmadura = character.getArmorLevel() - reduccion;
            character.setArmorLevel(nuevoNivelDeArmadura);
        }
        // Calculo la potencia ofensiva
        int offensivePower = (this.getAttack()-character.getArmorLevel());
        if(this.type == Type.ELFO && character.type ==  Type.ORCO){
            offensivePower += 10;
        }
        if(this.type == Type.HOBBIT && character.type ==  Type.TRASGO) {
            offensivePower -= 5;
        }
        // Si la potencia ofensiva es <= 0 la igualo a 0 para evitar los negativos y que se sume vida al oponente
        if(offensivePower<0){
            offensivePower = 0;
        }
        resultado += this.getName() + " saca " + this.getAttack() + " y le quita " + offensivePower + " de vida a " + character.getName() + "\n";
        // El otro personaje recibe el daño
        resultado += character.takeDamage(offensivePower);
        // En caso de que el daño lo realice un Orco, restablezco la armadura del oponente
        if(this.type == Type.ORCO){
            character.setArmorLevel(antiguoNivelDeArmadura);
        }
        return resultado;
    };

    public String takeDamage(int offensivePower){
        String resultado = "";
        // Le reduzco la vida
        this.setHp(this.getHp()-offensivePower);
        // Si la via es menor o igual a cero imprimo el mensaje por pantalla de que a muerto
        if(this.getHp()<=0){
            resultado += "¡Muere " + this.getType().getName() + " " + this.getName() + "!\n";
        }
        return resultado;
    };

    public String toString(){
        return this.name + " - " + this.getType().getName() + " (" + this.getHp() + ", " + this.getArmorLevel() + ")\n";
    }

    public Character createCharacter(String name, Type type, String hp, String armorLevel) {
        if (!name.isEmpty() && isPositiveInteger(hp) && isPositiveInteger(armorLevel) && Integer.parseInt(armorLevel)<100) {
            if(type.getName() == "Orco" || type.getName() == "Trasgo"){
                return new Beast(type, name, Integer.parseInt(hp), Integer.parseInt(armorLevel));
            }
            return new Hero(type, name, Integer.parseInt(hp), Integer.parseInt(armorLevel));
        }
        return null;
    }

}
