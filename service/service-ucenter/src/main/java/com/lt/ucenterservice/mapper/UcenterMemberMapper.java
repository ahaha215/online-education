package com.lt.ucenterservice.mapper;

import com.lt.ucenterservice.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2022-07-13
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    int selectRegisterSum(String day);
}
