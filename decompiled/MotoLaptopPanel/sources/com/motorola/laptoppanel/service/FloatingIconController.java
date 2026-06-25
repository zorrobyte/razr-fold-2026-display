package com.motorola.laptoppanel.service;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import com.motorola.laptoppanel.R;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import motorola.core_services.window.MotoWindowManager;

/* JADX INFO: compiled from: FloatingIconController.kt */
/* JADX INFO: loaded from: classes.dex */
public final class FloatingIconController {
    private final float TAP_MOVEMENT_THRESHOLD_DP;
    private final Context context;
    private final Lazy iconSize$delegate;
    private ImageView iconView;
    private float initialTouchX;
    private float initialTouchY;
    private float initialX;
    private float initialY;
    private boolean isDragging;
    private final Function0 onClick;
    private final Lazy tapMovementThresholdPx$delegate;
    private WindowManager windowManager;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    /* JADX INFO: compiled from: FloatingIconController.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public FloatingIconController(Context context, Function0 function0) {
        context.getClass();
        function0.getClass();
        this.context = context;
        this.onClick = function0;
        this.TAP_MOVEMENT_THRESHOLD_DP = 10.0f;
        this.tapMovementThresholdPx$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.laptoppanel.service.FloatingIconController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Float.valueOf(FloatingIconController.tapMovementThresholdPx_delegate$lambda$0(this.f$0));
            }
        });
        this.iconSize$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.laptoppanel.service.FloatingIconController$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Integer.valueOf(FloatingIconController.iconSize_delegate$lambda$1(this.f$0));
            }
        });
    }

    private final View.OnTouchListener createTouchListener() {
        return new View.OnTouchListener() { // from class: com.motorola.laptoppanel.service.FloatingIconController.createTouchListener.1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                int actionMasked = motionEvent.getActionMasked();
                if (actionMasked == 0) {
                    Log.d("FloatingIconController", "Press Down");
                    if (FloatingIconController.this.iconView != null) {
                        ImageView imageView = FloatingIconController.this.iconView;
                        imageView.getClass();
                        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                        layoutParams.getClass();
                        WindowManager.LayoutParams layoutParams2 = (WindowManager.LayoutParams) layoutParams;
                        FloatingIconController.this.initialX = layoutParams2.x;
                        FloatingIconController.this.initialY = layoutParams2.y;
                        FloatingIconController.this.initialTouchX = motionEvent.getRawX();
                        FloatingIconController.this.initialTouchY = motionEvent.getRawY();
                        FloatingIconController.this.isDragging = false;
                        Log.d("FloatingIconController", "    InitialX = " + FloatingIconController.this.initialX + ", InitialY = " + FloatingIconController.this.initialY + ", InitialTouchX = " + FloatingIconController.this.initialTouchX + ", InitialTouchY = " + FloatingIconController.this.initialTouchY);
                    }
                    return true;
                }
                if (actionMasked == 1) {
                    if (FloatingIconController.this.isDragging) {
                        Log.d("FloatingIconController", "Dragging the icon!");
                    } else {
                        FloatingIconController.this.onClick.invoke();
                    }
                    FloatingIconController.this.isDragging = false;
                    return true;
                }
                if (actionMasked != 2) {
                    return false;
                }
                Log.d("FloatingIconController", "Drag the icon");
                float rawX = FloatingIconController.this.initialTouchX - motionEvent.getRawX();
                float rawY = FloatingIconController.this.initialTouchY - motionEvent.getRawY();
                Log.d("FloatingIconController", "    Move to " + motionEvent.getRawX() + ", " + motionEvent.getRawY() + ", dx = " + rawX + ", dy = " + rawY);
                if (Math.abs(rawX) > FloatingIconController.this.getTapMovementThresholdPx() || Math.abs(rawY) > FloatingIconController.this.getTapMovementThresholdPx()) {
                    FloatingIconController.this.isDragging = true;
                }
                if (FloatingIconController.this.isDragging && FloatingIconController.this.iconView != null) {
                    ImageView imageView2 = FloatingIconController.this.iconView;
                    imageView2.getClass();
                    ViewGroup.LayoutParams layoutParams3 = imageView2.getLayoutParams();
                    layoutParams3.getClass();
                    WindowManager.LayoutParams layoutParams4 = (WindowManager.LayoutParams) layoutParams3;
                    layoutParams4.x = (int) (FloatingIconController.this.initialX + rawX);
                    layoutParams4.y = (int) (FloatingIconController.this.initialY + rawY);
                    WindowManager windowManager = FloatingIconController.this.windowManager;
                    if (windowManager != null) {
                        windowManager.updateViewLayout(FloatingIconController.this.iconView, layoutParams4);
                    }
                }
                return true;
            }
        };
    }

    private final int getIconSize() {
        return ((Number) this.iconSize$delegate.getValue()).intValue();
    }

    private final int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (identifier > 0) {
            return resources.getDimensionPixelSize(identifier);
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final float getTapMovementThresholdPx() {
        return ((Number) this.tapMovementThresholdPx$delegate.getValue()).floatValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int iconSize_delegate$lambda$1(FloatingIconController floatingIconController) {
        return (int) (floatingIconController.context.getResources().getDisplayMetrics().density * 56);
    }

    private final void initAndAddIconView() {
        Object systemService = this.context.getSystemService("window");
        systemService.getClass();
        WindowManager windowManager = (WindowManager) systemService;
        ImageView imageView = new ImageView(this.context);
        imageView.setImageResource(R.drawable.zz_moto_ic_floating_icon);
        imageView.setBackgroundResource(R.drawable.background_floating_icon);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.laptoppanel.service.FloatingIconController$initAndAddIconView$imageView$1$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.this$0.onClick.invoke();
            }
        });
        imageView.setOnTouchListener(createTouchListener());
        int navigationBarHeight = getNavigationBarHeight(this.context);
        float f = 48;
        int i = (int) (this.context.getResources().getDisplayMetrics().density * f);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(getIconSize(), getIconSize(), MotoWindowManager.LayoutParams.getConstantInt_TYPE_MOTO_TRUSTED_APPS_OVERLAY(), 520, -3);
        layoutParams.gravity = 8388693;
        layoutParams.y = navigationBarHeight + i;
        layoutParams.x = (int) (f * this.context.getResources().getDisplayMetrics().density);
        windowManager.addView(imageView, layoutParams);
        this.iconView = imageView;
        this.windowManager = windowManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float tapMovementThresholdPx_delegate$lambda$0(FloatingIconController floatingIconController) {
        return floatingIconController.context.getResources().getDisplayMetrics().density * floatingIconController.TAP_MOVEMENT_THRESHOLD_DP;
    }

    public final void hide() {
        ImageView imageView = this.iconView;
        if (imageView != null) {
            imageView.setVisibility(8);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void remove() {
        ImageView imageView = this.iconView;
        if (imageView != null) {
            try {
                WindowManager windowManager = this.windowManager;
                if (windowManager != null) {
                    windowManager.removeView(imageView);
                }
            } catch (Exception e) {
                Log.w("FloatingIconController", "Failed to remove floating icon: " + e.getMessage());
            } finally {
                this.iconView = null;
                this.windowManager = null;
            }
        }
    }

    public final void show() {
        ImageView imageView = this.iconView;
        if (imageView == null) {
            initAndAddIconView();
        } else if (imageView != null) {
            imageView.setVisibility(0);
        }
    }
}
