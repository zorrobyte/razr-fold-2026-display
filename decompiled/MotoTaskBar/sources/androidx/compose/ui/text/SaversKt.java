package androidx.compose.ui.text;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.compose.runtime.saveable.Saver;
import androidx.compose.runtime.saveable.SaverKt;
import androidx.compose.runtime.saveable.SaverScope;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.LinkAnnotation;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.intl.Locale;
import androidx.compose.ui.text.intl.LocaleList;
import androidx.compose.ui.text.style.BaselineShift;
import androidx.compose.ui.text.style.Hyphens;
import androidx.compose.ui.text.style.LineBreak;
import androidx.compose.ui.text.style.LineHeightStyle;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextDirection;
import androidx.compose.ui.text.style.TextGeometricTransform;
import androidx.compose.ui.text.style.TextIndent;
import androidx.compose.ui.text.style.TextMotion;
import androidx.compose.ui.unit.TextUnit;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.compose.ui.unit.TextUnitType;
import java.util.ArrayList;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Savers.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SaversKt {
    private static final Saver AnnotatedStringSaver = SaverKt.Saver(new Function2() { // from class: androidx.compose.ui.text.SaversKt$AnnotatedStringSaver$1
        public final Object invoke(SaverScope saverScope, AnnotatedString annotatedString) {
            return CollectionsKt.arrayListOf(SaversKt.save(annotatedString.getText()), SaversKt.save(annotatedString.getAnnotations$ui_text_release(), SaversKt.AnnotationRangeListSaver, saverScope));
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
            return invoke((SaverScope) null, (AnnotatedString) obj2);
        }
    }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$AnnotatedStringSaver$2
        @Override // kotlin.jvm.functions.Function1
        public final AnnotatedString invoke(Object obj) {
            obj.getClass();
            List list = (List) obj;
            Object obj2 = list.get(1);
            Saver saver = SaversKt.AnnotationRangeListSaver;
            List list2 = ((!Intrinsics.areEqual(obj2, Boolean.FALSE) || (saver instanceof NonNullValueClassSaver)) && obj2 != null) ? (List) saver.restore(obj2) : null;
            Object obj3 = list.get(0);
            String str = obj3 != null ? (String) obj3 : null;
            str.getClass();
            return new AnnotatedString(list2, str);
        }
    });
    private static final Saver AnnotationRangeListSaver = SaverKt.Saver(new Function2() { // from class: androidx.compose.ui.text.SaversKt$AnnotationRangeListSaver$1
        public final Object invoke(SaverScope saverScope, List list) {
            ArrayList arrayList = new ArrayList(list.size());
            int size = list.size();
            for (int i = 0; i < size; i++) {
                arrayList.add(SaversKt.save((AnnotatedString.Range) list.get(i), SaversKt.AnnotationRangeSaver, saverScope));
            }
            return arrayList;
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
            return invoke((SaverScope) null, (List) obj2);
        }
    }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$AnnotationRangeListSaver$2
        @Override // kotlin.jvm.functions.Function1
        public final List invoke(Object obj) {
            obj.getClass();
            List list = (List) obj;
            ArrayList arrayList = new ArrayList(list.size());
            int size = list.size();
            for (int i = 0; i < size; i++) {
                Object obj2 = list.get(i);
                Saver saver = SaversKt.AnnotationRangeSaver;
                AnnotatedString.Range range = null;
                if ((!Intrinsics.areEqual(obj2, Boolean.FALSE) || (saver instanceof NonNullValueClassSaver)) && obj2 != null) {
                    range = (AnnotatedString.Range) saver.restore(obj2);
                }
                range.getClass();
                arrayList.add(range);
            }
            return arrayList;
        }
    });
    private static final Saver AnnotationRangeSaver = SaverKt.Saver(new Function2() { // from class: androidx.compose.ui.text.SaversKt$AnnotationRangeSaver$1

        /* JADX INFO: compiled from: Savers.kt */
        public abstract /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[AnnotationType.values().length];
                try {
                    iArr[AnnotationType.Paragraph.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[AnnotationType.Span.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[AnnotationType.VerbatimTts.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                try {
                    iArr[AnnotationType.Url.ordinal()] = 4;
                } catch (NoSuchFieldError unused4) {
                }
                try {
                    iArr[AnnotationType.Link.ordinal()] = 5;
                } catch (NoSuchFieldError unused5) {
                }
                try {
                    iArr[AnnotationType.Clickable.ordinal()] = 6;
                } catch (NoSuchFieldError unused6) {
                }
                try {
                    iArr[AnnotationType.String.ordinal()] = 7;
                } catch (NoSuchFieldError unused7) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public final Object invoke(SaverScope saverScope, AnnotatedString.Range range) {
            AnnotationType annotationType;
            Object objSave;
            Object item = range.getItem();
            if (item instanceof ParagraphStyle) {
                annotationType = AnnotationType.Paragraph;
            } else if (item instanceof SpanStyle) {
                annotationType = AnnotationType.Span;
            } else if (item instanceof VerbatimTtsAnnotation) {
                annotationType = AnnotationType.VerbatimTts;
            } else if (item instanceof UrlAnnotation) {
                annotationType = AnnotationType.Url;
            } else if (item instanceof LinkAnnotation.Url) {
                annotationType = AnnotationType.Link;
            } else if (item instanceof LinkAnnotation.Clickable) {
                annotationType = AnnotationType.Clickable;
            } else {
                if (!(item instanceof StringAnnotation)) {
                    throw new UnsupportedOperationException();
                }
                annotationType = AnnotationType.String;
            }
            switch (WhenMappings.$EnumSwitchMapping$0[annotationType.ordinal()]) {
                case 1:
                    Object item2 = range.getItem();
                    item2.getClass();
                    objSave = SaversKt.save((ParagraphStyle) item2, SaversKt.getParagraphStyleSaver(), saverScope);
                    break;
                case 2:
                    Object item3 = range.getItem();
                    item3.getClass();
                    objSave = SaversKt.save((SpanStyle) item3, SaversKt.getSpanStyleSaver(), saverScope);
                    break;
                case 3:
                    Object item4 = range.getItem();
                    item4.getClass();
                    objSave = SaversKt.save((VerbatimTtsAnnotation) item4, SaversKt.VerbatimTtsAnnotationSaver, saverScope);
                    break;
                case 4:
                    Object item5 = range.getItem();
                    item5.getClass();
                    objSave = SaversKt.save((UrlAnnotation) item5, SaversKt.UrlAnnotationSaver, saverScope);
                    break;
                case 5:
                    Object item6 = range.getItem();
                    item6.getClass();
                    objSave = SaversKt.save((LinkAnnotation.Url) item6, SaversKt.LinkSaver, saverScope);
                    break;
                case 6:
                    Object item7 = range.getItem();
                    item7.getClass();
                    objSave = SaversKt.save((LinkAnnotation.Clickable) item7, SaversKt.ClickableSaver, saverScope);
                    break;
                case 7:
                    Object item8 = range.getItem();
                    item8.getClass();
                    objSave = SaversKt.save(((StringAnnotation) item8).m806unboximpl());
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            return CollectionsKt.arrayListOf(SaversKt.save(annotationType), objSave, SaversKt.save(Integer.valueOf(range.getStart())), SaversKt.save(Integer.valueOf(range.getEnd())), SaversKt.save(range.getTag()));
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
            return invoke((SaverScope) null, (AnnotatedString.Range) obj2);
        }
    }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$AnnotationRangeSaver$2

        /* JADX INFO: compiled from: Savers.kt */
        public abstract /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[AnnotationType.values().length];
                try {
                    iArr[AnnotationType.Paragraph.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[AnnotationType.Span.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[AnnotationType.VerbatimTts.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                try {
                    iArr[AnnotationType.Url.ordinal()] = 4;
                } catch (NoSuchFieldError unused4) {
                }
                try {
                    iArr[AnnotationType.Link.ordinal()] = 5;
                } catch (NoSuchFieldError unused5) {
                }
                try {
                    iArr[AnnotationType.Clickable.ordinal()] = 6;
                } catch (NoSuchFieldError unused6) {
                }
                try {
                    iArr[AnnotationType.String.ordinal()] = 7;
                } catch (NoSuchFieldError unused7) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        @Override // kotlin.jvm.functions.Function1
        public final AnnotatedString.Range invoke(Object obj) {
            obj.getClass();
            List list = (List) obj;
            Object obj2 = list.get(0);
            ParagraphStyle paragraphStyle = null;
            clickable = null;
            LinkAnnotation.Clickable clickable = null;
            url = null;
            LinkAnnotation.Url url = null;
            urlAnnotation = null;
            UrlAnnotation urlAnnotation = null;
            verbatimTtsAnnotation = null;
            VerbatimTtsAnnotation verbatimTtsAnnotation = null;
            spanStyle = null;
            SpanStyle spanStyle = null;
            paragraphStyle = null;
            AnnotationType annotationType = obj2 != null ? (AnnotationType) obj2 : null;
            annotationType.getClass();
            Object obj3 = list.get(2);
            Integer num = obj3 != null ? (Integer) obj3 : null;
            num.getClass();
            int iIntValue = num.intValue();
            Object obj4 = list.get(3);
            Integer num2 = obj4 != null ? (Integer) obj4 : null;
            num2.getClass();
            int iIntValue2 = num2.intValue();
            Object obj5 = list.get(4);
            String str = obj5 != null ? (String) obj5 : null;
            str.getClass();
            switch (WhenMappings.$EnumSwitchMapping$0[annotationType.ordinal()]) {
                case 1:
                    Object obj6 = list.get(1);
                    Saver paragraphStyleSaver = SaversKt.getParagraphStyleSaver();
                    if ((!Intrinsics.areEqual(obj6, Boolean.FALSE) || (paragraphStyleSaver instanceof NonNullValueClassSaver)) && obj6 != null) {
                        paragraphStyle = (ParagraphStyle) paragraphStyleSaver.restore(obj6);
                    }
                    paragraphStyle.getClass();
                    return new AnnotatedString.Range(paragraphStyle, iIntValue, iIntValue2, str);
                case 2:
                    Object obj7 = list.get(1);
                    Saver spanStyleSaver = SaversKt.getSpanStyleSaver();
                    if ((!Intrinsics.areEqual(obj7, Boolean.FALSE) || (spanStyleSaver instanceof NonNullValueClassSaver)) && obj7 != null) {
                        spanStyle = (SpanStyle) spanStyleSaver.restore(obj7);
                    }
                    spanStyle.getClass();
                    return new AnnotatedString.Range(spanStyle, iIntValue, iIntValue2, str);
                case 3:
                    Object obj8 = list.get(1);
                    Saver saver = SaversKt.VerbatimTtsAnnotationSaver;
                    if ((!Intrinsics.areEqual(obj8, Boolean.FALSE) || (saver instanceof NonNullValueClassSaver)) && obj8 != null) {
                        verbatimTtsAnnotation = (VerbatimTtsAnnotation) saver.restore(obj8);
                    }
                    verbatimTtsAnnotation.getClass();
                    return new AnnotatedString.Range(verbatimTtsAnnotation, iIntValue, iIntValue2, str);
                case 4:
                    Object obj9 = list.get(1);
                    Saver saver2 = SaversKt.UrlAnnotationSaver;
                    if ((!Intrinsics.areEqual(obj9, Boolean.FALSE) || (saver2 instanceof NonNullValueClassSaver)) && obj9 != null) {
                        urlAnnotation = (UrlAnnotation) saver2.restore(obj9);
                    }
                    urlAnnotation.getClass();
                    return new AnnotatedString.Range(urlAnnotation, iIntValue, iIntValue2, str);
                case 5:
                    Object obj10 = list.get(1);
                    Saver saver3 = SaversKt.LinkSaver;
                    if ((!Intrinsics.areEqual(obj10, Boolean.FALSE) || (saver3 instanceof NonNullValueClassSaver)) && obj10 != null) {
                        url = (LinkAnnotation.Url) saver3.restore(obj10);
                    }
                    url.getClass();
                    return new AnnotatedString.Range(url, iIntValue, iIntValue2, str);
                case 6:
                    Object obj11 = list.get(1);
                    Saver saver4 = SaversKt.ClickableSaver;
                    if ((!Intrinsics.areEqual(obj11, Boolean.FALSE) || (saver4 instanceof NonNullValueClassSaver)) && obj11 != null) {
                        clickable = (LinkAnnotation.Clickable) saver4.restore(obj11);
                    }
                    clickable.getClass();
                    return new AnnotatedString.Range(clickable, iIntValue, iIntValue2, str);
                case 7:
                    Object obj12 = list.get(1);
                    String str2 = obj12 != null ? (String) obj12 : null;
                    str2.getClass();
                    return new AnnotatedString.Range(StringAnnotation.m801boximpl(StringAnnotation.m802constructorimpl(str2)), iIntValue, iIntValue2, str);
                default:
                    throw new NoWhenBranchMatchedException();
            }
        }
    });
    private static final Saver VerbatimTtsAnnotationSaver = SaverKt.Saver(new Function2() { // from class: androidx.compose.ui.text.SaversKt$VerbatimTtsAnnotationSaver$1
        public final Object invoke(SaverScope saverScope, VerbatimTtsAnnotation verbatimTtsAnnotation) {
            return SaversKt.save(verbatimTtsAnnotation.getVerbatim());
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
            return invoke((SaverScope) null, (VerbatimTtsAnnotation) obj2);
        }
    }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$VerbatimTtsAnnotationSaver$2
        @Override // kotlin.jvm.functions.Function1
        public final VerbatimTtsAnnotation invoke(Object obj) {
            String str = obj != null ? (String) obj : null;
            str.getClass();
            return new VerbatimTtsAnnotation(str);
        }
    });
    private static final Saver UrlAnnotationSaver = SaverKt.Saver(new Function2() { // from class: androidx.compose.ui.text.SaversKt$UrlAnnotationSaver$1
        public final Object invoke(SaverScope saverScope, UrlAnnotation urlAnnotation) {
            return SaversKt.save(urlAnnotation.getUrl());
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
            return invoke((SaverScope) null, (UrlAnnotation) obj2);
        }
    }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$UrlAnnotationSaver$2
        @Override // kotlin.jvm.functions.Function1
        public final UrlAnnotation invoke(Object obj) {
            String str = obj != null ? (String) obj : null;
            str.getClass();
            return new UrlAnnotation(str);
        }
    });
    private static final Saver LinkSaver = SaverKt.Saver(new Function2() { // from class: androidx.compose.ui.text.SaversKt$LinkSaver$1
        public final Object invoke(SaverScope saverScope, LinkAnnotation.Url url) {
            return CollectionsKt.arrayListOf(SaversKt.save(url.getUrl()), SaversKt.save(url.getStyles(), SaversKt.getTextLinkStylesSaver(), saverScope));
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
            return invoke((SaverScope) null, (LinkAnnotation.Url) obj2);
        }
    }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$LinkSaver$2
        @Override // kotlin.jvm.functions.Function1
        public final LinkAnnotation.Url invoke(Object obj) {
            obj.getClass();
            List list = (List) obj;
            Object obj2 = list.get(0);
            TextLinkStyles textLinkStyles = null;
            String str = obj2 != null ? (String) obj2 : null;
            str.getClass();
            Object obj3 = list.get(1);
            Saver textLinkStylesSaver = SaversKt.getTextLinkStylesSaver();
            if ((!Intrinsics.areEqual(obj3, Boolean.FALSE) || (textLinkStylesSaver instanceof NonNullValueClassSaver)) && obj3 != null) {
                textLinkStyles = (TextLinkStyles) textLinkStylesSaver.restore(obj3);
            }
            return new LinkAnnotation.Url(str, textLinkStyles, null, 4, null);
        }
    });
    private static final Saver ClickableSaver = SaverKt.Saver(new Function2() { // from class: androidx.compose.ui.text.SaversKt$ClickableSaver$1
        public final Object invoke(SaverScope saverScope, LinkAnnotation.Clickable clickable) {
            return CollectionsKt.arrayListOf(SaversKt.save(clickable.getTag()), SaversKt.save(clickable.getStyles(), SaversKt.getTextLinkStylesSaver(), saverScope));
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
            return invoke((SaverScope) null, (LinkAnnotation.Clickable) obj2);
        }
    }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$ClickableSaver$2
        @Override // kotlin.jvm.functions.Function1
        public final LinkAnnotation.Clickable invoke(Object obj) {
            obj.getClass();
            List list = (List) obj;
            Object obj2 = list.get(0);
            String str = obj2 != null ? (String) obj2 : null;
            str.getClass();
            Object obj3 = list.get(1);
            Saver textLinkStylesSaver = SaversKt.getTextLinkStylesSaver();
            return new LinkAnnotation.Clickable(str, ((!Intrinsics.areEqual(obj3, Boolean.FALSE) || (textLinkStylesSaver instanceof NonNullValueClassSaver)) && obj3 != null) ? (TextLinkStyles) textLinkStylesSaver.restore(obj3) : null, null);
        }
    });
    private static final Saver ParagraphStyleSaver = SaverKt.Saver(new Function2() { // from class: androidx.compose.ui.text.SaversKt$ParagraphStyleSaver$1
        public final Object invoke(SaverScope saverScope, ParagraphStyle paragraphStyle) {
            return CollectionsKt.arrayListOf(SaversKt.save(TextAlign.m946boximpl(paragraphStyle.m777getTextAligne0LSkKk())), SaversKt.save(TextDirection.m953boximpl(paragraphStyle.m778getTextDirections_7Xco())), SaversKt.save(TextUnit.m1017boximpl(paragraphStyle.m776getLineHeightXSAIIZE()), SaversKt.getSaver(TextUnit.Companion), saverScope), SaversKt.save(paragraphStyle.getTextIndent(), SaversKt.getSaver(TextIndent.Companion), saverScope), SaversKt.save(paragraphStyle.getPlatformStyle(), Savers_androidKt.getSaver(PlatformParagraphStyle.Companion), saverScope), SaversKt.save(paragraphStyle.getLineHeightStyle(), SaversKt.getSaver(LineHeightStyle.Companion), saverScope), SaversKt.save(LineBreak.m892boximpl(paragraphStyle.m775getLineBreakrAG3T2k()), Savers_androidKt.getSaver(LineBreak.Companion), saverScope), SaversKt.save(Hyphens.m885boximpl(paragraphStyle.m774getHyphensvmbZdU8())), SaversKt.save(paragraphStyle.getTextMotion(), Savers_androidKt.getSaver(TextMotion.Companion), saverScope));
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
            return invoke((SaverScope) null, (ParagraphStyle) obj2);
        }
    }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$ParagraphStyleSaver$2
        @Override // kotlin.jvm.functions.Function1
        public final ParagraphStyle invoke(Object obj) {
            obj.getClass();
            List list = (List) obj;
            Object obj2 = list.get(0);
            TextMotion textMotion = null;
            TextAlign textAlign = obj2 != null ? (TextAlign) obj2 : null;
            textAlign.getClass();
            int iM952unboximpl = textAlign.m952unboximpl();
            Object obj3 = list.get(1);
            TextDirection textDirection = obj3 != null ? (TextDirection) obj3 : null;
            textDirection.getClass();
            int iM959unboximpl = textDirection.m959unboximpl();
            Object obj4 = list.get(2);
            Saver saver = SaversKt.getSaver(TextUnit.Companion);
            Boolean bool = Boolean.FALSE;
            TextUnit textUnit = ((!Intrinsics.areEqual(obj4, bool) || (saver instanceof NonNullValueClassSaver)) && obj4 != null) ? (TextUnit) saver.restore(obj4) : null;
            textUnit.getClass();
            long jM1026unboximpl = textUnit.m1026unboximpl();
            Object obj5 = list.get(3);
            Saver saver2 = SaversKt.getSaver(TextIndent.Companion);
            TextIndent textIndent = ((!Intrinsics.areEqual(obj5, bool) || (saver2 instanceof NonNullValueClassSaver)) && obj5 != null) ? (TextIndent) saver2.restore(obj5) : null;
            Object obj6 = list.get(4);
            Saver saver3 = Savers_androidKt.getSaver(PlatformParagraphStyle.Companion);
            PlatformParagraphStyle platformParagraphStyle = ((!Intrinsics.areEqual(obj6, bool) || (saver3 instanceof NonNullValueClassSaver)) && obj6 != null) ? (PlatformParagraphStyle) saver3.restore(obj6) : null;
            Object obj7 = list.get(5);
            Saver saver4 = SaversKt.getSaver(LineHeightStyle.Companion);
            LineHeightStyle lineHeightStyle = ((!Intrinsics.areEqual(obj7, bool) || (saver4 instanceof NonNullValueClassSaver)) && obj7 != null) ? (LineHeightStyle) saver4.restore(obj7) : null;
            Object obj8 = list.get(6);
            Saver saver5 = Savers_androidKt.getSaver(LineBreak.Companion);
            LineBreak lineBreak = ((!Intrinsics.areEqual(obj8, bool) || (saver5 instanceof NonNullValueClassSaver)) && obj8 != null) ? (LineBreak) saver5.restore(obj8) : null;
            lineBreak.getClass();
            int iM901unboximpl = lineBreak.m901unboximpl();
            Object obj9 = list.get(7);
            Hyphens hyphens = obj9 != null ? (Hyphens) obj9 : null;
            hyphens.getClass();
            int iM891unboximpl = hyphens.m891unboximpl();
            Object obj10 = list.get(8);
            Saver saver6 = Savers_androidKt.getSaver(TextMotion.Companion);
            if ((!Intrinsics.areEqual(obj10, bool) || (saver6 instanceof NonNullValueClassSaver)) && obj10 != null) {
                textMotion = (TextMotion) saver6.restore(obj10);
            }
            return new ParagraphStyle(iM952unboximpl, iM959unboximpl, jM1026unboximpl, textIndent, platformParagraphStyle, lineHeightStyle, iM901unboximpl, iM891unboximpl, textMotion, null);
        }
    });
    private static final Saver SpanStyleSaver = SaverKt.Saver(new Function2() { // from class: androidx.compose.ui.text.SaversKt$SpanStyleSaver$1
        public final Object invoke(SaverScope saverScope, SpanStyle spanStyle) {
            Color colorM272boximpl = Color.m272boximpl(spanStyle.m796getColor0d7_KjU());
            Color.Companion companion = Color.Companion;
            Object objSave = SaversKt.save(colorM272boximpl, SaversKt.getSaver(companion), saverScope);
            TextUnit textUnitM1017boximpl = TextUnit.m1017boximpl(spanStyle.m797getFontSizeXSAIIZE());
            TextUnit.Companion companion2 = TextUnit.Companion;
            Object objSave2 = SaversKt.save(textUnitM1017boximpl, SaversKt.getSaver(companion2), saverScope);
            Object objSave3 = SaversKt.save(spanStyle.getFontWeight(), SaversKt.getSaver(FontWeight.Companion), saverScope);
            spanStyle.m798getFontStyle4Lr2A7w();
            Object objSave4 = SaversKt.save(null);
            spanStyle.m799getFontSynthesisZQGJjVo();
            return CollectionsKt.arrayListOf(objSave, objSave2, objSave3, objSave4, SaversKt.save(null), SaversKt.save(-1), SaversKt.save(spanStyle.getFontFeatureSettings()), SaversKt.save(TextUnit.m1017boximpl(spanStyle.m800getLetterSpacingXSAIIZE()), SaversKt.getSaver(companion2), saverScope), SaversKt.save(spanStyle.m795getBaselineShift5SSeXJ0(), SaversKt.getSaver(BaselineShift.Companion), saverScope), SaversKt.save(spanStyle.getTextGeometricTransform(), SaversKt.getSaver(TextGeometricTransform.Companion), saverScope), SaversKt.save(spanStyle.getLocaleList(), SaversKt.getSaver(LocaleList.Companion), saverScope), SaversKt.save(Color.m272boximpl(spanStyle.m794getBackground0d7_KjU()), SaversKt.getSaver(companion), saverScope), SaversKt.save(spanStyle.getTextDecoration(), SaversKt.getSaver(TextDecoration.Companion), saverScope), SaversKt.save(spanStyle.getShadow(), SaversKt.getSaver(Shadow.Companion), saverScope));
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
            return invoke((SaverScope) null, (SpanStyle) obj2);
        }
    }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$SpanStyleSaver$2
        @Override // kotlin.jvm.functions.Function1
        public final SpanStyle invoke(Object obj) {
            obj.getClass();
            List list = (List) obj;
            Object obj2 = list.get(0);
            Color.Companion companion = Color.Companion;
            Saver saver = SaversKt.getSaver(companion);
            Boolean bool = Boolean.FALSE;
            Color color = ((!Intrinsics.areEqual(obj2, bool) || (saver instanceof NonNullValueClassSaver)) && obj2 != null) ? (Color) saver.restore(obj2) : null;
            color.getClass();
            long jM286unboximpl = color.m286unboximpl();
            Object obj3 = list.get(1);
            TextUnit.Companion companion2 = TextUnit.Companion;
            Saver saver2 = SaversKt.getSaver(companion2);
            TextUnit textUnit = ((!Intrinsics.areEqual(obj3, bool) || (saver2 instanceof NonNullValueClassSaver)) && obj3 != null) ? (TextUnit) saver2.restore(obj3) : null;
            textUnit.getClass();
            long jM1026unboximpl = textUnit.m1026unboximpl();
            Object obj4 = list.get(2);
            Saver saver3 = SaversKt.getSaver(FontWeight.Companion);
            FontWeight fontWeight = ((!Intrinsics.areEqual(obj4, bool) || (saver3 instanceof NonNullValueClassSaver)) && obj4 != null) ? (FontWeight) saver3.restore(obj4) : null;
            Object obj5 = list.get(3);
            if (obj5 != null) {
                ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj5);
            }
            Object obj6 = list.get(4);
            if (obj6 != null) {
                ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj6);
            }
            Object obj7 = list.get(6);
            String str = obj7 != null ? (String) obj7 : null;
            Object obj8 = list.get(7);
            Saver saver4 = SaversKt.getSaver(companion2);
            TextUnit textUnit2 = ((!Intrinsics.areEqual(obj8, bool) || (saver4 instanceof NonNullValueClassSaver)) && obj8 != null) ? (TextUnit) saver4.restore(obj8) : null;
            textUnit2.getClass();
            long jM1026unboximpl2 = textUnit2.m1026unboximpl();
            Object obj9 = list.get(8);
            Saver saver5 = SaversKt.getSaver(BaselineShift.Companion);
            BaselineShift baselineShift = ((!Intrinsics.areEqual(obj9, bool) || (saver5 instanceof NonNullValueClassSaver)) && obj9 != null) ? (BaselineShift) saver5.restore(obj9) : null;
            Object obj10 = list.get(9);
            Saver saver6 = SaversKt.getSaver(TextGeometricTransform.Companion);
            TextGeometricTransform textGeometricTransform = ((!Intrinsics.areEqual(obj10, bool) || (saver6 instanceof NonNullValueClassSaver)) && obj10 != null) ? (TextGeometricTransform) saver6.restore(obj10) : null;
            Object obj11 = list.get(10);
            Saver saver7 = SaversKt.getSaver(LocaleList.Companion);
            LocaleList localeList = ((!Intrinsics.areEqual(obj11, bool) || (saver7 instanceof NonNullValueClassSaver)) && obj11 != null) ? (LocaleList) saver7.restore(obj11) : null;
            Object obj12 = list.get(11);
            Saver saver8 = SaversKt.getSaver(companion);
            Color color2 = ((!Intrinsics.areEqual(obj12, bool) || (saver8 instanceof NonNullValueClassSaver)) && obj12 != null) ? (Color) saver8.restore(obj12) : null;
            color2.getClass();
            long jM286unboximpl2 = color2.m286unboximpl();
            Object obj13 = list.get(12);
            Saver saver9 = SaversKt.getSaver(TextDecoration.Companion);
            TextDecoration textDecoration = ((!Intrinsics.areEqual(obj13, bool) || (saver9 instanceof NonNullValueClassSaver)) && obj13 != null) ? (TextDecoration) saver9.restore(obj13) : null;
            Object obj14 = list.get(13);
            Saver saver10 = SaversKt.getSaver(Shadow.Companion);
            return new SpanStyle(jM286unboximpl, jM1026unboximpl, fontWeight, null, null, null, str, jM1026unboximpl2, baselineShift, textGeometricTransform, localeList, jM286unboximpl2, textDecoration, ((!Intrinsics.areEqual(obj14, bool) || (saver10 instanceof NonNullValueClassSaver)) && obj14 != null) ? (Shadow) saver10.restore(obj14) : null, null, null, 49184, null);
        }
    });
    private static final Saver TextLinkStylesSaver = SaverKt.Saver(new Function2() { // from class: androidx.compose.ui.text.SaversKt$TextLinkStylesSaver$1
        public final Object invoke(SaverScope saverScope, TextLinkStyles textLinkStyles) {
            return CollectionsKt.arrayListOf(SaversKt.save(textLinkStyles.getStyle(), SaversKt.getSpanStyleSaver(), saverScope), SaversKt.save(textLinkStyles.getFocusedStyle(), SaversKt.getSpanStyleSaver(), saverScope), SaversKt.save(textLinkStyles.getHoveredStyle(), SaversKt.getSpanStyleSaver(), saverScope), SaversKt.save(textLinkStyles.getPressedStyle(), SaversKt.getSpanStyleSaver(), saverScope));
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
            return invoke((SaverScope) null, (TextLinkStyles) obj2);
        }
    }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$TextLinkStylesSaver$2
        @Override // kotlin.jvm.functions.Function1
        public final TextLinkStyles invoke(Object obj) {
            obj.getClass();
            List list = (List) obj;
            Object obj2 = list.get(0);
            Saver spanStyleSaver = SaversKt.getSpanStyleSaver();
            Boolean bool = Boolean.FALSE;
            SpanStyle spanStyle = null;
            SpanStyle spanStyle2 = ((!Intrinsics.areEqual(obj2, bool) || (spanStyleSaver instanceof NonNullValueClassSaver)) && obj2 != null) ? (SpanStyle) spanStyleSaver.restore(obj2) : null;
            Object obj3 = list.get(1);
            Saver spanStyleSaver2 = SaversKt.getSpanStyleSaver();
            SpanStyle spanStyle3 = ((!Intrinsics.areEqual(obj3, bool) || (spanStyleSaver2 instanceof NonNullValueClassSaver)) && obj3 != null) ? (SpanStyle) spanStyleSaver2.restore(obj3) : null;
            Object obj4 = list.get(2);
            Saver spanStyleSaver3 = SaversKt.getSpanStyleSaver();
            SpanStyle spanStyle4 = ((!Intrinsics.areEqual(obj4, bool) || (spanStyleSaver3 instanceof NonNullValueClassSaver)) && obj4 != null) ? (SpanStyle) spanStyleSaver3.restore(obj4) : null;
            Object obj5 = list.get(3);
            Saver spanStyleSaver4 = SaversKt.getSpanStyleSaver();
            if ((!Intrinsics.areEqual(obj5, bool) || (spanStyleSaver4 instanceof NonNullValueClassSaver)) && obj5 != null) {
                spanStyle = (SpanStyle) spanStyleSaver4.restore(obj5);
            }
            return new TextLinkStyles(spanStyle2, spanStyle3, spanStyle4, spanStyle);
        }
    });
    private static final Saver TextDecorationSaver = SaverKt.Saver(new Function2() { // from class: androidx.compose.ui.text.SaversKt$TextDecorationSaver$1
        public final Object invoke(SaverScope saverScope, TextDecoration textDecoration) {
            return Integer.valueOf(textDecoration.getMask());
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
            return invoke((SaverScope) null, (TextDecoration) obj2);
        }
    }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$TextDecorationSaver$2
        @Override // kotlin.jvm.functions.Function1
        public final TextDecoration invoke(Object obj) {
            obj.getClass();
            return new TextDecoration(((Integer) obj).intValue());
        }
    });
    private static final Saver TextGeometricTransformSaver = SaverKt.Saver(new Function2() { // from class: androidx.compose.ui.text.SaversKt$TextGeometricTransformSaver$1
        public final Object invoke(SaverScope saverScope, TextGeometricTransform textGeometricTransform) {
            return CollectionsKt.arrayListOf(Float.valueOf(textGeometricTransform.getScaleX()), Float.valueOf(textGeometricTransform.getSkewX()));
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
            return invoke((SaverScope) null, (TextGeometricTransform) obj2);
        }
    }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$TextGeometricTransformSaver$2
        @Override // kotlin.jvm.functions.Function1
        public final TextGeometricTransform invoke(Object obj) {
            obj.getClass();
            List list = (List) obj;
            return new TextGeometricTransform(((Number) list.get(0)).floatValue(), ((Number) list.get(1)).floatValue());
        }
    });
    private static final Saver TextIndentSaver = SaverKt.Saver(new Function2() { // from class: androidx.compose.ui.text.SaversKt$TextIndentSaver$1
        public final Object invoke(SaverScope saverScope, TextIndent textIndent) {
            TextUnit textUnitM1017boximpl = TextUnit.m1017boximpl(textIndent.m961getFirstLineXSAIIZE());
            TextUnit.Companion companion = TextUnit.Companion;
            return CollectionsKt.arrayListOf(SaversKt.save(textUnitM1017boximpl, SaversKt.getSaver(companion), saverScope), SaversKt.save(TextUnit.m1017boximpl(textIndent.m962getRestLineXSAIIZE()), SaversKt.getSaver(companion), saverScope));
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
            return invoke((SaverScope) null, (TextIndent) obj2);
        }
    }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$TextIndentSaver$2
        @Override // kotlin.jvm.functions.Function1
        public final TextIndent invoke(Object obj) {
            obj.getClass();
            List list = (List) obj;
            Object obj2 = list.get(0);
            TextUnit.Companion companion = TextUnit.Companion;
            Saver saver = SaversKt.getSaver(companion);
            Boolean bool = Boolean.FALSE;
            TextUnit textUnit = null;
            TextUnit textUnit2 = ((!Intrinsics.areEqual(obj2, bool) || (saver instanceof NonNullValueClassSaver)) && obj2 != null) ? (TextUnit) saver.restore(obj2) : null;
            textUnit2.getClass();
            long jM1026unboximpl = textUnit2.m1026unboximpl();
            Object obj3 = list.get(1);
            Saver saver2 = SaversKt.getSaver(companion);
            if ((!Intrinsics.areEqual(obj3, bool) || (saver2 instanceof NonNullValueClassSaver)) && obj3 != null) {
                textUnit = (TextUnit) saver2.restore(obj3);
            }
            textUnit.getClass();
            return new TextIndent(jM1026unboximpl, textUnit.m1026unboximpl(), null);
        }
    });
    private static final Saver FontWeightSaver = SaverKt.Saver(new Function2() { // from class: androidx.compose.ui.text.SaversKt$FontWeightSaver$1
        public final Object invoke(SaverScope saverScope, FontWeight fontWeight) {
            return Integer.valueOf(fontWeight.getWeight());
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
            return invoke((SaverScope) null, (FontWeight) obj2);
        }
    }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$FontWeightSaver$2
        @Override // kotlin.jvm.functions.Function1
        public final FontWeight invoke(Object obj) {
            obj.getClass();
            return new FontWeight(((Integer) obj).intValue());
        }
    });
    private static final Saver BaselineShiftSaver = SaverKt.Saver(new Function2() { // from class: androidx.compose.ui.text.SaversKt$BaselineShiftSaver$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
            return m780invoke8a2Sb4w(null, ((BaselineShift) obj2).m883unboximpl());
        }

        /* JADX INFO: renamed from: invoke-8a2Sb4w, reason: not valid java name */
        public final Object m780invoke8a2Sb4w(SaverScope saverScope, float f) {
            return Float.valueOf(f);
        }
    }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$BaselineShiftSaver$2
        @Override // kotlin.jvm.functions.Function1
        /* JADX INFO: renamed from: invoke-jTk7eUs, reason: not valid java name and merged with bridge method [inline-methods] */
        public final BaselineShift invoke(Object obj) {
            obj.getClass();
            return BaselineShift.m878boximpl(BaselineShift.m879constructorimpl(((Float) obj).floatValue()));
        }
    });
    private static final Saver TextRangeSaver = SaverKt.Saver(new Function2() { // from class: androidx.compose.ui.text.SaversKt$TextRangeSaver$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
            return m786invokeFDrldGo(null, ((TextRange) obj2).m818unboximpl());
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX INFO: renamed from: invoke-FDrldGo, reason: not valid java name */
        public final Object m786invokeFDrldGo(SaverScope saverScope, long j) {
            return CollectionsKt.arrayListOf(SaversKt.save(Integer.valueOf(TextRange.m815getStartimpl(j))), SaversKt.save(Integer.valueOf(TextRange.m812getEndimpl(j))));
        }
    }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$TextRangeSaver$2
        @Override // kotlin.jvm.functions.Function1
        /* JADX INFO: renamed from: invoke-VqIyPBM, reason: not valid java name and merged with bridge method [inline-methods] */
        public final TextRange invoke(Object obj) {
            obj.getClass();
            List list = (List) obj;
            Object obj2 = list.get(0);
            Integer num = obj2 != null ? (Integer) obj2 : null;
            num.getClass();
            int iIntValue = num.intValue();
            Object obj3 = list.get(1);
            Integer num2 = obj3 != null ? (Integer) obj3 : null;
            num2.getClass();
            return TextRange.m807boximpl(TextRangeKt.TextRange(iIntValue, num2.intValue()));
        }
    });
    private static final Saver ShadowSaver = SaverKt.Saver(new Function2() { // from class: androidx.compose.ui.text.SaversKt$ShadowSaver$1
        public final Object invoke(SaverScope saverScope, Shadow shadow) {
            return CollectionsKt.arrayListOf(SaversKt.save(Color.m272boximpl(shadow.m340getColor0d7_KjU()), SaversKt.getSaver(Color.Companion), saverScope), SaversKt.save(Offset.m181boximpl(shadow.m341getOffsetF1C5BW0()), SaversKt.getSaver(Offset.Companion), saverScope), SaversKt.save(Float.valueOf(shadow.getBlurRadius())));
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
            return invoke((SaverScope) null, (Shadow) obj2);
        }
    }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$ShadowSaver$2
        @Override // kotlin.jvm.functions.Function1
        public final Shadow invoke(Object obj) {
            obj.getClass();
            List list = (List) obj;
            Object obj2 = list.get(0);
            Saver saver = SaversKt.getSaver(Color.Companion);
            Boolean bool = Boolean.FALSE;
            Color color = ((!Intrinsics.areEqual(obj2, bool) || (saver instanceof NonNullValueClassSaver)) && obj2 != null) ? (Color) saver.restore(obj2) : null;
            color.getClass();
            long jM286unboximpl = color.m286unboximpl();
            Object obj3 = list.get(1);
            Saver saver2 = SaversKt.getSaver(Offset.Companion);
            Offset offset = ((!Intrinsics.areEqual(obj3, bool) || (saver2 instanceof NonNullValueClassSaver)) && obj3 != null) ? (Offset) saver2.restore(obj3) : null;
            offset.getClass();
            long jM192unboximpl = offset.m192unboximpl();
            Object obj4 = list.get(2);
            Float f = obj4 != null ? (Float) obj4 : null;
            f.getClass();
            return new Shadow(jM286unboximpl, jM192unboximpl, f.floatValue(), null);
        }
    });
    private static final NonNullValueClassSaver ColorSaver = NonNullValueClassSaver(new Function2() { // from class: androidx.compose.ui.text.SaversKt$ColorSaver$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
            return m782invoke4WTKRHQ(null, ((Color) obj2).m286unboximpl());
        }

        /* JADX INFO: renamed from: invoke-4WTKRHQ, reason: not valid java name */
        public final Object m782invoke4WTKRHQ(SaverScope saverScope, long j) {
            return j == 16 ? Boolean.FALSE : Integer.valueOf(ColorKt.m292toArgb8_81llA(j));
        }
    }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$ColorSaver$2
        @Override // kotlin.jvm.functions.Function1
        /* JADX INFO: renamed from: invoke-ijrfgN4, reason: not valid java name and merged with bridge method [inline-methods] */
        public final Color invoke(Object obj) {
            if (Intrinsics.areEqual(obj, Boolean.FALSE)) {
                return Color.m272boximpl(Color.Companion.m291getUnspecified0d7_KjU());
            }
            obj.getClass();
            return Color.m272boximpl(ColorKt.Color(((Integer) obj).intValue()));
        }
    });
    private static final NonNullValueClassSaver TextUnitSaver = NonNullValueClassSaver(new Function2() { // from class: androidx.compose.ui.text.SaversKt$TextUnitSaver$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
            return m788invokempE4wyQ(null, ((TextUnit) obj2).m1026unboximpl());
        }

        /* JADX INFO: renamed from: invoke-mpE4wyQ, reason: not valid java name */
        public final Object m788invokempE4wyQ(SaverScope saverScope, long j) {
            return TextUnit.m1020equalsimpl0(j, TextUnit.Companion.m1027getUnspecifiedXSAIIZE()) ? Boolean.FALSE : CollectionsKt.arrayListOf(SaversKt.save(Float.valueOf(TextUnit.m1023getValueimpl(j))), SaversKt.save(TextUnitType.m1029boximpl(TextUnit.m1022getTypeUIouoOA(j))));
        }
    }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$TextUnitSaver$2
        @Override // kotlin.jvm.functions.Function1
        /* JADX INFO: renamed from: invoke-XNhUCwk, reason: not valid java name and merged with bridge method [inline-methods] */
        public final TextUnit invoke(Object obj) {
            if (Intrinsics.areEqual(obj, Boolean.FALSE)) {
                return TextUnit.m1017boximpl(TextUnit.Companion.m1027getUnspecifiedXSAIIZE());
            }
            obj.getClass();
            List list = (List) obj;
            Object obj2 = list.get(0);
            Float f = obj2 != null ? (Float) obj2 : null;
            f.getClass();
            float fFloatValue = f.floatValue();
            Object obj3 = list.get(1);
            TextUnitType textUnitType = obj3 != null ? (TextUnitType) obj3 : null;
            textUnitType.getClass();
            return TextUnit.m1017boximpl(TextUnitKt.m1028TextUnitanM5pPY(fFloatValue, textUnitType.m1035unboximpl()));
        }
    });
    private static final NonNullValueClassSaver OffsetSaver = NonNullValueClassSaver(new Function2() { // from class: androidx.compose.ui.text.SaversKt$OffsetSaver$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
            return m784invokeUv8p0NA(null, ((Offset) obj2).m192unboximpl());
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX INFO: renamed from: invoke-Uv8p0NA, reason: not valid java name */
        public final Object m784invokeUv8p0NA(SaverScope saverScope, long j) {
            return Offset.m186equalsimpl0(j, Offset.Companion.m194getUnspecifiedF1C5BW0()) ? Boolean.FALSE : CollectionsKt.arrayListOf(SaversKt.save(Float.valueOf(Float.intBitsToFloat((int) (j >> 32)))), SaversKt.save(Float.valueOf(Float.intBitsToFloat((int) (j & 4294967295L)))));
        }
    }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$OffsetSaver$2
        @Override // kotlin.jvm.functions.Function1
        /* JADX INFO: renamed from: invoke-x-9fifI, reason: not valid java name and merged with bridge method [inline-methods] */
        public final Offset invoke(Object obj) {
            if (Intrinsics.areEqual(obj, Boolean.FALSE)) {
                return Offset.m181boximpl(Offset.Companion.m194getUnspecifiedF1C5BW0());
            }
            obj.getClass();
            List list = (List) obj;
            Object obj2 = list.get(0);
            Float f = obj2 != null ? (Float) obj2 : null;
            f.getClass();
            float fFloatValue = f.floatValue();
            Object obj3 = list.get(1);
            Float f2 = obj3 != null ? (Float) obj3 : null;
            f2.getClass();
            return Offset.m181boximpl(Offset.m182constructorimpl((((long) Float.floatToRawIntBits(f2.floatValue())) & 4294967295L) | (((long) Float.floatToRawIntBits(fFloatValue)) << 32)));
        }
    });
    private static final Saver LocaleListSaver = SaverKt.Saver(new Function2() { // from class: androidx.compose.ui.text.SaversKt$LocaleListSaver$1
        public final Object invoke(SaverScope saverScope, LocaleList localeList) {
            List localeList2 = localeList.getLocaleList();
            ArrayList arrayList = new ArrayList(localeList2.size());
            int size = localeList2.size();
            for (int i = 0; i < size; i++) {
                arrayList.add(SaversKt.save((Locale) localeList2.get(i), SaversKt.getSaver(Locale.Companion), saverScope));
            }
            return arrayList;
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
            return invoke((SaverScope) null, (LocaleList) obj2);
        }
    }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$LocaleListSaver$2
        @Override // kotlin.jvm.functions.Function1
        public final LocaleList invoke(Object obj) {
            obj.getClass();
            List list = (List) obj;
            ArrayList arrayList = new ArrayList(list.size());
            int size = list.size();
            for (int i = 0; i < size; i++) {
                Object obj2 = list.get(i);
                Saver saver = SaversKt.getSaver(Locale.Companion);
                Locale locale = null;
                if ((!Intrinsics.areEqual(obj2, Boolean.FALSE) || (saver instanceof NonNullValueClassSaver)) && obj2 != null) {
                    locale = (Locale) saver.restore(obj2);
                }
                locale.getClass();
                arrayList.add(locale);
            }
            return new LocaleList(arrayList);
        }
    });
    private static final Saver LocaleSaver = SaverKt.Saver(new Function2() { // from class: androidx.compose.ui.text.SaversKt$LocaleSaver$1
        public final Object invoke(SaverScope saverScope, Locale locale) {
            return locale.toLanguageTag();
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
            return invoke((SaverScope) null, (Locale) obj2);
        }
    }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$LocaleSaver$2
        @Override // kotlin.jvm.functions.Function1
        public final Locale invoke(Object obj) {
            obj.getClass();
            return new Locale((String) obj);
        }
    });
    private static final Saver LineHeightStyleSaver = SaverKt.Saver(new Function2() { // from class: androidx.compose.ui.text.SaversKt$LineHeightStyleSaver$1
        public final Object invoke(SaverScope saverScope, LineHeightStyle lineHeightStyle) {
            return CollectionsKt.arrayListOf(SaversKt.save(LineHeightStyle.Alignment.m922boximpl(lineHeightStyle.m919getAlignmentPIaL0Z0())), SaversKt.save(LineHeightStyle.Trim.m938boximpl(lineHeightStyle.m921getTrimEVpEnUU())), SaversKt.save(LineHeightStyle.Mode.m930boximpl(lineHeightStyle.m920getModelzQqcRY())));
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
            return invoke((SaverScope) null, (LineHeightStyle) obj2);
        }
    }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$LineHeightStyleSaver$2
        @Override // kotlin.jvm.functions.Function1
        public final LineHeightStyle invoke(Object obj) {
            obj.getClass();
            List list = (List) obj;
            Object obj2 = list.get(0);
            LineHeightStyle.Alignment alignment = obj2 != null ? (LineHeightStyle.Alignment) obj2 : null;
            alignment.getClass();
            float fM928unboximpl = alignment.m928unboximpl();
            Object obj3 = list.get(1);
            LineHeightStyle.Trim trim = obj3 != null ? (LineHeightStyle.Trim) obj3 : null;
            trim.getClass();
            int iM944unboximpl = trim.m944unboximpl();
            Object obj4 = list.get(2);
            LineHeightStyle.Mode mode = obj4 != null ? (LineHeightStyle.Mode) obj4 : null;
            mode.getClass();
            return new LineHeightStyle(fM928unboximpl, iM944unboximpl, mode.m936unboximpl(), null);
        }
    });

    private static final NonNullValueClassSaver NonNullValueClassSaver(final Function2 function2, final Function1 function1) {
        return new NonNullValueClassSaver() { // from class: androidx.compose.ui.text.SaversKt.NonNullValueClassSaver.1
            @Override // androidx.compose.runtime.saveable.Saver
            public Object restore(Object obj) {
                return function1.invoke(obj);
            }

            @Override // androidx.compose.runtime.saveable.Saver
            public Object save(SaverScope saverScope, Object obj) {
                return function2.invoke(saverScope, obj);
            }
        };
    }

    public static final Saver getAnnotatedStringSaver() {
        return AnnotatedStringSaver;
    }

    public static final Saver getParagraphStyleSaver() {
        return ParagraphStyleSaver;
    }

    public static final Saver getSaver(Offset.Companion companion) {
        return OffsetSaver;
    }

    public static final Saver getSaver(Color.Companion companion) {
        return ColorSaver;
    }

    public static final Saver getSaver(Shadow.Companion companion) {
        return ShadowSaver;
    }

    public static final Saver getSaver(TextRange.Companion companion) {
        return TextRangeSaver;
    }

    public static final Saver getSaver(FontWeight.Companion companion) {
        return FontWeightSaver;
    }

    public static final Saver getSaver(Locale.Companion companion) {
        return LocaleSaver;
    }

    public static final Saver getSaver(LocaleList.Companion companion) {
        return LocaleListSaver;
    }

    public static final Saver getSaver(BaselineShift.Companion companion) {
        return BaselineShiftSaver;
    }

    public static final Saver getSaver(LineHeightStyle.Companion companion) {
        return LineHeightStyleSaver;
    }

    public static final Saver getSaver(TextDecoration.Companion companion) {
        return TextDecorationSaver;
    }

    public static final Saver getSaver(TextGeometricTransform.Companion companion) {
        return TextGeometricTransformSaver;
    }

    public static final Saver getSaver(TextIndent.Companion companion) {
        return TextIndentSaver;
    }

    public static final Saver getSaver(TextUnit.Companion companion) {
        return TextUnitSaver;
    }

    public static final Saver getSpanStyleSaver() {
        return SpanStyleSaver;
    }

    public static final Saver getTextLinkStylesSaver() {
        return TextLinkStylesSaver;
    }

    public static final Object save(Object obj) {
        return obj;
    }

    public static final Object save(Object obj, Saver saver, SaverScope saverScope) {
        Object objSave;
        return (obj == null || (objSave = saver.save(saverScope, obj)) == null) ? Boolean.FALSE : objSave;
    }
}
