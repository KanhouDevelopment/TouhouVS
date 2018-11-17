package novus.gdx.graphics;

import java.io.BufferedReader;
import java.io.FileReader;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import novus.gdx.assets.Assets;

public class Animation {
	/*
	 * 1 spritesheet
	 * Nr of animations
	 * Frames for each animation
	 * Animation speed for each animation (nr of 1/60s ticks before next frame)
	 * Width & Height for each animation
	 */
	
	//private texture ...
	private String spriteSheet;
	private int nrOfAnims;
	private int[] nrOfFrames;
	private int[] animSpeed;
	private int[] frameHeights;
	private int[] frameWidths;
	private int currentAnimation;
	private int currentFrame;
	private int ticks;
	
	public Animation(String filePath) {
		currentAnimation = 0;
		ticks = 0;
		currentFrame = 0;
		load(filePath);
	}
	
	private void load(String filePath) {
		/*
		 * Config file structure:
		 * Spritesheet: nameOfFile (The animation calls for the spritesheet from a static hashmap using the name)
		 * Animations: nrOfAnimations
		 * 		-nrOfFrames
		 * 		-frameHeight
		 * 		-frameWidth
		 * 		-animationSpeed
		 */
		try {
			BufferedReader in = new BufferedReader(new FileReader(filePath));
			String[] firstLineArray = in.readLine().split(" ");
			String[] secondLineArray = in.readLine().split(" ");
			
			spriteSheet = firstLineArray[1];
			nrOfAnims = Integer.parseInt(secondLineArray[1]);
			nrOfFrames = new int[nrOfAnims];
			animSpeed = new int[nrOfAnims];
			frameHeights = new int[nrOfAnims];
			frameWidths = new int[nrOfAnims];
			
			for(int i = 0; i < nrOfAnims; ++i) {
				String[] nrFrame = in.readLine().split(" ");
				String[] frameH = in.readLine().split(" ");
				String[] frameW = in.readLine().split(" ");
				String[] aniSpeed = in.readLine().split(" ");
				
				nrOfFrames[i] = Integer.parseInt(nrFrame[1]);
				frameHeights[i] = Integer.parseInt(frameH[1]);
				frameWidths[i] = Integer.parseInt(frameW[1]);
				animSpeed[i] = Integer.parseInt(aniSpeed[1]);
				
			}
			
			in.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void changeAnimation(int anim) {
		if(currentAnimation != anim && anim < nrOfAnims) {
			currentAnimation = anim;
			ticks = 0;
			currentFrame = 0;
		}
	}
	
	public void update() {
		ticks++;
		if(ticks == animSpeed[currentAnimation]) {
			currentFrame++;
			
			if(currentFrame == nrOfFrames[currentAnimation]) {
				currentFrame = 0;
			}
			ticks = 0;
		}
		
	}
	
	public TextureRegion getTex() {
		return new TextureRegion(Assets.SPRITESHEETS.get(spriteSheet), currentFrame*frameWidths[currentAnimation], currentAnimation*frameHeights[currentAnimation],
				frameWidths[currentAnimation], frameHeights[currentAnimation]);
	}
	
}
