package com.android.launcher3.icons;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.android.launcher3.icons.ImageUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public class IconPack {
    public static final IconPack DEFAULT_ICON_PACK;
    private static Paint sMaskPaint;
    private static Paint sMaskPaint2;
    private Map mAllIconPackages;
    private Context mContext;
    private Context mIconPackContext;
    private String mName;
    private String mPackageName;
    private String mTargetPackage;
    private final Bitmap mTestIcon;
    private final Canvas mTestIconCanvas;
    public static final Pattern COMPONENT_DRAWABLE_NAME_PATTERN = Pattern.compile("[.|/]");
    public static final Pattern DOUBLE_UNDERSCORE_PATTERN = Pattern.compile("__");
    public static final Pattern UNDERSCORE_PATTERN = Pattern.compile("_");
    private static Paint sPaint = new Paint(7);
    private boolean mLoaded = false;
    private Map mPreviewDrawables = new HashMap();
    private Map mComponentDrawables = new HashMap();
    private Map mPackageDrawables = new HashMap();
    private Map mCalendarComponentDrawables = new HashMap();
    private Map mCalendarPackageDrawables = new HashMap();
    private ArrayList mBackImageNames = new ArrayList();
    private List mBackImages = new ArrayList();
    private boolean mForceIconBack = false;
    private final Object mBackEdgePointsLock = new Object();
    private Bundle mBackHueColorBundle = new Bundle();
    private Map mBackHueColorMap = new HashMap();
    private String mMaskImageName = null;
    private Bitmap mMaskImage = null;
    private String mFrontImageName = null;
    private Bitmap mFrontImage = null;
    private float mFactor = 1.0f;

    class XppHolder {
        InputStream is;
        XmlPullParser xpp;

        private XppHolder(IconPack iconPack) {
        }
    }

    static {
        Paint paint = new Paint(7);
        sMaskPaint = paint;
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        Paint paint2 = new Paint(7);
        sMaskPaint2 = paint2;
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
        DEFAULT_ICON_PACK = new IconPack("system");
    }

    protected IconPack(Context context, String str, String str2) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        this.mTestIcon = bitmapCreateBitmap;
        this.mTestIconCanvas = new Canvas(bitmapCreateBitmap);
        this.mContext = context.getApplicationContext();
        this.mPackageName = str;
        this.mName = str2;
        logd("new IconPack - packageName=" + str + " | name=" + str2, new boolean[0]);
    }

    protected IconPack(String str) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        this.mTestIcon = bitmapCreateBitmap;
        this.mTestIconCanvas = new Canvas(bitmapCreateBitmap);
        this.mPackageName = str;
        logd("new IconPack - packageName=" + str, new boolean[0]);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        int i5 = 1;
        if (i3 <= i2 && i4 <= i) {
            return 1;
        }
        int i6 = i3 / 2;
        int i7 = i4 / 2;
        while (i6 / i5 >= i2 && i7 / i5 >= i) {
            i5 *= 2;
        }
        return i5;
    }

    public static Bitmap decodeIconBitmapFromResource(Resources resources, int i, int i2) {
        return decodeSampledBitmapFromResource(resources, i, i2, i2);
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources resources, int i, int i2, int i3) {
        if (i2 <= 0 || i3 <= 0) {
            return BitmapFactory.decodeResource(resources, i);
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, i, options);
        options.inSampleSize = calculateInSampleSize(options, i2, i3);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, i, options);
    }

    public static int generateResourceIndexWithCalendarDate() {
        return generateResourceIndexWithCalendarDate(false);
    }

    public static int generateResourceIndexWithCalendarDate(boolean z) {
        return Calendar.getInstance().get(5) - (z ? 1 : 0);
    }

    public static Bundle getBackHueColorBundle(Bundle bundle) {
        return bundle.getBundle("backHueColorBundle");
    }

    public static ArrayList getBackImageNames(Bundle bundle) {
        return bundle.getStringArrayList("backImageNames");
    }

    public static Map getCalendarComponentDrawables(Bundle bundle) {
        return JsonUtils.jsonToMap(GzipCompression.decompress(bundle.getByteArray("calendarComponentDrawables")));
    }

    private String getCalendarDrawable(String str, ExtraFgHolder extraFgHolder) {
        String str2 = (String) this.mCalendarComponentDrawables.get(str);
        if (str2 == null) {
            return str2;
        }
        String str3 = str2 + generateResourceIndexWithCalendarDate();
        if (extraFgHolder != null) {
            extraFgHolder.set(str2 + "week_");
            extraFgHolder.setCalendar(true);
        }
        return str3;
    }

    public static Map getCalendarPackageDrawables(Bundle bundle) {
        return JsonUtils.jsonToMap(GzipCompression.decompress(bundle.getByteArray("calendarPackageDrawables")));
    }

    public static Map getComponentDrawables(Bundle bundle) {
        return JsonUtils.jsonToMap(GzipCompression.decompress(bundle.getByteArray("componentDrawables")));
    }

    public static String getComponentNameFlattenString(String str) {
        return (str.contains("ComponentInfo{") && str.contains("}")) ? str.substring(14, str.indexOf("}")).toLowerCase() : str;
    }

    private String getDrawableName(ComponentName componentName, ExtraFgHolder extraFgHolder) {
        if (componentName == null) {
            return null;
        }
        if ("cai_testing".equals(componentName.getClassName())) {
            return (String) getAllIconPackages().get(componentName.getPackageName());
        }
        String strFlattenToString = componentName.flattenToString();
        String calendarDrawable = getCalendarDrawable(strFlattenToString, extraFgHolder);
        if (calendarDrawable == null) {
            calendarDrawable = (String) this.mComponentDrawables.get(strFlattenToString);
        }
        if (calendarDrawable == null) {
            calendarDrawable = (String) this.mComponentDrawables.get(new ComponentName(componentName.getPackageName(), "").flattenToString());
        }
        if (calendarDrawable == null) {
            calendarDrawable = (String) this.mPackageDrawables.get(componentName.getPackageName());
        }
        return (calendarDrawable != null || strFlattenToString == null) ? calendarDrawable : COMPONENT_DRAWABLE_NAME_PATTERN.matcher(strFlattenToString.toLowerCase()).replaceAll("_");
    }

    public static float getFactor(Bundle bundle) {
        return bundle.getFloat("factor");
    }

    public static boolean getForceIconBack(Bundle bundle) {
        return bundle.getBoolean("forceIconBack");
    }

    public static String getFrontImageName(Bundle bundle) {
        return bundle.getString("frontImageName");
    }

    public static float getHueColorFromColor(int i) {
        float[] fArr = new float[3];
        Color.colorToHSV(i, fArr);
        return fArr[0];
    }

    public static String getMaskImageName(Bundle bundle) {
        return bundle.getString("maskImageName");
    }

    private static String getPackageNameFlattenString(String str) {
        return (str.contains("PackageInfo{") && str.contains("PackageInfo{")) ? str.substring(12, str.indexOf("}")).toLowerCase() : str;
    }

    public static int getPaletteColorFromBitmap(Bitmap bitmap) {
        return 0;
    }

    public static boolean isAppEnabled(PackageManager packageManager, String str) {
        return isAppEnabled(packageManager, str, 0);
    }

    public static boolean isAppEnabled(PackageManager packageManager, String str, int i) {
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, i);
            if (applicationInfo != null) {
                if (applicationInfo.enabled) {
                    return true;
                }
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return false;
    }

    private void loadAppIconRes(XmlPullParser xmlPullParser) {
        String componentNameFlattenString = null;
        String attributeValue = null;
        for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
            if (xmlPullParser.getAttributeName(i).equalsIgnoreCase("name")) {
                componentNameFlattenString = getComponentNameFlattenString(xmlPullParser.getAttributeValue(i));
            } else if (xmlPullParser.getAttributeName(i).equalsIgnoreCase("image")) {
                attributeValue = xmlPullParser.getAttributeValue(i);
            }
        }
        if (TextUtils.isEmpty(componentNameFlattenString)) {
            return;
        }
        if (!this.mComponentDrawables.containsKey(componentNameFlattenString)) {
            this.mComponentDrawables.put(componentNameFlattenString, attributeValue);
        }
        ComponentName componentNameUnflattenFromString = ComponentName.unflattenFromString(componentNameFlattenString);
        if (componentNameUnflattenFromString != null) {
            String strFlattenToString = new ComponentName(componentNameUnflattenFromString.getPackageName(), "").flattenToString();
            if (this.mComponentDrawables.containsKey(strFlattenToString)) {
                return;
            }
            this.mComponentDrawables.put(strFlattenToString, attributeValue);
        }
    }

    private Bitmap loadBitmap(Context context, int i, int i2) {
        Resources iconPackRes;
        if (i == 0 || (iconPackRes = getIconPackRes(context)) == null) {
            return null;
        }
        return decodeIconBitmapFromResource(iconPackRes, i, i2);
    }

    private Bitmap loadBitmap(Context context, String str) {
        return loadBitmap(context, str, 0);
    }

    private Bitmap loadBitmap(Context context, String str, int i) {
        Resources iconPackRes;
        if (str == null || (iconPackRes = getIconPackRes(context)) == null) {
            return null;
        }
        int identifier = iconPackRes.getIdentifier(str, "mipmap", getTargetPackage());
        if (identifier == 0) {
            identifier = iconPackRes.getIdentifier(str, "drawable", getTargetPackage());
        }
        return loadBitmap(context, identifier, i);
    }

    private void loadCalendarRes(XmlPullParser xmlPullParser) {
        String componentNameFlattenString = null;
        String attributeValue = null;
        for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
            if (xmlPullParser.getAttributeName(i).equalsIgnoreCase("component")) {
                componentNameFlattenString = getComponentNameFlattenString(xmlPullParser.getAttributeValue(i));
            } else if (xmlPullParser.getAttributeName(i).equalsIgnoreCase("prefix")) {
                attributeValue = xmlPullParser.getAttributeValue(i);
            }
        }
        if (TextUtils.isEmpty(componentNameFlattenString)) {
            return;
        }
        if (!this.mCalendarComponentDrawables.containsKey(componentNameFlattenString)) {
            this.mCalendarComponentDrawables.put(componentNameFlattenString, attributeValue);
        }
        ComponentName componentNameUnflattenFromString = ComponentName.unflattenFromString(componentNameFlattenString);
        if (componentNameUnflattenFromString == null || this.mCalendarPackageDrawables.containsKey(componentNameUnflattenFromString.getPackageName())) {
            return;
        }
        this.mCalendarPackageDrawables.put(componentNameUnflattenFromString.getPackageName(), attributeValue);
    }

    private void loadComponentRes(XmlPullParser xmlPullParser) {
        String componentNameFlattenString = null;
        String attributeValue = null;
        String packageNameFlattenString = null;
        for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
            if (xmlPullParser.getAttributeName(i).equalsIgnoreCase("component")) {
                componentNameFlattenString = getComponentNameFlattenString(xmlPullParser.getAttributeValue(i));
            } else if (xmlPullParser.getAttributeName(i).equalsIgnoreCase("drawable")) {
                attributeValue = xmlPullParser.getAttributeValue(i);
            } else if (xmlPullParser.getAttributeName(i).equalsIgnoreCase("package")) {
                packageNameFlattenString = getPackageNameFlattenString(xmlPullParser.getAttributeValue(i));
            }
        }
        if (TextUtils.isEmpty(componentNameFlattenString)) {
            if (TextUtils.isEmpty(packageNameFlattenString) || this.mPackageDrawables.containsKey(packageNameFlattenString)) {
                return;
            }
            this.mPackageDrawables.put(packageNameFlattenString, attributeValue);
            return;
        }
        if (!this.mComponentDrawables.containsKey(componentNameFlattenString)) {
            this.mComponentDrawables.put(componentNameFlattenString, attributeValue);
        }
        ComponentName componentNameUnflattenFromString = ComponentName.unflattenFromString(componentNameFlattenString);
        if (componentNameUnflattenFromString != null) {
            String strFlattenToString = new ComponentName(componentNameUnflattenFromString.getPackageName(), "").flattenToString();
            if (this.mComponentDrawables.containsKey(strFlattenToString)) {
                return;
            }
            this.mComponentDrawables.put(strFlattenToString, attributeValue);
        }
    }

    private void loadDrawableRes(XmlPullParser xmlPullParser) {
        String strNextText = null;
        String strReplaceAll = null;
        for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
            if (xmlPullParser.getAttributeName(i).equalsIgnoreCase("name")) {
                strReplaceAll = UNDERSCORE_PATTERN.matcher(DOUBLE_UNDERSCORE_PATTERN.split(xmlPullParser.getAttributeValue(i))[0]).replaceAll(".");
            }
        }
        try {
            strNextText = xmlPullParser.nextText();
            if (strNextText.startsWith("@drawable/")) {
                strNextText = strNextText.substring(10);
            }
        } catch (Exception e) {
            Log.d("IconPack", "Get drawable value failed", e);
        }
        if (TextUtils.isEmpty(strReplaceAll)) {
            return;
        }
        String strFlattenToString = new ComponentName(strReplaceAll, "").flattenToString();
        if (this.mComponentDrawables.containsKey(strFlattenToString)) {
            return;
        }
        this.mComponentDrawables.put(strFlattenToString, strNextText);
    }

    private void loadFromIconPackInfo(Context context, Bundle bundle) {
        this.mComponentDrawables.putAll(getComponentDrawables(bundle));
        this.mCalendarComponentDrawables.putAll(getCalendarComponentDrawables(bundle));
        this.mCalendarPackageDrawables.putAll(getCalendarPackageDrawables(bundle));
        ArrayList backImageNames = getBackImageNames(bundle);
        if (backImageNames != null) {
            this.mBackImageNames.addAll(backImageNames);
        }
        this.mForceIconBack = getForceIconBack(bundle);
        Bundle backHueColorBundle = getBackHueColorBundle(bundle);
        if (backHueColorBundle != null) {
            this.mBackHueColorBundle.putAll(backHueColorBundle);
        }
        this.mMaskImageName = getMaskImageName(bundle);
        this.mFrontImageName = getFrontImageName(bundle);
        this.mFactor = getFactor(bundle);
        ArrayList arrayList = this.mBackImageNames;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            String str = (String) obj;
            Bitmap bitmapResizeMaxSized = resizeMaxSized(loadBitmap(context, str), 256);
            if (bitmapResizeMaxSized != null) {
                this.mBackImages.add(bitmapResizeMaxSized);
                this.mBackHueColorMap.put(bitmapResizeMaxSized, Float.valueOf(this.mBackHueColorBundle.getFloat(str)));
            }
        }
        this.mMaskImage = resizeMaxSized(loadBitmap(context, this.mMaskImageName), 256);
        this.mFrontImage = resizeMaxSized(loadBitmap(context, this.mFrontImageName), 256);
    }

    private void loadFromXml(Context context) {
        XppHolder xppHolder = null;
        try {
            try {
                try {
                    XppHolder xppHolderLoadXpp = loadXpp(context, "icon_config");
                    if (xppHolderLoadXpp != null) {
                        loadIcons(context, xppHolderLoadXpp.xpp);
                    } else {
                        xppHolderLoadXpp = loadXpp(context, "theme_resources");
                        if (xppHolderLoadXpp != null) {
                            loadIcons(context, xppHolderLoadXpp.xpp);
                        } else {
                            xppHolderLoadXpp = loadXpp(context, "icons");
                            if (xppHolderLoadXpp != null) {
                                loadIcons(context, xppHolderLoadXpp.xpp);
                            }
                        }
                    }
                    if (xppHolderLoadXpp != null) {
                        XmlPullParser xmlPullParser = xppHolderLoadXpp.xpp;
                        if (xmlPullParser instanceof XmlResourceParser) {
                            ((XmlResourceParser) xmlPullParser).close();
                        }
                        InputStream inputStream = xppHolderLoadXpp.is;
                        if (inputStream != null) {
                            GzipCompression.closeQuietly(inputStream);
                        }
                    }
                } catch (Exception unused) {
                }
            } catch (Exception unused2) {
                logd("Cannot load icons in iconPack " + getTargetPackage(), new boolean[0]);
                if (0 != 0) {
                    XmlPullParser xmlPullParser2 = xppHolder.xpp;
                    if (xmlPullParser2 instanceof XmlResourceParser) {
                        ((XmlResourceParser) xmlPullParser2).close();
                    }
                    InputStream inputStream2 = xppHolder.is;
                    if (inputStream2 != null) {
                        GzipCompression.closeQuietly(inputStream2);
                    }
                }
            }
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    XmlPullParser xmlPullParser3 = xppHolder.xpp;
                    if (xmlPullParser3 instanceof XmlResourceParser) {
                        ((XmlResourceParser) xmlPullParser3).close();
                    }
                    InputStream inputStream3 = xppHolder.is;
                    if (inputStream3 != null) {
                        GzipCompression.closeQuietly(inputStream3);
                    }
                } catch (Exception unused3) {
                }
            }
            throw th;
        }
    }

    private void loadIconBack(Context context, XmlPullParser xmlPullParser) {
        for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
            if (xmlPullParser.getAttributeName(i).startsWith("img")) {
                String attributeValue = xmlPullParser.getAttributeValue(i);
                Bitmap bitmapLoadBitmap = loadBitmap(context, attributeValue);
                if (bitmapLoadBitmap != null) {
                    this.mBackImages.add(bitmapLoadBitmap);
                    this.mBackImageNames.add(attributeValue);
                }
            } else if (xmlPullParser.getAttributeName(i).equalsIgnoreCase("force")) {
                this.mForceIconBack = Boolean.valueOf(xmlPullParser.getAttributeValue(i)).booleanValue();
            }
        }
    }

    private void loadIconMask(Context context, XmlPullParser xmlPullParser) {
        if (xmlPullParser.getAttributeCount() <= 0 || !xmlPullParser.getAttributeName(0).equals("img1")) {
            return;
        }
        String attributeValue = xmlPullParser.getAttributeValue(0);
        this.mMaskImage = loadBitmap(context, attributeValue);
        this.mMaskImageName = attributeValue;
    }

    private void loadIconUpon(Context context, XmlPullParser xmlPullParser) {
        if (xmlPullParser.getAttributeCount() <= 0 || !xmlPullParser.getAttributeName(0).equals("img1")) {
            return;
        }
        String attributeValue = xmlPullParser.getAttributeValue(0);
        this.mFrontImage = loadBitmap(context, attributeValue);
        this.mFrontImageName = attributeValue;
    }

    private void loadIcons(Context context, XmlPullParser xmlPullParser) {
        if (xmlPullParser == null) {
            return;
        }
        try {
            int eventType = xmlPullParser.getEventType();
            while (eventType != 1) {
                if (eventType == 2) {
                    if (xmlPullParser.getName().equalsIgnoreCase("preview")) {
                        loadPreviewRes(xmlPullParser);
                    }
                    if (xmlPullParser.getName().equalsIgnoreCase("item")) {
                        loadComponentRes(xmlPullParser);
                    } else if (xmlPullParser.getName().equalsIgnoreCase("calendar")) {
                        loadCalendarRes(xmlPullParser);
                    } else if (xmlPullParser.getName().equalsIgnoreCase("AppIcon")) {
                        loadAppIconRes(xmlPullParser);
                    } else if (xmlPullParser.getName().equals("iconback")) {
                        loadIconBack(context, xmlPullParser);
                    } else if (xmlPullParser.getName().equals("iconmask")) {
                        loadIconMask(context, xmlPullParser);
                    } else if (xmlPullParser.getName().equals("iconupon")) {
                        loadIconUpon(context, xmlPullParser);
                    } else if (xmlPullParser.getName().equals("scale")) {
                        loadScale(xmlPullParser);
                    } else if (xmlPullParser.getName().equalsIgnoreCase("drawable")) {
                        loadDrawableRes(xmlPullParser);
                    }
                }
                eventType = xmlPullParser.next();
            }
        } catch (IOException | XmlPullParserException e) {
            Log.d("IconPack", "Load icons failed", e);
        }
        for (int i = 0; i < this.mBackImages.size(); i++) {
            Bitmap bitmap = (Bitmap) this.mBackImages.get(i);
            float hueColorFromColor = getHueColorFromColor(getPaletteColorFromBitmap(bitmap));
            this.mBackHueColorMap.put(bitmap, Float.valueOf(hueColorFromColor));
            this.mBackHueColorBundle.putFloat((String) this.mBackImageNames.get(i), hueColorFromColor);
        }
    }

    private void loadPreviewRes(XmlPullParser xmlPullParser) {
        String attributeValue = null;
        String attributeValue2 = null;
        for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
            if (xmlPullParser.getAttributeName(i).equalsIgnoreCase("orientation")) {
                attributeValue = xmlPullParser.getAttributeValue(i);
            } else if (xmlPullParser.getAttributeName(i).equalsIgnoreCase("drawable")) {
                attributeValue2 = xmlPullParser.getAttributeValue(i);
            }
        }
        if (TextUtils.isEmpty(attributeValue) || this.mPreviewDrawables.containsKey(attributeValue)) {
            return;
        }
        this.mPreviewDrawables.put(attributeValue, attributeValue2);
    }

    private void loadScale(XmlPullParser xmlPullParser) {
        if (xmlPullParser.getAttributeCount() <= 0 || !xmlPullParser.getAttributeName(0).equals("factor")) {
            return;
        }
        try {
            this.mFactor = Float.valueOf(xmlPullParser.getAttributeValue(0)).floatValue();
        } catch (NumberFormatException unused) {
            this.mFactor = 1.0f;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private com.android.launcher3.icons.IconPack.XppHolder loadXpp(android.content.Context r7, java.lang.String r8) {
        /*
            r6 = this;
            r0 = 0
            android.content.res.Resources r7 = r6.getIconPackRes(r7)     // Catch: java.lang.Throwable -> L19
            if (r7 != 0) goto L8
            return r0
        L8:
            java.lang.String r1 = "xml"
            java.lang.String r2 = r6.getTargetPackage()     // Catch: java.lang.Throwable -> L19
            int r1 = r7.getIdentifier(r8, r1, r2)     // Catch: java.lang.Throwable -> L19
            if (r1 == 0) goto L1d
            android.content.res.XmlResourceParser r1 = r7.getXml(r1)     // Catch: java.lang.Throwable -> L19
            goto L1e
        L19:
            r7 = move-exception
            r1 = r0
            r2 = r1
            goto L57
        L1d:
            r1 = r0
        L1e:
            if (r1 != 0) goto L55
            android.content.res.AssetManager r7 = r7.getAssets()     // Catch: java.lang.Throwable -> L51
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L51
            r2.<init>()     // Catch: java.lang.Throwable -> L51
            r2.append(r8)     // Catch: java.lang.Throwable -> L51
            java.lang.String r3 = ".xml"
            r2.append(r3)     // Catch: java.lang.Throwable -> L51
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> L51
            java.io.InputStream r7 = r7.open(r2)     // Catch: java.lang.Throwable -> L51
            org.xmlpull.v1.XmlPullParserFactory r2 = org.xmlpull.v1.XmlPullParserFactory.newInstance()     // Catch: java.lang.Throwable -> L4b
            r3 = 1
            r2.setNamespaceAware(r3)     // Catch: java.lang.Throwable -> L4b
            org.xmlpull.v1.XmlPullParser r1 = r2.newPullParser()     // Catch: java.lang.Throwable -> L4b
            java.lang.String r2 = "utf-8"
            r1.setInput(r7, r2)     // Catch: java.lang.Throwable -> L4b
            goto L74
        L4b:
            r2 = move-exception
            r5 = r1
            r1 = r7
            r7 = r2
            r2 = r5
            goto L57
        L51:
            r7 = move-exception
            r2 = r1
            r1 = r0
            goto L57
        L55:
            r7 = r0
            goto L74
        L57:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Load xpp for "
            r3.append(r4)
            r3.append(r8)
            java.lang.String r8 = ".xml failed"
            r3.append(r8)
            java.lang.String r8 = r3.toString()
            java.lang.String r3 = "IconPack"
            android.util.Log.d(r3, r8, r7)
            r7 = r1
            r1 = r2
        L74:
            if (r1 == 0) goto L80
            com.android.launcher3.icons.IconPack$XppHolder r8 = new com.android.launcher3.icons.IconPack$XppHolder
            r8.<init>()
            r8.xpp = r1
            r8.is = r7
            r0 = r8
        L80:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.launcher3.icons.IconPack.loadXpp(android.content.Context, java.lang.String):com.android.launcher3.icons.IconPack$XppHolder");
    }

    public static void logd(String str, boolean... zArr) {
        logd(Logger.DEBUG, "IconPack", str, zArr);
    }

    public static void logd(boolean z, String str, String str2, boolean... zArr) {
        if (z) {
            if (zArr == null || zArr.length <= 0 || !zArr[0]) {
                Log.d(str, str2);
                return;
            }
            Log.d(str, str2, new RuntimeException("" + System.currentTimeMillis()));
        }
    }

    private Bitmap resizeMaxSized(Bitmap bitmap, int i) {
        if (bitmap == null) {
            return null;
        }
        return (bitmap.getWidth() > i || bitmap.getHeight() > i) ? ImageUtils.createScaledBitmapByWidth(bitmap, i, ImageUtils.ScalingLogic.CROP) : bitmap;
    }

    public boolean equals(Object obj) {
        String str;
        if ((obj instanceof IconPack) && (str = this.mPackageName) != null && str.equals(((IconPack) obj).mPackageName)) {
            return true;
        }
        return super.equals(obj);
    }

    public Map getAllIconPackages() {
        if (this.mAllIconPackages == null) {
            synchronized (this) {
                if (this.mAllIconPackages == null) {
                    this.mAllIconPackages = new HashMap();
                    for (Map.Entry entry : this.mComponentDrawables.entrySet()) {
                        try {
                            ComponentName componentNameUnflattenFromString = ComponentName.unflattenFromString((String) entry.getKey());
                            if (!this.mAllIconPackages.containsKey(componentNameUnflattenFromString.getPackageName())) {
                                this.mAllIconPackages.put(componentNameUnflattenFromString.getPackageName(), (String) entry.getValue());
                            }
                        } catch (Exception e) {
                            Log.d("IconPack", "Get component drawables package error: " + entry, e);
                        }
                    }
                    for (Map.Entry entry2 : this.mPackageDrawables.entrySet()) {
                        try {
                            if (!this.mAllIconPackages.containsKey(entry2.getKey())) {
                                this.mAllIconPackages.put((String) entry2.getKey(), (String) entry2.getValue());
                            }
                        } catch (Exception e2) {
                            Log.d("IconPack", "Get package drawables package error: " + entry2, e2);
                        }
                    }
                    int iGenerateResourceIndexWithCalendarDate = generateResourceIndexWithCalendarDate();
                    for (Map.Entry entry3 : this.mCalendarComponentDrawables.entrySet()) {
                        try {
                            ComponentName componentNameUnflattenFromString2 = ComponentName.unflattenFromString((String) entry3.getKey());
                            if (!this.mAllIconPackages.containsKey(componentNameUnflattenFromString2.getPackageName())) {
                                this.mAllIconPackages.put(componentNameUnflattenFromString2.getPackageName(), ((String) entry3.getValue()) + iGenerateResourceIndexWithCalendarDate);
                            }
                        } catch (Exception e3) {
                            Log.d("IconPack", "Get calendar component drawables package error: " + entry3, e3);
                        }
                    }
                    for (Map.Entry entry4 : this.mCalendarPackageDrawables.entrySet()) {
                        try {
                            if (!this.mAllIconPackages.containsKey(entry4.getKey())) {
                                this.mAllIconPackages.put((String) entry4.getKey(), ((String) entry4.getValue()) + iGenerateResourceIndexWithCalendarDate);
                            }
                        } catch (Exception e4) {
                            Log.d("IconPack", "Get calendar package drawables package error: " + entry4, e4);
                        }
                    }
                }
            }
        }
        return this.mAllIconPackages;
    }

    public Drawable getAppIcon(Context context, String str, String str2) {
        load(context);
        String lowerCase = str == null ? "" : str.toLowerCase();
        String lowerCase2 = str2 != null ? str2.toLowerCase() : "";
        Resources iconPackRes = getIconPackRes(context);
        ExtraFgHolder extraFgHolder = new ExtraFgHolder();
        String drawableName = getDrawableName(new ComponentName(lowerCase, lowerCase2), extraFgHolder);
        if (drawableName != null && iconPackRes != null) {
            int identifier = iconPackRes.getIdentifier(drawableName, "mipmap", getTargetPackage());
            if (identifier == 0) {
                identifier = iconPackRes.getIdentifier(drawableName, "drawable", getTargetPackage());
            }
            if (identifier != 0) {
                Drawable drawable = iconPackRes.getDrawable(identifier);
                if (drawable instanceof AdaptiveIconDrawable) {
                    if (extraFgHolder.get() != null) {
                        int dayOfWeekId = extraFgHolder.isCalendar() ? IconProvider.getDayOfWeekId(iconPackRes, lowerCase, extraFgHolder.get()) : 0;
                        Drawable drawable2 = dayOfWeekId != 0 ? iconPackRes.getDrawable(dayOfWeekId) : null;
                        boolean z = this.mContext.getResources().getBoolean(R$bool.test_day_of_week);
                        if (z) {
                            drawable2 = this.mContext.getDrawable(R$drawable.appicon_calendar_week_1);
                        }
                        if (drawable2 != null) {
                            AdaptiveIconDrawable adaptiveIconDrawable = (AdaptiveIconDrawable) drawable;
                            return new AdaptiveIconDrawable(adaptiveIconDrawable.getBackground(), new LayerDrawable(new Drawable[]{z ? this.mContext.getDrawable(R$drawable.ic_launcher_calendar_18_front) : adaptiveIconDrawable.getForeground(), drawable2}));
                        }
                    }
                    return drawable;
                }
            }
        }
        return null;
    }

    public Map getComponentDrawables() {
        return this.mComponentDrawables;
    }

    public Context getIconPackContext(Context context) {
        if (this.mIconPackContext == null) {
            synchronized (this) {
                if (this.mIconPackContext == null) {
                    try {
                        this.mIconPackContext = context.createPackageContext(getTargetPackage(), 0);
                    } catch (PackageManager.NameNotFoundException e) {
                        if (Logger.DEBUG) {
                            Log.d("IconPack", "getIconPackContext error: ", e);
                        }
                    }
                }
            }
        }
        return this.mIconPackContext;
    }

    public Resources getIconPackRes(Context context) {
        Context iconPackContext = getIconPackContext(context);
        if (iconPackContext != null) {
            return iconPackContext.getResources();
        }
        return null;
    }

    public Map getPackageDrawables() {
        return this.mPackageDrawables;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getTargetPackage() {
        String str = this.mTargetPackage;
        return str != null ? str : this.mPackageName;
    }

    public boolean isDefault() {
        return "system".equals(getPackageName());
    }

    public void load(Context context) {
        if (this.mLoaded) {
            return;
        }
        load(context, null);
    }

    public synchronized void load(Context context, Bundle bundle) {
        try {
            if (this.mLoaded) {
                return;
            }
            if (!IconPackExt.load(this, context, bundle)) {
                if (bundle != null) {
                    loadFromIconPackInfo(context, bundle);
                } else {
                    loadFromXml(context);
                }
            }
            this.mLoaded = true;
            Log.d("IconPack", "Loaded icons: " + getComponentDrawables());
        } catch (Throwable th) {
            throw th;
        }
    }

    public void resetConfig() {
        this.mIconPackContext = null;
    }

    public void setTargetPackage(String str) {
        this.mTargetPackage = str;
    }
}
