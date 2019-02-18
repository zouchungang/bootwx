package com.wx.service;

import com.wx.tools.Settings;

public enum ServiceSoftwareId {

    SOFTWARE_Demo(Settings.getSet().SOFTWARE_DEMO),
    MENE("777"),

    SHQB2("12354332"),

    NIUNIU("73405343"),
    SHQB_HELPER("938478032"),
    NIUNIU_HELPER("734053432"),
    QLJSF("78674541");
    public String softwareId;

    ServiceSoftwareId(String softwareId) {
        this.softwareId = softwareId;
    }
}
