package com.jdd.server.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdd.server.mapper.PositionMapper;
import com.jdd.server.pojo.Position;
import com.jdd.server.service.IPositionService;
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
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements IPositionService {

}
