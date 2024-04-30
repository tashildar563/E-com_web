package org.example.scanner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BarcodeInfoRepository extends JpaRepository<BarcodeInfoEntity,Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM  barcode_info WHERE barcode = ?1 and is_deleted = false")
    BarcodeInfoEntity findItemInfoByBarcode(String barcode);
}
