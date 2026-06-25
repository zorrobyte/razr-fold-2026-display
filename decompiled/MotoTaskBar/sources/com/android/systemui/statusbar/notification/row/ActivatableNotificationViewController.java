package com.android.systemui.statusbar.notification.row;

import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.util.ViewController;

/* JADX INFO: loaded from: classes.dex */
public class ActivatableNotificationViewController extends ViewController {
    private final AccessibilityManager mAccessibilityManager;
    private final ExpandableOutlineViewController mExpandableOutlineViewController;
    private final TouchHandler mTouchHandler;

    class TouchHandler implements Gefingerpoken, View.OnTouchListener {
        TouchHandler() {
        }

        @Override // com.android.systemui.Gefingerpoken
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            return false;
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == 1) {
                ((ActivatableNotificationView) ((ViewController) ActivatableNotificationViewController.this).mView).setLastActionUpTime(motionEvent.getEventTime());
            }
            ActivatableNotificationViewController.this.mAccessibilityManager.isTouchExplorationEnabled();
            return false;
        }

        @Override // com.android.systemui.Gefingerpoken
        public boolean onTouchEvent(MotionEvent motionEvent) {
            return false;
        }
    }

    public ActivatableNotificationViewController(ActivatableNotificationView activatableNotificationView, ExpandableOutlineViewController expandableOutlineViewController, AccessibilityManager accessibilityManager) {
        super(activatableNotificationView);
        this.mTouchHandler = new TouchHandler();
        this.mExpandableOutlineViewController = expandableOutlineViewController;
        this.mAccessibilityManager = accessibilityManager;
    }

    @Override // com.android.systemui.util.ViewController
    public void onInit() {
        this.mExpandableOutlineViewController.init();
        ((ActivatableNotificationView) this.mView).setOnTouchListener(this.mTouchHandler);
        ((ActivatableNotificationView) this.mView).setTouchHandler(this.mTouchHandler);
    }

    @Override // com.android.systemui.util.ViewController
    protected void onViewAttached() {
    }

    @Override // com.android.systemui.util.ViewController
    protected void onViewDetached() {
    }
}
