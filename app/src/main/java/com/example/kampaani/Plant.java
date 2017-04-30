package com.example.kampaani;

/**
 * Created by Belal on 2/23/2016.
 */
public class Plant {
    //name and address string
    private String number;
    private String name;

    public Plant() {
      /*Blank default constructor essential for Firebase*/
    }
    //Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}