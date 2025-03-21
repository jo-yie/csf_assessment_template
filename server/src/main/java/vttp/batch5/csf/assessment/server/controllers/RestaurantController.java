package vttp.batch5.csf.assessment.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import vttp.batch5.csf.assessment.server.services.RestaurantService;

@RestController
public class RestaurantController {

  @Autowired
  private RestaurantService restaurantService; 

  @GetMapping("/")
  public ResponseEntity<Object> get() { 
    return ResponseEntity.ok().body("hello");
  }

  // TODO: Task 2.2
  // You may change the method's signature
  @GetMapping("/api/menu")
  public ResponseEntity<Object> getMenus() {

    return ResponseEntity.ok()
      .body(restaurantService.getMenu());

    // return ResponseEntity.ok("testing");
  }

  // TODO: Task 4
  // Do not change the method's signature
  public ResponseEntity<String> postFoodOrder(@RequestBody String payload) {
    return ResponseEntity.ok("{}");
  }
}
