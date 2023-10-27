package com.springboot.curbside.payload;
import lombok.Data;

@Data
public class ItemInCartDTO {
    private long id;
    private String name;
    private String description;
    private String price;
    private String category;
    private String imgURL;
    private long cartId;
    private int quantity;
}
