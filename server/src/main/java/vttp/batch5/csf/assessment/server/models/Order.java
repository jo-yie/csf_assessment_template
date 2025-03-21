package vttp.batch5.csf.assessment.server.models;

import java.util.List;
import java.util.UUID;

public class Order {

    private String order_id;
    private String username; 
    private String password; 
    private List<MenuItem> items;
    public Order() {
        this.order_id = UUID.randomUUID().toString().substring(0, 8);
    }
    public Order(String order_id, String username, String password, List<MenuItem> items) {
        this.order_id = order_id;
        this.username = username;
        this.password = password;
        this.items = items;
    }
    public String getOrder_id() {
        return order_id;
    }
    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<MenuItem> getItems() {
        return items;
    }
    public void setItems(List<MenuItem> items) {
        this.items = items;
    }


    
}
