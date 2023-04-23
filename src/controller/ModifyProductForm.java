package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class is the controller for modifying products in the Inventory Management System app. */
public class ModifyProductForm implements Initializable {
    public TextField addProductIDText;
    public TextField modifyProductNameText;
    public TextField modifyProductInvText;
    public TextField modifyProductPriceText;
    public TextField modifyProductMaxText;
    public TextField modifyProductMinText;
    public TableView<Part> partsTable;
    public TableColumn<Part, Integer> partIDCol;
    public TableColumn<Part, String> partNameCol;
    public TableColumn<Part, Integer> partInventoryCol;
    public TableColumn<Part, Double> partPriceCol;
    public TableView<Part> assocPartTable;
    public TableColumn<Part, Integer> assocPartIDCol;
    public TableColumn<Part, String> assocPartNameCol;
    public TableColumn<Part, Integer> assocPartInventoryCol;
    public TableColumn<Part, Double> assocPartPriceCol;
    public TextField partSearchText;
    public Button addButton;
    public Button removeButton;
    public Button cancelButton;
    public Button saveButton;
    public Button partSearchButton;
    Product selectedProduct;
    public ObservableList<Part> assocParts = FXCollections.observableArrayList();

    /** This method initializes the "Modify Product" page of the app.
     * The modify product table will be populated with the values of the product that was selected by the user.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedProduct = MainForm.getProductToModify();
        assocParts = selectedProduct.getAllAssociatedParts();

        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTable.setItems(Inventory.getAllParts());

        assocPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        assocPartTable.setItems(assocParts);

        addProductIDText.setText(String.valueOf(selectedProduct.getId()));
        modifyProductNameText.setText(selectedProduct.getName());
        modifyProductInvText.setText(String.valueOf(selectedProduct.getStock()));
        modifyProductPriceText.setText(String.valueOf(selectedProduct.getPrice()));
        modifyProductMaxText.setText(String.valueOf(selectedProduct.getMax()));
        modifyProductMinText.setText(String.valueOf(selectedProduct.getMin()));
    }

    /** Adds the selected part in the parts table to the associated parts table and is populated there.
     * @param ActionEvent Event of taking the selected part from the table and setting the values to the associated parts table. */
    public void onAddButton(ActionEvent ActionEvent) {

        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "A part from the above table needs to be selected.");
            alert.showAndWait();
        } else {
            assocParts.add(selectedPart);
            assocPartTable.setItems(assocParts);
        }
    }

    /** This method will cancel the current actions on the page and return the the main Inventory Management System page.
     * It will bring up a pop-up window to confirm whether to exit or not.
     * @param actionEvent Represents that the method will perform some action. In this case, the action of notifying the user and confirming cancellation from the "Modify Part Form" page. */
    public void onCancelButton(ActionEvent actionEvent) throws IOException {
        Alert cancellation = new Alert(Alert.AlertType.CONFIRMATION);
        cancellation.setTitle("Confirm Cancellation");
        cancellation.setContentText("Discard changes and return to Inventory Management Screen?");
        Optional<ButtonType> result = cancellation.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            toMainForm(actionEvent);
        }
    }

    /** This is the method used to go back to the main page titled "Inventory Management System".
     * @param actionEvent The action of returning to the home page of the app titled "Inventory Management System". */
    public void toMainForm(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 970, 450);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /** This method takes all of the part input from the user and sets them in the corresponding variables to save.
     * An error message will popup in the scenario that certain criteria are not met.
     * These scenarios are: user not inputting integers for machine ID, inputting invalid values or leaving the fields blank, and not setting the proper stock values.
     * @param event Represents that the method will perform some action. In this case, setting all the part variables and notifying the user if there are any improper inputs.
     * */
    public void onPartSearchButton(ActionEvent event) {

        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        String searchString = partSearchText.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(searchString) ||
                    part.getName().contains(searchString)) {
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
    public void addPartSearchClicked(KeyEvent event) {

        if (partSearchText.getText().isEmpty()) {
            partsTable.setItems(Inventory.getAllParts());
        }
    }

    /** This method will remove the associated part from the table and provide a dialog window to the user to confirm removal.
     * @param event Event action will remove the associated part from the table.
     * */
    public void onRemoveButton(ActionEvent event) {

        Part selectedPart = assocPartTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No parts selected.");
            alert.showAndWait();
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Do you want to remove the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                assocParts.remove(selectedPart);
                assocPartTable.setItems(assocParts);
            }
        }
    }

    /** This method takes all of the part input from the user and sets them in the corresponding variables.
     * An error message will popup in the scenario that certain criteria are not met.
     * These scenarios are: user not inputting integers for machine ID, inputting invalid values or leaving the fields blank, and not setting the proper stock values.
     * @param event Represents that the method will perform some action. In this case, setting all the part variables and notifying the user if there are any improper inputs.
     * */
    public void onSaveButton(ActionEvent event) throws IOException {

        try {
            int id = selectedProduct.getId();
            String name = modifyProductNameText.getText();
            double price = Double.parseDouble(modifyProductPriceText.getText());
            int stock = Integer.parseInt(modifyProductInvText.getText());
            int min = Integer.parseInt(modifyProductMinText.getText());
            int max = Integer.parseInt(modifyProductMaxText.getText());

            if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Part name is empty. Please input a name.");
                alert.showAndWait();
            } else {
                if (minStock(min, max) && validInventory(min, max, stock)) {

                    Product newProduct = new Product(id, name, price, stock, min, max);

                    for (Part part : assocParts) {
                        newProduct.addAssociatedPart(part);
                    }

                    Inventory.addProduct(newProduct);
                    Inventory.deleteProduct(selectedProduct);
                    toMainForm(event);
                }
            }
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Form contains invalid input values or blanks. Please check and input proper values.");
            alert.showAndWait();
        }
    }

    /** This method ensures that the minimum value set for the part is greater than zero and less than the maximum value.
     * If the value is out of these bounds, an error message is displayed to the user.
     * @param min The minimum value of the inventory for the part.
     * @param max The maximum value of the inventory for the part.
     * @param stock The value of stock to check between min and max.
     * @return Returns the value of true to the validInventory variable if the conditions of the method are met (min value greater than 0 and less than max value).
     * */
    private boolean validInventory(int min, int max, int stock) {

        boolean validInv = true;

        if (stock < min || stock > max) {
            validInv = false;
            Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory value must be between minimum and maximum stock values.");
            alert.showAndWait();
        }

        return validInv;
    }

    /** This method ensures that the inventory value set by the user is less than or equal to the maximum stock and greater than or equal to the minimum stock.
     * If the value is out of these bounds, an error message is displayed to the user.
     * @param min The minimum value of the inventory for the part.
     * @param max The maximum value of the inventory for the part.
     * @return Returns the value of true to the validStock variable if the conditions of the method are met (inventory between min and max).
     * */
    public boolean minStock(int min, int max) {

        boolean validStock = true;

        if (min <= 0 || min >= max) {
            validStock = false;
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid minimum stock value entered. Must be greater than 0 and less than maximum value.");
            alert.showAndWait();
        }

        return validStock;
    }


}
