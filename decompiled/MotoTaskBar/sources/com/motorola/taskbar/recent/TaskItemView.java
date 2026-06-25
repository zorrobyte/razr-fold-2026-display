package com.motorola.taskbar.recent;

import android.R;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.systemui.Dependency;
import com.android.systemui.shared.recents.model.Task;
import com.android.systemui.shared.recents.model.ThumbnailData;
import com.android.systemui.statusbar.policy.KeyButtonRipple;
import com.motorola.taskbar.R$color;
import com.motorola.taskbar.R$dimen;
import com.motorola.taskbar.R$id;
import com.motorola.taskbar.R$layout;
import com.motorola.taskbar.R$string;
import com.motorola.taskbar.bar.TaskBarController;
import com.motorola.taskbar.recent.TaskController;
import com.motorola.taskbar.util.DebugConfig;
import com.motorola.taskbar.util.TooltipPopupManager;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes2.dex */
public final class TaskItemView extends LinearLayout implements TaskController.TaskChangedListener {
    private static boolean DEBUG = DebugConfig.DEBUG_RECENT_TASK_VIEW;
    private final Drawable mDefaultIcon;
    private Handler mHandler;
    private View mHoverTopTagView;
    private ImageView mIconView;
    private boolean mIsOnHover;
    private int mRequestThumbnailId;
    private final KeyButtonRipple mRipple;
    private View mRunningTagView;
    private TaskController mTaskController;
    private TaskItem mTaskItem;
    private long mTooltipPopupContextMenuShowingId;
    private TooltipPopupManager mTooltipPopupManager;
    private int mTooltipPopupOffsetY;
    private long mTooltipPopupShowingId;

    class ThumbnailDataConsumer implements Consumer {
        private int mRequestId;

