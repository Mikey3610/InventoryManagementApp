package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

/** This class is the main controller and creates an app that displays a menu for an inventory management system. */
public class Main extends Application {

    /** This will create the stage for the home screen of this app titled "Inventory Management System" from the MainForm fxml file.
     * @param stage This is the stage for the first screen of the app titled "Inventory Management System". */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(root, 970, 450));
        stage.show();
    }

    /** This method will indicate that the program has been terminated/closed when the user exits the app. */
    @Override
    public void stop(){
        System.out.println("Program Terminated");
    }

/** This is the main method.
 * This is the first method that gets called when you run this Java program.
 * The variables listed here are the items used as sample parts/products in the app.
 * So when the user opens the program, the initial page will be populated with these variables.
 * @param args The main method arguments.
 * */
    public static void main(String[] args) {
        int partId = Inventory.getNewPartId();
        InHouse brakes = new InHouse(partId, "Brakes", 10.00, 10, 1, 25, 101);

        partId = Inventory.getNewPartId();
        InHouse wheel = new InHouse(partId,"Wheel", 11.00,16,1,25,102);

        partId = Inventory.getNewPartId();
        InHouse seat = new InHouse(partId,"Seat", 15.00,10,1,25,103);

        Inventory.addPart(brakes);
        Inventory.addPart(wheel);
        Inventory.addPart(seat);

        int productId = Inventory.getNewProductId();
        Product giantBike = new Product(productId,"Giant Bike", 299.99, 5, 1, 50);

        giantBike.addAssociatedPart(brakes);
        giantBike.addAssociatedPart(wheel);
        giantBike.addAssociatedPart(seat);

        Inventory.addProduct(giantBike);

        launch(args);

    }

}
