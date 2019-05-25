package njc.asteroids.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import njc.asteroids.Game;
import njc.asteroids.managers.MusicHandler;
import njc.asteroids.managers.SceneManager;
import njc.asteroids.object.GameObject;
import njc.asteroids.object.Particle;
import njc.asteroids.object.PowerUp;
import njc.asteroids.object.TextObject;
import njc.asteroids.object.entities.Attractor;
import njc.asteroids.object.entities.BossEntity;
import njc.asteroids.object.entities.Entity;
import njc.asteroids.object.entities.SwarmEntity;
import njc.asteroids.object.entities.Entity.Team;
import njc.asteroids.object.entities.player.Player;
import njc.asteroids.object.entities.player.Weapon;

public class PlayScene extends Scene {
	boolean debug = false;
	
	Player player;
	Entity boss;
	Weapon bossWeapon;

	// Gui elements
	private GameObject pauseButton, optionButton, quitButton;
	private TextObject pauseText, levelText, timerText, scoreText;

	private Sound[] explosions = new Sound[3];
	private Sound[] lasers = new Sound[3];
	private Sound[] pickups = new Sound[3];

	private Sound blip;
	private Sound death;
	// private Music music;	

	//Player textures
	private Texture[] rocketTextures = {
			assetManager.get("textures/ships/rocket_0.png", Texture.class),
			assetManager.get("textures/ships/rocket_1.png", Texture.class)
	};
	
	private Texture[] shuttleTextures = {
			assetManager.get("textures/ships/shuttle_0.png", Texture.class),
			assetManager.get("textures/ships/shuttle_1.png", Texture.class)
	};
	
	private Texture[] ufoTextures = {
			assetManager.get("textures/ships/ufo_0.png", Texture.class),
			assetManager.get("textures/ships/ufo_1.png", Texture.class)
	};
	
	private Texture[] whaleTextures = {
			assetManager.get("textures/ships/whale_0.png", Texture.class),
			assetManager.get("textures/ships/whale_1.png", Texture.class)
	};
	
	private Texture[] eagleTextures = {
			assetManager.get("textures/ships/eagle_0.png", Texture.class),
			assetManager.get("textures/ships/eagle_1.png", Texture.class)
	};
	
	private Texture[] rockTextures = {
			assetManager.get("textures/ships/rock_0.png", Texture.class),
			assetManager.get("textures/ships/rock_1.png", Texture.class)
	};
	
	//Weapon textures
	private Texture laserTexture = assetManager.get("textures/weapons/laser.png", Texture.class);
	private Texture photonTexture = assetManager.get("textures/weapons/photon.png", Texture.class);
	private Texture missileTexture = assetManager.get("textures/weapons/missile.png", Texture.class);
	private Texture[] flakTextures = {
			assetManager.get("textures/weapons/flak_0.png", Texture.class),
			assetManager.get("textures/weapons/flak_1.png", Texture.class)
	};
	
	//Stuff
	private Texture[] starTextures = {
			assetManager.get("textures/star.png", Texture.class),
			assetManager.get("textures/star_1.png", Texture.class)
	};
	
	private Texture coinTexture = assetManager.get("textures/coin.png", Texture.class);
	private Texture[] asteroidTextures = {
			assetManager.get("textures/asteroid.png", Texture.class),
			assetManager.get("textures/asteroid_1.png", Texture.class)
	};
	
	private Texture shieldTexture = assetManager.get("textures/shield.png", Texture.class);
	private Texture	explosionTexture = assetManager.get("textures/boom.png", Texture.class);
	
	private Texture wormholeTexture = assetManager.get("textures/wormhole.png", Texture.class);
	
	//PowerUps
	private Texture[] atomTextures = {
			assetManager.get("textures/atom_0.png", Texture.class),
			assetManager.get("textures/atom_1.png", Texture.class),
			assetManager.get("textures/atom_2.png", Texture.class),
			assetManager.get("textures/atom_3.png", Texture.class),
			assetManager.get("textures/atom_4.png", Texture.class),
	};
	
	private Texture superstarTexture = assetManager.get("textures/superstar.png", Texture.class);
	private Texture tripleshotTexture = assetManager.get("textures/tripleshot.png", Texture.class);
	private Texture[] magnetTextures = {
			assetManager.get("textures/magnet_0.png", Texture.class),
			assetManager.get("textures/magnet_1.png", Texture.class)
	};
	
