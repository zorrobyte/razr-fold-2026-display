package androidx.core.view.insets;

import android.content.res.Configuration;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsAnimationCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class SystemBarStateMonitor {
    private final ArrayList mCallbacks = new ArrayList();
    private int mColorHint;
    private final View mDetector;
    private Insets mInsets;
    private Insets mInsetsIgnoringVisibility;

    interface Callback {
        void onAnimationEnd();

        void onAnimationProgress(int i, Insets insets, RectF rectF);

        void onAnimationStart();

        void onColorHintChanged(int i);

        void onInsetsChanged(Insets insets, Insets insets2);
    }

    public static /* synthetic */ void $r8$lambda$AIasHc4haC19mIVmA0UM953xlq0(SystemBarStateMonitor systemBarStateMonitor) {
        ViewParent parent = systemBarStateMonitor.mDetector.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(systemBarStateMonitor.mDetector);
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$nKGC9baBGIw6pzxW6_YrSZDe-_Q, reason: not valid java name */
    public static /* synthetic */ WindowInsetsCompat m1053$r8$lambda$nKGC9baBGIw6pzxW6_YrSZDe_Q(SystemBarStateMonitor systemBarStateMonitor, View view, WindowInsetsCompat windowInsetsCompat) {
        Insets insets = systemBarStateMonitor.getInsets(windowInsetsCompat);
        Insets insetsIgnoringVisibility = systemBarStateMonitor.getInsetsIgnoringVisibility(windowInsetsCompat);
        if (!insets.equals(systemBarStateMonitor.mInsets) || !insetsIgnoringVisibility.equals(systemBarStateMonitor.mInsetsIgnoringVisibility)) {
            systemBarStateMonitor.mInsets = insets;
            systemBarStateMonitor.mInsetsIgnoringVisibility = insetsIgnoringVisibility;
            for (int size = systemBarStateMonitor.mCallbacks.size() - 1; size >= 0; size--) {
                ((Callback) systemBarStateMonitor.mCallbacks.get(size)).onInsetsChanged(insets, insetsIgnoringVisibility);
            }
        }
        return windowInsetsCompat;
    }

    SystemBarStateMonitor(final ViewGroup viewGroup) {
        Insets insets = Insets.NONE;
        this.mInsets = insets;
        this.mInsetsIgnoringVisibility = insets;
        Drawable background = viewGroup.getBackground();
        int i = 0;
        this.mColorHint = background instanceof ColorDrawable ? ((ColorDrawable) background).getColor() : 0;
        View view = new View(viewGroup.getContext()) { // from class: androidx.core.view.insets.SystemBarStateMonitor.1
            @Override // android.view.View
            protected void onConfigurationChanged(Configuration configuration) {
                Drawable background2 = viewGroup.getBackground();
                int color = background2 instanceof ColorDrawable ? ((ColorDrawable) background2).getColor() : 0;
                if (SystemBarStateMonitor.this.mColorHint != color) {
                    SystemBarStateMonitor.this.mColorHint = color;
                    for (int size = SystemBarStateMonitor.this.mCallbacks.size() - 1; size >= 0; size--) {
                        ((Callback) SystemBarStateMonitor.this.mCallbacks.get(size)).onColorHintChanged(color);
                    }
                }
            }
        };
        this.mDetector = view;
        view.setWillNotDraw(true);
        ViewCompat.setOnApplyWindowInsetsListener(view, new OnApplyWindowInsetsListener() { // from class: androidx.core.view.insets.SystemBarStateMonitor$$ExternalSyntheticLambda0
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) {
                return SystemBarStateMonitor.m1053$r8$lambda$nKGC9baBGIw6pzxW6_YrSZDe_Q(this.f$0, view2, windowInsetsCompat);
            }
        });
        ViewCompat.setWindowInsetsAnimationCallback(view, new WindowInsetsAnimationCompat.Callback(i) { // from class: androidx.core.view.insets.SystemBarStateMonitor.2
            private final HashMap mAnimationSidesMap = new HashMap();

            private boolean animatesSystemBars(WindowInsetsAnimationCompat windowInsetsAnimationCompat) {
                return (windowInsetsAnimationCompat.getTypeMask() & WindowInsetsCompat.Type.systemBars()) != 0;
            }

            @Override // androidx.core.view.WindowInsetsAnimationCompat.Callback
            public void onEnd(WindowInsetsAnimationCompat windowInsetsAnimationCompat) {
                if (animatesSystemBars(windowInsetsAnimationCompat)) {
                    this.mAnimationSidesMap.remove(windowInsetsAnimationCompat);
                    for (int size = SystemBarStateMonitor.this.mCallbacks.size() - 1; size >= 0; size--) {
                        ((Callback) SystemBarStateMonitor.this.mCallbacks.get(size)).onAnimationEnd();
                    }
                }
            }

            @Override // androidx.core.view.WindowInsetsAnimationCompat.Callback
            public void onPrepare(WindowInsetsAnimationCompat windowInsetsAnimationCompat) {
                if (animatesSystemBars(windowInsetsAnimationCompat)) {
                    for (int size = SystemBarStateMonitor.this.mCallbacks.size() - 1; size >= 0; size--) {
                        ((Callback) SystemBarStateMonitor.this.mCallbacks.get(size)).onAnimationStart();
                    }
                }
            }

            @Override // androidx.core.view.WindowInsetsAnimationCompat.Callback
            public WindowInsetsCompat onProgress(WindowInsetsCompat windowInsetsCompat, List list) {
                RectF rectF = new RectF(1.0f, 1.0f, 1.0f, 1.0f);
                int i2 = 0;
                for (int size = list.size() - 1; size >= 0; size--) {
                    WindowInsetsAnimationCompat windowInsetsAnimationCompat = (WindowInsetsAnimationCompat) list.get(size);
                    Integer num = (Integer) this.mAnimationSidesMap.get(windowInsetsAnimationCompat);
                    if (num != null) {
                        int iIntValue = num.intValue();
                        float alpha = windowInsetsAnimationCompat.getAlpha();
                        if ((iIntValue & 1) != 0) {
                            rectF.left = alpha;
                        }
                        if ((iIntValue & 2) != 0) {
                            rectF.top = alpha;
                        }
                        if ((iIntValue & 4) != 0) {
                            rectF.right = alpha;
                        }
                        if ((iIntValue & 8) != 0) {
                            rectF.bottom = alpha;
                        }
                        i2 |= iIntValue;
                    }
                }
                Insets insets2 = SystemBarStateMonitor.this.getInsets(windowInsetsCompat);
                for (int size2 = SystemBarStateMonitor.this.mCallbacks.size() - 1; size2 >= 0; size2--) {
                    ((Callback) SystemBarStateMonitor.this.mCallbacks.get(size2)).onAnimationProgress(i2, insets2, rectF);
                }
                return windowInsetsCompat;
            }

            @Override // androidx.core.view.WindowInsetsAnimationCompat.Callback
            public WindowInsetsAnimationCompat.BoundsCompat onStart(WindowInsetsAnimationCompat windowInsetsAnimationCompat, WindowInsetsAnimationCompat.BoundsCompat boundsCompat) {
                if (!animatesSystemBars(windowInsetsAnimationCompat)) {
                    return boundsCompat;
                }
                Insets upperBound = boundsCompat.getUpperBound();
                Insets lowerBound = boundsCompat.getLowerBound();
                int i2 = upperBound.left != lowerBound.left ? 1 : 0;
                if (upperBound.top != lowerBound.top) {
                    i2 |= 2;
                }
                if (upperBound.right != lowerBound.right) {
                    i2 |= 4;
                }
                if (upperBound.bottom != lowerBound.bottom) {
                    i2 |= 8;
                }
                this.mAnimationSidesMap.put(windowInsetsAnimationCompat, Integer.valueOf(i2));
                return boundsCompat;
            }
        });
        viewGroup.addView(view, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Insets getInsets(WindowInsetsCompat windowInsetsCompat) {
        return Insets.min(windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars()), windowInsetsCompat.getInsets(WindowInsetsCompat.Type.tappableElement()));
    }

    private Insets getInsetsIgnoringVisibility(WindowInsetsCompat windowInsetsCompat) {
        return Insets.min(windowInsetsCompat.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.systemBars()), windowInsetsCompat.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.tappableElement()));
    }

    void addCallback(Callback callback) {
        if (this.mCallbacks.contains(callback)) {
            return;
        }
        this.mCallbacks.add(callback);
        callback.onInsetsChanged(this.mInsets, this.mInsetsIgnoringVisibility);
        callback.onColorHintChanged(this.mColorHint);
    }

    void detachFromWindow() {
        this.mDetector.post(new Runnable() { // from class: androidx.core.view.insets.SystemBarStateMonitor$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                SystemBarStateMonitor.$r8$lambda$AIasHc4haC19mIVmA0UM953xlq0(this.f$0);
            }
        });
    }

    boolean hasCallback() {
        return !this.mCallbacks.isEmpty();
    }

    void removeCallback(Callback callback) {
        this.mCallbacks.remove(callback);
    }
}
