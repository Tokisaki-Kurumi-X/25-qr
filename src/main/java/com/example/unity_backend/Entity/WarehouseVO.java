package com.example.unity_backend.Entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Data
public class WarehouseVO  {

   private String UserName;
   private List<WarehouseItemDTO> itemList = new ArrayList<>();
}
