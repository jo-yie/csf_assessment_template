package vttp.batch5.csf.assessment.server.services;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import vttp.batch5.csf.assessment.server.models.MenuItem;
import vttp.batch5.csf.assessment.server.models.Order;
import vttp.batch5.csf.assessment.server.models.Response;
import vttp.batch5.csf.assessment.server.repositories.OrdersRepository;
import vttp.batch5.csf.assessment.server.repositories.RestaurantRepository;

@Service
public class RestaurantService {

  private String urlEndpoint = "https://payment-service-production-a75a.up.railway.app/api/payment";
  private RestTemplate restTemplate = new RestTemplate(); 

  // mysql 
  @Autowired
  private RestaurantRepository restaurantRepository;

  // mongo db
  @Autowired
  private OrdersRepository ordersRepository; 

  // TODO: Task 2.2
  // You may change the method's signature
  public List<Document> getMenu() {

    return ordersRepository.getMenu();

  }
  
  // TODO: Task 4
  public Order getOrder(String payload) { 

    // convert JSON string into Order POJO 
    Order order = jsonStringToPojo(payload);
    return order;

  }

  public Response processRequest(Order order) {

    if (restaurantRepository.checkUserValid(order.getUsername(), order.getPassword()) == 1) {
      
      RequestEntity<String> request = RequestEntity
        .post(urlEndpoint)
        .contentType(MediaType.APPLICATION_JSON) 
        .accept(MediaType.APPLICATION_JSON)
        .header("X-Authenticate", order.getUsername())
        .body(buildRequest(order)); 

      ResponseEntity<Response> response;

      try {

        response = restTemplate.exchange(request, Response.class);
        Response r = response.getBody();
        System.out.println(">>>PAYMENT ID: " + r.getPayment_id());
        System.out.println(">>>ORDER ID: " + r.getOrder_id());
        System.out.println(">>>TIMESTAMP: " + r.getTimestamp());

        // insert into mongo + sql 
        restaurantRepository.insertOrder(order, r);
        ordersRepository.insertOrder(order, r);

        return r;
  
      } catch (Exception e) {
  
        // send error message to controller
        throw new RuntimeException(e.getMessage());

      }

    } else { 
      throw new RuntimeException("Invalid username and/or password");
    }

  }

  private String buildRequest(Order order) {
    
    float totalPrice = 0;
    
    for (MenuItem item: order.getItems()) {
      float temp = item.getPrice() * item.getQuantity();
      totalPrice += temp;
    }

    JsonObject jo = Json.createObjectBuilder()
      .add("order_id", order.getOrder_id())
      .add("payer", order.getUsername())
      .add("payee", "Leong Jo Yie")
      .add("payment", totalPrice)
      .build();

    return jo.toString();

  }

  private Order jsonStringToPojo(String jsonString) {
    StringReader sr = new StringReader(jsonString);
    JsonReader jr = Json.createReader(sr); 
    JsonObject jo = jr.readObject();

    Order order = new Order();
    order.setUsername(jo.getString("username"));
    order.setPassword(jo.getString("password"));

    JsonArray itemsJson = jo.getJsonArray("items");
    List<MenuItem> items = new ArrayList<>(); 
    for (JsonValue jv: itemsJson) {

      JsonObject temp = jv.asJsonObject();

      MenuItem item = new MenuItem(); 
      item.setId(temp.getString("id"));
      item.setPrice((float) temp.getJsonNumber("price").doubleValue());
      item.setQuantity(temp.getInt("quantity"));

      items.add(item);

    }

    order.setItems(items);

    return order;
  }

}
