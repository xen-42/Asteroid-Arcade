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
import njc.asteroids.managers.MusicHandler;
import njc.asteroids.managers.SceneManager;
import njc.asteroids.object.GameObject;
import njc.asteroids.object.Particle;
import njc.asteroids.object.PowerUp;
import njc.asteroids.object.TextObject;
import njc.asteroids.object.entities.BossEntity;
import njc.asteroids.object.entities.Entity;
import njc.asteroids.object.entities.SwarmEntity;
import njc.asteroids.object.entities.Entity.Team;
import njc.asteroids.object.entities.player.Player;
import njc.asteroids.object.entities.player.Weapon;

public class PlayScene extends Scene {
	Player player;
	Entity boss;
	Weapon bossWeapon;

	// Gui elements
	private GameObject pauseButton, optionButton, quitButton, unpauseButton;

	private Sound[] explosions = new Sound[3];
	private Sound[] lasers = new Sound[3];
	private Sound[] pickups = new Sound[3];

	private Sound blip;
	private Sound death;
	// private Music music;	

	//Player textures
	private Texture rocketTexture = assetManager.get("textures/ships/rocket.png", Texture.class);
	private Texture shuttleTexture = assetManager.get("textures/ships/shuttle.png", Texture.class);
	private Texture ufoTexture = assetManager.get("textures/ships/ufo.png", Texture.class);
	//private Texture laserUfoTexture = assetManager.get("textures/ships/laser_ship.png", Texture.class);
	private Texture whaleTexture1 = assetManager.get("textures/ships/whale_1.png", Texture.class);
	private Texture whaleTexture2= assetManager.get("textures/ships/whale_2.png", Texture.class);
	private Texture eagleTexture = assetManager.get("textures/ships/eagle.png", Texture.class);
	
	//Weapon textures
	private Texture laserTexture = assetManager.get("textures/weapons/laser.png", Texture.class);
	private Texture photonTexture = assetManager.get("textures/weapons/photon.png", Texture.class);
	private Texture missileTexture = assetManager.get("textures/weapons/missile.png", Texture.class);
	
	//Stuff
	private Texture starTexture = assetManager.get("textures/star.png", Texture.class);
	private Texture coinTexture = assetManager.get("textures/coin.png", Texture.class);
	private Texture asteroidTexture = assetManager.get("textures/asteroid.png", Texture.class);
	private Texture flameTexture1 = assetManager.get("textures/flame_1.png", Texture.class);
	private Texture flameTexture2 = assetManager.get("textures/flame_2.png", Texture.class);
	private Texture shieldTexture = assetManager.get("textures/shield.png", Texture.class);
	private Texture	explosionTexture = assetManager.get("textures/boom.png", Texture.class);
	
	private Texture wormholeTexture = assetManager.get("textures/wormhole.png", Texture.class);
	
	//PowerUps
	private Texture atomTexture = assetManager.get("textures/atom.png", Texture.class);
	private Texture superstarTexture = assetManager.get("textures/superstar.png", Texture.class);
	private Texture tripleshotTexture = assetManager.get("textures/tripleshot.png", Texture.class);
	
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
	
	private Texture ufoSwarmTexture = assetManager.get("textures/ships/swarm_ufo.png", Texture.class);
	private Texture ufoSwarmTexture2 = assetManager.get("textures/ships/swarm_ufo_2.png", Texture.class);
	
	private int highScore = Gdx.app.getPreferences("AsteroidArcadePrefs").getInteger("highscore", 0);
	private int bestTime = Gdx.app.getPreferences("AsteroidArcadePrefs").getInteger("besttime", 0);
	
	private boolean paused = false;
	private boolean gameOver = false;

	private Level level = Level.PAUSE;
	
	private boolean inverted = false;

