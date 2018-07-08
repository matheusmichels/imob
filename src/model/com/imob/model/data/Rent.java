/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.com.imob.model.data;

import java.sql.Date;

/**
 *
 * @author Matheus Michels
 */
public class Rent
    extends
        Identity
{
    private int immobileId;
    private int ownerId;
    private int renterId;
    private double value;
    private int profit;
    private int months;
    private String description;
    private Date date;

    public Rent()
    {
    }

    public int getImmobileId()
    {
        return immobileId;
    }

    public void setImmobileId( int immobileId )
    {
        this.immobileId = immobileId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId( int ownerId )
    {
        this.ownerId = ownerId;
    }

    public int getRenterId()
    {
        return renterId;
    }

    public void setRenterId(int rentedId)
    {
        this.renterId = rentedId;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate( Date date )
    {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }
}