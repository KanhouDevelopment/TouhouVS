package novus.gdx.characters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import novus.gdx.controller.XBox360Pad;
import novus.gdx.graphics.Animation;
import novus.gdx.other.Utils;
import novus.gdx.world.Level;
import novus.gdx.bullet.Bullet;
import novus.gdx.bullet.BulletHandler;

public class Character implements ControllerListener{
	private int x, y;
	private int prevX, prevY;
	private String name;
	private Controller controller;
	
	private Body body;
	private Animation anim;
	
	private TextureRegion[][] animation;
	private int frameTicks = 0;
	private final int aniSpeed = 6;
	private int current_frame = 0;
	private int ticks = 0;
	
	private boolean isFalling = false;
	private boolean isJumping = false;
	
	private float width = 0.5f;
	private float height = 1.0f;
	
	private boolean right, down;
	
	private PointLight pl;
	private float lightChange = 1f;
	private float sightRadius = 0;
	
	final private float RENDER_TO_WORLD = 1/96f;
	final private float WORLD_TO_RENDER = 96f;
	
	private Level levelWorld;
	
	private Bullet bullet;
	public int MAX_NUMBER_OF_BULLETS = 20;
	public int bulletCounter;
	private float shootAngleX;
	private float shootAngleY;
	private Boolean shootButtonDown = false;
	
	public List<Bullet> bulletPool = new ArrayList<>();
	
	
	/*
	 * paramaters: config file, x, y, world
	 * width & height and Animation will be retrieved from the config file
	 */
	public Character(String filePath, int x, int y, World world, RayHandler rh, Level levelWorld) {
		this.x = x;
		this.y = y;
		
		bulletCounter = 0;
		
		//Create body
		load(filePath, rh);
		BodyDef def = new BodyDef();
		def.type = BodyDef.BodyType.DynamicBody;
		float bodyX = ((float) x)/96 + this.width;
		float bodyY = ((float) y)/96 + this.height;
		def.position.set(bodyX, bodyY);
		body = world.createBody(def);
		body.setFixedRotation(true);
		PolygonShape shape = new PolygonShape();
		shape.set(Utils.correctedBoxShape(this.width, this.height, 0.05f));
		FixtureDef fixt = new FixtureDef();
		fixt.shape = shape;
		fixt.density = 1.f;
		body.createFixture(fixt);
		shape.dispose();
		
		setLight(rh);
		
		this.levelWorld = levelWorld;
		
		for(int i = 0; i < 20; i++) {
			bulletPool.add(new Bullet("../core/assets/Bullets/bullet.txt", getX(), getY(), rh));
		}
		
		//Create animations
		//TODO: Swap out for the improved general animation system
		
	}
	
	private void setLight(RayHandler rh) {
		pl = new PointLight(rh, 256, new Color(1f, 1f, 1f, 0.6f), sightRadius*RENDER_TO_WORLD, body.getPosition().x, body.getPosition().y + height/4);
		pl.setSoft(true);
		pl.setStaticLight(true);
	}
	
	
	public Controller getController() {
		return this.controller;
	}
	
