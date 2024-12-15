package com.example.rommall.service;

import com.example.rommall.domain.ResponseResult;
import com.example.rommall.domain.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GL
 * @since 2024-07-09
 */
public interface IProductService extends IService<Product> {

    ResponseResult getProductList(String name,Integer categoryId,Integer pageNum, Integer pageSize);

    ResponseResult getProduct(Integer id);

    ResponseResult insertProduct(Product product);

    ResponseResult updateProduct(Product product);

    ResponseResult deleteProduct(List<Integer> ids);
}
