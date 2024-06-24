package com.github.jokrkr.shopproject.server.models;

public class Item {
    private final String _name;
    private final double _price;
    private final String _type;
    private final int _quantity;
    private final double _value;

    public Item(String type, String name, double price, int quantity, double value) {
        this._name = name;
        this._price = price;
        this._type = type;
        this._quantity = quantity;
        this._value = value;
    }

    public String getType() {return _type;}

    public String getName() {return _name;}

    public double getPrice() {return _price;}

    public int getQuantity() {return _quantity;}

    public double getValue() {return _value;}
}
