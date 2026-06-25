package X;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.RemoteException;
import com.motorola.mobiledesktop.core.bean.AudioSettingInfo;
import com.motorola.mobiledesktop.core.bean.AudioSettingItemInfo;
import java.util.ArrayList;

/* JADX INFO: renamed from: X.e, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0013e {

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public static final Uri f314d = Uri.parse("content://com.motorola.mobiledesktop.media.MediaOutputRouteContentProvider/Device");

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public static final Uri f315e = Uri.parse("content://com.motorola.mobiledesktop.media.MediaOutputRouteContentProvider/UpdateFocus");

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public static final Uri f316f = Uri.parse("content://com.motorola.mobiledesktop.media.MediaOutputRouteContentProvider/UpdateVolume");

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public static final Uri f317g = Uri.parse("content://com.motorola.mobiledesktop.media.MediaOutputRouteContentProvider/UpdateCheckboxState");

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public Context f318a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public Handler f319b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public InterfaceC0029m f320c;

    public static void a(C0013e c0013e) {
        c0013e.getClass();
        try {
            v0.a("AudioSettingHelper", "updateData()");
            AudioSettingInfo audioSettingInfo = new AudioSettingInfo();
            ArrayList<AudioSettingItemInfo> arrayList = new ArrayList<>();
            audioSettingInfo.setItemInfoList(arrayList);
            Cursor cursorQuery = c0013e.f318a.getContentResolver().query(f314d, null, null, null);
            if (cursorQuery == null) {
                v0.b("AudioSettingHelper", "updateData() cursor is null");
                return;
            }
            if (!cursorQuery.moveToFirst()) {
                cursorQuery.close();
                v0.b("AudioSettingHelper", "updateData() cursor is empty");
                return;
            }
            int i2 = 0;
            do {
                if (i2 == 0) {
                    String string = cursorQuery.getString(cursorQuery.getColumnIndex("Title"));
                    String string2 = cursorQuery.getString(cursorQuery.getColumnIndex("SubTitle"));
                    int i3 = cursorQuery.getInt(cursorQuery.getColumnIndex("TitleIconId"));
                    v0.a("AudioSettingHelper", "updateData() title " + string + " , subTitle " + string2 + " titleIconId " + i3);
                    AudioSettingItemInfo audioSettingItemInfo = new AudioSettingItemInfo();
                    audioSettingItemInfo.setTitle(string);
                    audioSettingItemInfo.setSubTitle(string2);
                    audioSettingItemInfo.setTitleIconId(i3);
                    audioSettingItemInfo.setIndex(-1);
                    audioSettingInfo.setHeadItem(audioSettingItemInfo);
                }
                int i4 = cursorQuery.getInt(cursorQuery.getColumnIndex("DeviceIconId"));
                String string3 = cursorQuery.getString(cursorQuery.getColumnIndex("DeviceName"));
                String string4 = cursorQuery.getString(cursorQuery.getColumnIndex("DeviceSubInfo"));
                boolean z2 = true;
                if (cursorQuery.getInt(cursorQuery.getColumnIndex("isFocusDevice")) != 1) {
                    z2 = false;
                }
                int i5 = cursorQuery.getInt(cursorQuery.getColumnIndex("MaxVolume"));
                int i6 = cursorQuery.getInt(cursorQuery.getColumnIndex("CurrentVolume"));
                String string5 = cursorQuery.getString(cursorQuery.getColumnIndex("DeviceCheckboxInfo"));
                int i7 = cursorQuery.getInt(cursorQuery.getColumnIndex("DeviceCheckboxState"));
                v0.a("AudioSettingHelper", "updateData() mName " + string3 + "  mIconId " + i4 + " mDeviceSubInfo " + string4 + " mIsForceDevice " + z2 + " mMaxVolume " + i5 + " mCurrentVolume " + i6 + " mCheckboxInfo " + string5 + " mCheckBoxState " + i7);
                AudioSettingItemInfo audioSettingItemInfo2 = new AudioSettingItemInfo();
                audioSettingItemInfo2.setIndex(i2);
                audioSettingItemInfo2.setIconId(i4);
                audioSettingItemInfo2.setName(string3);
                audioSettingItemInfo2.setDeviceSubInfo(string4);
                audioSettingItemInfo2.setForceDevice(z2);
                audioSettingItemInfo2.setMaxVolume(i5);
                audioSettingItemInfo2.setCurrentVolume(i6);
                audioSettingItemInfo2.setCheckboxInfo(string5);
                audioSettingItemInfo2.setCheckBoxState(i7);
                arrayList.add(audioSettingItemInfo2);
                i2++;
            } while (cursorQuery.moveToNext());
            cursorQuery.close();
            InterfaceC0029m interfaceC0029m = c0013e.f320c;
            if (interfaceC0029m != null) {
                try {
                    ((C0025k) interfaceC0029m).a(audioSettingInfo);
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }
            }
        } catch (Exception e3) {
            v0.c("AudioSettingHelper", "updateData() Exception", e3);
        }
    }
}
