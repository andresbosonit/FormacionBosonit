package org.example;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        try{
            // Todas las personas con datos correctos.
            List<Person> lista = readLines("..\\block1-process-file-and-streams\\src\\main\\resources\\ficheroEjemplo.txt");
            System.out.println("Mostrando la lista con los nombres despues de quitar los defectuosos");
            lista.forEach(e -> System.out.println(e.toString()));

            // a) Primera filtración para menores de 25 y que no aparezcan los que tengan edad 0.
            System.out.println("Ejercicio a)");
            Stream<Person> streamA = lista.stream().filter(person -> person.getAge() < 25 && person.getAge() > 0);
            streamA.forEach(e -> System.out.println(e.toString()));

            // b) Elimine personas cuyo nombre empieza por A
            System.out.println("Ejercicio b)");
            Stream<Person> streamB = lista.stream().filter(person -> {String name = person.getName(); return !name.startsWith("Á") && !name.startsWith("A");});
            streamB.forEach(e -> System.out.println(e.toString()));

            // c) Crear un Stream a partir de la lista y obtener el primer elemento cuya ciudad sea Madrid
            System.out.println("Ejercicio c)");
            Optional<Person> madridPerson = lista.stream().filter(person -> person.getAge() < 25 && person.getAge() > 0).filter(person -> "Madrid".equals(person.getTown())).findFirst();

            // Imprimiendo el resultado
            madridPerson.ifPresentOrElse(person -> System.out.println("Encontrado: " + person.getName()), () -> System.out.println("No existe una persona cuya ciudad sea Madrid"));

            // d) Crear un Stream a partir de la lista y obtener el primer elemento cuya ciudad sea Barcelona.
            System.out.println("Ejercicio d)");
            Optional<Person> barcelonaPerson = lista.stream().filter(person -> person.getAge() < 25 && person.getAge() > 0).filter(person -> "Barcelona".equals(person.getTown())).findFirst();
            barcelonaPerson.ifPresentOrElse(person -> System.out.println("Encontrado: " + person.getName()), () -> System.out.println("No existe una persona cuya ciudad sea Barcelona"));
        }
        catch(InvalidLineFormatException | IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static List<Person> readLines(String filePath) throws IOException, InvalidLineFormatException{
        List<Person> listPerson = new ArrayList<>();
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path);

        int lineNumber = 0;

        for (String line : lines) {
            lineNumber++;
            int posicion, contador = 0;
            posicion = line.indexOf(":");
            while (posicion != -1) {
                contador++;
                posicion = line.indexOf(":", posicion + 1);
            }
            if (contador == 2) {
                String[] fields = line.split(":");
                String name = fields[0].trim();
                if (name.isBlank()) {
                    throw new InvalidLineFormatException("La linea " + lineNumber + " no contiene nombre");
                }

                String city = "unknown";
                int ageString = 0;

                if(!line.endsWith("::")){
                    if(!fields[1].isEmpty()){
                        city = fields[1].trim();
                    }

                    if(fields.length == 3){
                        ageString = Integer.parseInt(fields[2].trim());
                    }
                }

                listPerson.add(new Person(name,city,ageString));
            }
            else{
                throw new InvalidLineFormatException("La linea " + lineNumber + " no contiene dos caracteres ':'");
            }
        }
        return listPerson;
    }

    public static class InvalidLineFormatException extends Exception {
        public InvalidLineFormatException(String message) {
            super(message);
        }
    }
}