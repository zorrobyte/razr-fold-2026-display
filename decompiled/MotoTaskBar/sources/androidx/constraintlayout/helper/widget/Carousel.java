package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.motion.widget.MotionHelper;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.widget.R$styleable;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class Carousel extends MotionHelper {
    private int mAnimateTargetDelay;
    private int mBackwardTransition;
    private float mDampening;
    private int mEmptyViewBehavior;
    private int mFirstViewReference;
    private int mForwardTransition;
    private int mIndex;
    private boolean mInfiniteCarousel;
    int mLastStartId;
    private final ArrayList mList;
    private MotionLayout mMotionLayout;
    private int mNextState;
    private int mPreviousIndex;
    private int mPreviousState;
    private int mStartIndex;
    private int mTargetIndex;
    private int mTouchUpMode;
    Runnable mUpdateRunnable;
    private float mVelocityThreshold;

    public interface Adapter {
    }

    public Carousel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mList = new ArrayList();
        this.mPreviousIndex = 0;
        this.mIndex = 0;
        this.mFirstViewReference = -1;
        this.mInfiniteCarousel = false;
        this.mBackwardTransition = -1;
        this.mForwardTransition = -1;
        this.mPreviousState = -1;
        this.mNextState = -1;
        this.mDampening = 0.9f;
        this.mStartIndex = 0;
        this.mEmptyViewBehavior = 4;
        this.mTouchUpMode = 1;
        this.mVelocityThreshold = 2.0f;
        this.mTargetIndex = -1;
        this.mAnimateTargetDelay = 200;
        this.mLastStartId = -1;
        this.mUpdateRunnable = new Runnable() { // from class: androidx.constraintlayout.helper.widget.Carousel.1
            @Override // java.lang.Runnable
            public void run() {
                Carousel.this.mMotionLayout.setProgress(0.0f);
                Carousel.this.updateItems();
                Carousel.access$300(Carousel.this);
                int unused = Carousel.this.mIndex;
                throw null;
            }
        };
        init(context, attributeSet);
    }

    static /* synthetic */ Adapter access$300(Carousel carousel) {
        carousel.getClass();
        return null;
    }

    private void init(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.Carousel);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i);
                if (index == R$styleable.Carousel_carousel_firstView) {
                    this.mFirstViewReference = typedArrayObtainStyledAttributes.getResourceId(index, this.mFirstViewReference);
                } else if (index == R$styleable.Carousel_carousel_backwardTransition) {
                    this.mBackwardTransition = typedArrayObtainStyledAttributes.getResourceId(index, this.mBackwardTransition);
                } else if (index == R$styleable.Carousel_carousel_forwardTransition) {
                    this.mForwardTransition = typedArrayObtainStyledAttributes.getResourceId(index, this.mForwardTransition);
                } else if (index == R$styleable.Carousel_carousel_emptyViewsBehavior) {
                    this.mEmptyViewBehavior = typedArrayObtainStyledAttributes.getInt(index, this.mEmptyViewBehavior);
                } else if (index == R$styleable.Carousel_carousel_previousState) {
                    this.mPreviousState = typedArrayObtainStyledAttributes.getResourceId(index, this.mPreviousState);
                } else if (index == R$styleable.Carousel_carousel_nextState) {
                    this.mNextState = typedArrayObtainStyledAttributes.getResourceId(index, this.mNextState);
                } else if (index == R$styleable.Carousel_carousel_touchUp_dampeningFactor) {
                    this.mDampening = typedArrayObtainStyledAttributes.getFloat(index, this.mDampening);
                } else if (index == R$styleable.Carousel_carousel_touchUpMode) {
                    this.mTouchUpMode = typedArrayObtainStyledAttributes.getInt(index, this.mTouchUpMode);
                } else if (index == R$styleable.Carousel_carousel_touchUp_velocityThreshold) {
                    this.mVelocityThreshold = typedArrayObtainStyledAttributes.getFloat(index, this.mVelocityThreshold);
                } else if (index == R$styleable.Carousel_carousel_infinite) {
                    this.mInfiniteCarousel = typedArrayObtainStyledAttributes.getBoolean(index, this.mInfiniteCarousel);
                }
            }
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateItems() {
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getParent() instanceof MotionLayout) {
            MotionLayout motionLayout = (MotionLayout) getParent();
            this.mList.clear();
            for (int i = 0; i < this.mCount; i++) {
                int i2 = this.mIds[i];
                View viewById = motionLayout.getViewById(i2);
                if (this.mFirstViewReference == i2) {
                    this.mStartIndex = i;
                }
                this.mList.add(viewById);
            }
            this.mMotionLayout = motionLayout;
            if (this.mTouchUpMode == 2) {
                MotionScene.Transition transition = motionLayout.getTransition(this.mForwardTransition);
                if (transition != null) {
                    transition.setOnTouchUp(5);
                }
                MotionScene.Transition transition2 = this.mMotionLayout.getTransition(this.mBackwardTransition);
                if (transition2 != null) {
                    transition2.setOnTouchUp(5);
                }
            }
            updateItems();
        }
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mList.clear();
    }

    @Override // androidx.constraintlayout.motion.widget.MotionHelper, androidx.constraintlayout.motion.widget.MotionLayout.TransitionListener
    public void onTransitionChange(MotionLayout motionLayout, int i, int i2, float f) {
        this.mLastStartId = i;
    }

    @Override // androidx.constraintlayout.motion.widget.MotionHelper, androidx.constraintlayout.motion.widget.MotionLayout.TransitionListener
    public void onTransitionCompleted(MotionLayout motionLayout, int i) {
        int i2 = this.mIndex;
        this.mPreviousIndex = i2;
        if (i == this.mNextState) {
            this.mIndex = i2 + 1;
        } else if (i == this.mPreviousState) {
            this.mIndex = i2 - 1;
        }
        if (!this.mInfiniteCarousel) {
            throw null;
        }
        throw null;
    }
}
