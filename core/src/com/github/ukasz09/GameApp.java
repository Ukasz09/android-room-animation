package com.github.ukasz09;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.github.ukasz09.player.Room;

public class GameApp extends ApplicationAdapter {
    private static final float CAMERA_ZOOM = 4.0f;
    public static final float PIXEL_PER_METER = 16f;
    private static final float TIME_STEP = 1 / 60f;

    private OrthographicCamera camera;
    private Box2DDebugRenderer box2DDebugRenderer;
    private Room room;
    private SpriteBatch batch;
    private Texture texture;

    public GameApp() {
    }

    @Override
    public void create() {
        initCamera();
        initTextures();
        initRoom();
    }

    private void initCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() / CAMERA_ZOOM, Gdx.graphics.getHeight() / CAMERA_ZOOM);
    }

    private void initTextures() {
        box2DDebugRenderer = new Box2DDebugRenderer();
        batch = new SpriteBatch();
        texture = new Texture(Room.ROOM_SHEET_PATH);
    }

    private void initRoom() {
        room = new Room();
    }

    @Override
    public void render() {
        update();
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        drawRoom();
        batch.end();
//        renderDebugBoxes();
    }

    private void update() {
        inputUpdate();
        room.updateAnimation(TIME_STEP);
    }

    // TODO: fix
    private void drawRoom() {
        float truckOriginX = room.getWidthPx() / 2;
        float truckOriginY = room.getHeightPx() / 2;
        batch.draw(room.getRegion(), 0, 0, truckOriginX, truckOriginY, room.getWidthPx(), room.getHeightPx(), 1.2f, 1.2f, 0);
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width / CAMERA_ZOOM, height / CAMERA_ZOOM);
    }

    @Override
    public void dispose() {
        texture.dispose();
        batch.dispose();
        box2DDebugRenderer.dispose();
    }


    private void inputUpdate() {
        if (Gdx.input.isTouched()) {
            room.setIsScreenTouched(true);
            onTouchPositionUpdate();
        } else {
            room.setIsScreenTouched(false);
        }
    }

    private void onTouchPositionUpdate() {
        Vector3 touchPosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        touchPosition = camera.unproject(touchPosition);
        System.out.println(touchPosition);
    }
}
