package com.bumptech.glide.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Queue;

/* JADX INFO: loaded from: classes.dex */
public final class ExceptionPassthroughInputStream extends InputStream {
    private static final Queue POOL = Util.createQueue(0);
    private IOException exception;
    private InputStream wrapped;

    ExceptionPassthroughInputStream() {
    }

    public static ExceptionPassthroughInputStream obtain(InputStream inputStream) {
        ExceptionPassthroughInputStream exceptionPassthroughInputStream;
        Queue queue = POOL;
        synchronized (queue) {
            exceptionPassthroughInputStream = (ExceptionPassthroughInputStream) queue.poll();
        }
        if (exceptionPassthroughInputStream == null) {
            exceptionPassthroughInputStream = new ExceptionPassthroughInputStream();
        }
        exceptionPassthroughInputStream.setInputStream(inputStream);
        return exceptionPassthroughInputStream;
    }

    @Override // java.io.InputStream
    public int available() {
        return this.wrapped.available();
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.wrapped.close();
    }

    public IOException getException() {
        return this.exception;
    }

    @Override // java.io.InputStream
    public void mark(int i) {
        this.wrapped.mark(i);
    }

    @Override // java.io.InputStream
    public boolean markSupported() {
        return this.wrapped.markSupported();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        try {
            return this.wrapped.read();
        } catch (IOException e) {
            this.exception = e;
            throw e;
        }
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        try {
            return this.wrapped.read(bArr);
        } catch (IOException e) {
            this.exception = e;
            throw e;
        }
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        try {
            return this.wrapped.read(bArr, i, i2);
        } catch (IOException e) {
            this.exception = e;
            throw e;
        }
    }

    public void release() {
        this.exception = null;
        this.wrapped = null;
        Queue queue = POOL;
        synchronized (queue) {
            queue.offer(this);
        }
    }

    @Override // java.io.InputStream
    public synchronized void reset() {
        this.wrapped.reset();
    }

    void setInputStream(InputStream inputStream) {
        this.wrapped = inputStream;
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        try {
            return this.wrapped.skip(j);
        } catch (IOException e) {
            this.exception = e;
            throw e;
        }
    }
}
