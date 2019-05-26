package io.itch.nickjconnors.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import io.itch.nickjconnors.AsteroidArcadeGame;

public class HtmlLauncher extends GwtApplication {

        // USE THIS CODE FOR A FIXED SIZE APPLICATION
        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(AsteroidArcadeGame.WIDTH, AsteroidArcadeGame.HEIGHT);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new AsteroidArcadeGame();
        }
}