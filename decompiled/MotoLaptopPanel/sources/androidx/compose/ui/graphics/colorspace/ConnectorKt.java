package androidx.compose.ui.graphics.colorspace;

import androidx.collection.IntObjectMapKt;
import androidx.collection.MutableIntObjectMap;
import androidx.compose.ui.graphics.colorspace.RenderIntent;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: Connector.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ConnectorKt {
    private static final MutableIntObjectMap Connectors;

    static {
        ColorSpaces colorSpaces = ColorSpaces.INSTANCE;
        int id$ui_graphics_release = colorSpaces.getSrgb().getId$ui_graphics_release();
        int id$ui_graphics_release2 = colorSpaces.getSrgb().getId$ui_graphics_release();
        RenderIntent.Companion companion = RenderIntent.Companion;
        DefaultConstructorMarker defaultConstructorMarker = null;
        Connectors = IntObjectMapKt.mutableIntObjectMapOf(id$ui_graphics_release | (id$ui_graphics_release2 << 6) | (companion.m1040getPerceptualuksYyKA() << 12), Connector.Companion.identity$ui_graphics_release(colorSpaces.getSrgb()), colorSpaces.getSrgb().getId$ui_graphics_release() | (colorSpaces.getOklab().getId$ui_graphics_release() << 6) | (companion.m1040getPerceptualuksYyKA() << 12), new Connector(colorSpaces.getSrgb(), colorSpaces.getOklab(), companion.m1040getPerceptualuksYyKA(), defaultConstructorMarker), colorSpaces.getOklab().getId$ui_graphics_release() | (colorSpaces.getSrgb().getId$ui_graphics_release() << 6) | (companion.m1040getPerceptualuksYyKA() << 12), new Connector(colorSpaces.getOklab(), colorSpaces.getSrgb(), companion.m1040getPerceptualuksYyKA(), defaultConstructorMarker));
    }

    public static final MutableIntObjectMap getConnectors() {
        return Connectors;
    }
}
