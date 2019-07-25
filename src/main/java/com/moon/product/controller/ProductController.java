package com.moon.product.controller;

import com.moon.product.dao.dto.CarDTO;
import com.moon.product.entity.ProductInfo;
import com.moon.product.exception.RunMoonException;
import com.moon.product.service.ProductCategoryService;
import com.moon.product.vo.ProductVO;
import com.moon.product.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品服务接口
 */
@RestController
@Slf4j
public class ProductController {


    @Autowired
    private ProductCategoryService categoryService;

    /***
     * 1. 查询所有在架的商品
     * 2. 获取类目type列表
     * 3. 查询类目
     * 4. 构造数据
     */
    @GetMapping("/list")
    public ResultVO list(){
        List<ProductVO> productVOS = categoryService.selectProducts();

        return ResultVO.ResponseServer(200,"查询成功",productVOS);
    }


    /***
     * 获取商品列表（给订单服务用的）
     * @param productIdList
     * @return
     */
    @GetMapping("/listForOrder")
    public List<ProductInfo> listForOrder(@RequestParam(value = "productIdList",required = false) List<String> productIdList){
    List<ProductInfo> productInfos = null;

    try {
        productInfos = categoryService.selectProductListById(productIdList);
    } catch (RunMoonException e) {
        return new ArrayList<>();
    }

        return productInfos;
    }

    /***
     * 扣库存
     */
    @PostMapping("/decreaseStock")
    public void decreaseStock(@RequestBody List<CarDTO> carDTOS){

        try {
            categoryService.decreaseStock(carDTOS);
        } catch (RunMoonException e) {
            e.printStackTrace();
        }
    }
}
