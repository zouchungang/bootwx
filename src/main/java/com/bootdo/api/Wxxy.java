package com.bootdo.api;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.StdCallLibrary;

public interface Wxxy extends StdCallLibrary {

    Wxxy INSTANCE = (Wxxy) Native.loadLibrary("xcode", Wxxy.class);


    /**枚举蓝牙设备*/
    WinDef.BOOL WXObjectRelease();

    /**枚举串口设备*/
    WinDef.BOOL enumCOMPos(WString filter, WString[] deviceNames);

    /**获取二维码**/
  //  IntByReference  WXQRCodeDecode(String obj,String ret);

    /**获取授权**/
    int WXSetNetworkVerifyInfo(String ip, int port);
    /**设备初始化**/
    int WXInitialize(Pointer pVoid, String devname, String devtype, String uuid);

    int WXInitialize(byte[] pVoid, String devname, String devtype, String uuid);

    /**获取二维码**/
    IntByReference  WXGetQRCode(Pointer pVoid, PointerByReference ret);

    IntByReference  WXGetQRCode(byte[] pVoid, PointerByReference ret);


}
