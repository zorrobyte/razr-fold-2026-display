package com.google.android.setupdesign.span;

import android.content.Context;
import android.text.TextPaint;

/* JADX INFO: loaded from: classes.dex */
public class BoldLinkSpan extends LinkSpan {
    static final int BOLD_TEXT_ADJUSTMENT = 300;
    private final Context context;

    public BoldLinkSpan(Context context, String str) {
        super(str);
        this.context = context;
    }

    @Override // com.google.android.setupdesign.span.LinkSpan, android.text.style.ClickableSpan, android.text.style.CharacterStyle
    public void updateDrawState(TextPaint textPaint) {
        super.updateDrawState(textPaint);
        textPaint.setFakeBoldText(this.context.getResources().getConfiguration().fontWeightAdjustment == BOLD_TEXT_ADJUSTMENT);
        textPaint.setUnderlineText(true);
    }
}
