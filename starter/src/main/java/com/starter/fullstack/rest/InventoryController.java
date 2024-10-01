package com.starter.fullstack.rest;

import com.starter.fullstack.api.Inventory;
import com.starter.fullstack.dao.InventoryDAO;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Inventory Controller.
 */
@RestController
@RequestMapping("/inventory")
public class InventoryController {
  private final InventoryDAO inventoryDAO;

  /**
   * Default Constructor.
   * @param inventoryDAO inventoryDAO.
   */
  public InventoryController(InventoryDAO inventoryDAO) {
    Assert.notNull(inventoryDAO, "Inventory DAO must not be null.");
    this.inventoryDAO = inventoryDAO;
  }

  /**
   * Find Products.
   * @return List of Product.
   */
  @GetMapping
  public List<Inventory> findInventories() {
    return this.inventoryDAO.findAll();
  }

  @GetMapping("/getById")
  public Optional<Inventory> findInventoryById(String id) {
    return  this.inventoryDAO.retrieve(id);
  }

  @PostMapping
  public Inventory saveInventory(@Valid @RequestBody Inventory inventory) {
    return this.inventoryDAO.create(inventory);
  }

  @PostMapping("/update")
  public Optional<Inventory> updateInventory(@Valid @RequestParam String id, @Valid @RequestBody Inventory inventory) {
    Assert.notNull(id, "Id must not be null");
    return this.inventoryDAO.update(id, inventory);
  }

  @DeleteMapping
  public List<String> deleteInventoryById(@RequestBody List<String> ids) {
    Assert.notNull(ids, "Inventory Ids were not provided");
    for (String id : ids) {
      this.inventoryDAO.delete(id);
    }
    return ids;
  }
}