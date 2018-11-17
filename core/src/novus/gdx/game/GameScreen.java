package novus.gdx.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.World;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import novus.gdx.assets.Assets;
import novus.gdx.assets.PlaceObject;
import novus.gdx.graphics.Animation;
import novus.gdx.graphics.Camera;
import novus.gdx.world.Level;

public class GameScreen extends ScreenAdapter {
	SpriteBatch batch;
	private Texture backImage;
	private Level world;
	
	private boolean showDebug = false;
	private boolean showLight = true;
	private final float WORLD_TO_RENDER = 96f;
	private final float RENDER_TO_WORLD = 1/96f;
	private final float STEP = 1/60f;
	
	//------------------------------------
	private World box2DWorld;
	
	private RayHandler rayhandler;
	
	private Box2DDebugRenderer debugRender;
	private Camera cam;
	
	private Sound testSound;
	private Music testMusic;
	
	float camPosX = 1;
	float camPosY = 1;
	
	//private ParticleEffect snow;
	
	
	private List<novus.gdx.characters.Character> charList = new ArrayList<>();
	private novus.gdx.characters.Character testChar;
	private novus.gdx.characters.Character remiChar;
	
	PlaceObject po1, po2;
	
	final GameHandler game;
	
	public GameScreen(GameHandler game) {
		this.game = game;
		create();
	}
	
	public void create () {
		//Kî’šs vid startup, skapa de saker som behî’žs vid startup enbart, resterande kan ske pï¿½ andra stèˆ�len.
		Box2D.init();
		box2DWorld = new World(new Vector2(0, 0), true);
		
		rayhandler = new RayHandler(box2DWorld);
		rayhandler.setShadows(true);
		rayhandler.setAmbientLight(0.9f, 0.9f, 0.9f, 1f);
		rayhandler.setBlurNum(2);
		rayhandler.setCulling(true);
		RayHandler.useDiffuseLight(true);
		
		//create level
		world = new Level("../core/assets/TouhouVS2", box2DWorld, RENDER_TO_WORLD);
		
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		
//		worldCamera = new OrthographicCamera(width, height);
//		lightCamera = new OrthographicCamera(width*RENDER_TO_WORLD, height*RENDER_TO_WORLD);
		debugRender = new Box2DDebugRenderer();
		//Assets.loadTextures();
		
        backImage = new Texture(Gdx.files.internal("../core/assets/background.png"));
        
//        snow = new ParticleEffect();
//        snow.load(Gdx.files.internal("../core/assets/snow"), Gdx.files.internal("../core/assets"));
//        snow.start();
        
		batch = new SpriteBatch();
		
		//testAni = new Animation("../core/assets/gothloli.txt");
		testChar = new novus.gdx.characters.Character("../core/assets/characters/marisa.txt", world.getSpawnX()*64, -1*world.getSpawnY()*64, box2DWorld, rayhandler, world);
		charList.add(testChar);
		
		
		cam = new Camera(null, width, height, world);
	}

	@Override
	public void render(float delta) {
		
		//update function
		/*
		 * The update function should use a multithread approach
		 * Each part that can be split onto it's own should be on its own thread
		 * Examples:
		 * 	-Physics updates (Box2D)
		 * 	-Light updates (Box2D Lights), might have to be on physics thread
		 * 	-Input
		 * 	-Sound
		 * 	-Animation updates
		 * 	-A.I updates
		 * 	-World updates (Simulations etc) 
		 */
		
		update();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		batch.setProjectionMatrix(cam.getWorld().combined);
		batch.begin();
		float camPosX = cam.getWorld().position.x;
		float camPosY = cam.getWorld().position.y;
		
		batch.draw(backImage,camPosX - cam.getWorld().viewportWidth/2 ,camPosY - cam.getWorld().viewportHeight/2);
    	
		//Render the map from map object
    	for(int y = world.getMapHeight() - 1; y >= 0 ; y--) {
			for(int x = 0; x < world.getMapWidth(); x++) {
				if(world.getBackValue(y, x) != 0) {
					int h = Assets.BACK_OBJECT.get(world.getBackValue(y, x)-1).getHeight();
					int drawY = -(y*64) - (h-64);
		    		batch.draw(Assets.BACK_OBJECT.get(world.getBackValue(y, x)-1), x*64, drawY);
		    	}	
			}
		}
    	
    	//TODO: Draw interactive objects here
    	for(int y = world.getMapHeight() - 1; y >= 0 ; y--) {
			for(int x = 0; x < world.getMapWidth(); x++) {
				if(world.getObjectValue(y, x) != null) {
					int h = world.getObjectValue(y, x).getTex().getHeight();
					int drawY = -(y*64) - (h-64);
		    		batch.draw(world.getObjectValue(y, x).getTex(), x*64, drawY);
		    	}	
			}
		}
    	
    	//Render the map from map object
    	for(int y = world.getMapHeight() - 1; y >= 0 ; y--) {
			for(int x = 0; x < world.getMapWidth(); x++) {
				if(world.getValue(y, x) != 0) {
		    		batch.draw(Assets.BLOCKS.get(world.getValue(y, x)), x*64, -y*64);
		    	}
			}
		}
    	
    	//batch.draw(testAni.getTex(),camPosX*WORLD_TO_RENDER, camPosY*WORLD_TO_RENDER - worldCamera.viewportHeight/2  + 32);
    	for(int i = 0; i < charList.size(); ++i) {
    		if(charList.get(i) != null) {
    			batch.draw(charList.get(i).getTex(), (charList.get(i).getBoxX()-charList.get(i).getWidth()/2)*WORLD_TO_RENDER, charList.get(i).getBoxY()*WORLD_TO_RENDER);
    		}
    	}
    	
    	//TODO: Draw fore layer here!
    	
    	//snow.draw(batch, STEP);
    	
		batch.end();
		
		if(showLight) {
			rayhandler.setCombinedMatrix(cam.getLight());
	        rayhandler.updateAndRender();
		}
		
        
        if(showDebug) {
        	debugRender.render(box2DWorld, cam.getWorld().combined.cpy().scl(WORLD_TO_RENDER));
//        	debugRender.render(box2DWorld, cam.getDebug());
        }
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		
		//Dispose all assets
		Assets.dispose();
		rayhandler.dispose();
		box2DWorld.dispose();
	}
	
