package com.example.rommall.controller;

import com.example.rommall.domain.ResponseResult;
import com.example.rommall.domain.entity.Category;
import com.example.rommall.service.ICategoryService;
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
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    ICategoryService categoryService;
    @GetMapping("/list")
    public ResponseResult getCategoryList(@RequestParam(defaultValue = "1")Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize){
        return categoryService.getCategoryList(pageNum,pageSize);
    }

    @GetMapping("/admin/{id}")
    public ResponseResult getCategory(@PathVariable("id")Integer id){
        return categoryService.getCategory(id);
    }

    @PostMapping("/admin/insert")
    public ResponseResult insertCategory(@RequestBody Category category){
        return categoryService.insertCategory(category);
    }

    @PostMapping("/admin/update")
    public ResponseResult updateCategory(@RequestBody Category category){
        return categoryService.updateCategory(category);
    }

    @PostMapping("/admin/delete")
    public ResponseResult deleteCategory(@RequestBody List<Integer> ids){
        return categoryService.deleteCategory(ids);
    }
}
