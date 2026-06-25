package androidx.appcompat.widget;

import android.R;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.appcompat.R$styleable;
import b.AbstractC0122a;
import java.lang.ref.WeakReference;
import p.AbstractC0156b;
import p.InterfaceC0155a;

/* JADX INFO: renamed from: androidx.appcompat.widget.v, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0084v {

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public static final int[] f1316d = {R.attr.indeterminateDrawable, R.attr.progressDrawable};

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f1317a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final Object f1318b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Object f1319c;

    public /* synthetic */ C0084v(View view, int i2) {
        this.f1317a = i2;
        this.f1318b = view;
    }

    public C0084v(B b2, WeakReference weakReference) {
        this.f1317a = 2;
        this.f1319c = b2;
        this.f1318b = weakReference;
    }

    public void a() {
        n0 n0Var;
        ImageView imageView = (ImageView) this.f1318b;
        Drawable drawable = imageView.getDrawable();
        if (drawable != null) {
            Rect rect = G.f992a;
        }
        if (drawable == null || (n0Var = (n0) this.f1319c) == null) {
            return;
        }
        C0082t.m(drawable, n0Var, imageView.getDrawableState());
    }

    public void b(int i2, Handler handler) {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        handler.post(new F.e(this, i2));
    }

    public void c(Typeface typeface, Handler handler) {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        handler.post(new J.b(this, typeface, 2));
    }

    public void d(AttributeSet attributeSet, int i2) {
        int resourceId;
        Object obj = this.f1318b;
        switch (this.f1317a) {
            case 0:
                ProgressBar progressBar = (ProgressBar) obj;
                f0.b bVarM = f0.b.m(progressBar.getContext(), attributeSet, f1316d, i2, 0);
                Drawable drawableG = bVarM.g(0);
                if (drawableG != null) {
                    if (drawableG instanceof AnimationDrawable) {
                        AnimationDrawable animationDrawable = (AnimationDrawable) drawableG;
                        int numberOfFrames = animationDrawable.getNumberOfFrames();
                        AnimationDrawable animationDrawable2 = new AnimationDrawable();
                        animationDrawable2.setOneShot(animationDrawable.isOneShot());
                        for (int i3 = 0; i3 < numberOfFrames; i3++) {
                            Drawable drawableE = e(animationDrawable.getFrame(i3), true);
                            drawableE.setLevel(10000);
                            animationDrawable2.addFrame(drawableE, animationDrawable.getDuration(i3));
                        }
                        animationDrawable2.setLevel(10000);
                        drawableG = animationDrawable2;
                    }
                    progressBar.setIndeterminateDrawable(drawableG);
                }
                Drawable drawableG2 = bVarM.g(1);
                if (drawableG2 != null) {
                    progressBar.setProgressDrawable(e(drawableG2, false));
                }
                bVarM.n();
                return;
            default:
                ImageView imageView = (ImageView) obj;
                f0.b bVarM2 = f0.b.m(imageView.getContext(), attributeSet, R$styleable.AppCompatImageView, i2, 0);
                try {
                    Drawable drawable = imageView.getDrawable();
                    TypedArray typedArray = (TypedArray) bVarM2.f2538c;
                    if (drawable == null && (resourceId = typedArray.getResourceId(R$styleable.AppCompatImageView_srcCompat, -1)) != -1 && (drawable = AbstractC0122a.a(imageView.getContext(), resourceId)) != null) {
                        imageView.setImageDrawable(drawable);
                    }
                    if (drawable != null) {
                        Rect rect = G.f992a;
                    }
                    if (typedArray.hasValue(R$styleable.AppCompatImageView_tint)) {
                        imageView.setImageTintList(bVarM2.e(R$styleable.AppCompatImageView_tint));
                    }
                    if (typedArray.hasValue(R$styleable.AppCompatImageView_tintMode)) {
                        imageView.setImageTintMode(G.c(typedArray.getInt(R$styleable.AppCompatImageView_tintMode, -1), null));
                        break;
                    }
                    bVarM2.n();
                    return;
                } catch (Throwable th) {
                    bVarM2.n();
                    throw th;
                }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Drawable e(Drawable drawable, boolean z2) {
        if (drawable instanceof InterfaceC0155a) {
            ((AbstractC0156b) ((InterfaceC0155a) drawable)).getClass();
        } else {
            if (drawable instanceof LayerDrawable) {
                LayerDrawable layerDrawable = (LayerDrawable) drawable;
                int numberOfLayers = layerDrawable.getNumberOfLayers();
                Drawable[] drawableArr = new Drawable[numberOfLayers];
                for (int i2 = 0; i2 < numberOfLayers; i2++) {
                    int id = layerDrawable.getId(i2);
                    drawableArr[i2] = e(layerDrawable.getDrawable(i2), id == 16908301 || id == 16908303);
                }
                LayerDrawable layerDrawable2 = new LayerDrawable(drawableArr);
                for (int i3 = 0; i3 < numberOfLayers; i3++) {
                    layerDrawable2.setId(i3, layerDrawable.getId(i3));
                }
                return layerDrawable2;
            }
            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                Bitmap bitmap = bitmapDrawable.getBitmap();
                if (((Bitmap) this.f1319c) == null) {
                    this.f1319c = bitmap;
                }
                ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(new float[]{5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f}, null, null));
                shapeDrawable.getPaint().setShader(new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP));
                shapeDrawable.getPaint().setColorFilter(bitmapDrawable.getPaint().getColorFilter());
                return z2 ? new ClipDrawable(shapeDrawable, 3, 1) : shapeDrawable;
            }
        }
        return drawable;
    }
}
