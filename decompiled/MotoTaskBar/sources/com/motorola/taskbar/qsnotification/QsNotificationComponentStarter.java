package com.motorola.taskbar.qsnotification;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import com.android.systemui.statusbar.NotificationListener;
import com.motorola.internal.app.MotoDesktopManager;
import com.motorola.taskbar.qsnotification.dagger.QsNotificationComponent;

/* JADX INFO: loaded from: classes2.dex */
public class QsNotificationComponentStarter {
    private final Context mContext;
    private final DisplayManager mDisplayManager;
    private final NotificationListener mNotificationListener;
    private final SparseArray mQsNotificationCentralSurfaces = new SparseArray();
    private final QsNotificationComponent.Builder mQsNotificationComponentBuilder;

    QsNotificationComponentStarter(Context context, DisplayManager displayManager, NotificationListener notificationListener, QsNotificationComponent.Builder builder) {
        this.mContext = context;
        this.mNotificationListener = notificationListener;
        this.mQsNotificationComponentBuilder = builder;
        this.mDisplayManager = displayManager;
    }

    private void addDisplay(int i) {
        if (this.mQsNotificationCentralSurfaces.contains(i)) {
            Log.w("QsNotificationComponentStarter", "addDisplay fail: already exist display:" + i);
            return;
        }
        QsNotificationComponent qsNotificationComponentBuild = this.mQsNotificationComponentBuilder.setDisplayId(i).build();
        QsNotificationCentralSurfaces qsNotificationCentralSurfaces = qsNotificationComponentBuild.getQsNotificationCentralSurfaces();
        QsNotificationDependency qsNotificationDependency = new QsNotificationDependency(i);
        qsNotificationComponentBuild.createDependency().injectQsNotificationDependency(qsNotificationDependency);
        qsNotificationDependency.start(qsNotificationComponentBuild);
        this.mQsNotificationCentralSurfaces.append(i, qsNotificationCentralSurfaces);
        qsNotificationCentralSurfaces.start(qsNotificationDependency);
    }

    private boolean needCreateQsNotificationPanel(Display display) {
        return MotoDesktopManager.isDesktopMode(display) || MotoDesktopManager.isMobileUiModeOnly(display);
    }

    private void removeDisplay(int i) {
        QsNotificationCentralSurfaces qsNotificationCentralSurfaces = getQsNotificationCentralSurfaces(i);
        if (qsNotificationCentralSurfaces != null) {
            qsNotificationCentralSurfaces.stop();
            this.mQsNotificationCentralSurfaces.remove(i);
        }
    }

    public QsNotificationCentralSurfaces getQsNotificationCentralSurfaces(int i) {
        return (QsNotificationCentralSurfaces) this.mQsNotificationCentralSurfaces.get(i);
    }

    public void onDisplayReady(int i) {
        Display display = this.mDisplayManager.getDisplay(i);
        if (display == null || !needCreateQsNotificationPanel(display)) {
            return;
        }
        addDisplay(display.getDisplayId());
    }

    public void onDisplayRemoved(int i) {
        removeDisplay(i);
    }

    public void onSystemUIReady() {
        for (Display display : this.mDisplayManager.getDisplays()) {
            if (needCreateQsNotificationPanel(display)) {
                addDisplay(display.getDisplayId());
            }
        }
    }

    public void start() {
        this.mNotificationListener.registerAsSystemService();
    }
}
