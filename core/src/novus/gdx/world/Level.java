package novus.gdx.world;

import com.badlogic.gdx.physics.box2d.World;

import box2dLight.RayHandler;
import novus.gdx.assets.PlaceObject;

import java.io.BufferedReader;
import java.io.FileReader;

public class Level {
	/*
	 * Spawn point is set when a specific block ID is found
	 */
	
	private int[][] typeMatrix;
	private int[][] backMatrix;
	private PlaceObject[][] objectMatrix;
	
	private Block[][] worldBlocks; //Blocks used to create the physics world in Box2D
	private int spawnX, spawnY;
	private int SPAWN_ID = 17;
	private int numOfRows, numOfColumns;
	private String name;
	
	public Level(String fileName, World world, float RENDER_TO_WORLD) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			name = in.readLine();
			//String[] firstLineArray = in.readLine().split(" ");
			
			numOfColumns = Integer.parseInt(in.readLine());
			numOfRows = Integer.parseInt(in.readLine());
			
			
			spawnX = Integer.parseInt(in.readLine());
			spawnY = Integer.parseInt(in.readLine());
			
			typeMatrix = new int[numOfRows][numOfColumns];
			backMatrix = new int[numOfRows][numOfColumns];
			objectMatrix = new PlaceObject[numOfRows][numOfColumns];
			
			for(int y = 0; y < numOfRows; y++) {
				String[] rowArray = in.readLine().split(" ");
				
				for(int x = 0; x < rowArray.length; x++) {
					int val = Integer.parseInt(rowArray[x]);
					if(val == SPAWN_ID) {
						typeMatrix[y][x] = 0;
						spawnX = x;
						spawnY = y;
					} else {
						typeMatrix[y][x] = val;
					}
				}
			}
			
			for(int y = 0; y < numOfRows; y++) {
				String[] rowArray = in.readLine().split(" ");
				
				for(int x = 0; x < rowArray.length; x++) {
					int val = Integer.parseInt(rowArray[x]);
					backMatrix[y][x] = val;
					objectMatrix[y][x] = null;
				}
			}
			
			in.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
//		for(int y = 0; y < numOfRows; y++) {
//			for(int x = 0; x < numOfColumns; x++) {
//				System.out.print(typeMatrix[y][x] + " ");	
//			}
//			System.out.println();
//		}
		
		worldBlocks = new Block[numOfRows][numOfColumns];
		
		for(int y = 0; y < numOfRows; y++) {
			for(int x = 0; x < numOfColumns; x++) {
				if(typeMatrix[y][x] != 0) {
					//TODO: 1280 : Resolution in width, why is this even here? Should be second line instead
					//worldBlocks[y][x] = new Block(x*64, (1280-64)-y*64, world, 64*RENDER_TO_WORLD, 64*RENDER_TO_WORLD);
					worldBlocks[y][x] = new Block(x*64, -y*64, world, 64*RENDER_TO_WORLD, 64*RENDER_TO_WORLD);
				}
			}
		}
		
		
	}
	
	public int getSpawnX() {
		return spawnX;
	}
	
	public int getSpawnY() {
		return spawnY;
	}
	
	public int getMapWidth() {
		return numOfColumns;
	}
	
	public int getMapHeight() {
		return numOfRows;
	}
	
	public int getValue(int y, int x) {
		return typeMatrix[y][x];
	}
	
	public int getBackValue(int y, int x) {
		return backMatrix[y][x];
	}
	
	public PlaceObject getObjectValue(int y, int x) {
		return objectMatrix[y][x];
	}
	
	public void setLantern(int y, int x, RayHandler rayhandler) {
		y = y/64;
		x = x/64;
		System.out.println(rayhandler.toString());
		objectMatrix[-x][y] = new PlaceObject("../core/assets/interact/lantern.txt", rayhandler, y, x);
	}
}
