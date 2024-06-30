package com.niu.protect.accessibility.auto.service;

import androidx.annotation.NonNull;

import com.niu.protect.tools.ILog;

public class ScriptStateManager {


    public static ScriptStateManager f24286;

    public boolean f14844 = false;


    public interface InterfaceC7205 {
        /* renamed from: 肌緭 */
        void mo16191( ScriptState scriptState);
    }


    public class C4479 implements InterfaceC7205 {

        public final /* synthetic */ InterfaceC7206 f14845;

        public C4479(InterfaceC7206 interfaceC7206) {
            this.f14845 = interfaceC7206;
        }

        @Override // p564.ScriptStateManager.InterfaceC7205
        public void mo16191(@NonNull ScriptState scriptState) {
            if (ScriptStateManager.this.f14844) {
                scriptState.m27635(null);
                this.f14845.mo27665(true);
                ScriptStateManager.this.f14844 = false;
            } else {
                if (this.f14845.mo16185(scriptState)) {
                    ScriptState m16177 = scriptState.m16177();
                    if (m16177 == null) {
                        this.f14845.mo27665(true);
                        return;
                    } else {
                        m16177.m27637(this);
                        return;
                    }
                }
                scriptState.m27635(null);
                this.f14845.mo27665(false);
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted:
  classes5.dex
 */
    /* compiled from: ScriptStateManager.java */
    /* renamed from: 髿芫祜邕測.瞙餃莴埲$葋申湋骶映鍮秄憁鎓羭, reason: contains not printable characters */
    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
    public interface InterfaceC7206 {
        /* renamed from: 刻槒唱镧詴 */
        void mo27665(boolean z);

        /* renamed from: 肌緭 */
        boolean mo16185(@NonNull ScriptState scriptState);
    }

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐, reason: contains not printable characters */
    public static ScriptStateManager m27695() {
        if (f24286 == null) {
            f24286 = new ScriptStateManager();
        }
        return f24286;
    }

    /* renamed from: 垡玖 */
    public final ScriptState m16190(String str) {
        String[] split = str.split("->");
        if (split == null) {
            return null;
        }
        String str2 = split[0];
        if (split.length == 2) {
            String str3 = split[1];
            if (str2==null || str2.isEmpty()) {
                return null;
            }
            return new ScriptState(str2, str3);
        }
        if (str2==null || str2.isEmpty()) {
            return null;
        }
        return new ScriptState(str2, "");
    }

    /* renamed from: 旞莍癡, reason: contains not printable characters */
    public void m27696(boolean z) {
        this.f14844 = z;
    }

    public final ScriptState m27697(String str) {
        ScriptState scriptState = new ScriptState("s", "");
        if (str==null || str.isEmpty()) {
            return scriptState;
        }
        ScriptState scriptState2 = null;
        for (String str2 : str.split("\n")) {
            ScriptState m16190 = m16190(str2);
            if (m16190 != null) {
                ILog.d("ScriptState","op:" + m16190.m27634() + "---Value:" + m16190.m27636());
                if (scriptState2 == null) {
                    scriptState.m27635(m16190);
                } else {
                    scriptState2.m27635(m16190);
                }
                scriptState2 = m16190;
            }
        }
        return scriptState;
    }

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭, reason: contains not printable characters */
    public void m27698(@NonNull String str, @NonNull InterfaceC7206 interfaceC7206) {
        ScriptState m16177 = m27697(str).m16177();
        if (m16177 != null) {
            m16177.m27637(new C4479(interfaceC7206));
        } else {
            interfaceC7206.mo27665(false);
        }
    }
}

