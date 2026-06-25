package com.motorola.taskbar.qsnotification;

import android.util.ArrayMap;
import androidx.core.util.Preconditions;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationSectionsManager;
import com.motorola.taskbar.qsnotification.dagger.QsNotificationComponent;
import dagger.Lazy;

/* JADX INFO: loaded from: classes2.dex */
public class QsNotificationDependency {
    Lazy mActivityStarter;
    Lazy mAmbientState;
    Lazy mGroupExpansionManager;
    Lazy mGroupMembershipManager;
    Lazy mNotificationRemoteInputManager;
    Lazy mNotificationSectionsManager;
    private QsNotificationComponent mQsNotificationComponent;
    Lazy mQsNotificationTooltipPopupManager;
    Lazy mSmartReplyController;
    private final ArrayMap mDependencies = new ArrayMap();
    private final ArrayMap mProviders = new ArrayMap();
    private QsNotificationDependency sDependency = null;

    public interface DependencyInjector {
        void injectQsNotificationDependency(QsNotificationDependency qsNotificationDependency);
    }

    interface LazyDependencyCreator {
        Object createDependency();
    }

    public QsNotificationDependency(int i) {
    }

    private synchronized Object getDependencyInner(Object obj) {
        Object objCreateDependency;
        objCreateDependency = this.mDependencies.get(obj);
        if (objCreateDependency == null) {
            objCreateDependency = createDependency(obj);
            this.mDependencies.put(obj, objCreateDependency);
        }
        return objCreateDependency;
    }

    protected Object createDependency(Object obj) {
        Preconditions.checkArgument(obj instanceof Class);
        LazyDependencyCreator lazyDependencyCreator = (LazyDependencyCreator) this.mProviders.get(obj);
        if (lazyDependencyCreator != null) {
            return lazyDependencyCreator.createDependency();
        }
        throw new IllegalArgumentException("Unsupported dependency " + obj + ". " + this.mProviders.size() + " providers known.");
    }

    public Object get(Class cls) {
        QsNotificationDependency qsNotificationDependency = this.sDependency;
        if (qsNotificationDependency == null) {
            return null;
        }
        return qsNotificationDependency.getDependency(cls);
    }

    protected final Object getDependency(Class cls) {
        return getDependencyInner(cls);
    }

    public void start(QsNotificationComponent qsNotificationComponent) {
        this.mQsNotificationComponent = qsNotificationComponent;
        ArrayMap arrayMap = this.mProviders;
        final Lazy lazy = this.mSmartReplyController;
        lazy.getClass();
        arrayMap.put(SmartReplyController.class, new LazyDependencyCreator() { // from class: com.motorola.taskbar.qsnotification.QsNotificationDependency$$ExternalSyntheticLambda0
            @Override // com.motorola.taskbar.qsnotification.QsNotificationDependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy.get();
            }
        });
        ArrayMap arrayMap2 = this.mProviders;
        final Lazy lazy2 = this.mNotificationRemoteInputManager;
        lazy2.getClass();
        arrayMap2.put(NotificationRemoteInputManager.class, new LazyDependencyCreator() { // from class: com.motorola.taskbar.qsnotification.QsNotificationDependency$$ExternalSyntheticLambda0
            @Override // com.motorola.taskbar.qsnotification.QsNotificationDependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy2.get();
            }
        });
        ArrayMap arrayMap3 = this.mProviders;
        final Lazy lazy3 = this.mNotificationSectionsManager;
        lazy3.getClass();
        arrayMap3.put(NotificationSectionsManager.class, new LazyDependencyCreator() { // from class: com.motorola.taskbar.qsnotification.QsNotificationDependency$$ExternalSyntheticLambda0
            @Override // com.motorola.taskbar.qsnotification.QsNotificationDependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy3.get();
            }
        });
        ArrayMap arrayMap4 = this.mProviders;
        final Lazy lazy4 = this.mAmbientState;
        lazy4.getClass();
        arrayMap4.put(AmbientState.class, new LazyDependencyCreator() { // from class: com.motorola.taskbar.qsnotification.QsNotificationDependency$$ExternalSyntheticLambda0
            @Override // com.motorola.taskbar.qsnotification.QsNotificationDependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy4.get();
            }
        });
        ArrayMap arrayMap5 = this.mProviders;
        final Lazy lazy5 = this.mGroupExpansionManager;
        lazy5.getClass();
        arrayMap5.put(GroupExpansionManager.class, new LazyDependencyCreator() { // from class: com.motorola.taskbar.qsnotification.QsNotificationDependency$$ExternalSyntheticLambda0
            @Override // com.motorola.taskbar.qsnotification.QsNotificationDependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy5.get();
            }
        });
        ArrayMap arrayMap6 = this.mProviders;
        final Lazy lazy6 = this.mGroupMembershipManager;
        lazy6.getClass();
        arrayMap6.put(GroupMembershipManager.class, new LazyDependencyCreator() { // from class: com.motorola.taskbar.qsnotification.QsNotificationDependency$$ExternalSyntheticLambda0
            @Override // com.motorola.taskbar.qsnotification.QsNotificationDependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy6.get();
            }
        });
        ArrayMap arrayMap7 = this.mProviders;
        final Lazy lazy7 = this.mQsNotificationTooltipPopupManager;
        lazy7.getClass();
        arrayMap7.put(QsNotificationTooltipPopupManager.class, new LazyDependencyCreator() { // from class: com.motorola.taskbar.qsnotification.QsNotificationDependency$$ExternalSyntheticLambda0
            @Override // com.motorola.taskbar.qsnotification.QsNotificationDependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy7.get();
            }
        });
        ArrayMap arrayMap8 = this.mProviders;
        final Lazy lazy8 = this.mActivityStarter;
        lazy8.getClass();
        arrayMap8.put(ActivityStarter.class, new LazyDependencyCreator() { // from class: com.motorola.taskbar.qsnotification.QsNotificationDependency$$ExternalSyntheticLambda0
            @Override // com.motorola.taskbar.qsnotification.QsNotificationDependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy8.get();
            }
        });
        this.sDependency = this;
    }
}