	public PlayScene(SceneManager sm, AssetManager am, Font[] f, int selection) {
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
		pauseButton = new GameObject().setTexture(3f, -1f, pauseTexture, playTexture);
		pauseButton.setPosition(new Vector2(Game.WIDTH - 27, Game.HEIGHT - 28));

		quitButton = new TextObject("MAIN MENU", 1.5f, font[1]);
		quitButton.setPosition(new Vector2(0, 200));
		quitButton.centerX();

		unpauseButton = new TextObject("CONTINUE", 2f, font[1]);
		unpauseButton.centerX();
		unpauseButton.centerY();

		optionButton = new GameObject().setTexture(2f, optionTexture);
		optionButton.setPosition(new Vector2(Game.WIDTH - 32, Game.HEIGHT - 64));

		GameObject coin = new GameObject().setTexture(1.5f, coinTexture);
		coin.setPosition(new Vector2(8, Game.HEIGHT - 80));
		guiObjects.add(coin);

		newRandomStars();

		GameObject flame, shield;
		switch (selection) {
		case 0:
			player.setTexture(2f, rocketTexture);
			player.setHealth(14);			
			player.setSpeed(1.25f);
			player.setWeapon(Weapon.Type.BURST_LASER, 7f, laserTexture);
			
			//Flame
			flame = new GameObject()
					.setTexture(2f, 0.5f, flameTexture1, flameTexture2)
					.setRotation(180f, 0f);
			flame.setParent(player, new Vector2(0, -34f), true);
			objects.add(flame);
			break;
		case 1:
			player.setTexture(2f, shuttleTexture);
			player.setHealth(20);
			player.setWeapon(Weapon.Type.MISSILE, 0f, missileTexture);
			
			//Flame
			flame = new GameObject()
					.setTexture(2f, 0.5f, flameTexture1, flameTexture2)
					.setRotation(180f, 0f);
			flame.setParent(player, new Vector2(0, -42f), true);
			objects.add(flame);
			break;
		case 2:
			player.setTexture(2f, eagleTexture);
			player.setHealth(14);
			player.setWeapon(Weapon.Type.LASER, 7f, laserTexture);
			
			//Flame
			flame = new GameObject()
					.setTexture(2f, 0.5f, flameTexture1, flameTexture2)
					.setRotation(180f, 0f);
			flame.setParent(player, new Vector2(0, -48f), true);
			objects.add(flame);
			break;
			/*
		case 3:
			player.setTexture(2f, laserUfoTexture);
			player.setHealth(10);
			player.setWeapon(Weapon.Type.LASER, 5f, laserTexture);
			
			//Shield
			shield = new GameObject().setTexture(4.5f, shieldTexture).setRotation(0f, 90f);
			shield.setParent(player, new Vector2(0, 0), false);
			fgObjects.add(shield);
			player.addShield(shield, 5f);
			break;
			*/
		case 3:
			player.setTexture(2f, 0.5f, whaleTexture1, whaleTexture2);
			player.setHealth(10);
			player.addRegen();
			player.setWeapon(Weapon.Type.PHOTON, 5f, photonTexture);
			player.isOrganic = true;
			break;
		case 4:
			player.setTexture(2f, ufoTexture);
			player.setHealth(12);
			player.setSpeed(1.25f);
			player.setWeapon(Weapon.Type.BURST_LASER, 6f, laserTexture);
			
			//Shield
			shield = new GameObject().setTexture(4.5f, shieldTexture).setRotation(0f, 180f);
			shield.setParent(player, new Vector2(0,0), false);
			fgObjects.add(shield);
			player.addShield(shield, 6);
			break;
		}

		player.setPosition(new Vector2((Game.WIDTH - player.getWidth()) / 2f, Game.HEIGHT / 5));
		entities.add(player);

		sceneManager.musicHandler.play(MusicHandler.SALTY_DITTY);
	}

