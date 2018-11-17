package novus.gdx.other;

import com.badlogic.gdx.math.Vector2;

public class Utils {
	public static Vector2[] correctedBoxShape(float width, float height, float heightSlope) {
		Vector2[] vertices = new Vector2[8];
		vertices[0] = new Vector2(-(width/2), height-heightSlope);
		vertices[1] = new Vector2(-(width/2 - 0.05f), height);
		
		vertices[2] = new Vector2(width/2 - 0.05f, height);
		vertices[3] = new Vector2(width/2, height - heightSlope);
		
		vertices[4] = new Vector2(width/2, -(height-heightSlope));
		vertices[5] = new Vector2(width/2 - 0.05f, -(height-heightSlope/2));
		
		vertices[6] = new Vector2(-(width/2 - 0.05f), -(height - heightSlope/2));
		vertices[7] = new Vector2(-(width/2), -(height - heightSlope));
		
		return vertices;
	}
}
