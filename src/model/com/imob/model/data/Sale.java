package model.com.imob.model.data;

import java.sql.Date;

/**
 *
 * @author Matheus Michels
 */
public class Sale
    extends
        Identity
{
    private int immobileId;
    private int ownerId;
    private int buyerId;
    private double value;
    private int profit;
    private int plots;
    private String description;
    private Date date;

    public Sale()
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

    public int getBuyerId()
    {
        return buyerId;
    }

    public void setBuyerId(int buyerId)
    {
        this.buyerId = buyerId;
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

    public int getPlots() {
        return plots;
    }

    public void setPlots(int plots) {
        this.plots = plots;
    }
}