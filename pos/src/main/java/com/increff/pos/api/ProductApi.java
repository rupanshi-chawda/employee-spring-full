package com.increff.pos.api;

import com.increff.pos.dao.ProductDao;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.util.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(rollbackOn = ApiException.class)
public class ProductApi {

    @Autowired
    private ProductDao dao;

    public void add(ProductPojo p) throws ApiException {
        p = getProductBarcode(p);
        dao.insert(p);
    }

    public ProductPojo get(int id) throws ApiException {
        return getProductId(id);
    }

    public List<ProductPojo> getAll() {
        return dao.selectAll(ProductPojo.class);
    }

    public void update(int id, ProductPojo p) throws ApiException {
        ProductPojo bx = getProductId(id);
        bx.setName(p.getName());
        bx.setMrp(p.getMrp());
        dao.update(p);
    }

    public ProductPojo getProductId(int id) throws ApiException {
        ProductPojo p = dao.selectById(id, ProductPojo.class);
        if (Objects.isNull(p)) {
            throw new ApiException("Product with given ID does not exit, id: " + id);
        }
        return p;
    }

    public ProductPojo getProductBarcode(ProductPojo p) throws ApiException {
        ProductPojo d = dao.selectBarcode(p.getBarcode());
        if (!Objects.isNull(d)) {
            throw new ApiException("Product with given barcode already exists");
        }
        return p;
    }

    public void checkProductBarcode(String barcode) throws ApiException {
        ProductPojo d = dao.selectBarcode(barcode);
        if (Objects.isNull(d)) {
            throw new ApiException("Product with given barcode does not exists");
        }
    }

    public int getIdByBarcode(String barcode) {
        ProductPojo p = dao.selectBarcode(barcode);
        return p.getId();
    }

    public String getBarcodeById(int id) {
        ProductPojo p = dao.selectById(id, ProductPojo.class);
        return p.getBarcode();
    }

    public String getProductBarcodeByItemBarcode(String barcode) {
        ProductPojo p = dao.selectBarcode(barcode);
        return p.getBarcode();
    }
}