	public void setController(Controller ctrl) {
		this.controller = ctrl;
		this.controller.addListener(this);
	}
	
	
	private void load(String filePath, RayHandler rh) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(filePath));
			String[] first = in.readLine().split(" ");
			String[] second = in.readLine().split(" ");
			String[] third = in.readLine().split(" ");
			String[] fourth = in.readLine().split(" ");
			String[] fifth = in.readLine().split(" ");
			in.close();
			
			name = first[1];
			anim = new Animation("../core/assets/animations/" + second[1]);
			height = Float.parseFloat(third[1]);
			width = Float.parseFloat(fourth[1]);
			sightRadius = Integer.parseInt(fifth[1]);
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void moveX(float mx) {
		body.setLinearVelocity(mx, body.getLinearVelocity().y);
		
		if(mx > 0)
			right = true;
		else if( mx < 0 )
			right = false;
		
		if(((body.getPosition().x - width/2)*WORLD_TO_RENDER <= 0 && !right) || ((body.getPosition().x + width/2)*WORLD_TO_RENDER >= levelWorld.getMapWidth()*64 && right)) {
			body.setLinearVelocity(0, body.getLinearVelocity().y);
		}
	}
	
	public void jumpY(float my) {
		//body.setLinearVelocity(body.getLinearVelocity().x, my);
		if(!isJumping && !isFalling)
			body.applyLinearImpulse(0, my, body.getPosition().x, body.getPosition().y, true);
		if(my < 0)
			down = true;
		else
			down = false;
	}
	
	public void moveY(float my) {
		body.setLinearVelocity(body.getLinearVelocity().x, my);
	}
	
	
	public TextureRegion getTex() {
		return anim.getTex();
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public Body getBody() {
		return body;
	}
	
	public int getX() {
		return (int) (body.getPosition().x * WORLD_TO_RENDER);		
	}
		
	public int getY() {
		return (int) (body.getPosition().y * WORLD_TO_RENDER);
	}
		
	public int getPrevX() {
		return prevX;		
	}
		
	public int getPrevY() {
		return prevY;
	}
	
	public void setX(int sx) {
		x = sx;
	
	}
	
	public void setY(int sy) {
		y = sy;
	
	}
	
	public void setPrevX(int spx) {
		prevX = spx;
	}
		
	public void setPrevY(int spy) {
		prevY = spy;
	}
	
	public boolean goingRight() {
		return right;
	}
	
	public boolean goingDown() {
		return down;
	}
	
	public float getBoxX() {
		return body.getPosition().x - this.width;
	}
	
	public float getBoxY() {
		return body.getPosition().y - this.height;
	}
	
	public TextureRegion getTex(int i) {
		return anim.getTex();
	}
	
	public void update() {
		float moveSpeed = 3;
		
		if(controller != null) {
			
			if(controller.getAxis(XBox360Pad.AXIS_LEFT_X) > 0.4f || controller.getAxis(XBox360Pad.AXIS_LEFT_X) < -0.4f ) {
				this.moveX(controller.getAxis(XBox360Pad.AXIS_LEFT_X) * moveSpeed);
				Gdx.app.log("SIDA", controller.getAxis(XBox360Pad.AXIS_LEFT_X) + "");
			}
			else {
				this.moveX(0);
			}
			if(controller.getAxis(XBox360Pad.AXIS_LEFT_Y) > 0.4f || controller.getAxis(XBox360Pad.AXIS_LEFT_Y) < -0.4f ) {
				this.moveY(controller.getAxis(XBox360Pad.AXIS_LEFT_Y) * -moveSpeed);
				Gdx.app.log("VERTIKALT", controller.getAxis(XBox360Pad.AXIS_LEFT_Y) +"");
			}
			else {
				this.moveY(0);
			}
			
			if(shootButtonDown == false) {
				if(bulletCounter < MAX_NUMBER_OF_BULLETS) {
					bulletPool.get(bulletCounter).isActive = true;

					bulletPool.get(bulletCounter).setX(this.getX());
					bulletPool.get(bulletCounter).setY(this.getY());
					
					float shootAngleX = controller.getAxis(XBox360Pad.AXIS_RIGHT_X);
					float shootAngleY = controller.getAxis(XBox360Pad.AXIS_RIGHT_Y);
					
					float vectorLength = (float) Math.sqrt(shootAngleX*shootAngleX + shootAngleY*shootAngleY);
					bulletPool.get(bulletCounter).setTravelingAngleX(shootAngleX/ vectorLength);
					bulletPool.get(bulletCounter).setTravelingAngleY(shootAngleY/ vectorLength);
					
					bulletCounter++;
					
					shootButtonDown = true;
				}
				
			}
			
			
		}
		
		//ticks for falling/jumping
		if(body.getLinearVelocity().y != 0) {
			ticks++;
		}
		else if (!isJumping){
			ticks = 0;
		}
			
		
		//When n ticks have been reached, go to jumping/falling state
		if(ticks > 5) {
			if(body.getLinearVelocity().y > 0) {
				isJumping = true;
				isFalling = false;
				anim.changeAnimation(3);
			}
			else {
				isJumping = false;
				isFalling = true;
				anim.changeAnimation(2);
			}
				
		}
		else {
			isJumping = false;
			isFalling = false;
		}
		
		//Standing still
		if( body.getLinearVelocity().x == 0 && !isJumping && !isFalling) {
			anim.changeAnimation(2);
		} else if(right == true && !isJumping && !isFalling ) {
			anim.changeAnimation(0);
		} else if(right != true && !isJumping && !isFalling ) {
			anim.changeAnimation(1);
		}
		
		anim.update();
		
		pl.setPosition(body.getPosition().x, body.getPosition().y + height/4);
		
		if(pl.getDistance()*WORLD_TO_RENDER < 570 || pl.getDistance()*WORLD_TO_RENDER > 630)
			lightChange *= -1;
		pl.setDistance((pl.getDistance()*WORLD_TO_RENDER - lightChange)*RENDER_TO_WORLD);
		
		pl.update();
	}
	
	public void draw(SpriteBatch batch, float WORLD_TO_RENDER) {
		//idle
		////
		if( body.getLinearVelocity().x == 0 && !isJumping && !isFalling) {
			if(right) {
				batch.draw(getTex(0), getBoxX()*WORLD_TO_RENDER, getBoxY()*WORLD_TO_RENDER);
			}
			else
				batch.draw(getTex(0),(getBoxX()+width*2)*WORLD_TO_RENDER, getBoxY()*WORLD_TO_RENDER,
							-(getTex(0).getRegionWidth()),(getTex(0).getRegionHeight()));
		}
		///////////
		//Going right 
		else if(right == true && !isJumping && !isFalling ) {
			batch.draw(getTex(1),getBoxX()*WORLD_TO_RENDER,getBoxY()*WORLD_TO_RENDER);
		}
		//Going up and right
		else if( right == true && isJumping ) {
			batch.draw(getTex(2),getBoxX()*WORLD_TO_RENDER,getBoxY()*WORLD_TO_RENDER);
			
		}
		//Going down and right
		else if( right == true && isFalling ) {
			batch.draw(getTex(2),getBoxX()*WORLD_TO_RENDER,getBoxY()*WORLD_TO_RENDER);
		}
		////////////
		//Going left 
		else if(right != true && !isJumping && !isFalling ) {
			batch.draw(getTex(1),(getBoxX()+width*2)*WORLD_TO_RENDER, getBoxY()*WORLD_TO_RENDER,
					-(getTex(0).getRegionWidth()),(getTex(0).getRegionHeight()));
		}
		
		//Going up and left
		else if( right != true && isJumping ) {
			batch.draw(getTex(2),(getBoxX()+width*2)*WORLD_TO_RENDER, getBoxY()*WORLD_TO_RENDER,
					-(getTex(0).getRegionWidth()),(getTex(0).getRegionHeight()));
		}
		//Going down and left
		else if( right != true && isFalling ) {
			batch.draw(getTex(2),(getBoxX()+width*2)*WORLD_TO_RENDER, getBoxY()*WORLD_TO_RENDER,
					-(getTex(0).getRegionWidth()),(getTex(0).getRegionHeight()));
			
		}

		
	}

	@Override
	public void connected(Controller controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnected(Controller controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean buttonDown(Controller controller, int buttonCode) {
		// TODO Auto-generated method stub
		
		if(buttonCode == XBox360Pad.BUTTON_RB && shootButtonDown == false) {
			//System.out.println("button Down: " + shootButtonDown);
			shootButtonDown = true;
		}
		
//		if(buttonCode == XBox360Pad.BUTTON_A) {
//			System.out.println("Shooting!");
//			
//		}
		
		return false;
	}

	@Override
	public boolean buttonUp(Controller controller, int buttonCode) {
		if(buttonCode == XBox360Pad.BUTTON_RB && shootButtonDown == true) {
			//System.out.println("button UP: " + shootButtonDown);
			shootButtonDown = false;
		}
		
		return false;
	}

	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value) {
		// TODO Auto-generated method stub
		
//		if(axisCode == XBox360Pad.AXIS_RIGHT_X ) {
//			if(controller.getAxis(axisCode) > 0.4f || controller.getAxis(axisCode) < -0.4f) {
//				shootAngleX = value ;
//				//System.out.println("Aiming " + "--- X: " + value );
//
//			}
//		}
//			
//		if(axisCode == XBox360Pad.AXIS_RIGHT_Y) {
//			if(controller.getAxis(axisCode) > 0.4f || controller.getAxis(axisCode) < -0.4f) {
//				shootAngleY = value ;
//				//System.out.println("Aiming " + "--- Y: " + value );
//			}
//		}
		
//		if(axisCode == XBox360Pad.AXIS_RIGHT_TRIGGER) {
//			if(controller.getAxis(axisCode) > 0.2f || controller.getAxis(axisCode) < -0.2f) {
//
//				System.out.println("Shooting ");
//			}
//		}
//		
//		if(axisCode == XBox360Pad.AXIS_LEFT_TRIGGER) {
//			if(controller.getAxis(axisCode) > 0.2f || controller.getAxis(axisCode) < -0.2f) {
//
//				System.out.println("Left trigger ");
//			}
//		}
		
		
			
		
		
		//System.out.println("Axis" + controller.getAxis(axisCode) );
		
//		if(axisCode == XBox360Pad.AXIS_RIGHT_X ) {
//			System.out.println("Aiming " + "--- X: " + value );
//		}
//			
//		if(axisCode == XBox360Pad.AXIS_RIGHT_Y) {
//			System.out.println("Aiming " + "--- Y: " + value );
//		}
		return false;
	}

	@Override
	public boolean povMoved(Controller controller, int povCode, PovDirection value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
		// TODO Auto-generated method stub
		return false;
	}
}
