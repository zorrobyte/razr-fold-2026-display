package com.android.systemui.statusbar.notification;

import android.text.Layout;
import android.util.Pools;
import android.view.View;
import android.widget.TextView;
import com.android.app.animation.Interpolators;
import com.android.internal.widget.IMessagingLayout;
import com.android.internal.widget.MessagingGroup;
import com.android.internal.widget.MessagingImageMessage;
import com.android.internal.widget.MessagingLinearLayout;
import com.android.internal.widget.MessagingPropertyAnimator;
import com.android.systemui.statusbar.notification.TransformState;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class MessagingLayoutTransformState extends TransformState {
    private static Pools.SimplePool sInstancePool = new Pools.SimplePool(40);
    private HashMap mGroupMap = new HashMap();
    private MessagingLinearLayout mMessageContainer;
    private IMessagingLayout mMessagingLayout;
    private float mRelativeTranslationOffset;

    private void adaptGroupAppear(MessagingGroup messagingGroup, float f, float f2, boolean z) {
        float f3 = z ? f * this.mRelativeTranslationOffset : (1.0f - f) * this.mRelativeTranslationOffset;
        if (messagingGroup.getSenderView().getVisibility() != 8) {
            f3 *= 0.5f;
        }
        messagingGroup.getMessageContainer().setTranslationY(f3);
        messagingGroup.getSenderView().setTranslationY(f3);
        messagingGroup.setTranslationY(f2 * 0.9f);
    }

    private void appear(View view, float f) {
        if (view == null || view.getVisibility() == 8) {
            return;
        }
        TransformState transformStateCreateFrom = TransformState.createFrom(view, this.mTransformInfo);
        transformStateCreateFrom.appear(f, null);
        transformStateCreateFrom.recycle();
    }

    private void appear(MessagingGroup messagingGroup, float f) {
        MessagingLinearLayout messageContainer = messagingGroup.getMessageContainer();
        for (int i = 0; i < messageContainer.getChildCount(); i++) {
            View childAt = messageContainer.getChildAt(i);
            if (!isGone(childAt)) {
                appear(childAt, f);
                setClippingDeactivated(childAt, true);
            }
        }
        appear(messagingGroup.getAvatar(), f);
        appear(messagingGroup.getSenderView(), f);
        appear((View) messagingGroup.getIsolatedMessage(), f);
        setClippingDeactivated(messagingGroup.getSenderView(), true);
        setClippingDeactivated(messagingGroup.getAvatar(), true);
    }

    private void disappear(View view, float f) {
        if (view == null || view.getVisibility() == 8) {
            return;
        }
        TransformState transformStateCreateFrom = TransformState.createFrom(view, this.mTransformInfo);
        transformStateCreateFrom.disappear(f, null);
        transformStateCreateFrom.recycle();
    }

    private void disappear(MessagingGroup messagingGroup, float f) {
        MessagingLinearLayout messageContainer = messagingGroup.getMessageContainer();
        for (int i = 0; i < messageContainer.getChildCount(); i++) {
            View childAt = messageContainer.getChildAt(i);
            if (!isGone(childAt)) {
                disappear(childAt, f);
                setClippingDeactivated(childAt, true);
            }
        }
        disappear(messagingGroup.getAvatar(), f);
        disappear(messagingGroup.getSenderView(), f);
        disappear((View) messagingGroup.getIsolatedMessage(), f);
        setClippingDeactivated(messagingGroup.getSenderView(), true);
        setClippingDeactivated(messagingGroup.getAvatar(), true);
    }

    private ArrayList filterHiddenGroups(ArrayList arrayList) {
        ArrayList arrayList2 = new ArrayList(arrayList);
        int i = 0;
        while (i < arrayList2.size()) {
            if (isGone((MessagingGroup) arrayList2.get(i))) {
                arrayList2.remove(i);
                i--;
            }
            i++;
        }
        return arrayList2;
    }

    private HashMap findPairs(ArrayList arrayList, ArrayList arrayList2) {
        this.mGroupMap.clear();
        int i = Integer.MAX_VALUE;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            MessagingGroup messagingGroup = (MessagingGroup) arrayList.get(size);
            MessagingGroup messagingGroup2 = null;
            int i2 = 0;
            for (int iMin = Math.min(arrayList2.size(), i) - 1; iMin >= 0; iMin--) {
                MessagingGroup messagingGroup3 = (MessagingGroup) arrayList2.get(iMin);
                int iCalculateGroupCompatibility = messagingGroup.calculateGroupCompatibility(messagingGroup3);
                if (iCalculateGroupCompatibility > i2) {
                    i = iMin;
                    messagingGroup2 = messagingGroup3;
                    i2 = iCalculateGroupCompatibility;
                }
            }
            if (messagingGroup2 != null) {
                this.mGroupMap.put(messagingGroup, messagingGroup2);
            }
        }
        return this.mGroupMap;
    }

    private boolean hasEllipses(TextView textView) {
        Layout layout = textView.getLayout();
        return layout != null && layout.getEllipsisCount(layout.getLineCount() - 1) > 0;
    }

    private boolean isGone(View view) {
        if (view == null || view.getVisibility() == 8 || view.getParent() == null || view.getWidth() == 0) {
            return true;
        }
        MessagingLinearLayout.LayoutParams layoutParams = view.getLayoutParams();
        return (layoutParams instanceof MessagingLinearLayout.LayoutParams) && layoutParams.hide;
    }

    private boolean needsReflow(TextView textView, TextView textView2) {
        return hasEllipses(textView) != hasEllipses(textView2);
    }

    public static MessagingLayoutTransformState obtain() {
        MessagingLayoutTransformState messagingLayoutTransformState = (MessagingLayoutTransformState) sInstancePool.acquire();
        return messagingLayoutTransformState != null ? messagingLayoutTransformState : new MessagingLayoutTransformState();
    }

    private void resetTransformedView(View view) {
        TransformState transformStateCreateFrom = TransformState.createFrom(view, this.mTransformInfo);
        transformStateCreateFrom.resetTransformedView();
        transformStateCreateFrom.recycle();
    }

    private void setVisible(View view, boolean z, boolean z2) {
        if (isGone(view) || MessagingPropertyAnimator.isAnimatingAlpha(view)) {
            return;
        }
        TransformState transformStateCreateFrom = TransformState.createFrom(view, this.mTransformInfo);
        transformStateCreateFrom.setVisible(z, z2);
        transformStateCreateFrom.recycle();
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x007f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int transformGroups(com.android.internal.widget.MessagingGroup r18, com.android.internal.widget.MessagingGroup r19, float r20, boolean r21) {
        /*
            Method dump skipped, instruction units count: 262
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.MessagingLayoutTransformState.transformGroups(com.android.internal.widget.MessagingGroup, com.android.internal.widget.MessagingGroup, float, boolean):int");
    }

    private int transformView(float f, boolean z, View view, View view2, boolean z2, boolean z3) {
        TransformState transformStateCreateFrom = TransformState.createFrom(view, this.mTransformInfo);
        if (z3) {
            transformStateCreateFrom.setDefaultInterpolator(Interpolators.LINEAR);
        }
        int i = 0;
        transformStateCreateFrom.setIsSameAsAnyView(z2 && !isGone(view2));
        if (z) {
            if (view2 != null) {
                TransformState transformStateCreateFrom2 = TransformState.createFrom(view2, this.mTransformInfo);
                if (isGone(view2)) {
                    if (!isGone(view)) {
                        transformStateCreateFrom.disappear(f, null);
                    }
                    transformStateCreateFrom.transformViewVerticalTo(transformStateCreateFrom2, f);
                } else {
                    transformStateCreateFrom.transformViewTo(transformStateCreateFrom2, f);
                }
                i = transformStateCreateFrom.getLaidOutLocationOnScreen()[1] - transformStateCreateFrom2.getLaidOutLocationOnScreen()[1];
                transformStateCreateFrom2.recycle();
            } else {
                transformStateCreateFrom.disappear(f, null);
            }
        } else if (view2 != null) {
            TransformState transformStateCreateFrom3 = TransformState.createFrom(view2, this.mTransformInfo);
            if (isGone(view2)) {
                if (!isGone(view)) {
                    transformStateCreateFrom.appear(f, null);
                }
                transformStateCreateFrom.transformViewVerticalFrom(transformStateCreateFrom3, f);
            } else {
                transformStateCreateFrom.transformViewFrom(transformStateCreateFrom3, f);
            }
            i = transformStateCreateFrom.getLaidOutLocationOnScreen()[1] - transformStateCreateFrom3.getLaidOutLocationOnScreen()[1];
            transformStateCreateFrom3.recycle();
        } else {
            transformStateCreateFrom.appear(f, null);
        }
        transformStateCreateFrom.recycle();
        return i;
    }

    private void transformViewInternal(MessagingLayoutTransformState messagingLayoutTransformState, float f, boolean z) {
        float fMax;
        float f2;
        float fAbs;
        ensureVisible();
        ArrayList arrayListFilterHiddenGroups = filterHiddenGroups(this.mMessagingLayout.getMessagingGroups());
        HashMap mapFindPairs = findPairs(arrayListFilterHiddenGroups, filterHiddenGroups(messagingLayoutTransformState.mMessagingLayout.getMessagingGroups()));
        MessagingGroup messagingGroup = null;
        float translationY = 0.0f;
        for (int size = arrayListFilterHiddenGroups.size() - 1; size >= 0; size--) {
            MessagingGroup messagingGroup2 = (MessagingGroup) arrayListFilterHiddenGroups.get(size);
            MessagingGroup messagingGroup3 = (MessagingGroup) mapFindPairs.get(messagingGroup2);
            if (!isGone(messagingGroup2)) {
                if (messagingGroup3 != null) {
                    int iTransformGroups = transformGroups(messagingGroup2, messagingGroup3, f, z);
                    if (messagingGroup == null) {
                        translationY = z ? messagingGroup3.getAvatar().getTranslationY() - iTransformGroups : messagingGroup2.getAvatar().getTranslationY();
                        messagingGroup = messagingGroup2;
                    }
                } else {
                    if (messagingGroup != null) {
                        adaptGroupAppear(messagingGroup2, f, translationY, z);
                        float top = messagingGroup2.getTop() + translationY;
                        if (this.mTransformInfo.isAnimating()) {
                            float f3 = (-messagingGroup2.getHeight()) * 0.75f;
                            f2 = top - f3;
                            fAbs = Math.abs(f3) + messagingGroup2.getTop();
                        } else {
                            float f4 = (-messagingGroup2.getHeight()) * 0.5f;
                            f2 = top - f4;
                            fAbs = Math.abs(f4);
                        }
                        fMax = Math.max(0.0f, Math.min(1.0f, f2 / fAbs));
                        if (z) {
                            fMax = 1.0f - fMax;
                        }
                    } else {
                        fMax = f;
                    }
                    if (z) {
                        disappear(messagingGroup2, fMax);
                    } else {
                        appear(messagingGroup2, fMax);
                    }
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public void initFrom(View view, TransformState.TransformInfo transformInfo) {
        super.initFrom(view, transformInfo);
        MessagingLinearLayout messagingLinearLayout = this.mTransformedView;
        if (messagingLinearLayout instanceof MessagingLinearLayout) {
            MessagingLinearLayout messagingLinearLayout2 = messagingLinearLayout;
            this.mMessageContainer = messagingLinearLayout2;
            this.mMessagingLayout = messagingLinearLayout2.getMessagingLayout();
            this.mRelativeTranslationOffset = view.getContext().getResources().getDisplayMetrics().density * 8.0f;
        }
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public void prepareFadeIn() {
        super.prepareFadeIn();
        setVisible(true, false);
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public void recycle() {
        super.recycle();
        this.mGroupMap.clear();
        sInstancePool.release(this);
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    protected void reset() {
        super.reset();
        this.mMessageContainer = null;
        this.mMessagingLayout = null;
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    protected void resetTransformedView() {
        super.resetTransformedView();
        ArrayList messagingGroups = this.mMessagingLayout.getMessagingGroups();
        for (int i = 0; i < messagingGroups.size(); i++) {
            MessagingGroup messagingGroup = (MessagingGroup) messagingGroups.get(i);
            if (!isGone(messagingGroup)) {
                MessagingLinearLayout messageContainer = messagingGroup.getMessageContainer();
                for (int i2 = 0; i2 < messageContainer.getChildCount(); i2++) {
                    View childAt = messageContainer.getChildAt(i2);
                    if (!isGone(childAt)) {
                        resetTransformedView(childAt);
                        setClippingDeactivated(childAt, false);
                    }
                }
                resetTransformedView(messagingGroup.getAvatar());
                resetTransformedView(messagingGroup.getSenderView());
                MessagingImageMessage isolatedMessage = messagingGroup.getIsolatedMessage();
                if (isolatedMessage != null) {
                    resetTransformedView(isolatedMessage);
                }
                setClippingDeactivated(messagingGroup.getAvatar(), false);
                setClippingDeactivated(messagingGroup.getSenderView(), false);
                messagingGroup.setTranslationY(0.0f);
                messagingGroup.getMessageContainer().setTranslationY(0.0f);
                messagingGroup.getSenderView().setTranslationY(0.0f);
            }
            messagingGroup.setClippingDisabled(false);
            messagingGroup.updateClipRect();
        }
        this.mMessagingLayout.setMessagingClippingDisabled(false);
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public void setVisible(boolean z, boolean z2) {
        super.setVisible(z, z2);
        resetTransformedView();
        ArrayList messagingGroups = this.mMessagingLayout.getMessagingGroups();
        for (int i = 0; i < messagingGroups.size(); i++) {
            MessagingGroup messagingGroup = (MessagingGroup) messagingGroups.get(i);
            if (!isGone(messagingGroup)) {
                MessagingLinearLayout messageContainer = messagingGroup.getMessageContainer();
                for (int i2 = 0; i2 < messageContainer.getChildCount(); i2++) {
                    setVisible(messageContainer.getChildAt(i2), z, z2);
                }
                setVisible(messagingGroup.getAvatar(), z, z2);
                setVisible(messagingGroup.getSenderView(), z, z2);
                MessagingImageMessage isolatedMessage = messagingGroup.getIsolatedMessage();
                if (isolatedMessage != null) {
                    setVisible(isolatedMessage, z, z2);
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public void transformViewFrom(TransformState transformState, float f) {
        if (transformState instanceof MessagingLayoutTransformState) {
            transformViewInternal((MessagingLayoutTransformState) transformState, f, false);
        } else {
            super.transformViewFrom(transformState, f);
        }
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public boolean transformViewTo(TransformState transformState, float f) {
        if (!(transformState instanceof MessagingLayoutTransformState)) {
            return super.transformViewTo(transformState, f);
        }
        transformViewInternal((MessagingLayoutTransformState) transformState, f, true);
        return true;
    }
}