	private void input() {
		int speed = 3;
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			//Move left
//			testAni.changeAnimation(1);
			testChar.moveX(-speed);
			camPosX -= 0.1f;
		} else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			//Move right
//			testAni.changeAnimation(0);
			testChar.moveX(speed);
			camPosX += 0.1f;
		} else {
			//Stop moving in X
			testChar.moveX(0);
		}
		if(remiChar != null) {
			if(Gdx.input.isKeyPressed(Input.Keys.A)) {
				//Move left
//				testAni.changeAnimation(1);
				remiChar.moveX(-speed);
				//camPosX -= 0.1f;
			} else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
				//Move right
//				testAni.changeAnimation(0);
				remiChar.moveX(speed);
				//camPosX += 0.1f;
			} else {
				//Stop moving in X
				remiChar.moveX(0);
			}
			
			if(Gdx.input.isKeyPressed(Input.Keys.W)) {
				//Jump
//				testAni.changeAnimation(3);
				remiChar.moveY(speed);
				//camPosY += 0.1f;
			} else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
				remiChar.moveY(-speed);
			} else {
				remiChar.moveY(0);
			}
			
		}
		
		
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			//Jump
//			testAni.changeAnimation(3);
			testChar.moveY(speed);
			//camPosY += 0.1f;
		} else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			testChar.moveY(-speed);
//			testAni.changeAnimation(2);
			//camPosY -= 0.1f;
		} else {
			testChar.moveY(0);
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.G)) {
			showDebug = !showDebug;
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.L)) {
			showLight = !showLight;
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.P)) {
			testSound.play();
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.Y)) {
			remiChar = new novus.gdx.characters.Character("../core/assets/characters/remilia.txt", charList.get(0).getX(), charList.get(0).getY(), box2DWorld, rayhandler, world);
			charList.add(remiChar);
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.T)) {
			//Move left
//			testAni.changeAnimation(1);
			world.setLantern(charList.get(0).getX(), charList.get(0).getY(), rayhandler);
		}
	}

	private void update() {
		input();
		//update characters from input
		
		box2DWorld.step(STEP, 3, 3);
		
		camPosX = cam.getWorld().position.x;
		camPosY = cam.getWorld().position.y;
		float camX = camPosX*WORLD_TO_RENDER + 0;
		float camY = camPosY*WORLD_TO_RENDER + 0;

//		snow.setPosition(camPosX-cam.getWorld().viewportWidth/2, camPosY+cam.getWorld().viewportHeight/2);
		
		//lights
		//----------------------
		//TODO: Switch this to thread, optimize so that you don't search entire map
		for(int y = world.getMapHeight() - 1; y >= 0 ; y--) {
			for(int x = 0; x < world.getMapWidth(); x++) {
				if(world.getObjectValue(y, x) != null) {
		    		world.getObjectValue(y, x).update();
		    	}	
			}
		}
		//----------------------
		
		
		camX = 0 + 0;
		camY = 0 + 0;
//		lightCamera.position.set(camX, camY, 0);
//		lightCamera.update();
		
		cam.update();
		
		//TODO: Switch this to thread
		for(int i = 0; i < charList.size(); ++i) {
			if(charList.get(i) != null)
				charList.get(i).update();
		}
	}
	
}
