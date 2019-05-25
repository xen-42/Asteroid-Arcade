package njc.asteroids.object.entities;

import njc.asteroids.object.GameObject;
import njc.asteroids.object.PowerUp;
import njc.asteroids.object.entities.player.Player;

public class Entity extends GameObject {
	private float health = 1, maxHealth = 1;
	public boolean immortal;
	
	private boolean emitsSmoke = false;
	
	public boolean explodeOnDeath = false;
	public enum Team {
		GOOD,
		ENEMY,
		NEUTRAL,
		POWERUP,
		WORMHOLE
	}
	private Team team;
	public boolean canExplode = true;
	
	public Entity() {
		this.team = Team.NEUTRAL;
	}
	
	public void setTeam(Team t) {
		this.team = t;
	}
	
	public Team getTeam() {
		return this.team;
	}
	
	public Entity setHealth(float h) {
		this.health = h;
		if(h > this.maxHealth) this.maxHealth = h;
		return this;
	}
	
	@Override
	public void update(float dt) {
		super.update(dt);		
		if(this.health <= 0 && !immortal) this.setMarkedForRemoval(true);
	}
	
	public void damage() {
		if(--this.health <= 0 && !immortal) this.setMarkedForRemoval(true);
	}
	
	public float getHealth() {
		return this.health;
	}
	
	public float getMaxHealth() {
		return this.maxHealth;
	}
	
	public void setEmitsSmoke(boolean smoke) {
		this.emitsSmoke = smoke;
	}
	
	/* Returns emission chance, -1 if not flaming */
	public float isFlaming() {
		if (this.emitsSmoke && this.getHealth() < this.getMaxHealth() * 0.4) {
			return this.getMaxHealth() * 0.4f - this.getHealth();
		}
		return -1;
	}
	
	/* Return emission chance, -1 if not smoking */
	public float isSmoking() {
		if (this.emitsSmoke && this.getHealth() < this.getMaxHealth() * 0.75) {
			return this.getMaxHealth() * 0.75f - this.getHealth();
		}
		return -1;
	}
}
