package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class is used to set and retrieve data related to the inventory of the parts/products in the Inventory Management System app. */
public class Inventory {
    private static int partId = 0;
    private static int productId = 0;
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** Method that adds a new part object to the inventory.
     * @param newPart Creates a new part object to be added. */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /** Method to delete a part currently selected by the user from the inventory.
     * @param selectedPart  The part currently selected from the user. */
    public static boolean deletePart(Part selectedPart){
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }

    /** Method that increments the part ID so they are unique.
     * @return Returns an incremented value of the part ID. */
    public static int getNewPartId(){
        return ++partId;
    }

    /** Method that searches through the parts by part ID.
     * @param partId The part ID that is searched for.
     * @return Returns the part ID if found and null if not found. */
    public static Part lookupPart(int partId) {
        Part partIntFound = null;

        for (Part part : allParts) {
            if (part.getId() == partId) {
                partIntFound = part;
            }
        }
        return partIntFound;
    }

    /** Method that searches through the parts by part name.
     * @param partName The part name that is searched for.
     * @return Returns the part name if found and null if not found. */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partStringFound = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getName().equals(partName)) {
                partStringFound.add(part);
            }
        }
        return partStringFound;
    }

    /** Method that searches through the products by product ID.
     * @param productId The product ID that is searched for.
     * @return Returns the product ID if found and null if not found. */
    public static Product lookupProduct(int productId) {
        Product productIntFound = null;

        for (Product product : allProducts) {
            if (product.getId() == productId) {
                productIntFound = product;
            }
        }
        return productIntFound;
    }

    /** Method that searches through the products by product name.
     * @param productName The product name that is searched for.
     * @return Returns the product name if found and null if not found. */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productStringFound = FXCollections.observableArrayList();

        for (Product product : allProducts) {
            if (product.getName().equals(productName)) {
                productStringFound.add(product);
            }
        }
        return productStringFound;
    }

    /** Method that updates a part in the list.
     * @param index The index of the part to be updated.
     * @param selectedPart The part that will be updated. */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /** Method that updates the a product in the list.
     * @param index The index of the product to be updated.
     * @param newProduct The product that will be updated. */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /** Method that gets all of the parts in the list.
     * @return Returns all the parts in the list. */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /** Method that gets all of the products in the list.
     * @return Returns all the products in the list. */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    /** Method that adds a new product to the list.
     * @param newProduct The product to be added to the list. */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /** Method that deletes a product from the list.
     * @param selectedProduct The product that is selected.
     * @return Returns true if the product is found and false if not found. */
    public static boolean deleteProduct(Product selectedProduct){
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }

    /** Method that increments the product ID so they are unique.
     * @return Returns an incremented value of the product ID. */
    public static int getNewProductId(){
        return ++productId;
    }


}
