package com.example.rommall.controller;

import com.example.rommall.domain.ResponseResult;
import com.example.rommall.domain.entity.Cart;
import com.example.rommall.service.ICartService;
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
@RequestMapping("/cart")
public class CartController {
    @Autowired
    ICartService cartService;
    @GetMapping("/list")
    public ResponseResult getCartList(@RequestParam String userId,@RequestParam(defaultValue = "1")Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize){
        return cartService.getCartList(userId,pageNum,pageSize);
    }

    @GetMapping("/admin/{id}")
    public ResponseResult getCart(@PathVariable("id")Integer id){
        return cartService.getCart(id);
    }

    @PostMapping("/admin/insertOrUpdate")
    public ResponseResult insertOrUpdateCart(@RequestBody Cart cart){
        return cartService.insertOrUpdateCart(cart);
    }

    @PostMapping("/admin/update")
    public ResponseResult updateCart(@RequestBody Cart cart){
        return cartService.updateCart(cart);
    }

    @PostMapping("/admin/delete")
    public ResponseResult deleteCart(@RequestBody List<Integer> ids){
        return cartService.deleteCart(ids);
    }
}
