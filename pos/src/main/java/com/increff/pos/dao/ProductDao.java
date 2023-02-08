package com.increff.pos.dao;

import com.increff.pos.pojo.ProductPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

@Repository
public class ProductDao extends AbstractDao{

    private static final String SELECT_BY_BARCODE = "select p from ProductPojo p where barcode=:barcode";

    public ProductPojo selectByBarcode(String barcode){
        TypedQuery<ProductPojo> query = getQuery(SELECT_BY_BARCODE, ProductPojo.class);
        query.setParameter("barcode", barcode);
        return getSingle(query);
    }

    public void update(ProductPojo p){}
}
