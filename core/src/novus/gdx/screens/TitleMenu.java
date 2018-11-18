package novus.gdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import novus.gdx.game.GameHandler;
import novus.gdx.game.GameScreen;

public class TitleMenu extends ScreenAdapter {
	final GameHandler game;
	Texture logo = new Texture(Gdx.files.internal("../core/assets/TitleLogo1.png"));
	Texture background = new Texture(Gdx.files.internal("../core/assets/TitleBackground.png"));
	
	Texture start1 = new Texture(Gdx.files.internal("../core/assets/StartGame2.png"));
	Texture start2 = new Texture(Gdx.files.internal("../core/assets/StartGame3.png"));
	
	Texture settings1 = new Texture(Gdx.files.internal("../core/assets/Settings1.png"));
	Texture settings2 = new Texture(Gdx.files.internal("../core/assets/Settings2.png"));
	
	Texture exit1 = new Texture(Gdx.files.internal("../core/assets/ExitGame1.png"));
	Texture exit2 = new Texture(Gdx.files.internal("../core/assets/ExitGame2.png"));
	
	/*
	 * 0: Start Game
	 * 1: Settings
	 * 2: Exit Game
	 */
	int currentSelection = 0;
	
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
                if(keyCode == Input.Keys.ENTER) {
                	if(currentSelection == 0) {
                		game.setScreen(new CharacterSelection(game));
                	}
                }
                return true;
            }
        });
	}
	
	private void input() {
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			currentSelection--;
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			currentSelection++;
		}
	}
	
	@Override
	public void render(float delta) {
		
		input();
		
		if(currentSelection < 0) {
			currentSelection = 2;
		} else if(currentSelection > 2) {
			currentSelection = 0;
		}
		
		
		int logoX = Gdx.graphics.getWidth()/2 - logo.getWidth()/2;
		int logoY = Gdx.graphics.getHeight() - logo.getHeight() - 50;
		
		int startX = Gdx.graphics.getWidth()/2 - start1.getWidth()/2;
		int startY = 350;
		
		int settingsX = Gdx.graphics.getWidth()/2 - settings1.getWidth()/2;
		int settingsY = startY - settings1.getHeight() - 20;
		
		int exitX = Gdx.graphics.getWidth()/2 - exit1.getWidth()/2;
		int exitY = settingsY - exit1.getHeight() - 20;
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		game.batch.draw(background, 0, 0);
		game.batch.draw(logo, logoX, logoY);
		
		if(currentSelection == 0)
			game.batch.draw(start2, startX, startY);
		else
			game.batch.draw(start1, startX, startY);
		
		if(currentSelection == 1)
			game.batch.draw(settings2, settingsX, settingsY);
		else
			game.batch.draw(settings1, settingsX, settingsY);
		
		if(currentSelection == 2)
			game.batch.draw(exit2, exitX, exitY);
		else
			game.batch.draw(exit1, exitX, exitY);
		
		game.batch.end();
	}
	
	
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

}
