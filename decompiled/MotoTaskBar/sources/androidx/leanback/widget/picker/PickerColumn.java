package androidx.leanback.widget.picker;

/* JADX INFO: loaded from: classes.dex */
public class PickerColumn {
    private int mCurrentValue;
    private String mLabelFormat;
    private int mMaxValue;
    private int mMinValue;
    private CharSequence[] mStaticLabels;

    public int getCount() {
        return (this.mMaxValue - this.mMinValue) + 1;
    }

    public int getCurrentValue() {
        return this.mCurrentValue;
    }

    public CharSequence getLabelFor(int i) {
        CharSequence[] charSequenceArr = this.mStaticLabels;
        return charSequenceArr == null ? String.format(this.mLabelFormat, Integer.valueOf(i)) : charSequenceArr[i];
    }

    public int getMaxValue() {
        return this.mMaxValue;
    }

    public int getMinValue() {
        return this.mMinValue;
    }

    public void setCurrentValue(int i) {
        this.mCurrentValue = i;
    }

    public void setLabelFormat(String str) {
        this.mLabelFormat = str;
    }

    public void setMaxValue(int i) {
        this.mMaxValue = i;
    }

    public void setMinValue(int i) {
        this.mMinValue = i;
    }

    public void setStaticLabels(CharSequence[] charSequenceArr) {
        this.mStaticLabels = charSequenceArr;
    }
}
