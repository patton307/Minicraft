package com.theironyard.minicraft;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Minicraft extends ApplicationAdapter {
    SpriteBatch batch;
    TextureRegion down, up, right, left;
    FitViewport viewport;

    final int WIDTH = 100;
    final int HEIGHT = 100;

    float x;
    float y;
    float xV = 0;
    float yV = 0;
    float time = 0;

    final float MAX_VELOCITY = 500;

    @Override
    public void create () {
        batch = new SpriteBatch();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Texture tiles = new Texture("tiles.png");
        TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
        down = grid[6][0];
        up = grid[6][1];
        right = grid[6][2];
        left = grid[6][3];
    }

    @Override
    public void render () {
        move();
        draw();
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width, height);
    }

    public void move(){
        if(x >= 0 && y >= 0) {

            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                yV = MAX_VELOCITY;

            }

            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                yV = MAX_VELOCITY * -1;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                xV = MAX_VELOCITY;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                xV = MAX_VELOCITY * -1;
            }

            float oldX = x;
            float oldY = y;

            x += xV * Gdx.graphics.getDeltaTime();
            y += yV * Gdx.graphics.getDeltaTime();

            if (y < 0) {
                y = oldY;
                yV = MAX_VELOCITY;
            }

            if (y > viewport.getWorldHeight() - HEIGHT) {
                y = oldY;
                yV = MAX_VELOCITY * -1;
            }

            if (x < 0) {
                x = oldX;
                xV = MAX_VELOCITY;
            }

            if (x > viewport.getWorldWidth() - WIDTH) {
                x = oldX;
                xV = MAX_VELOCITY * -1;
            }




            /*if(x< 0 || x> (Gdx.graphics.getWidth()-WIDTH)){
                x = oldX;
            }
            if(y < 0 || y > (Gdx.graphics.getHeight()-HEIGHT)){
                y = oldY;
            }*/


            xV *= 0.7;
            yV *= 0.7;

        }
    }

    void draw(){
        time += Gdx.graphics.getDeltaTime();
        TextureRegion img;

        if(Math.abs(xV) < Math.abs(yV)){
            if(yV > 0){
                img = up;
            } else {
                img = down;
                }
            } else {
            if (xV > 0) {
                img = right;
            } else {
                img = left;
            }
        }
        Gdx.gl.glClearColor(0.5f, 1, 1, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        if(xV>=0) {
            batch.draw(img, x, y, WIDTH, HEIGHT);
             }
        else {
            batch.draw(img, x + WIDTH,y, WIDTH * -1, HEIGHT);
            }
        batch.end();
    }
}