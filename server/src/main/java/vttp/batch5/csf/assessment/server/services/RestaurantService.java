package vttp.batch5.csf.assessment.server.services;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.batch5.csf.assessment.server.repositories.OrdersRepository;
import vttp.batch5.csf.assessment.server.repositories.RestaurantRepository;

@Service
public class RestaurantService {

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


}
