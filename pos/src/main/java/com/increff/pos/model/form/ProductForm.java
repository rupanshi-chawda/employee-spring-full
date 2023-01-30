package com.increff.pos.model.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ProductForm {

    @NotBlank(message = "Barcode cannot be empty")
    private String barcode;
    private String brand;
    private String category;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "MRP cannot be empty")
    @Min(value = 1)
    private Double mrp;

}
