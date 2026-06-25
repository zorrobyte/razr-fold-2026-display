package com.google.android.setupcompat.template;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
class FooterButtonInflater {
    protected final Context context;

    public FooterButtonInflater(Context context) {
        this.context = context;
    }

    private FooterButton inflate(XmlPullParser xmlPullParser) {
        int next;
        AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xmlPullParser);
        do {
            try {
                next = xmlPullParser.next();
                if (next == 2) {
                    break;
                }
            } catch (IOException e) {
                throw new InflateException(xmlPullParser.getPositionDescription() + ": " + e.getMessage(), e);
            } catch (XmlPullParserException e2) {
                throw new InflateException(e2.getMessage(), e2);
            }
        } while (next != 1);
        if (next != 2) {
            throw new InflateException(xmlPullParser.getPositionDescription() + ": No start tag found!");
        }
        if (xmlPullParser.getName().equals("FooterButton")) {
            return new FooterButton(this.context, attributeSetAsAttributeSet);
        }
        throw new InflateException(xmlPullParser.getPositionDescription() + ": not a FooterButton");
    }

    public Resources getResources() {
        return this.context.getResources();
    }

    public FooterButton inflate(int i) {
        XmlResourceParser xml = getResources().getXml(i);
        try {
            return inflate(xml);
        } finally {
            xml.close();
        }
    }
}
