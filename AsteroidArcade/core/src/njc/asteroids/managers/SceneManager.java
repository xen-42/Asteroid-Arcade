package njc.asteroids.managers;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import njc.asteroids.scene.Scene;

public class SceneManager {
	private Stack<Scene> scenes;
	public MusicHandler musicHandler;
	public BitmapFont[] fonts;
	
	public SceneManager(MusicHandler mh, BitmapFont[] _fonts) {
		this.scenes = new Stack<Scene>();
		this.musicHandler = mh;
		this.fonts = _fonts;
	}
	
	public Scene peek() {
		return scenes.peek();
	}
	
	public void pop() {
		scenes.pop();
	}
	
	public void push(Scene scene) {
		scenes.push(scene);
	}
	
	public void set(Scene scene) {
		this.pop(); 
		this.push(scene);
	}
}

