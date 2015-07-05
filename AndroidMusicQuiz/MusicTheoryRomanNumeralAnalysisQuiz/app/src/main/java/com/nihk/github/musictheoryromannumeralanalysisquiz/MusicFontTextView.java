package com.nihk.github.musictheoryromannumeralanalysisquiz;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class MusicFontTextView extends TextView {
    public MusicFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    public MusicFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public MusicFontTextView(Context context) {
        super(context);
        init();
    }
    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/MusiQwik.ttf");
        setTypeface(tf);
    }
}