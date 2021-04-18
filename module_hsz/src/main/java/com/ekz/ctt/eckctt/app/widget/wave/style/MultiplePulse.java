package com.ekz.ctt.eckctt.app.widget.wave.style;


import com.ekz.ctt.eckctt.app.widget.wave.sprite.Sprite;
import com.ekz.ctt.eckctt.app.widget.wave.sprite.SpriteContainer;

/**
 * @author vondear
 */
public class MultiplePulse extends SpriteContainer {
    @Override
    public Sprite[] onCreateChild() {
        return new Sprite[]{
                new Pulse(),
                new Pulse(),
                new Pulse(),
        };
    }

    @Override
    public void onChildCreated(Sprite... sprites) {
        for (int i = 0; i < sprites.length; i++) {
            sprites[i].setAnimationDelay(200 * (i + 1));
        }
    }
}
