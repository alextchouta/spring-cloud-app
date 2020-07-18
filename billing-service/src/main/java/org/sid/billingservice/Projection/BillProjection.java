package org.sid.billingservice.Projection;

import org.sid.billingservice.Entities.Bill;
import org.sid.billingservice.Entities.ProductItem;
import org.springframework.data.rest.core.config.Projection;

import java.util.Collection;
import java.util.Date;

@Projection(name = "fullProjection", types = Bill.class)
public interface BillProjection {
    public Long getId();

    public Date getBillingDate();

    public Long getCustomerID();

    public Collection<ProductItem> getProductItems();

}
