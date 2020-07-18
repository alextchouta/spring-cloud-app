package org.sid.billingservice.RestController;

import org.sid.billingservice.Entities.Bill;
import org.sid.billingservice.Repositories.BillRepository;
import org.sid.billingservice.Repositories.ProductItemRepository;
import org.sid.billingservice.Services.CustomerService;
import org.sid.billingservice.Services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillRestController {
    @Autowired
    public BillRepository billRepository;
    @Autowired
    public ProductItemRepository productItemRepository;
    @Autowired
    public CustomerService customerService;
    @Autowired
    public InventoryService inventoryService;

    @GetMapping("/fullBill/{id}")
    public Bill getBill(@PathVariable(name = "id") Long id){
        Bill bill = billRepository.findById(id).get();
        bill.getProductItems().forEach(pi -> pi.setProduct(inventoryService.findCustomerById(pi.getId())));
        return bill;
    }
}
