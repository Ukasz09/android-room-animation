package com.github.ukasz09.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Room {
    private static final int FRAMES_QTY = 84;
    private static final int ZERO_PADDING_LENGTH = 3;
    public static final String ROOM_IMG_FRAME_PATH = "sheets/frame{number}.jpg";
    private static final float MOVING_FRAME_DURATION = 0.09f;

    private Animation<TextureRegion> cameraAnimation;
    private TextureRegion region;
    float stateTime;
    public boolean rightPartOfScreenTouched = false;
    public boolean leftPartOfScreenTouched = false;

    public Room() {
        createAnimation();
        resetIsScreenTouched();
    }

    public void resetIsScreenTouched() {
        rightPartOfScreenTouched = false;
        leftPartOfScreenTouched = false;
    }

    private void createAnimation() {
        TextureRegion[] walkFrames = createFrames(ROOM_IMG_FRAME_PATH, FRAMES_QTY);
        cameraAnimation = new Animation<>(MOVING_FRAME_DURATION, walkFrames);
        stateTime = 0f;
        region = cameraAnimation.getKeyFrame(stateTime);
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

    public void setRightPartOfScreenTouched(boolean isTouched) {
        this.rightPartOfScreenTouched = isTouched;
    }

    public void setLeftPartOfScreenTouched(boolean isTouched) {
        this.leftPartOfScreenTouched = isTouched;
    }

    public void updateAnimation(float delta) {
        if (rightPartOfScreenTouched) {
            stateTime += delta;
            if (stateTime > FRAMES_QTY) {
                stateTime = FRAMES_QTY;
            }
            region = cameraAnimation.getKeyFrame(stateTime, false);
        } else if (leftPartOfScreenTouched) {
            stateTime -= delta;
            if (stateTime < 0) {
                stateTime = 0;
            }
            region = cameraAnimation.getKeyFrame(stateTime, false);
        }
    }

    public TextureRegion getRegion() {
        return region;
    }
}
