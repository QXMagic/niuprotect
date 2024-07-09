package accessibility.lib;

import android.provider.Settings;

import com.blankj.utilcode.util.ObjectUtils;

import java.security.MessageDigest;
import java.util.UUID;

import accessibility.App;

public class CommonUtils {
    public static String md5(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            char[] charArray = str.toCharArray();
            byte[] bArr = new byte[charArray.length];
            for (int i = 0; i < charArray.length; i++) {
                bArr[i] = (byte) charArray[i];
            }
            byte[] digest = messageDigest.digest(bArr);
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                int i2 = b & 255;
                if (i2 < 16) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(Integer.toHexString(i2));
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
    }

    //    @SuppressLint({"HardwareIds"})
//    /* renamed from: 偣炱嘵蟴峗舟轛, reason: contains not printable characters */
    public static String m21223() {
        String str = null;
        String m15533 = DataKV.m15533("deviceId", null);
        if (ObjectUtils.isNotEmpty((CharSequence) m15533)) {
            return m15533;
        }

        try {
            str = Settings.Secure.getString(App.mContext.getContentResolver(), "android_id");
        } catch (Exception unused) {
        }
        if (str == null || str.equals("9774d56d682e549c") || str.length() < 5) {
            str = md5(String.valueOf(UUID.randomUUID()));
        }
        DataKV.m25859("deviceId", str);
        return str;
    }
//
//    /* renamed from: 刻槒唱镧詴, reason: contains not printable characters */
//    public static void m21224() {
//        SPUtils.getInstance().put("username", "");
//        SPUtils.getInstance().put("head_img", "");
//        SPUtils.getInstance().put("online_token", "");
//        SPUtils.getInstance().put("mem_time", "");
//    }
//
//    /* renamed from: 卝閄侸靤溆鲁扅, reason: contains not printable characters */
//    public static String m21225(String str, String str2, String str3) {
//        int indexOf = str.indexOf(str2);
//        int indexOf2 = str.indexOf(str3);
//        if (indexOf < 0) {
//            return "字符串 :---->" + str + "<---- 中不存在 " + str2 + ", 无法截取目标字符串";
//        }
//        if (indexOf2 < 0) {
//            return "字符串 :---->" + str + "<---- 中不存在 " + str3 + ", 无法截取目标字符串";
//        }
//        return str.substring(indexOf, indexOf2).substring(str2.length());
//    }
//
//    /* renamed from: 垡玖 */
//    public static boolean m13845(String str) {
//        return Integer.parseInt(str) >= new Random().nextInt(100) + 1;
//    }
//
//    /* JADX WARN: Multi-variable type inference failed */
//    /* renamed from: 彻薯铏螙憣欖愡鼭, reason: contains not printable characters */
//    public static void m21226(Context context, ArrayList<String> arrayList, int i) {
//        ((GalleryWrapper) Album.m21639(context).m23873(false).m23866(Widget.m19653(context).m19678("查看图片").m19681())).m23872(i).m23874(arrayList).m14775();
//    }
//
//    /* renamed from: 斃燸卺驼暲各撟嫺眧樬硱, reason: contains not printable characters */
//    public static String m21227(Date date) {
//        return DateUtil.m14311(date, "yyyy年MM月dd日");
//    }
//
    public static String m21228() {
        try {
            return App.mContext.getPackageManager().getApplicationInfo(App.mContext.getPackageName(), 128).metaData.getString("UMENG_CHANNEL");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean m21239() {
        return false;
    }


//
//    /* renamed from: 朽劔蚁灋嵿齩鶴琓麃沼瀙缹, reason: contains not printable characters */
//    public static String m21229() {
//        return SPUtils.getInstance().getString("user_id", "");
//    }
//
//    /* renamed from: 櫓昛刓叡賜, reason: contains not printable characters */
//    public static String m21230(String str) {
//        if (StringUtils.isEmpty(str)) {
//            return null;
//        }
//        return str.substring(str.lastIndexOf(47) + 1);
//    }
//
//    /* renamed from: 灞酞輀攼嵞漁綬迹, reason: contains not printable characters */
//    public static String m21231(int i) {
//        int floor = (int) Math.floor(i / 60.0f);
//        int i2 = i % 60;
//        if (floor == 0) {
//            return i2 + "分钟";
//        }
//        return floor + "小时" + i2 + "分钟";
//    }
//
//    /* renamed from: 睳堋弗粥辊惶, reason: contains not printable characters */
//    public static String m21232(Context context) {
//        ClipData primaryClip;
//        try {
//            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService("clipboard");
//            return (clipboardManager == null || (primaryClip = clipboardManager.getPrimaryClip()) == null) ? "" : primaryClip.getItemAt(0).getText().toString().trim();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//    }
//
//    /* renamed from: 瞙餃莴埲, reason: contains not printable characters */
//    public static String m21233() {
//        if (!m21235()) {
//            return "0";
//        }
//        String m21229 = m21229();
//        if (!ObjectUtils.isNotEmpty((CharSequence) m21229)) {
//            return "0";
//        }
//        UserDim userDim = (UserDim) LitePal.where("userId = ?", m21229).findFirst(UserDim.class);
//        if (userDim != null) {
//            return userDim.getUserIdDim();
//        }
//        String str = (new Random().nextInt(89) + 10) + m21229 + (new Random().nextInt(89) + 10);
//        UserDim userDim2 = new UserDim();
//        userDim2.setUserId(m21229);
//        userDim2.setUserIdDim(str);
//        userDim2.save();
//        return str;
//    }
//
    public static String m21234() {
        String m15533 = DataKV.m15533("child_c_d_id", "");
        if (!ObjectUtils.isEmpty((CharSequence) m15533)) {
            return m15533;
        }
//        ChildBind childBind = (ChildBind) LitePal.findFirst(ChildBind.class);
//        if (ObjectUtils.isEmpty(childBind)) {
//            return "";
//        }
//        String valueOf = String.valueOf(childBind.getD_id());
        String valueOf = "";
        DataKV.m25859("child_c_d_id", valueOf);
        return valueOf;
    }
//
//    /* renamed from: 綩私 */
//    public static String m13846() {
//        return SPUtils.getInstance().getString("online_token", "");
//    }
//
//    /* renamed from: 耣怳匮色紝参凵蛴纆勚躄, reason: contains not printable characters */
//    public static boolean m21235() {
//        return !TextUtils.isEmpty(m13846());
//    }
//
//    /* renamed from: 肌緭 */
//    public static Map<String, String> m13847(String str) {
//        LinkedHashMap linkedHashMap = new LinkedHashMap();
//        if (Build.VERSION.SDK_INT >= 26) {
//            Matcher matcher = Pattern.compile("(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)(?<county>[^县]+县|.+区|.+市|.+旗|.+海域|.+岛)?(?<town>[^区]+区|.+镇)?(?<village>.*)").matcher(str);
//            while (matcher.find()) {
//                String group = matcher.group("province");
//                String str2 = "";
//                linkedHashMap.put("province", group == null ? "" : group.trim());
//                String group2 = matcher.group("city");
//                linkedHashMap.put("city", group2 == null ? "" : group2.trim());
//                String group3 = matcher.group("county");
//                linkedHashMap.put("county", group3 == null ? "" : group3.trim());
//                String group4 = matcher.group("town");
//                linkedHashMap.put("town", group4 == null ? "" : group4.trim());
//                String group5 = matcher.group("village");
//                if (group5 != null) {
//                    str2 = group5.trim();
//                }
//                linkedHashMap.put("village", str2);
//            }
//        }
//        return linkedHashMap;
//    }
//
//    /* renamed from: 葋申湋骶映鍮秄憁鎓羭, reason: contains not printable characters */
//    public static long m21236(String str, String str2) {
//        if (ObjectUtils.equals("0000-00-00 00:00:00", str) || ObjectUtils.isEmpty((CharSequence) str)) {
//            return 0L;
//        }
//        try {
//            Date parse = new SimpleDateFormat(str2, Locale.CHINA).parse(str);
//            if (parse != null) {
//                return parse.getTime() / 1000;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0L;
//    }
//
//    /* renamed from: 蝸餺閃喍, reason: contains not printable characters */
//    public static String m21237(Date date) {
//        return DateUtil.m14311(date, "HH:mm:ss");
//    }
//
//    /* renamed from: 酸恚辰橔纋黺, reason: contains not printable characters */
//    public static String m21238() {
//        int i = SPUtils.getInstance().getInt("child_d_id", 0);
//        return i == 0 ? "" : String.valueOf(i);
//    }
//
//    /* renamed from: 鑭撇糁綖浓緗轟鱼萟磿焈, reason: contains not printable characters */
//    public static boolean m21239() {
//        return "ztl_system".equals(m21228());
//    }
//
//    /* renamed from: 镐藻 */
//    public static String m13848() {
//        BindDevice bindDevice = (BindDevice) LitePal.where("device_id=?", SPUtils.getInstance().getString("child_device_id")).findFirst(BindDevice.class);
//        return ObjectUtils.isNotEmpty(bindDevice) ? bindDevice.getPackagename() : "";
//    }
//
//    /* renamed from: 陟瓠魒踱褢植螉嚜, reason: contains not printable characters */
//    public static boolean m21240() {
//        String string = SPUtils.getInstance().getString("mem_time", "");
//        return m21235() && !ObjectUtils.isEmpty((CharSequence) string) && m21236(string, "yyyy-MM-dd HH:mm:ss") > System.currentTimeMillis() / 1000;
//    }
//
//    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐, reason: contains not printable characters */
//    public static long m21241(String str, String str2) {
//        if (ObjectUtils.equals("0000-00-00 00:00:00", str)) {
//            return 0L;
//        }
//        try {
//            Date parse = new SimpleDateFormat(str2, Locale.CHINA).parse(str);
//            if (parse != null) {
//                return parse.getTime();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0L;
//    }
}
