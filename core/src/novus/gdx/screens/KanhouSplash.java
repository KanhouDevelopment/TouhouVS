package novus.gdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;

import novus.gdx.game.GameHandler;

public class KanhouSplash extends ScreenAdapter {
	final GameHandler game;
	
	public KanhouSplash(GameHandler game) {
		this.game = game;
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE) {
                    game.setScreen(new TitleMenu(game)); 
                }
                return true;
            }
        });
	}
	
	@Override
	public void render(float delta) {
		System.out.println("KANHOU DEVELOPMENT!!!!");
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
