package com.motorola.plugin.core.misc;

import com.motorola.plugin.core.ExtensionsKt;
import com.motorola.plugin.core.PluginConfigKt;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FileChecksums.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class FileChecksums {
    private static final String ALGO_MD5 = "MD5";
    private static final String ALGO_SHA1 = "SHA-1";
    private static final String ALGO_SHA256 = "SHA-256";
    private static final String ALGO_SHA512 = "SHA-512";
    public static final Companion Companion = new Companion(null);

    /* JADX INFO: compiled from: FileChecksums.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final byte[] getFileChecksumByteArray(final File file, int i) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance(FileChecksums.Companion.getMessageDigestAlgoForChecksumKind(i));
                    messageDigest.update(fileInputStream.getChannel().map(FileChannel.MapMode.READ_ONLY, 0L, file.length()));
                    byte[] bArrDigest = messageDigest.digest();
                    CloseableKt.closeFinally(fileInputStream, null);
                    return bArrDigest;
                } finally {
                }
            } catch (IOException unused) {
                PluginConfigKt.trace$default(null, null, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.misc.FileChecksums$Companion$getFileChecksumByteArray$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public final Object mo2224invoke() {
                        return "Error reading " + ((Object) file.getAbsolutePath()) + " to compute hash.";
                    }
                }, 63, null);
                return null;
            } catch (NoSuchAlgorithmException unused2) {
                PluginConfigKt.trace$default(null, null, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.misc.FileChecksums$Companion$getFileChecksumByteArray$3
                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public final Object mo2224invoke() {
                        return "Device does not support MessageDigest algorithm";
                    }
                }, 63, null);
                return null;
            }
        }

        private final String getMessageDigestAlgoForChecksumKind(int i) throws NoSuchAlgorithmException {
            if (i == 0) {
                return FileChecksums.ALGO_MD5;
            }
            if (i == 1) {
                return FileChecksums.ALGO_SHA1;
            }
            if (i == 2) {
                return FileChecksums.ALGO_SHA256;
            }
            if (i == 3) {
                return FileChecksums.ALGO_SHA512;
            }
            throw new NoSuchAlgorithmException(Intrinsics.stringPlus("Invalid checksum type: ", Integer.valueOf(i)));
        }

        public final Checksum getFileChecksum(File file, int i) {
            file.getClass();
            byte[] fileChecksumByteArray = getFileChecksumByteArray(file, i);
            if (fileChecksumByteArray == null) {
                return null;
            }
            return new Checksum(i, ExtensionsKt.hex(fileChecksumByteArray));
        }
    }
}
