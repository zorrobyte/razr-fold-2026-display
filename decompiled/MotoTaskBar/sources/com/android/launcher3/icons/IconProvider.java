package com.android.launcher3.icons;

import android.R;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.LocaleList;
import android.os.Message;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.content.ContextCompat;
import com.android.launcher3.icons.cache.VaultUtils;
import com.android.launcher3.util.ComponentKey;
import com.android.launcher3.util.SafeCloseable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/* JADX INFO: loaded from: classes.dex */
public class IconProvider {
    public static final String ACTION_DYNAMIC_ICON = "com.motorola.launcher3.action.DYNAMIC_ICON";
    public static final String ACTION_DYNAMIC_ICON_CHANGED = "com.motorola.launcher3.action.DYNAMIC_ICON_CHANGED";
    private static final boolean DEBUG = false;
    private static final String ICON_METADATA_KEY_PREFIX = ".dynamic_icons";
    public static final HandlerThread ICON_PROVIDER_HANDLER_THREAD;
    private static final String ICON_ROUND_METADATA_KEY_PREFIX = ".dynamic_icons_nexus_round";
    private static final String SYSTEM_STATE_SEPARATOR = " ";
    private static final String TAG = "IconProvider";
    private final List mCalendars;
    private final List mClocks;
    private final Context mContext;
    static final int CONFIG_ICON_MASK_RES_ID = Resources.getSystem().getIdentifier("config_icon_mask", "string", "android");
    public static final boolean ATLEAST_T = true;
    private final String ACTION_OVERLAY_CHANGED = "android.intent.action.OVERLAY_CHANGED";
    private final Pattern DOT_PATTERN = Pattern.compile("\\.");
    private final String UNDERSCORE = "_";
    protected String mSystemState = "";
    protected final HashMap mDynamicIconIdMap = new HashMap();
    private final List mDynamicIconApps = getPackagesWithDynamicIcon();

    public interface IconChangeListener {
    }

    class IconChangeReceiver extends BroadcastReceiver implements SafeCloseable {
        private String mIconState;
        private final Handler mWorker = new Handler(IconProvider.ICON_PROVIDER_HANDLER_THREAD.getLooper(), new Handler.Callback() { // from class: com.android.launcher3.icons.IconProvider$IconChangeReceiver$$ExternalSyntheticLambda4
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                return this.f$0.lambda$new$0(message);
            }
        });

        IconChangeReceiver(IconChangeListener iconChangeListener, Handler handler) {
            this.mIconState = IconProvider.this.getSystemIconState();
            IntentFilter intentFilter = new IntentFilter("android.intent.action.OVERLAY_CHANGED");
            intentFilter.addDataScheme("package");
            intentFilter.addDataSchemeSpecificPart("android", 0);
            IconProvider.this.mContext.registerReceiver(this, intentFilter, null, handler);
            if (!IconProvider.this.mCalendars.isEmpty() || !IconProvider.this.mClocks.isEmpty()) {
                IntentFilter intentFilter2 = new IntentFilter("android.intent.action.TIMEZONE_CHANGED");
                if (!IconProvider.this.mCalendars.isEmpty()) {
                    intentFilter2.addAction("android.intent.action.TIME_SET");
                    intentFilter2.addAction("android.intent.action.DATE_CHANGED");
                }
                IconProvider.this.mContext.registerReceiver(this, intentFilter2, null, handler);
            }
            if (IconProvider.this.mDynamicIconApps.isEmpty()) {
                return;
            }
            ContextCompat.registerReceiver(IconProvider.this.mContext, this, new IntentFilter(IconProvider.ACTION_DYNAMIC_ICON_CHANGED), "com.motorola.launcher3.permission.DYNAMIC_ICON", handler, 2);
        }

