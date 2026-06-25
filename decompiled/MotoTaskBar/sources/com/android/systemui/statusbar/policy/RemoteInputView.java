package com.android.systemui.statusbar.policy;

import android.R;
import android.app.ActivityManager;
import android.content.ClipData;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.BlendMode;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Trace;
import android.os.UserHandle;
import android.text.Editable;
import android.text.SpannedString;
import android.text.TextWatcher;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.util.Property;
import android.view.ContentInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.OnReceiveContentListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.WindowInsetsController;
import android.view.accessibility.AccessibilityEvent;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import androidx.core.animation.Animator;
import androidx.core.animation.AnimatorListenerAdapter;
import androidx.core.animation.AnimatorSet;
import androidx.core.animation.Interpolator;
import androidx.core.animation.ObjectAnimator;
import androidx.core.animation.ValueAnimator;
import com.android.app.animation.InterpolatorsAndroidX;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.ContrastColorUtil;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.res.R$dimen;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper;
import com.android.systemui.statusbar.policy.RemoteInputView;
import com.motorola.taskbar.R$color;
import com.motorola.taskbar.R$drawable;
import com.motorola.taskbar.R$id;
import com.motorola.taskbar.R$layout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes.dex */
public class RemoteInputView extends LinearLayout implements View.OnClickListener {
    public static final Object VIEW_TAG = new Object();
    private boolean mColorized;
    private GradientDrawable mContentBackground;
    private Rect mContentBackgroundBounds;
    private LinearLayout mContentView;
    private RemoteInputController mController;
    private ImageView mDelete;
    private ImageView mDeleteBg;
    private RemoteEditText mEditText;
    private final ArrayList mEditTextFocusChangeListeners;
    private final TextView.OnEditorActionListener mEditorActionHandler;
    private NotificationEntry mEntry;
    private boolean mIsAnimatingAppearance;
    private int mLastBackgroundColor;
    private final ArrayList mOnSendListeners;
    private final ArrayList mOnVisibilityChangedListeners;
    private ProgressBar mProgressBar;
    private boolean mRemoved;
    private boolean mResetting;
    private ImageButton mSendButton;
    private boolean mSending;
    private ViewRootImpl mTestableViewRootImpl;
    private final SendButtonTextWatcher mTextWatcher;
    public final Object mToken;
    private final UiEventLogger mUiEventLogger;
    private RemoteInputViewController mViewController;
    private NotificationViewWrapper mWrapper;

    class EditorActionHandler implements TextView.OnEditorActionListener {
        private EditorActionHandler() {
        }

