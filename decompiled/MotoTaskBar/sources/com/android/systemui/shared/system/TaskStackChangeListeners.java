package com.android.systemui.shared.system;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.TaskStackListener;
import android.content.ComponentName;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Trace;
import android.util.Log;
import android.window.TaskSnapshot;
import com.android.internal.os.SomeArgs;
import com.android.systemui.shared.recents.model.ThumbnailData;
import com.motorola.plugin.core.R;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TaskStackChangeListeners {
    private static final TaskStackChangeListeners INSTANCE = new TaskStackChangeListeners();
    private static final String TAG = "TaskStackChangeListeners";
    private final Impl mImpl;

    class Impl extends TaskStackListener implements Handler.Callback {
        private final Handler mHandler;
        private boolean mRegistered;
        private final List mTaskStackListeners;
        private final List mTmpListeners;

        private Impl(TaskStackChangeListeners taskStackChangeListeners, Handler handler) {
            this.mTaskStackListeners = new ArrayList();
            this.mTmpListeners = new ArrayList();
            this.mHandler = handler;
        }

        private Impl(TaskStackChangeListeners taskStackChangeListeners, Looper looper) {
            this.mTaskStackListeners = new ArrayList();
            this.mTmpListeners = new ArrayList();
            this.mHandler = new Handler(looper, this);
        }

        public void addListener(TaskStackChangeListener taskStackChangeListener) {
            synchronized (this.mTaskStackListeners) {
                this.mTaskStackListeners.add(taskStackChangeListener);
            }
            if (this.mRegistered) {
                return;
            }
            try {
                ActivityTaskManager.getService().registerTaskStackListener(this);
                this.mRegistered = true;
            } catch (Exception e) {
                Log.w(TaskStackChangeListeners.TAG, "Failed to call registerTaskStackListener", e);
            }
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            synchronized (this.mTaskStackListeners) {
                try {
                    switch (message.what) {
                        case 1:
                            Trace.beginSection("onTaskStackChanged");
                            for (int size = this.mTaskStackListeners.size() - 1; size >= 0; size--) {
                                ((TaskStackChangeListener) this.mTaskStackListeners.get(size)).onTaskStackChanged();
                            }
                            Trace.endSection();
                            break;
                        case 2:
                            Trace.beginSection("onTaskSnapshotChanged");
                            TaskSnapshot taskSnapshot = (TaskSnapshot) message.obj;
                            ThumbnailData thumbnailDataFromSnapshot = ThumbnailData.fromSnapshot(taskSnapshot);
                            for (int size2 = this.mTaskStackListeners.size() - 1; size2 >= 0; size2--) {
                                zOnTaskSnapshotChanged |= ((TaskStackChangeListener) this.mTaskStackListeners.get(size2)).onTaskSnapshotChanged(message.arg1, thumbnailDataFromSnapshot);
                            }
                            if (!zOnTaskSnapshotChanged) {
                                thumbnailDataFromSnapshot.recycleBitmap();
                                if (taskSnapshot.getHardwareBuffer() != null) {
                                    taskSnapshot.getHardwareBuffer().close();
                                }
                            }
                            Trace.endSection();
                            break;
                        case 3:
                            PinnedActivityInfo pinnedActivityInfo = (PinnedActivityInfo) message.obj;
                            for (int size3 = this.mTaskStackListeners.size() - 1; size3 >= 0; size3--) {
                                ((TaskStackChangeListener) this.mTaskStackListeners.get(size3)).onActivityPinned(pinnedActivityInfo.mPackageName, pinnedActivityInfo.mUserId, pinnedActivityInfo.mTaskId, pinnedActivityInfo.mStackId);
                            }
                            break;
                        case 4:
                            SomeArgs someArgs = (SomeArgs) message.obj;
                            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) someArgs.arg1;
                            boolean z = someArgs.argi1 != 0;
                            boolean z2 = someArgs.argi2 != 0;
                            zOnTaskSnapshotChanged = someArgs.argi3 != 0;
                            for (int size4 = this.mTaskStackListeners.size() - 1; size4 >= 0; size4--) {
                                ((TaskStackChangeListener) this.mTaskStackListeners.get(size4)).onActivityRestartAttempt(runningTaskInfo, z, z2, zOnTaskSnapshotChanged);
                            }
                            break;
                        case 6:
                            for (int size5 = this.mTaskStackListeners.size() - 1; size5 >= 0; size5--) {
                                ((TaskStackChangeListener) this.mTaskStackListeners.get(size5)).onActivityForcedResizable((String) message.obj, message.arg1, message.arg2);
                            }
                            break;
                        case 7:
                            for (int size6 = this.mTaskStackListeners.size() - 1; size6 >= 0; size6--) {
                                ((TaskStackChangeListener) this.mTaskStackListeners.get(size6)).onActivityDismissingDockedStack();
                            }
                            break;
                        case 8:
                            ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) message.obj;
                            int i = message.arg1;
                            for (int size7 = this.mTaskStackListeners.size() - 1; size7 >= 0; size7--) {
                                ((TaskStackChangeListener) this.mTaskStackListeners.get(size7)).onTaskProfileLocked(runningTaskInfo2, i);
                            }
                            break;
                        case R.styleable.GradientColor_android_endX /* 10 */:
                            for (int size8 = this.mTaskStackListeners.size() - 1; size8 >= 0; size8--) {
                                ((TaskStackChangeListener) this.mTaskStackListeners.get(size8)).onActivityUnpinned();
                            }
                            break;
                        case R.styleable.GradientColor_android_endY /* 11 */:
                            ActivityManager.RunningTaskInfo runningTaskInfo3 = (ActivityManager.RunningTaskInfo) message.obj;
                            for (int size9 = this.mTaskStackListeners.size() - 1; size9 >= 0; size9--) {
                                ((TaskStackChangeListener) this.mTaskStackListeners.get(size9)).onActivityLaunchOnSecondaryDisplayFailed(runningTaskInfo3);
                            }
                            break;
                        case 12:
                            for (int size10 = this.mTaskStackListeners.size() - 1; size10 >= 0; size10--) {
                                ((TaskStackChangeListener) this.mTaskStackListeners.get(size10)).onTaskCreated(message.arg1, (ComponentName) message.obj);
                            }
                            break;
                        case 13:
                            for (int size11 = this.mTaskStackListeners.size() - 1; size11 >= 0; size11--) {
                                ((TaskStackChangeListener) this.mTaskStackListeners.get(size11)).onTaskRemoved(message.arg1);
                            }
                            break;
                        case 14:
                            ActivityManager.RunningTaskInfo runningTaskInfo4 = (ActivityManager.RunningTaskInfo) message.obj;
                            for (int size12 = this.mTaskStackListeners.size() - 1; size12 >= 0; size12--) {
                                ((TaskStackChangeListener) this.mTaskStackListeners.get(size12)).onTaskMovedToFront(runningTaskInfo4);
                            }
                            break;
                        case 15:
                            for (int size13 = this.mTaskStackListeners.size() - 1; size13 >= 0; size13--) {
                                ((TaskStackChangeListener) this.mTaskStackListeners.get(size13)).onActivityRequestedOrientationChanged(message.arg1, message.arg2);
                            }
                            break;
                        case 16:
                            ActivityManager.RunningTaskInfo runningTaskInfo5 = (ActivityManager.RunningTaskInfo) message.obj;
                            for (int size14 = this.mTaskStackListeners.size() - 1; size14 >= 0; size14--) {
                                ((TaskStackChangeListener) this.mTaskStackListeners.get(size14)).onActivityLaunchOnSecondaryDisplayRerouted(runningTaskInfo5);
                            }
                            break;
                        case 17:
                            for (int size15 = this.mTaskStackListeners.size() - 1; size15 >= 0; size15--) {
                                ((TaskStackChangeListener) this.mTaskStackListeners.get(size15)).onBackPressedOnTaskRoot((ActivityManager.RunningTaskInfo) message.obj);
                            }
                            break;
                        case 18:
                            for (int size16 = this.mTaskStackListeners.size() - 1; size16 >= 0; size16--) {
                                ((TaskStackChangeListener) this.mTaskStackListeners.get(size16)).onTaskDisplayChanged(message.arg1, message.arg2);
                            }
                            break;
                        case 19:
                            for (int size17 = this.mTaskStackListeners.size() - 1; size17 >= 0; size17--) {
                                ((TaskStackChangeListener) this.mTaskStackListeners.get(size17)).onRecentTaskListUpdated();
                            }
                            break;
                        case 20:
                            for (int size18 = this.mTaskStackListeners.size() - 1; size18 >= 0; size18--) {
                                ((TaskStackChangeListener) this.mTaskStackListeners.get(size18)).onRecentTaskListFrozenChanged(message.arg1 != 0);
                            }
                            break;
                        case 21:
                            ActivityManager.RunningTaskInfo runningTaskInfo6 = (ActivityManager.RunningTaskInfo) message.obj;
                            for (int size19 = this.mTaskStackListeners.size() - 1; size19 >= 0; size19--) {
                                ((TaskStackChangeListener) this.mTaskStackListeners.get(size19)).onTaskDescriptionChanged(runningTaskInfo6);
                            }
                            break;
                        case 22:
                            for (int size20 = this.mTaskStackListeners.size() - 1; size20 >= 0; size20--) {
                                ((TaskStackChangeListener) this.mTaskStackListeners.get(size20)).onActivityRotation(message.arg1);
                            }
                            break;
                        case 23:
                            for (int size21 = this.mTaskStackListeners.size() - 1; size21 >= 0; size21--) {
                                ((TaskStackChangeListener) this.mTaskStackListeners.get(size21)).onLockTaskModeChanged(message.arg1);
                            }
                            break;
                        case 24:
                            Trace.beginSection("onTaskSnapshotInvalidated");
                            ThumbnailData thumbnailData = new ThumbnailData();
                            for (int size22 = this.mTaskStackListeners.size() - 1; size22 >= 0; size22--) {
                                ((TaskStackChangeListener) this.mTaskStackListeners.get(size22)).onTaskSnapshotChanged(message.arg1, thumbnailData);
                            }
                            Trace.endSection();
                            break;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            Object obj = message.obj;
            if (obj instanceof SomeArgs) {
                ((SomeArgs) obj).recycle();
            }
            return true;
        }

        public void onActivityDismissingDockedTask() {
            this.mHandler.sendEmptyMessage(7);
        }

        public void onActivityForcedResizable(String str, int i, int i2) {
            this.mHandler.obtainMessage(6, i, i2, str).sendToTarget();
        }

        public void onActivityLaunchOnSecondaryDisplayFailed(ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
            this.mHandler.obtainMessage(11, i, 0, runningTaskInfo).sendToTarget();
        }

        public void onActivityLaunchOnSecondaryDisplayRerouted(ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
            this.mHandler.obtainMessage(16, i, 0, runningTaskInfo).sendToTarget();
        }

        public void onActivityPinned(String str, int i, int i2, int i3) {
            this.mHandler.removeMessages(3);
            this.mHandler.obtainMessage(3, new PinnedActivityInfo(str, i, i2, i3)).sendToTarget();
        }

        public void onActivityRequestedOrientationChanged(int i, int i2) {
            this.mHandler.obtainMessage(15, i, i2).sendToTarget();
        }

        public void onActivityRestartAttempt(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2, boolean z3) {
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.arg1 = runningTaskInfo;
            someArgsObtain.argi1 = z ? 1 : 0;
            someArgsObtain.argi2 = z2 ? 1 : 0;
            someArgsObtain.argi3 = z3 ? 1 : 0;
            this.mHandler.removeMessages(4);
            this.mHandler.obtainMessage(4, someArgsObtain).sendToTarget();
        }

        public void onActivityRotation(int i) {
            this.mHandler.obtainMessage(22, i, 0).sendToTarget();
        }

        public void onActivityUnpinned() {
            this.mHandler.removeMessages(10);
            this.mHandler.sendEmptyMessage(10);
        }

        public void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo runningTaskInfo) {
            this.mHandler.obtainMessage(17, runningTaskInfo).sendToTarget();
        }

        public void onLockTaskModeChanged(int i) {
            this.mHandler.obtainMessage(23, i, 0).sendToTarget();
        }

        public void onRecentTaskListFrozenChanged(boolean z) {
            this.mHandler.obtainMessage(20, z ? 1 : 0, 0).sendToTarget();
        }

        public void onRecentTaskListUpdated() {
            this.mHandler.obtainMessage(19).sendToTarget();
        }

        public void onTaskCreated(int i, ComponentName componentName) {
            this.mHandler.obtainMessage(12, i, 0, componentName).sendToTarget();
        }

        public void onTaskDescriptionChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
            this.mHandler.obtainMessage(21, runningTaskInfo).sendToTarget();
        }

        public void onTaskDisplayChanged(int i, int i2) {
            this.mHandler.obtainMessage(18, i, i2).sendToTarget();
        }

        public void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) {
            this.mHandler.obtainMessage(14, runningTaskInfo).sendToTarget();
        }

        public void onTaskProfileLocked(ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
            this.mHandler.obtainMessage(8, i, 0, runningTaskInfo).sendToTarget();
        }

        public void onTaskRemoved(int i) {
            this.mHandler.obtainMessage(13, i, 0).sendToTarget();
        }

        public void onTaskSnapshotChanged(int i, TaskSnapshot taskSnapshot) {
            this.mHandler.obtainMessage(2, i, 0, taskSnapshot).sendToTarget();
        }

        public void onTaskSnapshotInvalidated(int i) {
            this.mHandler.obtainMessage(24, i, 0).sendToTarget();
        }

        public void onTaskStackChanged() {
            synchronized (this.mTaskStackListeners) {
                this.mTmpListeners.addAll(this.mTaskStackListeners);
            }
            for (int size = this.mTmpListeners.size() - 1; size >= 0; size--) {
                ((TaskStackChangeListener) this.mTmpListeners.get(size)).onTaskStackChangedBackground();
            }
            this.mTmpListeners.clear();
            this.mHandler.removeMessages(1);
            this.mHandler.sendEmptyMessage(1);
        }
    }

    class PinnedActivityInfo {
        final String mPackageName;
        final int mStackId;
        final int mTaskId;
        final int mUserId;

        PinnedActivityInfo(String str, int i, int i2, int i3) {
            this.mPackageName = str;
            this.mUserId = i;
            this.mTaskId = i2;
            this.mStackId = i3;
        }
    }

    class TestSyncHandler extends Handler {
        private Handler.Callback mCb;

        public TestSyncHandler() {
            super(Looper.getMainLooper());
        }

        @Override // android.os.Handler
        public boolean sendMessageAtTime(Message message, long j) {
            return this.mCb.handleMessage(message);
        }

        public void setCallback(Handler.Callback callback) {
            this.mCb = callback;
        }
    }

    private TaskStackChangeListeners() {
        this.mImpl = new Impl(Looper.getMainLooper());
    }

    private TaskStackChangeListeners(Handler handler) {
        this.mImpl = new Impl(handler);
    }

    public static TaskStackChangeListeners getInstance() {
        return INSTANCE;
    }

    public static TaskStackChangeListeners getTestInstance() {
        TestSyncHandler testSyncHandler = new TestSyncHandler();
        TaskStackChangeListeners taskStackChangeListeners = new TaskStackChangeListeners(testSyncHandler);
        testSyncHandler.setCallback(taskStackChangeListeners.mImpl);
        return taskStackChangeListeners;
    }

    public TaskStackListener getListenerImpl() {
        return this.mImpl;
    }

    public void registerTaskStackListener(TaskStackChangeListener taskStackChangeListener) {
        synchronized (this.mImpl) {
            this.mImpl.addListener(taskStackChangeListener);
        }
    }
}
