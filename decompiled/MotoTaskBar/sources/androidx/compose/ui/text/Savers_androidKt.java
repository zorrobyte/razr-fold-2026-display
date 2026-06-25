package androidx.compose.ui.text;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.compose.runtime.saveable.Saver;
import androidx.compose.runtime.saveable.SaverKt;
import androidx.compose.runtime.saveable.SaverScope;
import androidx.compose.ui.text.PlatformParagraphStyle;
import androidx.compose.ui.text.style.LineBreak;
import androidx.compose.ui.text.style.TextMotion;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* JADX INFO: compiled from: Savers.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class Savers_androidKt {
    private static final Saver PlatformParagraphStyleSaver = SaverKt.Saver(new Function2() { // from class: androidx.compose.ui.text.Savers_androidKt$PlatformParagraphStyleSaver$1
        public final Object invoke(SaverScope saverScope, PlatformParagraphStyle platformParagraphStyle) {
            return CollectionsKt.arrayListOf(SaversKt.save(Boolean.valueOf(platformParagraphStyle.getIncludeFontPadding())), SaversKt.save(EmojiSupportMatch.m766boximpl(platformParagraphStyle.m779getEmojiSupportMatch_3YsG6Y())));
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
            return invoke((SaverScope) null, (PlatformParagraphStyle) obj2);
        }
    }, new Function1() { // from class: androidx.compose.ui.text.Savers_androidKt$PlatformParagraphStyleSaver$2
        @Override // kotlin.jvm.functions.Function1
        public final PlatformParagraphStyle invoke(Object obj) {
            obj.getClass();
            List list = (List) obj;
            Object obj2 = list.get(0);
            Boolean bool = obj2 != null ? (Boolean) obj2 : null;
            bool.getClass();
            boolean zBooleanValue = bool.booleanValue();
            Object obj3 = list.get(1);
            EmojiSupportMatch emojiSupportMatch = obj3 != null ? (EmojiSupportMatch) obj3 : null;
            emojiSupportMatch.getClass();
            return new PlatformParagraphStyle(emojiSupportMatch.m772unboximpl(), zBooleanValue, null);
        }
    });
    private static final Saver LineBreakSaver = SaverKt.Saver(new Function2() { // from class: androidx.compose.ui.text.Savers_androidKt$LineBreakSaver$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
            return m790invokenI23V4A(null, ((LineBreak) obj2).m901unboximpl());
        }

        /* JADX INFO: renamed from: invoke-nI23V4A, reason: not valid java name */
        public final Object m790invokenI23V4A(SaverScope saverScope, int i) {
            return Integer.valueOf(i);
        }
    }, new Function1() { // from class: androidx.compose.ui.text.Savers_androidKt$LineBreakSaver$2
        @Override // kotlin.jvm.functions.Function1
        /* JADX INFO: renamed from: invoke-8aCASmQ, reason: not valid java name and merged with bridge method [inline-methods] */
        public final LineBreak invoke(Object obj) {
            obj.getClass();
            return LineBreak.m892boximpl(LineBreak.m893constructorimpl(((Integer) obj).intValue()));
        }
    });
    private static final Saver TextMotionSaver = SaverKt.Saver(new Function2() { // from class: androidx.compose.ui.text.Savers_androidKt$TextMotionSaver$1
        public final Object invoke(SaverScope saverScope, TextMotion textMotion) {
            return CollectionsKt.arrayListOf(SaversKt.save(TextMotion.Linearity.m964boximpl(textMotion.m963getLinearity4e0Vf04$ui_text_release())), SaversKt.save(Boolean.valueOf(textMotion.getSubpixelTextPositioning$ui_text_release())));
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
            return invoke((SaverScope) null, (TextMotion) obj2);
        }
    }, new Function1() { // from class: androidx.compose.ui.text.Savers_androidKt$TextMotionSaver$2
        @Override // kotlin.jvm.functions.Function1
        public final TextMotion invoke(Object obj) {
            obj.getClass();
            List list = (List) obj;
            Object obj2 = list.get(0);
            TextMotion.Linearity linearity = obj2 != null ? (TextMotion.Linearity) obj2 : null;
            linearity.getClass();
            int iM970unboximpl = linearity.m970unboximpl();
            Object obj3 = list.get(1);
            Boolean bool = obj3 != null ? (Boolean) obj3 : null;
            bool.getClass();
            return new TextMotion(iM970unboximpl, bool.booleanValue(), null);
        }
    });

    public static final Saver getSaver(PlatformParagraphStyle.Companion companion) {
        return PlatformParagraphStyleSaver;
    }

    public static final Saver getSaver(LineBreak.Companion companion) {
        return LineBreakSaver;
    }

    public static final Saver getSaver(TextMotion.Companion companion) {
        return TextMotionSaver;
    }
}
