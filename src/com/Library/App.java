package com.Library;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Library Management System");

        Parent root = FXMLLoader.load(getClass().getResource("sign.fxml"));
        
        root.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        Scene scene = new Scene(root, 800, 500);
        
        stage.setScene(scene);

        stage.show();

    }

    public static void main(String[] args) {
        DB.createConnection();
        try {
            Library.initializeTables();
            Library.initializeBooksAndUsers();
            System.out.println(Library.books.get(0).id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Application.launch(args);
    }// Application logic goes here

    @Override
    public void stop() throws Exception {
        super.stop();
        DB.closeConnection();
    }
}
