package com.example.rommall.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.rommall.dao.CategoryMapper;
import com.example.rommall.domain.ResponseResult;
import com.example.rommall.domain.entity.Category;
import com.example.rommall.domain.vo.CategoryVo;
import com.example.rommall.service.ICategoryService;
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
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public ResponseResult getCategoryList(Integer pageNum, Integer pageSize) {
        Page<Category> categoryPage = new Page<>(pageNum, pageSize);
        List<Category> categorys = categoryMapper.selectPage(categoryPage,null).getRecords();
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categorys,CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult getCategory(Integer id) {
        Category category = categoryMapper.selectById(id);
        if(category == null){
            return ResponseResult.failResult();
        }else{
            CategoryVo categoryVo = BeanCopyUtils.copyBean(category,CategoryVo.class);
            return ResponseResult.okResult(categoryVo);
        }
    }

    @Override
    public ResponseResult insertCategory(Category category) {
        int result = categoryMapper.insert(category);
        if(result == 1){
            return ResponseResult.okResult();
        }else{
            return ResponseResult.failResult();
        }
    }

    @Override
    public ResponseResult updateCategory(Category category) {
        int result = categoryMapper.updateById(category);
        if(result == 1){
            return ResponseResult.okResult();
        }else{
            return ResponseResult.failResult();
        }
    }

    @Override
    public ResponseResult deleteCategory(List<Integer> ids) {
        int result = categoryMapper.deleteBatchIds(ids);
        if(result > 0){
            return ResponseResult.okResult();
        }else{
            return ResponseResult.failResult();
        }
    }
}
