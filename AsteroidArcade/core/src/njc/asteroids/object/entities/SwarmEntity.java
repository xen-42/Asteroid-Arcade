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
	private Sound[] lasers;
	
	public SwarmEntity(Texture weaponTexture, ArrayList<Entity> entities, Sound[] lasers) {
		super();
		this.setTeam(Team.ENEMY);
		this.laser = new Weapon(this, Weapon.Type.BURST_LASER, 0, weaponTexture);
		this.entities = entities;
		this.lasers = lasers;
		this.setPosition(new Vector2(Game.WIDTH - this.getWidth(), Game.HEIGHT));
	}
	
	@Override
	public void update(float dt) {
		super.update(dt);
		timer += dt;
		this.getPosition().x = (Game.WIDTH - this.getWidth()) / 2f * (float) Math.cos(2 * timer) + (Game.WIDTH - this.getWidth()) / 2f;
		
		if(this.getPosition().y > 0 && Math.random() < 2f * dt) {
			entities.add(this.laser.fire(270, true));
			lasers[(int) (Math.random() * 3)].play(Game.masterVolume);
		}
	}
}