        public ThumbnailDataConsumer(int i) {
            this.mRequestId = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$accept$0(View view) {
            TaskItemView.this.removeTask();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$accept$1(View view) {
            TaskItemView.this.switchTaskToTop();
        }

        @Override // java.util.function.Consumer
        public void accept(Task task) {
            if (TaskItemView.this.mIsOnHover && TaskItemView.this.mRequestThumbnailId == this.mRequestId) {
                ThumbnailData thumbnailData = task.thumbnail;
                if (thumbnailData == null || thumbnailData.getThumbnail() == null) {
                    Log.d("TaskItemView", "not thumbnail: " + task.key);
                    return;
                }
                if (TaskItemView.this.isAttachedToWindow()) {
                    Context context = TaskItemView.this.getContext();
                    int dimensionPixelSize = context.getResources().getDimensionPixelSize(R$dimen.taskbar_thumbnail_margin);
                    int height = thumbnailData.getThumbnail().getHeight();
                    int width = thumbnailData.getThumbnail().getWidth();
                    int i = (int) (context.getResources().getDisplayMetrics().heightPixels * context.getResources().getFloat(R$dimen.taskbar_thumbnail_proportion));
                    ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R$layout.task_thumbnail_view, (ViewGroup) null);
                    ImageView imageView = (ImageView) viewGroup.findViewById(R$id.thumbnail);
                    viewGroup.findViewById(R$id.close_btn).setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.recent.TaskItemView$ThumbnailDataConsumer$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f$0.lambda$accept$0(view);
                        }
                    });
                    viewGroup.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.recent.TaskItemView$ThumbnailDataConsumer$$ExternalSyntheticLambda1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f$0.lambda$accept$1(view);
                        }
                    });
                    imageView.setImageDrawable(TaskThumbnailDrawable.createTaskThumbnailDrawable(task));
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(((width * i) / height) + dimensionPixelSize, i + dimensionPixelSize);
                    TaskItemView taskItemView = TaskItemView.this;
                    TooltipPopupManager tooltipPopupManager = taskItemView.mTooltipPopupManager;
                    TaskItemView taskItemView2 = TaskItemView.this;
                    taskItemView.mTooltipPopupShowingId = tooltipPopupManager.show(1, taskItemView2, taskItemView2.mTooltipPopupOffsetY, true, viewGroup, layoutParams);
                }
            }
        }
    }

    public TaskItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRequestThumbnailId = 0;
        this.mTooltipPopupShowingId = -1L;
        this.mTooltipPopupContextMenuShowingId = -1L;
        this.mTooltipPopupOffsetY = 0;
        this.mIsOnHover = false;
        this.mHandler = new Handler() { // from class: com.motorola.taskbar.recent.TaskItemView.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what != 1) {
                    return;
                }
                TaskItemView.this.requestThumbnailPopup();
            }
        };
        Resources resources = context.getResources();
        this.mDefaultIcon = resources.getDrawable(R.drawable.sym_def_app_icon, context.getTheme());
        KeyButtonRipple keyButtonRipple = new KeyButtonRipple(context, this);
        this.mRipple = keyButtonRipple;
        setBackground(keyButtonRipple);
        keyButtonRipple.setType(KeyButtonRipple.Type.ROUNDED_RECT);
        setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.recent.TaskItemView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$0(view);
            }
        });
        setOnGenericMotionListener(new View.OnGenericMotionListener() { // from class: com.motorola.taskbar.recent.TaskItemView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnGenericMotionListener
            public final boolean onGenericMotion(View view, MotionEvent motionEvent) {
                return this.f$0.lambda$new$1(view, motionEvent);
            }
        });
        this.mTaskController = (TaskController) Dependency.get(TaskController.class);
        this.mTooltipPopupOffsetY = resources.getDimensionPixelSize(R$dimen.task_bar_tooltip_app_offset_y);
        this.mTooltipPopupManager = ((TaskBarController) Dependency.get(TaskBarController.class)).getTooltipPopupManager(this);
    }

    private void hidePopup() {
        if (DEBUG) {
            Log.d("TaskItemView", "hideThumbnailPopup");
        }
        this.mRequestThumbnailId++;
        this.mTooltipPopupManager.hide(this.mTooltipPopupShowingId, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        if (moveTaskToBack()) {
            return;
        }
        switchTaskToTop();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showContextPopupMenu$2(View view) {
        removeTask();
    }

    private boolean moveTaskToBack() {
        if (DEBUG) {
            Log.d("TaskItemView", "moveTaskToBack");
        }
        if (!this.mTaskItem.isTop()) {
            return false;
        }
        this.mRequestThumbnailId++;
        this.mTooltipPopupManager.hide(this.mTooltipPopupShowingId, true);
        return this.mTaskController.moveTaskToBack(this.mTaskItem);
    }

    private void onHoverEnter() {
        if (DEBUG) {
            Log.d("TaskItemView", "onHoverEnter");
        }
        this.mIsOnHover = true;
        this.mHandler.removeMessages(1);
        if (!this.mTooltipPopupManager.isShowing() || this.mTooltipPopupManager.getShowingId() != this.mTooltipPopupContextMenuShowingId) {
            this.mHandler.sendEmptyMessageDelayed(1, 500L);
        }
        updateHoverTopTag();
    }

    private void onHoverExit() {
        if (DEBUG) {
            Log.d("TaskItemView", "onHoverExit");
        }
        this.mIsOnHover = false;
        this.mHandler.removeMessages(1);
        hidePopup();
        updateHoverTopTag();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeTask() {
        this.mTaskController.removeTask(this.mTaskItem.getTaskId());
        this.mTooltipPopupManager.hide(this.mTooltipPopupShowingId, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestThumbnailPopup() {
        if (DEBUG) {
            Log.d("TaskItemView", "showThumbnailPopup");
        }
        TaskItem taskItem = this.mTaskItem;
        if (taskItem != null) {
            TaskController taskController = this.mTaskController;
            Task nativeTask = taskItem.getNativeTask();
            int i = this.mRequestThumbnailId + 1;
            this.mRequestThumbnailId = i;
            taskController.updateThumbnailInBackground(nativeTask, new ThumbnailDataConsumer(i));
        }
    }

    private void showContextPopupMenu() {
        if (DEBUG) {
            Log.d("TaskItemView", "showContextPopupMenu");
        }
        this.mRequestThumbnailId++;
        this.mHandler.removeMessages(1);
        long jShow = this.mTooltipPopupManager.show(1, this, this.mTooltipPopupOffsetY, getContext().getText(R$string.taskbar_app_close_window), new View.OnClickListener() { // from class: com.motorola.taskbar.recent.TaskItemView$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$showContextPopupMenu$2(view);
            }
        });
        this.mTooltipPopupShowingId = jShow;
        this.mTooltipPopupContextMenuShowingId = jShow;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void switchTaskToTop() {
        if (DEBUG) {
            Log.d("TaskItemView", "switchTaskToTop");
        }
        this.mRequestThumbnailId++;
        this.mTaskController.launchTaskFromView(this.mTaskItem.getNativeTask(), this, getContext().getDisplayId());
        this.mTooltipPopupManager.hide(this.mTooltipPopupShowingId, true);
    }

    private void updateHoverTopTag() {
        TaskItem taskItem = this.mTaskItem;
        if (taskItem == null) {
            return;
        }
        boolean zIsTop = taskItem.isTop();
        if (DEBUG) {
            Log.d("TaskItemView", "updateHoverTopTag isTop = " + zIsTop + "; mIsOnHover: " + this.mIsOnHover + ";key: " + this.mTaskItem.getTaskKey().getPackageName() + "; displayId: " + getContext().getDisplayId());
        }
        if (zIsTop && this.mIsOnHover) {
            this.mHoverTopTagView.setBackgroundColor(getResources().getColor(R$color.task_top_and_hover_color, null));
            return;
        }
        if (zIsTop) {
            this.mHoverTopTagView.setBackgroundColor(getResources().getColor(R$color.task_top_color, null));
        } else if (this.mIsOnHover) {
            this.mHoverTopTagView.setBackgroundColor(getResources().getColor(R$color.task_hover_color, null));
        } else {
            this.mHoverTopTagView.setBackgroundColor(getResources().getColor(R.color.transparent, null));
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        TaskItem taskItem = this.mTaskItem;
        if (taskItem != null) {
            TaskItem task = this.mTaskController.getTask(taskItem.getTaskId());
            if (task != null) {
                this.mTaskItem = task;
            } else {
                Log.w("TaskItemView", "onAttachedToWindow refresh TaskItem failed: " + this.mTaskItem.getTaskKey());
            }
        }
        this.mTaskController.addCallback((TaskController.TaskChangedListener) this);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mTaskController.removeCallback((TaskController.TaskChangedListener) this);
        this.mTooltipPopupManager.hide(this.mTooltipPopupShowingId, true);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        ImageView imageView = (ImageView) findViewById(R$id.task_icon);
        this.mIconView = imageView;
        imageView.setImageDrawable(this.mDefaultIcon);
        this.mRunningTagView = findViewById(R$id.running_tag);
        this.mHoverTopTagView = findViewById(R$id.hover_top_tag);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX INFO: renamed from: onGenericMotion, reason: merged with bridge method [inline-methods] */
    public boolean lambda$new$1(View view, MotionEvent motionEvent) {
        if (DEBUG && motionEvent.getAction() != 7) {
            Log.d("TaskItemView", "onGenericMotion action:" + MotionEvent.actionToString(motionEvent.getAction()) + "; button state: " + MotionEvent.buttonStateToString(motionEvent.getButtonState()) + "; toot type: " + MotionEvent.toolTypeToString(motionEvent.getToolType(0)));
        }
        if (motionEvent.getToolType(0) != 3) {
            return false;
        }
        switch (motionEvent.getAction()) {
            case 7:
                this.mTooltipPopupManager.rePostTimeOut(this.mTooltipPopupShowingId);
                return true;
            case 8:
            default:
                return true;
            case 9:
                onHoverEnter();
                return true;
            case com.motorola.plugin.core.R.styleable.GradientColor_android_endX /* 10 */:
                onHoverExit();
                return true;
            case com.motorola.plugin.core.R.styleable.GradientColor_android_endY /* 11 */:
                if (motionEvent.getButtonState() == 2) {
                    showContextPopupMenu();
                    return true;
                }
                return true;
        }
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptHoverEvent(MotionEvent motionEvent) {
        if (motionEvent.isFromSource(8194)) {
            int action = motionEvent.getAction();
            if (DEBUG && motionEvent.getAction() != 7) {
                Log.d("TaskItemView", "onInterceptHoverEvent = " + MotionEvent.actionToString(action));
            }
        }
        return super.onInterceptHoverEvent(motionEvent);
    }

    @Override // com.motorola.taskbar.recent.TaskController.TaskChangedListener
    public void onTaskInfoChanged(int i, TaskItem taskItem) {
        if (taskItem == null) {
            Log.w("TaskItemView", "onTaskInfoChanged task is null");
        } else if (this.mTaskItem == null) {
            Log.w("TaskItemView", "onTaskInfoChanged mTaskItem is null");
        }
    }

    @Override // com.motorola.taskbar.recent.TaskController.TaskChangedListener
    public void onTaskSnapshotChanged(int i, ThumbnailData thumbnailData) {
        if (thumbnailData == null || thumbnailData.getThumbnail() == null) {
            return;
        }
        TaskItem taskItem = this.mTaskItem;
        if (taskItem == null) {
            Log.w("TaskItemView", "onTaskSnapshotChanged mTaskItem is null");
        } else if (i == taskItem.getTaskId()) {
            this.mTaskItem.updateThumbnailData(thumbnailData);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (DEBUG) {
            Log.d("TaskItemView", "onTouchEvent action:" + MotionEvent.actionToString(motionEvent.getAction()) + "; button state: " + MotionEvent.buttonStateToString(motionEvent.getButtonState()) + "; toot type: " + MotionEvent.toolTypeToString(motionEvent.getToolType(0)));
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setDarkIntensity(float f) {
        this.mRipple.setDarkIntensity(f);
    }
}
