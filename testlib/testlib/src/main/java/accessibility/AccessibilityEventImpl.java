package accessibility;

import android.os.Parcelable;
import android.view.accessibility.AccessibilityNodeInfo;

import com.blankj.utilcode.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AccessibilityEventImpl {

    /* renamed from: 刻槒唱镧詴 */
    public final CharSequence className;

    /* renamed from: 垡玖 */
    public final int eventType;

    public final CharSequence packageName;

    /* renamed from: 灞酞輀攼嵞漁綬迹 */
    public final long f22225;

    /* renamed from: 睳堋弗粥辊惶 */
    public List<CharSequence> f22226;

    /* renamed from: 祴嚚橺谋肬鬧舘 */
    public final Parcelable f22227;

    /* renamed from: 肌緭 */
    public final int f13577;

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
    public final int f22228;

    /* renamed from: 镐藻 */
    public final AccessibilityNodeInfo f13578;

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
    public final CharSequence f22229;

    public AccessibilityEventImpl(CharSequence charSequence, int i, Parcelable parcelable, long j, int i2, CharSequence charSequence2, CharSequence charSequence3, List<CharSequence> list, AccessibilityNodeInfo accessibilityNodeInfo, int i3) {
        this.packageName = charSequence;
        this.eventType = i;
        this.f22227 = parcelable;
        this.f22225 = j;
        this.f13577 = i2;
        this.className = charSequence2;
        this.f22229 = charSequence3;
        this.f22226 = list;
        this.f13578 = accessibilityNodeInfo;
        this.f22228 = i3;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || AccessibilityEventImpl.class != obj.getClass()) {
            return false;
        }
        AccessibilityEventImpl accessibilityEventImpl = (AccessibilityEventImpl) obj;
        return this.f13577 == accessibilityEventImpl.f13577 && this.f22228 == accessibilityEventImpl.f22228 && this.f22225 == accessibilityEventImpl.f22225 && this.eventType == accessibilityEventImpl.eventType && Objects.equals(this.className, accessibilityEventImpl.className) && Objects.equals(this.f22229, accessibilityEventImpl.f22229) && Objects.equals(this.packageName, accessibilityEventImpl.packageName) && Objects.equals(this.f22227, accessibilityEventImpl.f22227) && Objects.equals(this.f13578, accessibilityEventImpl.f13578) && Objects.equals(this.f22226, accessibilityEventImpl.f22226);
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.f13577), this.className, Integer.valueOf(this.f22228), this.f22229, Long.valueOf(this.f22225), Integer.valueOf(this.eventType), this.packageName, this.f22227, this.f13578, this.f22226);
    }

    public String toString() {
        return "AccessibilityEventImpl{action=" + this.f13577 + ", className="
                + ((Object) this.className) + ", contentChangeTypes=" + this.f22228
                + ", contentDescription=" + ((Object) this.f22229)
                + ", eventTime=" + this.f22225
                + ", eventType=" + this.eventType
                + ", packageName=" + ((Object) this.packageName)
                + ", parcelableData=" + this.f22227 + ", source=" + this.f13578 + ", text=" + this.f22226 + '}';
    }

    /* renamed from: 刻槒唱镧詴 */
    public String className() {
        return getClassName().toString();
    }

    /* renamed from: 垡玖 */
    public CharSequence getPackageName() {
        CharSequence charSequence = this.packageName;
        return charSequence == null ? "" : charSequence;
    }

    /* renamed from: 旞莍癡 */
    public String packageName() {
        return getPackageName().toString();
    }

    /* renamed from: 灞酞輀攼嵞漁綬迹 */
    public int getEventType() {
        return this.eventType;
    }

    /* renamed from: 祴嚚橺谋肬鬧舘 */
    public AccessibilityNodeInfo m24460() {
        return this.f13578;
    }

    /* renamed from: 肌緭 */
    public CharSequence getClassName() {
        return ObjectUtils.isEmpty(this.className) ? "" : this.className;
    }

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
    public int m24461() {
        return this.f22228;
    }

    /* renamed from: 镐藻 */
    public List<CharSequence> m15021() {
        if (this.f22226 == null) {
            this.f22226 = new ArrayList();
        }
        return this.f22226;
    }

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
    public CharSequence m24462() {
        return this.f22229;
    }
}
