package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.R$styleable;
import androidx.constraintlayout.widget.VirtualLayout;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class Grid extends VirtualLayout {
    private int[] mBoxViewIds;
    private View[] mBoxViews;
    private int mColumns;
    private int mColumnsSet;
    ConstraintLayout mContainer;
    private float mHorizontalGaps;
    private final int mMaxColumns;
    private final int mMaxRows;
    private int mNextAvailableIndex;
    private int mOrientation;
    private boolean[][] mPositionMatrix;
    private int mRows;
    private int mRowsSet;
    Set mSpanIds;
    private String mStrColumnWeights;
    private String mStrRowWeights;
    private String mStrSkips;
    private String mStrSpans;
    private boolean mUseRtl;
    private boolean mValidateInputs;
    private float mVerticalGaps;

    public Grid(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMaxRows = 50;
        this.mMaxColumns = 50;
        this.mNextAvailableIndex = 0;
        this.mSpanIds = new HashSet();
    }

    private boolean arrangeWidgets() {
        Grid grid;
        View[] views = getViews(this.mContainer);
        int i = 0;
        while (i < this.mCount) {
            if (this.mSpanIds.contains(Integer.valueOf(this.mIds[i]))) {
                grid = this;
            } else {
                int nextPosition = this.getNextPosition();
                int rowByIndex = this.getRowByIndex(nextPosition);
                int colByIndex = this.getColByIndex(nextPosition);
                if (nextPosition == -1) {
                    return false;
                }
                grid = this;
                grid.connectView(views[i], rowByIndex, colByIndex, 1, 1);
            }
            i++;
            this = grid;
        }
        return true;
    }

    private void buildBoxes() {
        int iMax = Math.max(this.mRows, this.mColumns);
        View[] viewArr = this.mBoxViews;
        int i = 0;
        if (viewArr == null) {
            this.mBoxViews = new View[iMax];
            int i2 = 0;
            while (true) {
                View[] viewArr2 = this.mBoxViews;
                if (i2 >= viewArr2.length) {
                    break;
                }
                viewArr2[i2] = makeNewView();
                i2++;
            }
        } else if (iMax != viewArr.length) {
            View[] viewArr3 = new View[iMax];
            for (int i3 = 0; i3 < iMax; i3++) {
                View[] viewArr4 = this.mBoxViews;
                if (i3 < viewArr4.length) {
                    viewArr3[i3] = viewArr4[i3];
                } else {
                    viewArr3[i3] = makeNewView();
                }
            }
            int i4 = iMax;
            while (true) {
                View[] viewArr5 = this.mBoxViews;
                if (i4 >= viewArr5.length) {
                    break;
                }
                this.mContainer.removeView(viewArr5[i4]);
                i4++;
            }
            this.mBoxViews = viewArr3;
        }
        this.mBoxViewIds = new int[iMax];
        while (true) {
            View[] viewArr6 = this.mBoxViews;
            if (i >= viewArr6.length) {
                setBoxViewVerticalChains();
                setBoxViewHorizontalChains();
                return;
            } else {
                this.mBoxViewIds[i] = viewArr6[i].getId();
                i++;
            }
        }
    }

    private void clearHParams(View view) {
        ConstraintLayout.LayoutParams layoutParamsParams = params(view);
        layoutParamsParams.horizontalWeight = -1.0f;
        layoutParamsParams.leftToRight = -1;
        layoutParamsParams.leftToLeft = -1;
        layoutParamsParams.rightToLeft = -1;
        layoutParamsParams.rightToRight = -1;
        ((ViewGroup.MarginLayoutParams) layoutParamsParams).leftMargin = -1;
        view.setLayoutParams(layoutParamsParams);
    }

    private void clearVParams(View view) {
        ConstraintLayout.LayoutParams layoutParamsParams = params(view);
        layoutParamsParams.verticalWeight = -1.0f;
        layoutParamsParams.topToBottom = -1;
        layoutParamsParams.topToTop = -1;
        layoutParamsParams.bottomToTop = -1;
        layoutParamsParams.bottomToBottom = -1;
        ((ViewGroup.MarginLayoutParams) layoutParamsParams).topMargin = -1;
        view.setLayoutParams(layoutParamsParams);
    }

    private void connectView(View view, int i, int i2, int i3, int i4) {
        ConstraintLayout.LayoutParams layoutParamsParams = params(view);
        int[] iArr = this.mBoxViewIds;
        layoutParamsParams.leftToLeft = iArr[i2];
        layoutParamsParams.topToTop = iArr[i];
        layoutParamsParams.rightToRight = iArr[(i2 + i4) - 1];
        layoutParamsParams.bottomToBottom = iArr[(i + i3) - 1];
        view.setLayoutParams(layoutParamsParams);
    }

    private boolean generateGrid(boolean z) {
        int[][] spans;
        int[][] spans2;
        if (this.mContainer == null || this.mRows < 1 || this.mColumns < 1) {
            return false;
        }
        if (z) {
            for (int i = 0; i < this.mPositionMatrix.length; i++) {
                int i2 = 0;
                while (true) {
                    boolean[][] zArr = this.mPositionMatrix;
                    if (i2 < zArr[0].length) {
                        zArr[i][i2] = true;
                        i2++;
                    }
                }
            }
            this.mSpanIds.clear();
        }
        this.mNextAvailableIndex = 0;
        buildBoxes();
        String str = this.mStrSkips;
        boolean zHandleSkips = (str == null || str.trim().isEmpty() || (spans2 = parseSpans(this.mStrSkips)) == null) ? true : handleSkips(spans2);
        String str2 = this.mStrSpans;
        if (str2 != null && !str2.trim().isEmpty() && (spans = parseSpans(this.mStrSpans)) != null) {
            zHandleSkips &= handleSpans(this.mIds, spans);
        }
        return (zHandleSkips && arrangeWidgets()) || !this.mValidateInputs;
    }

    private int getColByIndex(int i) {
        return this.mOrientation == 1 ? i / this.mRows : i % this.mColumns;
    }

    private int getNextPosition() {
        boolean z = false;
        int i = 0;
        while (!z) {
            i = this.mNextAvailableIndex;
            if (i >= this.mRows * this.mColumns) {
                return -1;
            }
            int rowByIndex = getRowByIndex(i);
            int colByIndex = getColByIndex(this.mNextAvailableIndex);
            boolean[] zArr = this.mPositionMatrix[rowByIndex];
            if (zArr[colByIndex]) {
                zArr[colByIndex] = false;
                z = true;
            }
            this.mNextAvailableIndex++;
        }
        return i;
    }

    private int getRowByIndex(int i) {
        return this.mOrientation == 1 ? i % this.mRows : i / this.mColumns;
    }

    private boolean handleSkips(int[][] iArr) {
        for (int i = 0; i < iArr.length; i++) {
            int rowByIndex = getRowByIndex(iArr[i][0]);
            int colByIndex = getColByIndex(iArr[i][0]);
            int[] iArr2 = iArr[i];
            if (!invalidatePositions(rowByIndex, colByIndex, iArr2[1], iArr2[2])) {
                return false;
            }
        }
        return true;
    }

    private boolean handleSpans(int[] iArr, int[][] iArr2) {
        View[] views = getViews(this.mContainer);
        int i = 0;
        while (i < iArr2.length) {
            int rowByIndex = this.getRowByIndex(iArr2[i][0]);
            int colByIndex = this.getColByIndex(iArr2[i][0]);
            int[] iArr3 = iArr2[i];
            if (!this.invalidatePositions(rowByIndex, colByIndex, iArr3[1], iArr3[2])) {
                return false;
            }
            View view = views[i];
            int[] iArr4 = iArr2[i];
            int i2 = iArr4[1];
            int i3 = iArr4[2];
            Grid grid = this;
            grid.connectView(view, rowByIndex, colByIndex, i2, i3);
            grid.mSpanIds.add(Integer.valueOf(iArr[i]));
            i++;
            this = grid;
        }
        return true;
    }

    private void initVariables() {
        boolean[][] zArr = (boolean[][]) Array.newInstance((Class<?>) Boolean.TYPE, this.mRows, this.mColumns);
        this.mPositionMatrix = zArr;
        for (boolean[] zArr2 : zArr) {
            Arrays.fill(zArr2, true);
        }
    }

    private boolean invalidatePositions(int i, int i2, int i3, int i4) {
        for (int i5 = i; i5 < i + i3; i5++) {
            for (int i6 = i2; i6 < i2 + i4; i6++) {
                boolean[][] zArr = this.mPositionMatrix;
                if (i5 < zArr.length && i6 < zArr[0].length) {
                    boolean[] zArr2 = zArr[i5];
                    if (zArr2[i6]) {
                        zArr2[i6] = false;
                    }
                }
                return false;
            }
        }
        return true;
    }

    private boolean isSpansValid(CharSequence charSequence) {
        return true;
    }

    private View makeNewView() {
        View view = new View(getContext());
        view.setId(View.generateViewId());
        view.setVisibility(4);
        this.mContainer.addView(view, new ConstraintLayout.LayoutParams(0, 0));
        return view;
    }

    private ConstraintLayout.LayoutParams params(View view) {
        return (ConstraintLayout.LayoutParams) view.getLayoutParams();
    }

    private int[][] parseSpans(String str) {
        if (!isSpansValid(str)) {
            return null;
        }
        String[] strArrSplit = str.split(",");
        int[][] iArr = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, strArrSplit.length, 3);
        for (int i = 0; i < strArrSplit.length; i++) {
            String[] strArrSplit2 = strArrSplit[i].trim().split(":");
            String[] strArrSplit3 = strArrSplit2[1].split("x");
            iArr[i][0] = Integer.parseInt(strArrSplit2[0]);
            iArr[i][1] = Integer.parseInt(strArrSplit3[0]);
            iArr[i][2] = Integer.parseInt(strArrSplit3[1]);
        }
        return iArr;
    }

    private float[] parseWeights(int i, String str) {
        float[] fArr = null;
        if (str != null && !str.trim().isEmpty()) {
            String[] strArrSplit = str.split(",");
            if (strArrSplit.length != i) {
                return null;
            }
            fArr = new float[i];
            for (int i2 = 0; i2 < i; i2++) {
                fArr[i2] = Float.parseFloat(strArrSplit[i2].trim());
            }
        }
        return fArr;
    }

    private void setBoxViewHorizontalChains() {
        int i;
        int id = getId();
        int iMax = Math.max(this.mRows, this.mColumns);
        float[] weights = parseWeights(this.mColumns, this.mStrColumnWeights);
        int i2 = 0;
        ConstraintLayout.LayoutParams layoutParamsParams = params(this.mBoxViews[0]);
        if (this.mColumns == 1) {
            clearHParams(this.mBoxViews[0]);
            layoutParamsParams.leftToLeft = id;
            layoutParamsParams.rightToRight = id;
            this.mBoxViews[0].setLayoutParams(layoutParamsParams);
            return;
        }
        while (true) {
            i = this.mColumns;
            if (i2 >= i) {
                break;
            }
            ConstraintLayout.LayoutParams layoutParamsParams2 = params(this.mBoxViews[i2]);
            clearHParams(this.mBoxViews[i2]);
            if (weights != null) {
                layoutParamsParams2.horizontalWeight = weights[i2];
            }
            if (i2 > 0) {
                layoutParamsParams2.leftToRight = this.mBoxViewIds[i2 - 1];
            } else {
                layoutParamsParams2.leftToLeft = id;
            }
            if (i2 < this.mColumns - 1) {
                layoutParamsParams2.rightToLeft = this.mBoxViewIds[i2 + 1];
            } else {
                layoutParamsParams2.rightToRight = id;
            }
            if (i2 > 0) {
                ((ViewGroup.MarginLayoutParams) layoutParamsParams2).leftMargin = (int) this.mHorizontalGaps;
            }
            this.mBoxViews[i2].setLayoutParams(layoutParamsParams2);
            i2++;
        }
        while (i < iMax) {
            ConstraintLayout.LayoutParams layoutParamsParams3 = params(this.mBoxViews[i]);
            clearHParams(this.mBoxViews[i]);
            layoutParamsParams3.leftToLeft = id;
            layoutParamsParams3.rightToRight = id;
            this.mBoxViews[i].setLayoutParams(layoutParamsParams3);
            i++;
        }
    }

    private void setBoxViewVerticalChains() {
        int i;
        int id = getId();
        int iMax = Math.max(this.mRows, this.mColumns);
        float[] weights = parseWeights(this.mRows, this.mStrRowWeights);
        int i2 = 0;
        if (this.mRows == 1) {
            ConstraintLayout.LayoutParams layoutParamsParams = params(this.mBoxViews[0]);
            clearVParams(this.mBoxViews[0]);
            layoutParamsParams.topToTop = id;
            layoutParamsParams.bottomToBottom = id;
            this.mBoxViews[0].setLayoutParams(layoutParamsParams);
            return;
        }
        while (true) {
            i = this.mRows;
            if (i2 >= i) {
                break;
            }
            ConstraintLayout.LayoutParams layoutParamsParams2 = params(this.mBoxViews[i2]);
            clearVParams(this.mBoxViews[i2]);
            if (weights != null) {
                layoutParamsParams2.verticalWeight = weights[i2];
            }
            if (i2 > 0) {
                layoutParamsParams2.topToBottom = this.mBoxViewIds[i2 - 1];
            } else {
                layoutParamsParams2.topToTop = id;
            }
            if (i2 < this.mRows - 1) {
                layoutParamsParams2.bottomToTop = this.mBoxViewIds[i2 + 1];
            } else {
                layoutParamsParams2.bottomToBottom = id;
            }
            if (i2 > 0) {
                ((ViewGroup.MarginLayoutParams) layoutParamsParams2).topMargin = (int) this.mHorizontalGaps;
            }
            this.mBoxViews[i2].setLayoutParams(layoutParamsParams2);
            i2++;
        }
        while (i < iMax) {
            ConstraintLayout.LayoutParams layoutParamsParams3 = params(this.mBoxViews[i]);
            clearVParams(this.mBoxViews[i]);
            layoutParamsParams3.topToTop = id;
            layoutParamsParams3.bottomToBottom = id;
            this.mBoxViews[i].setLayoutParams(layoutParamsParams3);
            i++;
        }
    }

    private void updateActualRowsAndColumns() {
        int i;
        int i2 = this.mRowsSet;
        if (i2 != 0 && (i = this.mColumnsSet) != 0) {
            this.mRows = i2;
            this.mColumns = i;
            return;
        }
        int i3 = this.mColumnsSet;
        if (i3 > 0) {
            this.mColumns = i3;
            this.mRows = ((this.mCount + i3) - 1) / i3;
        } else if (i2 > 0) {
            this.mRows = i2;
            this.mColumns = ((this.mCount + i2) - 1) / i2;
        } else {
            int iSqrt = (int) (Math.sqrt(this.mCount) + 1.5d);
            this.mRows = iSqrt;
            this.mColumns = ((this.mCount + iSqrt) - 1) / iSqrt;
        }
    }

    @Override // androidx.constraintlayout.widget.VirtualLayout, androidx.constraintlayout.widget.ConstraintHelper
    protected void init(AttributeSet attributeSet) {
        super.init(attributeSet);
        this.mUseViewMeasure = true;
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.Grid);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i);
                if (index == R$styleable.Grid_grid_rows) {
                    this.mRowsSet = typedArrayObtainStyledAttributes.getInteger(index, 0);
                } else if (index == R$styleable.Grid_grid_columns) {
                    this.mColumnsSet = typedArrayObtainStyledAttributes.getInteger(index, 0);
                } else if (index == R$styleable.Grid_grid_spans) {
                    this.mStrSpans = typedArrayObtainStyledAttributes.getString(index);
                } else if (index == R$styleable.Grid_grid_skips) {
                    this.mStrSkips = typedArrayObtainStyledAttributes.getString(index);
                } else if (index == R$styleable.Grid_grid_rowWeights) {
                    this.mStrRowWeights = typedArrayObtainStyledAttributes.getString(index);
                } else if (index == R$styleable.Grid_grid_columnWeights) {
                    this.mStrColumnWeights = typedArrayObtainStyledAttributes.getString(index);
                } else if (index == R$styleable.Grid_grid_orientation) {
                    this.mOrientation = typedArrayObtainStyledAttributes.getInt(index, 0);
                } else if (index == R$styleable.Grid_grid_horizontalGaps) {
                    this.mHorizontalGaps = typedArrayObtainStyledAttributes.getDimension(index, 0.0f);
                } else if (index == R$styleable.Grid_grid_verticalGaps) {
                    this.mVerticalGaps = typedArrayObtainStyledAttributes.getDimension(index, 0.0f);
                } else if (index == R$styleable.Grid_grid_validateInputs) {
                    this.mValidateInputs = typedArrayObtainStyledAttributes.getBoolean(index, false);
                } else if (index == R$styleable.Grid_grid_useRtl) {
                    this.mUseRtl = typedArrayObtainStyledAttributes.getBoolean(index, false);
                }
            }
            updateActualRowsAndColumns();
            initVariables();
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    @Override // androidx.constraintlayout.widget.VirtualLayout, androidx.constraintlayout.widget.ConstraintHelper, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mContainer = (ConstraintLayout) getParent();
        generateGrid(false);
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isInEditMode()) {
            Paint paint = new Paint();
            paint.setColor(-65536);
            paint.setStyle(Paint.Style.STROKE);
            int top = getTop();
            int left = getLeft();
            int bottom = getBottom();
            int right = getRight();
            View[] viewArr = this.mBoxViews;
            int length = viewArr.length;
            int i = 0;
            while (i < length) {
                View view = viewArr[i];
                int left2 = view.getLeft() - left;
                int top2 = view.getTop() - top;
                int right2 = view.getRight() - left;
                int bottom2 = view.getBottom() - top;
                Canvas canvas2 = canvas;
                canvas2.drawRect(left2, 0.0f, right2, bottom - top, paint);
                canvas2.drawRect(0.0f, top2, right - left, bottom2, paint);
                i++;
                canvas = canvas2;
            }
        }
    }
}
