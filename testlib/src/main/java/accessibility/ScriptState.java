package accessibility;

import androidx.annotation.NonNull;

public class ScriptState {

    /* renamed from: 刻槒唱镧詴, reason: contains not printable characters */
    @NonNull
    public final String op;

    /* renamed from: 肌緭 */
    public ScriptState state;

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭, reason: contains not printable characters */
    @NonNull
    public final String value;

    public ScriptState(@NonNull String op, @NonNull String value) {
        this.op = op;
        this.value = value;
    }

    @NonNull
    public final String getOp() {
        return this.op;
    }

    /* renamed from: 灞酞輀攼嵞漁綬迹, reason: contains not printable characters */
    public void setScript(ScriptState scriptState) {
        this.state = scriptState;
    }

    /* renamed from: 肌緭 */
    public final ScriptState getScript() {
        return this.state;
    }

    @NonNull
    public final String getValue() {
        return this.value;
    }

    public final void execute(@NonNull ScriptStateManager.IRunner interfaceC7205) {
        interfaceC7205.execute(this);
    }
}
