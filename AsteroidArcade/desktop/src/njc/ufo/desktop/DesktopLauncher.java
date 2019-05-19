package njc.ufo.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import njc.asteroids.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = Game.TITLE;
		config.width = Game.WIDTH;
		config.height = Game.HEIGHT;
		
		config.addIcon("icons/icon-256.png", FileType.Internal);
		config.addIcon("icons/icon-128.png", FileType.Internal);
        config.addIcon("icons/icon-64.png", FileType.Internal);
        config.addIcon("icons/icon-32.png", FileType.Internal);
		
		new LwjglApplication(new Game(), config);
	}
}
