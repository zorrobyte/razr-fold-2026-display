package F;

import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final class b extends SingleLineTransformationMethod {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public Locale f117a;

    @Override // android.text.method.ReplacementTransformationMethod, android.text.method.TransformationMethod
    public final CharSequence getTransformation(CharSequence charSequence, View view) {
        CharSequence transformation = super.getTransformation(charSequence, view);
        if (transformation != null) {
            return transformation.toString().toUpperCase(this.f117a);
        }
        return null;
    }
}
