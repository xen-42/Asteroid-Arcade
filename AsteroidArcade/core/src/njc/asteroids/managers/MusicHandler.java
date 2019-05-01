package njc.asteroids.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

import njc.asteroids.Game;

public class MusicHandler {
	public static final int ARCADIA = 0;
	public static final int SALTY_DITTY = 1;
	private Music[] songs = new Music[2];
	
	private Music playing;
	
	private AssetManager assetManager;
	
	public MusicHandler(AssetManager assetManager) {
		this.assetManager = assetManager;
		songs[0] = getMusic("Arcadia");
		songs[1] = getMusic("Salty_Ditty");
	}
	
	public void play(int i) {
		update();
		playing = songs[i];
		playing.setLooping(true);
		playing.play();
	}
	
	public void play() {
		update();
		if(playing != null) playing.play();
	}
	
	public void pause() {
		update();
		if(playing != null) playing.pause();
	}
	
	public void stop() {
		update();
		if(playing != null) playing.stop();
		playing = null;
	}
	
	public void update() {
		for(Music m : songs) {
			m.setVolume(Game.musicVolume);
		}
	}
	
	private Music getMusic(String s) {
		return assetManager.get("audio/music/" + s + ".wav", Music.class);
	}
}


