package Y;

import X.Q0;
import X.v0;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.media.AudioManager;
import android.media.AudioRecordingConfiguration;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AbstractC0054a;
import androidx.transition.R$id;
import com.motorola.mobiledesktop.core.MotoDesktopService;
import com.motorola.mobiledesktop.core.bean.AppInfo;
import com.motorola.mobiledesktop.core.bean.MotoRunningTaskInfo;
import com.motorola.mobiledesktop.core.bean.MotoRunningTaskInfoNew;
import com.motorola.mobiledesktop.core.bean.NewMotoRunningTaskInfo;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import o.AbstractC0153b;

/* JADX INFO: loaded from: classes.dex */
public abstract class r {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static Boolean f427a = null;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static Boolean f428b = null;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static boolean f429c = false;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public static Method f430d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public static boolean f431e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public static Method f432f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public static boolean f433g;

    public static ArrayList A(MotoDesktopService motoDesktopService) {
        List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) motoDesktopService.getSystemService("activity")).getRunningTasks(Integer.MAX_VALUE);
        ArrayList arrayList = new ArrayList();
        for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTasks) {
            MotoRunningTaskInfoNew motoRunningTaskInfoNew = new MotoRunningTaskInfoNew();
            motoRunningTaskInfoNew.setRunningTaskInfo(runningTaskInfo);
            arrayList.add(motoRunningTaskInfoNew);
            v0.a("AppUtils", "getRunningTaskInfos list:" + runningTaskInfo);
        }
        return arrayList;
    }

    public static ArrayList B(MotoDesktopService motoDesktopService, int i2) {
        List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) motoDesktopService.getSystemService("activity")).getRunningTasks(Integer.MAX_VALUE);
        ArrayList arrayList = new ArrayList();
        for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTasks) {
            if (runningTaskInfo.displayId == i2) {
                MotoRunningTaskInfoNew motoRunningTaskInfoNew = new MotoRunningTaskInfoNew();
                motoRunningTaskInfoNew.setRunningTaskInfo(runningTaskInfo);
                arrayList.add(motoRunningTaskInfoNew);
            }
        }
        return arrayList;
    }

    public static String C(Context context, int i2) {
        Intent intent;
        for (ActivityManager.RunningTaskInfo runningTaskInfo : ((ActivityManager) context.getSystemService("activity")).getRunningTasks(Integer.MAX_VALUE)) {
            if (i2 == runningTaskInfo.taskId && (intent = runningTaskInfo.baseIntent) != null && intent.getComponent() != null) {
                return intent.getComponent().getPackageName();
            }
        }
        return null;
    }

    public static String D(Object obj, String str) {
        try {
            Field declaredField = Class.forName(str).getDeclaredField("mGlobalRouteId");
            declaredField.setAccessible(true);
            return (String) declaredField.get(obj);
        } catch (Exception e2) {
            v0.c("r", "getStringFieldValue error", e2);
            return null;
        }
    }

    public static AppInfo F(int i2) {
        Intent intent;
        try {
            ActivityManager.RunningTaskInfo topVisibleTask = ActivityTaskManager.getService().getTopVisibleTask(i2);
            if (topVisibleTask == null || (intent = topVisibleTask.baseIntent) == null || intent.getComponent() == null) {
                return null;
            }
            String packageName = intent.getComponent().getPackageName();
            int i3 = topVisibleTask.taskId;
            int i4 = topVisibleTask.displayId;
            int i5 = topVisibleTask.userId;
            return new AppInfo(i3, packageName, i4, i5, k(i5, packageName));
        } catch (NoSuchMethodError e2) {
            v0.b("AppUtils", "getTopTaskAppInfo NoSuchMethodError:" + e2.toString());
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x005d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static int K(int r5) {
        /*
            r0 = r5 & 255(0xff, float:3.57E-43)
            java.lang.String r1 = "VIRTUAL_DISPLAY_FLAG_RDP"
            java.lang.String r2 = "android.hardware.display.DisplayManager"
            if (r0 != 0) goto Lb
            r5 = 17
            goto L5e
        Lb:
            java.lang.String r3 = "VIRTUAL_DISPLAY_FLAG_SHOULD_SHOW_SYSTEM_DECORATIONS"
            int r3 = X.Q0.d(r2, r3)
            r4 = 11
            r3 = r3 | r4
            java.lang.String r4 = "VIRTUAL_DISPLAY_FLAG_SUPPORTS_TOUCH"
            int r4 = X.Q0.d(r2, r4)
            r3 = r3 | r4
            int r4 = X.Q0.d(r2, r1)
            r3 = r3 | r4
            java.lang.String r4 = "VIRTUAL_DISPLAY_FLAG_TRUSTED"
            int r4 = X.Q0.d(r2, r4)
            r3 = r3 | r4
            java.lang.String r4 = "VIRTUAL_DISPLAY_FLAG_OWN_DISPLAY_GROUP"
            int r4 = X.Q0.d(r2, r4)
            r3 = r3 | r4
            r4 = 3
            if (r0 != r4) goto L39
            java.lang.String r0 = "VIRTUAL_DISPLAY_FLAG_MOT_APP_STREAM"
            int r0 = X.Q0.d(r2, r0)
        L37:
            r3 = r3 | r0
            goto L44
        L39:
            r4 = 2
            if (r0 != r4) goto L3d
            goto L44
        L3d:
            java.lang.String r0 = "VIRTUAL_DISPLAY_FLAG_WINDOWING_MODE_FREEFORM"
            int r0 = X.Q0.d(r2, r0)
            goto L37
        L44:
            r0 = r5 & 256(0x100, float:3.59E-43)
            if (r0 == 0) goto L50
            java.lang.String r0 = "VIRTUAL_DISPLAY_FLAG_ROTATES_WITH_CONTENT"
            int r0 = X.Q0.d(r2, r0)
            r0 = r0 | r3
            goto L51
        L50:
            r0 = r3
        L51:
            r5 = r5 & 512(0x200, float:7.175E-43)
            if (r5 == 0) goto L5d
            java.lang.String r5 = "VIRTUAL_DISPLAY_FLAG_MOT_SUPPORTS_PC_IME"
            int r5 = X.Q0.d(r2, r5)
            r5 = r5 | r0
            goto L5e
        L5d:
            r5 = r0
        L5e:
            int r0 = X.Q0.d(r2, r1)
            r5 = r5 | r0
            r5 = r5 | 4
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: Y.r.K(int):int");
    }

    public static boolean L() {
        return !TextUtils.isEmpty(Q0.f295h);
    }

    public static boolean N() {
        Boolean bool;
        String str;
        Boolean bool2 = f427a;
        if (bool2 != null) {
            return bool2.booleanValue();
        }
        try {
            bool = Boolean.TRUE;
            f427a = bool;
            str = AbstractC0047a.f354e;
        } catch (Exception e2) {
            v0.b("isAndroidTNpiProjectsOrLater", e2.getMessage());
        }
        if (TextUtils.isEmpty(str)) {
            return f427a.booleanValue();
        }
        v0.a("AudioUtils", "isAndroidTNpiProjectsOrLater product wave " + str);
        String[] strArrSplit = str.split("\\.");
        String[] strArrSplit2 = "2023.1".split("\\.");
        if (strArrSplit != null && strArrSplit.length == 2 && strArrSplit2 != null && strArrSplit2.length == 2) {
            int i2 = Integer.parseInt(strArrSplit[0]);
            int i3 = Integer.parseInt(strArrSplit[1]);
            int i4 = Integer.parseInt(strArrSplit2[0]);
            int i5 = Integer.parseInt(strArrSplit2[1]);
            if (i2 < i4) {
                f427a = Boolean.FALSE;
            } else if (i2 <= i4 && i3 < i5) {
                f427a = Boolean.FALSE;
            } else {
                f427a = bool;
            }
        }
        return f427a.booleanValue();
    }

    public static boolean O(Drawable drawable) {
        return drawable.isAutoMirrored();
    }

    public static boolean P(int i2) {
        return i2 == 8 || i2 == 23 || i2 == 26 || i2 == 7;
    }

    public static boolean Q(String str) {
        return L() && Q0.f295h.equalsIgnoreCase(str);
    }

    public static boolean R(boolean z2, boolean z3) {
        if (!z2) {
            return false;
        }
        if (z3 && AbstractC0047a.f352c) {
            return true;
        }
        return !z3 && AbstractC0047a.f350a;
    }

    public static boolean S(int i2) {
        return i2 == 3 || i2 == 4 || i2 == 22 || i2 == 11;
    }

    public static boolean T(List list) {
        if (list != null && list.size() != 0) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                if (((AudioRecordingConfiguration) it.next()).getAudioSource() != 8) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean U(Context context) {
        Boolean bool = f428b;
        if (bool != null) {
            return bool.booleanValue();
        }
        ((DisplayManager) context.getSystemService("display")).getDisplay(0).getRealMetrics(new DisplayMetrics());
        double dSqrt = Math.sqrt(Math.pow(r1.heightPixels / r1.ydpi, 2.0d) + Math.pow(r1.widthPixels / r1.xdpi, 2.0d));
        v0.a("AudioUtils", "isPad screenInches:" + dSqrt);
        boolean z2 = dSqrt >= 7.0d;
        boolean zEquals = "tablet".equals(SystemProperties.get("ro.build.characteristics"));
        v0.a("AudioUtils", "isTablet isPad:" + z2 + ", isTablet:" + zEquals);
        Boolean boolValueOf = Boolean.valueOf(z2 || zEquals);
        f428b = boolValueOf;
        return boolValueOf.booleanValue();
    }

    public static boolean V(boolean z2, boolean z3) {
        if (!z2) {
            return false;
        }
        if (z3 && AbstractC0047a.f353d) {
            return true;
        }
        return !z3 && AbstractC0047a.f351b;
    }

    public static void a(Parcel parcel, Parcelable parcelable) {
        if (parcelable == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcelable.writeToParcel(parcel, 0);
        }
    }

    public static void b(Parcel parcel, Parcelable parcelable) {
        if (parcelable == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcelable.writeToParcel(parcel, 0);
        }
    }

    public static void c(Object obj, StringBuilder sb) {
        int iLastIndexOf;
        if (obj == null) {
            sb.append("null");
            return;
        }
        String simpleName = obj.getClass().getSimpleName();
        if (simpleName.length() <= 0 && (iLastIndexOf = (simpleName = obj.getClass().getName()).lastIndexOf(46)) > 0) {
            simpleName = simpleName.substring(iLastIndexOf + 1);
        }
        sb.append(simpleName);
        sb.append('{');
        sb.append(Integer.toHexString(System.identityHashCode(obj)));
    }

    public static int d(Context context, String str) {
        int iMyPid = Process.myPid();
        int iMyUid = Process.myUid();
        String packageName = context.getPackageName();
        if (context.checkPermission(str, iMyPid, iMyUid) == -1) {
            return -1;
        }
        String strPermissionToOp = AppOpsManager.permissionToOp(str);
        if (strPermissionToOp != null) {
            if (packageName == null) {
                String[] packagesForUid = context.getPackageManager().getPackagesForUid(iMyUid);
                if (packagesForUid == null || packagesForUid.length <= 0) {
                    return -1;
                }
                packageName = packagesForUid[0];
            }
            if (((AppOpsManager) context.getSystemService(AppOpsManager.class)).noteProxyOpNoThrow(strPermissionToOp, packageName) != 0) {
                return -2;
            }
        }
        return 0;
    }

    public static boolean e(ArrayList arrayList) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            t tVar = (t) it.next();
            if (tVar instanceof s) {
                int i2 = ((s) tVar).f436j;
                if (i2 == 3 || i2 == 2) {
                    return true;
                }
            } else {
                int iC = tVar.c();
                if (P(iC) || S(iC)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean f(ArrayList arrayList, String str) {
        if (arrayList != null && !arrayList.isEmpty()) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                t tVar = (t) it.next();
                if (tVar instanceof s) {
                    s sVar = (s) tVar;
                    int i2 = sVar.f436j;
                    if (i2 == 2) {
                        return true;
                    }
                    if (i2 == 3 && (TextUtils.isEmpty(str) || !str.equalsIgnoreCase(sVar.f439m))) {
                        return true;
                    }
                } else {
                    int iC = tVar.c();
                    if (S(iC)) {
                        return true;
                    }
                    if (P(iC) && (TextUtils.isEmpty(str) || !str.equalsIgnoreCase(tVar.f441b.getAddress()))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void g0(Drawable drawable, int i2, int i3, int i4, int i5) {
        drawable.setHotspotBounds(i2, i3, i4, i5);
    }

    public static void h0(AudioManager audioManager, int i2, boolean z2) {
        if ((i2 == 0 || z2) && audioManager != null && N()) {
            audioManager.setMode(i2);
        }
    }

    public static void i0(String str, String str2) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            cls.getMethod("set", String.class, String.class).invoke(cls, str, str2);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static String k(int i2, String str) {
        return str + ":" + i2;
    }

    public static void k0(Drawable drawable, ColorStateList colorStateList) {
        drawable.setTintList(colorStateList);
    }

    public static void l0(Drawable drawable, PorterDuff.Mode mode) {
        drawable.setTintMode(mode);
    }

    public static boolean n(ArrayList arrayList, int i2) {
        if (i2 < 0 || i2 >= arrayList.size()) {
            return false;
        }
        t tVar = (t) arrayList.get(i2);
        if (tVar instanceof s) {
            int i3 = ((s) tVar).f436j;
            if (i3 == 3 || i3 == 2) {
                return true;
            }
        } else {
            int iC = tVar.c();
            if (P(iC) || S(iC)) {
                return true;
            }
        }
        return false;
    }

    public static void n0(boolean z2) {
        if (z2) {
            i0("persist.desktop.activity_wireless_media", "true");
        } else {
            i0("persist.desktop.activity_wireless_media", "false");
        }
    }

    public static boolean o(ArrayList arrayList, int i2, String str) {
        if (i2 >= 0 && arrayList != null && i2 < arrayList.size()) {
            t tVar = (t) arrayList.get(i2);
            if (tVar instanceof s) {
                s sVar = (s) tVar;
                int i3 = sVar.f436j;
                if (i3 == 2) {
                    return true;
                }
                if (i3 == 3) {
                    return TextUtils.isEmpty(str) || !str.equalsIgnoreCase(sVar.f439m);
                }
            } else {
                int iC = tVar.c();
                if (S(iC)) {
                    return true;
                }
                if (P(iC)) {
                    return TextUtils.isEmpty(str) || !str.equalsIgnoreCase(tVar.f441b.getAddress());
                }
            }
        }
        return false;
    }

    public static boolean p(ArrayList arrayList, int i2, String str) {
        String address;
        if (!TextUtils.isEmpty(str) && i2 >= 0 && arrayList != null && i2 < arrayList.size()) {
            t tVar = (t) arrayList.get(i2);
            if (tVar instanceof s) {
                s sVar = (s) tVar;
                if (sVar.f436j != 3) {
                    return false;
                }
                address = sVar.f439m;
            } else if (P(tVar.c())) {
                address = tVar.f441b.getAddress();
            }
            if (str.equalsIgnoreCase(address)) {
                return true;
            }
        }
        return false;
    }

    public static void p0(ViewGroup viewGroup, boolean z2) {
        if (!AbstractC0054a.f511b) {
            try {
                Method declaredMethod = ViewGroup.class.getDeclaredMethod("suppressLayout", Boolean.TYPE);
                AbstractC0054a.f510a = declaredMethod;
                declaredMethod.setAccessible(true);
            } catch (NoSuchMethodException e2) {
                Log.i("ViewUtilsApi18", "Failed to retrieve suppressLayout method", e2);
            }
            AbstractC0054a.f511b = true;
        }
        Method method = AbstractC0054a.f510a;
        if (method != null) {
            try {
                method.invoke(viewGroup, Boolean.valueOf(z2));
            } catch (IllegalAccessException e3) {
                Log.i("ViewUtilsApi18", "Failed to invoke suppressLayout method", e3);
            } catch (InvocationTargetException e4) {
                Log.i("ViewUtilsApi18", "Error invoking suppressLayout method", e4);
            }
        }
    }

    public static Object r(Object[] objArr, int i2, C.n nVar) {
        int i3 = (i2 & 1) == 0 ? 400 : 700;
        boolean z2 = (i2 & 2) != 0;
        Object obj = null;
        int i4 = Integer.MAX_VALUE;
        for (Object obj2 : objArr) {
            int iAbs = (Math.abs(nVar.b(obj2) - i3) * 2) + (nVar.c(obj2) == z2 ? 0 : 1);
            if (obj == null || i4 > iAbs) {
                obj = obj2;
                i4 = iAbs;
            }
        }
        return obj;
    }

    public static boolean r0(DisplayManager displayManager) {
        for (Display display : displayManager.getDisplays()) {
            if (display != null && display.getType() == 3) {
                return true;
            }
        }
        return false;
    }

    public static boolean s(String str, boolean z2) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return ((Boolean) cls.getMethod("getBoolean", String.class, Boolean.TYPE).invoke(cls, str, Boolean.valueOf(z2))).booleanValue();
        } catch (Exception e2) {
            e2.printStackTrace();
            return z2;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:4:0x0008  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static int u(android.hardware.display.DisplayManager r7, int r8, android.content.Context r9) {
        /*
            boolean r0 = com.motorola.internal.app.MotoDesktopManager.isDesktopSupported()
            r1 = 0
            r2 = 1
            if (r0 != 0) goto La
        L8:
            r9 = r1
            goto L2f
        La:
            android.view.Display[] r0 = r7.getDisplays()
            int r3 = r0.length
            if (r3 != 0) goto L12
            goto L8
        L12:
            int r3 = r0.length
            r4 = r1
        L14:
            if (r4 >= r3) goto L8
            r5 = r0[r4]
            boolean r6 = com.motorola.internal.app.MotoDesktopManager.isReadyForDisplay(r9, r5)
            if (r6 == 0) goto L2c
            boolean r6 = com.motorola.internal.app.MotoDesktopManager.isDesktopOrMobileUiMode(r5)
            if (r6 != 0) goto L2c
            boolean r5 = com.motorola.internal.app.MotoDesktopManager.isAppStreamMode(r5)
            if (r5 != 0) goto L2c
            r9 = r2
            goto L2f
        L2c:
            int r4 = r4 + 1
            goto L14
        L2f:
            if (r7 == 0) goto L61
            r0 = -1
            if (r8 != r0) goto L35
            goto L61
        L35:
            android.view.Display r7 = r7.getDisplay(r8)
            if (r7 == 0) goto L5d
            int r8 = r7.getType()
            r0 = 2
            if (r8 != r0) goto L43
            return r0
        L43:
            int r8 = r7.getType()
            r0 = 3
            if (r8 != r0) goto L4b
            return r0
        L4b:
            int r8 = r7.getType()
            r0 = 5
            if (r8 != r0) goto L5d
            java.lang.String r7 = r7.getOwnerPackageName()
            boolean r7 = com.motorola.internal.app.MotoDesktopManager.isReadyForPackage(r7)
            if (r7 == 0) goto L5d
            return r2
        L5d:
            if (r9 == 0) goto L60
            return r2
        L60:
            return r1
        L61:
            if (r9 == 0) goto L64
            return r2
        L64:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: Y.r.u(android.hardware.display.DisplayManager, int, android.content.Context):int");
    }

    public static ArrayList v(Context context) {
        List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) context.getSystemService("activity")).getRunningTasks(Integer.MAX_VALUE);
        ArrayList arrayList = new ArrayList();
        for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTasks) {
            NewMotoRunningTaskInfo newMotoRunningTaskInfo = new NewMotoRunningTaskInfo();
            newMotoRunningTaskInfo.setRunningTaskInfo(runningTaskInfo);
            arrayList.add(newMotoRunningTaskInfo);
            v0.a("AppUtils", "getMotoRunningTaskInfos list:" + runningTaskInfo);
        }
        return arrayList;
    }

    public static ArrayList w(MotoDesktopService motoDesktopService, int i2) {
        List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) motoDesktopService.getSystemService("activity")).getRunningTasks(Integer.MAX_VALUE);
        ArrayList arrayList = new ArrayList();
        for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTasks) {
            if (runningTaskInfo.displayId == i2) {
                MotoRunningTaskInfo motoRunningTaskInfo = new MotoRunningTaskInfo();
                motoRunningTaskInfo.setRunningTaskInfo(runningTaskInfo);
                arrayList.add(motoRunningTaskInfo);
            }
        }
        return arrayList;
    }

    public static ArrayList x(int i2, int i3) {
        List<ActivityManager.RecentTaskInfo> recentTasks = ActivityTaskManager.getInstance().getRecentTasks(Integer.MAX_VALUE, 0, i3);
        ArrayList arrayList = new ArrayList();
        for (ActivityManager.RecentTaskInfo recentTaskInfo : recentTasks) {
            if (recentTaskInfo.displayId == i2) {
                MotoRunningTaskInfoNew motoRunningTaskInfoNew = new MotoRunningTaskInfoNew();
                motoRunningTaskInfoNew.userId = recentTaskInfo.userId;
                motoRunningTaskInfoNew.taskId = recentTaskInfo.taskId;
                motoRunningTaskInfoNew.isRunning = recentTaskInfo.isRunning;
                motoRunningTaskInfoNew.displayId = recentTaskInfo.displayId;
                motoRunningTaskInfoNew.isFocused = recentTaskInfo.isFocused;
                motoRunningTaskInfoNew.isVisible = recentTaskInfo.isVisible;
                Intent intent = recentTaskInfo.baseIntent;
                if (intent != null && intent.getComponent() != null) {
                    motoRunningTaskInfoNew.pkg = intent.getComponent().getPackageName();
                }
                arrayList.add(motoRunningTaskInfoNew);
            }
        }
        return arrayList;
    }

    public static AppInfo y(Context context, int i2) {
        Intent intent;
        for (ActivityManager.RunningTaskInfo runningTaskInfo : ((ActivityManager) context.getSystemService("activity")).getRunningTasks(Integer.MAX_VALUE)) {
            if (runningTaskInfo != null && i2 == runningTaskInfo.taskId && (intent = runningTaskInfo.baseIntent) != null && intent.getComponent() != null) {
                String packageName = intent.getComponent().getPackageName();
                int i3 = runningTaskInfo.taskId;
                int i4 = runningTaskInfo.displayId;
                int i5 = runningTaskInfo.userId;
                return new AppInfo(i3, packageName, i4, i5, k(i5, packageName));
            }
        }
        return null;
    }

    public static AppInfo z(Context context, String str, int i2) {
        Intent intent;
        int i3;
        for (ActivityManager.RunningTaskInfo runningTaskInfo : ((ActivityManager) context.getSystemService("activity")).getRunningTasks(Integer.MAX_VALUE)) {
            if (runningTaskInfo != null && (intent = runningTaskInfo.baseIntent) != null && intent.getComponent() != null && str.equals(intent.getComponent().getPackageName()) && i2 == (i3 = runningTaskInfo.userId)) {
                return new AppInfo(runningTaskInfo.taskId, str, runningTaskInfo.displayId, i3, k(i3, str));
            }
        }
        return null;
    }

    public abstract Context E();

    public float G(View view) {
        if (!f433g) {
            try {
                Method declaredMethod = View.class.getDeclaredMethod("getTransitionAlpha", null);
                f432f = declaredMethod;
                declaredMethod.setAccessible(true);
            } catch (NoSuchMethodException e2) {
                Log.i("ViewUtilsApi19", "Failed to retrieve getTransitionAlpha method", e2);
            }
            f433g = true;
        }
        Method method = f432f;
        if (method != null) {
            try {
                return ((Float) method.invoke(view, null)).floatValue();
            } catch (IllegalAccessException unused) {
            } catch (InvocationTargetException e3) {
                throw new RuntimeException(e3.getCause());
            }
        }
        return this.H(view);
    }

    public float H(View view) {
        Float f2 = (Float) view.getTag(R$id.save_non_transition_alpha);
        return f2 != null ? view.getAlpha() / f2.floatValue() : view.getAlpha();
    }

    public int I(View view) {
        return 0;
    }

    public int J() {
        return 0;
    }

    public boolean M() {
        return false;
    }

    public abstract void W();

    public void X() {
    }

    public abstract boolean Y(int i2, KeyEvent keyEvent);

    public boolean Z(KeyEvent keyEvent) {
        return false;
    }

    public void a0(View view, int i2) {
    }

    public abstract void b0(int i2);

    public abstract void c0(View view, int i2, int i3);

    public abstract void d0(View view, float f2, float f3);

    public boolean e0() {
        return false;
    }

    public abstract void f0(boolean z2);

    public abstract int g(View view, int i2);

    public abstract int h(View view, int i2);

    public boolean i() {
        return false;
    }

    public abstract boolean j();

    public abstract void j0(boolean z2);

    public Typeface l(Context context, n.b bVar, Resources resources, int i2) {
        n.c cVar = (n.c) r(bVar.f2770a, i2, new C.n(6));
        if (cVar == null) {
            return null;
        }
        int i3 = cVar.f2776f;
        Typeface typefaceM = AbstractC0153b.f2781a.m(context, resources, i3, cVar.f2771a, i2);
        if (typefaceM != null) {
            AbstractC0153b.f2782b.b(AbstractC0153b.b(resources, i3, i2), typefaceM);
        }
        return typefaceM;
    }

    public Typeface m(Context context, Resources resources, int i2, String str, int i3) {
        InputStream inputStreamOpenRawResource;
        File fileP = AbstractC0054a.p(context);
        try {
            if (fileP == null) {
                return null;
            }
            try {
                inputStreamOpenRawResource = resources.openRawResource(i2);
                try {
                    boolean zK = AbstractC0054a.k(fileP, inputStreamOpenRawResource);
                    AbstractC0054a.h(inputStreamOpenRawResource);
                    if (zK) {
                        return Typeface.createFromFile(fileP.getPath());
                    }
                    return null;
                } catch (Throwable th) {
                    th = th;
                    AbstractC0054a.h(inputStreamOpenRawResource);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                inputStreamOpenRawResource = null;
            }
        } catch (RuntimeException unused) {
            return null;
        } finally {
            fileP.delete();
        }
    }

    public abstract void m0(CharSequence charSequence);

    public d.b o0(F.f fVar) {
        return null;
    }

    public abstract void q(boolean z2);

    public abstract boolean q0(View view, int i2);

    public abstract int t();
}
