package f0;

import C.n;
import C.p;
import Y.r;
import android.animation.ValueAnimator;
import android.app.role.RoleManager;
import android.companion.CompanionDeviceManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import androidx.appcompat.app.AbstractC0054a;
import androidx.appcompat.app.f;
import androidx.appcompat.app.y;
import androidx.appcompat.app.z;
import androidx.appcompat.widget.C0082t;
import androidx.appcompat.widget.C0084v;
import androidx.recyclerview.widget.AbstractC0108a;
import androidx.recyclerview.widget.C0109b;
import androidx.recyclerview.widget.C0120m;
import androidx.recyclerview.widget.RecyclerView;
import b.AbstractC0122a;
import e0.k;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import n.InterfaceC0151a;
import o.AbstractC0153b;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public final class b {

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public static b f2534e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public static b f2535f;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f2536a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public Object f2537b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Object f2538c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public Object f2539d;

    public b(int i2) {
        this.f2536a = i2;
        switch (i2) {
            case 3:
                break;
            default:
                this.f2537b = new ArrayList();
                this.f2538c = null;
                this.f2539d = new p(2, this);
                break;
        }
    }

    public b(Context context) {
        this.f2536a = 0;
        Context applicationContext = context.getApplicationContext();
        this.f2537b = applicationContext;
        this.f2538c = (CompanionDeviceManager) applicationContext.getSystemService("companiondevice");
        RoleManager roleManager = (RoleManager) applicationContext.getSystemService(RoleManager.class);
        Objects.requireNonNull(roleManager);
        this.f2539d = roleManager;
    }

    public b(Context context, TypedArray typedArray) {
        this.f2536a = 4;
        this.f2537b = context;
        this.f2538c = typedArray;
    }

    public b(Context context, LocationManager locationManager) {
        this.f2536a = 2;
        this.f2539d = new z();
        this.f2537b = context;
        this.f2538c = locationManager;
    }

    public b(C0120m c0120m) {
        this.f2536a = 5;
        this.f2537b = new f(30);
        this.f2538c = new ArrayList();
        this.f2539d = new ArrayList();
        new C0120m(this);
    }

    public b(k kVar) {
        this.f2536a = 6;
        this.f2537b = kVar;
        this.f2538c = new C0109b();
        this.f2539d = new ArrayList();
    }

    public static b m(Context context, AttributeSet attributeSet, int[] iArr, int i2, int i3) {
        return new b(context, context.obtainStyledAttributes(attributeSet, iArr, i2, i3));
    }

    public void a(int[] iArr, ValueAnimator valueAnimator) {
        n nVar = new n(iArr, valueAnimator);
        valueAnimator.addListener((p) this.f2539d);
        ((ArrayList) this.f2537b).add(nVar);
    }

    public int b(int i2, int i3) {
        ArrayList arrayList = (ArrayList) this.f2539d;
        int size = arrayList.size();
        while (i3 < size) {
            AbstractC0108a abstractC0108a = (AbstractC0108a) arrayList.get(i3);
            abstractC0108a.getClass();
            abstractC0108a.getClass();
            i3++;
        }
        return i2;
    }

    public View c(int i2) {
        return ((RecyclerView) ((k) this.f2537b).f2495a).getChildAt(i(i2));
    }

    public int d() {
        return ((RecyclerView) ((k) this.f2537b).f2495a).getChildCount() - ((ArrayList) this.f2539d).size();
    }

    public ColorStateList e(int i2) {
        int resourceId;
        TypedArray typedArray = (TypedArray) this.f2538c;
        if (typedArray.hasValue(i2) && (resourceId = typedArray.getResourceId(i2, 0)) != 0) {
            Object obj = AbstractC0122a.f2010a;
            ColorStateList colorStateList = ((Context) this.f2537b).getColorStateList(resourceId);
            if (colorStateList != null) {
                return colorStateList;
            }
        }
        return typedArray.getColorStateList(i2);
    }

    public Drawable f(int i2) {
        int resourceId;
        TypedArray typedArray = (TypedArray) this.f2538c;
        return (!typedArray.hasValue(i2) || (resourceId = typedArray.getResourceId(i2, 0)) == 0) ? typedArray.getDrawable(i2) : AbstractC0122a.a((Context) this.f2537b, resourceId);
    }

    public Drawable g(int i2) {
        int resourceId;
        TypedArray typedArray = (TypedArray) this.f2538c;
        if (!typedArray.hasValue(i2) || (resourceId = typedArray.getResourceId(i2, 0)) == 0) {
            return null;
        }
        return C0082t.f().i((Context) this.f2537b, resourceId, true);
    }

    public Typeface h(int i2, int i3, C0084v c0084v) {
        int resourceId = ((TypedArray) this.f2538c).getResourceId(i2, 0);
        if (resourceId == 0) {
            return null;
        }
        if (((TypedValue) this.f2539d) == null) {
            this.f2539d = new TypedValue();
        }
        TypedValue typedValue = (TypedValue) this.f2539d;
        Context context = (Context) this.f2537b;
        if (context.isRestricted()) {
            return null;
        }
        Resources resources = context.getResources();
        resources.getValue(resourceId, typedValue, true);
        CharSequence charSequence = typedValue.string;
        if (charSequence == null) {
            throw new Resources.NotFoundException("Resource \"" + resources.getResourceName(resourceId) + "\" (" + Integer.toHexString(resourceId) + ") is not a Font: " + typedValue);
        }
        String string = charSequence.toString();
        if (!string.startsWith("res/")) {
            c0084v.b(-3, null);
            return null;
        }
        h.f fVar = AbstractC0153b.f2782b;
        Typeface typefaceM = (Typeface) fVar.a(AbstractC0153b.b(resources, resourceId, i3));
        if (typefaceM != null) {
            c0084v.c(typefaceM, null);
        } else {
            try {
                if (string.toLowerCase().endsWith(".xml")) {
                    InterfaceC0151a interfaceC0151aS = AbstractC0054a.s(resources.getXml(resourceId), resources);
                    if (interfaceC0151aS != null) {
                        return AbstractC0153b.a(context, interfaceC0151aS, resources, resourceId, i3, c0084v);
                    }
                    Log.e("ResourcesCompat", "Failed to find font-family tag");
                    c0084v.b(-3, null);
                    return null;
                }
                typefaceM = AbstractC0153b.f2781a.m(context, resources, resourceId, string, i3);
                if (typefaceM != null) {
                    fVar.b(AbstractC0153b.b(resources, resourceId, i3), typefaceM);
                }
                if (typefaceM != null) {
                    c0084v.c(typefaceM, null);
                } else {
                    c0084v.b(-3, null);
                }
            } catch (IOException e2) {
                Log.e("ResourcesCompat", "Failed to read xml resource ".concat(string), e2);
                c0084v.b(-3, null);
                return null;
            } catch (XmlPullParserException e3) {
                Log.e("ResourcesCompat", "Failed to parse xml resource ".concat(string), e3);
                c0084v.b(-3, null);
                return null;
            }
        }
        return typefaceM;
    }

    public int i(int i2) {
        if (i2 < 0) {
            return -1;
        }
        int childCount = ((RecyclerView) ((k) this.f2537b).f2495a).getChildCount();
        int i3 = i2;
        while (i3 < childCount) {
            C0109b c0109b = (C0109b) this.f2538c;
            int iA = i2 - (i3 - c0109b.a(i3));
            if (iA == 0) {
                while (c0109b.c(i3)) {
                    i3++;
                }
                return i3;
            }
            i3 += iA;
        }
        return -1;
    }

    public View j(int i2) {
        return ((RecyclerView) ((k) this.f2537b).f2495a).getChildAt(i2);
    }

    public int k() {
        return ((RecyclerView) ((k) this.f2537b).f2495a).getChildCount();
    }

    public boolean l() {
        Location location;
        long j2;
        z zVar = (z) this.f2539d;
        if (zVar.f660b > System.currentTimeMillis()) {
            return zVar.f659a;
        }
        Context context = (Context) this.f2537b;
        int iD = r.d(context, "android.permission.ACCESS_COARSE_LOCATION");
        LocationManager locationManager = (LocationManager) this.f2538c;
        Location lastKnownLocation = null;
        if (iD == 0) {
            try {
            } catch (Exception e2) {
                Log.d("TwilightManager", "Failed to get last known location", e2);
            }
            Location lastKnownLocation2 = locationManager.isProviderEnabled("network") ? locationManager.getLastKnownLocation("network") : null;
            location = lastKnownLocation2;
        } else {
            location = null;
        }
        if (r.d(context, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            try {
                if (locationManager.isProviderEnabled("gps")) {
                    lastKnownLocation = locationManager.getLastKnownLocation("gps");
                }
            } catch (Exception e3) {
                Log.d("TwilightManager", "Failed to get last known location", e3);
            }
        }
        if (lastKnownLocation == null || location == null ? lastKnownLocation != null : lastKnownLocation.getTime() > location.getTime()) {
            location = lastKnownLocation;
        }
        if (location == null) {
            Log.i("TwilightManager", "Could not get last known location. This is probably because the app does not have any location permissions. Falling back to hardcoded sunrise/sunset values.");
            int i2 = Calendar.getInstance().get(11);
            return i2 < 6 || i2 >= 22;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (y.f655d == null) {
            y.f655d = new y();
        }
        y yVar = y.f655d;
        yVar.a(jCurrentTimeMillis - 86400000, location.getLatitude(), location.getLongitude());
        yVar.a(jCurrentTimeMillis, location.getLatitude(), location.getLongitude());
        boolean z2 = yVar.f658c == 1;
        long j3 = yVar.f657b;
        long j4 = yVar.f656a;
        yVar.a(jCurrentTimeMillis + 86400000, location.getLatitude(), location.getLongitude());
        long j5 = yVar.f657b;
        if (j3 == -1 || j4 == -1) {
            j2 = jCurrentTimeMillis + 43200000;
        } else {
            if (jCurrentTimeMillis <= j4) {
                j5 = jCurrentTimeMillis > j3 ? j4 : j3;
            }
            j2 = j5 + 60000;
        }
        zVar.f659a = z2;
        zVar.f660b = j2;
        return z2;
    }

    public void n() {
        ((TypedArray) this.f2538c).recycle();
    }

    public void o(ArrayList arrayList) {
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            AbstractC0108a abstractC0108a = (AbstractC0108a) arrayList.get(i2);
            abstractC0108a.f1845a = null;
            ((f) this.f2537b).c(abstractC0108a);
        }
        arrayList.clear();
    }

    public String toString() {
        switch (this.f2536a) {
            case 6:
                return ((C0109b) this.f2538c).toString() + ", hidden list:" + ((ArrayList) this.f2539d).size();
            default:
                return super.toString();
        }
    }
}
