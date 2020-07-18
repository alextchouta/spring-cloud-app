package org.sid.billingservice.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.billingservice.Dto.Product;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double price;
    private double quantity;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Long productId;
    @Transient
    private Product product;

    @ManyToOne()
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Bill bill;
}
