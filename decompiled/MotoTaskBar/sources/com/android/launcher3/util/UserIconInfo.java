package com.android.launcher3.util;

import android.os.UserHandle;

/* JADX INFO: loaded from: classes.dex */
public class UserIconInfo {
    public final int type;
    public final UserHandle user;
    public final long userSerial;

    public UserIconInfo(UserHandle userHandle, int i) {
        this(userHandle, i, 0L);
    }

    public UserIconInfo(UserHandle userHandle, int i, long j) {
        this.user = userHandle;
        this.type = i;
        this.userSerial = j;
    }

    public FlagOp applyBitmapInfoFlags(FlagOp flagOp) {
        return flagOp.setFlag(1, isWork()).setFlag(4, isCloned()).setFlag(8, isPrivate()).setFlag(16, isVault());
    }

    public boolean isCloned() {
        return this.type == 2;
    }

    public boolean isPrivate() {
        return this.type == 3;
    }

    public boolean isVault() {
        return this.type == 4;
    }

    public boolean isWork() {
        return this.type == 1;
    }
}
