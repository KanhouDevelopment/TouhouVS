package novus.gdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

import novus.gdx.game.GameHandler;

public class IntroTitleSplash extends ScreenAdapter {
	final GameHandler game;
	
	public IntroTitleSplash(GameHandler game) {
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
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		System.out.println("Intro Title Splash");
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
