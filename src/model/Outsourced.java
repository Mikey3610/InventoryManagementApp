package model;

/** This class is used to get and set the values for when the user selects "Outsourced" in the Inventory Management System app. */
public class Outsourced extends Part{
    private String companyName;

    /** This is the constructor of the different variables that will be set for Outsourced parts.
     * @param id The ID for the Outsourced part.
     * @param name The name of the Outsourced part.
     * @param price The price of the Outsourced part.
     * @param stock The stock (or inventory level) of the Outsourced part.
     * @param min The minimum stock (or inventory level) of the Outsourced part.
     * @param max The maximum stock (or inventory level) of the Outsourced part.
     * @param companyName The company name of the Outsourced part.
     * */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** The setter for the Outsourced part.
     * @param companyName The company name of the Outsourced part. */
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    /** The getter for the Outsourced part.
     * @return Returns the company name of the Outsourced part. */
    public String getCompanyName(){
        return companyName;
    }
}
