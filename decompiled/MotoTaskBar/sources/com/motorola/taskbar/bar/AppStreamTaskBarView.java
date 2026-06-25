package com.motorola.taskbar.bar;

import android.app.IDisplayRotatableListener;
import android.content.Context;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.util.Log;
import android.view.IWindowManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManagerGlobal;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.policy.KeyButtonView;
import com.motorola.taskbar.R$drawable;
import com.motorola.taskbar.R$id;
import com.motorola.taskbar.R$string;
import com.motorola.taskbar.recent.TaskController;
import com.motorola.taskbar.recent.TaskItem;
import com.motorola.taskbar.sysicons.VolumeSysIconView;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class AppStreamTaskBarView extends TaskBarView {
    private static final String TAG = AppStreamTaskBarView.class.getSimpleName();
    private IDisplayRotatableListener.Stub mDisplayRotatableStub;
    private View mImeContainer;
    private boolean mIsVertical;
    private final List mRecentBlackTaskList;
    private View mRecentsContainer;
    private final TaskController.TaskChangedListener mTaskChangedListener;
    private final Runnable mTaskChangedRunnable;
    private TaskController mTaskController;
    private LinearLayout mTaskbarContentLayout;
    private View mVolumeContainer;
    private IWindowManager mWindowManagerService;

    /* JADX INFO: renamed from: com.motorola.taskbar.bar.AppStreamTaskBarView$4, reason: invalid class name */
    class AnonymousClass4 extends IDisplayRotatableListener.Stub {
        AnonymousClass4() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDisplayRotatableChange$0(boolean z) {
            AppStreamTaskBarView.this.setRotationEnabled(z);
        }

        public void onDisplayRotatableChange(final boolean z) {
            if (TaskBarView.DEBUG.booleanValue()) {
                Log.d(AppStreamTaskBarView.TAG, "onDisplayRotatableChange: " + z);
            }
            AppStreamTaskBarView.this.post(new Runnable() { // from class: com.motorola.taskbar.bar.AppStreamTaskBarView$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onDisplayRotatableChange$0(z);
                }
            });
        }
    }

    public AppStreamTaskBarView(Context context) {
        this(context, null);
    }

    public AppStreamTaskBarView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AppStreamTaskBarView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsVertical = false;
        this.mRecentBlackTaskList = new ArrayList() { // from class: com.motorola.taskbar.bar.AppStreamTaskBarView.1
            {
                add("com.motorola.mobiledesktop");
                add("com.motorola.mobiledesktop.core");
            }
        };
        this.mTaskChangedListener = new TaskController.TaskChangedListener() { // from class: com.motorola.taskbar.bar.AppStreamTaskBarView.2
            @Override // com.motorola.taskbar.recent.TaskController.TaskChangedListener
            public void onTaskCreated(int i2, TaskItem taskItem) {
                if (i2 == ((FrameLayout) AppStreamTaskBarView.this).mContext.getDisplayId()) {
                    AppStreamTaskBarView.this.onTasksChanged();
                }
            }

            @Override // com.motorola.taskbar.recent.TaskController.TaskChangedListener
            public void onTaskRemoved(int i2, int i3) {
                if (i2 == ((FrameLayout) AppStreamTaskBarView.this).mContext.getDisplayId()) {
                    AppStreamTaskBarView.this.onTasksChanged();
                }
            }
        };
        this.mTaskChangedRunnable = new Runnable() { // from class: com.motorola.taskbar.bar.AppStreamTaskBarView.3
            @Override // java.lang.Runnable
            public void run() {
                ArrayList tasks = AppStreamTaskBarView.this.mTaskController.getTasks(((FrameLayout) AppStreamTaskBarView.this).mContext.getDisplayId());
                int size = tasks.size();
                int size2 = tasks.size();
                int i2 = 0;
                while (i2 < size2) {
                    Object obj = tasks.get(i2);
                    i2++;
                    if (AppStreamTaskBarView.this.mRecentBlackTaskList.contains(((TaskItem) obj).getPackageName())) {
                        size--;
                    }
                }
                if (AppStreamTaskBarView.this.mRecentsContainer != null) {
                    AppStreamTaskBarView.this.mRecentsContainer.setVisibility(size <= 1 ? 8 : 0);
                }
                AppStreamTaskBarView.this.updateRestartView();
            }
        };
        this.mDisplayRotatableStub = new AnonymousClass4();
        this.mTaskController = (TaskController) Dependency.get(TaskController.class);
        this.mWindowManagerService = WindowManagerGlobal.getWindowManagerService();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFinishInflate$0(View view) {
        if (this.mVolumeSysIconView.onClick()) {
            return;
        }
        this.mTaskBarButtonClickHelper.requestSwitchVolumeDialog(this.mTaskBarContentView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFinishInflate$1(View view) {
        this.mTaskBarButtonClickHelper.toggleRestartDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFinishInflate$2(View view) {
        this.mScreenshotController.takeScreenshot(2, ((FrameLayout) this).mContext.getDisplayId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateRestartView$3(View view) {
        this.mTaskBarButtonClickHelper.toggleRestartDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTasksChanged() {
        removeCallbacks(this.mTaskChangedRunnable);
        post(this.mTaskChangedRunnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setRotationEnabled(boolean z) {
        KeyButtonView keyButtonView = this.mRotationButton;
        if (keyButtonView != null) {
            keyButtonView.setEnabled(z);
            this.mRotationButton.setDarkIntensity(z ? this.mDarkIntensity : 0.7f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRestartView() {
        boolean zIsBlackAppRunningTop = isBlackAppRunningTop();
        KeyButtonView keyButtonView = this.mRestartButton;
        if (keyButtonView != null) {
            keyButtonView.setOnClickListener(!zIsBlackAppRunningTop ? new View.OnClickListener() { // from class: com.motorola.taskbar.bar.AppStreamTaskBarView$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$updateRestartView$3(view);
                }
            } : null);
            this.mRestartButton.setDarkIntensity(zIsBlackAppRunningTop ? 0.7f : this.mDarkIntensity);
            this.mRestartButton.setNeedHover(!zIsBlackAppRunningTop);
            this.mRestartButton.setToolTipText(zIsBlackAppRunningTop ? "" : getResources().getString(R$string.restart_decription));
        }
    }

    @Override // com.motorola.taskbar.bar.TaskBarView, android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mTaskController.addCallback(this.mTaskChangedListener);
        try {
            this.mWindowManagerService.registerDisplayRotatableObserver(((FrameLayout) this).mContext.getDisplayId(), this.mDisplayRotatableStub);
            boolean zIsDisplayRotatable = this.mWindowManagerService.isDisplayRotatable(((FrameLayout) this).mContext.getDisplayId());
            Log.d(TAG, "onAttachedToWindow, rotatable:" + zIsDisplayRotatable);
            setRotationEnabled(zIsDisplayRotatable);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        updateRestartView();
    }

    @Override // com.motorola.taskbar.bar.TaskBarView
    protected void onAudioStatusChanged() {
        View view = this.mVolumeContainer;
        if (view != null) {
            view.setVisibility(this.mAudioIconShow ? 0 : 8);
        }
    }

    @Override // com.motorola.taskbar.bar.TaskBarView, android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mTaskController.removeCallback(this.mTaskChangedListener);
        removeCallbacks(this.mTaskChangedRunnable);
        try {
            this.mWindowManagerService.unregisterDisplayRotatableObserver(((FrameLayout) this).mContext.getDisplayId(), this.mDisplayRotatableStub);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.motorola.taskbar.bar.TaskBarView, android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mTaskbarContentLayout = (LinearLayout) findViewById(R$id.taskbar_content);
        initView();
        this.mRecentsContainer = findViewById(R$id.recent_container);
        this.mVolumeContainer = findViewById(R$id.volume_container);
        this.mImeContainer = findViewById(R$id.ime_container);
        VolumeSysIconView volumeSysIconView = this.mVolumeSysIconView;
        if (volumeSysIconView != null) {
            volumeSysIconView.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.AppStreamTaskBarView$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$onFinishInflate$0(view);
                }
            });
        }
        if (this.mRestartButton != null) {
            this.mRecentsButton.setImageDrawable(getDrawable(R$drawable.ic_mobileui_recents));
            this.mRestartButton.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.AppStreamTaskBarView$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$onFinishInflate$1(view);
                }
            });
        }
        KeyButtonView keyButtonView = this.mScreenshotButton;
        if (keyButtonView != null) {
            keyButtonView.setImageDrawable(getDrawable(R$drawable.mobileui_screenshot));
            this.mScreenshotButton.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.AppStreamTaskBarView$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$onFinishInflate$2(view);
                }
            });
        }
        onTasksChanged();
    }

    @Override // com.motorola.taskbar.bar.TaskBarView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r0v5 */
    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i);
        ?? r0 = (size <= 0 || View.MeasureSpec.getSize(i2) <= size) ? 0 : 1;
        if (r0 != this.mIsVertical) {
            this.mIsVertical = r0;
            this.mTaskbarContentLayout.setOrientation(r0);
            int childCount = this.mTaskbarContentLayout.getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = this.mTaskbarContentLayout.getChildAt(i3);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
                boolean z = this.mIsVertical;
                int i4 = -1;
                layoutParams.width = z ? -1 : 0;
                if (z) {
                    i4 = 0;
                }
                layoutParams.height = i4;
                layoutParams.weight = 1.0f;
                childAt.setLayoutParams(layoutParams);
            }
        }
        super.onMeasure(i, i2);
    }

    @Override // com.motorola.taskbar.bar.TaskBarView
    public void setTaskBarImeSwitchButtonVisible(boolean z) {
        View view = this.mImeContainer;
        if (view != null) {
            view.setVisibility(z ? 0 : 8);
        }
    }
}
