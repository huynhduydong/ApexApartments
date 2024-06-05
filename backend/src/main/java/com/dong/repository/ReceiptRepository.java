package com.dong.repository;

import com.dong.DTO.ReceiptDTO;
import com.dong.pojo.Receipt;
import com.dong.pojo.Room;

import java.util.List;
import java.util.Map;

public interface ReceiptRepository {

    List<ReceiptDTO> getReceipt(Map<String, String> params);

    boolean deleteReceipt(int id);

    public Receipt getReceiptById(int id);
    boolean addOrUpdateReceipt(Receipt r );

//    List<ReceiptDTO> getReceiptDetail(Map<String, String> params);
}
