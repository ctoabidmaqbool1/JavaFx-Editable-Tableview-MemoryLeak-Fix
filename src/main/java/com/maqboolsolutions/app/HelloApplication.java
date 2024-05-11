package com.maqboolsolutions.app;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import one.jpro.platform.treeshowing.TreeShowing;

public class HelloApplication extends Application {

    ObservableList<Person> data = FXCollections.observableArrayList();
    int rowId = 1;

    @Override
    public void start(Stage stage) {
        VBox vbxRoot = new VBox(10);
        vbxRoot.setPadding(new Insets(10));
        vbxRoot.setAlignment(Pos.TOP_CENTER);

        TableView<Person> tblPerson = new TableView<>();

        TableColumn<Person, Integer> clmId = new TableColumn<>("#");
        TableColumn<Person, TextField> clmFirstName = new TableColumn<>("First Name");
        TableColumn<Person, TextField> clmLastName = new TableColumn<>("Last Name");

        clmId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        clmLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        tblPerson.getColumns().addAll(clmId, clmFirstName, clmLastName);

        Button btnAdd = new Button("Add");
        Button btnClear = new Button("Clear");

        vbxRoot.getChildren().addAll(tblPerson, btnAdd, btnClear);

        Scene scene = new Scene(vbxRoot, 500, 400);
        stage.setScene(scene);
        stage.setTitle("Editable TableView Example");
        stage.show();

        btnAdd.setOnAction(event -> {
            tblPerson.setItems(getData());
        });

        btnClear.setOnAction(event -> {
            data.clear();
            rowId = 1;
        });
    }

    private ObservableList<Person> getData() {
        for (int i = rowId; i < rowId + 20; i++) {
            Person person = getPerson(i, "", "");

            data.add(person);
        }
        rowId += 20;

        return data;
    }

    Person getPerson(int id, String firstName, String lastName) {
        TextField txtFirstName = new TextField(firstName);
        TextField txtLastName = new TextField(lastName);

        // Define the event handler
        EventHandler<KeyEvent> eventHandler = (event) -> {
            if (event.isControlDown() && event.getCode() == KeyCode.SPACE) {
                // Handle Ctrl + Space key combination
                System.out.println("Ctrl + Space pressed on #" + id + "!");
            }
        };

        // Add the event handler to firstName
        //txtFirstName.addEventHandler(KeyEvent.KEY_PRESSED, eventHandler);

        // If you need to remove the event handler later
        TreeShowing.treeShowing(txtFirstName).addListener((p, o, showing) -> {
            System.out.println("Fist name #" + id + ", showing: " + showing);
            if (showing) {
                txtFirstName.addEventHandler(KeyEvent.KEY_PRESSED, eventHandler);
            } else {
                txtFirstName.removeEventHandler(KeyEvent.KEY_PRESSED, eventHandler);
            }
        });

        // Add the event handler to lastName
        // txtLastName.addEventHandler(KeyEvent.KEY_PRESSED, eventHandler);

        // If you need to remove the event handler later
        TreeShowing.treeShowing(txtLastName).addListener((p, o, showing) -> {
            System.out.println("Last name #" + id + ", showing: " + showing);
            if (showing) {
                txtLastName.addEventHandler(KeyEvent.KEY_PRESSED, eventHandler);
            } else {
                txtLastName.removeEventHandler(KeyEvent.KEY_PRESSED, eventHandler);
            }
        });

        return new Person(id, txtFirstName, txtLastName);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class Person {
        private final int id;
        private final TextField firstName;
        private final TextField lastName;

        public Person(int id, TextField firstName, TextField lastName) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public int getId() {
            return id;
        }

        public TextField getFirstName() {
            return firstName;
        }

        public TextField getLastName() {
            return lastName;
        }
    }
}


