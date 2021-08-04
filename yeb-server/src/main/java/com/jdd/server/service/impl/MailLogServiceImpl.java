package com.jdd.server.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdd.server.mapper.MailLogMapper;
import com.jdd.server.pojo.MailLog;
import com.jdd.server.service.IMailLogService;
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
public class MailLogServiceImpl extends ServiceImpl<MailLogMapper, MailLog> implements IMailLogService {

}
