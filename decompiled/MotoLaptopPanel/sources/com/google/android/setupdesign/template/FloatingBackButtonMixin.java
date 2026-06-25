package com.google.android.setupdesign.template;

import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.FrameLayout;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.template.Mixin;
import com.google.android.setupdesign.R$id;
import com.google.android.setupdesign.util.HeaderAreaStyler;
import com.google.android.setupdesign.util.LayoutStyler;
import com.google.android.setupdesign.util.PartnerStyleHelper;

/* JADX INFO: loaded from: classes.dex */
public class FloatingBackButtonMixin implements Mixin {
    static final String KEY_BACK_BUTTON_ON_CLICK_COUNT = "BackButton_onClickCount";
    private View.OnClickListener listener;
    private final TemplateLayout templateLayout;
    boolean tryInflatingBackButton = false;
    private int clickCount = 0;

    public FloatingBackButtonMixin(TemplateLayout templateLayout, AttributeSet attributeSet, int i) {
        this.templateLayout = templateLayout;
    }

    private Button findBackButton() {
        Button button = (Button) this.templateLayout.findManagedViewById(R$id.sud_floating_back_button);
        if (button == null) {
            Log.w("FloatingBackButtonMixin", "Can't find the back button.");
        }
        return button;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setOnClickListener$0(View.OnClickListener onClickListener, View view) {
        if (onClickListener != null) {
            onClickListener.onClick(view);
            this.clickCount++;
        }
    }

    Button getBackButton() {
        Button buttonFindBackButton = findBackButton();
        if (buttonFindBackButton != null) {
            return buttonFindBackButton;
        }
        if (!this.tryInflatingBackButton) {
            this.tryInflatingBackButton = true;
            ViewStub viewStub = (ViewStub) this.templateLayout.findManagedViewById(R$id.sud_floating_back_button_stub);
            if (viewStub != null) {
                try {
                    inflateButton(viewStub);
                } catch (InflateException e) {
                    Log.w("FloatingBackButtonMixin", "Incorrect theme:" + e.toString());
                    return null;
                }
            }
        }
        return findBackButton();
    }

    protected FrameLayout getContainerView() {
        return (FrameLayout) this.templateLayout.findManagedViewById(R$id.sud_layout_floating_back_button_container);
    }

    public PersistableBundle getMetrics() {
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putInt(KEY_BACK_BUTTON_ON_CLICK_COUNT, this.clickCount);
        return persistableBundle;
    }

    void inflateButton(ViewStub viewStub) {
        viewStub.setLayoutInflater(LayoutInflater.from(this.templateLayout.getContext()));
        viewStub.inflate();
    }

    public void setOnClickListener(final View.OnClickListener onClickListener) {
        Button backButton = getBackButton();
        if (backButton != null) {
            this.listener = onClickListener;
            backButton.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.setupdesign.template.FloatingBackButtonMixin$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$setOnClickListener$0(onClickListener, view);
                }
            });
        }
    }

    public void setVisibility(int i) {
        Button backButton = getBackButton();
        if (backButton != null) {
            backButton.setVisibility(i);
            getContainerView().setVisibility(i);
        }
    }

    public void tryApplyPartnerCustomizationStyle() {
        if (!PartnerStyleHelper.shouldApplyPartnerResource(this.templateLayout) || getContainerView() == null) {
            return;
        }
        LayoutStyler.applyPartnerCustomizationExtraPaddingStyle(getContainerView());
        HeaderAreaStyler.applyPartnerCustomizationBackButtonStyle(getContainerView());
    }
}
