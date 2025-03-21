package vttp.batch5.csf.assessment.server.models;

import java.time.LocalDateTime;
import java.util.Date;

public class Response {

    private String payment_id; 
    private String order_id; 
    private Date timestamp;
    public String getPayment_id() {
        return payment_id;
    }
    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }
    public String getOrder_id() {
        return order_id;
    }
    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    public Response() {
    }
    public Response(String payment_id, String order_id, Date timestamp) {
        this.payment_id = payment_id;
        this.order_id = order_id;
        this.timestamp = timestamp;
    }

    
    
}
