package njc.asteroids.object.entities;

import java.util.ArrayList;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import njc.asteroids.Game;
import njc.asteroids.object.entities.player.Weapon;

public class SwarmEntity extends Entity {
	private Weapon laser;
	private ArrayList<Entity> entities;
	private float timer;
	private float nextShot = 3f;
	private Sound[] lasers;
	
	public SwarmEntity(Texture weaponTexture, ArrayList<Entity> entities, Sound[] lasers) {
		super();
		this.setTeam(Team.ENEMY);
		this.laser = new Weapon(this, Weapon.Type.PHOTON, 0, weaponTexture);
		this.entities = entities;
		this.lasers = lasers;
		this.setPosition(new Vector2(Game.WIDTH - this.getWidth(), Game.HEIGHT));
	}
	
	@Override
	public void update(float dt) {
		super.update(dt);
		timer += dt;
		this.getPosition().x = (Game.WIDTH - this.getWidth()) / 2.2f * (float) Math.cos(2 * timer) + (Game.WIDTH - this.getWidth()) / 2.2f;
		
		if(this.getPosition().y > 0 && timer > nextShot) {
			entities.add(this.laser.fire(270, true).setHealth(1f));
			lasers[(int) (Math.random() * 3)].play(Game.masterVolume);
			nextShot = timer + 0.8f;
		}
	}
}
