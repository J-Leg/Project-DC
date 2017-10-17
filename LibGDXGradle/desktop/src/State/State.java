package State;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import Tileset.*;
import Tileset.GameObject.ObjectType;
import Interface.EditorModel;
import Interface.TileTuple;
import Interface.Stages.Editor;
import Interface.Stages.TableTuple;
import Interface.Stages.Selections.ToolbarSelection;


public class State extends Stage{
	
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_MAP_WIDTH = 50; // 50 tiles 
	private static final int DEFAULT_MAP_HEIGHT = 50;
	
	// Coords of player
	private Coord playerCoord;

	private int rowActors;
	private int colActors;
	private Stage related;

	private boolean has_player = false;			// Is this the appropriate place
	private TextureRegion selected_tr;
	private GameObject cur_object;
	
	private ArrayList<Tile> tileList;
	
	// Should rename it soon, Image Stack can hold more than one "layer" of object objects.
	private ToolbarSelection selectedLayer;
	
	// private List<List<Tile>> map;
	// The outer index is x, the inner index is y
	// private int mapWidth;
	// private int mapHeight;
	
	
	//************************//
	//******* CREATORS *******//
	//************************//
	
	// default create an empty State
	public State(Viewport v){
		super(v);
		this.rowActors = DEFAULT_MAP_HEIGHT;
		this.colActors = DEFAULT_MAP_WIDTH;
		this.tileList = new ArrayList<Tile>();
		initialise();
		
		// assumes no player initially
		this.playerCoord = null;
		
		// this.map = new ArrayList<List<Tile>>();
		
		// for(int i = 0; i < DEFAULT_MAP_WIDTH; i++) {
		// 	this.map.add(new ArrayList<Tile>());
		// 	for(int j = 0; j < DEFAULT_MAP_HEIGHT; j++) {
		// 		Coordinates tempCoord = new Coordinates(i,j);
		// 		this.map.get(i).add(new Tile(tempCoord));
		// 	}
		// }
		
		// this.mapWidth = DEFAULT_MAP_WIDTH;
		// this.mapHeight = DEFAULT_MAP_HEIGHT;
	}

	//************************//
	//***** INITIALISE *******//
	//************************//

	private void initialise() {
		Table gridTable = new Table();
		//ImageStack[] tiles = new ImageStack[rowActors * colActors];
		
		for(int i = 0; i < rowActors; i++) {
			for(int j = 0; j < colActors; j++) {
				//System.out.println("x: " + i + " y: " + j);
								
				final Tile tile = new Tile();
				tileList.add(tile);
				
				tile.addListener(new ClickListener(){
					@Override
			        public void clicked(InputEvent event, float x, float y) {
						// Allow for only one player per map (multiplayer possibly later)
						setTileTexture(tile, selectedLayer);
			        }
				});
				gridTable.add(tile).size(40, 40);
			}
			gridTable.row();
		}
		//gridTable.setPosition(tablePos.getX(), tablePos.getY());
		gridTable.top();
		gridTable.setFillParent(true);
		super.addActor(gridTable);
	}
	
	/* 
	 * Called by Editor.java when attempting to edit an enemy
	 * or player attribute
	 */
	public void isEditable() {
		System.out.println(selectedLayer);
	}

	public void setDependence(Stage s) {
		this.related = s;
	}
	
	public void setDependence(Editor s) {
		this.related = s;
	}
	
	public void setSelection(Texture t, ToolbarSelection s, GameObject icon) {
		// TODO Auto-generated method stub
		selected_tr = new TextureRegion(t);
		selectedLayer = s;
		if (s != ToolbarSelection.FLOOR) cur_object = icon;
	}
	
