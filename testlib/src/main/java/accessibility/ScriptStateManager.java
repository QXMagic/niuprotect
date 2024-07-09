package accessibility;

import androidx.annotation.NonNull;

import com.niu.protect.tools.ILog;

public class ScriptStateManager {


    public static ScriptStateManager _instance;

    public boolean f14844 = false;


    public interface IRunner {
        void execute(ScriptState scriptState);
    }


    public class C4479 implements IRunner {

        public final /* synthetic */ IScriptExecutor executor;

        public C4479(IScriptExecutor executor) {
            this.executor = executor;
        }

        @Override
        public void execute(@NonNull ScriptState scriptState) {
            if (ScriptStateManager.this.f14844) {
                scriptState.setScript(null);
                this.executor.setResult(true);
                ScriptStateManager.this.f14844 = false;
            } else {
                if (this.executor.executeScript(scriptState)) {
                    ScriptState state = scriptState.getScript();
                    if (state == null) {
                        this.executor.setResult(true);
                        return;
                    } else {
                        state.execute(this);
                        return;
                    }
                }
                scriptState.setScript(null);
                this.executor.setResult(false);
            }
        }
    }


    public interface IScriptExecutor {
        void setResult(boolean z);

        boolean executeScript(@NonNull ScriptState scriptState);
    }

    public static ScriptStateManager instance() {
        if (_instance == null) {
            _instance = new ScriptStateManager();
        }
        return _instance;
    }

    public final ScriptState decodeCmd(String str) {
        String[] split = str.split("->");
        if (split == null) {
            return null;
        }
        String cmd = split[0];
        if (cmd==null || cmd.isEmpty()) {
            return null;
        }
        if (split.length == 2) {
            String value = split[1];
            return new ScriptState(cmd, value);
        }
        return new ScriptState(cmd, "");
    }

    public void m27696(boolean z) {
        this.f14844 = z;
    }

    public final ScriptState excuteScript(String str) {
        ScriptState scriptState = new ScriptState("s", "");
        if (str==null || str.isEmpty()) {
            return scriptState;
        }
        ScriptState scriptState2 = null;
        for (String str2 : str.split("\n")) {
            ScriptState state = decodeCmd(str2);
            if (state != null) {
                ILog.d("ScriptState","op:" + state.getOp() + "---Value:" + state.getValue());
                if (scriptState2 == null) {
                    scriptState.setScript(state);
                } else {
                    scriptState2.setScript(state);
                }
                scriptState2 = state;
            }
        }
        return scriptState;
    }

    public void runScript(@NonNull String str, @NonNull IScriptExecutor executor) {
        ScriptState state = excuteScript(str).getScript();
        if (state != null) {
            state.execute(new C4479(executor));
        } else {
            executor.setResult(false);
        }
    }
}

