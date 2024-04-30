package org.example.scanner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemInventoryRepositoryI extends JpaRepository<ItemEntity,Long> {
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM  item_inventory WHERE barcode_info_id = ?1 and is_deleted = 'false'")
    ItemEntity findByBarcodeInfoId(String id);
}