        @Override // android.widget.TextView.OnEditorActionListener
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            boolean z = keyEvent == null && (i == 6 || i == 5 || i == 4);
            boolean z2 = keyEvent != null && KeyEvent.isConfirmKey(keyEvent.getKeyCode()) && keyEvent.getAction() == 0;
            if (!z && !z2) {
                return false;
            }
            if (RemoteInputView.this.mEditText.length() > 0 || RemoteInputView.this.mEntry.remoteInputAttachment != null) {
                RemoteInputView.this.sendRemoteInput();
            }
            return true;
        }
    }

    enum NotificationRemoteInputEvent implements UiEventLogger.UiEventEnum {
        NOTIFICATION_REMOTE_INPUT_OPEN(795),
        NOTIFICATION_REMOTE_INPUT_CLOSE(796),
        NOTIFICATION_REMOTE_INPUT_SEND(797),
        NOTIFICATION_REMOTE_INPUT_FAILURE(798),
        NOTIFICATION_REMOTE_INPUT_ATTACH_IMAGE(825);

        private final int mId;

        NotificationRemoteInputEvent(int i) {
            this.mId = i;
        }

        public int getId() {
            return this.mId;
        }
    }

    public class RemoteEditText extends EditText {
        private InputMethodManager mInputMethodManager;
        private final OnBackInvokedCallback mOnBackInvokedCallback;
        private final OnReceiveContentListener mOnReceiveContentListener;
        private RemoteInputView mRemoteInputView;
        boolean mShowImeOnInputConnection;
        private final ArraySet mSupportedMimes;
        UserHandle mUser;

        public static /* synthetic */ boolean $r8$lambda$B9cyphaILp52hHbO5fjtX4j8Y6Y(ClipData.Item item) {
            return item.getUri() != null;
        }

        public RemoteEditText(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.mOnReceiveContentListener = new OnReceiveContentListener() { // from class: com.android.systemui.statusbar.policy.RemoteInputView$RemoteEditText$$ExternalSyntheticLambda0
                @Override // android.view.OnReceiveContentListener
                public final ContentInfo onReceiveContent(View view, ContentInfo contentInfo) {
                    return this.f$0.onReceiveContent(view, contentInfo);
                }
            };
            this.mSupportedMimes = new ArraySet();
            this.mOnBackInvokedCallback = new OnBackInvokedCallback() { // from class: com.android.systemui.statusbar.policy.RemoteInputView$RemoteEditText$$ExternalSyntheticLambda1
                @Override // android.window.OnBackInvokedCallback
                public final void onBackInvoked() {
                    this.f$0.lambda$new$0();
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void defocusIfNeeded(boolean z) {
            RemoteInputView remoteInputView;
            RemoteInputView remoteInputView2 = this.mRemoteInputView;
            if ((remoteInputView2 != null && remoteInputView2.mEntry.getRow().isChangingPosition()) || isTemporarilyDetached()) {
                if (!isTemporarilyDetached() || (remoteInputView = this.mRemoteInputView) == null) {
                    return;
                }
                remoteInputView.mEntry.remoteInputText = getText();
                return;
            }
            if (isFocusable() && isEnabled()) {
                setInnerFocusable(false);
                RemoteInputView remoteInputView3 = this.mRemoteInputView;
                if (remoteInputView3 != null) {
                    remoteInputView3.onDefocus(z, true, null);
                }
                this.mShowImeOnInputConnection = false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void hideIme() {
            Trace.beginSection("RemoteEditText#hideIme");
            WindowInsetsController windowInsetsController = getWindowInsetsController();
            if (windowInsetsController != null) {
                windowInsetsController.hide(WindowInsets.Type.ime());
            }
            Trace.endSection();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public ContentInfo onReceiveContent(View view, ContentInfo contentInfo) {
            Pair pairPartition = contentInfo.partition(new Predicate() { // from class: com.android.systemui.statusbar.policy.RemoteInputView$RemoteEditText$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return RemoteInputView.RemoteEditText.$r8$lambda$B9cyphaILp52hHbO5fjtX4j8Y6Y((ClipData.Item) obj);
                }
            });
            ContentInfo contentInfo2 = (ContentInfo) pairPartition.first;
            ContentInfo contentInfo3 = (ContentInfo) pairPartition.second;
            if (contentInfo2 != null) {
                this.mRemoteInputView.setAttachment(contentInfo2);
            }
            return contentInfo3;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: respondToKeycodeBack, reason: merged with bridge method [inline-methods] */
        public void lambda$new$0() {
            defocusIfNeeded(true);
        }

        @Override // android.widget.TextView, android.view.View
        public void getFocusedRect(Rect rect) {
            super.getFocusedRect(rect);
            int i = ((EditText) this).mScrollY;
            rect.top = i;
            rect.bottom = i + (((EditText) this).mBottom - ((EditText) this).mTop);
        }

        @Override // android.widget.TextView, android.view.View
        public boolean onCheckIsTextEditor() {
            RemoteInputView remoteInputView = this.mRemoteInputView;
            return (remoteInputView == null || !remoteInputView.mRemoved) && super.onCheckIsTextEditor();
        }

        @Override // android.widget.TextView
        public void onCommitCompletion(CompletionInfo completionInfo) {
            clearComposingText();
            setText(completionInfo.getText());
            setSelection(getText().length());
        }

        @Override // android.widget.TextView, android.view.View
        public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
            Context context;
            InputConnection inputConnectionOnCreateInputConnection = super.onCreateInputConnection(editorInfo);
            try {
                Context context2 = ((EditText) this).mContext;
                context = context2.createPackageContextAsUser(context2.getPackageName(), 0, this.mUser);
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("RemoteInput", "Unable to create user context:" + e.getMessage(), e);
                context = null;
            }
            if (this.mShowImeOnInputConnection && inputConnectionOnCreateInputConnection != null) {
                if (context == null) {
                    context = getContext();
                }
                InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(InputMethodManager.class);
                this.mInputMethodManager = inputMethodManager;
                if (inputMethodManager != null) {
                    post(new Runnable() { // from class: com.android.systemui.statusbar.policy.RemoteInputView.RemoteEditText.1
                        @Override // java.lang.Runnable
                        public void run() {
                            RemoteEditText.this.mInputMethodManager.viewClicked(RemoteEditText.this);
                            RemoteEditText.this.mInputMethodManager.showSoftInput(RemoteEditText.this, 0);
                        }
                    });
                }
            }
            return inputConnectionOnCreateInputConnection;
        }

        @Override // android.widget.TextView, android.view.View
        protected void onFocusChanged(boolean z, int i, Rect rect) {
            super.onFocusChanged(z, i, rect);
            RemoteInputView remoteInputView = this.mRemoteInputView;
            if (remoteInputView != null) {
                remoteInputView.onEditTextFocusChanged(this, z);
            }
            if (z) {
                return;
            }
            defocusIfNeeded(true);
        }

        @Override // android.widget.TextView, android.view.View, android.view.KeyEvent.Callback
        public boolean onKeyDown(int i, KeyEvent keyEvent) {
            if (i == 4) {
                return true;
            }
            return super.onKeyDown(i, keyEvent);
        }

        @Override // android.widget.TextView, android.view.View
        public boolean onKeyPreIme(int i, KeyEvent keyEvent) {
            if (keyEvent.getKeyCode() == 4 && keyEvent.getAction() == 1) {
                defocusIfNeeded(true);
            }
            return super.onKeyPreIme(i, keyEvent);
        }

        @Override // android.widget.TextView, android.view.View, android.view.KeyEvent.Callback
        public boolean onKeyUp(int i, KeyEvent keyEvent) {
            if (i != 4) {
                return super.onKeyUp(i, keyEvent);
            }
            lambda$new$0();
            return true;
        }

        @Override // android.widget.TextView, android.view.View
        protected void onVisibilityChanged(View view, int i) {
            super.onVisibilityChanged(view, i);
            if (isShown()) {
                return;
            }
            defocusIfNeeded(false);
        }

        @Override // android.view.View
        public boolean requestRectangleOnScreen(Rect rect) {
            return this.mRemoteInputView.requestScrollTo();
        }

        void setInnerFocusable(boolean z) {
            setFocusableInTouchMode(z);
            setFocusable(z);
            setCursorVisible(z);
            if (z) {
                requestFocus();
            }
        }

        void setSupportedMimeTypes(Collection collection) {
            String[] strArr;
            OnReceiveContentListener onReceiveContentListener;
            if (collection == null || collection.isEmpty()) {
                strArr = null;
                onReceiveContentListener = null;
            } else {
                strArr = (String[]) collection.toArray(new String[0]);
                onReceiveContentListener = this.mOnReceiveContentListener;
            }
            setOnReceiveContentListener(strArr, onReceiveContentListener);
            this.mSupportedMimes.clear();
            this.mSupportedMimes.addAll(collection);
        }
    }

    class SendButtonTextWatcher implements TextWatcher {
        private SendButtonTextWatcher() {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            RemoteInputView.this.updateSendButton();
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    public RemoteInputView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mToken = new Object();
        this.mOnSendListeners = new ArrayList();
        this.mOnVisibilityChangedListeners = new ArrayList();
        this.mEditTextFocusChangeListeners = new ArrayList();
        this.mIsAnimatingAppearance = false;
        this.mTextWatcher = new SendButtonTextWatcher();
        this.mEditorActionHandler = new EditorActionHandler();
        this.mUiEventLogger = (UiEventLogger) Dependency.get(UiEventLogger.class);
        this.mLastBackgroundColor = getContext().getColor(R.color.material_blue_grey_950);
    }

    private ColorStateList colorStateListWithDisabledAlpha(int i, int i2) {
        return new ColorStateList(new int[][]{new int[]{-16842910}, new int[0]}, new int[]{ColorUtils.setAlphaComponent(i, i2), i});
    }

    private static UserHandle computeTextOperationUser(UserHandle userHandle) {
        return UserHandle.ALL.equals(userHandle) ? UserHandle.of(ActivityManager.getCurrentUser()) : userHandle;
    }

    private Animator getDefocusAnimator(View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        Property property = View.ALPHA;
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, property, 1.0f, 0.0f);
        objectAnimatorOfFloat.setDuration(83L);
        objectAnimatorOfFloat.setStartDelay(120L);
        Interpolator interpolator = InterpolatorsAndroidX.LINEAR;
        objectAnimatorOfFloat.setInterpolator(interpolator);
        final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, 0.5f);
        valueAnimatorOfFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.policy.RemoteInputView$$ExternalSyntheticLambda1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                this.f$0.lambda$getDefocusAnimator$3(valueAnimatorOfFloat, animator);
            }
        });
        valueAnimatorOfFloat.setDuration(360L);
        valueAnimatorOfFloat.setInterpolator(InterpolatorsAndroidX.FAST_OUT_SLOW_IN);
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.policy.RemoteInputView.5
            @Override // androidx.core.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator, boolean z) {
                RemoteInputView.this.setFocusAnimationScaleY(1.0f);
            }
        });
        if (view == null) {
            animatorSet.playTogether(objectAnimatorOfFloat, valueAnimatorOfFloat);
            return animatorSet;
        }
        view.forceHasOverlappingRendering(false);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view, property, 0.0f, 1.0f);
        objectAnimatorOfFloat2.setDuration(83L);
        objectAnimatorOfFloat2.setInterpolator(interpolator);
        objectAnimatorOfFloat2.setStartDelay(180L);
        animatorSet.playTogether(objectAnimatorOfFloat, valueAnimatorOfFloat, objectAnimatorOfFloat2);
        return animatorSet;
    }

    private Animator getFocusAnimator(final View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        Property property = View.ALPHA;
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, property, 0.0f, 1.0f);
        objectAnimatorOfFloat.setStartDelay(33L);
        objectAnimatorOfFloat.setDuration(83L);
        Interpolator interpolator = InterpolatorsAndroidX.LINEAR;
        objectAnimatorOfFloat.setInterpolator(interpolator);
        final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.5f, 1.0f);
        valueAnimatorOfFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.policy.RemoteInputView$$ExternalSyntheticLambda3
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                this.f$0.lambda$getFocusAnimator$2(valueAnimatorOfFloat, animator);
            }
        });
        valueAnimatorOfFloat.setDuration(360L);
        valueAnimatorOfFloat.setInterpolator(InterpolatorsAndroidX.FAST_OUT_SLOW_IN);
        if (view == null) {
            animatorSet.playTogether(objectAnimatorOfFloat, valueAnimatorOfFloat);
            return animatorSet;
        }
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view, property, 1.0f, 0.0f);
        objectAnimatorOfFloat2.setDuration(50L);
        objectAnimatorOfFloat2.setInterpolator(interpolator);
        animatorSet.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.statusbar.policy.RemoteInputView.4
            @Override // androidx.core.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator, boolean z) {
                view.setAlpha(1.0f);
            }
        });
        animatorSet.playTogether(objectAnimatorOfFloat, valueAnimatorOfFloat, objectAnimatorOfFloat2);
        return animatorSet;
    }

    public static RemoteInputView inflate(Context context, ViewGroup viewGroup, NotificationEntry notificationEntry, RemoteInputController remoteInputController) {
        RemoteInputView remoteInputView = (RemoteInputView) LayoutInflater.from(context).inflate(R$layout.remote_input, viewGroup, false);
        remoteInputView.mController = remoteInputController;
        remoteInputView.mEntry = notificationEntry;
        UserHandle userHandleComputeTextOperationUser = computeTextOperationUser(notificationEntry.getSbn().getUser());
        RemoteEditText remoteEditText = remoteInputView.mEditText;
        remoteEditText.mUser = userHandleComputeTextOperationUser;
        remoteEditText.setTextOperationUser(userHandleComputeTextOperationUser);
        remoteInputView.setTag(VIEW_TAG);
        return remoteInputView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDefocusAnimator$3(ValueAnimator valueAnimator, Animator animator) {
        setFocusAnimationScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getFocusAnimator$2(ValueAnimator valueAnimator, Animator animator) {
        setFocusAnimationScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFinishInflate$0(View view) {
        setAttachment(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reset$1() {
        this.mEntry.remoteInputTextWhenReset = SpannedString.valueOf(this.mEditText.getText());
        this.mEditText.getText().clear();
        this.mEditText.setEnabled(isAggregatedVisible());
        this.mSendButton.setVisibility(0);
        updateSendButton();
        setAttachment(null);
        this.mResetting = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onEditTextFocusChanged(RemoteEditText remoteEditText, boolean z) {
        ArrayList arrayList = new ArrayList(this.mEditTextFocusChangeListeners);
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((View.OnFocusChangeListener) obj).onFocusChange(remoteEditText, z);
        }
    }

    private void registerBackCallback() {
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl == null) {
            return;
        }
        viewRootImpl.getOnBackInvokedDispatcher().registerOnBackInvokedCallback(1000000, this.mEditText.mOnBackInvokedCallback);
    }

    private void reset() {
        this.mProgressBar.setVisibility(4);
        this.mResetting = true;
        this.mSending = false;
        this.mController.removeSpinning(this.mEntry.getKey(), this.mToken);
        onDefocus(true, false, new Runnable() { // from class: com.android.systemui.statusbar.policy.RemoteInputView$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$reset$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRemoteInput() {
        ArrayList arrayList = new ArrayList(this.mOnSendListeners);
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((Runnable) obj).run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFocusAnimationScaleY(float f) {
        int height = (int) ((1.0f - f) * 0.5f * this.mContentView.getHeight());
        Rect rect = new Rect(0, height, this.mContentView.getWidth(), this.mContentView.getHeight() - height);
        this.mContentBackground.setBounds(rect);
        this.mContentView.setBackground(this.mContentBackground);
        if (f == 1.0f) {
            this.mContentBackgroundBounds = null;
        } else {
            this.mContentBackgroundBounds = rect;
        }
        setTranslationY(height);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTopMargin(int i) {
        if (getLayoutParams() instanceof FrameLayout.LayoutParams) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
            layoutParams.topMargin = i;
            setLayoutParams(layoutParams);
        }
    }

    private void unregisterBackCallback() {
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl == null) {
            return;
        }
        viewRootImpl.getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(this.mEditText.mOnBackInvokedCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSendButton() {
        this.mSendButton.setEnabled((this.mEditText.length() == 0 && this.mEntry.remoteInputAttachment == null) ? false : true);
    }

    public void addOnEditTextFocusChangedListener(View.OnFocusChangeListener onFocusChangeListener) {
        this.mEditTextFocusChangeListeners.add(onFocusChangeListener);
    }

    public void addOnSendRemoteInputListener(Runnable runnable) {
        this.mOnSendListeners.add(runnable);
    }

    public void addOnVisibilityChangedListener(Consumer consumer) {
        this.mOnVisibilityChangedListeners.add(consumer);
    }

    public void clearAttachment() {
        setAttachment(null);
    }

    public void close() {
        this.mEditText.defocusIfNeeded(false);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchFinishTemporaryDetach() {
        if (isAttachedToWindow()) {
            RemoteEditText remoteEditText = this.mEditText;
            attachViewToParent(remoteEditText, 0, remoteEditText.getLayoutParams());
        } else {
            removeDetachedView(this.mEditText, false);
        }
        super.dispatchFinishTemporaryDetach();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchStartTemporaryDetach() {
        super.dispatchStartTemporaryDetach();
        int iIndexOfChild = indexOfChild(this.mEditText);
        if (iIndexOfChild != -1) {
            detachViewFromParent(iIndexOfChild);
        }
    }

    public void focus() {
        this.mUiEventLogger.logWithInstanceId(NotificationRemoteInputEvent.NOTIFICATION_REMOTE_INPUT_OPEN, this.mEntry.getSbn().getUid(), this.mEntry.getSbn().getPackageName(), this.mEntry.getSbn().getInstanceId());
        setVisibility(0);
        NotificationViewWrapper notificationViewWrapper = this.mWrapper;
        if (notificationViewWrapper != null) {
            notificationViewWrapper.setRemoteInputVisible(true);
        }
        this.mEditText.setInnerFocusable(true);
        RemoteEditText remoteEditText = this.mEditText;
        remoteEditText.mShowImeOnInputConnection = true;
        remoteEditText.setText(this.mEntry.remoteInputText);
        RemoteEditText remoteEditText2 = this.mEditText;
        remoteEditText2.setSelection(remoteEditText2.length());
        this.mEditText.requestFocus();
        this.mController.addRemoteInput(this.mEntry, this.mToken, "RemoteInputView#focus");
        setAttachment(this.mEntry.remoteInputAttachment);
        updateSendButton();
    }

    public void focusAnimated() {
        if (getVisibility() != 0) {
            this.mIsAnimatingAppearance = true;
            setAlpha(0.0f);
            Animator focusAnimator = getFocusAnimator(getActionsContainerLayout());
            focusAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.policy.RemoteInputView.3
                @Override // androidx.core.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator, boolean z) {
                    RemoteInputView.this.mIsAnimatingAppearance = false;
                }
            });
            focusAnimator.start();
        }
        focus();
    }

    public View getActionsContainerLayout() {
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (viewGroup == null) {
            return null;
        }
        return viewGroup.findViewById(R.id.actions_container_layout);
    }

    public RemoteInputViewController getController() {
        return this.mViewController;
    }

    public CharSequence getText() {
        return this.mEditText.getText();
    }

    public ViewRootImpl getViewRootImpl() {
        ViewRootImpl viewRootImpl = this.mTestableViewRootImpl;
        return viewRootImpl != null ? viewRootImpl : super.getViewRootImpl();
    }

    public void hideIme() {
        this.mEditText.hideIme();
    }

    public boolean isActive() {
        return this.mEditText.isFocused() && this.mEditText.isEnabled();
    }

    public boolean isAnimatingAppearance() {
        return this.mIsAnimatingAppearance;
    }

    public boolean isSending() {
        return getVisibility() == 0 && this.mController.isSpinning(this.mEntry.getKey(), this.mToken);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setEditTextReferenceToSelf();
        this.mEditText.setOnEditorActionListener(this.mEditorActionHandler);
        this.mEditText.addTextChangedListener(this.mTextWatcher);
        if (this.mEntry.getRow().isChangingPosition() && getVisibility() == 0 && this.mEditText.isFocusable()) {
            this.mEditText.requestFocus();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view == this.mSendButton) {
            sendRemoteInput();
        }
    }

    void onDefocus(boolean z, boolean z2, final Runnable runnable) {
        this.mController.removeRemoteInput(this.mEntry, this.mToken, "RemoteInputView#onDefocus");
        this.mEntry.remoteInputText = this.mEditText.getText();
        if (!this.mRemoved) {
            ViewGroup viewGroup = (ViewGroup) getParent();
            if (!z || viewGroup == null) {
                setVisibility(8);
                if (runnable != null) {
                    runnable.run();
                }
                NotificationViewWrapper notificationViewWrapper = this.mWrapper;
                if (notificationViewWrapper != null) {
                    notificationViewWrapper.setRemoteInputVisible(false);
                }
            } else {
                final ViewGroup viewGroup2 = (ViewGroup) viewGroup.getParent();
                View actionsContainerLayout = getActionsContainerLayout();
                setTopMargin((actionsContainerLayout != null ? actionsContainerLayout.getHeight() : 0) - getHeight());
                if (viewGroup2 != null) {
                    viewGroup2.setClipChildren(false);
                }
                Animator defocusAnimator = getDefocusAnimator(actionsContainerLayout);
                defocusAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.policy.RemoteInputView.2
                    @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        RemoteInputView.this.setTopMargin(0);
                        ViewGroup viewGroup3 = viewGroup2;
                        if (viewGroup3 != null) {
                            viewGroup3.setClipChildren(true);
                        }
                        RemoteInputView.this.setVisibility(8);
                        RemoteInputView.this.setAlpha(1.0f);
                        if (RemoteInputView.this.mWrapper != null) {
                            RemoteInputView.this.mWrapper.setRemoteInputVisible(false);
                        }
                        Runnable runnable2 = runnable;
                        if (runnable2 != null) {
                            runnable2.run();
                        }
                    }
                });
                if (actionsContainerLayout != null) {
                    actionsContainerLayout.setAlpha(0.0f);
                }
                defocusAnimator.start();
            }
        }
        if (z2) {
            this.mUiEventLogger.logWithInstanceId(NotificationRemoteInputEvent.NOTIFICATION_REMOTE_INPUT_CLOSE, this.mEntry.getSbn().getUid(), this.mEntry.getSbn().getPackageName(), this.mEntry.getSbn().getInstanceId());
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mEditText.removeTextChangedListener(this.mTextWatcher);
        this.mEditText.setOnEditorActionListener(null);
        this.mEditText.mRemoteInputView = null;
        if (this.mEntry.getRow().isChangingPosition() || isTemporarilyDetached()) {
            return;
        }
        NotificationEntry notificationEntry = this.mEntry;
        notificationEntry.mRemoteEditImeAnimatingAway = false;
        this.mController.removeRemoteInput(notificationEntry, this.mToken, "RemoteInputView#onDetachedFromWindow");
        this.mController.removeSpinning(this.mEntry.getKey(), this.mToken);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mProgressBar = (ProgressBar) findViewById(R$id.remote_input_progress);
        ImageButton imageButton = (ImageButton) findViewById(R$id.remote_input_send);
        this.mSendButton = imageButton;
        imageButton.setOnClickListener(this);
        this.mContentBackground = (GradientDrawable) ((LinearLayout) this).mContext.getDrawable(R$drawable.remote_input_view_text_bg).mutate();
        this.mDelete = (ImageView) findViewById(R$id.remote_input_delete);
        ImageView imageView = (ImageView) findViewById(R$id.remote_input_delete_bg);
        this.mDeleteBg = imageView;
        BlendMode blendMode = BlendMode.SRC_IN;
        imageView.setImageTintBlendMode(blendMode);
        this.mDelete.setImageTintBlendMode(blendMode);
        this.mDelete.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.policy.RemoteInputView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onFinishInflate$0(view);
            }
        });
        LinearLayout linearLayout = (LinearLayout) findViewById(R$id.remote_input_content);
        this.mContentView = linearLayout;
        linearLayout.setBackground(this.mContentBackground);
        RemoteEditText remoteEditText = (RemoteEditText) findViewById(R$id.remote_input_text);
        this.mEditText = remoteEditText;
        remoteEditText.setInnerFocusable(false);
        this.mEditText.setEnabled(false);
        this.mEditText.setWindowInsetsAnimationCallback(new WindowInsetsAnimation.Callback(0) { // from class: com.android.systemui.statusbar.policy.RemoteInputView.1
            @Override // android.view.WindowInsetsAnimation.Callback
            public void onEnd(WindowInsetsAnimation windowInsetsAnimation) {
                super.onEnd(windowInsetsAnimation);
                if (windowInsetsAnimation.getTypeMask() == WindowInsets.Type.ime()) {
                    boolean z = false;
                    RemoteInputView.this.mEntry.mRemoteEditImeAnimatingAway = false;
                    WindowInsets rootWindowInsets = RemoteInputView.this.mEditText.getRootWindowInsets();
                    if (rootWindowInsets == null) {
                        Log.w("RemoteInput", "onEnd called on detached view", new Exception());
                    }
                    NotificationEntry notificationEntry = RemoteInputView.this.mEntry;
                    if (rootWindowInsets != null && rootWindowInsets.isVisible(WindowInsets.Type.ime())) {
                        z = true;
                    }
                    notificationEntry.mRemoteEditImeVisible = z;
                    if (RemoteInputView.this.mEntry.mRemoteEditImeVisible || RemoteInputView.this.mEditText.mShowImeOnInputConnection) {
                        return;
                    }
                    RemoteInputView.this.mController.removeRemoteInput(RemoteInputView.this.mEntry, RemoteInputView.this.mToken, "RemoteInputView$WindowInsetAnimation#onEnd");
                }
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public WindowInsets onProgress(WindowInsets windowInsets, List list) {
                return windowInsets;
            }
        });
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            this.mController.requestDisallowLongPressAndDismiss();
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        setPivotY(getMeasuredHeight());
        Rect rect = this.mContentBackgroundBounds;
        if (rect != null) {
            this.mContentBackground.setBounds(rect);
        }
    }

    public void onNotificationUpdateOrReset() {
        NotificationViewWrapper notificationViewWrapper;
        if (this.mProgressBar.getVisibility() == 0) {
            reset();
        }
        if (!isActive() || (notificationViewWrapper = this.mWrapper) == null) {
            return;
        }
        notificationViewWrapper.setRemoteInputVisible(true);
    }

    @Override // android.view.ViewGroup
    public boolean onRequestSendAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        if (this.mResetting && view == this.mEditText) {
            return false;
        }
        return super.onRequestSendAccessibilityEvent(view, accessibilityEvent);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    @Override // android.view.View
    public void onVisibilityAggregated(boolean z) {
        if (z) {
            registerBackCallback();
        } else {
            unregisterBackCallback();
        }
        super.onVisibilityAggregated(z);
        this.mEditText.setEnabled(z && !this.mSending);
    }

    @Override // android.view.View
    protected void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (view == this) {
            ArrayList arrayList = new ArrayList(this.mOnVisibilityChangedListeners);
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                ((Consumer) obj).accept(Boolean.valueOf(i == 0));
            }
            if (i == 0 || this.mController.isRemoteInputActive()) {
                return;
            }
            this.mEditText.hideIme();
        }
    }

    public void removeOnEditTextFocusChangedListener(View.OnFocusChangeListener onFocusChangeListener) {
        this.mEditTextFocusChangeListeners.remove(onFocusChangeListener);
    }

    public void removeOnSendRemoteInputListener(Runnable runnable) {
        this.mOnSendListeners.remove(runnable);
    }

    public boolean requestScrollTo() {
        this.mController.lockScrollTo(this.mEntry);
        return true;
    }

    protected void setAttachment(ContentInfo contentInfo) {
        ContentInfo contentInfo2 = this.mEntry.remoteInputAttachment;
        if (contentInfo2 != null && contentInfo2 != contentInfo) {
            contentInfo2.releasePermissions();
        }
        NotificationEntry notificationEntry = this.mEntry;
        notificationEntry.remoteInputAttachment = contentInfo;
        if (contentInfo != null) {
            notificationEntry.remoteInputUri = contentInfo.getClip().getItemAt(0).getUri();
            this.mEntry.remoteInputMimeType = contentInfo.getClip().getDescription().getMimeType(0);
        }
        View viewFindViewById = findViewById(R$id.remote_input_content_container);
        ImageView imageView = (ImageView) findViewById(R$id.remote_input_attachment_image);
        imageView.setImageDrawable(null);
        if (contentInfo == null) {
            viewFindViewById.setVisibility(8);
            return;
        }
        imageView.setImageURI(contentInfo.getClip().getItemAt(0).getUri());
        if (imageView.getDrawable() == null) {
            viewFindViewById.setVisibility(8);
        } else {
            viewFindViewById.setVisibility(0);
            this.mUiEventLogger.logWithInstanceId(NotificationRemoteInputEvent.NOTIFICATION_REMOTE_INPUT_ATTACH_IMAGE, this.mEntry.getSbn().getUid(), this.mEntry.getSbn().getPackageName(), this.mEntry.getSbn().getInstanceId());
        }
        updateSendButton();
    }

    public void setBackgroundTintColor(int i, boolean z) {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        int color;
        int defaultColor;
        int color2;
        int color3;
        if (z == this.mColorized && i == this.mLastBackgroundColor) {
            return;
        }
        this.mColorized = z;
        this.mLastBackgroundColor = i;
        int dimensionPixelSize = z ? ((LinearLayout) this).mContext.getResources().getDimensionPixelSize(R$dimen.remote_input_view_text_stroke) : 0;
        if (z) {
            boolean zIsColorDark = ContrastColorUtil.isColorDark(i);
            defaultColor = DarkIconDispatcher.DEFAULT_INVERSE_ICON_TINT;
            int i2 = zIsColorDark ? -1 : -16777216;
            if (!zIsColorDark) {
                defaultColor = -1;
            }
            colorStateList = colorStateListWithDisabledAlpha(i2, 77);
            colorStateList2 = colorStateListWithDisabledAlpha(i2, 153);
            color = ColorUtils.setAlphaComponent(i2, 153);
            color3 = i2;
            color2 = i;
        } else {
            colorStateList = ((LinearLayout) this).mContext.getColorStateList(R$color.remote_input_send);
            colorStateList2 = ((LinearLayout) this).mContext.getColorStateList(R$color.remote_input_text);
            color = ((LinearLayout) this).mContext.getColor(R$color.remote_input_hint);
            defaultColor = colorStateList2.getDefaultColor();
            color2 = getContext().getColor(R.color.material_blue_grey_950);
            color3 = getContext().getColor(R.color.material_deep_teal_300);
        }
        this.mEditText.setTextColor(colorStateList2);
        this.mEditText.setHintTextColor(color);
        if (this.mEditText.getTextCursorDrawable() != null) {
            this.mEditText.getTextCursorDrawable().setColorFilter(colorStateList.getDefaultColor(), PorterDuff.Mode.SRC_IN);
        }
        this.mContentBackground.setColor(color2);
        this.mContentBackground.setStroke(dimensionPixelSize, colorStateList);
        this.mDelete.setImageTintList(ColorStateList.valueOf(defaultColor));
        this.mDeleteBg.setImageTintList(ColorStateList.valueOf(color3));
        this.mSendButton.setImageTintList(colorStateList);
        this.mProgressBar.setProgressTintList(colorStateList);
        this.mProgressBar.setIndeterminateTintList(colorStateList);
        this.mProgressBar.setSecondaryProgressTintList(colorStateList);
        setBackgroundColor(i);
    }

    public void setController(RemoteInputViewController remoteInputViewController) {
        this.mViewController = remoteInputViewController;
    }

    public void setEditTextContent(CharSequence charSequence) {
        this.mEditText.setText(charSequence);
    }

    protected void setEditTextReferenceToSelf() {
        this.mEditText.mRemoteInputView = this;
    }

    public void setHintText(CharSequence charSequence) {
        this.mEditText.setHint(charSequence);
    }

    public void setRemoved() {
        this.mRemoved = true;
    }

    public void setSupportedMimeTypes(Collection collection) {
        this.mEditText.setSupportedMimeTypes(collection);
    }

    protected void setViewRootImpl(ViewRootImpl viewRootImpl) {
        this.mTestableViewRootImpl = viewRootImpl;
    }

    public void setWrapper(NotificationViewWrapper notificationViewWrapper) {
        this.mWrapper = notificationViewWrapper;
    }

    public void startSending() {
        this.mEditText.setEnabled(false);
        this.mSending = true;
        this.mSendButton.setVisibility(4);
        this.mProgressBar.setVisibility(0);
        this.mEditText.mShowImeOnInputConnection = false;
    }
}
