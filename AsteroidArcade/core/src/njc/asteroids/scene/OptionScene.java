package njc.asteroids.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

import njc.asteroids.Game;
import njc.asteroids.managers.SceneManager;
import njc.asteroids.object.GameObject;
import njc.asteroids.object.TextObject;

public class OptionScene extends Scene {
	private Texture muteSoundTexture, soundTexture, muteMusicTexture, musicTexture,
		asteroidTexture, satelliteTexture, starTexture1, starTexture2,
		xTexture, mineTexture;
	
	private GameObject asteroid, mine, satellite;
	private GameObject[] stars1, stars2;
	
	private Sound blip;
	
	private GameObject sound, music, back;
	
	private boolean soundMute, musicMute;
	
	public OptionScene(SceneManager sm, AssetManager am, BitmapFont[] f) {		
		super(sm, am, f);
		muteSoundTexture = assetManager.get("gui/soundMute.png", Texture.class);
		soundTexture = assetManager.get("gui/sound.png", Texture.class);
		muteMusicTexture = assetManager.get("gui/mute.png", Texture.class);
		musicTexture = assetManager.get("gui/music.png", Texture.class);
		
		asteroidTexture = assetManager.get("textures/asteroid.png", Texture.class);
		mineTexture = assetManager.get("textures/mine.png", Texture.class);
		satelliteTexture = assetManager.get("textures/satellite.png", Texture.class);
		
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
		
		asteroid = new GameObject().setTexture(6f, asteroidTexture);
		asteroid.setPosition(new Vector2(16, Game.HEIGHT - 128));
		asteroid.setRotation(0, 45f);
		this.fgObjects.add(asteroid);
		
		mine = new GameObject().setTexture(4f, mineTexture);
		mine.setPosition(new Vector2(256, 256));
		mine.setRotation(0f, 30f);
		this.fgObjects.add(mine);
		
		satellite = new GameObject().setTexture(4f, satelliteTexture);
		satellite.setPosition(new Vector2(32, 128));
		satellite.setRotation(-196f, 0f);
		this.fgObjects.add(satellite);
		
		blip = assetManager.get("audio/blip.wav", Sound.class);
		
		sound = new GameObject().setTexture(2f, -1f, soundTexture, muteSoundTexture);
		sound.setPosition(new Vector2(Game.WIDTH / 2 - sound.getWidth() * 2, 400));
		this.guiObjects.add(sound);
		
		music = new GameObject().setTexture(2f, -1f, musicTexture, muteMusicTexture);
		music.setPosition(new Vector2(Game.WIDTH/2 + music.getWidth(), 400));
		this.guiObjects.add(music);
		
		back = new GameObject().setTexture(2f, xTexture);
		back.setPosition(new Vector2(Game.WIDTH - 40, Game.HEIGHT - 40));
		this.guiObjects.add(back);
		
		TextObject text1 = new TextObject("OPTIONS", 2f, font[0]);
		text1.setPosition(new Vector2(0f, Game.HEIGHT - 160));
		text1.centerX();
		this.guiObjects.add(text1);

		TextObject text2 = new TextObject("SOUND", 1f, font[0]);
		text2.setPosition(new Vector2(0f, Game.HEIGHT - 192));
		text2.centerX();
		this.guiObjects.add(text2);
		
		soundMute = Gdx.app.getPreferences("AsteroidArcadePrefs").getBoolean("soundMute", false);
		musicMute = Gdx.app.getPreferences("AsteroidArcadePrefs").getBoolean("musicMute", false);
		
		if(soundMute) sound.changeFrame();
		if(musicMute) music.changeFrame();
		
		this.guiObjects.add(
				new TextObject("ABOUT", 1f, font[0])
				.setPosition(new Vector2(0, 64))
				.centerX()
				);
		
		String text = ""
				+ "Music from INCOMPETECH.\n"
				+ "Uses LibGDX.\n";
		this.guiObjects.add(
				new TextObject(text, 1f, font[1])
				.setPosition(new Vector2(8, 8))
				);
	}
	
	@Override
	public void update(float dt) {
		super.update(dt);
		
		asteroid.getPosition().y += 0.25 * Math.sin(4 * timer);
		mine.getPosition().y += 0.4 * Math.cos(3 * timer);
		satellite.getPosition().y += 0.1 * Math.cos(2 * timer);
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
				setPreferences();
				blip.play(Game.masterVolume);
				
				sceneManager.pop();
			}
		}
	}
	
	private void setPreferences() {
		Preferences prefs = Gdx.app.getPreferences("AsteroidArcadePrefs");
		prefs.putBoolean("soundMute", soundMute);
		prefs.putBoolean("musicMute", musicMute);
		prefs.flush();
	}
}
