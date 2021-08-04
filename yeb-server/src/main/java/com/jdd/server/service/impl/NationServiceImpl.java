package com.jdd.server.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdd.server.mapper.NationMapper;
import com.jdd.server.pojo.Nation;
import com.jdd.server.service.INationService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jdd
 * @since 2021-08-04
 */
@Service
public class NationServiceImpl extends ServiceImpl<NationMapper, Nation> implements INationService {

}
