package novus.gdx.world;

import com.badlogic.gdx.physics.box2d.*;

public class Block {
	private int x, y;
	private Body body;
	private float width, height;
	
	Block(int x, int y, World world, float width, float height) {
		//Physics world count from middle point -> up/side
		this.width = width/2;
		this.height = height/2;
		
		//Create the physics body
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody; //Static, aka non-movable physics object for higher performance
		bodyDef.position.set(((float) x)/96 + this.width, ((float) y)/96 + this.height);
		body = world.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(this.width, this.height);
		FixtureDef fixt = new FixtureDef();
		fixt.shape = shape;
		fixt.density = 0.f;
		fixt.friction = 0.f;
		body.createFixture(fixt);
		
		shape.dispose();
	}
	
	public float getX() {
		return body.getPosition().x - width;
	}
	
	public float getY() {
		return body.getPosition().y - height;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
}