	@Override
	public void update(float dt) {
		if (!paused) {
			super.update(dt);

			// Spawning stars
			if (Math.random() < 5 * dt) {
				newStar(2f);
			}
			if (Math.random() < 5 * dt) {
				newStar(1.5f);
			}
			if (Math.random() < 5 * dt) {
				newStar(1f);
			}

			// Spawning powerups
			

			//Trails
			if(player.getHealth() > 0) {
				if(player.superMode) {
					if (Math.random() < dt * 2f) {
						addTrail(player, startrailTexture, 4f);
					}
				}
				if(player.tripleShot) {
					if (Math.random() < dt * 2f) {
						addTrail(player, orangetrailTexture, 4f);
					}
				}	
			}
			
			if (!player.isOrganic && player.getHealth() > 0) {
				// If ship is damaged
				if (player.getHealth() < player.getMaxHealth() * 0.75) {
					if (Math.random() < dt * (player.getMaxHealth() * 0.75 - player.getHealth())) {
						addTrail(player, smokeTexture, 4f);
					}
				}
				// Flames
				if (player.getHealth() < player.getMaxHealth() * 0.4) {
					if (Math.random() < dt * (player.getMaxHealth() * 0.4 - player.getHealth())) {
						addTrail(player, fireTexture, 4f);
					}
				}
			}

			// Spawning asteroids
			switch (level) {
			case ASTEROIDS:
				if (Math.random() < 3f * spawnChance(dt)) {
					newAsteroid();
				}
				break;
			case DEBRIS:
				if (Math.random() < 3f * spawnChance(dt)) {
					newDebris();
				}
				break;
			case MINES:
				if (Math.random() < 1f * spawnChance(dt)) {
					newMine();
				}
				break;
			case SWARM:
				if(Math.random() < 0.5f * spawnChance(dt)) {
					newSwarm();
				}
				break;
			case WORMHOLE:
				float x = player.getPosition().x + player.getWidth() / 2f;
				float dist = (float) Math.abs(x - Game.WIDTH / 2f);
				float acc = (float) Math.pow(dist / (Game.WIDTH / 2f), 2) * 12800f;
				
				if(x < Game.WIDTH / 2f) player.getVelocity().x += acc * dt;
				else player.getVelocity().x -= acc * dt;
				break;
			default:
				break;
			}

			if (level.isBoss()) {
				// Spawn
				if (boss == null) {
					Texture t, wt;
					switch(level) {
						case BOSS_UFO:
							t = ufoBossTexture;
							wt = laserTexture;
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
							t = ufoTexture;
							wt = laserTexture;
							break;		
					}
					boss = new BossEntity(t, wt, level, entities, lasers, player);
					entities.add(boss);
					if(level == Level.BOSS_UFO) {
						GameObject spikes = new GameObject().setTexture(2f, ufoBossSpikesTexture);
						spikes.setParent(boss, new Vector2(-2, 2), false);
						spikes.setRotation(0f, 180f);
						objects.add(spikes);
					}
					if(level == Level.BOSS_LASER) {
						GameObject spikes = new GameObject().setTexture(2f, laserBossSpikesTexture);
						spikes.setParent(boss, new Vector2(0, -32), false);
						objects.add(spikes);
					}
				}

				// Boss flames
				// If ship is damaged
				if (boss.getHealth() < boss.getMaxHealth() * 0.75) {
					if (Math.random() < dt * (boss.getMaxHealth() * 0.75 - boss.getHealth()) / 5f) {
						addTrail(boss, smokeTexture, 4f);
					}
				}
				// Flames
				if (boss.getHealth() < boss.getMaxHealth() * 0.4) {
					if (Math.random() < dt * (boss.getMaxHealth() * 0.4 - boss.getHealth()) / 5f) {
						addTrail(boss, fireTexture, 4f);
					}
				}
				
				/*
				 * TODO: Add to boss level
				if (boss.markForRemoval) {
					for (int i = 0; i < 8; i++)
						newPowerUp(PowerUp.Type.COIN);
					boss = null;
					bossWeapon = null;
					level = Level.PAUSE;
					milestone = timer + 5;
				}
				*/
			}
			
			//Explosion Powerup
			if(player.atomBomb) {
				for(Entity e : entities) {
					if(e instanceof Player || e instanceof PowerUp) continue;
					e.setHealth(-1f);
				}	
				player.atomBomb = false;
				fade(1f, 0.7f, 0.6f);
			}

			// Collision check
			for (int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);

				for (int j = 0; j < entities.size(); j++) {
					if (j == i)
						continue;
					Entity e2 = entities.get(j);
					if (!e.markForRemoval && !e2.markForRemoval && Entity.entityCollisionCheck(e, e2)) {
						e.damage();
						//If e is now marked for removal, damage the other object as well (collision check will be false when it checks it later)
						if(e.markForRemoval) e2.damage();
					}
					
					//Weird
					if(!(e.explodeOnDeath && e2.explodeOnDeath)) { 
						if(e.explodeOnDeath) {
							if(e.markForRemoval && e2.markForRemoval) {
								e2.canExplode = false;
							}
						} else if(e2.explodeOnDeath) {
							if(e.markForRemoval && e2.markForRemoval) {
								e.canExplode = false;
							}
						}
					}
				}
			}
			
			//Explode things
			for(Entity e : entities) {
				if (e.getHealth() <= 0) {
					if (e.explodeOnDeath) {
						explodeOnDeath(e);
					} else {
						newExplosion(e);
					}
					if(!(e instanceof Player)) e.markForRemoval = true;
				}

				if (e.markForRemoval) {
					if (!(e instanceof PowerUp))
						newExplosion(e);
					else if (e.getPosition().y < Game.HEIGHT)
						pickups[(int) (Math.random() * 3)].play(Game.masterVolume);
				}
			}
			
			//SuperMode powerup
			if(player.superMode) {
				player.weapon.setEnergy(player.weapon.getMaxEnergy());
			}

			//Do once when the player dies
			if (!player.markForRemoval && player.getHealth() <= 0) {
				player.markForRemoval = true;
				death.play(Game.masterVolume);
				sceneManager.musicHandler.stop();
				gameOver = true;
			}
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
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
			for (int i = 0; i < player.weapon.getEnergy(); i++) {
				batch.draw(energyTexture, i * 32, 40, 32, 32);
			}
			pauseButton.render(batch);
			optionButton.render(batch);
		}

