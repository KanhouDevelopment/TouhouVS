package novus.gdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import novus.gdx.assets.Assets;
import novus.gdx.controller.ControllerHandler;
import novus.gdx.screens.*;

public class GameHandler extends Game{

	public SpriteBatch batch;
	public ControllerHandler ctrlHandler;
	
	@Override
	public void create() {
		// TODO: Add creation
		batch = new SpriteBatch();
		Assets.loadTextures();
		ctrlHandler = new ControllerHandler();
		setScreen(new KanhouSplash(this));
	}
	
	@Override
	public void dispose() {
		//TODO: Remove creation
		batch.dispose();
	}

}
