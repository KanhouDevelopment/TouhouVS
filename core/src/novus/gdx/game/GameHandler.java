package novus.gdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import novus.gdx.assets.Assets;
import novus.gdx.controller.ControllerHandler;
import novus.gdx.screens.*;

public class GameHandler extends Game{

	SpriteBatch batch;
	ControllerHandler ctrlHandler;
	
	@Override
	public void create() {
		// TODO: Add creation
		batch = new SpriteBatch();
		Assets.loadTextures();
		setScreen(new KanmonSplash(this));
		ctrlHandler = new ControllerHandler();
	}
	
	@Override
	public void dispose() {
		//TODO: Remove creation
		batch.dispose();
	}

}
