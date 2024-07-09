package accessibility;

import android.text.TextUtils;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

public class AppArraySet implements IAppFilterType {
    public static final int Forbidden = 0,  Forbidden_Time = 2, WaitForAllow = 3, GROUP_Forbidden = 4,  Forbidden_MULT_TIME = 6;

    public final int type;

    public final CopyOnWriteArraySet<String> _list = new CopyOnWriteArraySet<>();

    public AppArraySet(int i) {
        this.type = i;
    }

    @Override // p304.ListSet
    public void clear() {
        this._list.clear();
    }

    @Override // p304.ListSet
    public void remove(String str) {
        this._list.remove(str);
    }

    public String toString() {
        int i = this.type;
        return (i == Forbidden ? "被禁应用" : i == Forbidden_MULT_TIME ? "多时段限制" : GROUP_Forbidden == i ? "组合限制" : WaitForAllow == i ? "待审核" : Forbidden_Time == i ? "时长限制" : "") + "：" + this._list;
    }

    /* renamed from: 垡玖 */
    public <T extends IPackageName> boolean notContains(List<T> list) {
        if (list == null || size() != list.size()) {
            return true;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (!contains(it.next().getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: 旞莍癡 */
    public int size() {
        return this._list.size();
    }

    /* renamed from: 灞酞輀攼嵞漁綬迹 */
    public boolean contains(String str) {
        if (str == null) {
            return false;
        }
        return this._list.contains(str);
    }

    @Override // p304.IAppFilterType
    /* renamed from: 肌緭 */
    public int getType() {
        return this.type;
    }

    @Override // p304.ListSet
    public void addPackageName(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this._list.add(str);
    }
}
