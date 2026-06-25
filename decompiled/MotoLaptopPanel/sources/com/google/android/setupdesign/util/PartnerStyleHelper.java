package com.google.android.setupdesign.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import com.google.android.setupcompat.PartnerCustomizationLayout;
import com.google.android.setupcompat.R$attr;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.google.android.setupdesign.GlifLayout;
import com.google.android.setupdesign.R$id;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public abstract class PartnerStyleHelper {
    private static TemplateLayout findLayoutFromActivity(Activity activity) {
        View viewFindViewById;
        if (activity == null || (viewFindViewById = activity.findViewById(R$id.suc_layout_status)) == null) {
            return null;
        }
        return (TemplateLayout) viewFindViewById.getParent();
    }

    static boolean getDynamicColorPatnerConfig(Context context) {
        try {
            Activity activityLookupActivityFromContext = PartnerCustomizationLayout.lookupActivityFromContext(context);
            TemplateLayout templateLayoutFindLayoutFromActivity = findLayoutFromActivity(activityLookupActivityFromContext);
            return templateLayoutFindLayoutFromActivity instanceof GlifLayout ? ((GlifLayout) templateLayoutFindLayoutFromActivity).shouldApplyDynamicColor() : PartnerConfigHelper.isSetupWizardFullDynamicColorEnabled(activityLookupActivityFromContext);
        } catch (ClassCastException | IllegalArgumentException unused) {
            return false;
        }
    }

    public static int getLayoutGravity(Context context) {
        String string = PartnerConfigHelper.get(context).getString(context, PartnerConfig.CONFIG_LAYOUT_GRAVITY);
        if (string == null) {
            return 0;
        }
        String lowerCase = string.toLowerCase(Locale.ROOT);
        lowerCase.hashCode();
        if (lowerCase.equals("center")) {
            return 17;
        }
        return !lowerCase.equals("start") ? 0 : 8388611;
    }

    public static boolean isPartnerHeavyThemeLayout(TemplateLayout templateLayout) {
        if (templateLayout instanceof GlifLayout) {
            return ((GlifLayout) templateLayout).shouldApplyPartnerHeavyThemeResource();
        }
        return false;
    }

    public static boolean isPartnerLightThemeLayout(TemplateLayout templateLayout) {
        if (templateLayout instanceof PartnerCustomizationLayout) {
            return ((PartnerCustomizationLayout) templateLayout).shouldApplyPartnerResource();
        }
        return false;
    }

    private static boolean shouldApplyPartnerResource(Context context) {
        if (!PartnerConfigHelper.get(context).isAvailable()) {
            return false;
        }
        Activity activityLookupActivityFromContext = null;
        try {
            activityLookupActivityFromContext = PartnerCustomizationLayout.lookupActivityFromContext(context);
            if (activityLookupActivityFromContext != null) {
                TemplateLayout templateLayoutFindLayoutFromActivity = findLayoutFromActivity(activityLookupActivityFromContext);
                if (templateLayoutFindLayoutFromActivity instanceof PartnerCustomizationLayout) {
                    return ((PartnerCustomizationLayout) templateLayoutFindLayoutFromActivity).shouldApplyPartnerResource();
                }
            }
        } catch (ClassCastException | IllegalArgumentException unused) {
        }
        boolean zIsAnySetupWizard = activityLookupActivityFromContext != null ? WizardManagerHelper.isAnySetupWizard(activityLookupActivityFromContext.getIntent()) : false;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(new int[]{R$attr.sucUsePartnerResource});
        boolean z = typedArrayObtainStyledAttributes.getBoolean(0, true);
        typedArrayObtainStyledAttributes.recycle();
        return zIsAnySetupWizard || z;
    }

    public static boolean shouldApplyPartnerResource(View view) {
        if (view == null) {
            return false;
        }
        return view instanceof PartnerCustomizationLayout ? isPartnerLightThemeLayout((PartnerCustomizationLayout) view) : shouldApplyPartnerResource(view.getContext());
    }

    public static boolean useDynamicColor(View view) {
        if (view == null) {
            return false;
        }
        return getDynamicColorPatnerConfig(view.getContext());
    }
}
