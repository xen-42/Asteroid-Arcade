package njc.asteroids.scene;

public enum Level {
	PAUSE("Space"),
	ASTEROIDS("Asteroids"),
	DEBRIS("Debris Field"),
	MINES("Mine Field"),
	SWARM("UFO swarm"),
	BOSS_UFO("Enemy UFO"),
	BOSS_TURRET("Missile Turret"),
	BOSS_LASER("Laser Ship"),
	WORMHOLE("???");
	
	private String name;
	
	Level(String n) {
		name = n;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public static Level getRandom() {
		return Level.values()[(int) (Math.random() * 3) + 1];
	}
	
	public static Level getRandomBoss() {
		return Level.values()[(int) (Math.random() * 4) + 4];
	}
	
	public boolean isBoss() {
		return this == BOSS_UFO || this == BOSS_TURRET || this == BOSS_LASER;
	}
}
