package com.android.systemui.statusbar.notification.collection.render;

import java.util.List;

/* JADX INFO: compiled from: NodeController.kt */
/* JADX INFO: loaded from: classes.dex */
public interface NodeSpec {
    List getChildren();

    NodeController getController();

    NodeSpec getParent();
}
