package njc.asteroids.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import njc.asteroids.Game;
import njc.asteroids.graphics.Font;
import njc.asteroids.managers.DataManager;
import njc.asteroids.managers.MusicHandler;
import njc.asteroids.managers.SceneManager;
import njc.asteroids.object.GameObject;
import njc.asteroids.object.TextObject;

public class MenuScene extends Scene {
	private GameObject player, arrowLeft, arrowRight;
	private TextObject playButton;
	private DataManager stats;
	
	private GameObject[] stars1, stars2;
	
	private Texture[] playerTextures = new Texture[5];
	private String[] playerLabels = new String[5];
	
	private Texture starTexture, asteroidTexture, satelliteTexture, panelTexture, optionsTexture, 
		coinTexture, arrowLeftTexture, arrowRightTexture, unknownTexture;
	private Sound blip, bloop, unlock;
	
	private GameObject coin, asteroid1, asteroid2, asteroid3, satellite, panel, optionsButton;
	private TextObject shipLabel, bankTextObj;
	
	private int bank, selection;
	
	public MenuScene(SceneManager sm, AssetManager am, Font[] f) {
		super(sm, am, f);
		
		stats = new DataManager();
		
		bank = Gdx.app.getPreferences("AsteroidArcadePrefs").getInteger("bank", 0);
		selection = 0;
		
		playerTextures[0] = assetManager.get("textures/ships/rocket.png", Texture.class);
		playerTextures[1] = assetManager.get("textures/ships/shuttle.png", Texture.class);
		playerTextures[2] = assetManager.get("textures/ships/eagle.png", Texture.class);
		//playerTextures[3] = assetManager.get("textures/ships/laser_ship.png", Texture.class);
		playerTextures[3] = assetManager.get("textures/ships/whale_1.png", Texture.class);
		playerTextures[4] = assetManager.get("textures/ships/ufo.png", Texture.class);
		
		playerLabels[0] = "Rocket";
		playerLabels[1] = "Orbiter";
		playerLabels[2] = "Eagle";
		//playerLabels[3] = "Laser Ship";
		playerLabels[3] = "Doby Mick";
		playerLabels[4] = "Roswell";
		
		unknownTexture = assetManager.get("gui/unknown.png", Texture.class);
		
		starTexture = assetManager.get("textures/star.png", Texture.class);
		asteroidTexture = assetManager.get("textures/asteroid.png", Texture.class);
		satelliteTexture = assetManager.get("textures/satellite.png", Texture.class);
		panelTexture = assetManager.get("textures/panel.png", Texture.class);
		
		blip = assetManager.get("audio/blip.wav", Sound.class);
		bloop = assetManager.get("audio/bloop.wav", Sound.class);
		unlock = assetManager.get("audio/pickup1.wav", Sound.class);
		
		arrowLeftTexture = assetManager.get("gui/arrowLeft.png", Texture.class);
		arrowRightTexture = assetManager.get("gui/arrowRight.png", Texture.class);
		
		coinTexture = assetManager.get("textures/coin.png", Texture.class);
		coin = new GameObject().setTexture(2f, coinTexture);
		coin.setPosition(new Vector2(8, 16));
		guiObjects.add(coin);
		
		optionsTexture = assetManager.get("gui/optionsLarge.png", Texture.class);
		optionsButton = new GameObject().setTexture(1.5f, optionsTexture);
		optionsButton.setPosition(new Vector2(Game.WIDTH - 64 + 8, 8));
		this.guiObjects.add(optionsButton);
		
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
		asteroid1 = new GameObject().setTexture(4f, asteroidTexture);
		asteroid1.setPosition(new Vector2(48f, 200f));
		asteroid1.setRotation(0f, 45f);
		fgObjects.add(asteroid1);
		
		asteroid2 = new GameObject().setTexture(5f, asteroidTexture);
		asteroid2.setPosition(new Vector2(256f, 128f));
		asteroid2.setRotation(0f, -24f);
		fgObjects.add(asteroid2);
		
		asteroid3 = new GameObject().setTexture(2f, asteroidTexture);
		asteroid3.setPosition(new Vector2(200f, 64f));
		asteroid3.setRotation(0f, -24f);
		fgObjects.add(asteroid3);
		
		panel = new GameObject().setTexture(3f, panelTexture);
		panel.setPosition(new Vector2(256f, 444f));
		panel.setRotation(0f, 12f);
		fgObjects.add(panel);
		
		satellite = new GameObject().setTexture(3f, satelliteTexture);
		satellite.setPosition(new Vector2(32f, 432f));
		satellite.setRotation(66f, 0f);
		fgObjects.add(satellite);
		
		//Ship selection
		arrowLeft = new GameObject().setTexture(4f, arrowLeftTexture);
		arrowLeft.setPosition(new Vector2(40f, Game.HEIGHT / 2f));
		guiObjects.add(arrowLeft);
		
		arrowRight = new GameObject().setTexture(4f, arrowRightTexture);
		arrowRight.setPosition(new Vector2(256f + 8f, Game.HEIGHT / 2f));
		guiObjects.add(arrowRight);
		
		player = new GameObject().setTexture(5f, playerTextures[selection]);
		player.setPosition(new Vector2(
				(Game.WIDTH - player.getWidth()) / 2f,
				(Game.HEIGHT - player.getHeight()) / 2f
				));
		guiObjects.add(player);
		
		playButton = new TextObject("Tap to play", 1f, font[1]);
		playButton.setPosition(new Vector2(96, 128));
		guiObjects.add(playButton);
		
		//TextObjects
		guiObjects.add(
				new TextObject("ASTEROID ARCADE", 1.5f, font[0])
				.setPosition(new Vector2(0, Game.HEIGHT - 64))
				.centerX());
		bankTextObj = new TextObject(bank + "", 1.5f, font[0]);
		guiObjects.add(
				bankTextObj
				.setPosition(new Vector2(48, 20))
				);
		
		shipLabel = new TextObject(playerLabels[0], 1f, font[0]);
		shipLabel.setPosition(new Vector2(0f, 200f));
		shipLabel.centerX();

		guiObjects.add(shipLabel);
		
		//Start music
		sceneManager.musicHandler.play(MusicHandler.ARCADIA);
	}

	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		
		if(!stats.unlocks[selection]) {
			batch.draw(coinTexture, 104, 240, 24f, 24f);
			font[0].write(batch, "" + stats.prices[selection], 104 + 40, 240, 1.5f);
		}
	}

	@Override
	public void update(float dt) {
		super.update(dt);
		
		player.getPosition().mulAdd(new Vector2(0, 32f * (float) Math.sin(3 * timer)), dt);
		
		for(int i = 0; i < 20; i++) {
			stars1[i].getPosition().mulAdd(new Vector2(4f * (float) Math.sin(2 * timer + i), 0), dt);
			stars2[i].getPosition().mulAdd(new Vector2(4f * (float) Math.sin(2 * timer + i), 0), dt);
		}
		
		asteroid1.getPosition().mulAdd(new Vector2(0, 6f * (float) Math.cos(2 * timer)), dt);
		asteroid2.getPosition().mulAdd(new Vector2(0, 8f * (float) Math.cos(3 * timer)), dt);
		asteroid3.getPosition().mulAdd(new Vector2(0, 6f * (float) Math.cos(1 * timer)), dt);
		satellite.getPosition().mulAdd(new Vector2(0, 4f * (float) Math.sin(2 * timer)), dt);
		panel.getPosition().mulAdd(new Vector2(0, 16f * (float) Math.sin(2 * timer)), dt);
	}

	@Override
	public void handleInput(float dt, Vector2 mouse, float roll) {
		if(Gdx.input.justTouched()) {					
			if(optionsButton.isTouched(mouse)) {
				blip.play(Game.masterVolume);
				sceneManager.push(new OptionScene(sceneManager, assetManager, font));
			}
			
			if(arrowLeft.isTouched(mouse) || arrowRight.isTouched(mouse)) {
				blip.play(Game.masterVolume);
				
				if(arrowLeft.isTouched(mouse)) {
					selection--;
					if(selection < 0) selection = playerTextures.length - 1;
				}
				if(arrowRight.isTouched(mouse)) {
					selection++;
					if(selection > playerTextures.length - 1) selection = 0;
				}
				
				if(stats.unlocks[selection]) {
					player.setTexture(5f, playerTextures[selection]);
					shipLabel.setMsg(playerLabels[selection]);
					shipLabel.centerX();
					shipLabel.setVisibility(true);
				}
				else {
					player.setTexture(5f, unknownTexture);
					shipLabel.setVisibility(false);
				}
				player.setPosition(new Vector2(
						(Game.WIDTH - player.getWidth()) / 2f,
						(Game.HEIGHT - player.getHeight()) / 2f
						));
			}
			
			if(player.isTouched(mouse) || playButton.isTouched(mouse)) {
				if(!stats.unlocks[selection]) {
					if(bank < stats.prices[selection]) bloop.play(Game.masterVolume * 2f);
					else {
						bank -= stats.prices[selection];
						player.setTexture(5f, playerTextures[selection]);
						
						stats.unlocks[selection] = true;
						
						Preferences prefs = Gdx.app.getPreferences("AsteroidArcadePrefs");
						prefs.putBoolean("unlock" + selection, true);
						prefs.putInteger("bank", bank);
						prefs.flush();
						
						bankTextObj.setMsg("" + bank);
						unlock.play(Game.masterVolume);
					}
				}
				else {
					blip.play(Game.masterVolume);
					sceneManager.musicHandler.stop();
					sceneManager.push(new PlayScene(sceneManager, assetManager, font, selection));
				}
			}
		}
	}
}
