package com.motorola.taskbar.bar;

import android.content.Context;
import android.os.Handler;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.motorola.taskbar.bar.QSNotificationPanelController;
import com.motorola.taskbar.qsnotification.QsNotificationCentralSurfaces;
import com.motorola.taskbar.qsnotification.QsNotificationComponentStarter;
import java.util.ArrayList;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes2.dex */
public class QSNotificationPanelController implements ConfigurationController.ConfigurationListener, CallbackController {
    private final Context mContext;
    private final ArrayList mListeners = new ArrayList();
    private final Handler mMainHandler;
    private final QsNotificationComponentStarter mQsNotificationComponentStarter;

    public interface QSNotificationPanelControllerCallBack {
        void onUnreadNotificationCountChanged(int i, int i2);
    }

    public QSNotificationPanelController(Context context, Handler handler, QsNotificationComponentStarter qsNotificationComponentStarter) {
        this.mContext = context;
        this.mMainHandler = handler;
        this.mQsNotificationComponentStarter = qsNotificationComponentStarter;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyUnReadNotificationSizeChanged$1(final int i, final int i2) {
        this.mListeners.forEach(new Consumer() { // from class: com.motorola.taskbar.bar.QSNotificationPanelController$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((QSNotificationPanelController.QSNotificationPanelControllerCallBack) obj).onUnreadNotificationCountChanged(i, i2);
            }
        });
    }

    private void notifyUnReadNotificationSizeChanged(final int i, final int i2) {
        this.mMainHandler.post(new Runnable() { // from class: com.motorola.taskbar.bar.QSNotificationPanelController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$notifyUnReadNotificationSizeChanged$1(i, i2);
            }
        });
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void addCallback(QSNotificationPanelControllerCallBack qSNotificationPanelControllerCallBack) {
        this.mListeners.add(qSNotificationPanelControllerCallBack);
    }

    public int getUnreadNotificationCount(int i) {
        QsNotificationCentralSurfaces qsNotificationCentralSurfaces = this.mQsNotificationComponentStarter.getQsNotificationCentralSurfaces(i);
        if (qsNotificationCentralSurfaces != null) {
            return qsNotificationCentralSurfaces.getUnReadNotificationSize();
        }
        return 0;
    }

    public boolean isMediaPanelVisible(int i) {
        QsNotificationCentralSurfaces qsNotificationCentralSurfaces = this.mQsNotificationComponentStarter.getQsNotificationCentralSurfaces(i);
        if (qsNotificationCentralSurfaces != null) {
            return qsNotificationCentralSurfaces.isMediaPanelVisible();
        }
        return false;
    }

    public void onUnReadNotificationSizeChanged(int i, int i2) {
        notifyUnReadNotificationSizeChanged(i, i2);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void removeCallback(QSNotificationPanelControllerCallBack qSNotificationPanelControllerCallBack) {
        this.mListeners.remove(qSNotificationPanelControllerCallBack);
    }

    public void requestHideNotificationPanel(int i) {
        QsNotificationCentralSurfaces qsNotificationCentralSurfaces = this.mQsNotificationComponentStarter.getQsNotificationCentralSurfaces(i);
        if (qsNotificationCentralSurfaces != null) {
            qsNotificationCentralSurfaces.requestHidePanel();
        }
    }

    public void requestShowNotificationPanel(int i) {
        QsNotificationCentralSurfaces qsNotificationCentralSurfaces = this.mQsNotificationComponentStarter.getQsNotificationCentralSurfaces(i);
        if (qsNotificationCentralSurfaces != null) {
            qsNotificationCentralSurfaces.requestShowPanel();
        }
    }

    public void requestSwitchNotificationPanel(int i) {
        QsNotificationCentralSurfaces qsNotificationCentralSurfaces = this.mQsNotificationComponentStarter.getQsNotificationCentralSurfaces(i);
        if (qsNotificationCentralSurfaces != null) {
            qsNotificationCentralSurfaces.requestTogglePanel();
        }
    }
}