	public void fillGrid() {
		
		if(selected_tr == null || selectedLayer != ToolbarSelection.FLOOR) 
			return;
		
		Texture texture = selected_tr.getTexture();
		String path = ((FileTextureData)texture.getTextureData()).getFileHandle().name();
		System.out.println("Fill grid with : " + path);
		
		for(Tile tile : tileList) {
			// setTileTexture(tile, selectedLayer);
			setTileTexture(tile, ToolbarSelection.FLOOR);
		}
	}
	
	public void clearGrid() {		
		has_player = false;
		for(Tile tile : tileList) {
			tile.clear();
		}
	}
	
	/*
	 * Setting the tile texture, if it's an object we pass that instead
	 */
	private void setTileTexture(Tile tile, ToolbarSelection ts) {
		if (ts == null) return;
		switch (ts){
		case FLOOR:
			tile.setFloor(selected_tr);
			break;
		case ENEMY:
			if (tile.getObjectType() == ObjectType.PLAYER) this.has_player = false;			// Overwrite player
			tile.setObject(cur_object);
			break;
		case ITEM:
			if (tile.getObjectType() == ObjectType.ITEM) this.has_player = false;
			tile.setObject(cur_object);
			break;	
		case WALL:
			if (tile.getObjectType() == ObjectType.PLAYER) this.has_player = false;			// Overwrite player
			tile.setObject(cur_object);														// TODO
			break;
		case PLAYER:
			if (has_player == true) return;			// Don't add multiple players
			has_player = true;
			tile.setObject(cur_object);
			break;
		case SAVE:
			break;
		}
	}

	/*
	 * Check the map is valid before saving (e.g, at least one player)
	 * At least one tile, (check creatures on tile, etc. etc.)
	 */
	public boolean checkValidMap() {
		boolean no_err = true;
		
		if (has_player == false) {
			System.out.println("No player present, insert Player to fix");
			no_err = false;
		}
		
		for (Tile tile: tileList) {
			if (tile.isValid() == false) {
				System.out.println("Invalid object on empty tile");
				no_err = false;
			}
		}	
		
		return no_err;
	}
	
	//************************//
	//******* GENERAL ********//
	//************************//

	
	// TODO: get object from coord that is dynamic if able
	
	
	public GameObject getObject(Coord coord) {
		return this.tileList.get(coord.getX()  + coord.getY() * colActors).getObject();
	}
	

	
	/*
	public void setObject(GameObject newObject, Coord coord) {
		this.tileList.get(coord.getX()  + coord.getY() * colActors).setObject(newObject);
	}
	*/
	
	public void deleteObject(Coord coord) {
		this.tileList.get(coord.getX()  + coord.getY() * colActors).deleteObject();
	}
	
	/*
	 * Moves object, will overrite any object moving too
	 */ 
	public void moveObject(Coord from, Coord to) {
		GameObject toMove = getObject(from);
		deleteObject(from);
		getTile(to).setObject(toMove);
	}
	
	
	/*
	public void swapObject(Coord from, Coord to) {
		GameObject fromObject = getObject(from);
		GameObject toObject = getObject(to);
		setObject(fromObject, to);
		setObject(toObject, from);
	}
	*/
	
	public List<GameObject> getAllObjects() {
		List<GameObject> ret = new LinkedList<GameObject>();
		for (Tile ta : tileList) {	
			ret.add(ta.getObject());
		}
		return ret;
	}
	
	public List<DynamicObject> getAllDynamicObjects() {
		List<DynamicObject> ret = new LinkedList<DynamicObject>();
		for (Tile ta : tileList) {
			if(ta.getObject().isDynamic()) {
				ret.add((DynamicObject) ta.getObject());
			}
		}
		return ret;
	}
	
	
	//************************//
	//******* PLAYER *********//
	//************************//
	
	// Return coord of player
	public Coord findPlayer(){
		return this.playerCoord;
	}
	
	// Get player object
	public Player getPlayer(){
		if (playerCoord == null) return null;
		return (Player) getObject(playerCoord);
	}
	
