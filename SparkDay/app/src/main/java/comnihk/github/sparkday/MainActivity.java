package comnihk.github.sparkday;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ImageView mBbLogoMarkers;
    private Bitmap mBbLogoBitMap;
    private Animation mFadeOut;
    private Map<Integer, Integer> mColorToSound;
    private Map<Integer, ImageView> mColorToImageView;
    private SoundPool mSoundPool;
    private static final int MAX_STREAMS = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        // http://stackoverflow.com/a/6822116/2997980
//        mFadeOut = new AlphaAnimation(1, 0);
//        mFadeOut.setInterpolator(new AccelerateInterpolator());
//        mFadeOut.setStartOffset(1000);
//        mFadeOut.setDuration(1000);

        mFadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        mBbLogoMarkers = (ImageView) findViewById(R.id.bbLogoMarkers);

        // http://stackoverflow.com/a/7807442/2997980
        mBbLogoBitMap = ((BitmapDrawable) mBbLogoMarkers.getDrawable()).getBitmap();
        mSoundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);

        populateColorToSound();
        populateColorToImageView();

        mBbLogoMarkers.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() != MotionEvent.ACTION_DOWN) return false;

                // The bb logo image scales on the device; this compensates for that scaling
                // http://android-er.blogspot.ca/2012/10/get-touched-pixel-color-of-scaled.html
                float[] eventXY = { event.getX(), event.getY() };

                Matrix invertMatrix = new Matrix();
                ((ImageView)v).getImageMatrix().invert(invertMatrix);

                invertMatrix.mapPoints(eventXY);
                int x = (int) eventXY[0];
                int y = (int) eventXY[1];

                // Prevent pixel out of bounds of bb logo
                if (x < 0) x = 0;
                if (y < 0) y = 0;
                if (x > mBbLogoBitMap.getWidth() - 1) x = mBbLogoBitMap.getWidth() - 1;
                if (y > mBbLogoBitMap.getHeight() - 1) y = mBbLogoBitMap.getHeight() - 1;

                int pixelColor = mBbLogoBitMap.getPixel(x, y);

                // Play the respective sound
                if (mColorToSound.containsKey(pixelColor)) {
                    playSound(mColorToSound.get(pixelColor));
                }

                // Animate the respective node
//                if (mColorToImageView.containsKey(pixelColor)) {
//                    fadeOut(pixelColor);
//                }

                return true;
            }
        });
    }

    private void playSound(int soundId) {
        // Args: soundId, leftVolume, rightVolume, priority, loop, rate
        mSoundPool.play(soundId, 1f, 1f, 0, 0, 1f);
    }

    private void populateColorToSound() {
        mColorToSound = new HashMap<>();
        mColorToSound.put(Color.DKGRAY, mSoundPool.load(this, R.raw.a, 1));
        mColorToSound.put(Color.BLUE, mSoundPool.load(this, R.raw.b, 1));
        mColorToSound.put(Color.GREEN, mSoundPool.load(this, R.raw.c, 1));
        mColorToSound.put(Color.RED, mSoundPool.load(this, R.raw.d, 1));
        mColorToSound.put(Color.YELLOW, mSoundPool.load(this, R.raw.e, 1));
        mColorToSound.put(Color.CYAN, mSoundPool.load(this, R.raw.f, 1));
        mColorToSound.put(Color.MAGENTA, mSoundPool.load(this, R.raw.g, 1));
    }

    private void populateColorToImageView() {
        mColorToImageView = new HashMap<>();
        mColorToImageView.put(Color.DKGRAY, (ImageView) findViewById(R.id.bbLogoMarkerA));
    }

    private void fadeOut(int imageViewId) {
        ImageView imageView = mColorToImageView.get(imageViewId);
        imageView.setVisibility(ImageView.VISIBLE);
        imageView.startAnimation(mFadeOut);
        imageView.setVisibility(ImageView.INVISIBLE);
    }
}