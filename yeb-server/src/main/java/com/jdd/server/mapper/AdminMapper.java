package com.jdd.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdd.server.pojo.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jdd
 * @since 2021-08-04
 */
public interface AdminMapper extends BaseMapper<Admin> {

    List<Admin> getAllAdmins(@Param("id") Integer id, @Param("keywords") String keywords);
}
