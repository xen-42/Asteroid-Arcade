package njc.asteroids.scene;

public enum Level {
	PAUSE, ASTEROIDS, DEBRIS, MINES, SWARM, WORMHOLE, BOSS_UFO, BOSS_TURRET, BOSS_LASER;

	public static Level randomBoss() {
		Level[] bosses = {BOSS_UFO, BOSS_TURRET, BOSS_LASER, SWARM};
		return bosses[(int) (Math.random() * bosses.length)];
	}
	
	public static Level random() {
		Level[] regularLevels = {ASTEROIDS, DEBRIS, MINES};
		return regularLevels[(int) (Math.random() * regularLevels.length)];
	}
	
	public boolean isBoss() {
		if(this == BOSS_UFO || this == BOSS_TURRET || this == BOSS_LASER)
			return true;
		return false;
	} 
	
	@Override
	public String toString() {
		switch(this) {
		case SWARM: return "UFO SWARM";
		case BOSS_UFO: return "ENEMY UFO";
		case BOSS_TURRET: return "ENEMY TURRET";
		case BOSS_LASER: return "ENEMY SHIP";
		default: return super.toString();
		}
	}
}
