package com.Library;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class UsersViewController implements Initializable {

    @FXML
    private TableColumn<User, String> userAction;

    @FXML
    private TableColumn<User, String> userEmail;

    @FXML
    private TableColumn<User, Integer> userId;

    @FXML
    private TableColumn<User, String> userName;

    @FXML
    private TableView<User> usersTable;

    @FXML
    private JFXTextField newUserEmail;

    @FXML
    private JFXTextField newUserName;

    @FXML
    public void initialize(URL url, ResourceBundle bundle) {
        // Set cell value factories for each column in Users Table
        userId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().id).asObject());
        userName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().name));
        userEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().email));
        userAction.setCellFactory(col -> {
            TableCell<User, String> cell = new TableCell<User, String>() {
                final Button deleteButton = new Button("Delete");
                {
                    // Disable Delete Button if User is Librarian

                    deleteButton.setOnAction(event -> {
                        User user = getTableView().getItems().get(getIndex());
                        Library.users.remove(user);
                        try {
                            DB.statement.execute("DELETE FROM users WHERE id = " + user.id);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        usersTable.setItems(FXCollections.observableList(Library.users));
                    });
                }

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        deleteButton.setDisable(getTableView().getItems().get(getIndex()).type.equals("librarian"));
                        setGraphic(deleteButton);
                        setText(null);
                    }
                }
            };
            return cell;
        });
        // Add all users in the List Users to this table
        // Make FXCollections.observableList of all items in the Users List
        usersTable.setItems(FXCollections.observableList(Library.users));
    }

    @FXML
    void createUser(ActionEvent event) {
        String name = newUserName.getText();
        String email = newUserEmail.getText();
        String password = "password";

        try {
            DB.statement.execute(
                    "INSERT INTO users (name, email, password, type) VALUES ('" + name + "', '" + email + "', '"
                            + password + "', 'public')");
            // Get ID of the Added User
            int id = DB.statement.getGeneratedKeys().getInt(1);
            User user = new User(id, name, email, password, "public");
            Library.users.add(user);
            usersTable.setItems(FXCollections.observableList(Library.users));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
