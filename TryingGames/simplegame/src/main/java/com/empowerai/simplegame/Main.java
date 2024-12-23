package com.empowerai.simplegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture texture;

    @Override
    public void create() {
        batch = new SpriteBatch();
        texture = new Texture("texture.png"); // Place this image in the assets folder
    }

    @Override
    public void render() {
        batch.begin();
        batch.draw(texture, 100, 100); // Draw the texture at (100, 100)
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("My libGDX Game");
        config.setWindowedMode(800, 600);
        new Lwjgl3Application(new Main(), config);
    }
}
