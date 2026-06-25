package com.android.systemui.shared.recents.model;

import android.app.ActivityManager;
import android.app.TaskInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.ArrayUtils;
import com.android.wm.shell.shared.split.SplitScreenConstants;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class Task {
    public int cliDisplayMode = 0;
    public int colorBackground;
    public int colorPrimary;
    public Drawable icon;
    public boolean isDockable;
    public boolean isLocked;
    public TaskKey key;
    public ActivityManager.TaskDescription taskDescription;
    public ThumbnailData thumbnail;
    public String title;
    public String titleDescription;
    public ComponentName topActivity;

    public class TaskKey implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.android.systemui.shared.recents.model.Task.TaskKey.1
            @Override // android.os.Parcelable.Creator
            public TaskKey createFromParcel(Parcel parcel) {
                return TaskKey.readFromParcel(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public TaskKey[] newArray(int i) {
                return new TaskKey[i];
            }
        };
        public ComponentName baseActivity;
        public final Intent baseIntent;
        public final int displayId;
        public final int id;
        public boolean isActivityStackTransparent;
        public boolean isTopActivityNoDisplay;
        public long lastActiveTime;
        private int mHashCode;
        public int numActivities;
        public final ComponentName sourceComponent;
        public final int userId;
        public int windowingMode;

        public TaskKey(int i, int i2, Intent intent, ComponentName componentName, int i3, long j) {
            this.id = i;
            this.windowingMode = i2;
            this.baseIntent = intent;
            this.sourceComponent = componentName;
            this.userId = i3;
            this.lastActiveTime = j;
            this.displayId = 0;
            updateHashCode();
        }

        public TaskKey(int i, int i2, Intent intent, ComponentName componentName, int i3, long j, int i4, ComponentName componentName2, int i5, boolean z, boolean z2) {
            this.id = i;
            this.windowingMode = i2;
            this.baseIntent = intent;
            this.sourceComponent = componentName;
            this.userId = i3;
            this.lastActiveTime = j;
            this.displayId = i4;
            this.baseActivity = componentName2;
            this.numActivities = i5;
            this.isTopActivityNoDisplay = z;
            this.isActivityStackTransparent = z2;
            updateHashCode();
        }

        public TaskKey(TaskInfo taskInfo) {
            ComponentName componentName = taskInfo.origActivity;
            componentName = componentName == null ? taskInfo.realActivity : componentName;
            this.id = taskInfo.taskId;
            this.windowingMode = taskInfo.configuration.windowConfiguration.getWindowingMode();
            this.baseIntent = taskInfo.baseIntent;
            this.sourceComponent = componentName;
            this.userId = taskInfo.userId;
            this.lastActiveTime = taskInfo.lastActiveTime;
            this.displayId = taskInfo.displayId;
            this.baseActivity = taskInfo.baseActivity;
            this.numActivities = taskInfo.numActivities;
            this.isTopActivityNoDisplay = taskInfo.isTopActivityNoDisplay;
            this.isActivityStackTransparent = taskInfo.isActivityStackTransparent;
            updateHashCode();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static TaskKey readFromParcel(Parcel parcel) {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            int i3 = parcel.readInt();
            long j = parcel.readLong();
            int i4 = parcel.readInt();
            Parcelable.Creator creator = ComponentName.CREATOR;
            return new TaskKey(i, i2, intent, (ComponentName) parcel.readTypedObject(creator), i3, j, i4, (ComponentName) parcel.readTypedObject(creator), parcel.readInt(), parcel.readBoolean(), parcel.readBoolean());
        }

        private void updateHashCode() {
            this.mHashCode = Objects.hash(Integer.valueOf(this.id), Integer.valueOf(this.windowingMode), Integer.valueOf(this.userId));
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof TaskKey)) {
                return false;
            }
            TaskKey taskKey = (TaskKey) obj;
            return this.id == taskKey.id && this.windowingMode == taskKey.windowingMode && this.userId == taskKey.userId;
        }

        public ComponentName getComponent() {
            return this.baseIntent.getComponent();
        }

        public String getPackageName() {
            return this.baseIntent.getComponent() != null ? this.baseIntent.getComponent().getPackageName() : this.baseIntent.getPackage();
        }

        public int hashCode() {
            return this.mHashCode;
        }

        public String toString() {
            return "id=" + this.id + " windowingMode=" + this.windowingMode + " user=" + this.userId + " lastActiveTime=" + this.lastActiveTime;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.id);
            parcel.writeInt(this.windowingMode);
            parcel.writeTypedObject(this.baseIntent, i);
            parcel.writeInt(this.userId);
            parcel.writeLong(this.lastActiveTime);
            parcel.writeInt(this.displayId);
            parcel.writeTypedObject(this.sourceComponent, i);
            parcel.writeTypedObject(this.baseActivity, i);
            parcel.writeInt(this.numActivities);
            parcel.writeBoolean(this.isTopActivityNoDisplay);
            parcel.writeBoolean(this.isActivityStackTransparent);
        }
    }

    public Task(TaskKey taskKey, int i, int i2, boolean z, boolean z2, ActivityManager.TaskDescription taskDescription, ComponentName componentName) {
        this.key = taskKey;
        this.colorPrimary = i;
        this.colorBackground = i2;
        this.taskDescription = taskDescription;
        this.isDockable = z;
        this.isLocked = z2;
        this.topActivity = componentName;
    }

    public static Task from(TaskKey taskKey, TaskInfo taskInfo, boolean z) {
        ActivityManager.TaskDescription taskDescription = taskInfo.taskDescription;
        return new Task(taskKey, taskDescription != null ? taskDescription.getPrimaryColor() : 0, taskDescription != null ? taskDescription.getBackgroundColor() : 0, taskInfo.supportsMultiWindow && ArrayUtils.contains(SplitScreenConstants.CONTROLLED_WINDOWING_MODES_WHEN_ACTIVE, taskInfo.getWindowingMode()) && (taskInfo.getActivityType() == 0 || ArrayUtils.contains(SplitScreenConstants.CONTROLLED_ACTIVITY_TYPES, taskInfo.getActivityType())), z, taskDescription, taskInfo.topActivity);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Task) {
            return this.key.equals(((Task) obj).key);
        }
        return false;
    }

    public ComponentName getTopComponent() {
        ComponentName componentName = this.topActivity;
        return componentName != null ? componentName : this.key.baseIntent.getComponent();
    }

    public int hashCode() {
        return this.key.hashCode();
    }

    public String toString() {
        return "[" + this.key.toString() + "] " + this.title;
    }
}
