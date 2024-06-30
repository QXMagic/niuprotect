package com.niu.protect.accessibility.auto.service;

import com.blankj.utilcode.util.ObjectUtils;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

public class AppArraySet implements InterfaceC3725 {

    /* renamed from: 刻槒唱镧詴 */
    public final int f23359;

    /* renamed from: 肌緭 */
    public final CopyOnWriteArraySet<String> f14235 = new CopyOnWriteArraySet<>();

    public AppArraySet(int i) {
        this.f23359 = i;
    }

    @Override // p304.ListSet
    public void clear() {
        this.f14235.clear();
    }

    @Override // p304.ListSet
    public void remove(String str) {
        this.f14235.remove(str);
    }

    /* renamed from: 刻槒唱镧詴 */
    public String m25900() {
        int i = this.f23359;
        return (i == 0 ? "被禁应用" : i == 6 ? "多时段限制" : i == 4 ? "组合限制" : i == 3 ? "待审核" : i == 2 ? "时长限制" : "") + "：" + this.f14235;
    }

    /* renamed from: 垡玖 */
    public <T extends InterfaceC4191> boolean m15536(List<T> list) {
        if (list == null || m25901() != list.size()) {
            return true;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (!m25902(it.next().getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: 旞莍癡 */
    public int m25901() {
        return this.f14235.size();
    }

    /* renamed from: 灞酞輀攼嵞漁綬迹 */
    public boolean m25902(String str) {
        if (str == null) {
            return false;
        }
        return this.f14235.contains(str);
    }

    @Override // p304.InterfaceC3725
    /* renamed from: 肌緭 */
    public int mo14579() {
        return this.f23359;
    }

    @Override // p304.ListSet
    /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
    public void mo23319(String str) {
        if (ObjectUtils.isEmpty((CharSequence) str)) {
            return;
        }
        this.f14235.add(str);
    }
}
