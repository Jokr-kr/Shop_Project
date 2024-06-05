package com.github.jokrkr.shopproject.server.models;

public class Item {
    String _name;
    double _price;
    String _type;


    public Item(String type, String name, double price) {
        this._name = name;
        this._price = price;
        this._type = type;
    }

    public String getName() {return _name;}
    public double getPrice() {return _price;}
    public String getType() {return _type;}
}
