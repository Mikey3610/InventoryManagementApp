package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class is the controller for the main page of the Inventory Management System created in this program. */
public class MainForm implements Initializable {

    public Button partSearchButton;
    public TextField partSearchText;
    public Button addPart;
    public Button modifyPart;
    public Button deletePart;
    public TableView <Part> partsTable;
    public TableColumn <Part, Integer> partIDCol;
    public TableColumn <Part, String> partNameCol;
    public TableColumn <Part, Integer> partInventoryCol;
    public TableColumn <Part, Double> partPriceCol;
    public TextField productSearchText;
    public Button addProduct;
    public Button modifyProduct;
    public Button deleteProduct;
    public TableView <Product> productsTable;
    public Button productSearchButton;
    public TableColumn <Product, Integer> productIdCol;
    public TableColumn <Product, String> productNameCol;
    public TableColumn <Product, Integer> productInventoryCol;
    public TableColumn <Product, Double> productPriceCol;
    public static Part partToModify;
    public static Product productToModify;
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** This method initializes the main page of the app populated with the data listed below.
     * The parts table will be populated with the values set by the part commands.
     * The products table will be populated with the values set by the product commands.
     * RUNTIME ERROR - Not including the addPartSearchClicked method for when a user clicks on the search button leads to a runtime error, so this was added to fix that issue.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
      */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized!");

        partsTable.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /** This method allows the user to add a part to the Inventory Management System app.
     * It will create a new stage where the "Add Part Form" is.
     * @param actionEvent Represents that the method will perform some action. In this case - an action of opening a new page in the app. */
    public void onAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPartForm.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 840, 500);
        stage.setTitle("Add Part Form");
        stage.setScene(scene);
        stage.show();
    }
    /** This method is utilized in other methods to indicate specific part to modify when selected by the user.
     * @return Returns a part object if selected by the user. */
    public static Part getPartToModify() {
        return partToModify;
    }

    /** This method utilizes the selected part from the user and opens the page titled "Modify Part Form".
     * @param actionEvent Represents that the method will perform some action. In this case - the action of opening a new page for modifying a part within the app. */
    public void onModifyPart(ActionEvent actionEvent) throws IOException {
        partToModify = partsTable.getSelectionModel().getSelectedItem();

        if (partToModify == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No parts selected.");
            alert.showAndWait();
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyPartForm.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 840, 500);
            stage.setTitle("Modify Part Form");
            stage.setScene(scene);
            stage.show();
        }
    }

    /** This method will utilize the parts search field when a user clicks on the search button to find if there are any matching parts.
     * @param event Will check the Inventory of parts against the user's input to see if there are any matches when the search button is clicked.
     * */
    public void onPartSearchButton(ActionEvent event) {

        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        String searchString = partSearchText.getText().toLowerCase();

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).toLowerCase().contains(searchString) ||
                    part.getName().toLowerCase().contains(searchString)) {
                partsFound.add(part);
            }
        }

        partsTable.setItems(partsFound);

        if (partsFound.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Part not found.");
            alert.showAndWait();
        }
    }

    /** This method will check to see if the parts search field for Parts table in the app is empty.
     * If empty, the table will be repopulated and shown to the user of whatever the inventory is.
     * @param event Represents that the method will perform some action. In this case, resets the parts table to show all the existing parts. */
    //This method added to prevent runtime error from occurring.
    public void addPartSearchClicked(KeyEvent event) {

        if (partSearchText.getText().isEmpty()) {
            partsTable.setItems(Inventory.getAllParts());
        }

    }

    /** This method allows the user to delete a selected part from the Inventory Management System app.
     * It will remove the selected part from the Inventory class.
     * @param actionEvent Represents that the method will perform some action. In this case - an action of deleting the selected part from the Inventory.*/
    public void onDeletePart(ActionEvent actionEvent) {
        Part deletePart = partsTable.getSelectionModel().getSelectedItem();

        if (deletePart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No parts selected.");
            alert.showAndWait();
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Do you want to delete the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(deletePart);
            }
        }
        }
    /** This method allows the user to add a product to the Inventory Management System app.
     * It will create a new stage where the "Add Product Form" is.
     * @param actionEvent Represents that the method will perform some action. In this case - an action of opening a new page titled "Add Product Form" in the app.*/
    public void onAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 640);
        stage.setTitle("Add Product Form");
        stage.setScene(scene);
        stage.show();
    }

    /** This method is utilized in other methods to indicate specific product to modify when selected by the user.
     * @return Returns a product object if selected by the user. */
    public static Product getProductToModify() {
        return productToModify;
    }

    /** This method utilizes the selected part from the user and opens the page titled "Modify Product Form".
     * @param actionEvent Represents that the method will perform some action. In this case - the action of opening a new page for modifying a product within the app. */
    public void onModifyProduct(ActionEvent actionEvent) throws IOException {
        productToModify = productsTable.getSelectionModel().getSelectedItem();

        if (productToModify == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No products selected.");
            alert.showAndWait();
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyProductForm.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 900, 640);
            stage.setTitle("Modify Product Form");
            stage.setScene(scene);
            stage.show();
        }
    }

    /** This method allows the user to delete a selected product from the Inventory Management System app.
     * It will remove the selected product from the Inventory class.
     * @param actionEvent Represents that the method will perform some action. In this case - an action of deleting the selected product from the Inventory.*/
    public void onDeleteProduct(ActionEvent actionEvent) {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No products selected.");
            alert.showAndWait();
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Do you want to delete the selected product?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                ObservableList<Part> assocParts = selectedProduct.getAllAssociatedParts();

                if (assocParts.size() >= 1) {
                    Alert assocPartAlert = new Alert(Alert.AlertType.ERROR, "All associated parts must be removed from product before proceeding.");
                    assocPartAlert.showAndWait();
                } else {
                    Inventory.deleteProduct(selectedProduct);
                }
            }
        }
    }

    /** This method will utilize the product search field when a user clicks on the search button to find if there are any matching products.
     * @param event Represents that the method will perform some action.
     * In this case, it will check the Inventory of products against the user's input to see if there are any matches when the search button is clicked. */
    public void onProductSearchButton(ActionEvent event) {

        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> productsFound = FXCollections.observableArrayList();
        String searchString = productSearchText.getText().toLowerCase();

        for (Product product : allProducts) {
            if (String.valueOf(product.getId()).toLowerCase().contains(searchString) ||
                    product.getName().toLowerCase().contains(searchString)) {
                productsFound.add(product);
            }
        }

        productsTable.setItems(productsFound);

        if (productsFound.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No products found.");
            alert.showAndWait();
        }
    }

    /** This method will check to see if the products search field for Parts table in the app is empty.
     * If empty, the table will be repopulated and shown to the user of whatever the inventory is.
     * @param event Represents that the method will perform some action. In this case, resets the products table to show all the existing parts. */
    public void onAddProductSearchClicked(KeyEvent event) {

        if (productSearchText.getText().isEmpty()) {
            productsTable.setItems(Inventory.getAllProducts());
        }
    }

    /** This method will display a notification to the user asking to confirm if they want to exit the program or not.
     * @param event Represents that the method will perform some action. In this case, the confirmation of closing the program. */
    public void onExit(ActionEvent event) {
        Alert exit = new Alert(Alert.AlertType.CONFIRMATION);
        exit.setTitle("Confirm Exit");
        exit.setContentText("Do you want to exit?");
        Optional<ButtonType> result = exit.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }
}
