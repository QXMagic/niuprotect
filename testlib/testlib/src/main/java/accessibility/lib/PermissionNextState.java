package accessibility.lib;

import androidx.annotation.NonNull;

import accessibility.PermissionBean;

public class PermissionNextState {
    private PermissionBean mCurr;
    private PermissionNextState nextState;

    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
    public interface StateCall {
        void call(@NonNull PermissionNextState permissionNextState);
    }

    public PermissionNextState() {
    }

    public PermissionBean getCurr() {
        return this.mCurr;
    }

    public PermissionNextState getNextState() {
        return this.nextState;
    }

    public final void onState(@NonNull StateCall stateCall) {
        stateCall.call(this);
    }

    public void setCurr(PermissionBean permissionBean) {
        this.mCurr = permissionBean;
    }

    public void setNextState(PermissionNextState permissionNextState) {
        this.nextState = permissionNextState;
    }
}
