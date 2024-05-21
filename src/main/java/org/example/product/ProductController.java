package org.example.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.member.MemberService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductColorSizeMappingRepository productColorSizeMappingRepository;

    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private SizeRepository sizeRepository;

    @PostMapping("/getAllProductDetails")
    public List<JSONObject> addMember(@RequestBody String product) throws JsonProcessingException {
        try {
            Map<String, String> productDet = memberService.getStringStringMap(product);
            List<JSONObject> productsList = new ArrayList<>();
            String productId = productDet.get("product");
            if (!productId.isEmpty()) {
                ProductEntity productEntity = productService.findOneById(Long.parseLong(productId));
                if (productEntity != null) {
                    ProductColorSizeMapping pCZ = productColorSizeMappingRepository.findByProductId(productEntity.getId());
                    ColorEntity colorEntity = colorRepository.findById(pCZ.getColorId()).orElse(null);
                    SizeEntity sizeEntity = sizeRepository.findById(pCZ.getSizeId()).orElse(null);
                    JSONObject productDetails = new JSONObject();
                    productDetails.put("name", productEntity.getName());
                    productDetails.put("size", sizeEntity.getName());
                    productDetails.put("color", colorEntity.getName());
                    productDetails.put("category_id", productEntity.getCategoryId().toString());
                    productsList.add(productDetails);
                    return productsList;
                } else {
                    return productsList;
                }
            }else{
                List<ProductEntity> productEntityList = productService.findAll();
                if(!productEntityList.isEmpty()){
                    for(ProductEntity productEntity:productEntityList){
                        List<Long> productIds = productEntityList.stream().map(ProductEntity::getId).toList();
                        List<ProductColorSizeMapping> pCZList = productColorSizeMappingRepository.findAllByProductIds(productIds);
                        Map<Long, ProductColorSizeMapping> productMappingMap = pCZList.stream()
                                .collect(Collectors.toMap(ProductColorSizeMapping::getProductId, pCZ -> pCZ));
                        List<ColorEntity> colorEntityList = colorRepository.findAll();
                        Map<Long, ColorEntity> colorEntityMap = colorEntityList.stream()
                                .collect(Collectors.toMap(ColorEntity::getId, colorEntity -> colorEntity));
                        List<SizeEntity> sizeEntity = sizeRepository.findAll();
                        Map<Long, SizeEntity> sizeEntityMap = sizeEntity.stream()
                                .collect(Collectors.toMap(SizeEntity::getId, sizeEntity1 -> sizeEntity1));
                        JSONObject productDetails = new JSONObject();
                        productDetails.put("name", productEntity.getName());
                        String productSize = sizeEntityMap.get(productMappingMap.get(productEntity.getId()).getSizeId()).getName();
                        String productColor = colorEntityMap.get(productMappingMap.get(productEntity.getId()).getColorId()).getName();
                        productDetails.put("size", productSize);
                        productDetails.put("color", productColor);
                        productDetails.put("category_id", productEntity.getCategoryId().toString());
                        productsList.add(productDetails);
                    }

                }
            }
            System.out.println(productDet);
            return productsList;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