	//Debris
	private Texture junkTexture = assetManager.get("textures/junk.png", Texture.class);
	private Texture panelTexture = assetManager.get("textures/panel.png", Texture.class);
	private Texture dishTexture = assetManager.get("textures/dish.png", Texture.class);
	private Texture satelliteTexture = assetManager.get("textures/satellite.png", Texture.class);
	
	//Mine
	private Texture mineTexture = assetManager.get("textures/mine.png", Texture.class);

	//Trails
	private Texture smokeTexture = assetManager.get("textures/smoke.png", Texture.class);
	private Texture fireTexture = assetManager.get("textures/smallflame.png", Texture.class);
	private Texture startrailTexture = assetManager.get("textures/startrail.png", Texture.class);
	private Texture orangetrailTexture = assetManager.get("textures/orangetrail.png", Texture.class);

	// GUI
	private Texture heartTexture = assetManager.get("textures/heart.png", Texture.class);
	private Texture halfheartTexture = assetManager.get("textures/halfheart.png", Texture.class);
	private Texture shieldheartTexture = assetManager.get("textures/shieldheart.png", Texture.class);
	private Texture energyTexture = assetManager.get("textures/energy.png", Texture.class);

	private Texture pauseTexture = assetManager.get("gui/pause.png", Texture.class);
	private Texture playTexture = assetManager.get("gui/play.png", Texture.class);
	private Texture optionTexture = assetManager.get("gui/options.png", Texture.class);
	
	//Bosses
	private Texture laserBossTexture = assetManager.get("textures/ships/boss_laser.png", Texture.class);
	private Texture laserBossSpikesTexture = assetManager.get("textures/ships/boss_laser_spikes.png", Texture.class);
	
	private Texture ufoBossTexture = assetManager.get("textures/ships/boss_ufo.png", Texture.class);
	private Texture ufoBossSpikesTexture = assetManager.get("textures/ships/boss_ufo_spikes.png", Texture.class);
	
	private Texture turretBossTexture = assetManager.get("textures/ships/boss_turret.png", Texture.class);
	
	private Texture[] ufoSwarmTextures = {
			assetManager.get("textures/ships/swarm_ufo_0.png", Texture.class),
			assetManager.get("textures/ships/swarm_ufo_1.png", Texture.class)
	};
	
	private int highScore = Gdx.app.getPreferences("AsteroidArcadePrefs").getInteger("highscore", 0);
	private int bestTime = Gdx.app.getPreferences("AsteroidArcadePrefs").getInteger("besttime", 0);
	
	private boolean paused = false;
	private boolean gameOver = false;

	private Level level = Level.PAUSE;
	
	private boolean inverted = false;
	private float milestone = 3f;
	private float swarmSpawn = 0f;

