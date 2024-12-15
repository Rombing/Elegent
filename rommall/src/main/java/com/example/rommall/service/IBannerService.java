package com.example.rommall.service;

import com.example.rommall.domain.ResponseResult;
import com.example.rommall.domain.entity.Banner;
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
public interface IBannerService extends IService<Banner> {

    ResponseResult getBannerList(Integer pageNum, Integer pageSize);

    ResponseResult getBanner(Integer id);

    ResponseResult insertBanner(Banner banner);

    ResponseResult updateBanner(Banner banner);
    ResponseResult deleteBanner(List<Integer> ids);
}
