package org.sid.billingservice;

import org.sid.billingservice.Dto.Customer;
import org.sid.billingservice.Dto.Product;
import org.sid.billingservice.Entities.Bill;
import org.sid.billingservice.Entities.ProductItem;
import org.sid.billingservice.Repositories.BillRepository;
import org.sid.billingservice.Repositories.ProductItemRepository;
import org.sid.billingservice.Services.CustomerService;
import org.sid.billingservice.Services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;


import java.util.Date;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(BillRepository billRepository, ProductItemRepository productItemRepository,
                            CustomerService customerService, InventoryService inventoryService) {
        return args -> {

            Customer c1 = customerService.findCustomerById(1L);
            System.out.println("*************** CUSTOMER ********************");
            System.out.println(c1);
            System.out.println("*************************************");

            Bill bill1 = billRepository.save(new Bill(null, new Date(), c1.getId(),null, null));

            PagedModel<Product> products = inventoryService.findAllProducts();
            products.getContent().forEach(p ->{
                productItemRepository.save(new ProductItem(null, p.getId(), null, p.getPrix(), 30, bill1));
            });


        };
    }

}




