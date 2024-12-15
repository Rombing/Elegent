package com.example.rommall.service;

import com.example.rommall.domain.ResponseResult;
import com.example.rommall.domain.entity.Category;
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
public interface ICategoryService extends IService<Category> {

    ResponseResult getCategoryList(Integer pageNum, Integer pageSize);

    ResponseResult getCategory(Integer id);

    ResponseResult insertCategory(Category category);

    ResponseResult updateCategory(Category category);

    ResponseResult deleteCategory(List<Integer> ids);
}
