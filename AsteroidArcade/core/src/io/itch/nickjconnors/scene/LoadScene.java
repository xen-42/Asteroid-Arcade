package io.itch.nickjconnors.scene;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

import io.itch.nickjconnors.AsteroidArcadeGame;
import io.itch.nickjconnors.managers.SceneManager;
import io.itch.nickjconnors.object.GameObject;
import io.itch.nickjconnors.object.TextObject;

public class LoadScene extends Scene {
	private Texture asteroidTexture;
	private GameObject asteroid, progress;
	public LoadScene(SceneManager sm, AssetManager am, BitmapFont[] f) {
		super(sm, am, f);
		asteroidTexture = assetManager.get("textures/asteroid.png", Texture.class);
		
		asteroid = new GameObject().setTexture(6f, asteroidTexture);
		asteroid.setPosition(new Vector2((AsteroidArcadeGame.WIDTH - asteroid.getWidth()) / 2f, (AsteroidArcadeGame.HEIGHT - asteroid.getHeight()) / 2f));
		asteroid.setRotation(0f, 45f);		

		bgObjects.add(asteroid);
		
		guiObjects.add(
				new TextObject("Loading...", 1f, font[0])
				.setPosition(new Vector2(10, 30)
				));
		
		progress = new TextObject("0%", 1f, font[1]).setPosition(new Vector2(320, 30));
		guiObjects.add(progress);
	}

	@Override
	public void update(float dt) {
		super.update(dt);

		((TextObject) progress).setMsg(Math.round(assetManager.getProgress() * 100) + "%");
		
		if(assetManager.update()) {
			sceneManager.push(new MenuScene(sceneManager, assetManager, font));
		}
	}
}
