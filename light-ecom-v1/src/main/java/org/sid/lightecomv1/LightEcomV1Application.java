package org.sid.lightecomv1;

import net.bytebuddy.utility.RandomString;
import org.sid.lightecomv1.entities.Category;
import org.sid.lightecomv1.entities.Product;
import org.sid.lightecomv1.repositories.CategoryRepository;
import org.sid.lightecomv1.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.Random;

@SpringBootApplication
public class LightEcomV1Application {

    public static void main(String[] args) {
        SpringApplication.run(LightEcomV1Application.class, args);
    }

    @Bean
    CommandLineRunner start(ProductRepository productRepository, CategoryRepository categoryRepository, RepositoryRestConfiguration restConfiguration){
        return args -> {
            restConfiguration.exposeIdsFor(Product.class, Category.class);
            categoryRepository.save(new Category(null, "Computers",null,null,null));
            categoryRepository.save(new Category(null, "Printers",null,null,null));
            categoryRepository.save(new Category(null, "Smart phones",null,null,null));
            Random rnd = new Random();
            categoryRepository.findAll().forEach(c -> {

                for (int i = 0; i < 10; i++) {
                    Product p = new Product();
                    p.setName(RandomString.make(18));
                    p.setCurrentprice(100+rnd.nextInt(10000));
                    p.setAvailable(rnd.nextBoolean());
                    p.setPromotion(rnd.nextBoolean());
                    p.setSelected(rnd.nextBoolean());
                    p.setCategory(c);
                    p.setPhotoName("unknown.png");

                    productRepository.save(p);
                }

            });
        };
    }

}
