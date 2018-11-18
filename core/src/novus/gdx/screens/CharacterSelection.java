package novus.gdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import novus.gdx.game.GameHandler;
import novus.gdx.game.GameScreen;

public class CharacterSelection extends ScreenAdapter{
	final GameHandler game;
	Texture background = new Texture(Gdx.files.internal("../core/assets/TitleBackground.png"));
	Texture frame = new Texture(Gdx.files.internal("../core/assets/SelectionFrame.png"));
	Texture darklayer = new Texture(Gdx.files.internal("../core/assets/DarkLayer.png"));
	
	public CharacterSelection(GameHandler game) {
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
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}
	
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		
		game.batch.draw(background, 0, 0);
		game.batch.draw(darklayer, 0, 0);
		game.batch.draw(frame, 0, 0);
		
		game.batch.end();
	}
	
}