	// Remove player from current coords and set player coords to -1,-1
	// Returns false if player is already deleted/not on the map
	public void deletePlayer(){
		deleteObject(playerCoord);
		// change to null
		playerCoord = null;
	}
	
	// Moves player to different tile
	// Returns false if player is already on that Tile
	
	/* CURRENTLY REDUNDANT but spawns errors
	 * 
	public void setPlayer(Coord to){
		Player currPlayer = this.getPlayer();
		this.deletePlayer();
		setObject(currPlayer, to);
	}
	*/
	
	// Same as setPlayer, redundant 
	public void movePlayer(Coord to){
		moveObject(playerCoord, to);
		playerCoord = to.clone();
	}
	
	
	
	//************************//
	//******* TERRAIN ********//
	//************************//

	public boolean hasWall(Coord pos) {
		GameObject g = getTile(pos).getObject();
		return (g != null) && (g.getType() == ObjectType.WALL);
	}
	
	public boolean isBlocked(Coord pos) {
		return getTile(pos).hasObject();
	}
	
	
	
	//************************//
	//******** TILES *********//
	//************************//
	
	public Tile getTile(Coord coord) {
		return this.tileList.get(coord.getX()  + coord.getY() * colActors); 
	}

	public void clearTile(Coord coord) {
		deleteObject(coord);
	}
	
	public int getMapWidth() {
		return colActors;
	}

	public int getMapHeight() {
		return rowActors;
	}

	public EditorModel getModel() {
		EditorModel model = new EditorModel(rowActors, colActors);
		
		// Conversion should not take place inside the object
		for(int i = 0; i < tileList.size(); i++) {
			int row_val = i/colActors;
			int col_val = i % colActors;
			
			Tile tile = tileList.get(i);
			ObjectType ID = tile.getObjectType();
			TileTuple t = new TileTuple(tile.getObjectPath(), tile.getTerrainPath(), ID);
			model.setTile(t, row_val, col_val);
		}
		return model;
	}
	

	/*
	 * Regenerate the textures from string paths
	 * Place back onto grid via direct calls instead of click listeners
	 */
	public void restoreModel(EditorModel m) {
		TileTuple[][] map = m.getmodelPaths();
		
		for(int i = 0; i < rowActors; i++) {
			for(int j = 0; j < colActors; j++) {
				int index = colActors*i + j;
				
				TileTuple t_tuple = map[i][j];
				Tile tile = tileList.get(index);

				// Set terrain
				if(t_tuple.getFloor() != null)
					tile.setFloor(new TextureRegion(new Texture(Gdx.files.internal(t_tuple.getFloor()))));
				
				
				// Set object
				if(t_tuple.getObject() != null) {
					TextureRegion cur_texture = new TextureRegion(new Texture(Gdx.files.internal(t_tuple.getObject())));
					GameObject new_obj = new GameObject(t_tuple.getID(), cur_texture);
					tile.setObject(new_obj);
				}
			}
		}
	}
	
	/*
	 * String splitting
	 */
	private ObjectType getType(String path) {
		String[] parts = path.split("/");
		
		for(ObjectType t : ObjectType.values()) {
			if(t.toString().toLowerCase().equals(parts[1]))
					return t;
		}
		return null;
	}
	
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ FOR CAMERA MOVEMENT ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	/*
	 * Movement involves left click followed by dragging motion
	 * Degree of movement by variable intensity
	 */
	/*
	private int dragX, dragY;
	private float intensity = 150f;
	
	
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		dragX = screenX;
		dragY = screenY;
		return true;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
	    float dX = (float)(dragX - screenX)/(float)Gdx.graphics.getWidth();
	    float dY = (float)(screenY - dragY)/(float)Gdx.graphics.getHeight();
	    dragX = screenX;
	    dragY = screenY;
	    
	    this.getCamera().position.add(dX * intensity, dY * intensity, 0f);
	    this.getCamera().update();
	    return true;
	}
	*/
}
