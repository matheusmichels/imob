/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.com.imob.model.db.fetcher;

import java.sql.ResultSet;
import model.com.imob.model.data.Payment;

/**
 *
 * @author Matheus Michels
 */
public class PaymentFetcher
    implements
        Fetcher<Payment>
{
    @Override
    public Payment fetch( ResultSet resultSet ) throws Exception
    {
        int i = 1;
        
        Payment payment = new Payment();
        
        payment.setId( resultSet.getInt( i++ ) );
        payment.setSequence( resultSet.getInt( i++ ) );
        payment.setSaleId( resultSet.getInt( i++ ) );
        payment.setRentId( resultSet.getInt( i++ ) );
        payment.setValue( resultSet.getDouble( i++ ) );
        payment.setDate( resultSet.getDate( i++ ) );
        
        return payment;
    }
}