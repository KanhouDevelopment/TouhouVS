package novus.gdx.assets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Assets {
	public static Map<String, Texture> SPRITESHEETS = new HashMap<String, Texture>();
	public static List<Texture> BLOCKS = new ArrayList<Texture>();
	public static List<Texture> BACK_OBJECT = new ArrayList<Texture>();
	public static List<Texture> USE_OBJECT = new ArrayList<Texture>();
	
	public static void loadTextures() {
		loadBlocks();
		loadBackObjects();
		USE_OBJECT.add(new Texture(Gdx.files.internal("../core/assets/torch_1.png")));
		SPRITESHEETS.put("GothLoli_Spritesheet2.png", new Texture(Gdx.files.internal("../core/assets/GothLoli_Spritesheet2.png")));
		SPRITESHEETS.put("testSheet.png", new Texture(Gdx.files.internal("../core/assets/testSheet.png")));
		SPRITESHEETS.put("moukou.png", new Texture(Gdx.files.internal("../core/assets/moukou.png")));
		SPRITESHEETS.put("remilia.png", new Texture(Gdx.files.internal("../core/assets/remilia.png")));
		SPRITESHEETS.put("chen.png", new Texture(Gdx.files.internal("../core/assets/chen.png")));
		SPRITESHEETS.put("marisa.png", new Texture(Gdx.files.internal("../core/assets/marisa.png")));
		SPRITESHEETS.put("basesprite.png", new Texture(Gdx.files.internal("../core/assets/basesprite.png")));
	}
	
	private static void loadBlocks() {
		BLOCKS.add(new Texture(Gdx.files.internal("../core/assets/block1.png")));
		BLOCKS.add(new Texture(Gdx.files.internal("../core/assets/block2.png")));
		BLOCKS.add(new Texture(Gdx.files.internal("../core/assets/grass.png")));
		BLOCKS.add(new Texture(Gdx.files.internal("../core/assets/wooden_box.png")));
		BLOCKS.add(new Texture(Gdx.files.internal("../core/assets/black_box.png")));
		BLOCKS.add(new Texture(Gdx.files.internal("../core/assets/floor.png")));
		BLOCKS.add(new Texture(Gdx.files.internal("../core/assets/wall.png")));
		BLOCKS.add(new Texture(Gdx.files.internal("../core/assets/wooden_floor_2.png")));
	}
	
	private static void loadBackObjects() {
		BACK_OBJECT.add(new Texture(Gdx.files.internal("../core/assets/wooden_window_2.png")));
		BACK_OBJECT.add(new Texture(Gdx.files.internal("../core/assets/wooden_wall_4.png")));
		BACK_OBJECT.add(new Texture(Gdx.files.internal("../core/assets/wooden_window_1.png")));
		BACK_OBJECT.add(new Texture(Gdx.files.internal("../core/assets/wooden_ladder_1.png")));
		BACK_OBJECT.add(new Texture(Gdx.files.internal("../core/assets/wooden_wall_2.png")));
		BACK_OBJECT.add(new Texture(Gdx.files.internal("../core/assets/wooden_wall_3.png")));
	}
	
	public static void dispose() {
		for(String key : SPRITESHEETS.keySet()) {
			SPRITESHEETS.get(key).dispose();
		}
		for(Texture tex : BLOCKS) {
			tex.dispose();
		}
		SPRITESHEETS = null;
		BLOCKS = null;
	}
}
