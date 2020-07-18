package org.sid.billingservice.Entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.billingservice.Dto.Customer;


import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date billingDate;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Long customerId;
    @Transient
    private Customer customer;

    @OneToMany(mappedBy = "bill")
    private Collection<ProductItem> productItems;
}
