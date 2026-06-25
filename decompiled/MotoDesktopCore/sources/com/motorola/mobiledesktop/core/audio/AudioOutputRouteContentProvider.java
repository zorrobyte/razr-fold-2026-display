package com.motorola.mobiledesktop.core.audio;

import X.v0;
import Y.C0048b;
import Y.i;
import Y.r;
import Y.t;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import com.motorola.mobiledesktop.core.R;
import java.util.ArrayList;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class AudioOutputRouteContentProvider extends ContentProvider {

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public static final Uri f2307d = Uri.parse("content://com.motorola.mobiledesktop.media.MediaOutputRouteContentProvider/Device");

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public static final Uri f2308e = Uri.parse("content://com.motorola.mobiledesktop.media.MediaOutputRouteContentProvider/UpdateFocus");

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public static final Uri f2309f = Uri.parse("content://com.motorola.mobiledesktop.media.MediaOutputRouteContentProvider/UpdateVolume");

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public static final Uri f2310g = Uri.parse("content://com.motorola.mobiledesktop.media.MediaOutputRouteContentProvider/CurrentMediaDeviceName");

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public static final Uri f2311h = Uri.parse("content://com.motorola.mobiledesktop.media.MediaOutputRouteContentProvider/ShowAudioIcon");

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public static final Uri f2312i = Uri.parse("content://com.motorola.mobiledesktop.media.MediaOutputRouteContentProvider/ToastUseClientMic");

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public static final Uri f2313j = Uri.parse("content://com.motorola.mobiledesktop.media.MediaOutputRouteContentProvider/UpdateCheckboxState");

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public i f2314a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f2315b = 0;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final C0048b f2316c = new C0048b(this);

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        i iVarU = i.u(getContext());
        this.f2314a = iVarU;
        C0048b c0048b = this.f2316c;
        iVarU.getClass();
        Objects.requireNonNull(c0048b, "callback must not be null");
        iVarU.f391d.add(c0048b);
        this.f2314a.x();
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        if (f2310g.equals(uri)) {
            MatrixCursor matrixCursor = new MatrixCursor(new String[]{"CurrentMediaDeviceName", "CurrentMediaDeviceIconId"});
            i iVar = this.f2314a;
            matrixCursor.addRow(new Object[]{iVar.r, Integer.valueOf(iVar.f405s)});
            return matrixCursor;
        }
        if (f2311h.equals(uri)) {
            MatrixCursor matrixCursor2 = new MatrixCursor(new String[]{"ShowAudioIcon"});
            matrixCursor2.addRow(new Object[]{Integer.valueOf(this.f2314a.f370A ? 1 : 0)});
            return matrixCursor2;
        }
        if (f2312i.equals(uri)) {
            MatrixCursor matrixCursor3 = new MatrixCursor(new String[]{"ToastDisplayId"});
            matrixCursor3.addRow(new Object[]{Integer.valueOf(this.f2314a.f374E)});
            return matrixCursor3;
        }
        ArrayList arrayList = this.f2314a.f395h;
        if (arrayList == null) {
            return null;
        }
        Object[] objArr = getContext().getPackageManager().checkPermission("android.permission.RECORD_AUDIO", getContext().getPackageName()) == 0;
        MatrixCursor matrixCursor4 = new MatrixCursor(new String[]{"DeviceIconId", "DeviceName", "isFocusDevice", "MaxVolume", "CurrentVolume", "DeviceVolumeIconId", "RouteMode", "Title", "SubTitle", "TitleIconId", "CheckRecordAudioPermission", "AllowAdjustVolume", "DeviceSubInfo", "DeviceCheckboxInfo", "DeviceCheckboxState"});
        int i2 = 0;
        while (i2 < arrayList.size()) {
            t tVar = (t) arrayList.get(i2);
            Object[] objArr2 = new Object[15];
            objArr2[0] = Integer.valueOf(tVar.d());
            objArr2[1] = tVar.f();
            objArr2[2] = Integer.valueOf(this.f2314a.f397j == i2 ? 1 : 0);
            objArr2[3] = Integer.valueOf(tVar.e());
            objArr2[4] = Integer.valueOf(tVar.b());
            objArr2[5] = Integer.valueOf(tVar.g());
            objArr2[6] = Integer.valueOf(this.f2315b);
            if (this.f2315b == 1) {
                objArr2[7] = getContext().getString(R.string.call_output_route_fragment_title);
                objArr2[8] = getContext().getString(R.string.call_output_route_fragment_sub_title);
                objArr2[9] = Integer.valueOf(R.drawable.ic_call_title);
            } else {
                objArr2[7] = getContext().getString(R.string.media_output_route_fragment_title);
                objArr2[8] = getContext().getString(R.string.media_output_route_fragment_sub_title);
                objArr2[9] = Integer.valueOf(R.drawable.ic_media_title);
            }
            if (objArr == true) {
                objArr2[10] = Boolean.TRUE;
            } else {
                objArr2[10] = Boolean.FALSE;
            }
            objArr2[11] = Boolean.valueOf(this.f2314a.f412z);
            objArr2[12] = "";
            if (r.N()) {
                objArr2[13] = tVar.a();
            } else {
                objArr2[13] = "";
            }
            objArr2[14] = Integer.valueOf(tVar.f446g ? 1 : 0);
            matrixCursor4.addRow(objArr2);
            i2++;
        }
        return matrixCursor4;
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        if (f2308e.equals(uri)) {
            Integer asInteger = contentValues.getAsInteger("DeviceIndex");
            v0.a("AudioOutputRouteContentProvider", "update focus index = " + asInteger);
            if (asInteger != null) {
                i iVar = this.f2314a;
                int iIntValue = asInteger.intValue();
                if (iVar.f399l) {
                    iVar.n(iIntValue, false);
                } else {
                    iVar.o(iIntValue, false);
                }
                return 1;
            }
        } else if (f2309f.equals(uri)) {
            Integer asInteger2 = contentValues.getAsInteger("DeviceIndex");
            Integer asInteger3 = contentValues.getAsInteger("CurrentVolume");
            v0.a("AudioOutputRouteContentProvider", "update volume index = " + asInteger2 + "; volume = " + asInteger3);
            if (asInteger2 != null && asInteger3 != null) {
                ArrayList arrayList = this.f2314a.f395h;
                if (asInteger2.intValue() < arrayList.size()) {
                    ((t) arrayList.get(asInteger2.intValue())).i(asInteger3.intValue());
                    return 1;
                }
                v0.a("AudioOutputRouteContentProvider", "update volume error index = " + asInteger2 + "; size = " + arrayList.size());
                return 0;
            }
        } else if (f2313j.equals(uri)) {
            boolean zBooleanValue = contentValues.getAsBoolean("DeviceCheckboxUpdateState").booleanValue();
            v0.a("AudioOutputRouteContentProvider", "update checkbox state = " + zBooleanValue);
            this.f2314a.y(zBooleanValue);
            return 1;
        }
        return 0;
    }
}
