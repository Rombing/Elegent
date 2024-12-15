package com.example.rommall.service;

import com.example.rommall.domain.ResponseResult;
import com.example.rommall.domain.entity.User;
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
public interface IUserService extends IService<User> {

    User login(String phone, String password);

    boolean userExists(String phone);

    int register(String phone, String password);

    ResponseResult getUserList(Integer pageNum, Integer pageSize);

    ResponseResult getUser(Integer id);

    ResponseResult updateUser(User user);

    ResponseResult deleteUser(List<Integer> ids);
}
