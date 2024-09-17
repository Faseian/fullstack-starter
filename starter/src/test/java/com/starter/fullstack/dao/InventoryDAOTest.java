package com.starter.fullstack.dao;

import com.starter.fullstack.api.Inventory;
import java.util.List;
import javax.annotation.Resource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Test Inventory DAO.
 */
@DataMongoTest
@RunWith(SpringRunner.class)
public class InventoryDAOTest {
  @ClassRule
  public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

  @Resource
  private MongoTemplate mongoTemplate;
  private InventoryDAO inventoryDAO;
  private static final String NAME = "Amber";
  private static final String NAMETWO = "Nathan";
  private static final String PRODUCT_TYPE = "hops";

  @Before
  public void setup() {
    this.inventoryDAO = new InventoryDAO(this.mongoTemplate);
  }

  @After
  public void tearDown() {
    this.mongoTemplate.dropCollection(Inventory.class);
  }

  /**
   * Test Find All method.
   */
  @Test
  public void findAll() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    this.mongoTemplate.save(inventory);
    List<Inventory> actualInventory = this.inventoryDAO.findAll();
    Assert.assertFalse(actualInventory.isEmpty());
  }

  @Test
  public void create() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    inventoryDAO.create(inventory);
    Inventory inventoryTwo = new Inventory();
    inventoryTwo.setName("NAME");
    inventoryTwo.setProductType(PRODUCT_TYPE);
    inventoryDAO.create(inventoryTwo);
    List<Inventory> actualInventory = this.inventoryDAO.findAll();
    Assert.assertEquals(2, actualInventory.size());
  }

  @Test
  public void createMultiple() {
    Inventory inventory = new Inventory();
    Inventory inventory1 = new Inventory();
    inventory.setName("Nathan");
    inventory.setProductType(PRODUCT_TYPE);
    inventory1.setName(NAME);
    inventory1.setProductType(PRODUCT_TYPE);
    inventoryDAO.create(inventory);
    inventoryDAO.create(inventory1);
    List<Inventory> actualInventory = this.inventoryDAO.findAll();
    Assert.assertEquals(2, actualInventory.size());
    Assert.assertEquals(inventory, actualInventory.get(0));
    Assert.assertEquals(inventory1, actualInventory.get(1));
  }

  @Test
  public void retrieve() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    inventoryDAO.create(inventory);
    List<Inventory> actualList = this.inventoryDAO.findAll();
    String inventoryId = actualList.get(0).getId().toString();
    Assert.assertEquals(inventory.getName(), inventoryDAO.retrieve(inventoryId).get().getName());
  }

  @Test
  public void update() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    inventoryDAO.create(inventory);
    inventory.setName(NAMETWO);
    List<Inventory> actualList = this.inventoryDAO.findAll();
    String inventoryId = actualList.get(0).getId();
    inventory.setId(inventoryId);
    inventoryDAO.update(inventoryId, inventory);
    Assert.assertEquals(inventory.getName(), inventoryDAO.retrieve(inventoryId).get().getName());
  }
  @Test
  public void delete() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    inventoryDAO.create(inventory);
    List<Inventory> actualList = this.inventoryDAO.findAll();
    String inventoryId = actualList.get(0).getId();
    inventoryDAO.delete(inventoryId);
    List<Inventory> actualInventory = this.inventoryDAO.findAll();
    Assert.assertEquals(0, actualInventory.size());
  }
}
