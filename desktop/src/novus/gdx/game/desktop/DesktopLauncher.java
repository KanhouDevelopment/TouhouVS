package novus.gdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import novus.gdx.game.GameHandler;
import novus.gdx.game.GameScreen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "Kanmon Engine";
		config.width = 1920;
		config.height = 1080;
		config.fullscreen = false;
		
		new LwjglApplication(new GameHandler(), config); 
	}
}
