package com.bootdo.api;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.ByReference;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.StdCallLibrary;

import java.util.ArrayList;
import java.util.List;

public interface Wxxy extends StdCallLibrary {

    Wxxy INSTANCE = (Wxxy) Native.loadLibrary("xcode.dll", Wxxy.class);


    /**枚举蓝牙设备*/
    WinDef.BOOL WXObjectRelease();

    /**枚举串口设备*/
    WinDef.BOOL enumCOMPos(WString filter, WString[] deviceNames);

    /**获取授权**/
    int WXSetNetworkVerifyInfo(String ip, int port);

    int WXInitialize(Pointer pVoid, String devname, String devtype, String uuid);

    int WXCheckQRCode(int obj, PointerByReference ret);

    /**获取二维码**/
    int  WXGetQRCode(int pVoid, PointerByReference ret);

    /**
     * 获取登录token
     * @param pVoid
     * @param ret
     * @return
     */
    IntByReference  WXGetLoginToken(int pVoid, PointerByReference ret);

    /**
     * 自动登录
     * @param pVoid
     * @param ret
     * @return
     */
    IntByReference  WXAutoLogin(int pVoid,String token, PointerByReference ret);

    /**
     * 获取62数据
     * @param pVoid
     * @param ret
     * @return
     */
    IntByReference  WXGenerateWxDat(int pVoid, PointerByReference ret);

    IntByReference  WXGetContact(int pVoid,String user,PointerByReference ret);

    /**
     * 获取标签列表
     * @param pVoid
     * @param ret
     * @return
     */
    IntByReference  WXGetContactLabelList(int pVoid,PointerByReference ret);

    /**
     * 同步通讯录
     * @param pVoid
     * @param ret
     * @return
     */
    IntByReference  WXSyncContact(int pVoid,PointerByReference ret);

    IntByReference  WXUserLogin(int pVoid,String user,String pwd,PointerByReference ret);

    IntByReference  WXGetQRCode(byte[] pVoid, PointerByReference ret);

    int WXQRCodeLogin(int pVoid,String username,String password,PointerByReference ref);


}
