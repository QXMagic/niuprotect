package accessibility.lib;

import android.content.Context;
import android.os.Build;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.RomUtils;
import com.blankj.utilcode.util.SpanUtils;

import java.util.ArrayList;
import java.util.List;

import accessibility.App;
import accessibility.PermissionAutoManager;
import accessibility.PermissionBean;
import accessibility.Tools;

public class PermissionValues {

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
    public final boolean f20840;

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
    public final List<PermissionBean> f20841 = new ArrayList();

    /* renamed from: 灞酞輀攼嵞漁綬迹 */
    public int f20839 = 0;

    /* renamed from: 肌緭 */
    public final int f12844 = Build.VERSION.SDK_INT;

    /* renamed from: 刻槒唱镧詴 */
    public final String f20838 = AppUtils.getAppName();

    public PermissionValues(boolean z) {
        this.f20840 = z;
    }

    /* renamed from: 偣炱嘵蟴峗舟轛 */
    public final List<PermissionBean> m22433() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(m22438("设备管理器", "允许 【" + this.f20838 + "】 管理此设备，激活设备管理员后，孩子无法卸载 【" + this.f20838 + "】。", "激活【设备管理器】", "找到设备管理器,点击【激活】按钮完成激活", 1));
        StringBuilder sb = new StringBuilder();
        sb.append("允许 【");
        sb.append(this.f20838);
        sb.append("】 显示在其他应用程序之上");
        arrayList.add(m22438("悬浮窗", sb.toString(), "激活【悬浮窗】", "①滑动,找到" + this.f20838 + "并点击\n②点击【开关】按钮开启(请确保开关是开启状态)", 2));
        arrayList.add(m22438("位置权限", "让家长能实时获取孩子位置", "", "", 30));
        if (this.f20840) {
            if (this.f12844 > 23) {
                arrayList.add(m22438("忽略电池优化", "增强稳定性", "忽略电池优化", "为了保持孩子的设备始终连接，请为" + this.f20838 + "启用'后台运行'", 4));
            }
            if (!RomUtils.isGoogle() && !RomUtils.isLg()) {
                arrayList.add(m14269("锁定多任务栏", "防止按多任务被清理,请务必正确设置", "锁定多任务栏", "请下滑或点击小锁图标锁定app", 21, true));
            }
        }
        arrayList.add(m22438("通知使用权", "要在您孩子的设备上捕获通知，请为" + this.f20838 + "启用“通知访问”。", "激活【通知使用权】", "①点击" + this.f20838 + "右边滑块\n②点击【开关】按钮开启(请确保开关是开启状态)", 19));
        arrayList.add(m22438("App使用情况访问", "让家长知道孩子的应用使用记录", "激活【APP情况访问】权限", "①点击" + this.f20838 + "右边滑块\n②点击【开关】按钮开启(请确保开关是开启状态)", 8));
        m14271(arrayList, "other");
        return arrayList;
    }

    /* renamed from: 刻槒唱镧詴 */
    public PermissionBean m22434() {
        if (RomUtils.isVivo()) {
            return m22438("无障碍", "激活后,将有助于" + this.f20838 + "更稳定的管理孩子端设备", "激活【辅助功能】", "滑动到最下方,点击【已下载的服务】列表中的【" + this.f20838 + "】,点击【开关】按钮开启(请确保开关是开启状态)", 12);
        }
        if (!RomUtils.isHuawei() && !Tools.m22457()) {
            if (RomUtils.isXiaomi()) {
                return m22438("无障碍", "激活后,将有助于" + this.f20838 + "更稳定的管理孩子端设备", "激活【辅助功能】", "点击【已下载的应用】列表中的【" + this.f20838 + "】,点击【开关】按钮开启(请确保开关是开启状态)", 12);
            }
            if (!RomUtils.isOppo() && !Tools.m22497()) {
                if (RomUtils.isMeizu()) {
                    return m22438("无障碍", "激活后,将有助于" + this.f20838 + "更稳定的管理孩子端设备", "激活【辅助功能】", "①点击【无障碍】\n②点击【" + this.f20838 + "】\n③点击【允许】", 12);
                }
                return m22438("无障碍", "激活后,将有助于" + this.f20838 + "更稳定的管理孩子端设备", "激活【辅助功能】", "点击【已下载的应用】列表中的【" + this.f20838 + "】,点击【开关】按钮开启(请确保开关是开启状态)", 12);
            }
            if (this.f12844 >= 31) {
                return m22438("无障碍", "激活后,将有助于" + this.f20838 + "更稳定的管理孩子端设备", "激活【辅助功能】", "点击【更多】列表中的【" + this.f20838 + "】,点击【开关】按钮开启(请确保开关是开启状态)", 12);
            }
            return m22438("无障碍", "激活后,将有助于" + this.f20838 + "更稳定的管理孩子端设备", "激活【辅助功能】", "滑动到最下方,点击【已下载的应用】列表中的【" + this.f20838 + "】,点击【开关】按钮开启(请确保开关是开启状态)", 12);
        }
        return m22438("无障碍", "激活后,将有助于" + this.f20838 + "更稳定的管理孩子端设备", "激活【辅助功能】", "①滑动到最下方,在【下载服务】或【已安装的服务】中点击" + this.f20838 + "\n②点击【滑块】\n③点击【确定】", 12);
    }

    /* renamed from: 垡玖 */
    public final PermissionBean m14269(String str, String str2, String str3, String str4, int i, boolean z) {
        return new PermissionBean(str, str2, str3, str4, i, z, i != 4 && i != 21 && i != 29 && i != 41 && i != 17 && i != 18);
    }

    /* renamed from: 旞莍癡 */
    public final List<PermissionBean> m22435() {
        ArrayList arrayList = new ArrayList();
        int i = Build.VERSION.SDK_INT;
        Context context = App.mContext;

//        if (!Tools.m22483() || (!Mgr.getInstance().mo23037() && !Mgr.getInstance().mo22203())) {
//            if (i >= 26) {
//                if (Tools.m22508()) {
//                    arrayList.add(m14269("自动管理", "防止app被结束", "关闭【自动管理】功能", "①点击【应用启动管理】\n②点击【" + this.f20838 + "】右边滑块切换为手动管理\n③点击【允许自启动】（确保弹窗中的开关处于开启状态）\n④点击【允许关联启动】\n⑤点击【允许后台活动】\n⑥点击【确定】", 3, true));
//                } else {
//                    arrayList.add(m14269("自动管理", "防止app被结束", "关闭【自动管理】功能", "①点击【" + this.f20838 + "】右边滑块切换为手动管理\n②点击【允许自启动】（确保弹窗中的开关处于开启状态）\n③点击【允许关联启动】\n④点击【允许后台活动】\n⑤点击【确定】", 3, true));
//                }
//            } else {
//                arrayList.add(m14269("自动启动", "防止app被结束", "开启【自动启动】功能", "①点击【" + this.f20838 + "】右边滑块切换为开启状态\n②点击【允许】", 3, true));
//            }
//        }
        if (i >= 23) {
            arrayList.add(m22438("悬浮窗", "允许 【" + this.f20838 + "】 显示在其他应用程序之上", "激活【悬浮窗】", "①滑动,找到" + this.f20838 + "并点击\n②点击【开关】按钮开启(请确保开关是开启状态)", 2));
        }
        if (Tools.m22483() && Tools.m22498() >= 200) {
            arrayList.add(m22438("后台弹窗", "允许 【" + this.f20838 + "】 在后台弹出窗口", "开启【后台弹窗】", "①找到权限\n②找到后台弹窗权限\n③设置为允许", 34));
        }
//        if (Tools.m22452(context)) {
//            arrayList.add(m14269("关闭智慧多窗", "关闭后,将有助于" + this.f20838 + "更稳定的管理孩子端设备", "关闭智慧多窗", "", 28, true));
//        }
        arrayList.add(m22438("设备管理器", "允许 【" + this.f20838 + "】 管理此设备，激活设备管理员后，孩子无法卸载 【" + this.f20838 + "】。", "激活【设备管理器】", "找到设备管理器,点击【激活】按钮完成激活", 1));
        arrayList.add(m22438("位置权限", "让家长能实时获取孩子位置", "", "", 30));
        if (this.f20840) {
            arrayList.add(m14269("锁定多任务栏", "防止按多任务被清理,请务必正确设置", "锁定多任务栏", "请下滑或点击小锁图标锁定app", 21, true));
        }
        if (this.f20840 && this.f12844 > 23) {
            arrayList.add(m22438("忽略电池优化", "增强稳定性", "忽略电池优化", "为了保持孩子的设备始终连接，请为" + this.f20838 + "启用'后台运行'", 4));
        }
        int i2 = this.f12844;
        if (i2 >= 26 && i2 != 30 && Tools.m22483()) {
            arrayList.add(m14269("允许安装未知来源", "允许孩子端更新", "", "", 40, true));
        }
//        if (i <= 25 && Tools.m22471(context, "com.huawei.systemmanager")) {
//            arrayList.add(m14269("锁屏清理", "防止app被结束", "关闭【锁屏清理】", "①点击【" + this.f20838 + "】右边滑块切换为不清理", 20, true));
//        }
        if (this.f20840) {
            if (Tools.m22483() && Tools.m22498() >= 300) {
                arrayList.add(m14269("高耗电提醒", "防止app被结束", "关闭【高耗电提醒】", "①点击【更多电池设置】\n②点击【高耗电提醒】右边滑块切换为关闭状态", 25, true));
            } else if (!Tools.m22508()) {
                arrayList.add(m14269("异常耗电清理", "防止app被结束", "关闭【异常耗电清理】", "①点击【异常耗电清理】右边滑块切换为关闭状态", 25, true));
            }
        }
        arrayList.add(m22438("通知使用权", "要在您孩子的设备上捕获通知，请为" + this.f20838 + "启用“通知访问”。", "激活【通知使用权】", "①点击" + this.f20838 + "右边滑块\n②点击【开关】按钮开启(请确保开关是开启状态)", 19));
        arrayList.add(m22438("App使用情况访问", "让家长知道孩子的应用使用记录", "激活【APP情况访问】权限", "①点击" + this.f20838 + "右边滑块\n②点击【开关】按钮开启(请确保开关是开启状态)", 8));
        m14271(arrayList, "huawei");
        return arrayList;
    }

    /* renamed from: 朽劔蚁灋嵿齩鶴琓麃沼瀙缹 */
    public List<PermissionBean> m22436() {
        int i;
        PermissionBean m14269;
        ArrayList arrayList = new ArrayList();
        if (RomUtils.isVivo() && !PermissionAutoManager.checkIndex(App.mContext, 12) && (i = this.f12844) >= 23) {
            if (i == 23) {
                m14269 = m14269("后台高耗电", "为了保持孩子的设备始终连接，请为" + this.f20838 + "开启此功能", "关闭后台高耗电", "①点击【" + this.f20838 + "】右边滑块为开启状态", 14, true);
            } else {
                m14269 = m14269("允许后台高耗电", "为了保持孩子的设备始终连接，请为" + this.f20838 + "开启此功能", "开启允许后台高耗电开关", "①点击【后台耗电管理】或【后台高耗电】\n②点击【" + this.f20838 + "】\n④选择【允许后台高耗电】", 14, true);
            }
            arrayList.add(m14269);
        }
        return arrayList;
    }

    /* renamed from: 櫓昛刓叡賜 */
    public List<PermissionBean> m22437() {
        if (CommonUtils.m21239()) {
            return m22442();
        }
        if (RomUtils.isVivo()) {
            return m22440();
        }
        if (!RomUtils.isHuawei() && !Tools.m22457()) {
            if (!RomUtils.isXiaomi() && !Tools.m22507()) {
                if (!RomUtils.isOppo() && !Tools.m22497() && !RomUtils.isOneplus()) {
                    if (RomUtils.isMeizu()) {
                        return m14272();
                    }
                    if (RomUtils.isSamsung()) {
                        return m22444();
                    }
                    return m22433();
                }
                return m22445();
            }
            return m22439();
        }
        return m22435();
    }

    /* renamed from: 灞酞輀攼嵞漁綬迹 */
    public final PermissionBean m22438(String str, String str2, String str3, String str4, int i) {
        return m14269(str, str2, str3, str4, i, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0143, code lost:

        r1.add(m14269("小爱同学", "将无法通过语音助手关闭app", "关闭小爱助手同学语音", "①点击【语音唤醒】切换滑块为关闭状态\n②点击【按键唤醒】，选择【无】", 29, true));
     */
    /* renamed from: 睳堋弗粥辊惶 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final List<PermissionBean> m22439() {
        /*
            Method dump skipped, instructions count: 645
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: p256.PermissionValues.m22439():java.util.List");
    }

    /* renamed from: 瞙餃莴埲 */
    public final List<PermissionBean> m22440() {
        ArrayList arrayList = new ArrayList();
        if (this.f20840) {
            int i = this.f12844;
            if (i >= 23) {
                if (i > 24) {
                    if (Build.MODEL.contains("V1945A")) {
                        arrayList.add(m14269("自启动", "为了保持孩子的设备始终连接，请为" + this.f20838 + "启用'自启动'", "允许自启动", "①点击上方权限tab\n②点击【自启动】\n③找到" + this.f20838 + ",点击右边滑块为开启状态", 33, true));
                        arrayList.add(m14269("后台弹出界面权限", "为了保持孩子的设备始终连接，请为" + this.f20838 + "启用'后台弹出界面'", "后台弹出权限", "①点击【后台弹出界面】右边滑块为开启状态", 3, true));
                    } else {
                        arrayList.add(m14269("自启动,后台弹出权限和关联启动", "为了保持孩子的设备始终连接，请为" + this.f20838 + "启用'后台运行'", "允许自启动和后台弹出权限", "①点击【自启动】右边滑块为开启状态\n②点击【关联启动】右边滑块为开启状态\n③点击【后台弹出】右边滑块为开启状态", 3, true));
                    }
                } else if (!Tools.m22462(App.mContext)) {
                    arrayList.add(m14269("自启动", "为了保持孩子的设备始终连接，请为" + this.f20838 + "启用'自启动'", "允许自启动", "①点击【" + this.f20838 + "】右边滑块为开启状态", 3, true));
                }
            }
            if (this.f12844 <= 23) {
                arrayList.add(m14269("后台高耗电", "为了保持孩子的设备始终连接，请为" + this.f20838 + "关闭此功能", "关闭后台高耗电", "①点击【" + this.f20838 + "】右边滑块为开启状态", 14, true));
            } else {
                arrayList.add(m14269("睡眠模式和后台高耗电开关", "为了保持孩子的设备始终连接，请为" + this.f20838 + "关闭此功能", "关闭睡眠模式和后台高耗电开关", "①点击【睡眠模式】滑块关闭（如果没有此功能可以忽略）\n②点击【后台高耗电】或【后台耗电管理】\n③点击【" + this.f20838 + "】\n④选择【允许后台高耗电】", 14, true));
            }
        }
        if (this.f12844 >= 23) {
            arrayList.add(m22438("悬浮窗", "允许 【" + this.f20838 + "】 显示在其他应用程序之上", "激活【悬浮窗】", "①滑动,找到" + this.f20838 + "并点击\n②点击【开关】按钮开启(请确保开关是开启状态)", 2));
        }
        arrayList.add(m22438("通知使用权", "要在您孩子的设备上捕获通知，请为" + this.f20838 + "启用“通知访问”。", "激活【通知使用权】", "①点击" + this.f20838 + "右边滑块\n②点击【开关】按钮开启(请确保开关是开启状态)", 19));
        arrayList.add(m22438("设备管理器", "允许 【" + this.f20838 + "】 管理此设备，激活设备管理员后，孩子无法卸载 【" + this.f20838 + "】。", "激活【设备管理器】", "找到设备管理器,点击【激活】按钮完成激活", 1));
        StringBuilder sb = new StringBuilder();
        sb.append("①点击");
        sb.append(this.f20838);
        sb.append("右边滑块\n②点击【开关】按钮开启(请确保开关是开启状态)");
        arrayList.add(m22438("App使用情况访问", "让家长知道孩子的应用使用记录", "激活【APP情况访问】权限", sb.toString(), 8));
        if (this.f20840 && this.f12844 > 23) {
            arrayList.add(m22438("忽略电池优化", "增强稳定性", "忽略电池优化", "为了保持孩子的设备始终连接，请为" + this.f20838 + "启用'后台运行'", 4));
        }
        int i2 = this.f12844;
        if (i2 >= 26 && i2 != 30) {
            arrayList.add(m14269("允许安装未知来源", "允许孩子端更新", "", "", 40, true));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            arrayList.add(m14269("锁定多任务栏", "防止按多任务被清理,请务必正确设置", "锁定多任务栏", "请下滑或点击小锁图标锁定app", 21, true));
        }
        arrayList.add(m22438("位置权限", "让家长能实时获取孩子位置", "", "", 30));
        m14271(arrayList, "vivo");
        return arrayList;
    }

    /* renamed from: 祴嚚橺谋肬鬧舘 */
    public List<PermissionBean> m22441() {
        return this.f20841;
    }

    /* renamed from: 綩私 */
    public PermissionBean m14270() {
        return null;
    }

    /* renamed from: 耣怳匮色紝参凵蛴纆勚躄 */
    public final List<PermissionBean> m22442() {
        ArrayList arrayList = new ArrayList();
        if (Build.VERSION.SDK_INT >= 23) {
            arrayList.add(m22438("悬浮窗", "允许 【" + this.f20838 + "】 显示在其他应用程序之上", "激活【悬浮窗】", "①滑动,找到" + this.f20838 + "并点击\n②点击【开关】按钮开启(请确保开关是开启状态)", 2));
        }
        arrayList.add(m22438("位置权限", "让家长能实时获取孩子位置", "", "", 30));
        arrayList.add(m22438("通知使用权", "要在您孩子的设备上捕获通知，请为" + this.f20838 + "启用“通知访问”。", "激活【通知使用权】", "①点击" + this.f20838 + "右边滑块\n②点击【开关】按钮开启(请确保开关是开启状态)", 19));
        arrayList.add(m22438("App使用情况访问", "让家长知道孩子的应用使用记录", "激活【APP情况访问】权限", "①点击" + this.f20838 + "右边滑块\n②点击【开关】按钮开启(请确保开关是开启状态)", 8));
        return arrayList;
    }

    /* renamed from: 肌緭 */
    public final void m14271(List<PermissionBean> list, String str) {
        if (ExamineUtils.m24113() && !"huawei".equals(str)) {
            list.add(m22438("截屏", "允许父母在孩子的设备上截取屏幕截图。", "屏幕截图", "①勾选【不再提示】选择框（没有请忽略）\n②点击【立即开始】或者【允许】", 23));
        }
        if (Tools.isDeveloperOpen()) {
            list.add(m22438("关闭USB调试", "防止孩子端通过电脑连接卸载" + this.f20838, "关闭USB调试", "①进入开发者选项下滑找到下方USB调试选项\n②点击右边滑块按钮关闭(请确保开关是关闭状态)", 18));
        }
    }

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
    public int m22443() {
        return this.f20839;
    }

    /* renamed from: 蝸餺閃喍 */
    public final List<PermissionBean> m22444() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(m22438("设备管理器", "允许 【" + this.f20838 + "】 管理此设备，激活设备管理员后，孩子无法卸载 【" + this.f20838 + "】。", "激活【设备管理器】", "找到设备管理器,点击【激活】按钮完成激活", 1));
        StringBuilder sb = new StringBuilder();
        sb.append("允许 【");
        sb.append(this.f20838);
        sb.append("】 显示在其他应用程序之上");
        arrayList.add(m22438("悬浮窗", sb.toString(), "激活【悬浮窗】", "①滑动,找到" + this.f20838 + "并点击\n②点击【开关】按钮开启(请确保开关是开启状态)", 2));
        arrayList.add(m22438("位置权限", "让家长能实时获取孩子位置", "", "", 30));
        if (this.f20840 && this.f12844 > 23) {
            arrayList.add(m22438("忽略电池优化", "增强稳定性", "忽略电池优化", "为了保持孩子的设备始终连接，请为" + this.f20838 + "启用'后台运行'", 4));
        }
        arrayList.add(m22438("通知使用权", "要在您孩子的设备上捕获通知，请为" + this.f20838 + "启用“通知访问”。", "激活【通知使用权】", "①点击" + this.f20838 + "右边滑块\n②点击【开关】按钮开启(请确保开关是开启状态)", 19));
        arrayList.add(m22438("App使用情况访问", "让家长知道孩子的应用使用记录", "激活【APP情况访问】权限", "①点击" + this.f20838 + "右边滑块\n②点击【开关】按钮开启(请确保开关是开启状态)", 8));
        m14271(arrayList, "samsung");
        return arrayList;
    }

    /* renamed from: 酸恚辰橔纋黺 */
    public final List<PermissionBean> m22445() {
        ArrayList arrayList = new ArrayList();
        int i = Build.VERSION.SDK_INT;
        arrayList.add(m22438("设备管理器", "允许 【" + this.f20838 + "】 管理此设备，激活设备管理员后，孩子无法卸载 【" + this.f20838 + "】。", "激活【设备管理器】", "找到设备管理器,点击【激活】按钮完成激活", 1));
        if (i >= 23) {
            arrayList.add(m22438("悬浮窗", "允许 【" + this.f20838 + "】 显示在其他应用程序之上", "激活【悬浮窗】", "①滑动,找到" + this.f20838 + "并点击\n②点击【开关】按钮开启(请确保开关是开启状态)", 2));
            arrayList.add(m22438("忽略电池优化", "增强稳定性", "忽略电池优化", "为了保持孩子的设备始终连接，请为" + this.f20838 + "启用'后台运行'", 4));
        }
        if (this.f20840) {
            if (Tools.m22497()) {
                if (i <= 29) {
                    arrayList.add(m14269("省电优化", "防止app被结束", "耗电管理", "①点击【耗电管理】或者【耗电保护】\n②点击【允许后台完全行为】或者【允许后台运行】\n③返回电池首页，点击【智能省电场景】\n④滑动【睡眠待机优化】右边滑块为关闭状态", 24, true));
                } else {
                    arrayList.add(m14269("省电优化", "防止app被结束", "耗电管理", "①点击【耗电管理】\n②点击【允许后台完全行为】\n③返回电池首页，点击【更多】\n④点击【耗电异常优化】\n⑤点击【" + this.f20838 + "】,选择不优化", 24, true));
                }
            } else if (i < 24) {
                arrayList.add(m14269("省电优化", "防止app被结束", "耗电管理", "①点击【耗电保护】或者【其它】\n②点击【" + this.f20838 + "】\n④点击【后台速冻】右边滑块，确保处于关闭状态\n③点击【检测到异常时自动优化】右边滑块，确保处于关闭状态", 24, true));
            } else if (i < 26) {
                arrayList.add(m14269("省电优化", "防止app被结束", "耗电管理", "①点击【智能耗电保护】开关，确保处于关闭状态\n②点击【自定义耗电保护】\n③点击【" + this.f20838 + "】\n④点击【允许后台运行】\n⑤返回【电池】页面\n⑥点击【应用速冻】\n⑦点击【" + this.f20838 + "】 右边滑块，确保处于关闭状态", 24, true));
            } else if (i < 29) {
                arrayList.add(m14269("省电优化", "防止app被结束", "耗电管理", "①点击【耗电保护】\n②点击【允许后台运行】", 24, true));
            } else if (i == 29) {
                arrayList.add(m14269("省电优化", "防止app被结束", "耗电管理", "①点击【耗电管理】或者【耗电保护】\n②点击【允许后台完全行为】或者【允许后台运行】", 24, true));
            } else {
                arrayList.add(m14269("省电优化", "防止app被结束", "耗电管理", "①点击【耗电管理】\n②点击【允许后台完全行为】", 24, true));
            }
            if (i >= 24) {
                arrayList.add(m14269("自启动和关联启动", "防止app被结束", "允许后台完全运行权限", "①点击【耗电保护】\n②点击【允许应用自启动】\n⑥点击【允许应用关联启动】", 3, true));
            } else {
                arrayList.add(m14269("自启动", "防止app被结束", "开启自启动", "①点击【权限隐私】\n②点击【自启动管理】\n③点击" + this.f20838 + "右边【开关】（确保开关处于开启状态)", 3, true));
            }
        }
        if (this.f20840) {
            arrayList.add(m14269("锁定多任务栏", "防止按多任务被清理,请务必正确设置", "锁定多任务栏", "请下滑或点击小锁图标锁定app", 21, true));
        }
        if (i >= 26 && i != 30) {
            arrayList.add(m14269("允许安装未知来源", "允许孩子端更新", "", "", 40, true));
        }
        arrayList.add(m22438("位置权限", "让家长能实时获取孩子位置", "", "", 30));
        arrayList.add(m22438("通知使用权", "要在您孩子的设备上捕获通知，请为" + this.f20838 + "启用“通知访问”。", "激活【通知使用权】", "①点击" + this.f20838 + "右边滑块\n②点击【开关】按钮开启(请确保开关是开启状态)", 19));
        arrayList.add(m22438("App使用情况访问", "让家长知道孩子的应用使用记录", "激活【APP情况访问】权限", "①点击" + this.f20838 + "右边滑块\n②点击【开关】按钮开启(请确保开关是开启状态)", 8));
        m14271(arrayList, "oppo");
        return arrayList;
    }

    /* renamed from: 镐藻 */
    public final List<PermissionBean> m14272() {
        ArrayList arrayList = new ArrayList();
        int i = Build.VERSION.SDK_INT;
        arrayList.add(m22438("设备管理器", "允许 【" + this.f20838 + "】 管理此设备，激活设备管理员后，孩子无法卸载 【" + this.f20838 + "】。", "激活【设备管理器】", "找到设备管理器,点击【激活】按钮完成激活", 1));
        if (this.f20840) {
            arrayList.add(m14269("后台运行权限", "允许在后台运行", "开启后台运行权限", "①点击【权限管理】\n②点击【后台管理】\n③点击【" + this.f20838 + "】\n④选择【运行后台运行】", 22, true));
        }
        if (i >= 23) {
            arrayList.add(m22438("悬浮窗", "允许 【" + this.f20838 + "】 显示在其他应用程序之上", "激活【悬浮窗】", "①滑动,找到" + this.f20838 + "并点击\n②点击【开关】按钮开启(请确保开关是开启状态)", 2));
            arrayList.add(m22438("忽略电池优化", "增强稳定性", "忽略电池优化", "为了保持孩子的设备始终连接，请为" + this.f20838 + "启用'后台运行'", 4));
        }
        arrayList.add(m22438("位置权限", "让家长能实时获取孩子位置", "", "", 30));
        arrayList.add(m22438("通知使用权", "要在您孩子的设备上捕获通知，请为" + this.f20838 + "启用“通知访问”。", "激活【通知使用权】", "①点击" + this.f20838 + "右边滑块\n②点击【开关】按钮开启(请确保开关是开启状态)", 19));
        arrayList.add(m22438("App使用情况访问", "让家长知道孩子的应用使用记录", "激活【APP情况访问】权限", "①点击" + this.f20838 + "右边滑块\n②点击【开关】按钮开启(请确保开关是开启状态)", 8));
        m14271(arrayList, "meizu");
        return arrayList;
    }

    /* renamed from: 陟瓠魒踱褢植螉嚜 */
    public void m22446(TextView textView) {
        if (RomUtils.isVivo()) {
            SpanUtils.with(textView).append("①滑动到最下方,在【下载服务】或【已安装的服务】中找到" + this.f20838 + "\n\n").appendImage(ModelManager.m22787("vivo_access")).append("\n\n②点击并开启开关").create();
            return;
        }
        if (!RomUtils.isHuawei() && !Tools.m22457()) {
            if (RomUtils.isXiaomi()) {
                SpanUtils.with(textView).append("①滑动找到【更多已下载服务】模块\n\n").appendImage(ModelManager.m22787("XIAOMI_ACCESS_1")).append("\n\n②找到" + this.f20838 + "并开启开关\n\n").appendImage(ModelManager.m22787("XIAOMI_ACCESS_2")).create();
                return;
            }
            if (!RomUtils.isOppo() && !Tools.m22497() && !RomUtils.isOneplus()) {
                SpanUtils.with(textView).append("①滑动到最下方,找到【已下载服务】模块\n").append("②找到" + this.f20838 + "并开启开关").create();
                return;
            }
            if (this.f12844 >= 31) {
                SpanUtils.with(textView).append("①滑动找到【更多】模块\n\n").appendImage(ModelManager.m22787("OPPO_ACCESS_HIGH")).append("\n\n②找到" + this.f20838 + "并开启开关").create();
                return;
            }
            SpanUtils.with(textView).append("①滑动到最下方,找到【已下载服务】模块\n").append("②找到" + this.f20838 + "并开启开关\n\n").appendImage(ModelManager.m22787("OPPO_ACCESS_LOW")).create();
            return;
        }
        SpanUtils.with(textView).append("①滑动到最下方,找到【下载服务】或【已安装的服务】模块\n\n").appendImage(ModelManager.m22787("HUAWEI_ACCESS_1")).append("\n\n②找到" + this.f20838 + "并开启开关\n\n").appendImage(ModelManager.m22787("HUAWEI_ACCESS_2")).create();
    }

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
    public PermissionNextState m22447() {
        ArrayList<PermissionBean> arrayList = new ArrayList();
        for (PermissionBean permissionBean : m22437()) {
            if (PermissionAutoManager.m14283(permissionBean.getId())) {
                arrayList.add(permissionBean);
            } else if (permissionBean.getId() != 23) {
                this.f20841.add(permissionBean);
            }
        }
        this.f20839 = arrayList.size();
        PermissionNextState permissionNextState = new PermissionNextState();
        PermissionNextState permissionNextState2 = null;
        for (PermissionBean permissionBean2 : arrayList) {
            PermissionNextState permissionNextState3 = new PermissionNextState();
            permissionNextState3.setCurr(permissionBean2);
            if (permissionNextState2 == null) {
                permissionNextState.setNextState(permissionNextState3);
            } else {
                permissionNextState2.setNextState(permissionNextState3);
            }
            permissionNextState2 = permissionNextState3;
        }
        return permissionNextState;
    }
}
