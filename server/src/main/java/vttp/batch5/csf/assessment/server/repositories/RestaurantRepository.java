package vttp.batch5.csf.assessment.server.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.batch5.csf.assessment.server.models.MenuItem;
import vttp.batch5.csf.assessment.server.models.Order;
import vttp.batch5.csf.assessment.server.models.Response;

// Use the following class for MySQL database
@Repository
public class RestaurantRepository {

    @Autowired
    private JdbcTemplate template;

    String SQL_COUNT_QUERY = 
    """
        SELECT COUNT(*) AS COUNT FROM customers 
            WHERE username = ?
            AND password = ?
    """;
    
    public int checkUserValid(String username, String password) {

        int count = 0;

        SqlRowSet rs = template.queryForRowSet(
            SQL_COUNT_QUERY, 
            username,
            password
        );

        if (rs.next()) {
            count = rs.getInt("COUNT"); 
      
        }

        return count;
   
    }

    String SQL_INSERT_ORDER = 
    """
        INSERT INTO place_orders (order_id, payment_id, order_date, total, username)
            VALUES (?, ?, ?, ?, ?)         
    """;

    public void insertOrder(Order order, Response response) { 

        float totalPrice = 0;
        for (MenuItem item: order.getItems()) {
            float temp = item.getPrice() * item.getQuantity();
            totalPrice += temp;
        }

        template.update(SQL_INSERT_ORDER, 
            order.getOrder_id(),
            response.getPayment_id(), 
            response.getTimestamp(), 
            totalPrice, 
            order.getUsername()
        );

    }

}
