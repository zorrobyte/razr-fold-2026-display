package com.google.android.setupcompat.partnerconfig;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import androidx.window.embedding.ActivityEmbeddingController;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.util.BuildCompatUtils;
import java.util.EnumMap;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class PartnerConfigHelper {
    public static final String EMBEDDED_ACTIVITY_RESOURCE_SUFFIX = "_embedded_activity";
    static final String FORCE_TWO_PANE_SUFFIX = "_two_pane";
    public static final String GET_SUW_DEFAULT_THEME_STRING_METHOD = "suwDefaultThemeString";
    public static final String GLIF_EXPRESSIVE_RESOURCE_SUFFIX = "_expressive";
    public static final String IS_DYNAMIC_COLOR_ENABLED_METHOD = "isDynamicColorEnabled";
    public static final String IS_EMBEDDED_ACTIVITY_ONE_PANE_ENABLED_METHOD = "isEmbeddedActivityOnePaneEnabled";
    public static final String IS_ENHANCED_SETUP_DESIGN_METRICS_ENABLED = "isEnhancedSetupDesignMetricsEnabled";
    public static final String IS_EXTENDED_PARTNER_CONFIG_ENABLED_METHOD = "isExtendedPartnerConfigEnabled";
    public static final String IS_FONT_WEIGHT_ENABLED_METHOD = "isFontWeightEnabled";
    public static final String IS_FORCE_TWO_PANE_ENABLED_METHOD = "isForceTwoPaneEnabled";
    public static final String IS_FULL_DYNAMIC_COLOR_ENABLED_METHOD = "isFullDynamicColorEnabled";
    public static final String IS_GLIF_EXPRESSIVE_ENABLED = "isGlifExpressiveEnabled";
    public static final String IS_KEYBOARD_FOCUS_ENHANCEMENT_ENABLED_METHOD = "isKeyboardFocusEnhancementEnabled";
    public static final String IS_MATERIAL_YOU_STYLE_ENABLED_METHOD = "IsMaterialYouStyleEnabled";
    public static final String IS_NEUTRAL_BUTTON_STYLE_ENABLED_METHOD = "isNeutralButtonStyleEnabled";
    public static final String IS_SUW_DAY_NIGHT_ENABLED_METHOD = "isSuwDayNightEnabled";
    public static final String KEY_FALLBACK_CONFIG = "fallbackConfig";
    public static final String MATERIAL_YOU_RESOURCE_SUFFIX = "_material_you";
    public static final String SUW_GET_PARTNER_CONFIG_METHOD = "getOverlayConfig";
    public static final String SUW_PACKAGE_NAME = "com.google.android.setupwizard";
    private static final String TAG = "PartnerConfigHelper";
    public static Bundle applyDynamicColorBundle = null;
    public static Bundle applyEmbeddedActivityOnePaneBundle = null;
    public static Bundle applyExtendedPartnerConfigBundle = null;
    public static Bundle applyFontWeightBundle = null;
    public static Bundle applyForceTwoPaneBundle = null;
    public static Bundle applyFullDynamicColorBundle = null;
    public static Bundle applyGlifExpressiveBundle = null;
    public static Bundle applyMaterialYouConfigBundle = null;
    public static Bundle applyNeutralButtonStyleBundle = null;
    static Bundle applyTransitionBundle = null;
    private static ContentObserver contentObserver = null;
    public static Bundle enableMetricsLoggingBundle = null;
    private static PartnerConfigHelper instance = null;
    public static Bundle keyboardFocusEnhancementBundle = null;
    private static boolean savedConfigEmbeddedActivityMode = false;
    private static int savedConfigUiMode = 0;
    public static int savedOrientation = 1;
    public static int savedScreenHeight;
    public static int savedScreenWidth;
    public static Bundle suwDayNightEnabledBundle;
    public static Bundle suwDefaultThemeBundle;
    Bundle resultBundle = null;
    final EnumMap partnerResourceCache = new EnumMap(PartnerConfig.class);

    private PartnerConfigHelper(Context context) {
        getPartnerConfigBundle(context);
        registerContentObserver(context);
    }

    private static ResourceEntry adjustResourceEntryDayNightMode(Context context, ResourceEntry resourceEntry) {
        Resources resources = resourceEntry.getResources();
        Configuration configuration = resources.getConfiguration();
        if (!isSetupWizardDayNightEnabled(context) && Util.isNightMode(configuration)) {
            configuration.uiMode = (configuration.uiMode & (-49)) | 16;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        return resourceEntry;
    }

    public static synchronized PartnerConfigHelper get(Context context) {
        try {
            if (!isValidInstance(context)) {
                instance = new PartnerConfigHelper(context);
            }
        } catch (Throwable th) {
            throw th;
        }
        return instance;
    }

    static Uri getContentUri() {
        return new Uri.Builder().scheme("content").authority("com.google.android.setupwizard.partner").build();
    }

    private static float getDimensionFromTypedValue(Context context, TypedValue typedValue) {
        return typedValue.getDimension(context.getResources().getDisplayMetrics());
    }

    private void getPartnerConfigBundle(Context context) {
        Bundle bundle = this.resultBundle;
        if (bundle == null || bundle.isEmpty()) {
            try {
                this.resultBundle = context.getContentResolver().call(getContentUri(), SUW_GET_PARTNER_CONFIG_METHOD, (String) null, (Bundle) null);
                this.partnerResourceCache.clear();
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("PartnerConfigsBundle=");
                Bundle bundle2 = this.resultBundle;
                sb.append(bundle2 != null ? Integer.valueOf(bundle2.size()) : "(null)");
                Log.i(str, sb.toString());
            } catch (IllegalArgumentException | SecurityException unused) {
                Log.w(TAG, "Fail to get config from suw provider");
            }
        }
    }

    private static TypedValue getTypedValueFromResource(Resources resources, int i, int i2) {
        TypedValue typedValue = new TypedValue();
        resources.getValue(i, typedValue, true);
        if (typedValue.type == i2) {
            return typedValue;
        }
        throw new Resources.NotFoundException("Resource ID #0x" + Integer.toHexString(i) + " type #0x" + Integer.toHexString(typedValue.type) + " is not valid");
    }

    public static boolean isEmbeddedActivityOnePaneEnabled(Context context) {
        if (applyEmbeddedActivityOnePaneBundle == null) {
            try {
                applyEmbeddedActivityOnePaneBundle = context.getContentResolver().call(getContentUri(), IS_EMBEDDED_ACTIVITY_ONE_PANE_ENABLED_METHOD, (String) null, (Bundle) null);
            } catch (IllegalArgumentException | SecurityException unused) {
                Log.w(TAG, "SetupWizard one-pane support in embedded activity status unknown; return as false.");
                applyEmbeddedActivityOnePaneBundle = null;
                return false;
            }
        }
        Bundle bundle = applyEmbeddedActivityOnePaneBundle;
        return bundle != null && bundle.getBoolean(IS_EMBEDDED_ACTIVITY_ONE_PANE_ENABLED_METHOD, false);
    }

    public static boolean isEnhancedSetupDesignMetricsEnabled(Context context) {
        Bundle bundle = enableMetricsLoggingBundle;
        if (bundle == null || bundle.isEmpty()) {
            try {
                enableMetricsLoggingBundle = context.getContentResolver().call(getContentUri(), IS_ENHANCED_SETUP_DESIGN_METRICS_ENABLED, (String) null, (Bundle) null);
            } catch (IllegalArgumentException | SecurityException unused) {
                Log.w(TAG, "Method isEnhancedSetupDesignMetricsEnabled is unknown");
                enableMetricsLoggingBundle = null;
                return false;
            }
        }
        Bundle bundle2 = enableMetricsLoggingBundle;
        if (bundle2 == null || bundle2.isEmpty()) {
            return false;
        }
        return enableMetricsLoggingBundle.getBoolean(IS_ENHANCED_SETUP_DESIGN_METRICS_ENABLED, false);
    }

    public static boolean isFontWeightEnabled(Context context) {
        if (applyFontWeightBundle == null) {
            try {
                applyFontWeightBundle = context.getContentResolver().call(getContentUri(), IS_FONT_WEIGHT_ENABLED_METHOD, (String) null, (Bundle) null);
            } catch (IllegalArgumentException | SecurityException unused) {
                Log.w(TAG, "Font weight supporting status unknown; return as false.");
                applyFontWeightBundle = null;
                return false;
            }
        }
        Bundle bundle = applyFontWeightBundle;
        return bundle != null && bundle.getBoolean(IS_FONT_WEIGHT_ENABLED_METHOD, true);
    }

    public static boolean isForceTwoPaneEnabled(Context context) {
        Bundle bundle = applyForceTwoPaneBundle;
        if (bundle == null || bundle.isEmpty()) {
            try {
                applyForceTwoPaneBundle = context.getContentResolver().call(getContentUri(), IS_FORCE_TWO_PANE_ENABLED_METHOD, (String) null, (Bundle) null);
            } catch (IllegalArgumentException | SecurityException unused) {
                Log.w(TAG, "isForceTwoPaneEnabled status is unknown; return as false.");
            }
        }
        Bundle bundle2 = applyForceTwoPaneBundle;
        if (bundle2 == null || bundle2.isEmpty()) {
            return false;
        }
        return applyForceTwoPaneBundle.getBoolean(IS_FORCE_TWO_PANE_ENABLED_METHOD, false);
    }

    public static boolean isGlifExpressiveEnabled(Context context) {
        Bundle bundle = applyGlifExpressiveBundle;
        if (bundle == null || bundle.isEmpty()) {
            try {
                applyGlifExpressiveBundle = context.getContentResolver().call(getContentUri(), IS_GLIF_EXPRESSIVE_ENABLED, (String) null, (Bundle) null);
            } catch (IllegalArgumentException | SecurityException unused) {
                Log.w(TAG, "isGlifExpressiveEnabled status is unknown; return as false.");
            }
        }
        Bundle bundle2 = applyGlifExpressiveBundle;
        if (bundle2 == null || bundle2.isEmpty()) {
            return false;
        }
        return applyGlifExpressiveBundle.getBoolean(IS_GLIF_EXPRESSIVE_ENABLED, false);
    }

    public static boolean isKeyboardFocusEnhancementEnabled(Context context) {
        Bundle bundle = keyboardFocusEnhancementBundle;
        if (bundle == null || bundle.isEmpty()) {
            try {
                keyboardFocusEnhancementBundle = context.getContentResolver().call(getContentUri(), IS_KEYBOARD_FOCUS_ENHANCEMENT_ENABLED_METHOD, (String) null, (Bundle) null);
            } catch (IllegalArgumentException | SecurityException unused) {
                Log.w(TAG, "SetupWizard keyboard focus enhancement status unknown; return as false.");
                keyboardFocusEnhancementBundle = null;
                return false;
            }
        }
        Bundle bundle2 = keyboardFocusEnhancementBundle;
        if (bundle2 == null || bundle2.isEmpty()) {
            return false;
        }
        return keyboardFocusEnhancementBundle.getBoolean(IS_KEYBOARD_FOCUS_ENHANCEMENT_ENABLED_METHOD);
    }

    public static boolean isNeutralButtonStyleEnabled(Context context) {
        if (applyNeutralButtonStyleBundle == null) {
            try {
                applyNeutralButtonStyleBundle = context.getContentResolver().call(getContentUri(), IS_NEUTRAL_BUTTON_STYLE_ENABLED_METHOD, (String) null, (Bundle) null);
            } catch (IllegalArgumentException | SecurityException unused) {
                Log.w(TAG, "Neutral button style supporting status unknown; return as false.");
                applyNeutralButtonStyleBundle = null;
                return false;
            }
        }
        Bundle bundle = applyNeutralButtonStyleBundle;
        return bundle != null && bundle.getBoolean(IS_NEUTRAL_BUTTON_STYLE_ENABLED_METHOD, false);
    }

    public static boolean isSetupWizardDayNightEnabled(Context context) {
        if (suwDayNightEnabledBundle == null) {
            try {
                suwDayNightEnabledBundle = context.getContentResolver().call(getContentUri(), IS_SUW_DAY_NIGHT_ENABLED_METHOD, (String) null, (Bundle) null);
            } catch (IllegalArgumentException | SecurityException unused) {
                Log.w(TAG, "SetupWizard DayNight supporting status unknown; return as false.");
                suwDayNightEnabledBundle = null;
                return false;
            }
        }
        Bundle bundle = suwDayNightEnabledBundle;
        return bundle != null && bundle.getBoolean(IS_SUW_DAY_NIGHT_ENABLED_METHOD, false);
    }

    public static boolean isSetupWizardDynamicColorEnabled(Context context) {
        if (applyDynamicColorBundle == null) {
            try {
                applyDynamicColorBundle = context.getContentResolver().call(getContentUri(), IS_DYNAMIC_COLOR_ENABLED_METHOD, (String) null, (Bundle) null);
            } catch (IllegalArgumentException | SecurityException unused) {
                Log.w(TAG, "SetupWizard dynamic color supporting status unknown; return as false.");
                applyDynamicColorBundle = null;
                return false;
            }
        }
        Bundle bundle = applyDynamicColorBundle;
        return bundle != null && bundle.getBoolean(IS_DYNAMIC_COLOR_ENABLED_METHOD, false);
    }

    public static boolean isSetupWizardFullDynamicColorEnabled(Context context) {
        if (applyFullDynamicColorBundle == null) {
            try {
                applyFullDynamicColorBundle = context.getContentResolver().call(getContentUri(), IS_FULL_DYNAMIC_COLOR_ENABLED_METHOD, (String) null, (Bundle) null);
            } catch (IllegalArgumentException | SecurityException unused) {
                Log.w(TAG, "SetupWizard full dynamic color supporting status unknown; return as false.");
                applyFullDynamicColorBundle = null;
                return false;
            }
        }
        Bundle bundle = applyFullDynamicColorBundle;
        return bundle != null && bundle.getBoolean(IS_FULL_DYNAMIC_COLOR_ENABLED_METHOD, false);
    }

    private static boolean isValidInstance(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        if (instance == null) {
            savedConfigEmbeddedActivityMode = isEmbeddedActivityOnePaneEnabled(context) && BuildCompatUtils.isAtLeastU();
            savedConfigUiMode = configuration.uiMode & 48;
            savedOrientation = configuration.orientation;
            savedScreenWidth = configuration.screenWidthDp;
            savedScreenHeight = configuration.screenHeightDp;
            return false;
        }
        boolean z = isSetupWizardDayNightEnabled(context) && (configuration.uiMode & 48) != savedConfigUiMode;
        boolean z2 = isEmbeddedActivityOnePaneEnabled(context) && BuildCompatUtils.isAtLeastU();
        if (!z && z2 == savedConfigEmbeddedActivityMode && configuration.orientation == savedOrientation && configuration.screenWidthDp == savedScreenWidth && configuration.screenHeightDp == savedScreenHeight) {
            return true;
        }
        savedConfigUiMode = configuration.uiMode & 48;
        savedOrientation = configuration.orientation;
        savedScreenHeight = configuration.screenHeightDp;
        savedScreenWidth = configuration.screenWidthDp;
        resetInstance();
        return false;
    }

    public static Activity lookupActivityFromContext(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            return lookupActivityFromContext(((ContextWrapper) context).getBaseContext());
        }
        throw new IllegalArgumentException("Cannot find instance of Activity in parent tree");
    }

    private static void registerContentObserver(Context context) {
        if (isSetupWizardDayNightEnabled(context)) {
            if (contentObserver != null) {
                unregisterContentObserver(context);
            }
            Uri contentUri = getContentUri();
            try {
                contentObserver = new ContentObserver(null) { // from class: com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.1
                    @Override // android.database.ContentObserver
                    public void onChange(boolean z) {
                        super.onChange(z);
                        PartnerConfigHelper.resetInstance();
                    }
                };
                context.getContentResolver().registerContentObserver(contentUri, true, contentObserver);
            } catch (IllegalArgumentException | NullPointerException | SecurityException e) {
                Log.w(TAG, "Failed to register content observer for " + contentUri + ": " + e);
            }
        }
    }

    public static synchronized void resetInstance() {
        instance = null;
        suwDayNightEnabledBundle = null;
        applyExtendedPartnerConfigBundle = null;
        applyMaterialYouConfigBundle = null;
        applyDynamicColorBundle = null;
        applyFullDynamicColorBundle = null;
        applyNeutralButtonStyleBundle = null;
        applyEmbeddedActivityOnePaneBundle = null;
        suwDefaultThemeBundle = null;
        applyTransitionBundle = null;
        applyForceTwoPaneBundle = null;
        applyGlifExpressiveBundle = null;
        keyboardFocusEnhancementBundle = null;
        enableMetricsLoggingBundle = null;
    }

    public static boolean shouldApplyExtendedPartnerConfig(Context context) {
        if (applyExtendedPartnerConfigBundle == null) {
            try {
                applyExtendedPartnerConfigBundle = context.getContentResolver().call(getContentUri(), IS_EXTENDED_PARTNER_CONFIG_ENABLED_METHOD, (String) null, (Bundle) null);
            } catch (IllegalArgumentException | SecurityException unused) {
                Log.w(TAG, "SetupWizard extended partner configs supporting status unknown; return as false.");
                applyExtendedPartnerConfigBundle = null;
                return false;
            }
        }
        Bundle bundle = applyExtendedPartnerConfigBundle;
        return bundle != null && bundle.getBoolean(IS_EXTENDED_PARTNER_CONFIG_ENABLED_METHOD, false);
    }

    public static boolean shouldApplyMaterialYouStyle(Context context) {
        Bundle bundle = applyMaterialYouConfigBundle;
        if (bundle == null || bundle.isEmpty()) {
            try {
                Bundle bundleCall = context.getContentResolver().call(getContentUri(), IS_MATERIAL_YOU_STYLE_ENABLED_METHOD, (String) null, (Bundle) null);
                applyMaterialYouConfigBundle = bundleCall;
                if (bundleCall != null && bundleCall.isEmpty() && !BuildCompatUtils.isAtLeastT()) {
                    return shouldApplyExtendedPartnerConfig(context);
                }
            } catch (IllegalArgumentException | SecurityException unused) {
                Log.w(TAG, "SetupWizard Material You configs supporting status unknown; return as false.");
                applyMaterialYouConfigBundle = null;
                return false;
            }
        }
        Bundle bundle2 = applyMaterialYouConfigBundle;
        return (bundle2 != null && bundle2.getBoolean(IS_MATERIAL_YOU_STYLE_ENABLED_METHOD, false)) || isGlifExpressiveEnabled(context);
    }

    private static void unregisterContentObserver(Context context) {
        try {
            context.getContentResolver().unregisterContentObserver(contentObserver);
            contentObserver = null;
        } catch (IllegalArgumentException | NullPointerException | SecurityException e) {
            Log.w(TAG, "Failed to unregister content observer: " + e);
        }
    }

    ResourceEntry adjustEmbeddedActivityResourceEntryDefaultValue(Context context, ResourceEntry resourceEntry) {
        String resourceTypeName;
        String strConcat;
        int identifier;
        try {
            resourceTypeName = resourceEntry.getResources().getResourceTypeName(resourceEntry.getResourceId());
            strConcat = resourceEntry.getResourceName().concat(EMBEDDED_ACTIVITY_RESOURCE_SUFFIX);
            identifier = resourceEntry.getResources().getIdentifier(strConcat, resourceTypeName, resourceEntry.getPackageName());
        } catch (PackageManager.NameNotFoundException | Resources.NotFoundException unused) {
        }
        if (identifier == 0) {
            Resources resourcesForApplication = context.getPackageManager().getResourcesForApplication(SUW_PACKAGE_NAME);
            int identifier2 = resourcesForApplication.getIdentifier(strConcat, resourceTypeName, SUW_PACKAGE_NAME);
            if (identifier2 != 0) {
                return new ResourceEntry(SUW_PACKAGE_NAME, strConcat, identifier2, resourcesForApplication);
            }
            return resourceEntry;
        }
        Log.i(TAG, "use embedded activity resource:" + strConcat);
        return new ResourceEntry(resourceEntry.getPackageName(), strConcat, identifier, resourceEntry.getResources());
    }

    ResourceEntry adjustForceTwoPaneResourceEntryDefaultValue(Context context, ResourceEntry resourceEntry) {
        if (context != null) {
            try {
                String resourceTypeName = resourceEntry.getResources().getResourceTypeName(resourceEntry.getResourceId());
                String strConcat = resourceEntry.getResourceName().concat(FORCE_TWO_PANE_SUFFIX);
                int identifier = resourceEntry.getResources().getIdentifier(strConcat, resourceTypeName, resourceEntry.getPackageName());
                if (identifier != 0) {
                    Log.i(TAG, "two pane resource=" + strConcat);
                    return new ResourceEntry(resourceEntry.getPackageName(), strConcat, identifier, resourceEntry.getResources());
                }
                Resources resourcesForApplication = context.getPackageManager().getResourcesForApplication(SUW_PACKAGE_NAME);
                int identifier2 = resourcesForApplication.getIdentifier(strConcat, resourceTypeName, SUW_PACKAGE_NAME);
                if (identifier2 != 0) {
                    return new ResourceEntry(SUW_PACKAGE_NAME, strConcat, identifier2, resourcesForApplication);
                }
            } catch (PackageManager.NameNotFoundException | Resources.NotFoundException unused) {
            }
        }
        return resourceEntry;
    }

    ResourceEntry adjustGlifExpressiveResourceEntryDefaultValue(Context context, ResourceEntry resourceEntry) {
        try {
            if (Objects.equals(resourceEntry.getPackageName(), SUW_PACKAGE_NAME)) {
                String resourceTypeName = resourceEntry.getResources().getResourceTypeName(resourceEntry.getResourceId());
                String strConcat = resourceEntry.getResourceName().concat(GLIF_EXPRESSIVE_RESOURCE_SUFFIX);
                int identifier = resourceEntry.getResources().getIdentifier(strConcat, resourceTypeName, resourceEntry.getPackageName());
                if (identifier != 0) {
                    Log.i(TAG, "use expressive resource:" + strConcat);
                    return new ResourceEntry(resourceEntry.getPackageName(), strConcat, identifier, resourceEntry.getResources());
                }
            }
        } catch (Resources.NotFoundException unused) {
        }
        return resourceEntry;
    }

    ResourceEntry adjustMaterialYouResourceEntryDefaultValue(Context context, ResourceEntry resourceEntry) {
        try {
            if (Objects.equals(resourceEntry.getPackageName(), SUW_PACKAGE_NAME)) {
                String resourceTypeName = resourceEntry.getResources().getResourceTypeName(resourceEntry.getResourceId());
                String strConcat = resourceEntry.getResourceName().concat(MATERIAL_YOU_RESOURCE_SUFFIX);
                int identifier = resourceEntry.getResources().getIdentifier(strConcat, resourceTypeName, resourceEntry.getPackageName());
                if (identifier != 0) {
                    Log.i(TAG, "use material you resource:" + strConcat);
                    return new ResourceEntry(resourceEntry.getPackageName(), strConcat, identifier, resourceEntry.getResources());
                }
            }
        } catch (Resources.NotFoundException unused) {
        }
        return resourceEntry;
    }

    public boolean getBoolean(Context context, PartnerConfig partnerConfig, boolean z) {
        if (partnerConfig.getResourceType() != PartnerConfig.ResourceType.BOOL) {
            throw new IllegalArgumentException("Not a bool resource");
        }
        if (this.partnerResourceCache.containsKey(partnerConfig)) {
            return ((Boolean) this.partnerResourceCache.get(partnerConfig)).booleanValue();
        }
        try {
            ResourceEntry resourceEntryFromKey = getResourceEntryFromKey(context, partnerConfig.getResourceName());
            z = resourceEntryFromKey.getResources().getBoolean(resourceEntryFromKey.getResourceId());
            this.partnerResourceCache.put(partnerConfig, Boolean.valueOf(z));
            return z;
        } catch (Resources.NotFoundException | NullPointerException unused) {
            return z;
        }
    }

    public int getColor(Context context, PartnerConfig partnerConfig) {
        if (partnerConfig.getResourceType() != PartnerConfig.ResourceType.COLOR) {
            throw new IllegalArgumentException("Not a color resource");
        }
        if (this.partnerResourceCache.containsKey(partnerConfig)) {
            return ((Integer) this.partnerResourceCache.get(partnerConfig)).intValue();
        }
        int color = 0;
        try {
            ResourceEntry resourceEntryFromKey = getResourceEntryFromKey(context, partnerConfig.getResourceName());
            Resources resources = resourceEntryFromKey.getResources();
            int resourceId = resourceEntryFromKey.getResourceId();
            TypedValue typedValue = new TypedValue();
            resources.getValue(resourceId, typedValue, true);
            if (typedValue.type == 1 && typedValue.data == 0) {
                return 0;
            }
            color = resources.getColor(resourceId, null);
            this.partnerResourceCache.put(partnerConfig, Integer.valueOf(color));
            return color;
        } catch (NullPointerException unused) {
            return color;
        }
    }

    public float getDimension(Context context, PartnerConfig partnerConfig) {
        return getDimension(context, partnerConfig, 0.0f);
    }

    public float getDimension(Context context, PartnerConfig partnerConfig, float f) {
        if (partnerConfig.getResourceType() != PartnerConfig.ResourceType.DIMENSION) {
            throw new IllegalArgumentException("Not a dimension resource");
        }
        if (this.partnerResourceCache.containsKey(partnerConfig)) {
            return getDimensionFromTypedValue(context, (TypedValue) this.partnerResourceCache.get(partnerConfig));
        }
        try {
            ResourceEntry resourceEntryFromKey = getResourceEntryFromKey(context, partnerConfig.getResourceName());
            Resources resources = resourceEntryFromKey.getResources();
            int resourceId = resourceEntryFromKey.getResourceId();
            f = resources.getDimension(resourceId);
            this.partnerResourceCache.put(partnerConfig, getTypedValueFromResource(resources, resourceId, 5));
            return getDimensionFromTypedValue(context, (TypedValue) this.partnerResourceCache.get(partnerConfig));
        } catch (Resources.NotFoundException | NullPointerException unused) {
            return f;
        }
    }

    public Drawable getDrawable(Context context, PartnerConfig partnerConfig) {
        if (partnerConfig.getResourceType() != PartnerConfig.ResourceType.DRAWABLE) {
            throw new IllegalArgumentException("Not a drawable resource");
        }
        if (this.partnerResourceCache.containsKey(partnerConfig)) {
            return (Drawable) this.partnerResourceCache.get(partnerConfig);
        }
        Drawable drawable = null;
        try {
            ResourceEntry resourceEntryFromKey = getResourceEntryFromKey(context, partnerConfig.getResourceName());
            Resources resources = resourceEntryFromKey.getResources();
            int resourceId = resourceEntryFromKey.getResourceId();
            TypedValue typedValue = new TypedValue();
            resources.getValue(resourceId, typedValue, true);
            if (typedValue.type == 1 && typedValue.data == 0) {
                return null;
            }
            drawable = resources.getDrawable(resourceId, null);
            this.partnerResourceCache.put(partnerConfig, drawable);
            return drawable;
        } catch (Resources.NotFoundException | NullPointerException unused) {
            return drawable;
        }
    }

    public float getFraction(Context context, PartnerConfig partnerConfig) {
        return getFraction(context, partnerConfig, 0.0f);
    }

    public float getFraction(Context context, PartnerConfig partnerConfig, float f) {
        if (partnerConfig.getResourceType() != PartnerConfig.ResourceType.FRACTION) {
            throw new IllegalArgumentException("Not a fraction resource");
        }
        if (this.partnerResourceCache.containsKey(partnerConfig)) {
            return ((Float) this.partnerResourceCache.get(partnerConfig)).floatValue();
        }
        try {
            ResourceEntry resourceEntryFromKey = getResourceEntryFromKey(context, partnerConfig.getResourceName());
            f = resourceEntryFromKey.getResources().getFraction(resourceEntryFromKey.getResourceId(), 1, 1);
            this.partnerResourceCache.put(partnerConfig, Float.valueOf(f));
            return f;
        } catch (Resources.NotFoundException | NullPointerException unused) {
            return f;
        }
    }

    public int getInteger(Context context, PartnerConfig partnerConfig, int i) {
        if (partnerConfig.getResourceType() != PartnerConfig.ResourceType.INTEGER) {
            throw new IllegalArgumentException("Not a integer resource");
        }
        if (this.partnerResourceCache.containsKey(partnerConfig)) {
            return ((Integer) this.partnerResourceCache.get(partnerConfig)).intValue();
        }
        try {
            ResourceEntry resourceEntryFromKey = getResourceEntryFromKey(context, partnerConfig.getResourceName());
            i = resourceEntryFromKey.getResources().getInteger(resourceEntryFromKey.getResourceId());
            this.partnerResourceCache.put(partnerConfig, Integer.valueOf(i));
            return i;
        } catch (Resources.NotFoundException | NullPointerException unused) {
            return i;
        }
    }

    ResourceEntry getResourceEntryFromKey(Context context, String str) {
        Bundle bundle = this.resultBundle.getBundle(str);
        Bundle bundle2 = this.resultBundle.getBundle(KEY_FALLBACK_CONFIG);
        if (bundle2 != null) {
            bundle.putBundle(KEY_FALLBACK_CONFIG, bundle2.getBundle(str));
        }
        ResourceEntry resourceEntryFromBundle = ResourceEntry.fromBundle(context, bundle);
        if (BuildCompatUtils.isAtLeastU() && isActivityEmbedded(context)) {
            resourceEntryFromBundle = adjustEmbeddedActivityResourceEntryDefaultValue(context, resourceEntryFromBundle);
        } else if (BuildCompatUtils.isAtLeastV() && isGlifExpressiveEnabled(context)) {
            resourceEntryFromBundle = adjustGlifExpressiveResourceEntryDefaultValue(context, resourceEntryFromBundle);
        } else if (BuildCompatUtils.isAtLeastU() && isForceTwoPaneEnabled(context)) {
            resourceEntryFromBundle = adjustForceTwoPaneResourceEntryDefaultValue(context, resourceEntryFromBundle);
        } else if (BuildCompatUtils.isAtLeastT() && shouldApplyMaterialYouStyle(context)) {
            resourceEntryFromBundle = adjustMaterialYouResourceEntryDefaultValue(context, resourceEntryFromBundle);
        }
        return adjustResourceEntryDayNightMode(context, resourceEntryFromBundle);
    }

    public String getString(Context context, PartnerConfig partnerConfig) {
        if (partnerConfig.getResourceType() != PartnerConfig.ResourceType.STRING) {
            throw new IllegalArgumentException("Not a string resource");
        }
        if (this.partnerResourceCache.containsKey(partnerConfig)) {
            return (String) this.partnerResourceCache.get(partnerConfig);
        }
        String string = null;
        try {
            ResourceEntry resourceEntryFromKey = getResourceEntryFromKey(context, partnerConfig.getResourceName());
            string = resourceEntryFromKey.getResources().getString(resourceEntryFromKey.getResourceId());
            this.partnerResourceCache.put(partnerConfig, string);
            return string;
        } catch (NullPointerException unused) {
            return string;
        }
    }

    boolean isActivityEmbedded(Context context) {
        try {
            Activity activityLookupActivityFromContext = lookupActivityFromContext(context);
            return isEmbeddedActivityOnePaneEnabled(context) && ActivityEmbeddingController.getInstance(activityLookupActivityFromContext).isActivityEmbedded(activityLookupActivityFromContext);
        } catch (IllegalArgumentException unused) {
            Log.w(TAG, "Not a Activity instance in parent tree");
            return false;
        }
    }

    public boolean isAvailable() {
        Bundle bundle = this.resultBundle;
        return (bundle == null || bundle.isEmpty()) ? false : true;
    }

    public boolean isPartnerConfigAvailable(PartnerConfig partnerConfig) {
        return isAvailable() && this.resultBundle.containsKey(partnerConfig.getResourceName());
    }
}
