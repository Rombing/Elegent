package com.example.rommall.controller;

import com.example.rommall.domain.ResponseResult;
import com.example.rommall.domain.entity.Product;
import com.example.rommall.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author GL
 * @since 2024-07-09
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    IProductService productService;
    @GetMapping("/list")
    public ResponseResult getProductList(@RequestParam(required = false)String name,@RequestParam(required = false)Integer categoryId,@RequestParam(defaultValue = "1")Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize){
        return productService.getProductList(name,categoryId,pageNum,pageSize);
    }

    @GetMapping("/admin/{id}")
    public ResponseResult getProduct(@PathVariable("id")Integer id){
        return productService.getProduct(id);
    }

    @PostMapping("/admin/insert")
    public ResponseResult insertProduct(@RequestBody Product product){
        return productService.insertProduct(product);
    }

    @PostMapping("/admin/update")
    public ResponseResult updateProduct(@RequestBody Product product){
        return productService.updateProduct(product);
    }

    @PostMapping("/admin/delete")
    public ResponseResult deleteProduct(@RequestBody List<Integer> ids){
        return productService.deleteProduct(ids);
    }
}
