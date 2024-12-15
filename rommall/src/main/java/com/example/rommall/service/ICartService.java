package com.example.rommall.service;

import com.example.rommall.domain.ResponseResult;
import com.example.rommall.domain.entity.Cart;
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
public interface ICartService extends IService<Cart> {

    ResponseResult getCartList(String userId, Integer pageNum, Integer pageSize);

    ResponseResult getCart(Integer id);

    ResponseResult insertOrUpdateCart(Cart cart);

    ResponseResult updateCart(Cart cart);

    ResponseResult deleteCart(List<Integer> ids);
}
