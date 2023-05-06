package com.kocak.kmenuserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {
    private String tableId;
    private String menuItemId;
    private String quantity;
}