	public PlayScene(SceneManager sm, AssetManager am, BitmapFont[] f, int selection) {
		super(sm, am, f);

		player = new Player();

		// Sounds
		for (int i = 1; i < 4; i++) {
			explosions[i - 1] = assetManager.get("audio/explosion" + i + ".wav", Sound.class);
			lasers[i - 1] = assetManager.get("audio/laser" + i + ".wav", Sound.class);
			pickups[i - 1] = assetManager.get("audio/pickup" + i + ".wav", Sound.class);
		}
		blip = assetManager.get("audio/blip.wav", Sound.class);
		death = assetManager.get("audio/death.wav", Sound.class);

		// music = assetManager.get("audio/music/Salty_Ditty.wav", Music.class);

		//Buttons
		pauseButton = new GameObject().setTexture(2f, -1f, pauseTexture, playTexture);
		pauseButton.setPosition(new Vector2(Game.WIDTH - 40, Game.HEIGHT - 32));

		quitButton = new TextObject("MAIN MENU", 1.5f, font[1]);
		quitButton.setPosition(new Vector2(0, 400));
		quitButton.centerX();

		optionButton = new GameObject().setTexture(2f, optionTexture);
		optionButton.setPosition(new Vector2(Game.WIDTH - 40, Game.HEIGHT - 64));

		GameObject coin = new GameObject().setTexture(1.5f, coinTexture);
		coin.setPosition(new Vector2(8, Game.HEIGHT - 80));
		guiObjects.add(coin);

		newRandomStars();

		switch (selection) {
		case 0:
			player.setTexture(2f, 0.5f, rocketTextures);
			player.setHealth(14);			
			player.setSpeed(1.25f);
			player.setWeapon(Weapon.Type.BURST_LASER, 7f, laserTexture);
			player.setEmitsSmoke(true);
			
			break;
		case 1:
			player.setTexture(2f, 0.5f, shuttleTextures);
			player.setHealth(20);
			player.setWeapon(Weapon.Type.MISSILE, 0f, missileTexture);
			player.setEmitsSmoke(true);
			
			break;
		case 2:
			player.setTexture(2f, 0.5f, eagleTextures);
			player.setHealth(14);
			player.setWeapon(Weapon.Type.LASER, 7f, laserTexture);
			player.setEmitsSmoke(true);
			
			break;
		case 3:
			player.setTexture(2f, 0.5f, whaleTextures);
			player.setHealth(10);
			player.addRegen();
			player.setWeapon(Weapon.Type.PHOTON, 5f, photonTexture);

			break;
		case 4:
			player.setTexture(2f, 0.5f, ufoTextures);
			player.setHealth(12);
			player.setSpeed(1.25f);
			player.setWeapon(Weapon.Type.PHOTON, 5f, photonTexture);
			player.setEmitsSmoke(true);
			
			//Shield
			fgObjects.add(player.addShield(6, shieldTexture, 4.5f));
			break;
		case 5:
			player.setTexture(2f, 0.5f, rockTextures);
			player.setHealth(10);
			player.setWeapon(Weapon.Type.FLAK, 0f, flakTextures);
			player.setEmitsSmoke(true);
			
			//Shield
			fgObjects.add(player.addShield(5, shieldTexture, 4.5f));
		}
		player.setPosition(new Vector2((Game.WIDTH - player.getWidth()) / 2f, Game.HEIGHT / 5));
		entities.add(player);

		sceneManager.musicHandler.play(MusicHandler.SALTY_DITTY);
		Attractor a = new Attractor(player, 0.5f);
		a.onlyPowerups = true;
		entities.add(a);
		
		pauseText = new TextObject("PAUSED", 2f, font[0]);
		pauseText.setPosition(new Vector2(0, Game.HEIGHT - 128));
		pauseText.centerX();
		pauseText.setVisibility(false);
		this.guiObjects.add(pauseText);
		
		levelText = new TextObject("", 1f, font[0]);
		levelText.setPosition(new Vector2(8, Game.HEIGHT - 24));
		this.guiObjects.add(levelText);
		
		timerText = new TextObject("", 1f, font[0]);
		timerText.setPosition(new Vector2(8, Game.HEIGHT - 48));
		this.guiObjects.add(timerText);
		
		scoreText = new TextObject("", 1.4f, font[0]);
		scoreText.setPosition(new Vector2(40, Game.HEIGHT - 75));
		this.guiObjects.add(scoreText);
	}

