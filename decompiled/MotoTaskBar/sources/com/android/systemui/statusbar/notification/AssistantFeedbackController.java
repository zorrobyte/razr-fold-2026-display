package com.android.systemui.statusbar.notification;

import android.R;
import android.content.Context;
import android.os.Handler;
import android.provider.DeviceConfig;
import android.service.notification.NotificationListenerService;
import android.util.SparseArray;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.util.DeviceConfigProxy;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public class AssistantFeedbackController {
    private final Context mContext;
    private final DeviceConfigProxy mDeviceConfigProxy;
    private volatile boolean mFeedbackEnabled;
    private final Handler mHandler;
    private final SparseArray mIcons;
    private final DeviceConfig.OnPropertiesChangedListener mPropertiesChangedListener;

    public AssistantFeedbackController(Handler handler, Context context, DeviceConfigProxy deviceConfigProxy) {
        DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.statusbar.notification.AssistantFeedbackController.1
            public void onPropertiesChanged(DeviceConfig.Properties properties) {
                if (properties.getKeyset().contains("enable_nas_feedback")) {
                    AssistantFeedbackController.this.mFeedbackEnabled = properties.getBoolean("enable_nas_feedback", false);
                }
            }
        };
        this.mPropertiesChangedListener = onPropertiesChangedListener;
        this.mHandler = handler;
        this.mContext = context;
        this.mDeviceConfigProxy = deviceConfigProxy;
        this.mFeedbackEnabled = deviceConfigProxy.getBoolean("systemui", "enable_nas_feedback", false);
        deviceConfigProxy.addOnPropertiesChangedListener("systemui", new Executor() { // from class: com.android.systemui.statusbar.notification.AssistantFeedbackController$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.Executor
            public final void execute(Runnable runnable) {
                this.f$0.postToHandler(runnable);
            }
        }, onPropertiesChangedListener);
        SparseArray sparseArray = new SparseArray(4);
        this.mIcons = sparseArray;
        sparseArray.set(1, new FeedbackIcon(R.drawable.pointer_vertical_text_vector, R.string.permdesc_activityRecognition));
        sparseArray.set(2, new FeedbackIcon(R.drawable.pointer_wait_0, R.string.permdesc_audioWrite));
        sparseArray.set(3, new FeedbackIcon(R.drawable.pointer_wait_1, R.string.permdesc_answerPhoneCalls));
        sparseArray.set(4, new FeedbackIcon(R.drawable.pointer_vertical_text_vector_icon, R.string.permdesc_addVoicemail));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postToHandler(Runnable runnable) {
        this.mHandler.post(runnable);
    }

    public FeedbackIcon getFeedbackIcon(NotificationEntry notificationEntry) {
        return (FeedbackIcon) this.mIcons.get(getFeedbackStatus(notificationEntry));
    }

    public int getFeedbackStatus(NotificationEntry notificationEntry) {
        if (!isFeedbackEnabled()) {
            return 0;
        }
        NotificationListenerService.Ranking ranking = notificationEntry.getRanking();
        int importance = ranking.getChannel().getImportance();
        int importance2 = ranking.getImportance();
        if (importance < 3 && importance2 >= 3) {
            return 1;
        }
        if (importance >= 3 && importance2 < 3) {
            return 2;
        }
        if (importance < importance2 || ranking.getRankingAdjustment() == 1) {
            return 3;
        }
        return (importance > importance2 || ranking.getRankingAdjustment() == -1) ? 4 : 0;
    }

    public boolean isFeedbackEnabled() {
        return this.mFeedbackEnabled;
    }
}
