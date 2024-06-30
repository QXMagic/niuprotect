package com.niu.protect.accessibility.auto.service.tmp;

import org.litepal.crud.LitePalSupport;

public abstract class BaseLitePal extends LitePalSupport {
    public long getLitePalId() {
        return getBaseObjId();
    }

    public int update() {
        return super.update(getBaseObjId());
    }
}
