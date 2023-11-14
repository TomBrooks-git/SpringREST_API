package com.springboot.curbside.payload;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OrderDTO {
    private long id;
    private long customerId;
    private LocalDateTime orderDate;
    private LocalDateTime pickupTime;
    private OrderSummaryDTO orderSummaryDto;
}
