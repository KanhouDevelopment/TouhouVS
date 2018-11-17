package novus.gdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;

import novus.gdx.game.GameHandler;
import novus.gdx.game.GameScreen;

public class TitleMenu extends ScreenAdapter {
	final GameHandler game;
	
	public TitleMenu(GameHandler game) {
		this.game = game;
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE) {
                    game.setScreen(new GameScreen(game));
                }
                return true;
            }
        });
	}
	
	@Override
	public void render(float delta) {
		System.out.println("Title Menu!!!");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

}
