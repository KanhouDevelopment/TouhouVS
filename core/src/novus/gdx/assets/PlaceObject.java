package novus.gdx.assets;


import java.io.BufferedReader;
import java.io.FileReader;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import novus.gdx.graphics.Animation;

public class PlaceObject {
	private PointLight light;
	private Animation anim;
	private ParticleEffect emit;
	private String tex = "";
	private int x, y;
	
	private int maxRadius = 0;
	private int minRadius = 0;
	private int currentRadius = 0;
	private int updateStep = 1;
	private float posX, posY;
	
	boolean useLight = false;
	boolean useAnimation = false;
	boolean useParticles = false;
	
	private final float RENDER_TO_WORLD = 1/96f;
	private final float WORLD_TO_RENDER = 96f;
	
	public PlaceObject(String filePath, RayHandler rh, int x, int y) {
		this.x = x;
		this.y = y;
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(filePath));
			useLight = Boolean.parseBoolean(in.readLine().split(" ")[1]);
			useAnimation = Boolean.parseBoolean(in.readLine().split(" ")[1]);
			useParticles = Boolean.parseBoolean(in.readLine().split(" ")[1]);
			
			if(useLight) {
				String[] line = in.readLine().split(" ");
				float r = Float.parseFloat(line[1]);
				float g = Float.parseFloat(line[2]);
				float b = Float.parseFloat(line[3]);
				float a = Float.parseFloat(line[4]);
				
				line = in.readLine().split(" ");
				maxRadius = Integer.parseInt(line[1]);
				line = in.readLine().split(" ");
				minRadius = Integer.parseInt(line[1]);
				currentRadius = minRadius;
				
				posX = x*64 * RENDER_TO_WORLD + 32*RENDER_TO_WORLD;
				posY = -y*64 * RENDER_TO_WORLD + -48*RENDER_TO_WORLD;
				light = new PointLight(rh, 256, new Color(r, g, b, a), minRadius*RENDER_TO_WORLD, posX, -posY);
				light.setSoft(true);
				light.setStaticLight(true);
				light.setXray(false);
				light.update();
				rh.update();
			}
			
			if(useAnimation) {
				
			} else {
				tex = in.readLine().split(" ")[1];
			}
			
			if(useParticles) {
				
			}
			in.close();
		} catch (Exception e) {
			
		}
	}
	
	public void update() {
		if(updateStep > 0 && currentRadius >= maxRadius) {
			updateStep *= -1;
		} else if(updateStep < 0 && currentRadius <= minRadius) {
			updateStep *= -1;
		}
		
		currentRadius += updateStep;
		light.setDistance(currentRadius*RENDER_TO_WORLD);
		light.update();
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Texture getTex() {
		return Assets.USE_OBJECT.get(0);
	}
}
