package com.bootdo.common.service;

import com.bootdo.wx.domain.TaskinfoDO;

public interface SettleAccountsJobService {

    int run(TaskinfoDO taskinfoDO);

}
