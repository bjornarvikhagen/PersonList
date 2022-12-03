package com.personlist.personlist;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PersonList extends Application {
    // Declare the list of Persons as an instance variable
    private ObservableList<Person> persons = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

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

    @Override
    public void start(Stage stage) {
        // Create Text Fields
        TextField textFieldFirstName = new TextField();
        textFieldFirstName.setPromptText("Enter first name");
        TextField textFieldLastName = new TextField();
        textFieldLastName.setPromptText("Enter last name");

        // Create List View
        ListView<Person> listView = new ListView<>();


        // Populate the list of Persons
        try {
            readPersons();
        } catch (IOException exc) {
            System.out.println("Error reading file: " + exc);
        }

        // Set list of persons to list view
        listView.setItems(persons);
// Add the listener to the persons list
        listView.getItems().addListener((ListChangeListener) change -> {
            try {
                savePersons();
            } catch (IOException exc) {
                System.out.println("Error writing to file: " + exc);
            }
        });

        // Create Buttons
        Button btnAdd = new Button("Add Person");

        btnAdd.setOnAction(e -> {
            String firstName = textFieldFirstName.getText();
            String lastName = textFieldLastName.getText();
            addPerson(firstName, lastName);
            textFieldFirstName.setText("");
            textFieldLastName.setText("");
        });
        Button btnDelete = new Button("Delete Person");
        btnDelete.setOnAction(e -> {
            Person selectedPerson = listView.getSelectionModel().getSelectedItem();
            persons.remove(selectedPerson);
        });


        // Create an HBox container and add the buttons to it
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10));
        hbox.getChildren().addAll(btnAdd, btnDelete);
        hbox.setAlignment(Pos.CENTER);

        // Set up the Scene
        VBox box = new VBox(textFieldFirstName, textFieldLastName, listView, hbox);
        box.setSpacing(10);
        Scene scene = new Scene(box, 500, 400);

        // Show the Stage
        stage.setTitle("Person List");
        stage.setScene(scene);
        stage.show();
    }


    // Function to add a Person to the List
    public void addPerson(String firstName, String lastName) {
        Person person = new Person(firstName, lastName);
        persons.add(person);
    }

    // Function to save the list of persons to a file
    public void savePersons() throws IOException {
        File file = new File("persons.txt");
        FileWriter writer = new FileWriter(file);
        for (Person person : persons) {
            writer.write(person.getFirstName() + " " + person.getLastName() + "\n");
        }
        writer.close();
    }
}