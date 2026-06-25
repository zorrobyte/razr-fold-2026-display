package com.android.systemui.statusbar.notification.row;

import java.util.function.Consumer;

/* JADX INFO: compiled from: NotificationEntryProcessorFactory.kt */
/* JADX INFO: loaded from: classes.dex */
public interface NotificationEntryProcessorFactory {
    Processor create(Consumer consumer);
}
