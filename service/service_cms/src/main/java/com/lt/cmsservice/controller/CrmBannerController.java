package com.lt.cmsservice.controller;


import com.lt.cmsservice.entity.CrmBanner;
import com.lt.cmsservice.service.CrmBannerService;
import com.lt.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-07-10
 */
@RestController
@CrossOrigin
@RequestMapping("/cmsservice/banner")
public class CrmBannerController {

    @Autowired
    CrmBannerService bannerService;

    // 获取轮播图列表
    @Cacheable(value = "index",key = "'banner'")
    @GetMapping("bannerList")
    public R bannerList(){
        List<CrmBanner> list = bannerService.list(null);
        return R.ok().data("list",list);
    }

    // 新增轮播图
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner banner){

        bannerService.save(banner);
        return R.ok();
    }

    // 删除轮播图
    @DeleteMapping("deleteBanner/{bannerId}")
    public R  deleteBanner(@PathVariable("bannerId") String bannerId){

        bannerService.removeById(bannerId);
        return R.ok();
    }

    // 按照id获取轮播图信息
    @GetMapping("getBannerById/{bannerId}")
    public R getBannerById(@PathVariable("bannerId") String bannerId){

        CrmBanner banner = bannerService.getById(bannerId);
        return R.ok().data("banner",banner);
    }

    // 修改轮播图信息
    @PostMapping("updateBanner")
    public R updateBanner(@RequestBody CrmBanner banner){

        bannerService.updateById(banner);
        return R.ok();
    }
}

