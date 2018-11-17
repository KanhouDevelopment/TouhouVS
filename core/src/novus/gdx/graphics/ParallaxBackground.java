package novus.gdx.graphics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import novus.gdx.world.Level;

public class ParallaxBackground {
	private List<String> textures = new ArrayList<String>();
	private List<Integer> startPoint = new ArrayList<Integer>();
	private List<Integer> endPoint = new ArrayList<Integer>();
	
	public ParallaxBackground(String filePath, Camera cam) {
		int width = (int)cam.getWorld().viewportWidth/2;
		try {
			BufferedReader in = new BufferedReader(new FileReader(filePath));
			int nrOfImages = Integer.parseInt(in.readLine().split(" ")[1]);
			
			for(int i = 0; i < nrOfImages; ++i) {
				String[] line = in.readLine().split(" ");
				textures.add(line[0]);
				startPoint.add(width);
				int end = Integer.parseInt(line[1]) - width;
				endPoint.add(end);
			}
			
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private float getCurrentLocation(int start, int end, int camPercentage) {
		return (end-start)*camPercentage + start;
	}
	
	private TextureRegion getRegion(String tex, int x) {
		return null;
	}
	
	public void draw(SpriteBatch sb, Camera cam) {
		for(int i = 0; i < textures.size(); ++i) {
			int start = startPoint.get(i);
			int end = endPoint.get(i);
			sb.draw(getRegion(textures.get(i), (int)getCurrentLocation(start, end, 0)), cam.getWorld().position.x, cam.getWorld().position.y);
		}
	}
}