		if (paused) {
			String s = "PAUSED";
			font[0].write(batch, s, (Game.WIDTH - s.length() * Font.SIZE * 2f) / 2f, Game.HEIGHT - 128, 2f);
		}
		if (gameOver) {
			String s = "GAME OVER";
			font[0].write(batch, s, (Game.WIDTH - s.length() * Font.SIZE * 2f) / 2f, Game.HEIGHT - 128, 2f);
			
			s = "High Score: " + (player.score > highScore ? player.score + " NEW" : highScore);
			font[0].write(batch, s, (Game.WIDTH - s.length() * Font.SIZE) / 2f, Game.HEIGHT - 256, 1f);
			
			s = "Best time: " + (player.timer > bestTime ? formatTime(player.timer) + " NEW" : formatTime(bestTime));
			font[0].write(batch, s, (Game.WIDTH - s.length() * Font.SIZE) / 2f, Game.HEIGHT - 320, 1f);
		}

		if (paused || gameOver)
			quitButton.render(batch);
		if (paused)
			unpauseButton.render(batch);
/*
		font[1].write(batch, "e:" + entities.size(), 228, 16, 1f);
		font[1].write(batch, "bg:" + bgObjects.size(), 228, 32, 1f);
		font[1].write(batch, "fg:" + fgObjects.size(), 228, 48, 1f);
		font[1].write(batch, "gui:" + guiObjects.size(), 228, 64, 1f);
*/
		font[0].write(batch, "" + level.toString(), 8, Game.HEIGHT - 24, 1f);

		font[0].write(batch, "Time: " + formatTime(player.timer), 8, Game.HEIGHT - 48, 1f);

