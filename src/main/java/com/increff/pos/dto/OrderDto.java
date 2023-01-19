package com.increff.pos.dto;

import com.increff.pos.api.InventoryApi;
import com.increff.pos.api.OrderApi;
import com.increff.pos.api.ProductApi;
import com.increff.pos.helper.OrderHelper;
import com.increff.pos.helper.OrderHelper;
import com.increff.pos.model.data.OrderData;
import com.increff.pos.model.data.OrderItemData;
import com.increff.pos.model.form.OrderForm;
import com.increff.pos.model.form.OrderItemForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.api.OrderApi;
import com.increff.pos.util.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Service
public class OrderDto {

    @Autowired
    private OrderApi orderApi;

    @Autowired
    private ProductApi productApi;

    @Autowired
    private InventoryApi inventoryApi;

    public void addOrder(OrderForm form) throws ApiException {
        OrderPojo p = OrderHelper.convert(form);
        orderApi.addOrder(p);
    }

    public OrderData getOrder(int id) throws ApiException {
        OrderPojo p = orderApi.getOrder(id);
        return OrderHelper.convert(p);
    }

    public List<OrderData> getAllOrder() {
        return orderApi.getAllOrder().stream().map(OrderHelper::convert).collect(Collectors.toList());
    }

    public void addItem(List<OrderItemForm> forms) throws ApiException {

        // Validating every order item before adding it.
        for(OrderItemForm f : forms) {
            OrderHelper.normalize(f);
            OrderHelper.validate(f);

            int id = productApi.getIdByBarcode(f.getBarcode());
            OrderHelper.validateId(id);

            int quantity = inventoryApi.getQuantityById(id);
            OrderHelper.validateInventory(f, quantity);
        }

        // Place order
        OrderPojo op = new OrderPojo();
        orderApi.addOrder(op);

        // Adding order item to table
        for(OrderItemForm f : forms) {
            OrderItemPojo p = OrderHelper.convert(f);
            //Reduce inventory
            reduceInventory(f.getBarcode(), f.getQuantity());
            int pid = productApi.getIdByBarcode(f.getBarcode());
            orderApi.addItem(p, pid, op.getId());
        }
    }

    public OrderItemData getItem(int id) throws ApiException {
        OrderItemPojo p = orderApi.getItem(id);
        String barcode = productApi.getBarcodeById(p.getProductId());
        return OrderHelper.convert(p, barcode);
    }

    public List<OrderItemData> getAllItem() {
        //TODO: apply java stream method
        List<OrderItemPojo> list = orderApi.getAllItem();
        List<OrderItemData> list2 = new ArrayList<OrderItemData>();
        for(OrderItemPojo b : list) {
            String barcode = productApi.getBarcodeById(b.getProductId());
            list2.add(OrderHelper.convert(b, barcode));
        }
        return list2;
    }

    public List<OrderItemData> getByOrderId(int orderId) throws ApiException {
        List<OrderItemPojo> list = orderApi.getByOrderId(orderId);
        List<OrderItemData> list2 = new ArrayList<OrderItemData>();
        for(OrderItemPojo b : list) {
            String barcode = productApi.getBarcodeById(b.getProductId());
            list2.add(OrderHelper.convert(b, barcode));
        }
        return list2;
    }

    private void reduceInventory(String barcode, int quantity) throws ApiException {
        int id = productApi.getIdByBarcode(barcode);
        InventoryPojo p = inventoryApi.getInventoryId(id);
        int newQuantity = p.getQuantity() - quantity;
        p.setQuantity(newQuantity);
        inventoryApi.update(p);
    }
}
