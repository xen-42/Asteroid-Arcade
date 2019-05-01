package njc.asteroids.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import njc.asteroids.Game;
import njc.asteroids.graphics.Font;
import njc.asteroids.managers.SceneManager;
import njc.asteroids.object.GameObject;
import njc.asteroids.object.TextObject;

public class OptionScene extends Scene {
	private Texture muteSoundTexture, soundTexture, muteMusicTexture, musicTexture,
		asteroidTexture, rocketTexture, satelliteTexture, starTexture;
	
	private GameObject asteroid, rocket, satellite;
	private GameObject[] stars1, stars2;
	
	private Sound blip;
	
	private GameObject sound, music;
	private TextObject back;
	
	private boolean soundMute, musicMute;
	
	public OptionScene(SceneManager sm, AssetManager am, Font[] f) {		
		super(sm, am, f);
		muteSoundTexture = assetManager.get("gui/soundMute.png", Texture.class);
		soundTexture = assetManager.get("gui/sound.png", Texture.class);
		muteMusicTexture = assetManager.get("gui/mute.png", Texture.class);
		musicTexture = assetManager.get("gui/music.png", Texture.class);
		
		asteroidTexture = assetManager.get("textures/asteroid.png", Texture.class);
		rocketTexture = assetManager.get("textures/ships/rocket.png", Texture.class);
		satelliteTexture = assetManager.get("textures/satellite.png", Texture.class);
		starTexture = assetManager.get("textures/star.png", Texture.class);
		
		stars1 = new GameObject[20];
		stars2 = new GameObject[20];
		
		for(int i = 0; i < 20; i++) {
			GameObject star = new GameObject().setTexture(2f, starTexture);
			int x = ((int) ((Math.random() * (Game.WIDTH - star.getWidth())) / star.getWidth())) * (int) star.getWidth();
			int y = ((int) ((Math.random() * (Game.HEIGHT - star.getWidth())) / star.getWidth())) * (int) star.getWidth();
			star.setPosition(new Vector2(x, y));
			star.setRotation((float) Math.random() * 360f, (float) Math.random() * 180f - 90f);
			
			bgObjects.add(star);
			stars1[i] = star;
		}
		for(int i = 0; i < 20; i++) {
			GameObject star = new GameObject().setTexture(1f, starTexture);
			int x = ((int) ((Math.random() * (Game.WIDTH - star.getWidth())) / star.getWidth())) * (int) star.getWidth();
			int y = ((int) ((Math.random() * (Game.HEIGHT - star.getWidth())) / star.getWidth())) * (int) star.getWidth();
			star.setPosition(new Vector2(x, y));
			star.setRotation((float) Math.random() * 360f, (float) Math.random() * 180f - 90f);
			
			bgObjects.add(star);
			stars2[i] = star;
		}
		
		asteroid = new GameObject().setTexture(6f, asteroidTexture);
		asteroid.setPosition(new Vector2(16, Game.HEIGHT - 128));
		asteroid.setRotation(0, 45f);
		this.fgObjects.add(asteroid);
		
		rocket = new GameObject().setTexture(3f, rocketTexture);
		rocket.setPosition(new Vector2(256, 256));
		rocket.setRotation(66f, 0);
		this.fgObjects.add(rocket);
		
		satellite = new GameObject().setTexture(4f, satelliteTexture);
		satellite.setPosition(new Vector2(32, 128));
		satellite.setRotation(-196f, 0f);
		this.fgObjects.add(satellite);
		
		blip = assetManager.get("audio/blip.wav", Sound.class);
		
		sound = new GameObject().setTexture(2f, -1f, soundTexture, muteSoundTexture);
		sound.setPosition(new Vector2(128 + 16, 400));
		this.guiObjects.add(sound);
		
		music = new GameObject().setTexture(2f, -1f, musicTexture, muteMusicTexture);
		music.setPosition(new Vector2(128 + 64 + 16, 400));
		this.guiObjects.add(music);
		
		back = new TextObject("BACK", 1.5f, font[1]);
		back.setPosition(new Vector2(128 + 16, Game.HEIGHT - 256 - 32));
		
		this.guiObjects.add(back);
		
		TextObject text1 = new TextObject("OPTIONS", 2f, font[0]);
		text1.setPosition(new Vector2(0f, Game.HEIGHT - 160));
		text1.centerX();
		this.guiObjects.add(text1);

		TextObject text2 = new TextObject("SOUND", 2f, font[0]);
		text2.setPosition(new Vector2(0f, Game.HEIGHT - 192));
		text2.centerX();
		this.guiObjects.add(text2);
		
		TextObject text3 = new TextObject("CREDITS", 1f, font[0]);
		text3.setPosition(new Vector2(0f, 96));
		text3.centerX();
		this.guiObjects.add(text3);
		
		this.guiObjects.add(
				new TextObject("Powered by LibGDX", 1f, font[0])
				.setPosition(new Vector2(8, 40))
				);
		
		this.guiObjects.add(
				new TextObject("Music from Incompetech", 1f, font[0])
				.setPosition(new Vector2(8, 24))
				);
		
		this.guiObjects.add(
				new TextObject("Ceres Logiciel", 1f, font[0])
				.setPosition(new Vector2(8, 8))
				);
		
		soundMute = Gdx.app.getPreferences("AsteroidArcadePrefs").getBoolean("soundMute", false);
		musicMute = Gdx.app.getPreferences("AsteroidArcadePrefs").getBoolean("musicMute", false);
		
		if(soundMute) sound.changeFrame();
		if(musicMute) music.changeFrame();
	}
	
	@Override
	public void update(float dt) {
		super.update(dt);
		
		asteroid.getPosition().y += 0.25 * Math.sin(4 * timer);
		rocket.getPosition().y += 0.4 * Math.cos(3 * timer);
		satellite.getPosition().y += 0.1 * Math.cos(2 * timer);
		
		for(int i = 0; i < 20; i++) {
			stars1[i].getPosition().mulAdd(new Vector2(4f * (float) Math.sin(2 * timer + i), 0), dt);
			stars2[i].getPosition().mulAdd(new Vector2(4f * (float) Math.sin(2 * timer + i), 0), dt);
		}
	}
	
	@Override
	public void handleInput(float dt, Vector2 mouse, float roll) {
		if(Gdx.input.justTouched()) {			
			if(sound.isTouched(mouse)) {
				sound.changeFrame();
				soundMute = !soundMute;
				Game.masterVolume = soundMute ? 0f : 0.5f;
				
				blip.play(Game.masterVolume);
			}
			if(music.isTouched(mouse)) {
				music.changeFrame();
				musicMute = !musicMute;
				
				Game.musicVolume = musicMute ? 0f : 0.5f;
				sceneManager.musicHandler.update();
				
				blip.play(Game.masterVolume);
			}
			if(back.isTouched(mouse)) {
				back.changeFrame();
				Preferences prefs = Gdx.app.getPreferences("AsteroidArcadePrefs");
				prefs.putBoolean("soundMute", soundMute);
				prefs.putBoolean("musicMute", musicMute);
				prefs.flush();
				blip.play(Game.masterVolume);
				
				sceneManager.pop();
			}
		}
	}
}