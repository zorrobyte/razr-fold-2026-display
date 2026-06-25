package com.android.systemui.util.concurrency;

import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public interface ThreadFactory {
    Executor buildExecutorOnNewThread(String str);
}
