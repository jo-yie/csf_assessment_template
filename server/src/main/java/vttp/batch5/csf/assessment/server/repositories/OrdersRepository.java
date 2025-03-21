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
  //  Native MongoDB query here
  
}