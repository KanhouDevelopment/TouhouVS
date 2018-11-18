package novus.gdx.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import novus.gdx.game.GameHandler;
import novus.gdx.game.GameScreen;
import novus.gdx.graphics.Animation;

public class CharacterSelection extends ScreenAdapter{
	final GameHandler game;
	
	List<Animation> anims = new ArrayList<>();
	
	Texture background = new Texture(Gdx.files.internal("../core/assets/TitleBackground.png"));
	Texture frame = new Texture(Gdx.files.internal("../core/assets/SelectionFrame.png"));
	Texture darklayer = new Texture(Gdx.files.internal("../core/assets/DarkLayer.png"));
	Texture arrowLeft = new Texture(Gdx.files.internal("../core/assets/ArrowLeft.png"));
	Texture arrowRight = new Texture(Gdx.files.internal("../core/assets/ArrowRight.png"));
	
	Animation anim = new Animation("../core/assets/animations/remilia.txt");
	
	int playerOneSelection = 0;
	int playerTwoSelection = 0;
	
	public CharacterSelection(GameHandler game) {
		this.game = game;
		anims.add(new Animation("../core/assets/animations/remilia.txt"));
		anims.add(new Animation("../core/assets/animations/marisa.txt"));
		anims.add(new Animation("../core/assets/animations/chen.txt"));
		anims.add(new Animation("../core/assets/animations/moukou.txt"));
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE) {
                    game.setScreen(new GameScreen(game));
                }
                if(keyCode == Input.Keys.RIGHT) {
                	playerTwoSelection++;
                	if(playerTwoSelection == anims.size()) {
                		playerTwoSelection = 0;
                	}
                } else if(keyCode == Input.Keys.LEFT) {
                	playerTwoSelection--;
                	if(playerTwoSelection < 0) {
                		playerTwoSelection = anims.size() - 1;
                	}
                }
                
                if(keyCode == Input.Keys.D) {
                	playerOneSelection++;
                	if(playerOneSelection == anims.size()) {
                		playerOneSelection = 0;
                	}
                } else if(keyCode == Input.Keys.A) {
                	playerOneSelection--;
                	if(playerOneSelection < 0) {
                		playerOneSelection = anims.size() - 1;
                	}
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
		
		for(int i = 0; i < anims.size(); ++i) {
			anims.get(i).update();
		}
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		
		game.batch.draw(background, 0, 0);
		game.batch.draw(darklayer, 0, 0);
		game.batch.draw(frame, 0, 0);
		
		int p1X = 445;
		int p1Y = 1080-270;
		
		game.batch.draw(anims.get(playerOneSelection).getTex(), p1X - anims.get(playerOneSelection).getTex().getRegionWidth()/2, 
				p1Y - anims.get(playerOneSelection).getTex().getRegionHeight()/2);
		
		int p2X = 1455;
		int p2Y = 1080-270;
		
		game.batch.draw(anims.get(playerTwoSelection).getTex(), p2X - anims.get(playerTwoSelection).getTex().getRegionWidth()/2, 
				p2Y - anims.get(playerTwoSelection).getTex().getRegionHeight()/2);
		
		game.batch.end();
	}
	
}
