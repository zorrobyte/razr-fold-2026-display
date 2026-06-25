package com.motorola.plugin.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Process;
import android.os.UserHandle;
import android.view.ContextThemeWrapper;
import com.motorola.plugin.core.context.PluginId;
import com.motorola.plugin.core.misc.BitFlag;
import com.motorola.plugin.core.misc.Checksum;
import com.motorola.plugin.core.misc.FileChecksums;
import com.motorola.plugin.core.misc.NotPluginClassException;
import com.motorola.plugin.core.utils.HiddenApiKt;
import com.motorola.plugin.sdk.annotations.ProvidesInterface;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import kotlinx.coroutines.channels.SendChannel;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: compiled from: Extensions.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class ExtensionsKt {
    public static final BitFlag asFlag(int i) {
        return BitFlag.Companion.wrap(i);
    }

    public static final void beginDocument(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        xmlPullParser.getClass();
        beginDocumentOrElse(xmlPullParser, "", new Function1() { // from class: com.motorola.plugin.core.ExtensionsKt.beginDocument.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((XmlPullParser) obj);
                return Unit.INSTANCE;
            }

            public final void invoke(XmlPullParser xmlPullParser2) {
                xmlPullParser2.getClass();
            }
        });
    }

    private static final void beginDocumentOrElse(XmlPullParser xmlPullParser, String str, Function1 function1) throws XmlPullParserException, IOException {
        int next;
        do {
            next = xmlPullParser.next();
            if (next == 1) {
                break;
            }
        } while (next != 2);
        if (Intrinsics.areEqual(xmlPullParser.getName(), str)) {
            return;
        }
        function1.invoke(xmlPullParser);
    }

    public static final void beginDocumentOrThrow(XmlPullParser xmlPullParser, final String str) throws XmlPullParserException, IOException {
        xmlPullParser.getClass();
        str.getClass();
        beginDocumentOrElse(xmlPullParser, str, new Function1() { // from class: com.motorola.plugin.core.ExtensionsKt.beginDocumentOrThrow.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) throws XmlPullParserException {
                invoke((XmlPullParser) obj);
                return Unit.INSTANCE;
            }

            public final void invoke(XmlPullParser xmlPullParser2) throws XmlPullParserException {
                xmlPullParser2.getClass();
                throw new XmlPullParserException("Unexpected start tag: found [" + ((Object) xmlPullParser2.getName()) + "], expected [" + str + ']');
            }
        });
    }

    public static final Object fastSend(SendChannel sendChannel, Object obj, Continuation continuation) {
        Object objSend;
        return (!sendChannel.offer(obj) && (objSend = sendChannel.send(obj, continuation)) == IntrinsicsKt.getCOROUTINE_SUSPENDED()) ? objSend : Unit.INSTANCE;
    }

    public static final Field findDeclaredFieldRecursively(Class cls, String str, boolean z) {
        Object objM2707constructorimpl;
        cls.getClass();
        str.getClass();
        try {
            Result.Companion companion = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(cls.getDeclaredField(str));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m2709exceptionOrNullimpl(objM2707constructorimpl) != null) {
            Class superclass = cls.getSuperclass();
            objM2707constructorimpl = Result.m2707constructorimpl(superclass == null ? null : findDeclaredFieldRecursively(superclass, str, true));
        }
        return (Field) (Result.m2711isFailureimpl(objM2707constructorimpl) ? null : objM2707constructorimpl);
    }

    public static /* synthetic */ Field findDeclaredFieldRecursively$default(Class cls, String str, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return findDeclaredFieldRecursively(cls, str, z);
    }

    public static final Method findDeclaredMethodRecursively(Class cls, String str, boolean z, Class... clsArr) {
        Object objM2707constructorimpl;
        cls.getClass();
        str.getClass();
        clsArr.getClass();
        try {
            Result.Companion companion = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(cls.getDeclaredMethod(str, (Class[]) Arrays.copyOf(clsArr, clsArr.length)));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m2709exceptionOrNullimpl(objM2707constructorimpl) != null) {
            Class superclass = cls.getSuperclass();
            objM2707constructorimpl = Result.m2707constructorimpl(superclass == null ? null : findDeclaredMethodRecursively(superclass, str, true, (Class[]) Arrays.copyOf(clsArr, clsArr.length)));
        }
        return (Method) (Result.m2711isFailureimpl(objM2707constructorimpl) ? null : objM2707constructorimpl);
    }

    public static /* synthetic */ Method findDeclaredMethodRecursively$default(Class cls, String str, boolean z, Class[] clsArr, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return findDeclaredMethodRecursively(cls, str, z, clsArr);
    }

    public static final List getChecksumsSha1(PackageInfo packageInfo) {
        packageInfo.getClass();
        SigningInfo signingInfo = packageInfo.signingInfo;
        if (signingInfo == null) {
            return CollectionsKt.emptyList();
        }
        Signature[] apkContentsSigners = signingInfo.getApkContentsSigners();
        apkContentsSigners.getClass();
        ArrayList arrayList = new ArrayList(apkContentsSigners.length);
        for (Signature signature : apkContentsSigners) {
            byte[] byteArray = signature.toByteArray();
            byteArray.getClass();
            arrayList.add(sha1(byteArray));
        }
        return arrayList;
    }

    public static final String getPluginActionOrThrow(Class cls) {
        cls.getClass();
        ProvidesInterface providesInterface = (ProvidesInterface) cls.getDeclaredAnnotation(ProvidesInterface.class);
        if (providesInterface == null) {
            throw new NotPluginClassException(cls.getName(), new Exception(cls + " doesn't provide an interface"));
        }
        if (!StringsKt.isBlank(providesInterface.action())) {
            return providesInterface.action();
        }
        throw new NotPluginClassException(cls.getName(), new Exception(cls + " doesn't provide an action"));
    }

    public static final String hashCodeHex(Object obj) {
        obj.getClass();
        String string = Integer.toString(obj.hashCode(), CharsKt.checkRadix(16));
        string.getClass();
        return Intrinsics.stringPlus("0x", string);
    }

    public static final String hex(byte[] bArr) {
        bArr.getClass();
        StringBuffer stringBuffer = new StringBuffer();
        int length = bArr.length;
        if (length > 0) {
            int i = 0;
            while (true) {
                int i2 = i + 1;
                String string = Integer.toString(bArr[i] & 255, CharsKt.checkRadix(16));
                string.getClass();
                stringBuffer.append(StringsKt.padStart(string, 2, '0'));
                if (i2 >= length) {
                    break;
                }
                i = i2;
            }
        }
        String string2 = stringBuffer.toString();
        string2.getClass();
        return string2;
    }

    public static final Object ifElse(boolean z, Object obj, Object obj2) {
        return z ? obj : obj2;
    }

    public static final boolean isPrimary(UserHandle userHandle) {
        userHandle.getClass();
        return Intrinsics.areEqual(Process.myUserHandle(), userHandle);
    }

    public static final boolean isSystem(ApplicationInfo applicationInfo) {
        applicationInfo.getClass();
        return (applicationInfo.flags & 1) != 0;
    }

    public static final boolean isSystem(PackageInfo packageInfo) {
        packageInfo.getClass();
        ApplicationInfo applicationInfo = packageInfo.applicationInfo;
        applicationInfo.getClass();
        return isSystem(applicationInfo);
    }

    public static final boolean isSystemOrUpdated(ApplicationInfo applicationInfo) {
        applicationInfo.getClass();
        return (applicationInfo.flags & 129) != 0;
    }

    public static final boolean isSystemOrUpdated(PackageInfo packageInfo) {
        packageInfo.getClass();
        ApplicationInfo applicationInfo = packageInfo.applicationInfo;
        applicationInfo.getClass();
        return isSystemOrUpdated(applicationInfo);
    }

    public static final String md5(File file) {
        file.getClass();
        Checksum fileChecksum = FileChecksums.Companion.getFileChecksum(file, 0);
        if (fileChecksum == null) {
            return null;
        }
        return fileChecksum.getHex();
    }

    public static final String md5(String str) {
        str.getClass();
        byte[] bytes = str.getBytes(Charsets.UTF_8);
        bytes.getClass();
        return md5(bytes);
    }

    public static final String md5(byte[] bArr) throws NoSuchAlgorithmException {
        bArr.getClass();
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(bArr);
        byte[] bArrDigest = messageDigest.digest();
        bArrDigest.getClass();
        return hex(bArrDigest);
    }

    public static final void nextElement(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int next;
        xmlPullParser.getClass();
        do {
            next = xmlPullParser.next();
            if (next == 1) {
                return;
            }
        } while (next != 2);
    }

    public static final boolean nextElementWithin(XmlPullParser xmlPullParser, int i) throws XmlPullParserException, IOException {
        xmlPullParser.getClass();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1) {
                return false;
            }
            if (next == 3 && xmlPullParser.getDepth() == i) {
                return false;
            }
            if (next == 2 && xmlPullParser.getDepth() == i + 1) {
                return true;
            }
        }
    }

    public static final Context rootContext(Context context) {
        context.getClass();
        if (!(context instanceof ContextThemeWrapper)) {
            return context;
        }
        Context baseContext = ((ContextWrapper) context).getBaseContext();
        baseContext.getClass();
        return rootContext(baseContext);
    }

    public static final String safeIntern(String str) {
        if (str == null) {
            return null;
        }
        String strIntern = str.intern();
        strIntern.getClass();
        return strIntern;
    }

    public static final boolean sendToActivityThread(Message message) {
        message.getClass();
        return HiddenApiKt.sendMessage2ActivityThread(message);
    }

    public static final String sha1(File file) {
        file.getClass();
        Checksum fileChecksum = FileChecksums.Companion.getFileChecksum(file, 1);
        if (fileChecksum == null) {
            return null;
        }
        return fileChecksum.getHex();
    }

    public static final String sha1(String str) {
        str.getClass();
        byte[] bytes = str.getBytes(Charsets.UTF_8);
        bytes.getClass();
        return sha1(bytes);
    }

    public static final String sha1(byte[] bArr) throws NoSuchAlgorithmException {
        bArr.getClass();
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        messageDigest.update(bArr);
        byte[] bArrDigest = messageDigest.digest();
        bArrDigest.getClass();
        return hex(bArrDigest);
    }

    public static final String sha256(File file) {
        file.getClass();
        Checksum fileChecksum = FileChecksums.Companion.getFileChecksum(file, 2);
        if (fileChecksum == null) {
            return null;
        }
        return fileChecksum.getHex();
    }

    public static final String sha256(String str) {
        str.getClass();
        byte[] bytes = str.getBytes(Charsets.UTF_8);
        bytes.getClass();
        return sha256(bytes);
    }

    public static final String sha256(byte[] bArr) throws NoSuchAlgorithmException {
        bArr.getClass();
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(bArr);
        byte[] bArrDigest = messageDigest.digest();
        bArrDigest.getClass();
        return hex(bArrDigest);
    }

    public static final String simpleString(UUID uuid) {
        uuid.getClass();
        String string = uuid.toString();
        string.getClass();
        return StringsKt.replace$default(string, "-", "", false, 4, null);
    }

    public static final void skipCurrentTag(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        xmlPullParser.getClass();
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && xmlPullParser.getDepth() <= depth) {
                return;
            }
        }
    }

    public static final OffsetDateTime timestampWithZone(long j) {
        OffsetDateTime offsetDateTimeOfInstant = OffsetDateTime.ofInstant(Instant.ofEpochMilli(j), ZoneId.systemDefault());
        offsetDateTimeOfInstant.getClass();
        return offsetDateTimeOfInstant;
    }

    public static final Bitmap toBitmap(Drawable drawable) {
        Bitmap bitmapCreateBitmap;
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmapCreateBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            bitmapCreateBitmap.getClass();
        } else {
            bitmapCreateBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            bitmapCreateBitmap.getClass();
        }
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }

    public static final Icon toIcon(Drawable drawable) {
        Bitmap bitmap = toBitmap(drawable);
        if (bitmap == null) {
            return null;
        }
        return Icon.createWithBitmap(bitmap);
    }

    public static final PluginId toPluginId(String str) {
        str.getClass();
        return new PluginId(str);
    }

    public static final void unbindServiceSafely(ServiceConnection serviceConnection, Context context) {
        serviceConnection.getClass();
        context.getClass();
        try {
            context.unbindService(serviceConnection);
        } catch (Exception unused) {
        }
    }

    public static final void unlinkToDeathSafely(IInterface iInterface, IBinder.DeathRecipient deathRecipient) {
        deathRecipient.getClass();
        if (iInterface == null) {
            return;
        }
        try {
            IBinder iBinderAsBinder = iInterface.asBinder();
            if (iBinderAsBinder == null) {
                return;
            }
            iBinderAsBinder.unlinkToDeath(deathRecipient, 0);
        } catch (NoSuchElementException unused) {
        }
    }

    public static final void unregisterReceiverSafely(BroadcastReceiver broadcastReceiver, Context context) {
        broadcastReceiver.getClass();
        context.getClass();
        try {
            context.unregisterReceiver(broadcastReceiver);
        } catch (Exception unused) {
        }
    }

    public static final Lazy unsafeLazy(Function0 function0) {
        function0.getClass();
        return LazyKt.lazy(LazyThreadSafetyMode.NONE, function0);
    }

    public static final /* synthetic */ Class validPluginClassOrThrow(String str, ClassLoader classLoader, boolean z) {
        Object objM2707constructorimpl;
        str.getClass();
        try {
            Result.Companion companion = Result.Companion;
            Class<?> cls = Class.forName(str, z, classLoader);
            Intrinsics.reifiedOperationMarker(4, "T");
            objM2707constructorimpl = Result.m2707constructorimpl(cls.asSubclass(Object.class));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM2709exceptionOrNullimpl = Result.m2709exceptionOrNullimpl(objM2707constructorimpl);
        if (thM2709exceptionOrNullimpl != null) {
            throw new NotPluginClassException(str, thM2709exceptionOrNullimpl);
        }
        objM2707constructorimpl.getClass();
        return (Class) objM2707constructorimpl;
    }

    public static /* synthetic */ Class validPluginClassOrThrow$default(String str, ClassLoader classLoader, boolean z, int i, Object obj) {
        Object objM2707constructorimpl;
        if ((i & 2) != 0) {
            z = false;
        }
        str.getClass();
        try {
            Result.Companion companion = Result.Companion;
            Class<?> cls = Class.forName(str, z, classLoader);
            Intrinsics.reifiedOperationMarker(4, "T");
            objM2707constructorimpl = Result.m2707constructorimpl(cls.asSubclass(Object.class));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM2709exceptionOrNullimpl = Result.m2709exceptionOrNullimpl(objM2707constructorimpl);
        if (thM2709exceptionOrNullimpl != null) {
            throw new NotPluginClassException(str, thM2709exceptionOrNullimpl);
        }
        objM2707constructorimpl.getClass();
        return (Class) objM2707constructorimpl;
    }
}
