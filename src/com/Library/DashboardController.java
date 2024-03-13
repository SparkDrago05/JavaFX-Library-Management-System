package com.Library;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DashboardController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private StackPane dashboardStack;

    Parent booksView;
    Parent usersView;
    Parent issueBookView;
    TableView allBooksTable;
    TableView issuedBooksTable;

    @FXML
    public void initialize(URL url, ResourceBundle bundle) {
        // Load booksView.fxml and set to Right of dashboardBorderPane
        try {
            booksView = FXMLLoader.load(getClass().getResource("booksView.fxml"));
            // Get allBooksTable from booksView
            // AllBooksTableController allBooksTableController = new
            // AllBooksTableController();
            // allBooksTableController.allBooksTable =
            // (javafx.scene.control.TableView<Book>) booksView
            // .lookup("#allBooksTable");
            usersView = FXMLLoader.load(getClass().getResource("usersView.fxml"));
            FXMLLoader issueBookViewLoader = new FXMLLoader(getClass().getResource("issueBookView.fxml"));
            issueBookView = issueBookViewLoader.load();
            IssueBookViewController issueBookViewController = (IssueBookViewController) issueBookViewLoader
                    .getController();

            allBooksTable = (javafx.scene.control.TableView<Book>) booksView.lookup("#allBooksTable");
            issuedBooksTable = (javafx.scene.control.TableView<Book>) booksView.lookup("#issuedBooksTable");

            issueBookViewController.allBooksTable = allBooksTable;
            issueBookViewController.issuedBooksTable = issuedBooksTable;

            dashboardStack.getChildren().addAll(booksView, usersView, issueBookView);

            booksView.setVisible(true);
            usersView.setVisible(false);
            issueBookView.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    @FXML
    void openAddBookDialog(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("addBookDialog.fxml"));
            Parent addBookDialog = fxmlLoader.load();
            addBookDialog.getStylesheets().add(getClass().getResource("addBookDialog.css").toExternalForm());

            AddBookDialogController addBookDialogController = (AddBookDialogController) fxmlLoader.getController();
            addBookDialogController.allBooksTable = allBooksTable;
            Scene addBookDialogScene = new Scene(addBookDialog, 400, 400);
            Stage addBookDialogStage = new Stage();

            // Hide the titlebar of Dialog
            addBookDialogStage.initStyle(javafx.stage.StageStyle.UNDECORATED);
            // Disable Previous Stage until this Dialog is exited
            addBookDialogStage.initOwner(dashboardStack.getScene().getWindow());

            addBookDialogStage.setScene(addBookDialogScene);
            addBookDialogStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showUsersTable(ActionEvent event) {
        booksView.setVisible(false);
        issueBookView.setVisible(false);
        usersView.setVisible(true);
    }

    @FXML
    void showBooksView(ActionEvent event) {
        booksView.setVisible(true);
        issueBookView.setVisible(false);
        usersView.setVisible(false);
    }

    @FXML
    void showIssueBookView(ActionEvent event) {
        booksView.setVisible(false);
        issueBookView.setVisible(true);
        usersView.setVisible(false);
    }
}