		font[0].write(batch, "" + player.score, 40, Game.HEIGHT - 80, 1.5f);
	}

	@Override
	public void handleInput(float dt, Vector2 mouse, float roll) {
		// To stop it from immediately shooting when u load
		if (timer < 0.5f)
			return;
		
		//Set player motion
		this.player.setRoll(roll);

		// Held down
		if (Gdx.input.isTouched()) {
			if (!pauseButton.isTouched(mouse) && !optionButton.isTouched(mouse) && !paused && !gameOver) {
				//PowerUp
				if(player.tripleShot) {
					playerFire(45, true);
					playerFire(135, true);	
				}
				playerFire(90, false);
			}
		}

		// Tap
		if (Gdx.input.justTouched()) {
			if (!gameOver && pauseButton.isTouched(mouse)) {
				blip.play(Game.masterVolume);
				paused = !paused;
				pauseButton.changeFrame();
			}
			if (!gameOver && optionButton.isTouched(mouse)) {
				blip.play(Game.masterVolume);
				if (!paused) {
					paused = true;
					pauseButton.changeFrame();
				}
				sceneManager.push(new OptionScene(sceneManager, assetManager, font));
			}
			if (paused && unpauseButton.isTouched(mouse)) {
				paused = false;
				pauseButton.changeFrame();
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
		float scale = (float) Math.random() * 2f + 2f;
		Entity asteroid = (Entity) new Entity().setTexture(scale, asteroidTexture);

		float x = (float) Math.random() * (Game.WIDTH - asteroid.getWidth());
		float y = inverted ? -asteroid.getHeight() : Game.HEIGHT;

		asteroid.setHealth(scale);
		asteroid.setPosition(new Vector2(x, y));
		asteroid.setVelocity(new Vector2((float) Math.random() * 300f - 150f, (float) Math.random() * (inverted ? 1 : -1) * 300f - 100f));
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
		satellite.setVelocity(new Vector2((float) Math.random() * 300f - 150f, (float) Math.random() * (inverted ? 1 : -1) * 300f - 100f));
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
		SwarmEntity entity = (SwarmEntity) new SwarmEntity(laserTexture, entities, lasers);
		if(spawnCollision(entity)) return;

		entity.setTexture(2f, 0.5f, ufoSwarmTexture, ufoSwarmTexture2);
		entity.setHealth(0.5f);
		entity.setVelocity(new Vector2(0, -100f));
		
		entities.add(entity);
	}
	
	private boolean spawnCollision(Entity e) {
		for(Entity e2 : entities) {
			if(e.getTeam() == e2.getTeam() && Entity.collisionCheck(e, e2)) return true;
		}
		return false;
	}
	
	private void explodeOnDeath(Entity e) {
		newExplosion(e);
		for (int k = 0; k < entities.size(); k++) {
			Entity e3 = entities.get(k);
			if(e3.markForRemoval || e3 instanceof PowerUp) continue;
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
						e3.markForRemoval = true;
						explodeOnDeath(e3);
					} else e3.canExplode = false;
				}
			}
		}
	}

	private void newExplosion(Entity e) {
		if(!e.canExplode) return;
		
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
	
	private void playerFire(float dir, boolean ignoreEnergy) {
		if(inverted) dir += 180f;
		Entity e = player.weapon.fire(dir, ignoreEnergy);
		if (e != null) {
			if (player.weapon.type != Weapon.Type.LASER)
				lasers[(int) (Math.random() * 3)].play(Game.masterVolume);
			else {
				lasers[1].play(Game.masterVolume * 0.25f);
			}

			entities.add(e);
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
			newRandomStar(2f);
		}
		for (int i = 0; i < 15; i++) {
			newRandomStar(1.5f);
		}
		for (int i = 0; i < 15; i++) {
			newRandomStar(1f);
		}
	}
	
	private void invert() {
		inverted = !inverted;
		if(inverted) {
			player.getPosition().y = Game.HEIGHT - Game.HEIGHT / 5 - player.getHeight();
			player.setRotation(180f, 0f);
			player.weapon.setOffset(-1);
		} else {
			player.getPosition().y = Game.HEIGHT / 5;
			player.setRotation(0f, 0f);
			player.weapon.setOffset(1);
		}
		
		for(int i = bgObjects.size() - 1; i >= 0; i--) {
			if(bgObjects.get(i).getParent() instanceof Player) continue;
			bgObjects.remove(i);
		}
		
		for(int i = objects.size() - 1; i >= 0; i--) {
			if(objects.get(i).getParent() instanceof Player) continue;
			objects.remove(i);
		}
		
		for(int i = fgObjects.size() - 1; i >= 0; i--) {
			if(fgObjects.get(i).getParent() instanceof Player) continue;
			else fgObjects.remove(i);
		}
		
		for(int i = entities.size() - 1; i >= 0; i--) {
			if(entities.get(i) instanceof Player) continue;
			else entities.remove(i);
		}
		
		newRandomStars();
		fade(0.3f, 0.7f, 0.8f);
	}
	
	private void newWormhole() {
		GameObject wormhole = new GameObject().setTexture(8f, wormholeTexture);
		wormhole.setPosition(new Vector2((Game.WIDTH - wormhole.getWidth()) / 2f, Game.HEIGHT));
		wormhole.setRotation(0f, 180f);
		wormhole.setVelocity(new Vector2(0, -130f));
		
		fgObjects.add(0, wormhole);
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
		if (Math.random() < dt / 20) {
			newPowerUp(PowerUp.Type.ATOM);
		}
	}
	
	private void newPowerUp(PowerUp.Type type) {
		boolean rotates = false;
		boolean spawnMultiple = false;
		float scale = 4f;
		Texture t = null;
		switch(type) {
			case ATOM: t = atomTexture; scale = 2f; rotates = true; break;
			case SUPER_MODE: t = superstarTexture; scale = 2f; rotates = true; break;
			case TRIPLE_SHOT: t = tripleshotTexture; scale = 2f; rotates = true; break;
			case ENERGY: t = energyTexture; break;
			case HEART: t = heartTexture; break;
			case COIN: t = coinTexture; scale = 2f; spawnMultiple = true; break;
		}
		
		if(spawnMultiple) {
			//Same x
			float x = ((int) ((Math.random() * (Game.WIDTH - 32)) / 32)) * 32f;
			for (int i = 0; i < (int) (Math.random() * 8) + 1; i++) {
				entities.add(new PowerUp(x, inverted ? ((i + 1) * -32) : Game.HEIGHT + i * 32, t, type, scale));
			}
		} else {
			PowerUp p = new PowerUp(inverted ? -32 : Game.HEIGHT, t, type, scale);
			if(rotates) p.setRotation(0f,  45f);
			entities.add(p);
		}
	}
	
	private void newStar(float scale) {
		GameObject star = new GameObject().setTexture(scale, starTexture);

		int x = ((int) ((Math.random() * (Game.WIDTH - star.getWidth())) / star.getWidth())) * (int) star.getWidth();
		star.setPosition(new Vector2((float) x, inverted ? -star.getHeight() : Game.HEIGHT));
		star.setVelocity(new Vector2(0, (inverted ? 1 : -1) * 200f / (2f / scale)));
		star.setRotation((float) Math.random() * 90f, (float) Math.random() * 180f - 90f);

		for (GameObject star2 : bgObjects) {
			if (star2.getWidth() == star.getWidth() && Entity.collisionCheck(star, star2)) {
				newStar(scale);
				return;
			}
		}

		bgObjects.add(star);
	}
	
	private void newRandomStar(float scale) {
		GameObject star = new GameObject().setTexture(scale, starTexture);

		int x = ((int) ((Math.random() * (Game.WIDTH - star.getWidth())) / star.getWidth())) * (int) star.getWidth();
		int y = ((int) ((Math.random() * (Game.HEIGHT - star.getWidth())) / star.getWidth())) * (int) star.getWidth();

		star.setPosition(new Vector2(x, y));
		star.setVelocity(new Vector2(0, (inverted ? 1 : -1) * 200f / (2f / scale)));
		star.setRotation((float) Math.random() * 90f, (float) Math.random() * 180f - 90f);

		bgObjects.add(star);
	}
}