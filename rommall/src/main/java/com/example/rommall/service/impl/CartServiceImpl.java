package com.example.rommall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.rommall.dao.CartMapper;
import com.example.rommall.dao.ProductMapper;
import com.example.rommall.domain.ResponseResult;
import com.example.rommall.domain.entity.Cart;
import com.example.rommall.domain.entity.Product;
import com.example.rommall.domain.vo.CartVo;
import com.example.rommall.service.ICartService;
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
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements ICartService {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;
    @Override
    public ResponseResult getCartList(String userId, Integer pageNum, Integer pageSize) {
        Page<Cart> cartPage = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<Cart>().eq(Cart::getUserId, userId);
        List<Cart> carts = cartMapper.selectPage(cartPage,queryWrapper).getRecords();
        List<CartVo> cartVos = BeanCopyUtils.copyBeanList(carts,CartVo.class);
        for(CartVo cartVo:cartVos){
            Product product = productMapper.selectById(cartVo.getProdId());
            cartVo.setProdName(product.getTitle());
            cartVo.setPrice(product.getPrice());
            cartVo.setImg(product.getImg());
        }
        return ResponseResult.okResult(cartVos);
    }

    @Override
    public ResponseResult getCart(Integer id) {
        Cart cart = cartMapper.selectById(id);
        if(cart == null){
            return ResponseResult.failResult();
        }else{
            CartVo cartVo = BeanCopyUtils.copyBean(cart,CartVo.class);
            Product product = productMapper.selectById(cartVo.getProdId());
            cartVo.setProdName(product.getTitle());
            cartVo.setPrice(product.getPrice());
            cartVo.setImg(product.getImg());
            return ResponseResult.okResult(cartVo);
        }
    }

    @Override
    public ResponseResult insertOrUpdateCart(Cart cart) {
        if(cart.getUserId() == null || cart.getProdId() == null){
            return ResponseResult.failResult("用户或商品ID不能为空");
        }
        Cart cart1 = cartMapper.selectOne(new LambdaQueryWrapper<Cart>()
                .eq(Cart::getUserId, cart.getUserId())
                .eq(Cart::getProdId, cart.getProdId()));
        if(cart1 == null){
            int result = cartMapper.insert(cart);
            if(result == 1){
                return ResponseResult.okResult("新增一条购物记录");
            }else{
                return ResponseResult.failResult();
            }
        }else{
            cart1.setNum(cart1.getNum()+cart.getNum());
            int result = cartMapper.updateById(cart1);
            if(result == 1){
                return ResponseResult.okResult("更新一条购物记录");
            }else{
                return ResponseResult.failResult();
            }
        }
    }

    @Override
    public ResponseResult updateCart(Cart cart) {
        if(cart.getId() == null){
            return ResponseResult.failResult("购物车ID不能为空");
        }
        int result = cartMapper.updateById(cart);
        if(result == 1){
            return ResponseResult.okResult();
        }else{
            return ResponseResult.failResult();
        }
    }

    @Override
    public ResponseResult deleteCart(List<Integer> ids) {
        int result = cartMapper.deleteBatchIds(ids);
        if(result > 0){
            return ResponseResult.okResult();
        }else{
            return ResponseResult.failResult();
        }
    }
}
