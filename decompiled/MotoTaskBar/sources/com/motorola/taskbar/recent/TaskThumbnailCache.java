package com.motorola.taskbar.recent;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.launcher3.Utilities;
import com.android.launcher3.util.Executors;
import com.android.launcher3.util.LooperExecutor;
import com.android.launcher3.util.Preconditions;
import com.android.systemui.shared.recents.model.Task;
import com.android.systemui.shared.recents.model.ThumbnailData;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.motorola.taskbar.R$bool;
import com.motorola.taskbar.R$integer;
import java.util.ArrayList;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes2.dex */
public class TaskThumbnailCache {
    private final Handler mBackgroundHandler;
    private final TaskKeyLruCache mCache;
    private final int mCacheSize;
    private final boolean mEnableTaskSnapshotPreloading;
    private final HighResLoadingState mHighResLoadingState;

    /* JADX INFO: renamed from: com.motorola.taskbar.recent.TaskThumbnailCache$1, reason: invalid class name */
    class AnonymousClass1 extends ThumbnailLoadRequest {
        final /* synthetic */ Consumer val$callback;
        final /* synthetic */ Task.TaskKey val$key;
        final /* synthetic */ boolean val$lowResolution;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Handler handler, boolean z, Task.TaskKey taskKey, boolean z2, Consumer consumer) {
            super(handler, z);
            this.val$key = taskKey;
            this.val$lowResolution = z2;
            this.val$callback = consumer;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0(Task.TaskKey taskKey, ThumbnailData thumbnailData, Consumer consumer) {
            if (isCanceled()) {
                return;
            }
            TaskThumbnailCache.this.mCache.put(taskKey, thumbnailData);
            consumer.accept(thumbnailData);
            onEnd();
        }

        @Override // java.lang.Runnable
        public void run() {
            final ThumbnailData taskThumbnail = ActivityManagerWrapper.getInstance().getTaskThumbnail(this.val$key.id, this.val$lowResolution);
            LooperExecutor looperExecutor = Executors.MAIN_EXECUTOR;
            final Task.TaskKey taskKey = this.val$key;
            final Consumer consumer = this.val$callback;
            looperExecutor.execute(new Runnable() { // from class: com.motorola.taskbar.recent.TaskThumbnailCache$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$run$0(taskKey, taskThumbnail, consumer);
                }
            });
        }
    }

    public class HighResLoadingState {
        private ArrayList mCallbacks;
        private boolean mFlingingFast;
        private boolean mForceHighResThumbnails;
        private boolean mHighResLoadingEnabled;
        private boolean mVisible;

        private HighResLoadingState(Context context) {
            this.mCallbacks = new ArrayList();
            this.mForceHighResThumbnails = !TaskThumbnailCache.supportsLowResThumbnails();
        }

        private void updateState() {
            int size;
            boolean z = this.mHighResLoadingEnabled;
            boolean z2 = this.mForceHighResThumbnails || (this.mVisible && !this.mFlingingFast);
            this.mHighResLoadingEnabled = z2;
            if (z == z2 || (size = this.mCallbacks.size() - 1) < 0) {
                return;
            }
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(this.mCallbacks.get(size));
            throw null;
        }

        public boolean isEnabled() {
            return this.mHighResLoadingEnabled;
        }

        public void setVisible(boolean z) {
            this.mVisible = z;
            updateState();
        }
    }

    public abstract class ThumbnailLoadRequest extends CancellablHandlerRunnable {
        public final boolean mLowResolution;

        ThumbnailLoadRequest(Handler handler, boolean z) {
            super(handler, null);
            this.mLowResolution = z;
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$tCNupZtE8jsXmTXE-LX45FwH8mU, reason: not valid java name */
    public static /* synthetic */ void m2578$r8$lambda$tCNupZtE8jsXmTXELX45FwH8mU(Task task, Consumer consumer, ThumbnailData thumbnailData) {
        task.thumbnail = thumbnailData;
        consumer.accept(task);
    }

    public TaskThumbnailCache(Context context, Looper looper) {
        this.mBackgroundHandler = new Handler(looper);
        this.mHighResLoadingState = new HighResLoadingState(context);
        Resources resources = context.getResources();
        int integer = resources.getInteger(R$integer.recentsThumbnailCacheSize);
        this.mCacheSize = integer;
        this.mEnableTaskSnapshotPreloading = resources.getBoolean(R$bool.config_enableTaskSnapshotPreloading);
        this.mCache = new TaskKeyLruCache(integer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean supportsLowResThumbnails() {
        Resources system = Resources.getSystem();
        int identifier = system.getIdentifier("config_lowResTaskSnapshotScale", "dimen", "android");
        return identifier == 0 || 0.0f < system.getFloat(identifier);
    }

    private ThumbnailLoadRequest updateThumbnailInBackground(Task.TaskKey taskKey, boolean z, Consumer consumer) {
        return updateThumbnailInBackground(taskKey, z, consumer, false);
    }

    private ThumbnailLoadRequest updateThumbnailInBackground(Task.TaskKey taskKey, boolean z, Consumer consumer, boolean z2) {
        Preconditions.assertUIThread();
        ThumbnailData thumbnailData = (ThumbnailData) this.mCache.getAndInvalidateIfModified(taskKey);
        if (thumbnailData != null && thumbnailData.getThumbnail() != null && ((!thumbnailData.reducedResolution || z) && !z2)) {
            consumer.accept(thumbnailData);
            return null;
        }
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.mBackgroundHandler, z, taskKey, z, consumer);
        Utilities.postAsyncCallback(this.mBackgroundHandler, anonymousClass1);
        return anonymousClass1;
    }

    public HighResLoadingState getHighResLoadingState() {
        return this.mHighResLoadingState;
    }

    public void onTaskSnapshotChanged(Task.TaskKey taskKey, ThumbnailData thumbnailData) {
        if (thumbnailData == null || thumbnailData.getThumbnail() == null) {
            return;
        }
        this.mCache.put(taskKey, thumbnailData);
    }

    public void remove(Task.TaskKey taskKey) {
        this.mCache.remove(taskKey);
    }

    public void updateTaskSnapShot(int i, ThumbnailData thumbnailData) {
        Preconditions.assertUIThread();
        this.mCache.updateIfAlreadyInCache(i, thumbnailData);
    }

    public ThumbnailLoadRequest updateThumbnailInBackground(final Task task, final Consumer consumer) {
        Preconditions.assertUIThread();
        boolean zIsEnabled = this.mHighResLoadingState.isEnabled();
        ThumbnailData thumbnailData = task.thumbnail;
        if (thumbnailData == null || thumbnailData.getThumbnail() == null || (task.thumbnail.reducedResolution && zIsEnabled)) {
            return updateThumbnailInBackground(task.key, !this.mHighResLoadingState.isEnabled(), new Consumer() { // from class: com.motorola.taskbar.recent.TaskThumbnailCache$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    TaskThumbnailCache.m2578$r8$lambda$tCNupZtE8jsXmTXELX45FwH8mU(task, consumer, (ThumbnailData) obj);
                }
            });
        }
        consumer.accept(task);
        return null;
    }

    public void updateThumbnailInCache(final Task task, boolean z) {
        Preconditions.assertUIThread();
        if (task.thumbnail == null || z) {
            updateThumbnailInBackground(task.key, true, new Consumer() { // from class: com.motorola.taskbar.recent.TaskThumbnailCache$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    task.thumbnail = (ThumbnailData) obj;
                }
            }, z);
        }
    }
}
