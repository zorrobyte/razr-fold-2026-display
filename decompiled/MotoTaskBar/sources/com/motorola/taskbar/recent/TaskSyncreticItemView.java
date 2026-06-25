package com.motorola.taskbar.recent;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.UserHandle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.Dependency;
import com.android.systemui.shared.recents.model.Task;
import com.android.systemui.shared.recents.model.ThumbnailData;
import com.android.systemui.statusbar.policy.KeyButtonRipple;
import com.motorola.taskbar.R$dimen;
import com.motorola.taskbar.R$drawable;
import com.motorola.taskbar.R$id;
import com.motorola.taskbar.R$layout;
import com.motorola.taskbar.R$string;
import com.motorola.taskbar.bar.TaskBarController;
import com.motorola.taskbar.provider.ShortcutInfo;
import com.motorola.taskbar.provider.TaskbarProviderManager;
import com.motorola.taskbar.recent.TaskSyncreticController;
import com.motorola.taskbar.recent.TaskSyncreticItem;
import com.motorola.taskbar.util.DebugConfig;
import com.motorola.taskbar.util.TooltipPopupManager;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes2.dex */
public final class TaskSyncreticItemView extends LinearLayout implements TaskSyncreticController.TaskSyncreticChangedListener {
    private static boolean DEBUG = DebugConfig.DEBUG_RECENT_TASK_VIEW;
    private final Drawable mDefaultIcon;
    private Handler mHandler;
    private View mHoverTopTagView;
    private ImageView mIconView;
    private boolean mIsOnHover;
    private int mRequestThumbnailId;
    private final KeyButtonRipple mRipple;
    private View mRunningTagView;
    private TaskSyncreticController mTaskSyncreticController;
    private TaskSyncreticItem mTaskSyncreticItem;
    private long mTooltipPopupContextMenuShowingId;
    private TooltipPopupManager mTooltipPopupManager;
    private int mTooltipPopupOffsetY;
    private long mTooltipPopupShowingId;

    public static /* synthetic */ void $r8$lambda$9FnzCTCb2zsa7_KYtOeayOP000E(Task task) {
    }

    /* JADX INFO: renamed from: $r8$lambda$WPt0Y7Qf2icnfu-HqRn6Y9KHD9g, reason: not valid java name */
    public static /* synthetic */ void m2576$r8$lambda$WPt0Y7Qf2icnfuHqRn6Y9KHD9g(Task task) {
    }

