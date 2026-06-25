package androidx.compose.ui.text.android;

import android.text.StaticLayout;

/* JADX INFO: compiled from: StaticLayoutFactory.android.kt */
/* JADX INFO: loaded from: classes.dex */
final class StaticLayoutFactory23 implements StaticLayoutFactoryImpl {
    @Override // androidx.compose.ui.text.android.StaticLayoutFactoryImpl
    public StaticLayout create(StaticLayoutParams staticLayoutParams) {
        StaticLayout.Builder builderObtain = StaticLayout.Builder.obtain(staticLayoutParams.getText(), staticLayoutParams.getStart(), staticLayoutParams.getEnd(), staticLayoutParams.getPaint(), staticLayoutParams.getWidth());
        builderObtain.setTextDirection(staticLayoutParams.getTextDir());
        builderObtain.setAlignment(staticLayoutParams.getAlignment());
        builderObtain.setMaxLines(staticLayoutParams.getMaxLines());
        builderObtain.setEllipsize(staticLayoutParams.getEllipsize());
        builderObtain.setEllipsizedWidth(staticLayoutParams.getEllipsizedWidth());
        builderObtain.setLineSpacing(staticLayoutParams.getLineSpacingExtra(), staticLayoutParams.getLineSpacingMultiplier());
        builderObtain.setIncludePad(staticLayoutParams.getIncludePadding());
        builderObtain.setBreakStrategy(staticLayoutParams.getBreakStrategy());
        builderObtain.setHyphenationFrequency(staticLayoutParams.getHyphenationFrequency());
        builderObtain.setIndents(staticLayoutParams.getLeftIndents(), staticLayoutParams.getRightIndents());
        StaticLayoutFactory26.setJustificationMode(builderObtain, staticLayoutParams.getJustificationMode());
        StaticLayoutFactory28.setUseLineSpacingFromFallbacks(builderObtain, staticLayoutParams.getUseFallbackLineSpacing());
        StaticLayoutFactory33.setLineBreakConfig(builderObtain, staticLayoutParams.getLineBreakStyle(), staticLayoutParams.getLineBreakWordStyle());
        StaticLayoutFactory35.disableUseBoundsForWidth(builderObtain);
        return builderObtain.build();
    }

    @Override // androidx.compose.ui.text.android.StaticLayoutFactoryImpl
    public boolean isFallbackLineSpacingEnabled(StaticLayout staticLayout, boolean z) {
        return StaticLayoutFactory33.isFallbackLineSpacingEnabled(staticLayout);
    }
}
