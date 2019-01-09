package com.bootdo.api;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class test {

/*    static {
        System.out.println( System.getProperty("java.library.path"));
        System.loadLibrary("Dll2");
    }

    public native void WXObjectRelease();//动态库中调用的方法

    public native int main();//动态库中调用的方法*/

    @Test
    public static void main(String[] args) {
        Wxxy qdPos=Wxxy.INSTANCE;
        Pointer obj = new Pointer(200);
        byte[] obj1 = new byte[100];
        PointerByReference ret = new PointerByReference();

        System.out.println("初始化授权→"+qdPos.WXSetNetworkVerifyInfo("120.79.185.75",18808));
       //System.out.println(qdPos.WXQRCodeDecode(obj,ret));
        System.out.println("获取二维码→"+qdPos.WXGetQRCode(obj1,ret));

        String uuid = UUID.randomUUID().toString();

        System.out.println("初始化设备→"+qdPos.WXInitialize(obj,"a","<softtype><k3>9.0.2</k3><k9>iPad</k9><k10>2</k10><k19>58BF17B5-2D8E-4BFB-A97E-38F1226F13F8</k19><k20>"+uuid+"</k20><k21>neihe_5GHz</k21><k22>(null)</k22><k24>34-97-F6-90-66-89</k24><k33>\\345\\276\\256\\344\\277\\241</k33><k47>1</k47><k50>1</k50><k51>com.tencent.xin</k51><k54>iPad4,4</k54></softtype>",uuid));
        System.out.println(obj);
        String sign ="";
            sign=new String(obj1);
            System.out.println(sign);

        /*PointerByReference ret1 = new PointerByReference(obj);
        System.out.println(ret1.getValue());*/
      //  System.out.println(ret.getValue());


//        qdPos.WXGetQRCode(obj,ret);
        String[] rrr = null;
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        //  System.out.println("rrr"+qdPos.WXGetQRCode(obj,ret));
   //     System.out.println("rrr"+ ret);
        //   System.out.println(qdPos.WXGetQRCode());
/*        try {
            System.out.println(disConnect(1));
        } catch (NativeException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }*/
    }

/*    *//**
     * 分配内存，并返回指针
     *//*
    public Pointer createPointer() throws NativeException {
        Pointer pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(getSizeOf()));
        return pointer;
    }

    /**
     * 内存大小
     */
/*    public int getSizeOf() {
        return 8 * 2;
    }
    private  static  String dllNmae ="xcode.DLL";
    public static int  disConnect(int hHandle) throws NativeException, IllegalAccessException
    {
        JNative jnative = new JNative(dllNmae,"WXGetQRCode");
        jnative.setRetVal(Type.INT);
        int pindex = 0;
        jnative.setParameter(pindex++, hHandle);
        jnative.invoke();
        return jnative.getRetValAsInt();
    }*/





}
