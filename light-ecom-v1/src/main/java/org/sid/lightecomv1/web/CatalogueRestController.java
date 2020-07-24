package org.sid.lightecomv1.web;

import org.sid.lightecomv1.entities.Product;
import org.sid.lightecomv1.repositories.ProductRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin(origins = "*")
public class CatalogueRestController {

    private ProductRepository productRepository;


    public CatalogueRestController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping(path = "photoProduct/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getPhoto(@PathVariable("id") Long id) throws Exception{
        Product p = productRepository.findById(id).get();
       return Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/ecom/products/"+p.getPhotoName()));
    }

    @PostMapping(path = "/uploadPhoto/{id}")
    public void uploadPhoto(MultipartFile file, @PathVariable Long id) throws IOException {
        Product p = productRepository.findById(id).get();
        p.setPhotoName(file.getOriginalFilename());
       Files.write(Paths.get(System.getProperty("user.home") + "/ecom/products/" + p.getPhotoName()), file.getBytes());
       productRepository.save(p);
    }
}
