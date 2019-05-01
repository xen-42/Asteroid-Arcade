package njc.asteroids.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class DataManager {	
	public boolean[] unlocks = new boolean[5];
	public int[] prices = {
			0,
			200,
			400,
			600,
			800
	};
	
	public DataManager() {
		Preferences prefs = Gdx.app.getPreferences("AsteroidArcadePrefs");
		unlocks[0] = true;
		for(int i = 1; i < unlocks.length; i++) {
			unlocks[i] = prefs.getBoolean("unlock" + i , false);
		}
		
	}

}
