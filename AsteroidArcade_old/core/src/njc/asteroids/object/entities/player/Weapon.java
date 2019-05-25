package njc.asteroids.object.entities.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import njc.asteroids.object.entities.BossEntity;
import njc.asteroids.object.entities.Entity;

public class Weapon {
	public enum Type {
		LASER,
		MISSILE,
		PHOTON,
		BURST_LASER,
		FLAK
	}
	private Entity parent;
	public Type type;
	private float cooldown;
	private float energy, maxEnergy;
	
	private Texture texture;
	private Texture[] textures;
	
	public Weapon(Entity p, Type t, float e, Texture tex) {
		this.parent = p;
		this.type = t;
		this.maxEnergy = e;
		this.energy = e;
		
		this.texture = tex;
	}
	
	public Weapon(Entity p, Type t, float e, Texture... tex) {
		this.parent = p;
		this.type = t;
		this.maxEnergy = e;
		this.energy = e;
		
		this.textures = tex;
	}
	
	public void setEnergy(float e) {
		if(e > this.maxEnergy) this.energy = this.maxEnergy;
		else this.energy = e;
	}
	
	public float getEnergy() {
		return this.energy;
	}
	
	public float getMaxEnergy() {
		return this.maxEnergy;
	}

	private Texture getTexture() {
		if(this.texture != null)
			return this.texture;
		else
			return this.textures[(int)(Math.random() * this.textures.length)];
	}
	
	public Entity[] fire() {
		return fire(90);
	}
	
	public Entity[] fire(float dir) {
		return fire(dir, false);
	}
	
	public Entity[] fire(float dir, boolean ignoreEnergy) {
		if(this.type == Type.FLAK)
			return this.fire(dir, ignoreEnergy, 5);
		return this.fire(dir, ignoreEnergy, 1);
	}
	
	private Entity[] fire(float dir, boolean ignoreEnergy, int number) {
		if(cooldown > 0 || (this.parent instanceof Player && energy < 0))
			return null;
		
		Entity[] entities = new Entity[number];
		for(int i = 0; i < number; i++) {
			Entity e = new Entity();
			e.setRotation(dir - 90f, 0);

			float vel = 300f;
			float hp = 4f;
			
			switch(type) {
				case LASER:
					e.setTexture(3f, this.getTexture());
					e.canExplode = false;
					vel = 1000f;
					e.setHeight(48f);
					hp = 0.5f;
					if(!ignoreEnergy) {
						cooldown -= 0.08f;
						if(this.parent instanceof Player) {
							energy -= 0.1f;
							if(energy <= 0) cooldown += 1.0f;
						}
					}
					break;
				case PHOTON:
					e.setTexture(3f, this.getTexture());
					e.setRotation((float) Math.random() * 90, (float) Math.random() * 180f - 90f); 
					hp = 4f;
					if(!ignoreEnergy) {
						cooldown += 0.15f;
						if(this.parent instanceof Player) energy -= 0.75f;
					}
					break;
				case MISSILE:
					e.setTexture(2f, this.getTexture());
					vel = 600f;
					e.explodeOnDeath = true;
					if(!ignoreEnergy) cooldown += 0.3f;
					hp = 1f;
					break;
				case BURST_LASER:
					e.canExplode = false;
					e.setTexture(3f, this.getTexture());
					vel = 1000f;
					e.setHeight(48f);
					hp = 2f;
					if(!ignoreEnergy) cooldown += 0.10f;
					if(!ignoreEnergy && this.parent instanceof Player) {
						energy -= 0.50f;
					}
					break;
				case FLAK:
					e.setTexture(Math.random() > 0.5f ? 1f : 1.4f, this.getTexture());
					vel = 200f + 100f * (float)Math.random();
					if(!ignoreEnergy) cooldown += 0.1f;
					hp = 1f;
					e.setRotation(e.getRotation(), (float) Math.random() * 360f - 180f); 
					dir += (60f * (i / (float)number) * Math.random() - 30f * (i/(float)number));
					break;
				default:
					return null;
			}
	
			float offsetX = (parent.getRotation() < 180 && !(parent instanceof BossEntity))  ? 1 : -1;
			float x = parent.getPosition().x + (parent.getWidth() - e.getWidth()) / 2f + (float) offsetX;
			float y = parent.getPosition().y + (parent.getHeight() - e.getHeight()) / 2f;
			
			e.setPosition(new Vector2(x, y));
			e.setVelocity(new Vector2(vel * (float) Math.cos(Math.toRadians(dir)), vel * (float) Math.sin(Math.toRadians(dir))));
			
			e.setHealth(hp);
			e.setTeam(parent.getTeam());
			entities[i] = e;
		}
			
		return entities;
	}
	
	public void update(float dt) {
		if(cooldown > 0) cooldown -= dt;
		if(cooldown < 0) cooldown = 0;
		
		if(energy < maxEnergy) {
			this.energy += 2f * dt;
		}
		if(energy > maxEnergy) {
			this.energy = maxEnergy;
		}
	}
}
