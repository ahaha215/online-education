package com.lt.ucenterservice.controller;


import com.lt.commonutils.JwtUtils;
import com.lt.commonutils.R;
import com.lt.ucenterservice.entity.UcenterMember;
import com.lt.ucenterservice.entity.vo.RegisterVo;
import com.lt.ucenterservice.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-07-13
 */
@RestController
@CrossOrigin
@RequestMapping("/ucenterservice/member")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @PostMapping("login")
    public R loginUser(@RequestBody UcenterMember member){

        String token = ucenterMemberService.login(member);

        return R.ok().data("token",token);
    }

    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo){
        ucenterMemberService.register(registerVo);
        return R.ok();
    }

    @GetMapping("getUserInfo")
    public R getUserInfo(HttpServletRequest request){

        // 根据 jwt 获取用户id
        String id = JwtUtils.getMemberIdByJwtToken(request);

        // 根据 id 查询用户信息
        UcenterMember member = ucenterMemberService.getById(id);

        return R.ok().data("userInfo",member);
    }

    //根据token字符串获取用户信息
    @PostMapping("getInfoUc/{id}")
    public UcenterMember getInfo(@PathVariable("id") String id) {
        //根据用户id获取用户信息
        UcenterMember ucenterMember = ucenterMemberService.getById(id);
        UcenterMember member = new UcenterMember();
        BeanUtils.copyProperties(ucenterMember,member);
        return member;
    }

    /**
     * 统计一天内的注册人数
     * @param day
     * @return
     */
    @GetMapping("countRegisterSum/{day}")
    public R countRegisterSum(@PathVariable("day") String day){

        int registerSum = ucenterMemberService.countRegisterSum(day);

        return R.ok().data("registerSum",registerSum);
    }
}

