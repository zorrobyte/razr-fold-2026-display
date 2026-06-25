package com.bumptech.glide.load.resource.drawable;

import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.ImageHeaderParserUtils;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.resource.DefaultOnHeaderDecodedListener;
import com.bumptech.glide.util.ByteBufferUtil;
import com.bumptech.glide.util.Util;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class AnimatedImageDecoder {
    private final ArrayPool arrayPool;
    private final List imageHeaderParsers;

    final class AnimatedImageDrawableResource implements Resource {
        private final AnimatedImageDrawable imageDrawable;

        AnimatedImageDrawableResource(AnimatedImageDrawable animatedImageDrawable) {
            this.imageDrawable = animatedImageDrawable;
        }

        @Override // com.bumptech.glide.load.engine.Resource
        public AnimatedImageDrawable get() {
            return this.imageDrawable;
        }

        @Override // com.bumptech.glide.load.engine.Resource
        public Class getResourceClass() {
            return Drawable.class;
        }

        @Override // com.bumptech.glide.load.engine.Resource
        public int getSize() {
            return this.imageDrawable.getIntrinsicWidth() * this.imageDrawable.getIntrinsicHeight() * Util.getBytesPerPixel(Bitmap.Config.ARGB_8888) * 2;
        }

        @Override // com.bumptech.glide.load.engine.Resource
        public void recycle() {
            this.imageDrawable.stop();
            this.imageDrawable.clearAnimationCallbacks();
        }
    }

    final class ByteBufferAnimatedImageDecoder implements ResourceDecoder {
        private final AnimatedImageDecoder delegate;

        ByteBufferAnimatedImageDecoder(AnimatedImageDecoder animatedImageDecoder) {
            this.delegate = animatedImageDecoder;
        }

        @Override // com.bumptech.glide.load.ResourceDecoder
        public Resource decode(ByteBuffer byteBuffer, int i, int i2, Options options) {
            return this.delegate.decode(ImageDecoder.createSource(byteBuffer), i, i2, options);
        }

        @Override // com.bumptech.glide.load.ResourceDecoder
        public boolean handles(ByteBuffer byteBuffer, Options options) {
            return this.delegate.handles(byteBuffer);
        }
    }

    final class StreamAnimatedImageDecoder implements ResourceDecoder {
        private final AnimatedImageDecoder delegate;

        StreamAnimatedImageDecoder(AnimatedImageDecoder animatedImageDecoder) {
            this.delegate = animatedImageDecoder;
        }

        @Override // com.bumptech.glide.load.ResourceDecoder
        public Resource decode(InputStream inputStream, int i, int i2, Options options) {
            return this.delegate.decode(ImageDecoder.createSource(ByteBufferUtil.fromStream(inputStream)), i, i2, options);
        }

        @Override // com.bumptech.glide.load.ResourceDecoder
        public boolean handles(InputStream inputStream, Options options) {
            return this.delegate.handles(inputStream);
        }
    }

    private AnimatedImageDecoder(List list, ArrayPool arrayPool) {
        this.imageHeaderParsers = list;
        this.arrayPool = arrayPool;
    }

    public static ResourceDecoder byteBufferDecoder(List list, ArrayPool arrayPool) {
        return new ByteBufferAnimatedImageDecoder(new AnimatedImageDecoder(list, arrayPool));
    }

    private boolean isHandled(ImageHeaderParser.ImageType imageType) {
        return imageType == ImageHeaderParser.ImageType.ANIMATED_WEBP || imageType == ImageHeaderParser.ImageType.ANIMATED_AVIF;
    }

    public static ResourceDecoder streamDecoder(List list, ArrayPool arrayPool) {
        return new StreamAnimatedImageDecoder(new AnimatedImageDecoder(list, arrayPool));
    }

    Resource decode(ImageDecoder.Source source, int i, int i2, Options options) throws IOException {
        Drawable drawableDecodeDrawable = ImageDecoder.decodeDrawable(source, new DefaultOnHeaderDecodedListener(i, i2, options));
        if (drawableDecodeDrawable instanceof AnimatedImageDrawable) {
            return new AnimatedImageDrawableResource((AnimatedImageDrawable) drawableDecodeDrawable);
        }
        throw new IOException("Received unexpected drawable type for animated image, failing: " + drawableDecodeDrawable);
    }

    boolean handles(InputStream inputStream) {
        return isHandled(ImageHeaderParserUtils.getType(this.imageHeaderParsers, inputStream, this.arrayPool));
    }

    boolean handles(ByteBuffer byteBuffer) {
        return isHandled(ImageHeaderParserUtils.getType(this.imageHeaderParsers, byteBuffer));
    }
}
