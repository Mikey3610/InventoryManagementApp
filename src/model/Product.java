package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class is used to set and retrieve data related to the products in the Inventory Management System app. */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** This is the constructor of the different variables that will be set for products.
     * @param id The ID of the products.
     * @param name The name of the product.
     * @param price The price of the product.
     * @param stock The stock (or inventory level) of the products.
     * @param min The minimum stock (or inventory level) of the product.
     * @param max The maximum stock (or inventory level) of the product.
     * */
    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** Getter method that gets the ID of the product.
     * @return Returns the ID of the product. */
    public int getId(){
        return id;
    }

    /** Setter method that sets the ID of the product.
     * @param id The ID of the product. */
    public void setId(int id) {
        this.id = id;
    }

    /** Getter method that returns the name of the product.
     * @return Returns name of the product. */
    public String getName() {
        return name;
    }

    /** Setter method that sets the name of the product.
     * @param name The name of the product. */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter method that returns the price of the product.
     * @return Returns the price of the product. */
    public double getPrice() {
        return price;
    }

    /** Setter method that sets the price of the product.
     * @param price The price of the product. */
    public void setPrice(double price) {
        this.price = price;
    }

    /** Getter method that gets the stock (inventory level) of the product.
     * @return Returns the stock (inventory level) of the product. */
    public int getStock() {
        return stock;
    }

    /** Setter method that sets the stock (inventory level) of the product.
     * @param stock The stock (inventory level) of the product. */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** Getter method that returns the minimum stock of the product.
     * @return Returns the minimum value of the stock for the product. */
    public int getMin() {
        return min;
    }

    /** Setter method that sets the minimum value of the stock of the product.
     * @param min The minimum value of the stock of the product. */
    public void setMin(int min) {
        this.min = min;
    }

    /** Getter method that returns the maximum value of the stock of the product.
     * @return Returns the maximum value of the stock for the product. */
    public int getMax() {
        return max;
    }

    /** Setter method that sets the maximum value of the stock of the product.
     * @param max The maximum value of the stock of the product. */
    public void setMax(int max) {
        this.max = max;
    }

    /** Method that adds an associated part to the list.
     * @param part The part to be added to the associated parts list. */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /** Method that deletes an associated part that is selected from the list.
     * @param selectedAssociatedPart The part that is selected from the associated table to be deleted.
     * @return Returns a true value and deletes the part if the part is found and returns false if the part is not found. */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else
            return false;
    }

    /** Method that returns the list of associated parts.
     * @return Returns the list of associated parts. */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
