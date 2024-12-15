package com.example.rommall.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.rommall.dao.BannerMapper;
import com.example.rommall.domain.ResponseResult;
import com.example.rommall.domain.entity.Banner;
import com.example.rommall.domain.vo.BannerVo;
import com.example.rommall.service.IBannerService;
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
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements IBannerService {

    @Autowired
    private BannerMapper bannerMapper;
    @Override
    public ResponseResult getBannerList(Integer pageNum, Integer pageSize) {
        Page<Banner> bannerPage = new Page<>(pageNum, pageSize);
        List<Banner> banners = bannerMapper.selectPage(bannerPage,null).getRecords();
        List<BannerVo> bannerVos = BeanCopyUtils.copyBeanList(banners,BannerVo.class);
        return ResponseResult.okResult(bannerVos);
    }

    @Override
    public ResponseResult getBanner(Integer id) {
        Banner banner = bannerMapper.selectById(id);
        if(banner == null){
            return ResponseResult.failResult();
        }else{
            BannerVo bannerVo = BeanCopyUtils.copyBean(banner,BannerVo.class);
            return ResponseResult.okResult(bannerVo);
        }
    }

    @Override
    public ResponseResult insertBanner(Banner banner) {
        int result = bannerMapper.insert(banner);
        if(result == 1){
            return ResponseResult.okResult();
        }else{
            return ResponseResult.failResult();
        }
    }

    @Override
    public ResponseResult updateBanner(Banner banner) {
        int result = bannerMapper.updateById(banner);
        if(result == 1){
            return ResponseResult.okResult();
        }else{
            return ResponseResult.failResult();
        }
    }

    @Override
    public ResponseResult deleteBanner(List<Integer> ids) {
        int result = bannerMapper.deleteBatchIds(ids);
        if(result > 0){
            return ResponseResult.okResult();
        }else{
            return ResponseResult.failResult();
        }
    }
}
