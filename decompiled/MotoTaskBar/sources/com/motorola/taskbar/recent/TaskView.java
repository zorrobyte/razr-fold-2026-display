package com.motorola.taskbar.recent;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.widget.ViewClippingUtil;
import com.android.systemui.Dependency;
import com.android.systemui.shared.recents.model.Task;
import com.motorola.taskbar.R$dimen;
import com.motorola.taskbar.R$id;
import com.motorola.taskbar.recent.TaskIconCache;
import com.motorola.taskbar.recent.TaskLayoutUtil;
import com.motorola.taskbar.recent.TaskThumbnailCache;
import java.util.function.Consumer;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TaskView.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class TaskView extends FrameLayout {
    public View deleteView;
    private ImageView iconView;
    private TaskViewHostCallback mHostCallback;
    private TaskIconCache.IconLoadRequest mIconLoadRequest;
    private Task mTask;
    private TaskThumbnailCache.ThumbnailLoadRequest mThumbnailLoadRequest;
    private final long myDuration;
    private int myMargin;
    private int myMarginDesktop;
    private int myPadding;
    private int myPaddingDesktop;
    private DisplayRatioScaledView snapshotContainer;
    private TaskThumbnailView snapshotView;
    private final Lazy taskController$delegate;
    private TextView titleView;
    public static final Companion Companion = new Companion(null);
    private static final TaskView$Companion$CLIPPING_PARAMETERS$1 CLIPPING_PARAMETERS = new ViewClippingUtil.ClippingParameters() { // from class: com.motorola.taskbar.recent.TaskView$Companion$CLIPPING_PARAMETERS$1
        public boolean isClippingEnablingAllowed(View view) {
            view.getClass();
            return true;
        }

        public boolean shouldFinish(View view) {
            view.getClass();
            return view instanceof RecentsView;
        }
    };

    /* JADX INFO: compiled from: TaskView.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TaskView(Context context) {
        this(context, null, 0, 6, null);
        context.getClass();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TaskView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        context.getClass();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TaskView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        context.getClass();
        this.taskController$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.taskbar.recent.TaskView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return TaskView.taskController_delegate$lambda$0();
            }
        });
        this.myDuration = 500L;
    }

    public /* synthetic */ TaskView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    private final void cancelPendingLoadTasks() {
        TaskThumbnailCache.ThumbnailLoadRequest thumbnailLoadRequest = this.mThumbnailLoadRequest;
        if (thumbnailLoadRequest != null) {
            thumbnailLoadRequest.cancel();
            this.mThumbnailLoadRequest = null;
        }
        TaskIconCache.IconLoadRequest iconLoadRequest = this.mIconLoadRequest;
        if (iconLoadRequest != null) {
            iconLoadRequest.cancel();
            this.mIconLoadRequest = null;
        }
    }

    private final TaskController getTaskController() {
        return (TaskController) this.taskController$delegate.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void launchTaskInner(Task task, View view, boolean z) {
        Task.TaskKey taskKey = task.key;
        Log.d("TaskView", "launch task id = " + taskKey.id + ", pkg=" + taskKey.getPackageName() + ", alt=" + z);
        TaskViewHostCallback taskViewHostCallback = this.mHostCallback;
        if (taskViewHostCallback != null) {
            taskViewHostCallback.launchTask(view, task, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setIcon(Drawable drawable) {
        ImageView imageView = this.iconView;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("iconView");
            imageView = null;
        }
        imageView.setImageDrawable(drawable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setTitle(CharSequence charSequence) {
        TextView textView = this.titleView;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("titleView");
            textView = null;
        }
        textView.setText(charSequence);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final TaskController taskController_delegate$lambda$0() {
        return (TaskController) Dependency.get(TaskController.class);
    }

    private final void updateViewByDisplayMode(int i) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        DisplayRatioScaledView displayRatioScaledView = null;
        ViewGroup.MarginLayoutParams marginLayoutParams = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : null;
        if (i == 1) {
            DisplayRatioScaledView displayRatioScaledView2 = this.snapshotContainer;
            if (displayRatioScaledView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("snapshotContainer");
                displayRatioScaledView2 = null;
            }
            displayRatioScaledView2.setRatioX(0.67f);
            DisplayRatioScaledView displayRatioScaledView3 = this.snapshotContainer;
            if (displayRatioScaledView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("snapshotContainer");
            } else {
                displayRatioScaledView = displayRatioScaledView3;
            }
            displayRatioScaledView.setRatioY(0.63f);
            int i2 = this.myPadding;
            setPadding(i2, i2, i2, i2);
            if (marginLayoutParams != null) {
                int i3 = this.myMargin;
                marginLayoutParams.setMargins(i3, i3, i3, i3);
                return;
            }
            return;
        }
        if (i != 2) {
            DisplayRatioScaledView displayRatioScaledView4 = this.snapshotContainer;
            if (displayRatioScaledView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("snapshotContainer");
                displayRatioScaledView4 = null;
            }
            displayRatioScaledView4.setRatioX(0.2f);
            DisplayRatioScaledView displayRatioScaledView5 = this.snapshotContainer;
            if (displayRatioScaledView5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("snapshotContainer");
            } else {
                displayRatioScaledView = displayRatioScaledView5;
            }
            displayRatioScaledView.setRatioY(0.2f);
            int i4 = this.myPaddingDesktop;
            setPadding(i4, i4, i4, i4);
            if (marginLayoutParams != null) {
                int i5 = this.myMarginDesktop;
                marginLayoutParams.setMargins(i5, i5, i5, i5);
                return;
            }
            return;
        }
        DisplayRatioScaledView displayRatioScaledView6 = this.snapshotContainer;
        if (displayRatioScaledView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("snapshotContainer");
            displayRatioScaledView6 = null;
        }
        displayRatioScaledView6.setRatioX(0.32f);
        DisplayRatioScaledView displayRatioScaledView7 = this.snapshotContainer;
        if (displayRatioScaledView7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("snapshotContainer");
        } else {
            displayRatioScaledView = displayRatioScaledView7;
        }
        displayRatioScaledView.setRatioY(0.36f);
        int i6 = this.myPadding;
        setPadding(i6, i6, i6, i6);
        if (marginLayoutParams != null) {
            int i7 = this.myMargin;
            marginLayoutParams.setMargins(i7, i7, i7, i7);
        }
    }

    public final void bindTask(Task task, TaskViewHostCallback taskViewHostCallback) {
        task.getClass();
        taskViewHostCallback.getClass();
        this.mTask = task;
        this.mHostCallback = taskViewHostCallback;
        updateViewByDisplayMode(taskViewHostCallback.getDisplayMode());
        TaskThumbnailView taskThumbnailView = this.snapshotView;
        if (taskThumbnailView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("snapshotView");
            taskThumbnailView = null;
        }
        taskThumbnailView.bind(this.mTask);
        onTaskViewVisibleChanged(true);
    }

    public final void deleteTask(View view) {
        view.getClass();
        Task task = this.mTask;
        if (task != null) {
            Task.TaskKey taskKey = task.key;
            Log.d("TaskView", "close task id = " + taskKey.id + ", pkg=" + taskKey.getPackageName());
            TaskViewHostCallback taskViewHostCallback = this.mHostCallback;
            if (taskViewHostCallback != null) {
                taskViewHostCallback.closeTask(view, task);
            }
        }
    }

    public final View getDeleteView() {
        View view = this.deleteView;
        if (view != null) {
            return view;
        }
        Intrinsics.throwUninitializedPropertyAccessException("deleteView");
        return null;
    }

    public final void launchTask(final View view, final boolean z) {
        view.getClass();
        final Task task = this.mTask;
        if (task != null) {
            if (z) {
                launchTaskInner(task, view, z);
                return;
            }
            ViewPropertyAnimator viewPropertyAnimatorAnimate = animate();
            viewPropertyAnimatorAnimate.scaleX(1.15f);
            viewPropertyAnimatorAnimate.scaleY(1.15f);
            viewPropertyAnimatorAnimate.setDuration(this.myDuration);
            viewPropertyAnimatorAnimate.withStartAction(new Runnable() { // from class: com.motorola.taskbar.recent.TaskView$launchTask$1$1$1
                @Override // java.lang.Runnable
                public final void run() {
                    ViewClippingUtil.setClippingDeactivated(view, true, TaskView.CLIPPING_PARAMETERS);
                }
            });
            viewPropertyAnimatorAnimate.withEndAction(new Runnable() { // from class: com.motorola.taskbar.recent.TaskView$launchTask$1$1$2
                @Override // java.lang.Runnable
                public final void run() {
                    this.this$0.launchTaskInner(task, view, z);
                    ViewClippingUtil.setClippingDeactivated(view, false, TaskView.CLIPPING_PARAMETERS);
                }
            });
            viewPropertyAnimatorAnimate.start();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.iconView = (ImageView) requireViewById(R$id.icon);
        this.titleView = (TextView) requireViewById(R$id.app_title);
        this.snapshotView = (TaskThumbnailView) requireViewById(R$id.snapshot);
        setDeleteView(requireViewById(R$id.delete));
        this.snapshotContainer = (DisplayRatioScaledView) requireViewById(R$id.snapshot_container);
        getDeleteView().setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.recent.TaskView.onFinishInflate.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                view.getClass();
                TaskView.this.deleteTask(view);
            }
        });
        setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.recent.TaskView.onFinishInflate.2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TaskView taskView = TaskView.this;
                view.getClass();
                taskView.launchTask(view, false);
            }
        });
        this.myPadding = getResources().getDimensionPixelOffset(R$dimen.task_view_padding);
        this.myPaddingDesktop = getResources().getDimensionPixelOffset(R$dimen.task_view_padding_desktop);
        this.myMargin = getResources().getDimensionPixelOffset(R$dimen.task_view_margin);
        this.myMarginDesktop = getResources().getDimensionPixelOffset(R$dimen.task_view_margin_desktop);
    }

    public final void onTaskViewVisibleChanged(boolean z) {
        cancelPendingLoadTasks();
        if (z) {
            this.mThumbnailLoadRequest = getTaskController().updateThumbnailInBackground(this.mTask, new Consumer() { // from class: com.motorola.taskbar.recent.TaskView.onTaskViewVisibleChanged.1
                @Override // java.util.function.Consumer
                public final void accept(Task task) {
                    TaskThumbnailView taskThumbnailView = TaskView.this.snapshotView;
                    if (taskThumbnailView == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("snapshotView");
                        taskThumbnailView = null;
                    }
                    taskThumbnailView.setThumbnail(task, task.thumbnail);
                }
            });
            this.mIconLoadRequest = getTaskController().updateIconInBackground(this.mTask, new Consumer() { // from class: com.motorola.taskbar.recent.TaskView.onTaskViewVisibleChanged.2
                @Override // java.util.function.Consumer
                public final void accept(Task task) {
                    TaskView.this.setIcon(task.icon);
                    TaskView taskView = TaskView.this;
                    TaskLayoutUtil.Companion companion = TaskLayoutUtil.Companion;
                    Context context = taskView.getContext();
                    context.getClass();
                    taskView.setTitle(companion.getTitle(context, task));
                }
            });
            return;
        }
        TaskThumbnailView taskThumbnailView = this.snapshotView;
        if (taskThumbnailView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("snapshotView");
            taskThumbnailView = null;
        }
        taskThumbnailView.setThumbnail(null, null);
        setIcon(null);
        setTitle(null);
        Task task = this.mTask;
        if (task != null) {
            task.thumbnail = null;
        }
    }

    public final void refreshThumbnail() {
        TaskThumbnailView taskThumbnailView = this.snapshotView;
        if (taskThumbnailView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("snapshotView");
            taskThumbnailView = null;
        }
        Task task = this.mTask;
        taskThumbnailView.setThumbnail(task, task != null ? task.thumbnail : null);
    }

    public final void setDeleteView(View view) {
        view.getClass();
        this.deleteView = view;
    }
}