    public TaskSyncreticItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRequestThumbnailId = 0;
        this.mTooltipPopupShowingId = -1L;
        this.mTooltipPopupContextMenuShowingId = -1L;
        this.mTooltipPopupOffsetY = 0;
        this.mIsOnHover = false;
        this.mHandler = new Handler() { // from class: com.motorola.taskbar.recent.TaskSyncreticItemView.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what != 1) {
                    return;
                }
                TaskSyncreticItemView.this.requestThumbnailPopup();
            }
        };
        Resources resources = context.getResources();
        this.mDefaultIcon = resources.getDrawable(R.drawable.sym_def_app_icon, context.getTheme());
        KeyButtonRipple keyButtonRipple = new KeyButtonRipple(context, this);
        this.mRipple = keyButtonRipple;
        setBackground(keyButtonRipple);
        keyButtonRipple.setType(KeyButtonRipple.Type.ROUNDED_RECT);
        setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.recent.TaskSyncreticItemView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$0(view);
            }
        });
        setOnGenericMotionListener(new View.OnGenericMotionListener() { // from class: com.motorola.taskbar.recent.TaskSyncreticItemView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnGenericMotionListener
            public final boolean onGenericMotion(View view, MotionEvent motionEvent) {
                return this.f$0.lambda$new$1(view, motionEvent);
            }
        });
        this.mTaskSyncreticController = (TaskSyncreticController) Dependency.get(TaskSyncreticController.class);
        this.mTooltipPopupOffsetY = -resources.getDimensionPixelSize(R$dimen.task_bar_tooltip_app_offset_y);
        this.mTooltipPopupManager = ((TaskBarController) Dependency.get(TaskBarController.class)).getTooltipPopupManager(this);
    }

    private Drawable getSafeIcon(Drawable drawable) {
        return drawable != null ? drawable : this.mDefaultIcon;
    }

    private void hidePopup() {
        if (DEBUG) {
            Log.d("TaskSyncreticItemView", "hideThumbnailPopup");
        }
        this.mRequestThumbnailId++;
        this.mTooltipPopupManager.hide(this.mTooltipPopupShowingId, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        this.mHandler.removeMessages(1);
        TaskSyncreticItem taskSyncreticItem = this.mTaskSyncreticItem;
        if (taskSyncreticItem == null) {
            return;
        }
        List taskItemList = taskSyncreticItem.getTaskItemList();
        if (!taskItemList.isEmpty()) {
            if (taskItemList.size() != 1) {
                requestThumbnailPopup();
                return;
            }
            TaskItem taskItem = (TaskItem) taskItemList.get(0);
            if (moveTaskToBack(taskItem)) {
                return;
            }
            switchTaskToTop(taskItem);
            return;
        }
        ShortcutInfo shortcutInfo = this.mTaskSyncreticItem.getShortcutInfo();
        if (shortcutInfo == null) {
            Log.w("TaskSyncreticItemView", "The Shortcut should have been removed, and will update UI later");
            return;
        }
        Intent intent = new Intent();
        intent.addFlags(268435456);
        intent.setComponent(new ComponentName(shortcutInfo.getPackageName(), shortcutInfo.getClassName()));
        ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
        activityOptionsMakeBasic.setLaunchDisplayId(getContext().getDisplay().getDisplayId());
        getContext().startActivityAsUser(intent, activityOptionsMakeBasic.toBundle(), UserHandle.of(shortcutInfo.getUserId()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestThumbnailPopup$6(TaskItem taskItem, View view) {
        removeTask(taskItem);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestThumbnailPopup$7(TaskItem taskItem, View view) {
        switchTaskToTop(taskItem);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showContextPopupMenu$4(View view) {
        removeAllTask();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showContextPopupMenu$5(View view) {
        TaskbarProviderManager.removeShortcutInfo(getContext().createContextAsUser(UserHandle.of(ActivityManager.getCurrentUser()), 0), this.mTaskSyncreticItem.getShortcutInfo());
    }

    private boolean moveTaskToBack(TaskItem taskItem) {
        if (DEBUG) {
            Log.d("TaskSyncreticItemView", "moveTaskToBack");
        }
        if (!this.mTaskSyncreticItem.isTop()) {
            return false;
        }
        this.mRequestThumbnailId++;
        this.mTooltipPopupManager.hide(this.mTooltipPopupShowingId, true);
        return this.mTaskSyncreticController.moveTaskToBack(taskItem);
    }

    private void onHoverEnter() {
        if (DEBUG) {
            Log.d("TaskSyncreticItemView", "onHoverEnter: " + this.mTaskSyncreticItem.getPackageName());
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
            Log.d("TaskSyncreticItemView", "onHoverExit: " + this.mTaskSyncreticItem.getPackageName());
        }
        this.mIsOnHover = false;
        this.mHandler.removeMessages(1);
        hidePopup();
        updateHoverTopTag();
    }

    private void removeAllTask() {
        Iterator it = this.mTaskSyncreticItem.getTaskItemList().iterator();
        while (it.hasNext()) {
            removeTask((TaskItem) it.next());
        }
    }

    private void removeTask(TaskItem taskItem) {
        this.mTaskSyncreticController.removeTask(taskItem.getTaskId());
        this.mTooltipPopupManager.hide(this.mTooltipPopupShowingId, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestThumbnailPopup() {
        LayoutInflater layoutInflater;
        int i;
        TooltipPopupManager tooltipPopupManager = this.mTooltipPopupManager;
        if (tooltipPopupManager == null) {
            Log.w("TaskSyncreticItemView", "requestThumbnailPopup with null mTooltipPopupManager");
            return;
        }
        if (this.mTaskSyncreticItem == null) {
            Log.w("TaskSyncreticItemView", "requestThumbnailPopup with null mTaskSyncreticItem");
            return;
        }
        long showingId = tooltipPopupManager.getShowingId();
        if (this.mTooltipPopupManager.isShowing() && showingId == this.mTooltipPopupShowingId && showingId != this.mTooltipPopupContextMenuShowingId) {
            if (DEBUG) {
                Log.d("TaskSyncreticItemView", "requestThumbnailPopup is showing already: " + this.mTaskSyncreticItem);
                return;
            }
            return;
        }
        if (DEBUG) {
            Log.d("TaskSyncreticItemView", "requestThumbnailPopup: " + this.mTaskSyncreticItem.getPackageName());
        }
        List taskItemList = this.mTaskSyncreticItem.getTaskItemList();
        if (taskItemList == null || taskItemList.isEmpty()) {
            Log.d("TaskSyncreticItemView", "requestThumbnailPopup get empty item list");
            return;
        }
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(getContext());
        ViewGroup viewGroup = null;
        ViewGroup viewGroup2 = (ViewGroup) layoutInflaterFrom.inflate(R$layout.syncretic_task_thumbnail_view, (ViewGroup) null);
        LinearLayout linearLayout = (LinearLayout) viewGroup2.findViewById(R$id.content_frame);
        int i2 = (int) (getContext().getResources().getDisplayMetrics().heightPixels * getContext().getResources().getFloat(R$dimen.taskbar_thumbnail_proportion));
        Resources resources = getContext().getResources();
        int i3 = R$dimen.taskbar_thumbnail_margin;
        int dimensionPixelSize = resources.getDimensionPixelSize(i3);
        int dimensionPixelSize2 = getContext().getResources().getDimensionPixelSize(i3);
        int dimensionPixelSize3 = getContext().getResources().getDimensionPixelSize(R$dimen.taskbar_thumbnail_gap);
        int measuredHeight = dimensionPixelSize + i2;
        int i4 = 0;
        int i5 = 0;
        while (i4 < taskItemList.size()) {
            final TaskItem taskItem = (TaskItem) taskItemList.get(i4);
            ViewGroup viewGroup3 = (ViewGroup) layoutInflaterFrom.inflate(R$layout.syncretic_task_thumbnail_view_item, viewGroup);
            ImageView imageView = (ImageView) viewGroup3.findViewById(R$id.thumbnail);
            View viewFindViewById = viewGroup3.findViewById(R$id.close_btn);
            TextView textView = (TextView) viewGroup3.findViewById(R$id.app_name);
            List list = taskItemList;
            textView.setText(taskItem.getTitleDescription());
            if (i4 == 0) {
                textView.measure(0, 0);
                measuredHeight += textView.getMeasuredHeight();
            }
            viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.recent.TaskSyncreticItemView$$ExternalSyntheticLambda6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$requestThumbnailPopup$6(taskItem, view);
                }
            });
            viewGroup3.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.recent.TaskSyncreticItemView$$ExternalSyntheticLambda7
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$requestThumbnailPopup$7(taskItem, view);
                }
            });
            ThumbnailData thumbnailData = taskItem.getNativeTask().thumbnail;
            if (thumbnailData == null || thumbnailData.getThumbnail() == null || thumbnailData.getThumbnail().isRecycled()) {
                float f = i2;
                int i6 = (int) (0.8f * f);
                int i7 = (int) (f * 0.45f);
                Drawable icon = taskItem.getIcon();
                if (icon == null) {
                    icon = this.mTaskSyncreticItem.getIcon();
                }
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageView.getLayoutParams();
                layoutInflater = layoutInflaterFrom;
                layoutParams.gravity = 17;
                layoutParams.width = i7;
                layoutParams.height = i7;
                imageView.setLayoutParams(layoutParams);
                imageView.setImageDrawable(icon);
                i = i6;
            } else {
                i = (int) (i2 * 1.78f);
                imageView.setImageDrawable(TaskThumbnailDrawable.createTaskThumbnailDrawable(taskItem.getNativeTask()));
                layoutInflater = layoutInflaterFrom;
            }
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(i + dimensionPixelSize2, measuredHeight);
            if (i4 > 0) {
                layoutParams2.setMarginStart(dimensionPixelSize3);
                i5 += dimensionPixelSize3;
            }
            i5 += layoutParams2.width;
            linearLayout.addView(viewGroup3, layoutParams2);
            i4++;
            layoutInflaterFrom = layoutInflater;
            taskItemList = list;
            viewGroup = null;
        }
        if (i5 > getContext().getResources().getDisplayMetrics().widthPixels) {
            i5 = getContext().getResources().getDisplayMetrics().widthPixels;
        }
        this.mTooltipPopupShowingId = this.mTooltipPopupManager.show(1, this, this.mTooltipPopupOffsetY, true, viewGroup2, new LinearLayout.LayoutParams(i5, measuredHeight));
    }

    private void requestUdateThumbnailAndIcon() {
        this.mTaskSyncreticController.updateThumbnailInBackground(this.mTaskSyncreticItem, new Consumer() { // from class: com.motorola.taskbar.recent.TaskSyncreticItemView$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TaskSyncreticItemView.$r8$lambda$9FnzCTCb2zsa7_KYtOeayOP000E((Task) obj);
            }
        });
        this.mTaskSyncreticController.updateItemIconInBackground(this.mTaskSyncreticItem, new Consumer() { // from class: com.motorola.taskbar.recent.TaskSyncreticItemView$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TaskSyncreticItemView.m2576$r8$lambda$WPt0Y7Qf2icnfuHqRn6Y9KHD9g((Task) obj);
            }
        });
    }

    private void showContextPopupMenu() {
        if (DEBUG) {
            Log.d("TaskSyncreticItemView", "showContextPopupMenu");
        }
        this.mRequestThumbnailId++;
        this.mHandler.removeMessages(1);
        if (!this.mTaskSyncreticItem.isEmpty() || this.mTaskSyncreticItem.isShortcut()) {
            ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getContext()).inflate(R$layout.syncretic_task_context_menus, (ViewGroup) null);
            TextView textView = (TextView) viewGroup.findViewById(R$id.close);
            TextView textView2 = (TextView) viewGroup.findViewById(R$id.unpin);
            if (this.mTaskSyncreticItem.isEmpty()) {
                textView.setVisibility(8);
            } else {
                textView.setVisibility(0);
                textView.setText(this.mTaskSyncreticItem.getTaskItemList().size() > 1 ? R$string.taskbar_app_close_all_window : R$string.taskbar_app_close_window);
                textView.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.recent.TaskSyncreticItemView$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showContextPopupMenu$4(view);
                    }
                });
            }
            if (this.mTaskSyncreticItem.isShortcut()) {
                textView2.setVisibility(0);
                textView2.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.recent.TaskSyncreticItemView$$ExternalSyntheticLambda3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showContextPopupMenu$5(view);
                    }
                });
            } else {
                textView2.setVisibility(8);
            }
            long jShow = this.mTooltipPopupManager.show(1, this, this.mTooltipPopupOffsetY, true, viewGroup, new LinearLayout.LayoutParams(-2, -2));
            this.mTooltipPopupShowingId = jShow;
            this.mTooltipPopupContextMenuShowingId = jShow;
        }
    }

    private void switchTaskToTop(TaskItem taskItem) {
        if (DEBUG) {
            Log.d("TaskSyncreticItemView", "switchTaskToTop");
        }
        this.mRequestThumbnailId++;
        this.mTaskSyncreticController.launchTaskFromView(taskItem.getNativeTask(), this, getContext().getDisplayId());
        this.mTooltipPopupManager.hide(this.mTooltipPopupShowingId, true);
    }

    private void updateHoverTopTag() {
        TaskSyncreticItem taskSyncreticItem = this.mTaskSyncreticItem;
        if (taskSyncreticItem == null) {
            return;
        }
        boolean zIsTop = taskSyncreticItem.isTop();
        if (DEBUG) {
            Log.d("TaskSyncreticItemView", "updateHoverTopTag isTop = " + zIsTop + "; mIsOnHover: " + this.mIsOnHover + "; packageName: " + this.mTaskSyncreticItem.getPackageName() + "; displayId: " + getContext().getDisplayId());
        }
        if (zIsTop && this.mIsOnHover) {
            this.mHoverTopTagView.setBackground(getResources().getDrawable(R$drawable.task_hover_and_top_bg, null));
        } else if (zIsTop) {
            this.mHoverTopTagView.setBackground(getResources().getDrawable(R$drawable.task_top_bg, null));
        } else if (this.mIsOnHover) {
            this.mHoverTopTagView.setBackground(getResources().getDrawable(R$drawable.task_hover_bg, null));
        } else {
            this.mHoverTopTagView.setBackgroundColor(getResources().getColor(R.color.transparent, null));
        }
        this.mRunningTagView.setBackground(getResources().getDrawable(R$drawable.task_running_bg));
    }

    public TaskSyncreticItem.TaskIconCacheKey getTaskIconCacheKey() {
        TaskSyncreticItem taskSyncreticItem = this.mTaskSyncreticItem;
        if (taskSyncreticItem == null) {
            return null;
        }
        return taskSyncreticItem.iconCacheKey;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mTaskSyncreticController.addCallback((TaskSyncreticController.TaskSyncreticChangedListener) this);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mTaskSyncreticController.removeCallback((TaskSyncreticController.TaskSyncreticChangedListener) this);
        TooltipPopupManager tooltipPopupManager = this.mTooltipPopupManager;
        if (tooltipPopupManager != null) {
            tooltipPopupManager.hide(this.mTooltipPopupShowingId, true);
        }
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
            Log.d("TaskSyncreticItemView", "onGenericMotion action:" + MotionEvent.actionToString(motionEvent.getAction()) + "; button state: " + MotionEvent.buttonStateToString(motionEvent.getButtonState()) + "; toot type: " + MotionEvent.toolTypeToString(motionEvent.getToolType(0)));
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
                Log.d("TaskSyncreticItemView", "onInterceptHoverEvent = " + MotionEvent.actionToString(action));
            }
        }
        return super.onInterceptHoverEvent(motionEvent);
    }

    @Override // com.motorola.taskbar.recent.TaskSyncreticController.TaskSyncreticChangedListener
    public void onSyncreticTaskInfoChanged(int i, String str, TaskSyncreticItem taskSyncreticItem) {
        if (taskSyncreticItem == null) {
            Log.w("TaskSyncreticItemView", "onSyncreticTaskInfoChanged task is null");
        } else if (this.mTaskSyncreticItem == null) {
            Log.w("TaskSyncreticItemView", "onSyncreticTaskInfoChanged mTaskSyncreticItem is null");
        }
    }

    @Override // com.motorola.taskbar.recent.TaskSyncreticController.TaskSyncreticChangedListener
    public void onTaskSnapshotChanged(int i, String str, TaskSyncreticItem taskSyncreticItem, TaskItem taskItem) {
        TaskSyncreticItem taskSyncreticItem2 = this.mTaskSyncreticItem;
        if (taskSyncreticItem2 == null) {
            Log.w("TaskSyncreticItemView", "onTaskSnapshotChanged mTaskSyncreticItem is null");
        } else {
            str.equals(taskSyncreticItem2.getPackageName());
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (DEBUG) {
            Log.d("TaskSyncreticItemView", "onTouchEvent action:" + MotionEvent.actionToString(motionEvent.getAction()) + "; button state: " + MotionEvent.buttonStateToString(motionEvent.getButtonState()) + "; toot type: " + MotionEvent.toolTypeToString(motionEvent.getToolType(0)));
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setDarkIntensity(float f) {
        this.mRipple.setDarkIntensity(f);
        updateHoverTopTag();
    }

    public void setIcon(Drawable drawable) {
        if (this.mTaskSyncreticItem.isEmptyShortcutItem()) {
            this.mIconView.setImageDrawable(null);
        } else {
            this.mIconView.setImageDrawable(getSafeIcon(drawable));
        }
    }

    public void setTask(TaskSyncreticItem taskSyncreticItem) {
        TaskSyncreticItem taskSyncreticItem2 = this.mTaskSyncreticItem;
        if (taskSyncreticItem2 != null && !taskSyncreticItem2.getSyncreticKey().equals(taskSyncreticItem.getSyncreticKey())) {
            this.mHandler.removeMessages(1);
            this.mRipple.abortDelayedRipple();
        }
        this.mTaskSyncreticItem = taskSyncreticItem;
        this.mRequestThumbnailId++;
        TooltipPopupManager tooltipPopupManager = this.mTooltipPopupManager;
        if (tooltipPopupManager != null) {
            tooltipPopupManager.hide(this.mTooltipPopupShowingId, true);
        } else {
            Log.w("TaskSyncreticItemView", "setTask mTooltipPopupManager is null display id: " + getContext().getDisplayId());
        }
        setIcon(taskSyncreticItem.getIcon());
        this.mRunningTagView.setVisibility(taskSyncreticItem.isRunning() ? 0 : 8);
        this.mIsOnHover = false;
        updateHoverTopTag();
        requestUdateThumbnailAndIcon();
    }
}
