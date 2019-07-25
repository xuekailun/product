package com.moon.product.service;


import com.moon.product.dao.dto.CarDTO;
import com.moon.product.entity.ProductCategory;
import com.moon.product.entity.ProductInfo;
import com.moon.product.exception.RunMoonException;
import com.moon.product.vo.ProductVO;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductCategoryService {

    /* 查询商品类目和商品明细 */
    List<ProductCategory> selectProductCateAndInfo();

    /***
     * 查询上架的商品
     * @return
     */
    List<ProductVO> selectProducts();

    /***
     * 通过商品Id去查询商品
     * @param productId 商品Id list
     * @return {@link List< ProductInfo >}
     * @throws RunMoonException
     */
    List<ProductInfo> selectProductListById(List<String> productId) throws RunMoonException;

    /***
     * 扣除库存
     * @param carDTOS
     */
    void decreaseStock(List<CarDTO> carDTOS) throws RunMoonException;
}
