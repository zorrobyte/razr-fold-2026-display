package com.motorola.extendscreenshot;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.motorola.taskbar.R$color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ScreenshotSelectorView extends View {
    private final Paint mCurvePaintArea;
    private Point mCurveStartPoint;
    private GraphicPath mGraphicPath;
    private boolean mIsUp;
    private boolean mIsValid;
    private final Paint mPaintBackground;
    private final Paint mPaintSelection;
    private final Paint mRectPaintArea;
    private Rect mSelectionRect;
    private int mSelectorMode;
    private List mSelectorViewStatusChangedListeners;
    private Point mStartPoint;

    public class GraphicPath implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.motorola.extendscreenshot.ScreenshotSelectorView.GraphicPath.1
            @Override // android.os.Parcelable.Creator
            public GraphicPath createFromParcel(Parcel parcel) {
                return new GraphicPath(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public GraphicPath[] newArray(int i) {
                return new GraphicPath[i];
            }
        };
        private transient Path mPath;
        public List pathX;
        public List pathY;

        GraphicPath() {
            this.pathX = new ArrayList();
            this.pathY = new ArrayList();
        }

        GraphicPath(Parcel parcel) {
            int i = parcel.readInt();
            int[] iArr = new int[i];
            int[] iArr2 = new int[i];
            parcel.readIntArray(iArr);
            parcel.readIntArray(iArr2);
            this.pathX = new ArrayList();
            this.pathY = new ArrayList();
            for (int i2 = 0; i2 < i; i2++) {
                this.pathX.add(Integer.valueOf(iArr[i2]));
            }
            for (int i3 = 0; i3 < i; i3++) {
                this.pathY.add(Integer.valueOf(iArr2[i3]));
            }
            this.mPath = createPath();
        }

        private Path createPath() {
            this.mPath = new Path();
            if (size() > 1) {
                this.mPath.moveTo(((Integer) this.pathX.get(0)).intValue(), ((Integer) this.pathY.get(0)).intValue());
                for (int i = 1; i < size(); i++) {
                    this.mPath.lineTo(((Integer) this.pathX.get(i)).intValue(), ((Integer) this.pathY.get(i)).intValue());
                }
            }
            return this.mPath;
        }

        private void createPathIfNeed() {
            if (this.mPath == null) {
                this.mPath = createPath();
            }
        }

        private int[] getXArray() {
            int size = this.pathX.size();
            int[] iArr = new int[size];
            for (int i = 0; i < size; i++) {
                iArr[i] = ((Integer) this.pathX.get(i)).intValue();
            }
            return iArr;
        }

        private int[] getYArray() {
            int size = this.pathY.size();
            int[] iArr = new int[size];
            for (int i = 0; i < size; i++) {
                iArr[i] = ((Integer) this.pathY.get(i)).intValue();
            }
            return iArr;
        }

        void addPath(int i, int i2) {
            createPathIfNeed();
            this.pathX.add(Integer.valueOf(i));
            this.pathY.add(Integer.valueOf(i2));
            if (this.mPath.isEmpty()) {
                this.mPath.moveTo(i, i2);
            } else {
                this.mPath.lineTo(i, i2);
            }
        }

        public void clear() {
            this.pathX.clear();
            this.pathY.clear();
            createPathIfNeed();
            this.mPath.reset();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public int getBottom() {
            int iIntValue = this.pathY.size() > 0 ? ((Integer) this.pathY.get(0)).intValue() : 0;
            Iterator it = this.pathY.iterator();
            while (it.hasNext()) {
                int iIntValue2 = ((Integer) it.next()).intValue();
                if (iIntValue2 > iIntValue) {
                    iIntValue = iIntValue2;
                }
            }
            return iIntValue;
        }

        Rect getBounds(Rect rect) {
            if (rect == null) {
                rect = new Rect();
            }
            RectF rectF = new RectF();
            getPath().computeBounds(rectF, true);
            rectF.round(rect);
            return rect;
        }

        public int getLeft() {
            int iIntValue = this.pathX.size() > 0 ? ((Integer) this.pathX.get(0)).intValue() : 0;
            Iterator it = this.pathX.iterator();
            while (it.hasNext()) {
                int iIntValue2 = ((Integer) it.next()).intValue();
                if (iIntValue2 < iIntValue) {
                    iIntValue = iIntValue2;
                }
            }
            return iIntValue;
        }

        public Path getPath() {
            createPathIfNeed();
            return this.mPath;
        }

        public int getRight() {
            int iIntValue = this.pathX.size() > 0 ? ((Integer) this.pathX.get(0)).intValue() : 0;
            Iterator it = this.pathX.iterator();
            while (it.hasNext()) {
                int iIntValue2 = ((Integer) it.next()).intValue();
                if (iIntValue2 > iIntValue) {
                    iIntValue = iIntValue2;
                }
            }
            return iIntValue;
        }

        public int getTop() {
            int iIntValue = this.pathY.size() > 0 ? ((Integer) this.pathY.get(0)).intValue() : 0;
            Iterator it = this.pathY.iterator();
            while (it.hasNext()) {
                int iIntValue2 = ((Integer) it.next()).intValue();
                if (iIntValue2 < iIntValue) {
                    iIntValue = iIntValue2;
                }
            }
            return iIntValue;
        }

        public int size() {
            return this.pathY.size();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.pathX.size());
            parcel.writeIntArray(getXArray());
            parcel.writeIntArray(getYArray());
        }
    }

    public interface SelectorViewStatusChangedListener {
        void onTouchChanged(boolean z);
    }

    public ScreenshotSelectorView(Context context) {
        this(context, null);
    }

    public ScreenshotSelectorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSelectorMode = 0;
        this.mSelectorViewStatusChangedListeners = new ArrayList();
        setLayerType(2, null);
        Paint paint = new Paint();
        this.mPaintBackground = paint;
        paint.setColor(context.getColor(R$color.screenshot_selector_mask));
        Paint paint2 = new Paint(1);
        this.mPaintSelection = paint2;
        paint2.setColor(0);
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        Paint paint3 = new Paint(1);
        this.mRectPaintArea = paint3;
        paint3.setColor(-1);
        Paint.Style style = Paint.Style.STROKE;
        paint3.setStyle(style);
        paint3.setStrokeWidth(4.0f);
        Paint paint4 = new Paint(1);
        this.mCurvePaintArea = paint4;
        paint4.setColor(-1);
        paint4.setStyle(style);
        paint4.setStrokeWidth(4.0f);
        paint4.setStrokeCap(Paint.Cap.ROUND);
        paint4.setDither(true);
        this.mGraphicPath = new GraphicPath();
    }

    private void drawCurve(Canvas canvas) {
        GraphicPath graphicPath = this.mGraphicPath;
        if (graphicPath != null) {
            if (!this.mIsUp || this.mIsValid) {
                canvas.drawPath(graphicPath.getPath(), this.mPaintSelection);
                canvas.drawPath(this.mGraphicPath.getPath(), this.mCurvePaintArea);
            } else {
                graphicPath.clear();
                canvas.drawPath(this.mGraphicPath.getPath(), this.mPaintSelection);
                canvas.drawPath(this.mGraphicPath.getPath(), this.mCurvePaintArea);
            }
        }
    }

    private void drawRect(Canvas canvas) {
        Rect rect = this.mSelectionRect;
        if (rect != null) {
            canvas.drawRect(rect, this.mPaintSelection);
            canvas.drawRect(this.mSelectionRect, this.mRectPaintArea);
        }
    }

    private boolean handleCurve(MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int action = motionEvent.getAction();
        if (action == 0) {
            startCurve(x, y);
            return true;
        }
        if (action != 1) {
            if (action != 2) {
                return true;
            }
            this.mGraphicPath.addPath(x, y);
            return true;
        }
        this.mGraphicPath.addPath(x, y);
        GraphicPath graphicPath = this.mGraphicPath;
        Point point = this.mCurveStartPoint;
        graphicPath.addPath(point.x, point.y);
        this.mGraphicPath.getBounds(this.mSelectionRect);
        this.mIsValid = isValidRect(this.mSelectionRect);
        return true;
    }

    private boolean handleRect(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            startSelection((int) motionEvent.getX(), (int) motionEvent.getY());
            return true;
        }
        if (action == 1) {
            this.mIsValid = isValidRect(this.mSelectionRect);
            return true;
        }
        if (action != 2) {
            return true;
        }
        updateSelection((int) motionEvent.getX(), (int) motionEvent.getY());
        return true;
    }

    private boolean isValidRect(Rect rect) {
        return (rect.right - rect.left) * (rect.bottom - rect.top) > 10;
    }

    private void notifyTouchChanged(boolean z) {
        Log.d("ExtendScreenshotSession", "notifyTouchChanged touching = " + z);
        Iterator it = this.mSelectorViewStatusChangedListeners.iterator();
        while (it.hasNext()) {
            ((SelectorViewStatusChangedListener) it.next()).onTouchChanged(z);
        }
    }

    private void startCurve(int i, int i2) {
        this.mIsUp = false;
        this.mCurveStartPoint = new Point(i, i2);
        this.mSelectionRect = new Rect();
        this.mGraphicPath.clear();
        this.mGraphicPath.addPath(i, i2);
    }

    public void addSelectorViewStatusChangeListener(SelectorViewStatusChangedListener selectorViewStatusChangedListener) {
        this.mSelectorViewStatusChangedListeners.add(selectorViewStatusChangedListener);
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.drawRect(((View) this).mLeft, ((View) this).mTop, ((View) this).mRight, ((View) this).mBottom, this.mPaintBackground);
        int i = this.mSelectorMode;
        if (i == 0) {
            drawRect(canvas);
        } else if (i == 1) {
            drawCurve(canvas);
        }
        canvas.restore();
    }

    public GraphicPath getGraphicPath() {
        return this.mGraphicPath;
    }

    public Rect getSelectionRect() {
        return this.mSelectionRect;
    }

    public int getSelectorMode() {
        return this.mSelectorMode;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled()) {
            return false;
        }
        int i = this.mSelectorMode;
        boolean zHandleCurve = i != 0 ? i != 1 ? false : handleCurve(motionEvent) : handleRect(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked == 1) {
                this.mIsUp = true;
            } else if (actionMasked == 3) {
            }
            notifyTouchChanged(false);
        } else {
            notifyTouchChanged(true);
            this.mIsUp = false;
        }
        postInvalidate();
        return zHandleCurve;
    }

    public void reset() {
        this.mIsValid = false;
        this.mIsUp = false;
        this.mStartPoint = null;
        this.mSelectionRect = null;
        this.mCurveStartPoint = null;
        this.mGraphicPath = new GraphicPath();
    }

    public void setSelectMode(int i) {
        this.mSelectorMode = i;
        if (i == 2 || i == 3) {
            setEnabled(false);
        } else {
            setEnabled(true);
        }
        reset();
    }

    public void startSelection(int i, int i2) {
        this.mStartPoint = new Point(i, i2);
        this.mSelectionRect = new Rect(i, i2, i, i2);
    }

    public void updateSelection(int i, int i2) {
        Rect rect = this.mSelectionRect;
        if (rect != null) {
            rect.left = Math.min(this.mStartPoint.x, i);
            this.mSelectionRect.right = Math.max(this.mStartPoint.x, i);
            this.mSelectionRect.top = Math.min(this.mStartPoint.y, i2);
            this.mSelectionRect.bottom = Math.max(this.mStartPoint.y, i2);
        }
    }
}
