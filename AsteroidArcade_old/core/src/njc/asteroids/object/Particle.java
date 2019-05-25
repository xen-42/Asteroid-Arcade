package njc.asteroids.object;

public class Particle extends GameObject {
	private float lifespan;
	private float age;
	public Particle(float l) {
		this.lifespan = l;
	}
	
	@Override
	public void update(float dt) {
		super.update(dt);
		this.age += dt;
		this.setOpacity((this.lifespan - this.age) / this.lifespan);
		if(age >= lifespan) this.setMarkedForRemoval(true);
	}
}
