package com.motorola.taskbar.recent;

import android.view.View;
import com.android.systemui.shared.recents.model.Task;

/* JADX INFO: compiled from: RecentsActivity.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface TaskViewHostCallback {
    void closeTask(View view, Task task);

    int getDisplayMode();

    void hideRecents(View view);

    void launchTask(View view, Task task, boolean z);
}
