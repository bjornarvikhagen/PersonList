package com.personlist.personlist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PersonListController {

    private ObservableList<Person> persons = FXCollections.observableArrayList();

    public void readPersons() throws IOException {
        File file = new File("persons.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String[] parts = scanner.nextLine().split(" ");
            String firstName = parts[0];
            String lastName = parts[1];
            addPerson(firstName, lastName);
        }
        scanner.close();
    }

    public void addPerson(String firstName, String lastName) {
        Person person = new Person(firstName, lastName);
        persons.add(person);
    }

    public void deletePerson(Person person) {
        persons.remove(person);
    }

    public void savePersons() throws IOException {
        File file = new File("persons.txt");
        FileWriter writer = new FileWriter(file);
        for (Person person : persons) {
            writer.write(person.getFirstName() + " " + person.getLastName() + "\n");
        }
        writer.close();
    }

    public ObservableList<Person> getPersons() {
        return persons;
    }
}