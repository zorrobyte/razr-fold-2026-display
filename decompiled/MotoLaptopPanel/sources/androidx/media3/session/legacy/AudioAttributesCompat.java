package androidx.media3.session.legacy;

import android.media.AudioAttributes;
import android.util.SparseIntArray;
import androidx.media3.common.util.Assertions;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class AudioAttributesCompat {
    private static final int[] SDK_USAGES;
    private static final SparseIntArray SUPPRESSIBLE_USAGES;
    static boolean sForceLegacyBehavior;
    public final AudioAttributesImpl mImpl;

    public interface AudioAttributesImpl {
        int getContentType();

        int getFlags();

        int getUsage();
    }

    public abstract class AudioAttributesImplApi21 implements AudioAttributesImpl {
        public AudioAttributes mAudioAttributes;
        public int mLegacyStreamType;

        AudioAttributesImplApi21(AudioAttributes audioAttributes, int i) {
            this.mAudioAttributes = audioAttributes;
            this.mLegacyStreamType = i;
        }

        public boolean equals(Object obj) {
            if (obj instanceof AudioAttributesImplApi21) {
                return Objects.equals(this.mAudioAttributes, ((AudioAttributesImplApi21) obj).mAudioAttributes);
            }
            return false;
        }

        @Override // androidx.media3.session.legacy.AudioAttributesCompat.AudioAttributesImpl
        public int getContentType() {
            return ((AudioAttributes) Assertions.checkNotNull(this.mAudioAttributes)).getContentType();
        }

        @Override // androidx.media3.session.legacy.AudioAttributesCompat.AudioAttributesImpl
        public int getFlags() {
            return ((AudioAttributes) Assertions.checkNotNull(this.mAudioAttributes)).getFlags();
        }

        @Override // androidx.media3.session.legacy.AudioAttributesCompat.AudioAttributesImpl
        public int getUsage() {
            return ((AudioAttributes) Assertions.checkNotNull(this.mAudioAttributes)).getUsage();
        }

        public int hashCode() {
            return ((AudioAttributes) Assertions.checkNotNull(this.mAudioAttributes)).hashCode();
        }

        public String toString() {
            return "AudioAttributesCompat: audioattributes=" + this.mAudioAttributes;
        }
    }

    public class AudioAttributesImplApi26 extends AudioAttributesImplApi21 {
        AudioAttributesImplApi26(AudioAttributes audioAttributes) {
            super(audioAttributes, -1);
        }
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        SUPPRESSIBLE_USAGES = sparseIntArray;
        sparseIntArray.put(5, 1);
        sparseIntArray.put(6, 2);
        sparseIntArray.put(7, 2);
        sparseIntArray.put(8, 1);
        sparseIntArray.put(9, 1);
        sparseIntArray.put(10, 1);
        SDK_USAGES = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 16};
    }

    AudioAttributesCompat(AudioAttributesImpl audioAttributesImpl) {
        this.mImpl = audioAttributesImpl;
    }

    public static AudioAttributesCompat wrap(Object obj) {
        if (sForceLegacyBehavior) {
            return null;
        }
        return new AudioAttributesCompat(new AudioAttributesImplApi26((AudioAttributes) obj));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof AudioAttributesCompat)) {
            return false;
        }
        AudioAttributesCompat audioAttributesCompat = (AudioAttributesCompat) obj;
        AudioAttributesImpl audioAttributesImpl = this.mImpl;
        return audioAttributesImpl == null ? audioAttributesCompat.mImpl == null : audioAttributesImpl.equals(audioAttributesCompat.mImpl);
    }

    public int getContentType() {
        return this.mImpl.getContentType();
    }

    public int getFlags() {
        return this.mImpl.getFlags();
    }

    public int getUsage() {
        return this.mImpl.getUsage();
    }

    public int hashCode() {
        return this.mImpl.hashCode();
    }

    public String toString() {
        return this.mImpl.toString();
    }
}
