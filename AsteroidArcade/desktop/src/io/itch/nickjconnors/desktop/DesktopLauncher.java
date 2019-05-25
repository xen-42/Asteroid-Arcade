package io.itch.nickjconnors.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import io.itch.nickjconnors.AsteroidArcadeGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = AsteroidArcadeGame.TITLE;
		config.width = AsteroidArcadeGame.WIDTH;
		config.height = AsteroidArcadeGame.HEIGHT;
		
		config.addIcon("icons/icon-256.png", FileType.Internal);
		config.addIcon("icons/icon-128.png", FileType.Internal);
        config.addIcon("icons/icon-64.png", FileType.Internal);
        config.addIcon("icons/icon-32.png", FileType.Internal);
		
		new LwjglApplication(new AsteroidArcadeGame(), config);
	}
}