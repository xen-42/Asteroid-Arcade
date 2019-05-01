package njc.asteroids.managers;

import java.util.Stack;

import njc.asteroids.graphics.Font;
import njc.asteroids.scene.Scene;

public class SceneManager {
	private Stack<Scene> scenes;
	public MusicHandler musicHandler;
	public Font[] fonts;
	
	public SceneManager(MusicHandler mh, Font[] fonts) {
		this.scenes = new Stack<Scene>();
		this.musicHandler = mh;
		this.fonts = fonts;
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

