package accessibility;

public class ProgressBusManager {

    /* renamed from: 肌緭 */
    public static ProgressBus<Boolean> f12467 = new ProgressBus<>("voice_bus");

    /* renamed from: 刻槒唱镧詴 */
    public static ProgressBus<Boolean> f20087 = new ProgressBus<>("study_voice_bus");

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
    public static ProgressBus<Boolean> f20088 = new ProgressBus<>("notification_cancel_bus");

    /* renamed from: 刻槒唱镧詴 */
    public static void m21159() {
        f12467.m21155();
        f20087.m21155();
        f20088.m21155();
    }

    /* renamed from: 肌緭 */
    public static void m13824() {
        f12467.m13822();
        f20087.m13822();
        f20088.m13822();
    }
}
