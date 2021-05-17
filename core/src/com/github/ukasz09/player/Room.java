package com.github.ukasz09.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Room {
    private static final int FRAMES_QTY = 96;
    private static final int ZERO_PADDING_LENGTH = 3;
    public static final String ROOM_IMG_FRAME_PATH = "sheets/frame{number}.jpg";
    private static final float MOVING_FRAME_DURATION = 0.1f;

    private Animation<TextureRegion> moveAnimation;
    private TextureRegion region;
    float stateTime;
    public boolean isScreenTouched = false;

    public Room() {
        createAnimation();
        reset();
    }

    public void reset() {
        isScreenTouched = false;
    }

    private void createAnimation() {
        TextureRegion[] walkFrames = createFrames(ROOM_IMG_FRAME_PATH, FRAMES_QTY);
        moveAnimation = new Animation<>(MOVING_FRAME_DURATION, walkFrames);
        stateTime = 0f;
        region = moveAnimation.getKeyFrame(0);
    }

    private TextureRegion[] createFrames(String framesImgPath, int framesQty) {
        TextureRegion[] frames = new TextureRegion[framesQty];
        for (int i = 0; i < framesQty; i++) {
            String zeroPaddingFormat = "%0" + ZERO_PADDING_LENGTH + "d";
            int frameNumber = i + 1;
            String frameNumberStr = String.format(zeroPaddingFormat, frameNumber);
            String pathToFrame = framesImgPath.replace("{number}", frameNumberStr);
            Texture frame = new Texture(Gdx.files.internal(pathToFrame));
            frames[i] = new TextureRegion(frame);
        }
        return frames;
    }

    public void setIsScreenTouched(boolean isScreenTouched) {
        this.isScreenTouched = isScreenTouched;
    }

    public void updateAnimation(float delta) {
        stateTime += delta;
        if (isScreenTouched)
            region = moveAnimation.getKeyFrame(stateTime, true);
    }

    public TextureRegion getRegion() {
        return region;
    }
}