	@Override
	public void update(float dt) {
		if (!paused) {
			//Atom bomb (do first to ensure everything has time to die)
			if(player.atomBomb) {
				fade(1f, 0.7f, 0.6f);
				player.atomBomb = false;
			}
			
			super.update(dt);

			// SPAWNS
			///////////////////////////////////////////////////////////////////////
			// Spawning stars
			if (Math.random() < 2 * dt) {
				newStar(2f, false);
			}
			if (Math.random() < 2 * dt) {
				newStar(1.5f, false);
			}
			if (Math.random() < 2 * dt) {
				newStar(1f, false);
			}

			// Spawning powerups
			if(!level.isBoss() && level != Level.WORMHOLE) {
				spawnPowerUps(dt);
			}

			//Trails
			if(player.getHealth() > 0) {
				if(player.superMode && Math.random() < dt * 2f) addTrail(player, startrailTexture, 4f);
				if(player.tripleShot && Math.random() < dt * 2f) addTrail(player, orangetrailTexture, 4f);
			}
			
			// Spawning asteroids
			switch (level) {
			case ASTEROIDS:
				if (Math.random() < 3f * spawnChance(dt)) newAsteroid();
				break;
			case DEBRIS:
				if (Math.random() < 3f * spawnChance(dt)) newDebris();
				break;
			case MINES:
				if (Math.random() < 1f * spawnChance(dt)) newMine();
				break;
			case SWARM:
				if(timer > swarmSpawn) {
					newSwarm();
					swarmSpawn = timer + 0.9f;
				}
				if(Math.random() < 0.5f * spawnChance(dt)) newDebris();
				break;
			case WORMHOLE:
				break;
			default:
				if(Math.random() < 0.5f * spawnChance(dt)) newAsteroid();
				break;
			}

			if (level.isBoss()) {
				// Spawn
				if (boss == null) {
					Texture t, wt;
					switch(level) {
						case BOSS_UFO:
							t = ufoBossTexture;
							wt = photonTexture;
							break;
						case BOSS_TURRET:
							t = turretBossTexture;
							wt = missileTexture;
							break;
						case BOSS_LASER:
							t = laserBossTexture;
							wt = laserTexture;
							break;
						default:
							t = ufoTextures[0];
							wt = laserTexture;
							break;		
					}
					boss = new BossEntity(t, wt, level, entities, lasers, player);
					boss.setEmitsSmoke(true);
					entities.add(boss);
					if(level == Level.BOSS_UFO)
						objects.add(((BossEntity) boss).addChild(new Vector2(-2,2), true, 2f, ufoBossSpikesTexture));
					if(level == Level.BOSS_LASER)
						objects.add(((BossEntity) boss).addChild(new Vector2(0, -32), false, 2f, laserBossSpikesTexture));
				}
				
				// Spawn coins after boss
				if (boss.isMarkedForRemoval()) {
					for (int i = 0; i < 8; i++)
						newPowerUp(PowerUp.Type.COIN);
					boss = null;
					bossWeapon = null;
					level = Level.PAUSE;
					milestone = timer + 5;
				}
			} 
			
			// Update level
			if(!level.isBoss() && timer > milestone) {
				//Update level
				// Invert after wormhole
				if(level == Level.WORMHOLE) {
					invert();
					level = Level.ASTEROIDS;
					milestone = timer + 15;
				} else if (level == Level.PAUSE){
					if(timer > 60){
						double chance = Math.random();
						if(chance > 0.6) {
							// Random boss level
							level = Level.randomBoss();
							//Timer only really effects alien swarm
							milestone = timer + 10;
						} else if(chance > 0.5) {
							level = Level.WORMHOLE;
							newWormhole();
							// Takes 5 seconds to reach wormhole
							milestone = timer + 5;
						} else {
							level = Level.random();
							milestone = timer + 15;
						}
					} else {
						level = Level.random();
						milestone = timer + 15;
					}
				} else {
					level = Level.PAUSE;
					milestone = timer + 3;
					// Go back to normal after inverting
					if(inverted) invert();
				}
			}
						
			//SuperMode powerup
			if(player.superMode) {
				player.getWeapon().setEnergy(player.getWeapon().getMaxEnergy());
			}

			//Do once when the player dies
			if (!player.isMarkedForRemoval() && player.getHealth() <= 0) {
				player.setMarkedForRemoval(true);
				death.play(Game.masterVolume);
				sceneManager.musicHandler.stop();
				gameOver = true;
				
				this.pauseText.setVisibility(true);
				this.pauseText.setMsg("GAME OVER", 1.4f);
				this.pauseText.centerX();
				
				String s;
				TextObject o;
				
				s = "High Score: " + (player.score > highScore ? player.score + " NEW" : highScore);
				o = new TextObject(s, 1f, font[0]);
				o.setPosition(new Vector2(0, Game.HEIGHT - 288));
				o.centerX();
				guiObjects.add(o);
				
				s = "Best time: " + (player.timer > bestTime ? formatTime(player.timer) + " NEW" : formatTime(bestTime));
				o = new TextObject(s, 1f, font[0]);
				o.setPosition(new Vector2(0, Game.HEIGHT - 336));
				o.centerX();
				guiObjects.add(o);			
			}
		}
	}
	
