package com.moon.product.service.impl;

import com.moon.product.dao.ProductCategoryDao;
import com.moon.product.dao.ProductInfoDao;
import com.moon.product.dao.dto.CarDTO;
import com.moon.product.entity.ProductCategory;
import com.moon.product.entity.ProductInfo;
import com.moon.product.enums.ProductStatusEnum;
import com.moon.product.enums.ResultEnum;
import com.moon.product.exception.RunMoonException;
import com.moon.product.service.ProductCategoryService;
import com.moon.product.vo.ProductInfoVO;
import com.moon.product.vo.ProductVO;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao categoryDao;

    @Autowired
    private ProductInfoDao productInfoDao;

    /* 查询商品类目和商品明细 */
    public List<ProductCategory> selectProductCateAndInfo() {
        return categoryDao.selectProductCateAndInfo();
    }


    /***
     * 查询上架的商品
     * @return
     */
    public List<ProductVO> selectProducts() {

        // 1. 查询所有在架的商品
        List<ProductInfo> productInfos = productInfoDao.selectProductStatus(ProductStatusEnum.UP.getCode(), null);

        // 2. 获取类目type列表
        List<ProductCategory> categories = categoryDao.selectProcutType();

        List<ProductVO> productVOS = new ArrayList<>();

        // 构造数据
        for (ProductCategory category : categories) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(category.getCategoryName());
            productVO.setCategoryType(category.getCategoryType());

            Integer type = category.getCategoryType();
            List<ProductInfoVO> infoVOS = new ArrayList<>();
            for (ProductInfo info : productInfos) {
                // 判断type类目是否相等
                if (info.getCategoryType() == type) {
                    ProductInfoVO infoVO = new ProductInfoVO();
                    //对象的copy
                    BeanUtils.copyProperties(info, infoVO);
                    infoVOS.add(infoVO);
                }
            }

            productVO.setProductInfoVOList(infoVOS);
            productVOS.add(productVO);
        }

        return productVOS;
    }


    /***
     * 通过商品Id去查询商品
     * @param productId 商品Id list
     * @return {@link List<ProductInfo>}
     * @throws RunMoonException
     */
    public List<ProductInfo> selectProductListById(List<String> productId) throws RunMoonException {
        if(CollectionUtils.isEmpty(productId)){
            log.error("selectProductListById()-> Exception ->{}",ResultEnum.PASS_LIST_NULL.getMessage());
            throw new RunMoonException(ResultEnum.PASS_LIST_NULL);
        }

        List<ProductInfo> productInfos = productInfoDao.selectProductListById(productId);

        if(CollectionUtils.isEmpty(productInfos)){
            log.error("selectProductListById()-> Exception ->{}",ResultEnum.INQUIRE_LIST_NULL);
            throw new RunMoonException(ResultEnum.INQUIRE_LIST_NULL);
        }

        return productInfos;
    }

    /***
     * 扣除库存
     * @param carDTOS
     */
    @Transactional
    public void decreaseStock(List<CarDTO> carDTOS) throws RunMoonException{
        // 先要判断是否存在
        for (CarDTO carDTO : carDTOS){
            //1. 要先判断是否存在
            ProductInfo productInfo = selectOne(carDTO.getProductId());
            if(productInfo == null){
                log.error("通过【商品id:{}】查询商品为空",carDTO.getProductId());
                throw new RunMoonException(ResultEnum.INQUIRE_Model_NULL);
            }
            // 2. 在判断库存是否充足
            int result = productInfo.getProductStock() - carDTO.getProductQuantity();
            if(result < 0){
                throw new RunMoonException(ResultEnum.STOCK_NULL);
            }

            productInfo.setProductStock(result);
            //该更新操作
            productInfoDao.updateProductById(productInfo);
        }
    }


    /* 查询单个商品*/
    private ProductInfo selectOne(String productId){
        List<ProductInfo> productInfos = productInfoDao.selectProductListById(Arrays.asList(productId));

        if(productInfos != null){
            return productInfos.get(0);
        }

        return null;
    }
}
