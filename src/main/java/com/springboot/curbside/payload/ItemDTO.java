package com.springboot.curbside.payload;

import lombok.Data;
@Data
public class ItemDTO {
    private long id;
    private String name;
    private String description;
    private String price;
    private String category;
    private String imgURL;
    private int quantity;
    private String discount = "NONE";
}