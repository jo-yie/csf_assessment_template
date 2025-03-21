package vttp.batch5.csf.assessment.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp.batch5.csf.assessment.server.models.MenuItem;
import vttp.batch5.csf.assessment.server.models.Order;
import vttp.batch5.csf.assessment.server.models.Response;
import vttp.batch5.csf.assessment.server.services.RestaurantService;

@RestController
public class RestaurantController {

  @Autowired
  private RestaurantService restaurantService; 

  // @GetMapping("/")
  // public ResponseEntity<Object> get() { 
  //   return ResponseEntity.ok().body("hello");
  // }

  // TODO: Task 2.2
  // You may change the method's signature
  @GetMapping("/api/menu")
  public ResponseEntity<Object> getMenus() {

    return ResponseEntity.ok()
      .body(restaurantService.getMenu());

  }

  // TODO: Task 4
  // Do not change the method's signature
  @PostMapping("/api/food_order")
  public ResponseEntity<String> postFoodOrder(@RequestBody String payload) {

    System.out.println(payload);
    
    try { 

      Order order = restaurantService.getOrder(payload);
      Response response = restaurantService.processRequest(order);

      float totalPrice = 0;
      for (MenuItem item: order.getItems()) {
        float temp = item.getPrice() * item.getQuantity();
        totalPrice += temp;
      }
      
      JsonObject jo = Json.createObjectBuilder()
        .add("orderId", order.getOrder_id())
        .add("paymentId", response.getPayment_id())
        .add("total", totalPrice)
        .add("timestamp", response.getTimestamp().getTime())
        .build();

      return ResponseEntity.ok().body(jo.toString());

    } catch (Exception e) {

      JsonObject jo = Json.createObjectBuilder()
      .add("error", e.getMessage())
      .build();

      return ResponseEntity.status(401).body(jo.toString());

    }

  }
}
