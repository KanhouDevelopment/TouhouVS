package novus.gdx.graphics;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;

import novus.gdx.world.Level;

public class Camera {

	private final float WORLD_TO_RENDER = 96f;
	private final float RENDER_TO_WORLD = 1/96f;
	
	private OrthographicCamera worldCamera, lightCamera;
	private Matrix4 cameraBox2D;
	
	private novus.gdx.characters.Character character;
	private Level world;
	
	private boolean staticCamera = false;
	
	public Camera(novus.gdx.characters.Character character, float w, float h, Level world) {
		worldCamera = new OrthographicCamera(w, h);
		lightCamera = new OrthographicCamera(w*RENDER_TO_WORLD, h*RENDER_TO_WORLD);
		
		if(character == null) {
			staticCamera = true;
		}
		
		float xc = 0;
		float yc = 0;
		
		if(staticCamera) {
			xc = w/2;
			yc = -h/2 + 64;
		} else {
			xc = character.getBoxX()*WORLD_TO_RENDER + w/2 - character.getWidth()*WORLD_TO_RENDER - 0.5f * WORLD_TO_RENDER;
			yc = character.getBoxY()*WORLD_TO_RENDER + h/2 - character.getHeight()*WORLD_TO_RENDER - 0.5f * WORLD_TO_RENDER;
		}
		
		this.character = character;
		
		worldCamera.position.set(xc, yc, 0);
		lightCamera.position.set(xc*RENDER_TO_WORLD, yc*RENDER_TO_WORLD, 0);
		
		worldCamera.update();
		lightCamera.update();
		
		this.world = world;
	}
	
	public void update() {
		if(!staticCamera) {
			float charX = character.getBody().getPosition().x;
			float charY = character.getBody().getPosition().y;
			float charWidth = character.getWidth();
			float charHeight = character.getHeight();
			
			float camX = worldCamera.position.x;
			float camY = worldCamera.position.y;
			float camWidth = worldCamera.viewportWidth;
			float camHeight = worldCamera.viewportHeight;
			
			float camBorderX = 3f * WORLD_TO_RENDER;
			
//			System.out.println("Character x is: " + (charX-charWidth/2)*WORLD_TO_RENDER);
//			System.out.println("Camera border is: " + (camX - camWidth/2));
//			System.out.println("Second border is: " + ((camX - camWidth/2)+camBorder));
			
			if(((charX-charWidth/2)*WORLD_TO_RENDER) <= ((camX - camWidth/2)+camBorderX) && !character.goingRight() && camX-camWidth/2 > 0) {
//				float xc = (charX+charWidth)*WORLD_TO_RENDER + camWidth/2 - 0.6f * WORLD_TO_RENDER;
				float xc = charX*WORLD_TO_RENDER + camWidth/2 - charWidth/2*WORLD_TO_RENDER - camBorderX;
				float yc = camY;
				worldCamera.position.set(xc, yc, 0);
				lightCamera.position.set(xc*RENDER_TO_WORLD, yc*RENDER_TO_WORLD, 0);
			} else if ((charX+charWidth/2)*WORLD_TO_RENDER >= ((camX + camWidth/2)-camBorderX) && character.goingRight() && camX+camWidth/2 <= world.getMapWidth()*64) {
				float xc = charX*WORLD_TO_RENDER - camWidth/2 + charWidth/2*WORLD_TO_RENDER + camBorderX;
				float yc = worldCamera.position.y;
				worldCamera.position.set(xc, yc, 0);
				lightCamera.position.set(xc*RENDER_TO_WORLD, yc*RENDER_TO_WORLD, 0);
			}
			
			if((character.getBoxY()+character.getHeight())*WORLD_TO_RENDER < (worldCamera.position.y - worldCamera.viewportHeight/2) + 2.5f*WORLD_TO_RENDER) {
				
				float xc = worldCamera.position.x;
				float yc = character.getBoxY()*WORLD_TO_RENDER + worldCamera.viewportWidth/2 - character.getHeight()*WORLD_TO_RENDER - 3f * WORLD_TO_RENDER;
				worldCamera.position.set(xc, yc, 0);
				lightCamera.position.set(xc*RENDER_TO_WORLD, yc*RENDER_TO_WORLD, 0);
				
			} else if ((charY+charHeight/2)*WORLD_TO_RENDER > (camY + camHeight/2) - 2f*WORLD_TO_RENDER) {
				float xc = worldCamera.position.x;
				float yc = charY*WORLD_TO_RENDER + charHeight/2*WORLD_TO_RENDER + 2f * WORLD_TO_RENDER - camHeight/2 ;
				worldCamera.position.set(xc, yc, 0);
				lightCamera.position.set(xc*RENDER_TO_WORLD, yc*RENDER_TO_WORLD, 0);
			}
		}
		
		
		worldCamera.update();
		lightCamera.update();
		
		cameraBox2D = worldCamera.combined.cpy();
		cameraBox2D.scl(WORLD_TO_RENDER);
	}
	
	public OrthographicCamera getWorld() {
		return worldCamera;
	}
	
	public OrthographicCamera getLight() {
		return lightCamera;
	}
	
	public Matrix4 getDebug() {
		return cameraBox2D;
	}
	
	public float WORLD_TO_RENDER() {
		return WORLD_TO_RENDER;
	}
	
	public float RENDER_TO_WORLD() {
		return RENDER_TO_WORLD;
	}
	
}
