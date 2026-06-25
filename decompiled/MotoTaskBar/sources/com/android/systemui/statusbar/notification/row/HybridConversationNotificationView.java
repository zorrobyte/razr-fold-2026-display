package com.android.systemui.statusbar.notification.row;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Icon;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.widget.ConversationAvatarData;
import com.android.internal.widget.ConversationHeaderData;
import com.android.internal.widget.ConversationLayout;
import com.android.systemui.res.R$dimen;
import com.android.systemui.statusbar.notification.NotificationFadeAware;
import com.android.systemui.statusbar.notification.row.HybridNotificationView;
import com.android.systemui.statusbar.notification.row.shared.AsyncHybridViewInflation;
import com.android.systemui.statusbar.notification.row.shared.ConversationStyleSetAvatarAsync;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.ConversationAvatar;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.FacePile;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.SingleIcon;
import com.motorola.taskbar.R$id;

/* JADX INFO: loaded from: classes.dex */
public class HybridConversationNotificationView extends HybridNotificationView {
    private View mConversationFacePile;
    private ViewStub mConversationFacePileStub;
    private ImageView mConversationIconView;
    private TextView mConversationSenderName;
    private int mFacePileAvatarSize;
    private int mFacePileProtectionWidth;
    private int mFacePileSize;
    private int mSingleAvatarSize;

    public HybridConversationNotificationView(Context context) {
        this(context, null);
    }

    public HybridConversationNotificationView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public HybridConversationNotificationView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public HybridConversationNotificationView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    private void loadConversationAvatar(ConversationLayout conversationLayout) {
        AsyncHybridViewInflation.assertInLegacyMode();
        if (ConversationStyleSetAvatarAsync.isEnabled()) {
            loadConversationAvatarWithDrawable(conversationLayout);
        } else {
            loadConversationAvatarWithIcon(conversationLayout);
        }
    }

    private void loadConversationAvatarWithDrawable(ConversationLayout conversationLayout) {
        AsyncHybridViewInflation.assertInLegacyMode();
        ConversationHeaderData conversationHeaderData = conversationLayout.getConversationHeaderData();
        conversationHeaderData.getClass();
        ConversationAvatarData.OneToOneConversationAvatarData conversationAvatar = conversationHeaderData.getConversationAvatar();
        conversationAvatar.getClass();
        if (conversationAvatar instanceof ConversationAvatarData.OneToOneConversationAvatarData) {
            this.mConversationFacePile.setVisibility(8);
            this.mConversationIconView.setVisibility(0);
            this.mConversationIconView.setImageDrawable(conversationAvatar.mDrawable);
            setSize(this.mConversationIconView, this.mSingleAvatarSize);
            return;
        }
        this.mConversationIconView.setVisibility(8);
        this.mConversationFacePile.setVisibility(0);
        View viewRequireViewById = requireViewById(R.id.conversation_header);
        this.mConversationFacePile = viewRequireViewById;
        ImageView imageView = (ImageView) viewRequireViewById.requireViewById(R.id.conversation_icon_badge);
        ImageView imageView2 = (ImageView) this.mConversationFacePile.requireViewById(R.id.conversation_icon);
        ImageView imageView3 = (ImageView) this.mConversationFacePile.requireViewById(R.id.conversation_icon_badge_bg);
        conversationLayout.bindFacePileWithDrawable(imageView, imageView2, imageView3, (ConversationAvatarData.GroupConversationAvatarData) conversationAvatar);
        setSize(this.mConversationFacePile, this.mFacePileSize);
        setSize(imageView2, this.mFacePileAvatarSize);
        setSize(imageView3, this.mFacePileAvatarSize);
        setSize(imageView, this.mFacePileAvatarSize + (this.mFacePileProtectionWidth * 2));
        this.mTransformationHelper.addViewTransformingToSimilar(imageView3);
        this.mTransformationHelper.addViewTransformingToSimilar(imageView2);
        this.mTransformationHelper.addViewTransformingToSimilar(imageView);
    }

    private void loadConversationAvatarWithIcon(ConversationLayout conversationLayout) {
        ConversationStyleSetAvatarAsync.assertInLegacyMode();
        AsyncHybridViewInflation.assertInLegacyMode();
        Icon conversationIcon = conversationLayout.getConversationIcon();
        if (conversationIcon != null) {
            this.mConversationFacePile.setVisibility(8);
            this.mConversationIconView.setVisibility(0);
            this.mConversationIconView.setImageIcon(conversationIcon);
            setSize(this.mConversationIconView, this.mSingleAvatarSize);
            return;
        }
        this.mConversationIconView.setVisibility(8);
        this.mConversationFacePile.setVisibility(0);
        View viewRequireViewById = requireViewById(R.id.conversation_header);
        this.mConversationFacePile = viewRequireViewById;
        ImageView imageView = (ImageView) viewRequireViewById.requireViewById(R.id.conversation_icon_badge);
        ImageView imageView2 = (ImageView) this.mConversationFacePile.requireViewById(R.id.conversation_icon);
        ImageView imageView3 = (ImageView) this.mConversationFacePile.requireViewById(R.id.conversation_icon_badge_bg);
        conversationLayout.bindFacePile(imageView, imageView2, imageView3);
        setSize(this.mConversationFacePile, this.mFacePileSize);
        setSize(imageView2, this.mFacePileAvatarSize);
        setSize(imageView3, this.mFacePileAvatarSize);
        setSize(imageView, this.mFacePileAvatarSize + (this.mFacePileProtectionWidth * 2));
        this.mTransformationHelper.addViewTransformingToSimilar(imageView3);
        this.mTransformationHelper.addViewTransformingToSimilar(imageView2);
        this.mTransformationHelper.addViewTransformingToSimilar(imageView);
    }