	@Override
	public void updateEntity(float dt, int i) {
		Entity e = this.entities.get(i);
		
		//Explosion Powerup (Kill everything)
		if(player.atomBomb) {
			if(!(e instanceof Player || e instanceof PowerUp || e.getTeam() == Team.WORMHOLE)) {
				e.setHealth(0);
			}
		}
		
		float smokeChance = e.isSmoking();
		float flameChance = e.isFlaming();

		if(smokeChance != -1 && Math.random() < dt * smokeChance) addTrail(e, smokeTexture, e.getWidth() / 16f);
		if(flameChance != -1 && Math.random() < dt * flameChance) addTrail(e, fireTexture, e.getWidth() / 16f);
	

		// Collision check
		for(int j = i + 1; j < entities.size(); j++) {
			Entity e2 = entities.get(j);
			if(e == e2) continue;
			
			if(e.isMarkedForRemoval())
				break;
			if(e2.isMarkedForRemoval())
				continue;
			
			//Ignore same team col for GOOD and ENEMY
			if(e.getTeam() == e2.getTeam() && (e.getTeam() == Team.GOOD || e.getTeam() == Team.ENEMY))
				continue;
			if(e instanceof Attractor && e2 instanceof Attractor)
				continue;
			
			//Ignore powerup-powerup collision
			if(e.getTeam() == e2.getTeam() && e.getTeam() == Team.POWERUP)
				continue;
			
			// Attractor
			if(e instanceof Attractor || e2 instanceof Attractor) {
				Attractor a;
				Entity e3;
				if(e instanceof Attractor) {
					a = (Attractor) e;
					e3 = e2;
				} else {
					a = (Attractor) e2;
					e3 = e;
				}
				
				//Magnet powerup
				if(a.getParent() == player && !((Player) a.getParent()).magnetMode)
					continue;
				if(a.getParent() != e3 && !(a.onlyPowerups && !(e3 instanceof PowerUp))) {
					Vector2 attraction = a.getAttractionAcc(e3).scl(dt);
					if(e3 == player) {
						// Ignore y acceleration for player, rly suck in player tho
						e3.getVelocity().x += attraction.x * 3;
					} else e3.getVelocity().add(attraction);
				}
				continue;
			}
						
			// Collision
			if(e.collisionCheck(e2)) {
				// Powerups

				if(e instanceof PowerUp) {
					if(e2 == player) {
						((PowerUp) e).onCollide(e2);
						break;
					} else continue;
				} else if (e2 instanceof PowerUp) {
					if(e == player) {
						((PowerUp) e2).onCollide(e);
						break;
					} else continue;
				}
				
				if(e.getTeam() == Team.WORMHOLE) {
					if(e2 != player) e2.damage();
					continue;
				} else if (e2.getTeam() == Team.WORMHOLE) {
					if(e != player) e.damage();
					continue;
				}
				
				// Default behaviour
				e.damage();
				e2.damage();
			}
			
		}
		
		// Explode things
		if (e.isMarkedForRemoval()) {
			if (!(e instanceof PowerUp)) {
				if (e.explodeOnDeath) {
					explodeOnDeath(e);
				} else {
					newExplosion(e);
				}
			}
			else if (e.isOnScreen())
				pickups[(int) (Math.random() * 3)].play(Game.masterVolume);
		}
		
		levelText.setMsg("" + level.toString());
		timerText.setMsg("TIME: " + formatTime(player.timer));
		scoreText.setMsg("" + player.score);
	}

	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		if(debug) {
			String s = "e : " + entities.size() + "\n"
				+ "bg: " + bgObjects.size() + "\n"
				+ "o : " + objects.size() + "\n"
				+ "fg: " + fgObjects.size() + "\n"
				+ "fps: " + Gdx.graphics.getFramesPerSecond();
			
			font[1].draw(batch, s, 20, 360);
		}
		
		// Gui|
		if (!gameOver) {
			for (int i = 0; i <= player.getHealth() / 2; i++) {
				batch.draw(heartTexture, (i - 1) * 32, 8, 32, 32);
			}
			if ((int) player.getHealth() % 2 != 0) {
				batch.draw(halfheartTexture, (int) player.getHealth() / 2 * 32, 8, 32, 32);
			}
			for (int i = 0; i < player.getShieldHealth(); i++) {
				batch.draw(shieldheartTexture, i * 32, 8, 32, 32);
			}
			for (int i = 0; i < player.getWeapon().getEnergy(); i++) {
				batch.draw(energyTexture, i * 32, 40, 32, 32);
			}
			if(player.superMode) {
				if(player.superModeTimer > 5f || (int)(timer / 0.125) % 2 == 0)
					batch.draw(superstarTexture, 0, 72, 32, 32);
			}
			if(player.tripleShot) {
				if(player.tripleShotTimer > 5f || (int)(timer / 0.125) % 2 == 0)
					batch.draw(tripleshotTexture, 32, 72, 32, 32);
			}
			if(player.magnetMode) {
				if(player.magnetTimer > 5f || (int)(timer / 0.125) % 2 == 0)
					batch.draw(magnetTextures[(int) ((timer / 0.25) % 2)], 64, 72, 32, 32);
			}
			
			pauseButton.render(batch);
			optionButton.render(batch);
		}

