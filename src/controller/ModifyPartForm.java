package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class is the controller for modifying parts in the Inventory Management System app. */
public class ModifyPartForm implements Initializable {

    public Part selectedPart;
    public Label inHouseOrOutsourced;
    public TextField modifyPartIDText;
    public TextField modifyPartNameText;
    public TextField modifyPartInvText;
    public TextField modifyPartPriceText;
    public TextField modifyPartMaxText;
    public TextField inHouseOrOutsourcedText;
    public TextField modifyPartMinText;
    public Button saveButton;
    public Button cancelButton;
    public RadioButton inHouse;
    public RadioButton outsourced;

    /** This method initializes the "Modify Part" page of the app.
     * The parts table will be populated with the values of the part that was selected by the user.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedPart = MainForm.getPartToModify();

        if (selectedPart instanceof InHouse) {
            inHouse.setSelected(true);
            inHouseOrOutsourced.setText("Machine ID");
            inHouseOrOutsourcedText.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        }

        if (selectedPart instanceof Outsourced){
            outsourced.setSelected(true);
            inHouseOrOutsourced.setText("Company Name");
            inHouseOrOutsourcedText.setText(((Outsourced) selectedPart).getCompanyName());
        }

        modifyPartIDText.setText(String.valueOf(selectedPart.getId()));
        modifyPartNameText.setText(selectedPart.getName());
        modifyPartInvText.setText(String.valueOf(selectedPart.getStock()));
        modifyPartPriceText.setText(String.valueOf(selectedPart.getPrice()));
        modifyPartMaxText.setText(String.valueOf(selectedPart.getMax()));
        modifyPartMinText.setText(String.valueOf(selectedPart.getMin()));
    }

    /** This method will cancel the current actions on the page and return the the main Inventory Management System page.
     * It will bring up a pop-up window to confirm whether to exit or not.
     * @param actionEvent Represents that the method will perform some action. In this case, the action of notifying the user and confirming cancellation from the "Modify Part Form" page. */
    public void onCancelButton(ActionEvent actionEvent) throws IOException {
        Alert cancellation = new Alert(Alert.AlertType.CONFIRMATION);
        cancellation.setTitle("Confirm Cancellation");
        cancellation.setContentText("Do you want to cancel and return to Inventory Management Screen?");
        Optional<ButtonType> result = cancellation.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            toMainForm(actionEvent);
        }
    }

    /** This is the method used to go back to the main page titled "Inventory Management System".
     * @param actionEvent The action of returning to the home page of the app titled "Inventory Management System". */
    private void toMainForm(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 970, 450);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /** This method will convert the label in the "Modify Part Form" to "Machine ID" when the user clicks on the In-House radio button.
     * @param actionEvent The action of setting the text in the inHouseOrOutsourced text label. */
    public void onInHouse(ActionEvent actionEvent) {
        inHouseOrOutsourced.setText("Machine ID");
    }

    /** This method will convert the label in the "Modify Part Form" to "Company Name" when the user clicks on the In-House radio button.
     * @param actionEvent The action of setting the text in the inHouseOrOutsourced text label. */
    public void onOutsourced(ActionEvent actionEvent) {
        inHouseOrOutsourced.setText("Company Name");
    }

    /** This method takes all of the part input from the user and sets them in the corresponding variables.
     * An error message will popup in the scenario that certain criteria are not met.
     * These scenarios are: user not inputting integers for machine ID, inputting invalid values or leaving the fields blank, and not setting the proper stock values.
     * @param event Represents that the method will perform some action. In this case, setting all the part variables and notifying the user if there are any improper inputs.
     * */
    public void onSaveButton(ActionEvent event) throws IOException {

        try {
            int id = selectedPart.getId();
            String name = modifyPartNameText.getText();
            double price = Double.parseDouble(modifyPartPriceText.getText());
            int stock = Integer.parseInt(modifyPartInvText.getText());
            int min = Integer.parseInt(modifyPartMinText.getText());
            int max = Integer.parseInt(modifyPartMaxText.getText());
            int machineId;
            String companyName;
            boolean partAdded = false;

            if (minStock(min, max) && validInventory(min, max, stock)) {

                if (inHouse.isSelected()) {
                    try {
                        machineId = Integer.parseInt(inHouseOrOutsourcedText.getText());
                        InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                        Inventory.addPart(newInHousePart);
                        partAdded = true;
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Machine ID Value Entered. Please input integer values only.");
                        alert.showAndWait();
                        return;
                    }
                }

                if (outsourced.isSelected()) {
                    companyName = inHouseOrOutsourcedText.getText();
                    Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max,
                            companyName);
                    Inventory.addPart(newOutsourcedPart);
                    partAdded = true;
                }

                if (partAdded) {
                    Inventory.deletePart(selectedPart);
                    toMainForm(event);
                }
            }
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Form contains invalid input values or blanks. Please check and input proper values.");
            alert.showAndWait();
        }
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
            Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory value must be between minimum and maximum stock values.");
            alert.showAndWait();
        }

        return validStock;
    }

    /** This method ensures that the minimum value set for the part is greater than zero and less than the maximum value.
     * If the value is out of these bounds, an error message is displayed to the user.
     * @param min The minimum value of the inventory for the part.
     * @param max The maximum value of the inventory for the part.
     * @param stock The value of stock to check between min and max.
     * @return Returns the value of true to the validInventory variable if the conditions of the method are met (min value greater than 0 and less than max value).
     * */
    public boolean validInventory(int min, int max, int stock) {

        boolean validInv = true;

        if (stock < min || stock > max) {
            validInv = false;
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid minimum stock value entered. Must be greater than 0 and less than maximum value.");
            alert.showAndWait();
        }

        return validInv;
    }
}
