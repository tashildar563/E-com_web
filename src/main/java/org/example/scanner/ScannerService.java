package org.example.scanner;


import org.example.member.MemberAuthEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ScannerService {

    @Autowired
    private BarcodeInfoRepository barcodeInfoRepository ;

    @Autowired
    private ItemInventoryRepositoryI itemInventoryRepository ;
    public ResponseEntity<Map<String,String>> getBarcodeInfo(String barcode) throws Exception {
            BarcodeInfoEntity barcodeInfoEntity = barcodeInfoRepository.findItemInfoByBarcode(barcode);

        Map<String,String> itemDetails = new HashMap<>();
            if(null!=barcodeInfoEntity){
                ItemEntity itemEntity = itemInventoryRepository.findByBarcodeInfoId(String.valueOf(barcodeInfoEntity.getId()));
                itemDetails.put("barcode",barcode);
                itemDetails.put("name",itemEntity.getName());
                itemDetails.put("brand",itemEntity.getBrand());
                itemDetails.put("price", itemEntity.getPrice());
                itemDetails.put("description",itemEntity.getDescription());
                itemDetails.put("category",itemEntity.getCategory());
                return ResponseEntity.ok(itemDetails);
            }else{
                itemDetails.put("barcode",barcode);
                return new ResponseEntity<>(itemDetails, HttpStatus.NOT_FOUND);
            }
    }
}
