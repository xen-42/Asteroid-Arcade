package njc.asteroids.object.entities;

import java.util.ArrayList;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import njc.asteroids.Game;
import njc.asteroids.object.GameObject;
import njc.asteroids.object.entities.player.Player;
import njc.asteroids.object.entities.player.Weapon;
import njc.asteroids.scene.Level;

public class BossEntity extends Entity {
	private Level type;
	private Weapon weapon;
	private ArrayList<Entity> entities;
	
	private float timer;
	private float milestone;
	
	private Sound[] lasers;
	private Player player;

	public BossEntity(Texture texture, Texture weaponTexture, Level t, ArrayList<Entity> e, Sound[] l, Player p) {
		this.type = t;
		this.entities = e;
		this.lasers = l;
		this.player = p;
		
		this.setHealth(0);
		switch (this.type) {
		case BOSS_UFO:
			this.setHealth(64);
			this.weapon = new Weapon(this, Weapon.Type.BURST_LASER, 0f, weaponTexture);
			break;
		case BOSS_LASER:
			this.setHealth(64);
			this.getVelocity().x = 100f;
			this.weapon = new Weapon(this, Weapon.Type.LASER, 0f, weaponTexture);
			break;
		case BOSS_TURRET:
			this.setHealth(64);
			this.weapon = new Weapon(this, Weapon.Type.MISSILE, 0f, weaponTexture);
			break;
		default:
			break;
		}
		this.setTeam(Team.ENEMY);
		this.setTexture(2f, texture);
		this.setPosition(new Vector2((Game.WIDTH - this.getWidth()) / 2f, Game.HEIGHT + this.getHeight()));
		this.setVelocity(new Vector2(0, -300f));
	}

	@Override
	public void update(float dt) {
		super.update(dt);
		timer += dt;
		this.weapon.update(dt);
		
		// Stop boss from moving down too far
		if (this.getVelocity().y != 0 && this.getPosition().y < Game.HEIGHT - this.getHeight() * 2.5f) {
			if (this.getVelocity().y != 0)
				this.setVelocity(new Vector2(0, 0));
		}
		if (this.getVelocity().y == 0) {
			this.getPosition().mulAdd(new Vector2(0, 1f), 12f * (float) Math.sin(3f * timer) * dt);
		}

		float fireChance = 1f;
		float fireDir = 270;

		// Boss type specific behavior
		switch (type) {
			case BOSS_UFO:
				this.getPosition().x = Game.WIDTH / 3f * (float) Math.cos(2 * timer) + 256 - 96f;
				// Shoot in middle of screen
				fireChance = 0f;
				if(this.getPosition().x > Game.WIDTH / 4 && this.getPosition().x < Game.WIDTH * 3/4)
					fireChance = 2f;
				break;
				
			case BOSS_LASER:
				//Start moving at the beginning
				if(milestone == 0 && this.getVelocity().y == 0) {
					this.getVelocity().x = 150f;
				}
				
				//Stop to shoot
				if(this.getVelocity().x != 0 &&
						(this.getPosition().x > Game.WIDTH - this.width || this.getPosition().x <= 0)) {
					this.getVelocity().x = 0;
					this.milestone = this.timer + 2f;					
				}
				
				if(timer > milestone && this.getPosition().x > Game.WIDTH - this.width) {
					this.getVelocity().x = -150f;
				}
				
				if(timer > milestone && this.getPosition().x <= 0) {
					this.getVelocity().x = 150f;
				}
				
				if(this.getVelocity().y == 0 && this.getVelocity().x == 0) fireChance = 60f;
				else fireChance = 0f;
				
				break;
				
			case BOSS_TURRET:
				fireChance = 0f;
				
				float dx = player.getPosition().x - this.getPosition().x;
				float dy = player.getPosition().y - this.getPosition().y;
				float dir = (float) Math.toDegrees(Math.atan2(dy, dx));
				
				if(this.getVelocity().y == 0 && this.milestone < this.timer && Math.abs(this.getRotation() - 90f - dir) < 1f) {
					fireChance = 100f;
					fireDir = this.getRotation() + 270f;
					this.milestone = this.timer + 1f;
				} 
				
				int omegaDir = (this.getRotation() - dir - 90f < 0) ? 1 : -1;
				this.setRotation(this.getRotation(), omegaDir * 25f);	
				
				if(this.timer < this.milestone) this.setRotation(this.getRotation(), 0f);				
				if(player.isMarkedForRemoval()) this.setRotation(this.getRotation(), 0f);
				
				break;
	
			default:
				break;
		}

		// Fire weapon
		if (Math.random() < fireChance * dt) {
			Entity[] ents = this.weapon.fire(fireDir, true);
			if (ents != null) {
				if (this.weapon.type != Weapon.Type.LASER)
					lasers[(int) (Math.random() * 3)].play(Game.masterVolume);
				else
					lasers[2].play(Game.masterVolume * 0.25f);
				
				for(Entity e : ents)
					entities.add(e);
			}
		}
	}
	
	public GameObject addChild(Vector2 offset, boolean rotate, float childScale, Texture... childTextures) {
		GameObject child = new GameObject();
		if(childTextures.length > 1) 
			child.setTexture(childScale, 0.5f, childTextures);
		else
			child.setTexture(childScale, childTextures[0]);
		child.setParent(this, offset, !rotate);
		if(rotate) child.setRotation(0f, 180f);
		return child;
	}
}