        private void handleOverlayChange() {
            CustomAppIcons.getInstance().resetConfig();
            String systemIconState = IconProvider.this.getSystemIconState();
            if (this.mIconState.equals(systemIconState)) {
                return;
            }
            this.mIconState = systemIconState;
            throw null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$new$0(Message message) {
            if (message.what != 1001) {
                return true;
            }
            handleOverlayChange();
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$1(UserHandle userHandle, String str) {
            throw null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$2(UserHandle userHandle, String str) {
            throw null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$4(UserHandle userHandle, String str) {
            throw null;
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            IconProvider.this.mContext.unregisterReceiver(this);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            action.hashCode();
            switch (action) {
                case "android.intent.action.OVERLAY_CHANGED":
                    this.mWorker.removeMessages(1001);
                    this.mWorker.sendEmptyMessageDelayed(1001, 800L);
                    break;
                case "com.motorola.launcher3.action.DYNAMIC_ICON_CHANGED":
                    final String packageNameParam = IconProvider.getPackageNameParam(intent);
                    if (packageNameParam != null) {
                        for (final UserHandle userHandle : ((UserManager) context.getSystemService(UserManager.class)).getUserProfiles()) {
                            IconProvider.this.mDynamicIconApps.stream().map(new IconProvider$$ExternalSyntheticLambda2()).filter(new Predicate() { // from class: com.android.launcher3.icons.IconProvider$IconChangeReceiver$$ExternalSyntheticLambda2
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    return Objects.equals((String) obj, packageNameParam);
                                }
                            }).forEach(new Consumer() { // from class: com.android.launcher3.icons.IconProvider$IconChangeReceiver$$ExternalSyntheticLambda3
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    this.f$0.lambda$onReceive$4(userHandle, (String) obj);
                                }
                            });
                        }
                        break;
                    }
                    break;
                case "android.intent.action.TIMEZONE_CHANGED":
                case "android.intent.action.TIME_SET":
                case "android.intent.action.DATE_CHANGED":
                    for (final UserHandle userHandle2 : ((UserManager) context.getSystemService(UserManager.class)).getUserProfiles()) {
                        if ("android.intent.action.TIMEZONE_CHANGED".equals(intent.getAction())) {
                            IconProvider.this.mClocks.stream().map(new IconProvider$$ExternalSyntheticLambda2()).forEach(new Consumer() { // from class: com.android.launcher3.icons.IconProvider$IconChangeReceiver$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    this.f$0.lambda$onReceive$1(userHandle2, (String) obj);
                                }
                            });
                        }
                        IconProvider.this.mCalendars.stream().map(new IconProvider$$ExternalSyntheticLambda2()).forEach(new Consumer() { // from class: com.android.launcher3.icons.IconProvider$IconChangeReceiver$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                this.f$0.lambda$onReceive$2(userHandle2, (String) obj);
                            }
                        });
                    }
                    break;
            }
        }
    }

    public class ThemeData {
        final int mResID;
        final Resources mResources;

        public ThemeData(Resources resources, int i) {
            this.mResources = resources;
            this.mResID = i;
        }

        Drawable loadPaddedDrawable() {
            if ("drawable".equals(this.mResources.getResourceTypeName(this.mResID))) {
                return new InsetDrawable(new InsetDrawable(this.mResources.getDrawable(this.mResID).mutate(), 0.2f), AdaptiveIconDrawable.getExtraInsetFraction() / ((AdaptiveIconDrawable.getExtraInsetFraction() * 2.0f) + 1.0f));
            }
            return null;
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$CKOsn6PXhfgefFROqfnDsqF-tE0, reason: not valid java name */
    public static /* synthetic */ boolean m1146$r8$lambda$CKOsn6PXhfgefFROqfnDsqFtE0(String str) {
        return !TextUtils.isEmpty(str);
    }

    /* JADX INFO: renamed from: $r8$lambda$sqIWn_pdvE-QfriWIHqGFkKgz5Q, reason: not valid java name */
    public static /* synthetic */ Drawable m1147$r8$lambda$sqIWn_pdvEQfriWIHqGFkKgz5Q(LauncherActivityInfo launcherActivityInfo, int i) {
        Drawable icon = launcherActivityInfo.getIcon(i);
        if (icon instanceof AdaptiveIconDrawable) {
            Drawable background = ((AdaptiveIconDrawable) icon).getBackground();
            if (background instanceof ColorDrawable) {
                Log.i(TAG, "getIcon color: " + Integer.toHexString(((ColorDrawable) background).getColor()) + ", componentName: " + launcherActivityInfo.getComponentName());
            }
        }
        return icon;
    }

    static {
        HandlerThread handlerThread = new HandlerThread("iconProvider", 0);
        ICON_PROVIDER_HANDLER_THREAD = handlerThread;
        handlerThread.start();
    }

    public IconProvider(Context context) {
        this.mContext = context;
        this.mCalendars = parseComponents(context, R$array.dynamic_icon_calendar_component_list);
        this.mClocks = parseComponents(context, R$array.dynamic_icon_clock_component_list);
    }

    private int getCalenderDynamicIconId(String str, Bundle bundle, Resources resources, AtomicInteger atomicInteger) {
        if (bundle == null) {
            return 0;
        }
        boolean isSystemUseRoundIcon = getIsSystemUseRoundIcon();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(isSystemUseRoundIcon ? ICON_ROUND_METADATA_KEY_PREFIX : ICON_METADATA_KEY_PREFIX);
        String string = sb.toString();
        int i = bundle.getInt(string, 0);
        if (i == 0) {
            return 0;
        }
        try {
            Calendar calendar = Calendar.getInstance();
            TypedArray typedArrayObtainTypedArray = resources.obtainTypedArray(i);
            int resourceId = typedArrayObtainTypedArray.getResourceId(getDay(calendar), 0);
            atomicInteger.set(getDayOfWeekId(resources, str, this.DOT_PATTERN.matcher(string).replaceAll("_") + "_week_", calendar));
            typedArrayObtainTypedArray.recycle();
            return resourceId;
        } catch (Resources.NotFoundException unused) {
            return 0;
        }
    }

    private static int getDay() {
        return getDay(Calendar.getInstance());
    }

    private static int getDay(Calendar calendar) {
        return calendar.get(5) - 1;
    }

    private static int getDayOfWeek() {
        return getDayOfWeek(Calendar.getInstance());
    }

    private static int getDayOfWeek(Calendar calendar) {
        return Calendar.getInstance().get(7) - 1;
    }

    private Drawable getDayOfWeekCalendarDrawable(AdaptiveIconDrawable adaptiveIconDrawable, Resources resources, int i, ThemeData themeData, int i2) {
        Drawable drawable = i != 0 ? resources.getDrawable(i) : null;
        boolean z = this.mContext.getResources().getBoolean(R$bool.test_day_of_week);
        if (z) {
            drawable = this.mContext.getDrawable(R$drawable.com_motorola_cn_calendar_dynamic_icons_week_1);
        }
        if (drawable == null) {
            return null;
        }
        Drawable background = adaptiveIconDrawable.getBackground();
        Drawable drawable2 = z ? this.mContext.getDrawable(R$drawable.ic_launcher_calendar_18_front) : adaptiveIconDrawable.getForeground();
        boolean z2 = (themeData == null || i2 == 0) ? false : true;
        if (!z2) {
            drawable2 = new LayerDrawable(new Drawable[]{drawable2, drawable});
        }
        Drawable drawableLoadPaddedDrawable = z2 ? new ThemeData(themeData.mResources, i2).loadPaddedDrawable() : adaptiveIconDrawable.getMonochrome();
        return new AdaptiveIconDrawable(background, drawable2, drawableLoadPaddedDrawable != null ? new LayerDrawable(new Drawable[]{drawableLoadPaddedDrawable, drawable}) : null);
    }

    public static int getDayOfWeekId(Resources resources, String str, String str2) {
        return getDayOfWeekId(resources, str, str2, Calendar.getInstance());
    }

    public static int getDayOfWeekId(Resources resources, String str, String str2, Calendar calendar) {
        Locale locale = Resources.getSystem().getConfiguration().getLocales().get(0);
        String lowerCase = (str2 + getDayOfWeek(calendar) + "_" + locale.getLanguage() + "_" + locale.getCountry()).toLowerCase();
        int identifier = resources.getIdentifier(lowerCase, "drawable", str);
        if (identifier == 0) {
            lowerCase = (str2 + getDayOfWeek(calendar) + "_" + locale.getLanguage()).toLowerCase();
            identifier = resources.getIdentifier(lowerCase, "drawable", str);
        }
        if (identifier == 0) {
            lowerCase = (str2 + getDayOfWeek(calendar)).toLowerCase();
            identifier = resources.getIdentifier(lowerCase, "drawable", str);
        }
        Log.d(TAG, "dayOfWeek: " + lowerCase + " | " + identifier);
        return identifier;
    }

    private Drawable getIconWithOverrides(Supplier supplier, int i, Supplier supplier2) {
        ComponentName componentName = (ComponentName) supplier.get();
        final String packageName = componentName.getPackageName();
        Drawable secureVaultAppIcon = VaultUtils.getSecureVaultAppIcon(this.mContext, packageName);
        if (secureVaultAppIcon != null) {
            return secureVaultAppIcon;
        }
        ThemeData themeDataForPackage = getThemeDataForPackage(packageName);
        Drawable drawableLoadIcon = isIconThemeSupported() ? null : CustomAppIcons.getInstance().loadIcon(this.mContext, componentName);
        if (this.mCalendars.stream().map(new IconProvider$$ExternalSyntheticLambda2()).anyMatch(new Predicate() { // from class: com.android.launcher3.icons.IconProvider$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.equals((String) obj, packageName);
            }
        })) {
            if (drawableLoadIcon == null) {
                drawableLoadIcon = loadCalendarDrawable(componentName, i, themeDataForPackage);
            }
        } else if (this.mClocks.stream().map(new IconProvider$$ExternalSyntheticLambda2()).anyMatch(new Predicate() { // from class: com.android.launcher3.icons.IconProvider$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.equals((String) obj, packageName);
            }
        })) {
            if (drawableLoadIcon == null) {
                drawableLoadIcon = loadClockDrawable(componentName, i, themeDataForPackage, null);
            }
        } else if (this.mDynamicIconApps.stream().map(new IconProvider$$ExternalSyntheticLambda2()).anyMatch(new Predicate() { // from class: com.android.launcher3.icons.IconProvider$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.equals((String) obj, packageName);
            }
        }) && drawableLoadIcon == null) {
            drawableLoadIcon = loadDynamicIconDrawable(componentName, i, themeDataForPackage);
        }
        if (drawableLoadIcon != null) {
            return drawableLoadIcon;
        }
        Drawable drawable = (Drawable) supplier2.get();
        if (!ATLEAST_T || !(drawable instanceof AdaptiveIconDrawable) || themeDataForPackage == null) {
            return drawable;
        }
        AdaptiveIconDrawable adaptiveIconDrawable = (AdaptiveIconDrawable) drawable;
        return adaptiveIconDrawable.getMonochrome() == null ? new AdaptiveIconDrawable(adaptiveIconDrawable.getBackground(), adaptiveIconDrawable.getForeground(), themeDataForPackage.loadPaddedDrawable()) : drawable;
    }

    private boolean getIsSystemUseRoundIcon() {
        try {
            return Resources.getSystem().getBoolean(((Integer) Class.forName("com.android.internal.R$bool").getField("config_useRoundIcon").get(null)).intValue());
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
            Log.e(TAG, "getIsSystemUseRoundIcon -> Exception", e);
            return false;
        }
    }

    public static String getPackageNameParam(Intent intent) {
        String stringExtra;
        ComponentName componentNameUnflattenFromString;
        String stringExtra2 = intent.getStringExtra("package");
        return (stringExtra2 != null || (stringExtra = intent.getStringExtra("component")) == null || (componentNameUnflattenFromString = ComponentName.unflattenFromString(stringExtra)) == null) ? stringExtra2 : componentNameUnflattenFromString.getPackageName();
    }

    public static String getSystemIconState(Context context) {
        StringBuilder sb = new StringBuilder();
        int i = CONFIG_ICON_MASK_RES_ID;
        sb.append(i == 0 ? "" : context.getResources().getString(i));
        sb.append(",accent:");
        sb.append(context.getColor(R.color.system_accent1_50));
        sb.append(",neutral:");
        sb.append(context.getColor(R.color.system_neutral1_50));
        return sb.toString();
    }

    protected static boolean isAtLeastPreReleaseCodename(String str, String str2) {
        if ("REL".equals(str2)) {
            return false;
        }
        Locale locale = Locale.ROOT;
        return str2.toUpperCase(locale).compareTo(str.toUpperCase(locale)) >= 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: loadActivityInfoIcon, reason: merged with bridge method [inline-methods] */
    public Drawable lambda$getIcon$4(ActivityInfo activityInfo, int i) {
        Drawable drawableForDensity;
        int iconResource = activityInfo.getIconResource();
        if (i == 0 || iconResource == 0) {
            drawableForDensity = null;
        } else {
            try {
                drawableForDensity = this.mContext.getPackageManager().getResourcesForApplication(activityInfo.applicationInfo).getDrawableForDensity(iconResource, i);
            } catch (PackageManager.NameNotFoundException | Resources.NotFoundException unused) {
                drawableForDensity = null;
            }
        }
        return drawableForDensity == null ? activityInfo.loadIcon(this.mContext.getPackageManager()) : drawableForDensity;
    }

    private Drawable loadCalendarDrawable(ComponentName componentName, int i, ThemeData themeData) {
        IconProvider iconProvider;
        String packageName = componentName.getPackageName();
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            Bundle bundle = packageManager.getActivityInfo(componentName, 8320).metaData;
            Resources resourcesForApplication = packageManager.getResourcesForApplication(packageName);
            AtomicInteger atomicInteger = new AtomicInteger(0);
            int calenderDynamicIconId = getCalenderDynamicIconId(packageName, bundle, resourcesForApplication, atomicInteger);
            if (calenderDynamicIconId != 0) {
                Drawable drawableForDensity = resourcesForApplication.getDrawableForDensity(calenderDynamicIconId, i, null);
                boolean z = ATLEAST_T;
                if (z && (drawableForDensity instanceof AdaptiveIconDrawable)) {
                    iconProvider = this;
                    Drawable dayOfWeekCalendarDrawable = iconProvider.getDayOfWeekCalendarDrawable((AdaptiveIconDrawable) drawableForDensity, resourcesForApplication, atomicInteger.get(), null, 0);
                    if (dayOfWeekCalendarDrawable != null) {
                        drawableForDensity = dayOfWeekCalendarDrawable;
                    }
                } else {
                    iconProvider = this;
                }
                if (z && (drawableForDensity instanceof AdaptiveIconDrawable) && themeData != null) {
                    AdaptiveIconDrawable adaptiveIconDrawable = (AdaptiveIconDrawable) drawableForDensity;
                    if (adaptiveIconDrawable.getMonochrome() == null && "array".equals(themeData.mResources.getResourceTypeName(themeData.mResID))) {
                        TypedArray typedArrayObtainTypedArray = themeData.mResources.obtainTypedArray(themeData.mResID);
                        int resourceId = typedArrayObtainTypedArray.getResourceId(getDay(), 0);
                        typedArrayObtainTypedArray.recycle();
                        if (resourceId != 0) {
                            Drawable dayOfWeekCalendarDrawable2 = iconProvider.getDayOfWeekCalendarDrawable(adaptiveIconDrawable, resourcesForApplication, atomicInteger.get(), themeData, resourceId);
                            return dayOfWeekCalendarDrawable2 == null ? new AdaptiveIconDrawable(adaptiveIconDrawable.getBackground(), adaptiveIconDrawable.getForeground(), new ThemeData(themeData.mResources, resourceId).loadPaddedDrawable()) : dayOfWeekCalendarDrawable2;
                        }
                    }
                }
                return drawableForDensity;
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return null;
    }

    private Drawable loadClockDrawable(ComponentName componentName, int i, ThemeData themeData, Drawable drawable) {
        return ClockDrawableWrapper.forPackage(this.mContext, componentName.getPackageName(), i, themeData, drawable);
    }

    private Drawable loadDynamicIconDrawable(ComponentName componentName, int i, ThemeData themeData) {
        Bundle bundleCall;
        String packageName = componentName.getPackageName();
        try {
            bundleCall = getContext().getContentResolver().call(packageName + ".DynamicIcon", "get_dynamic_icon", componentName.flattenToString(), (Bundle) null);
        } catch (Exception e) {
            Log.e(TAG, "Failed to get dynamic icon from provider", e);
            bundleCall = null;
        }
        String string = bundleCall == null ? null : bundleCall.getString("dynamicIconRes", null);
        Log.d(TAG, "loadDynamicIconDrawable: " + packageName + ", " + string);
        if (string != null) {
            try {
                Context contextCreatePackageContext = this.mContext.createPackageContext(packageName, 0);
                int identifier = contextCreatePackageContext.getResources().getIdentifier(string, "drawable", packageName);
                setDynamicIconId(packageName, identifier);
                if (identifier != 0) {
                    return contextCreatePackageContext.getResources().getDrawableForDensity(identifier, i, null);
                }
            } catch (Exception e2) {
                Log.e(TAG, "Failed to load dynamic icon drawable", e2);
            }
        }
        return null;
    }

    private static List parseComponents(Context context, int i) {
        return (List) Arrays.stream(context.getResources().getStringArray(i)).filter(new Predicate() { // from class: com.android.launcher3.icons.IconProvider$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return IconProvider.m1146$r8$lambda$CKOsn6PXhfgefFROqfnDsqFtE0((String) obj);
            }
        }).map(new Function() { // from class: com.android.launcher3.icons.IconProvider$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ComponentName.unflattenFromString((String) obj);
            }
        }).filter(new Predicate() { // from class: com.android.launcher3.icons.IconProvider$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.nonNull((ComponentName) obj);
            }
        }).collect(Collectors.toList());
    }

    public static boolean verifyMonoIcon(boolean z, Drawable drawable) {
        return z && ATLEAST_T && (drawable instanceof AdaptiveIconDrawable) && ((AdaptiveIconDrawable) drawable).getMonochrome() != null;
    }

    protected String getApplicationInfoHash(ApplicationInfo applicationInfo) {
        return applicationInfo.sourceDir;
    }

    public Context getContext() {
        return this.mContext;
    }

    public int getDynamicIconId(String str) {
        int iIntValue;
        synchronized (this.mDynamicIconIdMap) {
            iIntValue = ((Integer) this.mDynamicIconIdMap.getOrDefault(str, -1)).intValue();
        }
        return iIntValue;
    }

    public Drawable getFullResDefaultActivityIcon(int i) {
        Drawable drawableForDensity = Resources.getSystem().getDrawableForDensity(R.drawable.sym_def_app_icon, i);
        drawableForDensity.getClass();
        return drawableForDensity;
    }

    public Drawable getIcon(ActivityInfo activityInfo) {
        return getIcon(activityInfo, this.mContext.getResources().getConfiguration().densityDpi);
    }

    public Drawable getIcon(final ActivityInfo activityInfo, final int i) {
        return getIconWithOverrides(new Supplier() { // from class: com.android.launcher3.icons.IconProvider$$ExternalSyntheticLambda11
            @Override // java.util.function.Supplier
            public final Object get() {
                ActivityInfo activityInfo2 = activityInfo;
                return ComponentName.createRelative(activityInfo2.packageName, activityInfo2.name);
            }
        }, i, new Supplier() { // from class: com.android.launcher3.icons.IconProvider$$ExternalSyntheticLambda12
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.lambda$getIcon$4(activityInfo, i);
            }
        });
    }

    public Drawable getIcon(final LauncherActivityInfo launcherActivityInfo, final int i) {
        launcherActivityInfo.getClass();
        return getIconWithOverrides(new Supplier() { // from class: com.android.launcher3.icons.IconProvider$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return launcherActivityInfo.getComponentName();
            }
        }, i, new Supplier() { // from class: com.android.launcher3.icons.IconProvider$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return IconProvider.m1147$r8$lambda$sqIWn_pdvEQfriWIHqGFkKgz5Q(launcherActivityInfo, i);
            }
        });
    }

    public List getPackagesWithDynamicIcon() {
        ArrayList arrayList = new ArrayList();
        Iterator<ResolveInfo> it = this.mContext.getPackageManager().queryIntentActivities(new Intent(ACTION_DYNAMIC_ICON), 0).iterator();
        while (it.hasNext()) {
            ActivityInfo activityInfo = it.next().activityInfo;
            arrayList.add(new ComponentName(activityInfo.packageName, activityInfo.name));
        }
        if (arrayList.isEmpty()) {
            arrayList.addAll(parseComponents(this.mContext, R$array.dynamic_icon_component_list));
        }
        return arrayList;
    }

    public String getStateForApp(ApplicationInfo applicationInfo) {
        return applicationInfo == null ? this.mSystemState : getSystemStateForPackage(this.mSystemState, applicationInfo.packageName);
    }

    public String getSystemIconState() {
        return getSystemIconState(this.mContext);
    }

    public String getSystemState() {
        return this.mSystemState;
    }

    public String getSystemStateForPackage(String str, final String str2) {
        if (this.mCalendars.stream().map(new IconProvider$$ExternalSyntheticLambda2()).anyMatch(new Predicate() { // from class: com.android.launcher3.icons.IconProvider$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.equals((String) obj, str2);
            }
        })) {
            return str + SYSTEM_STATE_SEPARATOR + getDay();
        }
        if (!this.mDynamicIconApps.stream().map(new IconProvider$$ExternalSyntheticLambda2()).anyMatch(new Predicate() { // from class: com.android.launcher3.icons.IconProvider$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.equals((String) obj, str2);
            }
        })) {
            return str;
        }
        return str + SYSTEM_STATE_SEPARATOR + getDynamicIconId(str2);
    }

    protected ThemeData getThemeDataForPackage(String str) {
        return null;
    }

    protected boolean isClockIcon(ComponentKey componentKey) {
        throw null;
    }

    public boolean isIconThemeSupported() {
        return false;
    }

    public SafeCloseable registerIconChangeListener(IconChangeListener iconChangeListener, Handler handler) {
        return new IconChangeReceiver(iconChangeListener, handler);
    }

    public void setDynamicIconId(String str, int i) {
        synchronized (this.mDynamicIconIdMap) {
            this.mDynamicIconIdMap.put(str, Integer.valueOf(i));
        }
    }

    public void updateSystemState() {
        updateSystemState(this.mContext.getResources().getConfiguration().getLocales());
    }

    public void updateSystemState(LocaleList localeList) {
        this.mSystemState = localeList.toLanguageTags() + "," + Build.VERSION.SDK_INT;
    }
}
