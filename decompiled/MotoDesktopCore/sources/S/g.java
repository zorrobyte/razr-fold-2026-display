package s;

import C.n;
import C.z;
import Y.r;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import androidx.appcompat.app.AbstractC0054a;
import h.k;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import o.AbstractC0153b;
import o.C0154c;

/* JADX INFO: loaded from: classes.dex */
public abstract class g {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final h.f f2809a = new h.f(16);

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final z f2810b = new z(3);

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static final Object f2811c = new Object();

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public static final k f2812d = new k();

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public static final F.c f2813e = new F.c(2);

    public static androidx.appcompat.app.f a(Context context, C0158a c0158a) throws PackageManager.NameNotFoundException {
        Cursor cursorQuery;
        PackageManager packageManager = context.getPackageManager();
        Resources resources = context.getResources();
        String str = c0158a.f2790a;
        ProviderInfo providerInfoResolveContentProvider = packageManager.resolveContentProvider(str, 0);
        if (providerInfoResolveContentProvider == null) {
            throw new PackageManager.NameNotFoundException("No package found for authority: " + str);
        }
        String str2 = providerInfoResolveContentProvider.packageName;
        String str3 = c0158a.f2791b;
        if (!str2.equals(str3)) {
            throw new PackageManager.NameNotFoundException("Found content provider " + str + ", but package was not " + str3);
        }
        Signature[] signatureArr = packageManager.getPackageInfo(providerInfoResolveContentProvider.packageName, 64).signatures;
        ArrayList arrayList = new ArrayList();
        for (Signature signature : signatureArr) {
            arrayList.add(signature.toByteArray());
        }
        F.c cVar = f2813e;
        Collections.sort(arrayList, cVar);
        List listU = c0158a.f2793d;
        if (listU == null) {
            listU = AbstractC0054a.u(resources, 0);
        }
        int i2 = 0;
        loop1: while (true) {
            cursorQuery = null;
            if (i2 >= listU.size()) {
                providerInfoResolveContentProvider = null;
                break;
            }
            ArrayList arrayList2 = new ArrayList((Collection) listU.get(i2));
            Collections.sort(arrayList2, cVar);
            if (arrayList.size() == arrayList2.size()) {
                for (int i3 = 0; i3 < arrayList.size(); i3++) {
                    if (!Arrays.equals((byte[]) arrayList.get(i3), (byte[]) arrayList2.get(i3))) {
                        break;
                    }
                }
                break loop1;
            }
            i2++;
        }
        if (providerInfoResolveContentProvider == null) {
            return new androidx.appcompat.app.f(1, (e[]) null);
        }
        String str4 = providerInfoResolveContentProvider.authority;
        ArrayList arrayList3 = new ArrayList();
        Uri uriBuild = new Uri.Builder().scheme("content").authority(str4).build();
        Uri uriBuild2 = new Uri.Builder().scheme("content").authority(str4).appendPath("file").build();
        try {
            cursorQuery = context.getContentResolver().query(uriBuild, new String[]{"_id", "file_id", "font_ttc_index", "font_variation_settings", "font_weight", "font_italic", "result_code"}, "query = ?", new String[]{c0158a.f2792c}, null, null);
            if (cursorQuery != null && cursorQuery.getCount() > 0) {
                int columnIndex = cursorQuery.getColumnIndex("result_code");
                arrayList3 = new ArrayList();
                int columnIndex2 = cursorQuery.getColumnIndex("_id");
                int columnIndex3 = cursorQuery.getColumnIndex("file_id");
                int columnIndex4 = cursorQuery.getColumnIndex("font_ttc_index");
                int columnIndex5 = cursorQuery.getColumnIndex("font_weight");
                int columnIndex6 = cursorQuery.getColumnIndex("font_italic");
                while (cursorQuery.moveToNext()) {
                    arrayList3.add(new e(columnIndex3 == -1 ? ContentUris.withAppendedId(uriBuild, cursorQuery.getLong(columnIndex2)) : ContentUris.withAppendedId(uriBuild2, cursorQuery.getLong(columnIndex3)), columnIndex4 != -1 ? cursorQuery.getInt(columnIndex4) : 0, columnIndex5 != -1 ? cursorQuery.getInt(columnIndex5) : 400, columnIndex6 != -1 && cursorQuery.getInt(columnIndex6) == 1, columnIndex != -1 ? cursorQuery.getInt(columnIndex) : 0));
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return new androidx.appcompat.app.f(0, (e[]) arrayList3.toArray(new e[0]));
        } catch (Throwable th) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            throw th;
        }
    }

    public static f b(Context context, C0158a c0158a, int i2) {
        Typeface typefaceCreate = null;
        try {
            androidx.appcompat.app.f fVarA = a(context, c0158a);
            int i3 = fVarA.f563b;
            if (i3 != 0) {
                return new f(null, i3 == 1 ? -2 : -3);
            }
            C0154c c0154c = AbstractC0153b.f2781a;
            c0154c.getClass();
            e[] eVarArr = (e[]) fVarA.f564c;
            if (eVarArr.length >= 1) {
                if (c0154c.w0()) {
                    HashMap map = new HashMap();
                    for (e eVar : eVarArr) {
                        if (eVar.f2806e == 0) {
                            Uri uri = eVar.f2802a;
                            if (!map.containsKey(uri)) {
                                map.put(uri, AbstractC0054a.q(context, uri));
                            }
                        }
                    }
                    Map mapUnmodifiableMap = Collections.unmodifiableMap(map);
                    Object objX0 = c0154c.x0();
                    int length = eVarArr.length;
                    int i4 = 0;
                    boolean z2 = false;
                    while (true) {
                        if (i4 < length) {
                            e eVar2 = eVarArr[i4];
                            ByteBuffer byteBuffer = (ByteBuffer) mapUnmodifiableMap.get(eVar2.f2802a);
                            if (byteBuffer != null) {
                                try {
                                    if (!((Boolean) c0154c.f2786k.invoke(objX0, byteBuffer, Integer.valueOf(eVar2.f2803b), null, Integer.valueOf(eVar2.f2804c), Integer.valueOf(eVar2.f2805d ? 1 : 0))).booleanValue()) {
                                        c0154c.s0(objX0);
                                        break;
                                    }
                                    z2 = true;
                                } catch (IllegalAccessException | InvocationTargetException e2) {
                                    throw new RuntimeException(e2);
                                }
                            }
                            i4++;
                            z2 = z2;
                        } else if (!z2) {
                            c0154c.s0(objX0);
                        } else if (c0154c.v0(objX0)) {
                            typefaceCreate = Typeface.create(c0154c.u0(objX0), i2);
                        }
                    }
                } else {
                    e eVar3 = (e) r.r(eVarArr, i2, new n(5));
                    try {
                        ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = context.getContentResolver().openFileDescriptor(eVar3.f2802a, "r", null);
                        if (parcelFileDescriptorOpenFileDescriptor != null) {
                            try {
                                Typeface typefaceBuild = new Typeface.Builder(parcelFileDescriptorOpenFileDescriptor.getFileDescriptor()).setWeight(eVar3.f2804c).setItalic(eVar3.f2805d).build();
                                parcelFileDescriptorOpenFileDescriptor.close();
                                typefaceCreate = typefaceBuild;
                            } finally {
                            }
                        } else if (parcelFileDescriptorOpenFileDescriptor != null) {
                            parcelFileDescriptorOpenFileDescriptor.close();
                        }
                    } catch (IOException unused) {
                    }
                }
            }
            return new f(typefaceCreate, typefaceCreate != null ? 0 : -3);
        } catch (PackageManager.NameNotFoundException unused2) {
            return new f(null, -1);
        }
    }
}
