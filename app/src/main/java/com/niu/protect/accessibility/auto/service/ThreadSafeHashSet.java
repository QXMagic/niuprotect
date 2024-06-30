package com.niu.protect.accessibility.auto.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* JADX WARN: Classes with same name are omitted:
  classes5.dex
 */
/* compiled from: ThreadSafeHashSet.java */
/* renamed from: 灱嚥嚴.肌緭, reason: use source file name */
/* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
public class ThreadSafeHashSet<T> {

    /* renamed from: 肌緭 */
    public final Set<T> f13378 = Collections.synchronizedSet(new HashSet());

    /* renamed from: 刻槒唱镧詴, reason: contains not printable characters */
    public void m23899() {
        this.f13378.clear();
    }

    /* renamed from: 垡玖 */
    public void m14792(T t) {
        this.f13378.remove(t);
    }

    /* renamed from: 灞酞輀攼嵞漁綬迹, reason: contains not printable characters */
    public boolean m23900() {
        return this.f13378.isEmpty();
    }

    /* renamed from: 肌緭 */
    public void m14793(String str) {
    }
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Method arg registers not loaded: ﾞﾼﾇﾲﾇ￀.ﾼﾡﾾﾓ.ﾼﾡﾾﾓ(T):void, class status: GENERATED_AND_UNLOADED
        	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:289)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:155)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:401)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
        */

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
    public boolean m23901(String str) {
        return false;
    }
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Method arg registers not loaded: ﾞﾼﾇﾲﾇ￀.ﾼﾡﾾﾓ.￈ﾁ￉￪ﾜﾑ￷ﾾￓﾳ￦Bﾶfﾑm￦f￁|(T):boolean, class status: GENERATED_AND_UNLOADED
        	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:289)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:155)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:401)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
        */

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
    public Set<T> m23902() {
        return null;
    }
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Method arg registers not loaded: ﾞﾼﾇﾲﾇ￀.ﾼﾡﾾﾓ.￬ﾑ￹Wￖ￷ﾞﾂﾺﾢﾝ￼ﾯi￩iￚﾥￚ￯ﾙmﾔﾝ():java.util.Set<T>, class status: GENERATED_AND_UNLOADED
        	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:289)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:155)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:401)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
        */
}
