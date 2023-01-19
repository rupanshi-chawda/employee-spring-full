package com.increff.pos.controller;

import com.increff.pos.dto.InventoryDto;
import com.increff.pos.model.data.InventoryData;
import com.increff.pos.model.form.InventoryForm;
import com.increff.pos.util.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping(path = "/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryDto dto;

    @ApiOperation(value = "Adds a Product Inventory")
    @PostMapping(path = "")
    public void addInventory(@RequestBody InventoryForm form) throws ApiException {
        dto.add(form);
    }

    @ApiOperation(value = "Gets a Product Inventory by barcode")
    @GetMapping(path = "/{barcode}")
    public InventoryData getInventory(@PathVariable String barcode) throws ApiException {
        return dto.get(barcode);
    }

    @ApiOperation(value = "Gets list of all Product Inventories")
    @GetMapping(path = "")
    public List<InventoryData> getAllInventory() {
        return dto.getAll();
    }

    @ApiOperation(value = "Updates a Product Inventory")
    @PutMapping(path = "/{barcode}")
    public void updateInventory(@PathVariable String barcode, @RequestBody InventoryForm f) throws ApiException {
        dto.update(barcode, f);
    }
}
