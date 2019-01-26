package com.wx.service;

import org.apache.log4j.Logger;

import com.wx.bean.Message;

public class Service_Demo extends BaseService{
	
	 private static Logger logger = Logger.getLogger(Service_Demo.class);

	public Service_Demo(String randomids) {
		super(randomids);
	}

	@Override
	public void parseMsg(Message msg) {
        logger.info(msg.toString());
	}

}
