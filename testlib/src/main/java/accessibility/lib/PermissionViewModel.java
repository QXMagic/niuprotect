package accessibility.lib;

import android.text.TextUtils;

import com.blankj.utilcode.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

import accessibility.PermissionBean;

public class PermissionViewModel  extends BaseViewModel {

    /* renamed from: 偣炱嘵蟴峗舟轛 */
    public PermissionNextState f19321;

    /* renamed from: 旞莍癡 */
    public boolean f19322;

    /* renamed from: 櫓昛刓叡賜 */
    public List<PermissionBean> f19324;

    /* renamed from: 睳堋弗粥辊惶 */
    public String f19326;

    /* renamed from: 祴嚚橺谋肬鬧舘 */
    public PermissionBean f19328;

    /* renamed from: 蝸餺閃喍 */
    public PermissionValues f19330;

    /* renamed from: 镐藻 */
    public int f11974;

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
    public boolean f19332 = false;

    /* renamed from: 灞酞輀攼嵞漁綬迹 */
    public int f19325 = 0;

    /* renamed from: 垡玖 */
    public List<PermissionBean> f11972 = new ArrayList<>();

    /* renamed from: 酸恚辰橔纋黺 */
    public boolean f19331 = false;

    /* renamed from: 綩私 */
    public boolean f11973 = false;

    /* renamed from: 朽劔蚁灋嵿齩鶴琓麃沼瀙缹 */
    public int f19323 = 0;

    /* renamed from: 瞙餃莴埲 */
    public boolean f19327 = false;

    /* renamed from: 耣怳匮色紝参凵蛴纆勚躄 */
    public boolean f19329 = false;

    /* renamed from: 偣炱嘵蟴峗舟轛 */
    public boolean m20081(String str) {
        return ObjectUtils.equals(this.f19326, str);
    }

    /* renamed from: 朽劔蚁灋嵿齩鶴琓麃沼瀙缹 */
    public void m20082() {
        m20085(true);
    }

    /* renamed from: 櫓昛刓叡賜 */
    public void m20083() {
        this.f19323 = 1;
    }

    /* renamed from: 睳堋弗粥辊惶 */
    public int m20084() {
        return this.f19323;
    }

    /* renamed from: 瞙餃莴埲 */
    public void m20085(boolean z) {
        if (this.f11974 < this.f11972.size()) {
            this.f11972.get(this.f11974).setOpen(z);
        }
    }

    /* renamed from: 綩私 */
    public void m13541(String str) {
        if (TextUtils.isEmpty(this.f19326)) {
            return;
        }
        this.f19326 = str;
    }

    /* renamed from: 蝸餺閃喍 */
    public void m20086(boolean z) {
        this.f19332 = z;
    }

    /* renamed from: 酸恚辰橔纋黺 */
    public boolean m20087() {
        return this.f19332;
    }

    /* renamed from: 镐藻 */
    public void m13542() {
        this.f19323++;
    }
}


