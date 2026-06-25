package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.constraintlayout.motion.widget.MotionHelper;
import androidx.constraintlayout.widget.R$styleable;

/* JADX INFO: loaded from: classes.dex */
public class MotionEffect extends MotionHelper {
    private int mFadeMove;
    private float mMotionEffectAlpha;
    private int mMotionEffectEnd;
    private int mMotionEffectStart;
    private boolean mMotionEffectStrictMove;
    private int mMotionEffectTranslationX;
    private int mMotionEffectTranslationY;
    private int mViewTransitionId;

    public MotionEffect(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMotionEffectAlpha = 0.1f;
        this.mMotionEffectStart = 49;
        this.mMotionEffectEnd = 50;
        this.mMotionEffectTranslationX = 0;
        this.mMotionEffectTranslationY = 0;
        this.mMotionEffectStrictMove = true;
        this.mViewTransitionId = -1;
        this.mFadeMove = -1;
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.MotionEffect);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i);
                if (index == R$styleable.MotionEffect_motionEffect_start) {
                    int i2 = typedArrayObtainStyledAttributes.getInt(index, this.mMotionEffectStart);
                    this.mMotionEffectStart = i2;
                    this.mMotionEffectStart = Math.max(Math.min(i2, 99), 0);
                } else if (index == R$styleable.MotionEffect_motionEffect_end) {
                    int i3 = typedArrayObtainStyledAttributes.getInt(index, this.mMotionEffectEnd);
                    this.mMotionEffectEnd = i3;
                    this.mMotionEffectEnd = Math.max(Math.min(i3, 99), 0);
                } else if (index == R$styleable.MotionEffect_motionEffect_translationX) {
                    this.mMotionEffectTranslationX = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.mMotionEffectTranslationX);
                } else if (index == R$styleable.MotionEffect_motionEffect_translationY) {
                    this.mMotionEffectTranslationY = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.mMotionEffectTranslationY);
                } else if (index == R$styleable.MotionEffect_motionEffect_alpha) {
                    this.mMotionEffectAlpha = typedArrayObtainStyledAttributes.getFloat(index, this.mMotionEffectAlpha);
                } else if (index == R$styleable.MotionEffect_motionEffect_move) {
                    this.mFadeMove = typedArrayObtainStyledAttributes.getInt(index, this.mFadeMove);
                } else if (index == R$styleable.MotionEffect_motionEffect_strict) {
                    this.mMotionEffectStrictMove = typedArrayObtainStyledAttributes.getBoolean(index, this.mMotionEffectStrictMove);
                } else if (index == R$styleable.MotionEffect_motionEffect_viewTransition) {
                    this.mViewTransitionId = typedArrayObtainStyledAttributes.getResourceId(index, this.mViewTransitionId);
                }
            }
            int i4 = this.mMotionEffectStart;
            int i5 = this.mMotionEffectEnd;
            if (i4 == i5) {
                if (i4 > 0) {
                    this.mMotionEffectStart = i4 - 1;
                } else {
                    this.mMotionEffectEnd = i5 + 1;
                }
            }
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    @Override // androidx.constraintlayout.motion.widget.MotionHelper
    public boolean isDecorator() {
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:56:0x0190, code lost:
    
        if (r14 == 0.0f) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x01a6, code lost:
    
        if (r14 == 0.0f) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x01b7, code lost:
    
        if (r15 == 0.0f) goto L59;
     */
    /* JADX WARN: Removed duplicated region for block: B:48:0x016c  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01d0  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x01f3  */
    @Override // androidx.constraintlayout.motion.widget.MotionHelper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onPreSetup(androidx.constraintlayout.motion.widget.MotionLayout r23, java.util.HashMap r24) {
        /*
            Method dump skipped, instruction units count: 515
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.helper.widget.MotionEffect.onPreSetup(androidx.constraintlayout.motion.widget.MotionLayout, java.util.HashMap):void");
    }
}
