package com.itheima.ssm.service;

import com.itheima.ssm.domain.SysLog;

import java.util.List;

public interface ISysLogService {
    void savaLog(SysLog sysLog);

    List<SysLog> findAll();

}