    private static void setSize(View view, int i) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        layoutParams.width = i;
        layoutParams.height = i;
        view.setLayoutParams(layoutParams);
    }

    @Override // com.android.systemui.statusbar.notification.row.HybridNotificationView
    public void bind(CharSequence charSequence, CharSequence charSequence2, View view) {
        AsyncHybridViewInflation.assertInLegacyMode();
        if (!(view instanceof ConversationLayout)) {
            super.bind(charSequence, charSequence2, view);
            return;
        }
        ConversationLayout conversationLayout = (ConversationLayout) view;
        loadConversationAvatar(conversationLayout);
        CharSequence conversationTitle = conversationLayout.getConversationTitle();
        if (!TextUtils.isEmpty(conversationTitle)) {
            charSequence = conversationTitle;
        }
        if (conversationLayout.isOneToOne()) {
            this.mConversationSenderName.setVisibility(8);
        } else {
            this.mConversationSenderName.setVisibility(0);
            this.mConversationSenderName.setText(conversationLayout.getConversationSenderName());
        }
        CharSequence conversationText = conversationLayout.getConversationText();
        if (!TextUtils.isEmpty(conversationText)) {
            charSequence2 = conversationText;
        }
        super.bind(charSequence, charSequence2, conversationLayout);
    }

    TextView getConversationSenderNameView() {
        return this.mConversationSenderName;
    }

    @Override // com.android.systemui.statusbar.notification.row.HybridNotificationView, android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mConversationIconView = (ImageView) requireViewById(R.id.conversation_icon_container);
        if (AsyncHybridViewInflation.isEnabled()) {
            this.mConversationFacePileStub = (ViewStub) requireViewById(R.id.conversation_header);
        } else {
            this.mConversationFacePile = requireViewById(R.id.conversation_header);
        }
        TextView textView = (TextView) requireViewById(R$id.conversation_notification_sender);
        this.mConversationSenderName = textView;
        applyTextColor(textView, this.mSecondaryTextColor);
        this.mFacePileSize = getResources().getDimensionPixelSize(R$dimen.conversation_single_line_face_pile_size);
        this.mFacePileAvatarSize = getResources().getDimensionPixelSize(R$dimen.conversation_single_line_face_pile_avatar_size);
        this.mSingleAvatarSize = getResources().getDimensionPixelSize(R$dimen.conversation_single_line_avatar_size);
        this.mFacePileProtectionWidth = getResources().getDimensionPixelSize(R$dimen.conversation_single_line_face_pile_protection_width);
        this.mTransformationHelper.setCustomTransformation(new HybridNotificationView.FadeOutAndDownWithTitleTransformation(this.mConversationSenderName), this.mConversationSenderName.getId());
        this.mTransformationHelper.addViewTransformingToSimilar(this.mConversationIconView);
        this.mTransformationHelper.addTransformedView(this.mConversationSenderName);
    }

    public void setAvatar(ConversationAvatar conversationAvatar) {
        if (AsyncHybridViewInflation.isUnexpectedlyInLegacyMode()) {
            return;
        }
        if (conversationAvatar instanceof SingleIcon) {
            SingleIcon singleIcon = (SingleIcon) conversationAvatar;
            View view = this.mConversationFacePile;
            if (view != null) {
                view.setVisibility(8);
            }
            this.mConversationIconView.setVisibility(0);
            this.mConversationIconView.setImageDrawable(singleIcon.getIconDrawable());
            setSize(this.mConversationIconView, this.mSingleAvatarSize);
            return;
        }
        FacePile facePile = (FacePile) conversationAvatar;
        this.mConversationIconView.setVisibility(8);
        if (this.mConversationFacePile == null) {
            this.mConversationFacePile = this.mConversationFacePileStub.inflate();
        }
        this.mConversationFacePile.setVisibility(0);
        ImageView imageView = (ImageView) this.mConversationFacePile.requireViewById(R.id.conversation_icon_badge);
        ImageView imageView2 = (ImageView) this.mConversationFacePile.requireViewById(R.id.conversation_icon);
        ImageView imageView3 = (ImageView) this.mConversationFacePile.requireViewById(R.id.conversation_icon_badge_bg);
        imageView.setImageTintList(ColorStateList.valueOf(facePile.getBottomBackgroundColor()));
        imageView2.setImageDrawable(facePile.getBottomIconDrawable());
        imageView3.setImageDrawable(facePile.getTopIconDrawable());
        setSize(this.mConversationFacePile, this.mFacePileSize);
        setSize(imageView2, this.mFacePileAvatarSize);
        setSize(imageView3, this.mFacePileAvatarSize);
        setSize(imageView, this.mFacePileAvatarSize + (this.mFacePileProtectionWidth * 2));
        this.mTransformationHelper.addViewTransformingToSimilar(imageView3);
        this.mTransformationHelper.addViewTransformingToSimilar(imageView2);
        this.mTransformationHelper.addViewTransformingToSimilar(imageView);
    }

    @Override // com.android.systemui.statusbar.notification.row.HybridNotificationView, com.android.systemui.statusbar.notification.NotificationFadeAware
    public void setNotificationFaded(boolean z) {
        super.setNotificationFaded(z);
        NotificationFadeAware.setLayerTypeForFaded(this.mConversationFacePile, z);
    }

    public void setText(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        if (AsyncHybridViewInflation.isUnexpectedlyInLegacyMode()) {
            return;
        }
        if (charSequence3 == null) {
            this.mConversationSenderName.setVisibility(8);
        } else {
            this.mConversationSenderName.setVisibility(0);
            this.mConversationSenderName.setText(charSequence3);
        }
        super.bind(charSequence, charSequence2, null);
    }
}
