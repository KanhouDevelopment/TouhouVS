package novus.gdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import novus.gdx.game.GameHandler;

public class KanmonSplash extends ScreenAdapter {
	final GameHandler game;
	Texture logo = new Texture(Gdx.files.internal("../core/assets/kanmon.png"));
	Sprite sp = new Sprite(logo);
	float alpha = 0f;
	float alphaInc = 0.02f;
	int fadeInTicks = 3;
	int stayTicks = 0;
	int counter = 0;
	boolean doRender = true;
	
	public KanmonSplash(GameHandler game) {
		this.game = game;
		sp.setAlpha(alpha);
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
		//game.batch.setProjectionMatrix(cam.getWorld().combined);
		if(counter >= fadeInTicks) {
			counter = 0;
			
			
			if(stayTicks == 0) {
				alpha += alphaInc;
				if(alpha >= 1.0f) {
					alphaInc *= -1;
					stayTicks = 20;
				} else if(alpha < 0f) {
					doRender = false;
					alpha = 0;
					game.setScreen(new TitleMenu(game));
				}
				
				if(alpha > 1.0f) {
					alpha = 1.0f;
				}
				sp.setAlpha(alpha);
			} else {
				stayTicks--;
			}
			
			
		}
		
		if(doRender) {
			System.out.println("Alpha: " + alpha);
			game.batch.begin();
			
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			//game.batch.draw(logo, 0, 0);
			
			sp.draw(game.batch);
			game.batch.end();
			counter++;
		}
		
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
	
	
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}


}
