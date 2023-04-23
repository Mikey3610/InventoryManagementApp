package model;

/** This class is used to get and set the values for when the user selects "In-house" in the Inventory Management System app. */
public class InHouse extends Part {
    private int machineId;

    /** This is the constructor of the different variables that will be set for InHouse parts.
     * @param id The ID for the In-house part.
     * @param name The name of the In-house part.
     * @param price The price of the In-house part.
     * @param stock The stock (or inventory level) of the In-house part.
     * @param min The minimum stock (or inventory level) of the In-house part.
     * @param max The maximum stock (or inventory level) of the In-house part.
     * @param machineId The machine ID of the In-house part.
     * */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId){
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** The setter for the In-house part.
     * @param machineId The machine ID of the In-house part. */
    public void setMachineId(int machineId){
        this.machineId = machineId;
    }

    /** The getter for the In-house part.
     * @return Returns the Machine ID of the In-house part. */
    public int getMachineId(){
        return machineId;
    }
}
