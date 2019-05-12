package njc.asteroids.scene;

import java.util.ArrayList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import njc.asteroids.Game;
import njc.asteroids.managers.SceneManager;
import njc.asteroids.object.GameObject;
import njc.asteroids.object.entities.Entity;

public abstract class Scene {
	protected SceneManager sceneManager;
	protected AssetManager assetManager;
	protected ShapeRenderer shapeRenderer;
	protected BitmapFont[] font;
	
	protected ArrayList<GameObject> bgObjects, fgObjects, objects, guiObjects;
	protected ArrayList<Entity> entities;
	
	private boolean fade;
	private float fadeTimer;
	private float fadeOpacity = 1.0f;
	private float r, g, b;
	private Texture fadeTexture;
	
	protected float timer;
	
	public Scene(SceneManager sm, AssetManager am, BitmapFont[] f) {
		this.sceneManager = sm;
		this.assetManager = am;
		this.font = f;
		
		this.bgObjects = new ArrayList<GameObject>();
		this.objects = new ArrayList<GameObject>();
		this.entities = new ArrayList<Entity>();
		this.fgObjects = new ArrayList<GameObject>();
		
		this.guiObjects = new ArrayList<GameObject>();
		this.fadeTexture = assetManager.get("textures/fade.png", Texture.class);
	}

	public void render(SpriteBatch batch) {
		//Render objects in reverse order (older objects rendered first
		if(bgObjects.size() != 0) {
			for(int i = bgObjects.size() - 1; i >= 0; i--) {
				bgObjects.get(i).render(batch);
			}
		}
		if(objects.size() != 0) {
			for(int i = objects.size() - 1; i >= 0; i--) {
				objects.get(i).render(batch);
			}
		}
		if(entities.size() != 0) {
			for(int i = entities.size() - 1; i >= 0; i--) {
				entities.get(i).render(batch);
			}
		}
		if(fgObjects.size() != 0) {
			for(int i = fgObjects.size() - 1; i >= 0; i--) {
				fgObjects.get(i).render(batch);
			}
		}
		
		if(fade) {
			batch.setColor(r, g, b, fadeOpacity);
			batch.draw(
					new TextureRegion(this.fadeTexture),
					0f, 0f,
					0f, 0f,
					Game.WIDTH, Game.HEIGHT,
					1f, 1f, 0f);
			batch.setColor(1, 1, 1, 1.0f);
		}
		
		if(guiObjects.size() != 0) {
			for(int i = guiObjects.size() - 1; i >= 0; i--) {
				guiObjects.get(i).render(batch);
			}	
		}
	}
	
	public void fade(float r, float g, float b) {
		this.fade = true;
		fadeTimer = 0.0f;
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public void update(float dt) {
		timer += dt;
		
		if(fade) {
			this.fadeTimer += dt;
			if(fadeTimer > 1.0f){
				fade = false;
				fadeOpacity = 1.0f;
			}
			fadeOpacity = (1.0f - fadeTimer) / 1.0f;
		}
		
		//Entities first, because these are most likely to have children
		for(int i = 0; i < entities.size(); i++) {
			Entity obj = entities.get(i);
			
			if(obj == null) {
				entities.remove(i--);
			} else {
				obj.update(dt);
				updateEntity(dt, i);
				if(obj.isMarkedForRemoval()) {
					entities.remove(i--);
				} 
			}
		}
		
		for(int i = 0; i < objects.size(); i++) {
			GameObject obj = objects.get(i);
			
			obj.update(dt);
			if(obj.isMarkedForRemoval()) {
				objects.remove(i--);
			}
		}
		
		for(int i = 0; i < bgObjects.size(); i++) {
			GameObject obj = bgObjects.get(i);
			
			obj.update(dt);
			if(obj.isMarkedForRemoval()) {
				bgObjects.remove(i--);
			}
		}
		for(int i = 0; i < fgObjects.size(); i++) {
			GameObject obj = fgObjects.get(i);
			
			obj.update(dt);
			if(obj.isMarkedForRemoval()) {
				fgObjects.remove(i--);
			}
		}
	}
	
	public void updateEntity(float dt, int i) {
		// Overwrite this to add more entity stuff within the update loop
	}
	
	public void handleInput(float dt, Vector2 mouse, float roll) {
		
	}
}
