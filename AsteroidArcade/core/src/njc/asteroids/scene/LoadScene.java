package njc.asteroids.scene;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import njc.asteroids.Game;
import njc.asteroids.graphics.Font;
import njc.asteroids.managers.SceneManager;
import njc.asteroids.object.GameObject;
import njc.asteroids.object.TextObject;

public class LoadScene extends Scene {
	private Texture asteroidTexture;
	private GameObject asteroid, progress;
	public LoadScene(SceneManager sm, AssetManager am, Font[] f) {
		super(sm, am, f);
		asteroidTexture = assetManager.get("textures/asteroid.png", Texture.class);
		
		asteroid = new GameObject().setTexture(6f, asteroidTexture);
		asteroid.setPosition(new Vector2((Game.WIDTH - asteroid.getWidth()) / 2f, (Game.HEIGHT - asteroid.getHeight()) / 2f));
		asteroid.setRotation(0f, 45f);		

		bgObjects.add(asteroid);
		
		guiObjects.add(
				new TextObject("Loading...", 1f, font[0])
				.setPosition(new Vector2(10, 10)
				));
		
		progress = new TextObject("0%", 1f, font[1]).setPosition(new Vector2(320, 10));
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
