package io.itch.nickjconnors.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import io.itch.nickjconnors.AsteroidArcadeGame;
import io.itch.nickjconnors.managers.DataManager;
import io.itch.nickjconnors.managers.MusicHandler;
import io.itch.nickjconnors.managers.SceneManager;
import io.itch.nickjconnors.object.GameObject;
import io.itch.nickjconnors.object.TextObject;

public class MenuScene extends Scene {
	private GameObject player, arrowLeft, arrowRight;
	private TextObject playButton;
	private DataManager stats;
	
	private GameObject[] stars1, stars2;
	
	private Texture[][] playerTextures = new Texture[6][2];
	private String[] playerLabels = new String[6];
	
	private Texture starTexture1, starTexture2, asteroidTexture, asteroidTexture2, satelliteTexture, panelTexture, optionsTexture, 
		coinTexture, arrowLeftTexture, arrowRightTexture, unknownTexture;
	private Sound blip, bloop, unlock;
	
	private GameObject coin, asteroid1, asteroid2, asteroid3, satellite, panel, optionsButton, coin2;
	private TextObject shipLabel, bankTextObj;
	
	private int bank, selection;
	
	public MenuScene(SceneManager sm, AssetManager am, BitmapFont[] f) {
		super(sm, am, f);
		
		stats = new DataManager();
		
		bank = Gdx.app.getPreferences("AsteroidArcadePrefs").getInteger("bank", 0);
		selection = 0;
		
		playerTextures[0][0] = assetManager.get("textures/ships/rocket_0.png", Texture.class);
		playerTextures[0][1] = assetManager.get("textures/ships/rocket_1.png", Texture.class);
		
		playerTextures[1][0] = assetManager.get("textures/ships/shuttle_0.png", Texture.class);
		playerTextures[1][1] = assetManager.get("textures/ships/shuttle_1.png", Texture.class);
		
		playerTextures[2][0] = assetManager.get("textures/ships/eagle_0.png", Texture.class);
		playerTextures[2][1] = assetManager.get("textures/ships/eagle_1.png", Texture.class);
		
		playerTextures[3][0] = assetManager.get("textures/ships/whale_0.png", Texture.class);
		playerTextures[3][1] = assetManager.get("textures/ships/whale_1.png", Texture.class);
		
		playerTextures[4][0] = assetManager.get("textures/ships/ufo_0.png", Texture.class);
		playerTextures[4][1] = assetManager.get("textures/ships/ufo_1.png", Texture.class);
		
		playerTextures[5][0] = assetManager.get("textures/ships/rock_0.png", Texture.class);
		playerTextures[5][1] = assetManager.get("textures/ships/rock_1.png", Texture.class);
		
		playerLabels[0] = "Rocket";
		playerLabels[1] = "Orbiter";
		playerLabels[2] = "Eagle";
		playerLabels[3] = "Whale";
		playerLabels[4] = "Roswell";
		playerLabels[5] = "Asteroid";
		
		unknownTexture = assetManager.get("gui/unknown.png", Texture.class);
		
		starTexture1 = assetManager.get("textures/star.png", Texture.class);
		starTexture2 = assetManager.get("textures/star_1.png", Texture.class);
		asteroidTexture = assetManager.get("textures/asteroid.png", Texture.class);
		asteroidTexture2 = assetManager.get("textures/asteroid_1.png", Texture.class);
		satelliteTexture = assetManager.get("textures/satellite.png", Texture.class);
		panelTexture = assetManager.get("textures/panel.png", Texture.class);
		
		blip = assetManager.get("audio/blip.wav", Sound.class);
		bloop = assetManager.get("audio/bloop.wav", Sound.class);
		unlock = assetManager.get("audio/pickup1.wav", Sound.class);
		
		arrowLeftTexture = assetManager.get("gui/arrowLeft.png", Texture.class);
		arrowRightTexture = assetManager.get("gui/arrowRight.png", Texture.class);
		
		coinTexture = assetManager.get("textures/coin.png", Texture.class);
		coin = new GameObject().setTexture(2f, coinTexture);
		coin.setPosition(new Vector2(8, 24));
		guiObjects.add(coin);
		
		optionsTexture = assetManager.get("gui/optionsLarge.png", Texture.class);
		optionsButton = new GameObject().setTexture(2f, optionsTexture);
		optionsButton.setPosition(new Vector2(AsteroidArcadeGame.WIDTH - 88, 8));
		this.guiObjects.add(optionsButton);
		
		stars1 = new GameObject[20];
		stars2 = new GameObject[20];
		
		for(int i = 0; i < 20; i++) {
			GameObject star = new GameObject().setTexture(2f, (float) (Math.random() + 0.5f), starTexture1, starTexture2);
			int x = ((int) ((Math.random() * (AsteroidArcadeGame.WIDTH - star.getWidth())) / star.getWidth())) * (int) star.getWidth();
			int y = ((int) ((Math.random() * (AsteroidArcadeGame.HEIGHT - star.getWidth())) / star.getWidth())) * (int) star.getWidth();
			star.setPosition(new Vector2(x, y));

			bgObjects.add(star);
			stars1[i] = star;
		}
		for(int i = 0; i < 20; i++) {
			GameObject star = new GameObject().setTexture(1f, (float) (Math.random() + 0.5f), starTexture1, starTexture2);
			int x = ((int) ((Math.random() * (AsteroidArcadeGame.WIDTH - star.getWidth())) / star.getWidth())) * (int) star.getWidth();
			int y = ((int) ((Math.random() * (AsteroidArcadeGame.HEIGHT - star.getWidth())) / star.getWidth())) * (int) star.getWidth();
			star.setPosition(new Vector2(x, y));

			bgObjects.add(star);
			stars2[i] = star;
		}
		asteroid1 = new GameObject().setTexture(4f, asteroidTexture2);
		asteroid1.setPosition(new Vector2(48f, 200f));
		asteroid1.setRotation(0f, 45f);
		fgObjects.add(asteroid1);
		
		asteroid2 = new GameObject().setTexture(5f, asteroidTexture);
		asteroid2.setPosition(new Vector2(256f, 128f));
		asteroid2.setRotation(0f, -24f);
		fgObjects.add(asteroid2);
		
		asteroid3 = new GameObject().setTexture(3f, asteroidTexture);
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
		arrowLeft.setPosition(new Vector2(40f, (AsteroidArcadeGame.HEIGHT - arrowLeft.getHeight()) / 2f));
		guiObjects.add(arrowLeft);
		
		arrowRight = new GameObject().setTexture(4f, arrowRightTexture);
		arrowRight.setPosition(new Vector2(256f + 8f, (AsteroidArcadeGame.HEIGHT - arrowRight.getHeight()) / 2f));
		guiObjects.add(arrowRight);
		
		player = new GameObject().setTexture(5f, 0.5f, playerTextures[selection]);
		player.setPosition(new Vector2(
				(AsteroidArcadeGame.WIDTH - player.getWidth()) / 2f,
				(AsteroidArcadeGame.HEIGHT - player.getHeight()) / 2f
				));
		guiObjects.add(player);
		
		playButton = new TextObject("Tap to play", 1f, font[1]);
		playButton.setPosition(new Vector2(0, 128));
		playButton.centerX();
		guiObjects.add(playButton);
		
		//TextObjects
		guiObjects.add(
				new TextObject("ASTEROID ARCADE", 1.4f, font[0])
				.setPosition(new Vector2(0, AsteroidArcadeGame.HEIGHT - 64))
				.centerX());
		bankTextObj = new TextObject(bank + "", 1.6f, font[0]);
		guiObjects.add(
				bankTextObj
				.setPosition(new Vector2(48, 32))
				);
		
		shipLabel = new TextObject(playerLabels[0].toUpperCase(), 1f, font[0]);
		shipLabel.setPosition(new Vector2(0f, 180f));
		shipLabel.centerX();
		guiObjects.add(shipLabel);
		
		coin2 = new GameObject().setTexture(1f, coinTexture);
		coin2.setVisibility(false);
		guiObjects.add(coin2);
		
		//Start music
		sceneManager.musicHandler.play(MusicHandler.ARCADIA);
	}

	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
	}

	@Override
	public void update(float dt) {
		super.update(dt);
		
		player.getPosition().y = (-player.getHeight() + AsteroidArcadeGame.HEIGHT) / 2f + 16f * (float) Math.cos(2f * timer);
		player.update(dt);
		
		asteroid1.getPosition().y = (200 + 3f * (float) Math.cos(timer));
		asteroid2.getPosition().y = (128 + 4f * (float) Math.cos(1.5f * timer));
		asteroid3.getPosition().y = (64 + 3f * (float) Math.cos(0.5f * timer));
		satellite.getPosition().y = (432 + 2f * (float) Math.sin(timer));
		panel.getPosition().y = (444 + 8f * (float) Math.sin(1.1f * timer));
	}

	@Override
	public void handleInput(float dt, Vector2 mouse, float roll) {
		if(Gdx.input.justTouched()) {					
			if(optionsButton.isTouched(mouse)) {
				blip.play(AsteroidArcadeGame.masterVolume);
				sceneManager.push(new OptionScene(sceneManager, assetManager, font));
			}
			
			if(arrowLeft.isTouched(mouse) || arrowRight.isTouched(mouse)) {
				blip.play(AsteroidArcadeGame.masterVolume);
				
				if(arrowLeft.isTouched(mouse)) {
					selection--;
					if(selection < 0) selection = playerTextures.length - 1;
				}
				if(arrowRight.isTouched(mouse)) {
					selection++;
					if(selection > playerTextures.length - 1) selection = 0;
				}
				
				if(stats.unlocks[selection]) {
					player.setTexture(5f, 0.5f, playerTextures[selection]);
					shipLabel.setMsg(playerLabels[selection].toUpperCase());
					shipLabel.centerX();
					coin2.setVisibility(false);
				}
				else {
					player.setTexture(5f, unknownTexture);
					shipLabel.setMsg(stats.prices[selection] + "");
					shipLabel.centerX();
					coin2.setVisibility(true);
					coin2.setPosition(new Vector2((AsteroidArcadeGame.WIDTH - shipLabel.getWidth()) / 2 - 18, 178f));
				}
				player.centerX();
			}
			
			if(player.isTouched(mouse) || playButton.isTouched(mouse)) {
				if(!stats.unlocks[selection]) {
					if(bank < stats.prices[selection]) bloop.play(AsteroidArcadeGame.masterVolume * 2f);
					else {
						bank -= stats.prices[selection];
						player.setTexture(5f, 0.5f, playerTextures[selection]);
						shipLabel.setMsg(playerLabels[selection].toUpperCase());
						shipLabel.centerX();
						coin2.setVisibility(false);
						
						stats.unlocks[selection] = true;
						
						Preferences prefs = Gdx.app.getPreferences("AsteroidArcadePrefs");
						prefs.putBoolean("unlock" + selection, true);
						prefs.putInteger("bank", bank);
						prefs.flush();
						
						bankTextObj.setMsg("" + bank);
						unlock.play(AsteroidArcadeGame.masterVolume);
					}
				}
				else {
					blip.play(AsteroidArcadeGame.masterVolume);
					sceneManager.musicHandler.stop();
					sceneManager.push(new PlayScene(sceneManager, assetManager, font, selection));
				}
			}
		}
	}
}
