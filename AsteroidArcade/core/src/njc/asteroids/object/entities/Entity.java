package njc.asteroids.object.entities;

import njc.asteroids.object.GameObject;
import njc.asteroids.object.PowerUp;

public class Entity extends GameObject {
	private float health = 1, maxHealth = 1;
	
	public boolean explodeOnDeath = false;
	public enum Team {
		GOOD,
		ENEMY,
		NEUTRAL,
		POWERUP
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
	
	public void setHealth(float h) {
		this.health = h;
		if(h > this.maxHealth) this.maxHealth = h;
	}
	
	@Override
	public void update(float dt) {
		super.update(dt);
		
		if(this.health <= 0) this.markForRemoval = true;
	}
	
	public void damage() {
		if(--this.health <= 0) this.markForRemoval = true;
	}
	
	public static boolean entityCollisionCheck(Entity a, Entity b) {
		if(a.team == b.team && (a.team == Team.GOOD || a.team == Team.ENEMY)) return false;
		//if(a.team == b.team && a.team == Team.POWERUP) return false;
		
		if(collisionCheck(a, b)) {
			if(a.team == Team.POWERUP) {
				((PowerUp) a).onCollide(b);
				return false;
			} else if (b.team == Team.POWERUP) {
				((PowerUp) b).onCollide(a);
				return false;
			}
			return true;
		}
		
		return false;
	}
	
	public static boolean collisionCheck(GameObject a, GameObject b) {
		float dy = (a.getPosition().y + a.getHeight() / 2f) - (b.getPosition().y + b.getHeight() / 2f);
		if(Math.abs(dy) / 0.8 < (a.getHeight() + b.getHeight()) / 2f) {
			float dx = (a.getPosition().x + a.getWidth() / 2f) - (b.getPosition().x + b.getWidth() / 2f);
			if(Math.abs(dx) / 0.8 < (a.getWidth() + b.getWidth()) / 2f) {				
				return true;
			}
		}
		return false;
	}
	
	public float getHealth() {
		return this.health;
	}
	
	public float getMaxHealth() {
		return this.maxHealth;
	}
}
