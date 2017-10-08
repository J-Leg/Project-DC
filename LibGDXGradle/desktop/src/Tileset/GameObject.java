package Tileset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import State.Coordinates;

// Game object is in charge of: image/sprite, ObjectType, Coordinates
public class GameObject extends Actor {
	public static enum ObjectType {
		PLAYER, ENEMY, TERRAIN
	}
	
	ObjectType type;
	private Coordinates position;
	private Image image;
	private Sprite sprite;
	// height and width are in Actor


	public GameObject(ObjectType type, int width, int height, Coordinates position) {
		this.type = type;
		this.setSize(width,height);
		this.position = position;
		this.image = null; // set by object's subclass
		this.sprite = null; // set by object's subclass
	}
	
	
	
	public ObjectType getType() {
		return this.type; 
	}
	
	public void setType(ObjectType type) {
		this.type = type;
	}
	
	public Coordinates getCoord() {
		return position;
	}
	
	
	public void setCoord(Coordinates coord) {
		position = coord;
	}
	
	
	public Image getImage() {
		return image;
	}

	
	// use the whole image
	public void setImage(String imageName) {
		Texture tilemap = new Texture(Gdx.files.internal(imageName));
		Image image = new Image(tilemap);
		this.image = image;
	}
	
	
	// use a subsection via texture region at a given position? TESTING NEEDED
	public void setImage(String imageName, int x, int y, float f, float g) {
		Texture tilemap = new Texture(Gdx.files.internal(imageName));
		TextureRegion region = new TextureRegion(tilemap, x, y, (int)f, (int)g);
		Image image = new Image(region);
		this.image = image;
	}
		
	
	public Sprite getSprite() {
		return sprite;
	}	
	
	
	public void setSprite(String imageName, int x, int y, float f, float g) {
		Texture texture = new Texture(Gdx.files.internal(imageName));
		this.sprite = new Sprite(texture, x, y, (int)f, (int)g);
	}
}
