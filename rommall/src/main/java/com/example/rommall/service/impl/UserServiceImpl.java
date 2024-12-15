package com.example.rommall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rommall.domain.ResponseResult;
import com.example.rommall.domain.entity.User;
import com.example.rommall.dao.UserMapper;
import com.example.rommall.domain.vo.UserVo;
import com.example.rommall.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.rommall.util.BeanCopyUtils;
import com.example.rommall.util.MD5Util;
import org.springframework.beans.BeanUtils;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    UserMapper userMapper;
    @Override
    public User login(String phone, String password) {
        String passwordMD5 = MD5Util.MD5Encode(password,"UTF-8");

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",phone)
                    .eq("password",passwordMD5);

        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean userExists(String phone) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",phone);
        return userMapper.exists(queryWrapper);
    }

    @Override
    public int register(String phone, String password) {
        String passswordMD5 = MD5Util.MD5Encode(password,"UTF-8");
        User user = new User();
        user.setPhone(phone);
        user.setPassword(passswordMD5);
        user.setNickName("用户"+ phone);
        return userMapper.insert(user);
    }

    @Override
    public ResponseResult getUserList(Integer pageNum, Integer pageSize) {
        Page<User> userPage = new Page<>(pageNum, pageSize);
        List<User> users = userMapper.selectPage(userPage,null).getRecords();
        List<UserVo> userVos = BeanCopyUtils.copyBeanList(users, UserVo.class);
        return ResponseResult.okResult(userVos);
    }

    @Override
    public ResponseResult getUser(Integer id) {
        User user = userMapper.selectById(id);
        if(user == null){
            return ResponseResult.failResult();
        }else{
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user,userVo);
            return ResponseResult.okResult(userVo);
        }
    }

    @Override
    public ResponseResult updateUser(User user) {
       int result = userMapper.updateById(user);
       if(result == 1){
           return ResponseResult.okResult();
       }else{
           return ResponseResult.failResult();
       }
    }

    @Override
    public ResponseResult deleteUser(List<Integer> ids) {
        int result = userMapper.deleteBatchIds(ids);
        if(result > 0){
            return ResponseResult.okResult();
        }else{
            return ResponseResult.failResult();
        }
    }
}
