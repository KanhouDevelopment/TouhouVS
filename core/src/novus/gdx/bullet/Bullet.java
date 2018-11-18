package novus.gdx.bullet;

import java.io.BufferedReader;
import java.io.FileReader;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import novus.gdx.assets.Assets;
import novus.gdx.controller.XBox360Pad;
import novus.gdx.graphics.Animation;
import novus.gdx.other.Utils;
import novus.gdx.world.Level;

public class Bullet {
	private int x, y;
	private int prevX, prevY;
	private String name;
	
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
	public int BULLET_SPEED = 10;
	public Boolean isActive = false;
	private float travelingAngleX;
	private float travelingAngleY;
	private float vectorLength;
	
	public Bullet(String filePath, int x, int y, RayHandler rh) {
		this.x = x;
		this.y = y;
		
		//Create body
		load(filePath, rh);
	}
	
	private void load(String filePath, RayHandler rh) {
		
		System.err.println("Loading bullets");
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
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	public int getPrevX() {
		return prevX;
	}
	public int getPrevY() {
		return prevY;
	}
	
	public float getTravelingAngleX() {
		return travelingAngleX;
	}
	public float getTravelingAngleY() {
		return travelingAngleY;
	}
	
	public void setTravelingAngleX(float travelingAngleX) {
		this.travelingAngleX = travelingAngleX;
	}
	public void setTravelingAngleY(float travelingAngleY) {
		this.travelingAngleY = travelingAngleY;
	}
	
	public void update() {
		this.moveX();
		this.moveY();
	}
	
//	public void moveX(float mx) {
//		body.setLinearVelocity(mx, body.getLinearVelocity().y);
//		
//		if(mx > 0)
//			right = true;
//		else if( mx < 0 )
//			right = false;
//		
//		if(((body.getPosition().x - width/2)*WORLD_TO_RENDER <= 0 && !right) || ((body.getPosition().x + width/2)*WORLD_TO_RENDER >= levelWorld.getMapWidth()*64 && right)) {
//			body.setLinearVelocity(0, body.getLinearVelocity().y);
//		}
//	}
	public void moveX() {
		
		this.x += this.travelingAngleX * this.BULLET_SPEED;
		
	}
	public void moveY() {
		
		this.y += this.travelingAngleY * -this.BULLET_SPEED;
	}
	
	public Texture getTex() {
		return Assets.TEXTURES.get("bullet.png");
	}
	
//	public void moveX(float mx) {
//		body.setLinearVelocity(mx, body.getLinearVelocity().y);
//		
//		if(mx > 0)
//			right = true;
//		else if( mx < 0 )
//			right = false;
//		
//		if(((body.getPosition().x - width/2)*WORLD_TO_RENDER <= 0 && !right) || ((body.getPosition().x + width/2)*WORLD_TO_RENDER >= levelWorld.getMapWidth()*64 && right)) {
//			body.setLinearVelocity(0, body.getLinearVelocity().y);
//		}
//	}
//	
//	public void moveY(float my) {
//		body.setLinearVelocity(body.getLinearVelocity().x, my);
//	}
	
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setPrevX(int prevX) {
		this.prevX = prevX;
	}
	public void setPrevY(int prevY) {
		this.prevY = prevY;
	}
	
}