		if (paused || gameOver)
			quitButton.render(batch);
	}

	@Override
	public void handleInput(float dt, Vector2 mouse, float roll) {
		// To stop it from immediately shooting when u load
		if (timer < 0.5f)
			return;
		
		//Set player motion
		this.player.setRoll(roll);

		// Held down
		if ((Gdx.input.isTouched() && (!pauseButton.isTouched(mouse) && !optionButton.isTouched(mouse) && !paused && !gameOver))
				|| Gdx.input.isKeyPressed(Keys.SPACE)) {
			//PowerUp
			if(player.tripleShot) {
				playerFire(70, true, true);
				playerFire(110, true, true);	
			}
			playerFire(90, false, false);
		}

		// Tap
		if (Gdx.input.justTouched()) {
			if (!gameOver && pauseButton.isTouched(mouse)) {
				blip.play(Game.masterVolume);
				paused = !paused;
				pauseText.setVisibility(paused);
				pauseButton.changeFrame();
			}
			if (!gameOver && optionButton.isTouched(mouse)) {
				blip.play(Game.masterVolume);
				if (!paused) {
					paused = true;
					pauseText.setVisibility(paused);
					pauseButton.changeFrame();
				}
				sceneManager.push(new OptionScene(sceneManager, assetManager, font));
			}
			if (paused || gameOver) {
				if (quitButton.isTouched(mouse)) {
					Preferences prefs = Gdx.app.getPreferences("AsteroidArcadePrefs");
					if (highScore <= player.score) {
						prefs.putInteger("highscore", player.score);
					}
					if (bestTime <= player.timer) {
						prefs.putInteger("besttime", (int) player.timer);
					}
					prefs.putInteger("bank", prefs.getInteger("bank", 0) + player.score);
					prefs.flush();

					blip.play(Game.masterVolume);
					this.sceneManager.musicHandler.stop();
					this.sceneManager.set(new MenuScene(sceneManager, assetManager, font));
				}
			}
		}
	}

	private void newAsteroid() {
		int scale = (int) (Math.random() * 2f) + 2;

		Entity asteroid = (Entity) new Entity().setTexture(scale, asteroidTextures[(int) (Math.random() * 2)]);

		float x = (float) Math.random() * (Game.WIDTH - asteroid.getWidth());
		float y = inverted ? -asteroid.getHeight() : Game.HEIGHT;

		asteroid.setHealth(scale);
		asteroid.setPosition(new Vector2(x, y));
		asteroid.setVelocity(
				new Vector2(
						(float) Math.random() * 300f - 150f, 
						(inverted ? 1f : -1f) * (float) (Math.random() * 300f + 100f)
						)
				);
		asteroid.setRotation((float) Math.random() * 90f, (float) Math.random() * 180f - 90f);

		entities.add(asteroid);
	}

	private void newDebris() {
		Texture texture;
		double chance = Math.random();
		float health = 1f;

		if (chance < 0.25) texture = junkTexture;
		else if (chance < 0.5) texture = panelTexture;
		else if (chance < 0.75) texture = dishTexture;
		else {
			texture = satelliteTexture;
			health = 4f;
		}

		Entity satellite = (Entity) new Entity().setTexture(2f, texture);

		float x = (float) Math.random() * (Game.WIDTH - satellite.getWidth());
		float y = inverted ? -satellite.getHeight() : Game.HEIGHT;

		satellite.setHealth(health);
		satellite.setPosition(new Vector2(x, y));
		satellite.setVelocity(
				new Vector2(
						(float) Math.random() * 300f - 150f, 
						(inverted ? 1f : -1f) * (float) (Math.random() * 300f + 100f)
						)
				);
		satellite.setRotation((float) Math.random() * 90f, (float) Math.random() * 180f - 90f);

		entities.add(satellite);
	}

	private void newMine() {
		Entity mine = (Entity) new Entity().setTexture(3.5f, mineTexture);

		do {
			float x = (float) Math.random() * (Game.WIDTH - mine.getWidth());
			float y = Game.HEIGHT;
			mine.setPosition(new Vector2(x, y)); 
		} while(spawnCollision(mine));

		mine.setHealth(0.5f);
		mine.setVelocity(new Vector2(0, -200f));
		mine.setRotation((float) Math.random() * 90f, (float) Math.random() * 180f - 90f);

		mine.explodeOnDeath = true;

		entities.add(mine);
	}
	
	private void newSwarm() {
		SwarmEntity entity = (SwarmEntity) new SwarmEntity(photonTexture, entities, lasers);
		if(spawnCollision(entity)) return;

		entity.setTexture(2f, 0.25f, ufoSwarmTextures);
		entity.setHealth(0.5f);
		entity.setVelocity(new Vector2(0, -100f));
		
		entities.add(entity);
	}
	
	private boolean spawnCollision(Entity e) {
		for(Entity e2 : entities) {
			if(e.getTeam() == e2.getTeam() && e.collisionCheck(e2)) return true;
		}
		return false;
	}
	
	private void explodeOnDeath(Entity e) {
		newExplosion(e);
		for (int k = 0; k < entities.size(); k++) {
			Entity e3 = entities.get(k);
			if(e3.isMarkedForRemoval() || e3 instanceof PowerUp) continue;
			if(e.getTeam() == Team.GOOD && e3.getTeam() == Team.GOOD) continue;

			float dy = (e.getPosition().y + e.getHeight() / 2f)
					- (e3.getPosition().y + e3.getHeight() / 2f);
			float dx = (e.getPosition().x + e.getWidth() / 2f)
					- (e3.getPosition().x + e3.getWidth() / 2f);
			float dist = dy * dy + dx * dx;
			
			float maxDist = 3f * 48f * 48f + 48f * 48f;
			
			if (dist < maxDist) {
				if(e3 instanceof Player && ((Player) e3).getShieldHealth() > 0) {
					Player p = (Player) e3;
					float shieldHP = p.getShieldHealth();
					shieldHP -= 4f * dist / maxDist;
					if(shieldHP < 0) {
						p.setHealth(p.getHealth() + shieldHP);
						p.setShieldHealth(0);
					} else {
						p.setShieldHealth(shieldHP);
					}
				} else e3.setHealth(e3.getHealth() - 4);
				if(e3.getHealth() <= 0) {
					if(e3.explodeOnDeath) {
						e3.setMarkedForRemoval(true);
						explodeOnDeath(e3);
					} else e3.canExplode = false;
				}
			}
		}
	}

	private void newExplosion(Entity e) {
		if(!e.canExplode || !e.isOnScreen()) return;
		
		float scale = e.getWidth() / explosionTexture.getWidth();
		if (e.explodeOnDeath)
			scale = 3f * 48f / explosionTexture.getWidth();
		Particle explosion = (Particle) new Particle(0.25f).setTexture(scale, explosionTexture);

		float x = e.getPosition().x + (e.getWidth() - explosion.getWidth()) / 2f;
		float y = e.getPosition().y + (e.getHeight() - explosion.getHeight()) / 2f;
		explosion.setPosition(new Vector2(x, y));
		explosion.setRotation((float) Math.random() * 180f, 0);

		fgObjects.add(explosion);

		explosions[(int) (Math.random() * 3)].play(Game.masterVolume * 0.75f);
	}

	private float spawnChance(float dt) {
		return (float) timer > 20 ? 3f * dt : (0.5f + (2.5f * timer / 20)) * dt;
	}
	
	private String formatTime(float time) {
		return "" + ((time / 60) < 10 ? "0" : "") + (int) (time / 60) + ":"
				+ ((time % 60) < 10 ? "0" : "") + (int) time % 60;
	}
	
	private void playerFire(float dir, boolean ignoreEnergy, boolean ignoreSound) {
		if(inverted) dir += 180f;
		Entity[] e = player.getWeapon().fire(dir, ignoreEnergy);
		if (e != null) {
			if(!ignoreSound) {
				if (player.getWeapon().type != Weapon.Type.LASER)
					lasers[(int) (Math.random() * 3)].play(Game.masterVolume);
				else {
					lasers[1].play(Game.masterVolume * 0.25f);
				}
			}
			
			for(Entity ents : e)
				entities.add(ents);
		}
	}
	
	private void addTrail(Entity e, Texture t, float scale) {
		Particle p = new Particle((float) Math.random() + 1f);
		p.setTexture(scale, t);
		
		float x = e.getPosition().x + (e.getWidth() - p.getWidth()) / 2f;
		float y = e.getPosition().y + (e.getHeight() - p.getHeight()) / 2f + (inverted ? 1 : -1) * 16f;
		
		x += ((float) Math.random() * 16) - 8f;
		y += ((float) Math.random() * 16) - 8f;
		
		p.setPosition(new Vector2(x, y));
		p.setRotation(0, (float) Math.random() * 180f - 90f);
		p.setVelocity(new Vector2(0, (inverted ? 1 : -1) * 200f));

		objects.add(p);
	}
	
	private void newRandomStars() {
		for (int i = 0; i < 15; i++) {
			newStar(2f, true);
		}
		for (int i = 0; i < 15; i++) {
			newStar(1.5f, true);
		}
		for (int i = 0; i < 15; i++) {
			newStar(1f, true);
		}
	}
	
	private void invert() {
		inverted = !inverted;
		
		//Invert player
		if(inverted) {
			player.getPosition().y = (Game.HEIGHT*4/5) - player.getHeight();
		} else {
			player.getPosition().y = Game.HEIGHT / 5;
		}
		
		//Invert objects
		rotateObject(player);
		for(GameObject o : bgObjects) rotateObject(o);
		
		for(GameObject o : objects) removeObject(o);
		for(GameObject o : fgObjects) removeObject(o);
		for(GameObject o : entities) removeObject(o);
		
		
		fade(0.3f, 0.7f, 0.8f);
	}
	
	private void rotateObject(GameObject o) {
		o.getVelocity().y *= -1;
		o.setRotation((o.getRotation() + 180) % 360, o.getAngularVelocity() * -1);
	}
	
	private void removeObject(GameObject o) {
		if(!(o instanceof Player || o.getParent() instanceof Player)) {
			o.setMarkedForRemoval(true);
			// Make sure they don't blow up when they disappear
			if(o instanceof Entity) {
				((Entity) o).explodeOnDeath = false;
			}
		}
	}
	
	private void newWormhole() {
		Entity wormhole = new Entity();
		wormhole.setTexture(8f, wormholeTexture);
		wormhole.setPosition(new Vector2((Game.WIDTH - wormhole.getWidth()) / 2f, Game.HEIGHT));
		wormhole.setRotation(0f, 180f);
		wormhole.setVelocity(new Vector2(0, -130f));
		wormhole.setTeam(Team.WORMHOLE);
		wormhole.immortal = true;
		
		Attractor a = new Attractor(wormhole, 2f);
		entities.add(a);
		
		entities.add(0, wormhole);
	}
	
	private void spawnPowerUps(float dt) {
		if (Math.random() < dt / 5) {
			newPowerUp(PowerUp.Type.COIN);
		}
		if (Math.random() < dt / 20) {
			newPowerUp(PowerUp.Type.ENERGY);
		}
		if (Math.random() < dt / 20) {
			newPowerUp(PowerUp.Type.HEART);
		}
		if (Math.random() < dt / 45) {
			newPowerUp(PowerUp.Type.TRIPLE_SHOT);
		}
		if (Math.random() < dt / 45) {
			newPowerUp(PowerUp.Type.SUPER_MODE);
		}
		if (Math.random() < dt / 30) {
			newPowerUp(PowerUp.Type.ATOM);
		}
		if (Math.random() < dt / 30) {
			newPowerUp(PowerUp.Type.MAGNET);
		}
	}
	
	private void newPowerUp(PowerUp.Type type) {
		boolean rotates = false;
		boolean spawnMultiple = false;
		float scale = 2f;
		Texture t = null;
		Texture[] textures = null;
		float frameLength = 0.25f;
		switch(type) {
			case ATOM: textures = atomTextures; frameLength = 0.1f; rotates = false; break;
			case SUPER_MODE: t = superstarTexture; rotates = true; break;
			case TRIPLE_SHOT: t = tripleshotTexture; rotates = true; break;
			case ENERGY: t = energyTexture; break;
			case HEART: t = heartTexture; break;
			case COIN: t = coinTexture; spawnMultiple = true; break;
			case MAGNET: textures = magnetTextures; rotates = true; break;
		}
		
		int number = spawnMultiple ? (int) (Math.random() * 8) + 1 : 1;
	
		//Same x
		float x = ((int) ((Math.random() * (Game.WIDTH - 32)) / 32)) * 32f;
		for (int i = 0; i < number; i++) {
			float y = inverted ? ((i + 1) * -32) : Game.HEIGHT + i * 32;
			
			PowerUp p;
			if(textures == null)
				p = new PowerUp(x, y, t, type, scale);
			else
				p = new PowerUp(x, y, textures, type, scale, frameLength);
			
			if(rotates)
				p.setRotation(0, 180f);
			p.getVelocity().y *= (inverted ? 1 : -1);
			
			boolean flagCollision = false;
			for(Entity e : entities) {
				if (e.collisionCheck(p)) flagCollision = true;
			}
			
			if(!flagCollision) entities.add(p);
		}
	}
	
	private void newStar(float scale, boolean randomY) {
		GameObject star = new GameObject().setTexture(scale, (float) (Math.random() + 0.5f), starTextures);

		float x = ((int) ((Math.random() * (Game.WIDTH - star.getWidth())) / star.getWidth())) * (int) star.getWidth();
		
		float y;
		if(randomY)
			y = ((int) ((Math.random() * (Game.HEIGHT - star.getWidth())) / star.getWidth())) * (int) star.getWidth();
		else
			y = (inverted ? -star.getHeight() : Game.HEIGHT);
		
		star.setPosition(new Vector2(x, y));
		star.setVelocity(new Vector2(0, (inverted ? 1 : -1) * 100f / (2f / scale)));

		for (GameObject star2 : bgObjects) {
			if (star2.getWidth() == star.getWidth() && star.collisionCheck(star2)) {
				newStar(scale, randomY);
				return;
			}
		}

		bgObjects.add(star);
	}
}
