package com.android.launcher3.icons;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

/* JADX INFO: loaded from: classes.dex */
public abstract class GzipCompression {
    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception unused) {
            }
        }
    }

    public static String decompress(byte[] bArr) throws Throwable {
        BufferedReader bufferedReader;
        StringBuilder sb = new StringBuilder();
        if (bArr == null || bArr.length == 0) {
            return "";
        }
        if (isCompressed(bArr)) {
            GZIPInputStream gZIPInputStream = null;
            try {
                GZIPInputStream gZIPInputStream2 = new GZIPInputStream(new ByteArrayInputStream(bArr));
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(gZIPInputStream2, "UTF-8"));
                    while (true) {
                        try {
                            String line = bufferedReader.readLine();
                            if (line == null) {
                                break;
                            }
                            sb.append(line);
                        } catch (IOException unused) {
                            gZIPInputStream = gZIPInputStream2;
                            closeQuietly(gZIPInputStream);
                        } catch (Throwable th) {
                            th = th;
                            gZIPInputStream = gZIPInputStream2;
                            closeQuietly(gZIPInputStream);
                            closeQuietly(bufferedReader);
                            throw th;
                        }
                    }
                    closeQuietly(gZIPInputStream2);
                } catch (IOException unused2) {
                    bufferedReader = null;
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader = null;
                }
            } catch (IOException unused3) {
                bufferedReader = null;
            } catch (Throwable th3) {
                th = th3;
                bufferedReader = null;
            }
            closeQuietly(bufferedReader);
        } else {
            sb.append(bArr);
        }
        return sb.toString();
    }

    public static boolean isCompressed(byte[] bArr) {
        return bArr[0] == 31 && bArr[1] == -117;
    }
}
