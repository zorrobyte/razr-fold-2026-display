package androidx.versionedparcelable;

import E.a;
import E.b;
import E.c;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseIntArray;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes.dex */
public class ParcelImpl implements Parcelable {
    public static final Parcelable.Creator<ParcelImpl> CREATOR = new a(0);

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final CustomVersionedParcelable f1944a;

    public ParcelImpl(Parcel parcel) {
        parcel.dataPosition();
        int iDataSize = parcel.dataSize();
        new SparseIntArray();
        String string = parcel.readString();
        CustomVersionedParcelable customVersionedParcelable = null;
        if (string != null) {
            try {
                customVersionedParcelable = (CustomVersionedParcelable) Class.forName(string, true, b.class.getClassLoader()).getDeclaredMethod("read", b.class).invoke(null, new c(parcel, parcel.dataPosition(), iDataSize, "  "));
            } catch (ClassNotFoundException e2) {
                throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", e2);
            } catch (IllegalAccessException e3) {
                throw new RuntimeException("VersionedParcel encountered IllegalAccessException", e3);
            } catch (NoSuchMethodException e4) {
                throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", e4);
            } catch (InvocationTargetException e5) {
                if (!(e5.getCause() instanceof RuntimeException)) {
                    throw new RuntimeException("VersionedParcel encountered InvocationTargetException", e5);
                }
                throw ((RuntimeException) e5.getCause());
            }
        }
        this.f1944a = customVersionedParcelable;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        parcel.dataPosition();
        int iDataSize = parcel.dataSize();
        new SparseIntArray();
        CustomVersionedParcelable customVersionedParcelable = this.f1944a;
        if (customVersionedParcelable == null) {
            parcel.writeString(null);
            return;
        }
        try {
            parcel.writeString(b.a(customVersionedParcelable.getClass()).getName());
            c cVar = new c(parcel, parcel.dataPosition(), iDataSize, "  ");
            try {
                b.a(customVersionedParcelable.getClass()).getDeclaredMethod("write", customVersionedParcelable.getClass(), b.class).invoke(null, customVersionedParcelable, cVar);
                int i3 = cVar.f113d;
                if (i3 >= 0) {
                    int i4 = cVar.f110a.get(i3);
                    Parcel parcel2 = cVar.f111b;
                    int iDataPosition = parcel2.dataPosition();
                    parcel2.setDataPosition(i4);
                    parcel2.writeInt(iDataPosition - i4);
                    parcel2.setDataPosition(iDataPosition);
                }
            } catch (ClassNotFoundException e2) {
                throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", e2);
            } catch (IllegalAccessException e3) {
                throw new RuntimeException("VersionedParcel encountered IllegalAccessException", e3);
            } catch (NoSuchMethodException e4) {
                throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", e4);
            } catch (InvocationTargetException e5) {
                if (!(e5.getCause() instanceof RuntimeException)) {
                    throw new RuntimeException("VersionedParcel encountered InvocationTargetException", e5);
                }
                throw ((RuntimeException) e5.getCause());
            }
        } catch (ClassNotFoundException e6) {
            throw new RuntimeException(customVersionedParcelable.getClass().getSimpleName().concat(" does not have a Parcelizer"), e6);
        }
    }
}
