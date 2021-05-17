package com.github.ukasz09.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.ukasz09.GameApp;

public class Room {
    private static final int MOVE_FRAMES_COLUMNS = 5, FRAME_ROWS = 1;
    public static final String ROOM_SHEET_PATH = "sheets/monster-move.png";
    private static final float MOVING_FRAME_DURATION = 0.03f;

    private Animation<TextureRegion> moveAnimation;
    private TextureRegion region;
    float stateTime;
    private float widthPx;
    private float heightPx;
    public boolean isScreenTouched = false;

    public Room() {
        createAnimation();
        reset();
    }

    public void reset() {
        isScreenTouched = false;
        this.widthPx = GameApp.VIEWPORT_WIDTH;
        this.heightPx = GameApp.VIEWPORT_HEIGHT;
    }

    private void createAnimation() {
        TextureRegion[] walkFrames = createFrames(ROOM_SHEET_PATH, MOVE_FRAMES_COLUMNS, FRAME_ROWS);
        moveAnimation = new Animation<>(MOVING_FRAME_DURATION, walkFrames);
        stateTime = 0f;
        region = moveAnimation.getKeyFrame(0);
    }

    private TextureRegion[] createFrames(String sheetPath, int frameColumns, int frameRows) {
        Texture sheet = new Texture(Gdx.files.internal(sheetPath));
        TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth() / frameColumns, sheet.getHeight() / frameRows);
        TextureRegion[] frames = new TextureRegion[frameColumns * frameRows];
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameColumns; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        return frames;
    }

    public float getWidthPx() {
        return widthPx;
    }

    public float getHeightPx() {
        return heightPx;
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
