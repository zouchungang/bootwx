package com.wx.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.*;

public class ConfigService {
    static Logger logger = Logger.getLogger(ConfigService.class);
    public static String RedisHost;
    public static int RedisPort;
    public static String RedisAuth;
    public static int RedisDB;
    public static int RobotId;
    public static boolean foreText;
    public static boolean test=false;
    private static long configLastModifyTime = 0;
    public static int server_port;
    public static String serverid;
    public static String ServerHost;
    public static String ServerIp;
    public static String APPID;
    public static String VERSION;
    public static String APPKEY;
    public static String APPTOCKEN;
    public static String APPHOST;
    public static String shortServerHost;
    public static String longServerHost;
    public static int protocolVer;
    public static int APPPORT;
    public static boolean debug;
    public static byte[] sessionKey = new byte[]{80, 117, -128, 85, 2, 55, -76, 126, -115, 93, -71, -36, 112, -114, 15, -128};

    public static void init() {
        loadProps();
        setlogname(server_port);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                try {
                    loadProps();
                    setlogname(server_port);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        timer.schedule(task, 10 * 1000, 10 * 1000);
    }

    public static void setlogname(int serverport) {
        Enumeration ees = Logger.getRootLogger().getAllAppenders();
        while (ees.hasMoreElements()) {
            Appender app = (Appender) ees.nextElement();
            if (app instanceof FileAppender) {
                FileAppender fileLogDemo = (FileAppender) app;
                fileLogDemo.setFile("logs/" + serverport + ".log");
                fileLogDemo.activateOptions();
            }
        }
    }

    public static String getRealIp() {
        String url = "http://myip.fireflysoft.net/";
        ServerIp = HttpUtil.sendPost(url, null);
        return ServerIp;
    }

    private static void loadProps() {
        //读取根目录下配置项文件
        File confFile = new File(System.getProperty("user.dir"), "config.json");
        if (configLastModifyTime != confFile.lastModified()) {
            configLastModifyTime = confFile.lastModified();
            InputStreamReader reader = null;
            try {
                reader = new InputStreamReader(new FileInputStream(confFile.toString()), "utf-8");
                char buf[] = new char[655360];
                int len;
                if ((len = reader.read(buf, 0, buf.length)) > 0) {
                    JSONObject root = JSON.parseObject(new String(buf, 0, len));
                    server_port = root.getInteger("server_port");
                    ServerIp = getRealIp();
                    ServerHost = ServerIp + ":" + server_port;
                    serverid = getMd5(ServerHost);
                    RedisHost = root.getString("redis_host");
                    APPID = root.getString("app_id");
                    APPKEY = root.getString("app_key");
                    APPTOCKEN = root.getString("machine_Code");
                    APPHOST = root.getString("app_host");
                    VERSION = root.getString("version");
                    shortServerHost = root.getString("shortServerHost");
                    longServerHost = root.getString("longServerHost");
                    APPPORT = root.getInteger("app_port");
                    protocolVer = root.getInteger("protocolVer");
                    RedisPort = root.getInteger("redis_port");
                    RobotId = root.getInteger("redis_Id");
                    RedisAuth = root.getString("redis_auth");
                    RedisDB = root.getInteger("redis_db");
                    foreText = root.getBoolean("force_text");
                    test = root.containsKey("test") ? root.getBoolean("test") : false;
                    debug = root.containsKey("debug") ? root.getBoolean("debug") : false;
                }
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static byte[] geturl(String url) {
        try {

            return HttpUtil.get(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param bytes
     * @return 将二进制转换为十六进制字符输出
     */
    private static String hexStr = "0123456789ABCDEF";

    public static String BinaryToHexString(byte[] bytes) {
        String result = "";
        String hex = "";
        for (int i = 0; i < bytes.length; i++) {
            hex = String.valueOf(hexStr.charAt((bytes[i] & 0xF0) >> 4));
            hex += String.valueOf(hexStr.charAt(bytes[i] & 0x0F));
            result += hex;
        }
        return result;
    }

    /**
     * @param hexString
     * @return 将十六进制转换为字节数组
     */
    public static byte[] HexStringToBinary(String hexString) {
        //hexString的长度对2取整，作为bytes的长度
        int len = hexString.length() / 2;
        byte[] bytes = new byte[len];
        byte high = 0;
        byte low = 0;
        for (int i = 0; i < len; i++) {
            //右移四位得到高位
            high = (byte) ((hexStr.indexOf(hexString.charAt(2 * i))) << 4);
            low = (byte) hexStr.indexOf(hexString.charAt(2 * i + 1));
            bytes[i] = (byte) (high | low);
        }
        return bytes;
    }

    String tostr(byte[] buffer) {
        try {
            int length = 0;
            for (int i = 0; i < buffer.length; ++i) {
                if (buffer[i] == 0) {
                    length = i;
                    break;
                }
            }
            return new String(buffer, 0, length, "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }


    public static String getMd5(String btInput) {
        return getMd5(btInput.getBytes());
    }

    public static String getMd5(byte[] btInput) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 字符串转化成为16进制字符串
     * @param s
     * @return
     */
    /**
     * 取两个文本之间的文本值
     *
     * @param parameters
     * @param charsetName
     * @param keyValue
     * @return
     */
    public static String createSign(SortedMap<String, Object> parameters, String charsetName, String keyValue) {
        try {
            StringBuffer sb = new StringBuffer();
            Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
            Iterator it = es.iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String k = (String) entry.getKey();
                Object v = entry.getValue();
                if (null != v && !"".equals(v)) {
                    sb.append(k + "=" + v + "&");
                }
            }
            sb.append("key=" + keyValue);

            return getMd5(sb.toString().getBytes(charsetName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getname() {
        String[] name = {"千渔", "小驭", "开娥", "思宏", "启然", "子鑫", "张今", "中文", "高炎", "永匀", "晨荣", "辉君", "宏泽", "明莲", "昊钟", "文鸿", "骏意", "泓超", "漾东", "仕松", "丽菱", "观桓", "悠君", "子兵", "医水", "之石", "丰隽", "亚林", "炅学", "梅宇", "同结", "华明", "汶洲", "悠梅", "昕腾", "才涛", "同丽", "依飞", "雨勇", "泓瑶", "彦贞", "红强", "皙萱", "建英", "福轩", "业丽", "佳芮", "梓怡", "学美", "盛平", "剑开", "芮仪", "泞惠", "张璞", "点丽", "思崴", "姿宇", "恩才", "青霏", "张愉", "维叶", "秀诚", "一宾", "相时", "昱哲", "采铭", "翌天", "海岳", "亮杰", "存原", "雁舒", "乐锋", "韶惠", "亚博", "旎婷", "梓远", "十锦", "丹祥", "季霖", "义涵", "雄月", "俊竣", "琼铭", "秋茵", "亦熠", "晨华", "俞麾", "琦娟", "瀚梅", "凡方", "皓仪", "春晶", "辰冉", "梓润", "蒋畅", "蒋贺", "君浩", "俊博", "小萱", "意辉", "蒋箐", "蒋锦", "迩谷", "蒋坤", "俊琪", "新红", "全领", "增佳", "蒋庸", "蒋斐", "开力", "文兴", "天彬", "士倩", "蒋克", "蒋婉", "锦明", "蒋晏", "泉良", "臻设", "程林", "咏爱", "鹏明", "耀儒", "韩博", "艺欣", "巨麟", "良珍", "虹胜", "潇宏", "安彤", "亦良", "润全", "立棠", "自芮", "乃盈", "蒋霓", "桂骏", "雅凤", "蒋斌", "亦鹏", "昀熙", "政玉", "蒋颜", "晓希", "宗丽", "浩钰", "小微", "蒋奕", "桢宸", "中今", "朝仪", "晓新", "子铄", "星舒", "子语", "怡晴", "春德", "蒋姗", "金煜", "帅雯", "贞元", "俏瞳", "丽铭", "蒋军", "春荷", "岩家", "蒋蕴", "梓睿", "晓彤", "雨萍", "涵涛", "佳雯", "雍宇", "佳嘉", "宝萌", "蒋迪", "蒋锌", "蒋坡", "蒋金", "广琪", "双明", "嘉葭", "泓杰", "蒋宁", "雨坤", "马芯", "钰林", "玉景", "春淇", "友成", "马梅", "马麟", "程辉", "荣平", "尉文", "丽杨", "晟玉", "怡菡", "凯诚", "誉红", "彦潼", "逸霞", "梓杭", "夕苗", "马煦", "思海", "米宵", "竞阳", "大羽", "爱冰", "亚茜", "克鑫", "一涵", "志玉", "瀛鑫", "树芝", "妸轩", "雨东", "凤帆", "颖亮", "上枫", "宏杰", "俊伟", "马蕾", "煜杰", "秦莹", "剑轩", "知亮", "宇婷", "煜元", "婧莲", "冲卫", "马之", "晚鹏", "鑫希", "静岚", "泊标", "中玲", "鹏艳", "恩华", "靖文", "马芳", "岽楷", "龙慧", "心紫", "思佳", "丹杰", "佳冰", "玳娜", "马了", "家涵", "应洪", "小欲", "秀治", "增琪", "马渔", "嘉实", "嗣华", "思颖", "永婷", "兴心", "马磬", "马媛", "修辰", "墨杰", "志雨", "洪思", "涪辉", "恩玲", "鸿泰", "梦玟", "马漩", "晓娟", "马淄", "向煌", "木文", "明杰", "景军", "紫君", "善晶", "房真", "昊松", "房微", "丁佳", "浩荃", "紫童", "杰舜", "良翔", "少芮", "建涵", "仁鸣", "房傧", "晓隽", "彤媛", "房祁", "福畅", "房亮", "翊鸽", "书宇", "淑泽", "房俊", "泰六", "思榕", "秀媚", "佳然", "誉霏", "伟海", "具军", "河涵", "小文", "竹芳", "雨聍", "怀荷", "彧明", "承彤", "睿淮", "冠义", "敏震", "宇涛", "兰坚", "筵明", "曾烁", "寰棉", "缜华", "世旎", "映林", "敬绩", "曾凝", "卓涵", "曾畅", "俊锋", "腊婷", "曾尚", "湛洋", "梅颖", "雅亮", "鸾凯", "雨梅", "曾城", "香波", "鸣佳", "香文", "悦薇", "文来", "智桥", "珍朵", "霆英", "天涵", "饴芹", "成红", "梓轩", "佳宇", "曾芙", "明帆", "子惠", "文芸", "青澄", "胜砚", "嘉旭", "曾超", "一迪", "曾莉", "梓渊", "昱博", "曾广", "锦鸣", "庆坤", "曾茸", "殷琨", "玉峰", "曾亮", "昶邑", "城涵", "梓含", "明莉", "秀一", "梦如", "彬晨", "云淼", "洪心", "哲钰", "鹏轩", "佳涛", "梦然", "冠樱", "驿轩", "雅媚", "曾瑜", "婉沁", "红源", "佳泽", "文馨", "嘉琳", "兆蕾", "晓健", "曾波", "睿燕", "曾原", "曾泺", "明浩", "曾彤", "曾钥", "雅岚", "凯勋", "曾乔", "韩爽", "瑞宇", "韩缘", "勐爱", "昌彬", "文鑫", "文尧", "雨舒", "祯智", "焯杰", "石泉", "则凡", "媛硕", "君升", "铭莹", "庄伟", "雨华", "韩婕", "尚珏", "韩钦", "彦荣", "小冠", "鑫齐", "惠聪", "韩于", "毓尔", "建平", "彬锛", "韩雨", "展恒", "韩烨", "晓明", "韩琼", "韩龙", "韩潼", "栩轩", "千宾", "怡龙", "韩七", "华弘", "韩童", "韩瑞", "丽豪", "韩旗", "韩晨", "韩维", "林谆", "宝宁", "新娇", "艺青", "韩梅", "丽丛", "昭亮", "桃恒", "永子", "韩妤", "学仁", "军霞", "韩静", "进锐", "嘉平", "秀清", "得林", "薇鸣", "梅渊", "庆怡", "佳升", "宏明", "鸣迪", "馨伦", "子瑶", "贵睿", "琼晨", "大民", "丽真", "希英", "彦武", "顺靖", "韩正", "羽辉", "呈恬", "沂涵", "立童", "闰乐", "敬淋", "妙绚", "叶耕", "瑾桓", "韩逸", "家宁", "凤仿", "瀚涛", "韩焱", "淇燮", "恩骏", "美翔", "泽江", "真谣", "郑萱", "傲云", "小涵", "郑跃", "红珊", "延明", "心云", "毛羲", "阔溪", "世涵", "茹芙", "龙怡", "春蕊", "思慧", "瑞华", "咏静", "锡达", "若入", "文宇", "虹婷", "作淇", "奕宾", "郑菁", "麒永", "永晗", "圣轩", "痍熹", "俊英", "媛瑾", "模彤", "法右", "建骋", "涵雅", "朝萍", "郑正", "娟苒", "斐龙", "城恩", "思予", "群弈", "鹏虎", "宜谦", "木智", "大修", "增澳", "郑菲", "锦萍", "思婷", "宝霞", "郑妤", "奕东", "绍明", "郑德", "滔菱", "正涵", "佳子", "智静", "怡适", "帅蕙", "郑迅", "小昊", "语师", "东瑶", "郑榕", "伟涵", "得宇", "东璇", "惠骏", "长璁", "郑柔", "晟娜", "艳豪", "郑彭", "三渝", "济平", "少几", "科生", "路昕", "郑赢", "羽茗", "沛硕", "郑恺", "艺池", "郑卉", "忠兰", "易晗", "玉馨", "占玲", "铖雨", "学英", "姚森", "楠蘅", "玉婷", "姚霭", "莉滨", "景诗", "福红", "姚柯", "小淼", "意瑞", "蓓平", "凯闻", "剑彤", "姚钺", "学燕", "应华", "国畅", "安烁", "晨臻", "荣富", "世文", "泽玉", "茂驿", "可瑜", "志捷", "姚盛", "奕哲", "洪轩", "姚婧", "曦伊", "全伟", "韵军", "咏哲", "蒙佟", "楚芳", "姚超", "姚元", "博众", "一琳", "青林", "跃甲", "姚乐", "园宇", "耀修", "银源", "铭元", "慧彦", "晋恬", "稚辉", "赫秦", "志哲", "明蓉", "睿龙", "月灏", "姚火", "鑫哲", "耀鹏", "银琪", "宛吉", "姚熙", "蔓洁", "思鹏", "松雯", "建雄", "沅祥", "姚宽", "庆芸", "云卿", "姚璇", "静晗", "楚茜", "柯诚", "锦玉", "姚流", "姚拓", "基宝", "姚薇", "子莹", "红萌", "澜梅", "惠平", "姚馨", "晟伟", "占耀", "姚婕", "歆雄", "佳翩", "明玲", "启晓", "柯辉", "姚含", "红怡", "忠淼", "春扬", "熠滨", "乔东", "弈威", "丹平", "明华", "铎婉", "芷民", "伍骏", "睿琳", "泠怡", "素磊", "语恒", "书辰", "杰杰", "亭涛", "溥林", "瀚飞", "玉然", "子松", "丽璐", "梦玲", "唐静", "贤雪", "一鑫", "唐文", "唐锋", "龙芳", "宛虹", "彤萍", "彦梅", "继梅", "唐上", "小玲", "擎伟", "伊蔓", "疏春", "唐桐", "晶平", "乃丽", "唐苗", "语曦", "奕发", "元星", "玉蒙", "思莹", "香丞", "祥泳", "舍文", "明辉", "琦乐", "秀品", "逸桔", "唐莫", "锦栓", "朔涵", "慕荣", "钻予", "庆伟", "延浩", "睿妍", "欣芝", "卫文", "子琪", "唐遥", "邵岩", "睿屏", "世华", "景娇", "骏根", "倩侠", "宗文", "乙薇", "静林", "唐星", "馨文", "嘉莹", "雯缓", "艳衡", "晓微", "婧杰", "涵新", "小波", "大榕", "笑福", "唐瑶", "小林", "金琴", "淳斌", "栩浩", "唐哲", "毅航", "名漪", "泽翔", "露瑜", "逸雯", "然彤", "泽凡", "姜兰", "为生", "博柳", "豪静", "姜煜", "胤智", "艺君", "建吉", "翔玲", "祉财", "筱辉", "军欣", "享祖", "翰生", "军雄", "纾明", "炫伟", "艺轩", "儒楠", "学涟", "姜励", "姜煊", "元铭", "康花", "德贤", "子鑫", "土洋", "冠腾", "建懿", "泽明", "宇基", "姿元", "保嘉", "强锋", "嘉珍", "姜奕", "晓源", "姜渝", "缦萌", "姜芳", "当甜", "人林", "垠琪", "佳茜", "秋菲", "紫淞", "惠翔", "建雅", "晓宁", "曾薇", "荣英", "姜鹂", "友岑", "竞岩", "丽坡", "姜芹", "京雨", "姜照", "国钦", "婀祁", "竞丽", "与煌", "姜烜", "希学", "七杰", "新柳", "尚聪", "荧平", "怡栩", "晨霖", "秭安", "姜淼", "宝睿", "宸源", "毛婷", "鸿晨", "姜砚", "璨芬", "政芬", "释媛", "嘉睫", "来立", "敦桦", "子健", "姜江", "悛洁", "庄敏", "艾兰", "瑞峰", "巍汶", "纪森", "振珂", "辛峰", "乐乜", "清童", "飞康", "家荣", "睿成", "思英", "和欣", "程焕", "骅文", "君琪", "辛凌", "昭妍", "旭文", "辛睿", "致婷", "思峰", "垄宇", "绘琛", "禾腾", "芷阳", "辛俊", "清予", "辛晔", "辛珏", "文华", "加鸢", "辛程", "嘉博", "嘉铭", "忠惠", "彦诺", "辛冬", "业栋", "晓涛", "显换", "小桦", "一佳", "曾心", "聿旺", "惠君", "麓桐", "齐棚", "齐远", "桂超", "胺晨", "晋硕", "雨轩", "翰平", "梓臻", "笑娴", "涵丰", "骢潍", "海风", "泽城", "雨军", "子平", "俊晔", "锦远", "靖心", "明萱", "亮萱", "汉轩", "凯涵", "法墨", "瑾奕", "淑铭", "晓源", "钰泫", "律如", "佑田", "齐千", "海燕", "子涵", "海阳", "曜茜", "艺君", "齐佳", "康聆", "诗珂", "朝东", "可洋", "泽呈", "齐昱", "雨鑫", "炳枫", "齐笛", "齐睫", "昌昙", "齐秀", "甘骊", "一毅", "思炜", "胤昊", "坤琪", "艾霖", "子铧", "千勤", "理洋", "齐帆", "齐婕", "紫诚", "洵宜", "丹荧", "阿熙", "若涓", "家雯", "齐晖", "正展", "雨翔", "宇朵", "媛建", "宇戈", "丰婷", "骏宸", "子祥", "文芹", "子胜", "玺帅", "明潭", "新甫", "千华", "慧震", "瑞茹", "星升", "若霞", "玉城", "健玲", "玉芸", "海夫", "鸿阳", "振婷", "诚涵", "思林", "水轩", "夏默", "嘉运", "聪烨", "雅庭", "澄林", "冬良", "巴菱", "叶林", "树亚", "海鹰", "慧霖", "太金", "雨晨", "橹涵", "可辛", "裕谨", "子慧", "勋怡", "泓馨", "惠茹", "明谊", "子芸", "江识", "葱阳", "夏薇", "一格", "云悦", "云玉", "新荣", "夏悠", "永木", "谈芳", "兰岳", "夏凡", "海昶", "乐霖", "田翔", "双方", "宇妮", "李彬", "予清", "建晨", "鸿林", "夏东", "相炅", "夏鲜", "夏寒", "瑗非", "夏与", "夏至", "文铭", "梓乾", "元素", "靖霖", "夏烨", "子平", "佳琳", "沛红", "夏果", "芷莉", "万蓓", "春豫", "煜滢", "钰舒", "黎月", "永强", "明润", "丞宇", "雯雅", "雁惠", "涵菱", "舒惠", "岚菱", "昊茗", "玥宁", "妘容", "芹潞", "从芸", "香琴", "芯茹", "冉抒", "茹轩", "蕊昱", "睿祁", "容歆", "轩玥", "芸绮", "盈雯", "凝遥", "萱焓", "多睿", "芩喧", "欣甜", "书乐", "颜抒", "昕宇", "谨谣", "锦然", "夏烁", "锦语", "玲鹭", "雪芩", "萧茹", "懿涵", "旭芹", "茵云", "羽溪", "忻灿", "珺谣", "馨元", "淑云", "莉馨", "梦蝶", "琳潼", "馨雅", "致语", "初露", "昕潞", "爱琴", "亦芸", "天翼", "昕芳", "依笛", "凤娟", "宇煊", "安娜", "惠彤", "娴易", "誉雯", "芩煊", "予宇", "婷秀", "雨彤", "夏颢", "雨泽", "婧云", "昱茹", "锦文", "荣诗", "笑芝", "语莲", "晓凡", "寄耘", "晗晨", "翠玲", "颜晗", "佳泓", "忻菲", "念暄", "琦兴", "秒欣", "颢舒", "玥暮", "容琼", "宸谣", "雁红", "耘雨", "盈昕", "丽书", "灵秀", "誉然", "湘晗", "雪甜", "文智", "姝伶", "芯洁", "译羽", "晨烨", "佩芩", "嘉羽", "语蔚", "美菏", "恩瑶", "纾芹", "芹蕊", "雁函", "涵妍", "彩菱", "聪晨", "诗煊", "玥函", "雯心", "含儒", "碧蝶", "莉语", "耘怀", "笑晗", "遥琴", "慧云", "函依", "南蓉", "清心", "遥羿", "羽思", "湘峪", "晨湘", "美儒", "兰韵", "念滢", "谣捷", "洳初", "瑾雯", "函文", "琴琀", "耘妤", "昕谣", "正妍", "舒睿", "修哲", "薇莹", "馨愉", "梦颜", "欢柔", "舒瑛", "云霏", "笑诗", "灵钰", "妍瑗", "含碹", "若辰", "宜岚", "璐雯", "毓静", "嘉美", "羽珏", "妍弘", "云忆", "雁鸿", "妍美", "纯娴", "艺含", "寄香", "蕊丹", "莺文", "芸璐", "笛妤", "彤夕", "晖莹", "清婉", "菲悦", "淑卉", "妘灏", "钧遥", "琳露", "芯文", "新睿", "聆雯", "依书", "锦颖", "以芸", "水芸", "雯亿", "润银", "舒萌", "婿睛", "旻琳", "翌茹", "莹翌", "如凡", "舒颐", "芳琳", "逸甜", "函芸", "歆洳", "暄笑", "翠茹", "宇欣", "抒谣", "淑琲", "雅姝", "颢轩", "安慧", "婌怀", "芹昊", "灵瞳", "涵燕", "宇宣", "秋莲", "锦雯", "智欣", "妤芠", "云纾", "烁浠", "珺谣", "琀雅", "韵颔", "水蓉", "意婌", "云含", "憧瑶", "依蝶", "雨译", "问雪", "晴晔", "辰茹", "婉曼", "羽轩", "晨悦", "雯彤", "昕叶", "采喧", "胜怡", "美瑗", "秀逸", "淑旋", "颍菡", "柔芹", "书枫", "婌莺", "鸿耘", "琳焓", "泽佳", "旭甜", "夕谣", "雅飞", "梓忻", "舒萌", "丽琳", "宛茹", "馨淯", "歆年", "蕊婷", "梦露", "莎雅", "函容", "千奕", "萱滢", "姝琦", "予羽", "舒弘", "雯予", "萤含", "诗姝", "欣斐", "茹渲", "露雅", "烨雪", "睿媚", "梦依", "黛萱", "莹骄", "欣辰", "雯歆", "馨琦", "涵韵", "安萱", "依笛", "心蕊", "舒菲", "鹭智", "姝逍", "琳善", "畅莺", "皓云", "焓音", "忆忻", "妙晴", "滢昱", "汝熙", "谣旭", "美羽", "舒晴", "若菡", "娜宇", "舒倩", "妘欷", "旻湘", "霜宇", "澜莹", "含湘", "颍舒", "誉珍", "霏涵", "函娇", "骄扬", "亿歆", "宁耘", "宇默", "亦蕊", "蕊誉", "晨洳", "语文", "佳韵", "欣聪", "柔如", "宁云", "加怡", "新言", "紫函", "心皓", "莉姝", "雨颢", "晟茹", "娴依", "含芹", "西函", "昕宜", "暄文", "羽意", "修含", "欣叶", "海洁", "滢倩", "莹雅", "晓纾", "若瑶", "云儒", "素忻", "湘孺", "书岚", "予云", "新凡", "梦懿", "茹琴", "书琀", "程歆", "焓玲", "羽姻", "亦彤", "之歆", "雨歆", "晗抒", "孺遥", "惜香", "语芩", "诗桐", "芩柔", "琳淼", "琴诺", "谦滢", "婌育", "妘勤", "奕浛", "秋荣", "舒桦", "儒梦", "易晨", "郁琬", "利羽", "翌焓", "雨娅", "宇雯", "溢琼", "羽莲", "誉然", "诗娜", "昊文", "玥姻", "潇睿", "舒文", "涵颖", "雨蓓", "睿晴", "小阳", "茹晗", "宁玲", "琴晔", "如雪", "慈遥", "馨梅", "向璐", "言烨", "芯憧", "昔纯", "梦芩", "誉纯", "耘绮", "昊汝", "晗颜", "可忆", "骊颖", "宸蕾", "语濡", "婌荣", "琴萱", "瑾琳", "淑柔", "菡露", "彦忻", "馨煊", "睿喧", "玥聪", "亦雯", "莹妃", "羽漩", "欣昱", "懿昕", "抒菲", "语灏", "溪雯", "馨毓", "蔓遥", "新婌", "心璐", "耘琪", "婷俪", "品梦", "昔琳", "语霏", "洁文", "晴含", "颢琴", "宸聪", "意耘", "莹弈", "容蕊", "姝羽", "悦亦", "汝元", "媛祺", "初鹭", "箫芩", "艺颖", "语真", "美扬", "思宇", "盈烁", "岚如", "昱雅", "菡喧", "荞蕾", "姝孺", "喧抒", "莹奕", "若翡", "雅纾", "亚娇", "迎蔓", "曼荷", "芹抒", "雅琳", "芯娅", "亦焓", "辰嘉", "浩焓", "琴羽", "昕得", "昔潇", "依薪", "颢函", "雅信", "懿语", "歆淇", "煜琼", "茹莺", "文洳", "瑞文", "茹喧", "颢阳", "淑渲", "茵宸", "蓉岩", "耘为", "颜璐", "抒孺", "莉雯", "睛菲", "彤雅", "书函", "语云", "淑芸", "雪芹", "懿宏", "从耘", "雯箫", "忆融", "予姝", "致萱", "美耘", "涵清", "欢月", "珺媛", "丝雯", "岚瑛", "岚涵", "雯碧", "淑菲", "媛岚", "岚晴", "惠菲", "涵絮", "寒舞", "絮华", "茹裳", "茹雅", "惠雯", "晴语", "涵雁", "翔雅", "云岚", "茜婷", "舒菡", "晴嘉", "絮嫣", "茵茜", "淑嫣", "雯茜", "涵媚", "雅梦", "媛珺", "岚语", "云惠", "岚嫣", "涵雅", "翔梦", "茵华", "云茜", "清岚", "惠媚", "雁菱", "茜语", "晴絮", "雅绿", "云雅", "涵雯", "舒婷", "珺裳", "茵嘉", "茜嘉", "茜雯", "茜瑛", "涵淑", "寒梦", "惠茹", "舒雅", "雯茵", "涵瑛", "晴瑛", "寒茹", "舒语", "清晴", "惜茵", "淑婷", "媛婷", "雯鸣", "舒淑", "涵云", "雯涵", "寒菱", "淑语", "媚雅", "茜寒", "珺淑", "惠珺", "茹菡", "清嫣", "媚嘉", "惠嫣", "晴云", "雯珺", "茹雯", "涵华", "寒惠", "淑茜", "茹珺", "云舒", "珺睿", "惠雅", "珺菡", "惠睿", "晴茜", "岚嫦", "云涵", "晴惠", "涵惠", "惠絮", "涵菡", "雯婷", "寒淑", "晴清", "淑涵", "珺涵", "云华", "舒媛", "岚雅", "清华", "寒菊", "涵茵", "岚菡", "岚菲", "寒云", "茹絮", "寒媛", "岚瑜", "淑淑", "惠语", "寒华", "涵婷", "晴珺", "寒瑜", "云嫦", "茵清", "茵嫣", "惠云", "翔雯", "淑梦", "晴菡", "珺云", "清雅", "雯嘉", "雯舒", "茜菡", "云嫣", "清梦", "惠茜", "茜华", "茜茜", "舒菲", "婷雯", "翔嘉", "晴岚", "晴翠", "雅舒", "茹语", "翔媛", "惠嘉", "云絮", "茹云", "雯翔", "雅淑", "雯嫣", "岚茹", "雯淑", "茜云", "晴睿", "茜梦", "云菡", "岚萍", "茵雅", "涵菲", "茵茹", "晴茵", "岚婷", "涵语", "寒雁", "淑雅", "岚舒", "寒嫣", "涵绮", "茜珺", "淑菡", "茜舒", "媚晴", "珺媚", "岚珺", "惠嫦", "舒华", "晴媛", "涵涵", "茵睿", "媛嫣", "雯菲", "茹惠", "晴雅", "岚淑", "寒媚", "雯惠", "岚雯", "茵语", "云珺", "惠瑛", "惠舒", "茜晴", "惠菡", "云瑜", "珺菲", "寒云", "舒岚", "翔淑", "珺惠", "琦翾", "晴华", "雯菱", "茜嫣", "岚云", "媛雅", "茹嫣", "茜惠", "云梦", "惠华", "晴晴", "茜茵", "茹瑜", "茜瑜", "惠婷", "茹菱", "茜茹", "媛惠", "雯绮", "雯云", "涵瑜", "惠菱", "涵珺", "惠舞", "惠雁", "雯媛", "岚碧", "茹嘉", "絮雅", "雯絮", "寒翔", "淑岚", "舒涵", "惠媛", "茜雅", "翔瑛", "清语", "雯萍", "寻雁", "清嘉", "茜媛", "涵茹", "惠晴", "茵惠", "媛菲", "珺绮", "茹菲", "涵嫣", "翔鸣", "寒睿", "雯瑛", "惠淑", "岚雁", "茜菲", "寒岚", "茜嫦", "茜菊", "涵媛", "淑云", "舒嫣", "清云", "茵萍", "淑华", "媛语", "云晴", "寒鸣", "云清", "岚华", "淑惠", "雯语", "晴茹", "雯晴", "雯睿", "清茜", "芬迎", "恬梨", "芙婕", "宸旋", "翾琳", "桐卿", "宸英", "素娅", "花曼", "芳梨", "芳海", "素海", "宸婕", "芳敏", "宸曼", "花敏", "素若", "素英", "珊婧", "桐彩", "桑婉", "珊娅", "宸悦", "珊英", "倚婕", "芬敏", "芬雪", "珊旋", "芙卿", "玲紫", "芬旋", "珊悦", "娜娅", "夏娅", "桑曼", "素婉", "花婉", "凌旋", "桑婕", "芸海", "芝婧", "蓝莺", "素敏", "芬婉", "桐旋", "花婕", "珊若", "芙悦", "芳婉", "夏卿", "珊雪", "倩婕", "花娅", "纹彩", "芙婉", "芹曼", "芬曼", "桐娅", "素曼", "宸雪", "桐梅", "芝雪", "素雪", "娜曼", "倩婉", "芳英", "芳珠", "宸珠", "芳曼", "芬梨", "芳婧", "芳卿", "翾琪", "宸卿", "桐悦", "倩雪", "素迎", "花卿", "素婧", "娜卿", "芳甜", "桐敏", "芙娅", "珊卿", "珊曼", "夏婕", "芷雪", "纹珠", "素旋", "珍娅", "笑甜", "夏婉", "娜婕", "素婕", "娜若", "芳娅", "凌雪", "宸婉", "芝英", "夏曼", "绿夏", "筠佩", "舞桑", "菱宸", "菊娜", "语恬", "淑音", "歆沐", "虞明", "绮凌", "涵柳", "晴柔", "虞青", "诗英", "欢蕾", "诗沁", "尔珍", "淑柳", "清盈", "翠芙"};
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            int num = rand.nextInt(1976) + 1;
            return name[num] + "的 iPad";
        }
        return "";
    }

    public static String getMac(String usercode) {
        String res = "";
        if (usercode.length() < 12) {
            usercode = ConfigService.getMd5(usercode.getBytes());
        }
        for (int x = 0; x < 6; x++) {
            res += usercode.substring(x * 2, x * 2 + 2);
            res += ":";
        }
        res = res.substring(0, res.length() - 1);
        return res;
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().toUpperCase();
    }

    public static String getDevideId(String secondUUid) {
        return "49" + getMd5(secondUUid).substring(2);
    }

    public static String getDeviceType(String deviceId) {
        return "<k21>TP_lINKS_5G</k21><k22>中国移动</k22><k24>" + getMac(deviceId) + "</k24>";
    }

    public static String getDeviceType(String Mac, String RouterName, String OperatorName) {
        if (RouterName == null) {
            RouterName = "TP_lINKS_5G";
        }
        if (OperatorName == null) {
            OperatorName = "中国移动";
        }
        return "<k21>" + RouterName + "</k21><k22>" + OperatorName + "</k22><k24>" + Mac + "</k24>";
    }

    /**
     * 字符串转化成为16进制字符串
     *
     * @param s
     * @return
     */
    public static String strTo16(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    /**
     * 取两个文本之间的文本值
     *
     * @param text
     * @param left
     * @param right
     * @return
     */
    public static String GetSubString(String text, String left, String right) {
        String result = "";
        int zLen;
        if (left == null || left.isEmpty()) {
            zLen = 0;
        } else {
            zLen = text.indexOf(left);
            if (zLen > -1) {
                zLen += left.length();
            } else {
                zLen = 0;
            }
        }
        int yLen = text.indexOf(right, zLen);
        if (yLen < 0 || right == null || right.isEmpty()) {
            yLen = text.length();
        }
        result = text.substring(zLen, yLen);
        return result;
    }

    public static int byteArrayToInt(byte[] b, int offset) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (b[i + offset] & 0x000000FF) << shift;
        }
        return value;
    }

    public static String getPublicIpAddress() throws Exception {
        return Settings.getSet().localIp;
    }
    /**
     * 实体类转成Map对象
     * @param model
     * @return
     */
    public static <T> Map<String, Object> Entity2Map(Object model){
        Map<String, Object> map = null;
        try{
            map = EntToMap(model,model.getClass(),map);
        }catch(Exception e){
            e.printStackTrace();
        }
        return map;
    }

    private static <T> Map<String,Object> EntToMap(Object model, Class<T> t, Map<String, Object> map){
        try{
            Field[] fields =t.getDeclaredFields();
            if(fields.length>0 && map==null)
                map = new HashMap<String,Object>();
            for(Field f:fields){
                String name = f.getName();
                name = name.substring(0,1).toUpperCase()+name.substring(1); //将属性的首字符大写，方便构造get，set方法
                Method m = model.getClass().getMethod("get"+name);
                String value = String.valueOf(m.invoke(model));
                if(map!=null && value!=null)
                    map.put(f.getName(), value);
                else
                    map.put(f.getName(), "");
            }
            if(t.getSuperclass()!=null){
                EntToMap(model, t.getSuperclass(), map);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return map;
    }

    public static String makePK(String area, String uid) {
        return getMd5((area + uid).getBytes()).substring(8, 24);
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        ConfigService.logger = logger;
    }

    public static String getRedisHost() {
        return RedisHost;
    }

    public static void setRedisHost(String redisHost) {
        RedisHost = redisHost;
    }

    public static int getRedisPort() {
        return RedisPort;
    }

    public static void setRedisPort(int redisPort) {
        RedisPort = redisPort;
    }

    public static String getRedisAuth() {
        return RedisAuth;
    }

    public static void setRedisAuth(String redisAuth) {
        RedisAuth = redisAuth;
    }

    public static int getRedisDB() {
        return RedisDB;
    }

    public static void setRedisDB(int redisDB) {
        RedisDB = redisDB;
    }

    public static int getRobotId() {
        return RobotId;
    }

    public static void setRobotId(int robotId) {
        RobotId = robotId;
    }

    public static boolean isForeText() {
        return foreText;
    }

    public static void setForeText(boolean foreText) {
        ConfigService.foreText = foreText;
    }

    public static boolean isTest() {
        return test;
    }

    public static void setTest(boolean test) {
        ConfigService.test = test;
    }

    public static long getConfigLastModifyTime() {
        return configLastModifyTime;
    }

    public static void setConfigLastModifyTime(long configLastModifyTime) {
        ConfigService.configLastModifyTime = configLastModifyTime;
    }

    public static int getServer_port() {
        return server_port;
    }

    public static void setServer_port(int server_port) {
        ConfigService.server_port = server_port;
    }

    public static String getServerid() {
        return serverid;
    }

    public static void setServerid(String serverid) {
        ConfigService.serverid = serverid;
    }

    public static String getServerHost() {
        return ServerHost;
    }

    public static void setServerHost(String serverHost) {
        ServerHost = serverHost;
    }

    public static String getServerIp() {
        return ServerIp;
    }

    public static void setServerIp(String serverIp) {
        ServerIp = serverIp;
    }

    public static String getAPPID() {
        return APPID;
    }

    public static void setAPPID(String APPID) {
        ConfigService.APPID = APPID;
    }

    public static String getVERSION() {
        return VERSION;
    }

    public static void setVERSION(String VERSION) {
        ConfigService.VERSION = VERSION;
    }

    public static String getAPPKEY() {
        return APPKEY;
    }

    public static void setAPPKEY(String APPKEY) {
        ConfigService.APPKEY = APPKEY;
    }

    public static String getAPPTOCKEN() {
        return APPTOCKEN;
    }

    public static void setAPPTOCKEN(String APPTOCKEN) {
        ConfigService.APPTOCKEN = APPTOCKEN;
    }

    public static String getAPPHOST() {
        return APPHOST;
    }

    public static void setAPPHOST(String APPHOST) {
        ConfigService.APPHOST = APPHOST;
    }

    public static String getShortServerHost() {
        return shortServerHost;
    }

    public static void setShortServerHost(String shortServerHost) {
        ConfigService.shortServerHost = shortServerHost;
    }

    public static String getLongServerHost() {
        return longServerHost;
    }

    public static void setLongServerHost(String longServerHost) {
        ConfigService.longServerHost = longServerHost;
    }

    public static int getProtocolVer() {
        return protocolVer;
    }

    public static void setProtocolVer(int protocolVer) {
        ConfigService.protocolVer = protocolVer;
    }

    public static int getAPPPORT() {
        return APPPORT;
    }

    public static void setAPPPORT(int APPPORT) {
        ConfigService.APPPORT = APPPORT;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        ConfigService.debug = debug;
    }

    public static byte[] getSessionKey() {
        return sessionKey;
    }

    public static void setSessionKey(byte[] sessionKey) {
        ConfigService.sessionKey = sessionKey;
    }
}