package com.ekz.ctt.eckctt.app.widget.wave.style;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.view.animation.LinearInterpolator;

import com.ekz.ctt.eckctt.app.widget.wave.animation.SpriteAnimatorBuilder;
import com.ekz.ctt.eckctt.app.widget.wave.sprite.RectSprite;
import com.ekz.ctt.eckctt.app.widget.wave.sprite.Sprite;
import com.ekz.ctt.eckctt.app.widget.wave.sprite.SpriteContainer;


/**
 * @author vondear
 */
public class Wave extends SpriteContainer {

    @Override
    public Sprite[] onCreateChild() {
        WaveItem[] waveItems = new WaveItem[5];
        for (int i = 0; i < waveItems.length; i++) {
            waveItems[i] = new WaveItem();
            waveItems[i].setAnimationDelay(-1200 + i * 100);
        }
        return waveItems;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        bounds = clipSquare(bounds);
        int rw = bounds.width() / getChildCount();
        int width = bounds.width() / 5 * 3 / 5;
        for (int i = 0; i < getChildCount(); i++) {
            Sprite sprite = getChildAt(i);
            int l = bounds.left + i * rw + rw / 5;
            int r = l + width;
            sprite.setDrawBounds(l, bounds.top, r, bounds.bottom);
        }
    }

    private class WaveItem extends RectSprite {

        WaveItem() {
            setScaleY(0.4f);
        }

        @Override
        public ValueAnimator onCreateAnimation() {
            float fractions[] = new float[]{0f, 0.2f, 0.4f, 1f};
            return new SpriteAnimatorBuilder(this).scaleY(fractions, 0.4f, 1f,0.4f,0.3f).
                    duration(1000).
                    easeInOut(fractions)
                    .interpolator(new LinearInterpolator())
                    .build();
        }
    }
}
