package com.android.systemui.qs.tiles.controller;

import android.util.Log;
import com.android.systemui.statusbar.policy.CallbackController;
import com.motorola.taskbar.DesktopQSTileData;
import com.motorola.taskbar.TaskBarServiceCallBack;
import com.motorola.taskbar.TaskBarServiceProxy;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: QSTileController.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class QSTileController implements CallbackController {
    public static final Companion Companion = new Companion(null);
    private final Set callbacks;
    private DesktopQSTileData desktopQsTileData;
    private final QSTileController$taskBarServiceCallBack$1 taskBarServiceCallBack;
    private final TaskBarServiceProxy taskBarServiceProxy;
    private final DesktopQSTileData.QSTileType type;

    /* JADX INFO: compiled from: QSTileController.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.qs.tiles.controller.QSTileController$taskBarServiceCallBack$1] */
    public QSTileController(DesktopQSTileData.QSTileType qSTileType, TaskBarServiceProxy taskBarServiceProxy) {
        qSTileType.getClass();
        taskBarServiceProxy.getClass();
        this.type = qSTileType;
        this.taskBarServiceProxy = taskBarServiceProxy;
        this.callbacks = new LinkedHashSet();
        this.taskBarServiceCallBack = new TaskBarServiceCallBack() { // from class: com.android.systemui.qs.tiles.controller.QSTileController$taskBarServiceCallBack$1
            @Override // com.motorola.taskbar.TaskBarServiceCallBack
            public void onQSTileDataUpdated(DesktopQSTileData desktopQSTileData) {
                if (desktopQSTileData != null) {
                    if (desktopQSTileData.type != this.this$0.getType().ordinal()) {
                        desktopQSTileData = null;
                    }
                    if (desktopQSTileData != null) {
                        QSTileController qSTileController = this.this$0;
                        qSTileController.setDesktopQsTileData(desktopQSTileData);
                        qSTileController.notifyCallBack();
                    }
                }
            }
        };
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void addCallback(QSTileControllerCallback qSTileControllerCallback) {
        qSTileControllerCallback.getClass();
        this.callbacks.add(qSTileControllerCallback);
        if (this.callbacks.size() == 1) {
            this.desktopQsTileData = this.taskBarServiceProxy.getQSTileDataData(this.type.ordinal());
            this.taskBarServiceProxy.addCallback((TaskBarServiceCallBack) this.taskBarServiceCallBack);
        }
        qSTileControllerCallback.onDataChanged(this.desktopQsTileData);
    }

    public final Set getCallbacks() {
        return this.callbacks;
    }

    public final DesktopQSTileData getDesktopQsTileData() {
        return this.desktopQsTileData;
    }

    public final DesktopQSTileData.QSTileType getType() {
        return this.type;
    }

    public final void handleClick(int i) {
        Log.d("QSTileController", "handleClick: display id: " + i + ", " + this.type.name());
        this.taskBarServiceProxy.onQsTileClick(this.type.ordinal(), i);
    }

    public final void notifyCallBack() {
        Iterator it = this.callbacks.iterator();
        while (it.hasNext()) {
            ((QSTileControllerCallback) it.next()).onDataChanged(this.desktopQsTileData);
        }
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void removeCallback(QSTileControllerCallback qSTileControllerCallback) {
        qSTileControllerCallback.getClass();
        this.callbacks.remove(qSTileControllerCallback);
        if (this.callbacks.size() == 0) {
            TaskBarServiceProxy taskBarServiceProxy = this.taskBarServiceProxy;
            taskBarServiceProxy.removeCallback((TaskBarServiceCallBack) taskBarServiceProxy);
        }
    }

    public final void setDesktopQsTileData(DesktopQSTileData desktopQSTileData) {
        this.desktopQsTileData = desktopQSTileData;
    }
}
