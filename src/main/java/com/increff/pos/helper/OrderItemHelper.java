package com.increff.pos.helper;

import com.increff.pos.model.data.OrderItemData;
import com.increff.pos.model.form.OrderItemForm;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.util.ApiException;
import com.increff.pos.util.StringUtil;

import java.util.Objects;

public class OrderItemHelper {

    public static OrderItemData convert(OrderItemPojo p, String barcode) {
        OrderItemData d = new OrderItemData();
        d.setBarcode(barcode);
        d.setId(p.getId());
        d.setOrderId(p.getOrderId());
        d.setProductId(p.getProductId());
        d.setQuantity(p.getQuantity());
        d.setSellingPrice(p.getSellingPrice());
        return d;
    }

    public static OrderItemPojo convert(OrderItemForm f) {
        OrderItemPojo p = new OrderItemPojo();
        p.setSellingPrice(f.getSellingPrice());
        p.setQuantity(f.getQuantity());
        return p;
    }

    public static void normalize(OrderItemForm f) {
        f.setBarcode(StringUtil.toLowerCase(f.getBarcode()));
    }

    public static void validate(OrderItemForm f) throws ApiException {
        if(StringUtil.isEmpty(f.getBarcode())) {
            throw new ApiException("Barcode cannot be empty");
        }
        if(f.getQuantity()<=0) {
            throw new ApiException("Quantity cannot be empty or less than one");
        }
        if(f.getSellingPrice()<=0) {
            throw new ApiException("Selling Price cannot be empty or less than one");
        }
    }

    public static void validateId(OrderItemPojo p, int id) throws ApiException {
        if (Objects.isNull(p)) {
            throw new ApiException("Product with given ID does not exit, id: " + id);
        }
    }

    public static void validateId(int id) throws ApiException {
        if (id <= 0) {
            throw new ApiException("Product with given ID does not exit, id: " + id);
        }
    }

    public static void validateInventory(OrderItemForm f, int quantity) throws ApiException {
        if (f.getQuantity() > quantity) {
            throw new ApiException("Product Quantity is more than available Inventory");
        }
    }
}
