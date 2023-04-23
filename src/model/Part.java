package model;

/** This class is used to set and retrieve data related to the parts in the Inventory Management System app. */
public abstract class Part {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** This is the constructor of the different variables that will be set for parts.
     * @param id The ID of the parts.
     * @param name The name of the part.
     * @param price The price of the part.
     * @param stock The stock (or inventory level) of the part.
     * @param min The minimum stock (or inventory level) of the part.
     * @param max The maximum stock (or inventory level) of the part.
     * */
    public Part(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** Getter method that gets the ID of the part.
     * @return Returns the ID of the part. */
    public int getId(){
        return id;
    }

    /** Setter method that sets the ID of the part.
     * @param id The ID of the part. */
    public void setId(int id) {
        this.id = id;
    }

    /** Getter method that returns the name of the part.
     * @return Returns name of the part. */
    public String getName() {
        return name;
    }

    /** Setter method that sets the name of the part.
     * @param name The name of the part. */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter method that returns the price of the part.
     * @return Returns the price of the part. */
    public double getPrice() {
        return price;
    }

    /** Setter method that sets the price of the part.
     * @param price The price of the part. */
    public void setPrice(double price) {
        this.price = price;
    }

    /** Getter method that gets the stock (inventory level) of the part.
     * @return Returns the stock (inventory level) of the part. */
    public int getStock() {
        return stock;
    }

    /** Setter method that sets the stock (inventory level) of the part.
     * @param stock The stock (inventory level) of the part. */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** Getter method that returns the minimum stock of the part.
     * @return Returns the minimum value of the stock for the part. */
    public int getMin() {
        return min;
    }

    /** Setter method that sets the minimum value of the stock of the part.
     * @param min The minimum value of the stock of the part. */
    public void setMin(int min) {
        this.min = min;
    }

    /** Getter method that returns the maximum value of the stock of the part.
     * @return Returns the maximum value of the stock for the part. */
    public int getMax() {
        return max;
    }

    /** Setter method that sets the maximum value of the stock of the part.
     * @param max The maximum value of the stock of the part. */
    public void setMax(int max) {
        this.max = max;
    }
}
