package com.example.islamicapp.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class SKtextViewAladin extends AppCompatTextView {
    public SKtextViewAladin(Context context) {
        super(context);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "Roboto_Medium.ttf"));
    }

    public SKtextViewAladin(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "Roboto_Medium.ttf"));
    }

    public SKtextViewAladin(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "Roboto_Medium.ttf"));
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
