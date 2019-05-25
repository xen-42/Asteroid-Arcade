package njc.asteroids.object.entities;

import com.badlogic.gdx.math.Vector2;

import njc.asteroids.object.GameObject;

public class Attractor extends Entity {
	private float gravity;
	public boolean onlyPowerups = false;
	
	public Attractor(GameObject parent, float gravity) {
		this.setVisibility(false);
		this.setParent(parent, new Vector2(0, 0), false);
		this.gravity = gravity * 10000000f;
	}
	
	@Override
	public void damage() {
		return;
	}
	
	@Override
	public void setMarkedForRemoval(boolean b) {
		return;
	}
	
	public Vector2 getAttractionAcc(GameObject obj) {
		float xDiff = this.getPosition().x - obj.getPosition().x - obj.getWidth()/2f;
		float yDiff = this.getPosition().y - obj.getPosition().y - obj.getHeight()/2f;
		
		Vector2 difference = new Vector2(xDiff, yDiff);
		
		float magnitude = this.gravity / difference.len2();
		
		return difference.nor().scl(magnitude);
	}
}
