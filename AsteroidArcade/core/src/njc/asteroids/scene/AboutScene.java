package njc.asteroids.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

import njc.asteroids.Game;
import njc.asteroids.managers.SceneManager;
import njc.asteroids.object.GameObject;
import njc.asteroids.object.TextObject;

public class AboutScene extends Scene {
	private Texture starTexture1, starTexture2,	xTexture;

private GameObject[] stars1, stars2;

private Sound blip;

private GameObject back;

public AboutScene(SceneManager sm, AssetManager am, BitmapFont[] f) {		
	super(sm, am, f);
	
	starTexture1 = assetManager.get("textures/star.png", Texture.class);
	starTexture2 = assetManager.get("textures/star_1.png", Texture.class);
	
	xTexture = assetManager.get("gui/x.png", Texture.class);
	
	stars1 = new GameObject[20];
	stars2 = new GameObject[20];
	
	for(int i = 0; i < 20; i++) {
		GameObject star = new GameObject().setTexture(2f, (float) Math.random() + 0.5f, starTexture1, starTexture2);
		int x = ((int) ((Math.random() * (Game.WIDTH - star.getWidth())) / star.getWidth())) * (int) star.getWidth();
		int y = ((int) ((Math.random() * (Game.HEIGHT - star.getWidth())) / star.getWidth())) * (int) star.getWidth();
		star.setPosition(new Vector2(x, y));
		//star.setRotation((float) Math.random() * 360f, (float) Math.random() * 180f - 90f);
		
		bgObjects.add(star);
		stars1[i] = star;
	}
	for(int i = 0; i < 20; i++) {
		GameObject star = new GameObject().setTexture(1f, (float) (Math.random() + 0.5f), starTexture1, starTexture2);
		int x = ((int) ((Math.random() * (Game.WIDTH - star.getWidth())) / star.getWidth())) * (int) star.getWidth();
		int y = ((int) ((Math.random() * (Game.HEIGHT - star.getWidth())) / star.getWidth())) * (int) star.getWidth();
		star.setPosition(new Vector2(x, y));
		//star.setRotation((float) Math.random() * 360f, (float) Math.random() * 180f - 90f);
		
		bgObjects.add(star);
		stars2[i] = star;
	}
	
	blip = assetManager.get("audio/blip.wav", Sound.class);
	
	back = new GameObject().setTexture(2f, xTexture);
	back.setPosition(new Vector2(Game.WIDTH - 40, Game.HEIGHT - 40));
	this.guiObjects.add(back);
		
	TextObject text3 = new TextObject("ABOUT", 1f, font[0]);
	text3.setPosition(new Vector2(0f, Game.HEIGHT - 96));
	text3.centerX();
	this.guiObjects.add(text3);
	
	String text = ""
			+ "Hello! This is a\n"
			+ "game I made in my\n"
			+ "free time.\n\n"
			+ "Made using LibGDX.\n"
			+ "Royalty free music\n"
			+ "from Incompetech.\n"
			+ "\n"
			+ "This game is open\n"
			+ "source.\n"
			+ "From Ceres Logiciel.";
	this.guiObjects.add(
			new TextObject(text, 1f, font[0])
			.setPosition(new Vector2(8, Game.HEIGHT - 136))
			);
}

@Override
public void update(float dt) {
	super.update(dt);
}

@Override
public void handleInput(float dt, Vector2 mouse, float roll) {
	if(Gdx.input.justTouched()) {			
		if(back.isTouched(mouse)) {
			blip.play(Game.masterVolume);
			
			sceneManager.pop();
		}
	}
}
}
