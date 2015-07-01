package com.nihk.github.musictheoryromannumeralanalysisquiz;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class MusicText extends TextView {
    public MusicText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    public MusicText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public MusicText(Context context) {
        super(context);
        init();
    }
    private void init() {
        String freeSerifPath = "fonts/FreeSerif.ttf";
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), freeSerifPath);
        setTypeface(tf);
    }
}