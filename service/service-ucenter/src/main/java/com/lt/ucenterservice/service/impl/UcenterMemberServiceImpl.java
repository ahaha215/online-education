package com.lt.ucenterservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lt.commonutils.JwtUtils;
import com.lt.commonutils.MD5;
import com.lt.servicebase.exceptionhandler.GuliException;
import com.lt.ucenterservice.entity.UcenterMember;
import com.lt.ucenterservice.entity.vo.RegisterVo;
import com.lt.ucenterservice.mapper.UcenterMemberMapper;
import com.lt.ucenterservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Queue;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-07-13
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Override
    public String login(UcenterMember member) {

        // 获取登录手机号，密码
        String mobile = member.getMobile();
        String password = member.getPassword();

        // 手机号和密码的非空的校验
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new GuliException(20001,"手机号或密码为空！");
        }

        // 判断手机号是否正确
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(queryWrapper);
        if (mobileMember == null){
            throw new GuliException(20001,"手机号不存在！");
        }

        // 判断密码
        if (!MD5.encrypt(password).equals(mobileMember.getPassword())){
            throw new GuliException(20001,"密码错误！");
        }

        // 判断用户是否被禁用
        if (mobileMember.getIsDisabled()){
            throw new GuliException(20001,"该用户被冻结！");
        }

        // 生成token字符串
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());

        return jwtToken;
    }

    @Override
    public void register(RegisterVo registerVo) {
        // 获取注册的数据
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        // 检验注册信息
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)){
            throw new GuliException(20001,"注册信息不能为空！");
        }

        // 判断验证码
        String thePhone = redisTemplate.opsForValue().get(mobile);
        System.out.println(code + "===" + thePhone);
        System.out.println(code.equals(thePhone));
        if (!code.equals(thePhone)){
            System.out.println("????");
            throw new GuliException(20001,"验证码错误！");
        }

        // 判断手机是否已经存在
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",mobile);
        UcenterMember queryUcenterMember = baseMapper.selectOne(queryWrapper);
        if (queryUcenterMember != null){
            throw new GuliException(20001,"电话号已经存在！");
        }

        // 添加用户信息
        UcenterMember ucenterMember = new UcenterMember();
        BeanUtils.copyProperties(registerVo,ucenterMember);
        // 密码使用 MD5 加密
        ucenterMember.setPassword(MD5.encrypt(password));
        baseMapper.insert(ucenterMember);
    }

    @Override
    public UcenterMember getByOpenid(String openid) {
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);
        UcenterMember member = baseMapper.selectOne(queryWrapper);
        return member;
    }

    @Override
    public int countRegisterSum(String day) {

        int registerSum = baseMapper.selectRegisterSum(day);

        return registerSum;
    }
}
