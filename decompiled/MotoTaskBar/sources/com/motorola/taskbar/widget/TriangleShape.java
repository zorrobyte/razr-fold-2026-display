package com.motorola.taskbar.widget;

import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.drawable.shapes.PathShape;

/* JADX INFO: loaded from: classes2.dex */
public class TriangleShape extends PathShape {
    private Path mTriangularPath;

    public TriangleShape(Path path, float f, float f2) {
        super(path, f, f2);
        this.mTriangularPath = path;
    }

    public static TriangleShape create(float f, float f2, int i) {
        Path path = new Path();
        if (i == 0) {
            path.moveTo(0.0f, f2);
            path.lineTo(f, f2);
            path.lineTo(f / 2.0f, 0.0f);
            path.close();
        } else if (i == 1) {
            path.moveTo(0.0f, f2 / 2.0f);
            path.lineTo(f, 0.0f);
            path.lineTo(f, f2);
            path.close();
        } else if (i == 2) {
            path.moveTo(0.0f, 0.0f);
            path.lineTo(f, f2 / 2.0f);
            path.lineTo(0.0f, f2);
            path.close();
        } else if (i == 3) {
            path.moveTo(0.0f, 0.0f);
            path.lineTo(f / 2.0f, f2);
            path.lineTo(f, 0.0f);
            path.close();
        }
        return new TriangleShape(path, f, f2);
    }

    @Override // android.graphics.drawable.shapes.Shape
    public void getOutline(Outline outline) {
        outline.setConvexPath(this.mTriangularPath);
    }
}
