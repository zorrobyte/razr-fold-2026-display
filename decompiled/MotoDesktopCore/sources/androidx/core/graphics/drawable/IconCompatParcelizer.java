package androidx.core.graphics.drawable;

/* JADX INFO: loaded from: classes.dex */
public class IconCompatParcelizer {
    /* JADX WARN: Removed duplicated region for block: B:42:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00d8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static androidx.core.graphics.drawable.IconCompat read(E.b r9) {
        /*
            Method dump skipped, instruction units count: 251
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.graphics.drawable.IconCompatParcelizer.read(E.b):androidx.core.graphics.drawable.IconCompat");
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0049  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void write(androidx.core.graphics.drawable.IconCompat r8, E.b r9) {
        /*
            r9.getClass()
            android.graphics.PorterDuff$Mode r0 = r8.f1406h
            java.lang.String r0 = r0.name()
            r8.f1407i = r0
            int r0 = r8.f1399a
            r1 = 5
            r2 = 4
            r3 = 3
            r4 = 2
            r5 = 1
            r6 = -1
            if (r0 == r6) goto L50
            if (r0 == r5) goto L49
            java.lang.String r7 = "UTF-16"
            if (r0 == r4) goto L3a
            if (r0 == r3) goto L33
            if (r0 == r2) goto L22
            if (r0 == r1) goto L49
            goto L56
        L22:
            java.lang.Object r0 = r8.f1400b
            java.lang.String r0 = r0.toString()
            java.nio.charset.Charset r7 = java.nio.charset.Charset.forName(r7)
            byte[] r0 = r0.getBytes(r7)
            r8.f1401c = r0
            goto L56
        L33:
            java.lang.Object r0 = r8.f1400b
            byte[] r0 = (byte[]) r0
            r8.f1401c = r0
            goto L56
        L3a:
            java.lang.Object r0 = r8.f1400b
            java.lang.String r0 = (java.lang.String) r0
            java.nio.charset.Charset r7 = java.nio.charset.Charset.forName(r7)
            byte[] r0 = r0.getBytes(r7)
            r8.f1401c = r0
            goto L56
        L49:
            java.lang.Object r0 = r8.f1400b
            android.os.Parcelable r0 = (android.os.Parcelable) r0
            r8.f1402d = r0
            goto L56
        L50:
            java.lang.Object r0 = r8.f1400b
            android.os.Parcelable r0 = (android.os.Parcelable) r0
            r8.f1402d = r0
        L56:
            int r0 = r8.f1399a
            r9.c(r5)
            r5 = r9
            E.c r5 = (E.c) r5
            android.os.Parcel r5 = r5.f111b
            r5.writeInt(r0)
            byte[] r0 = r8.f1401c
            r9.c(r4)
            if (r0 == 0) goto L72
            int r4 = r0.length
            r5.writeInt(r4)
            r5.writeByteArray(r0)
            goto L75
        L72:
            r5.writeInt(r6)
        L75:
            android.os.Parcelable r0 = r8.f1402d
            r9.c(r3)
            r3 = 0
            r5.writeParcelable(r0, r3)
            int r0 = r8.f1403e
            r9.c(r2)
            r5.writeInt(r0)
            int r0 = r8.f1404f
            r9.c(r1)
            r5.writeInt(r0)
            android.content.res.ColorStateList r0 = r8.f1405g
            r1 = 6
            r9.c(r1)
            r5.writeParcelable(r0, r3)
            java.lang.String r8 = r8.f1407i
            r0 = 7
            r9.c(r0)
            r5.writeString(r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.graphics.drawable.IconCompatParcelizer.write(androidx.core.graphics.drawable.IconCompat, E.b):void");
    }
}
