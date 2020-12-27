package com.linkedin.linkedinbatchv2;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

public class OrderItemPreparedStatementSetter implements ItemPreparedStatementSetter<Order> {

    public void setValues(Order order, PreparedStatement ps) throws SQLException {

        ps.setLong(1, order.getOrderId());
        ps.setString(2, order.getFirstName().toUpperCase());
        ps.setString(3, order.getLastName());
        ps.setString(4, order.getEmail());
        ps.setString(5, order.getItemId());
        ps.setString(6, order.getItemName());
        ps.setBigDecimal(7, order.getCost());
        ps.setDate(8, new Date(order.getShipDate().getTime()));
    }
}
