package com.example.rommall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.rommall.dao.CategoryMapper;
import com.example.rommall.dao.ProductMapper;
import com.example.rommall.domain.ResponseResult;
import com.example.rommall.domain.entity.Product;
import com.example.rommall.domain.vo.ProductVo;
import com.example.rommall.service.IProductService;
import com.example.rommall.util.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author GL
 * @since 2024-07-09
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public ResponseResult getProductList(String name, Integer categoryId, Integer pageNum, Integer pageSize) {
        Page<Product> productPage = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null,Product::getTitle,name)
                .eq(categoryId != null,Product::getCategoryId,categoryId);
        List<Product> products = productMapper.selectPage(productPage,queryWrapper).getRecords();
        List<ProductVo> productVos = BeanCopyUtils.copyBeanList(products,ProductVo.class);
        for(ProductVo productVo:productVos){
            productVo.setCategoryName(categoryMapper.selectById(productVo.getCategoryId()).getName());
        }
        return ResponseResult.okResult(productVos);
    }

    @Override
    public ResponseResult getProduct(Integer id) {
        Product product = productMapper.selectById(id);
        if(product == null){
            return ResponseResult.failResult();
        }else{
            ProductVo productVo = BeanCopyUtils.copyBean(product,ProductVo.class);
            productVo.setCategoryName(categoryMapper.selectById(productVo.getCategoryId()).getName());
            return ResponseResult.okResult(productVo);
        }
    }

    @Override
    public ResponseResult insertProduct(Product product) {
        if(product.getCode() == null){
            return ResponseResult.failResult("商品编码不能为空");
        }
        Product product1 = productMapper.selectOne(new LambdaQueryWrapper<Product>().eq(Product::getCode,product.getCode()));
        if(product1 == null){
            int result = productMapper.insert(product);
            if(result == 1){
                return ResponseResult.okResult();
            }else{
                return ResponseResult.failResult();
            }
        }else{
            return ResponseResult.failResult(501,"编码已存在，插入失败");
        }
    }

    @Override
    public ResponseResult updateProduct(Product product) {
        if(product.getId() == null){
            return ResponseResult.failResult("商品ID不能为空");
        }
        int result = productMapper.updateById(product);
        if(result == 1){
            return ResponseResult.okResult();
        }else{
            return ResponseResult.failResult();
        }
    }

    @Override
    public ResponseResult deleteProduct(List<Integer> ids) {
        int result = productMapper.deleteBatchIds(ids);
        if(result > 0){
            return ResponseResult.okResult();
        }else{
            return ResponseResult.failResult();
        }
    }
}
