package vttp.batch5.csf.assessment.server.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp.batch5.csf.assessment.server.models.MenuItem;
import vttp.batch5.csf.assessment.server.models.Order;
import vttp.batch5.csf.assessment.server.models.Response;


@Repository
public class OrdersRepository {

  @Autowired
  private MongoTemplate template;

  // TODO: Task 2.2
  // You may change the method's signature
  // Write the native MongoDB query in the comment below
  //
  // db.menus.aggregate([
  //   {
  //       $sort: { name : 1 }
  //   }
  // ])
  //
  public List<Document> getMenu() {

    // Criteria criteria = new Criteria(); 

    SortOperation sortOperation = Aggregation
      .sort(Sort.Direction.ASC, "name");

    Aggregation pipeline = Aggregation.newAggregation(
      sortOperation
    );

    return template.aggregate(
      pipeline, 
      "menus", 
      Document.class).getMappedResults();

  }

  // TODO: Task 4
  // Write the native MongoDB query for your access methods in the comment below
  //
  // db.orders.insert({
  //     _id: "hey",
  //     order_id: "hey", 
  //     payment_id: "payment id", 
  //     username: "fred", 
  //     total: 22, 
  //     timestamp: 2025-02-02, 
  //     items: [
  //         { id: "xxx", price: 7.70, quantity: 2 }, 
  //         { id: "yyy", price: 7.70, quantity: 1 }
  //     ]
  // }
  // );
  //
  public void insertOrder(Order order, Response response) {

    float totalPrice = 0;
    for (MenuItem item: order.getItems()) {
        float temp = item.getPrice() * item.getQuantity();
        totalPrice += temp;
    }

    Document doc = new Document(); 

    doc.put("_id", order.getOrder_id());
    doc.put("order_id", order.getOrder_id());
    doc.put("payment_id", response.getPayment_id());
    doc.put("username", order.getUsername());
    doc.put("total", totalPrice);
    doc.put("timestamp", response.getTimestamp());
    doc.put("items", order.getItems());

    template.insert(doc, "orders");

  }
  
}
