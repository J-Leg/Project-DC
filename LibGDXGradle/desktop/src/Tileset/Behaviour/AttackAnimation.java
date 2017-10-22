package Tileset.Behaviour;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import State.Coord;
import State.State;
import Tileset.DynamicObject;
import Tileset.GameObject.ObjectType;

public class AttackAnimation extends Actor implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7134641387590361982L;
	
	private final int NUM_FRAMES = 4;
	
	private Coord coord;
	
	private Sprite[] animationFrames;
	
	private String path = "SpriteFamily/attack/";
	
	private Animation animation;
	private Direction dir;
	
	private enum Type {
		ONE, TWO, THREE, FOUR
	}

	
	public AttackAnimation() {
		this.animationFrames = new Sprite[NUM_FRAMES];
		// load the attack image
		this.coord = new Coord(0,0);
		this.dir = Direction.EAST;
		this.loadImage(Type.ONE);
	}
	
	public AttackAnimation(Direction dir, Coord coord) {
		this.animationFrames = new Sprite[NUM_FRAMES];
		this.coord = coord;
		this.dir = dir;
		// load the attack image
		this.loadImage(Type.ONE);
	}
	
	public Animation getAttack() {
		return animation;
	}
	
	public Sprite[] getAnimationFrames() {
		return this.animationFrames;
	}
	
	


	

	

	private void loadImage(Type type) {
		switch (type) {
		case ONE:
			path += "cut_a/";
			break;
		case TWO:
			path += "cut_b/";
			break;
		case THREE:
			path += "cut_c/";
			break;
		default:
			path += "cut_d/";
			break;
		}
		
		for (int i = 0; i < NUM_FRAMES; i++) {
			String filePath = path + (i + 1) + ".png";
			Sprite sprite = new Sprite(new Texture(filePath));
			// sets the size and position
			sprite.setBounds(this.coord.getX() * 40, this.coord.getY() * 40, 40, 40);
			// sets the rotation origin to the middle
			sprite.setOrigin(sprite.getWidth()/2f, sprite.getHeight()/2f);
			// rotate
			sprite.setRotation(this.getRotation2());
			
			animationFrames[i] = sprite;
		}
		animation = new Animation(1f/20f, animationFrames);
	}
	
	private float getRotation2() {
		switch (this.dir) {
		case WEST:
			return 0;
		case EAST:
			return 180;
		case SOUTH:
			return 90;
		case NORTH:
			return 270;
		default:
			return 0;	
		}
	}
	
	float time = 0f;
	@Override
	public void act(float delta) {
		time += delta;
	}
	
	public void add(Coord c) {
		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		 if (time < this.animation.getAnimationDuration()) {
			 Sprite s = (Sprite)this.animation.getKeyFrame(time,false);
		 	 s.draw(batch);
		 } else if (time > this.animation.getAnimationDuration()) {
			 this.remove();
		 }
	}
	
	

}
