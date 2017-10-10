package Interface.Stages;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Toolbar extends Stage{
	private TextureAtlas atlas;
	private Skin skin;
	private Editor related;
	private TableTuple toolbarPos;

	/*
	 * Dimensions: 280 x 40
	 */
	public Toolbar(Viewport v, TextureAtlas atlas, Skin skin) {
		super(v);
		this.atlas = atlas;
		this.skin = skin;
		this.toolbarPos = new TableTuple(100, 20);
		initialise();
	}
	
	private void initialise() {		
		Table mainTable = new Table();
		int pad = 0;
		
		for(final ToolbarSelection s: ToolbarSelection.values()) {
			TextButton button = generateButton(s.name());
			mainTable.add(button).pad(5);	
			pad += 5;
			button.addListener(new ClickListener(){
				@Override
		        public void clicked(InputEvent event, float x, float y) {
					switch(s){
					case CREATURE:
						related.update(ToolbarSelection.CREATURE);
						break;
					case TERRAIN:
						related.update(ToolbarSelection.TERRAIN);
						break;
					default:
						break;
					}
		        }
			});
		}
		mainTable.setPosition(toolbarPos.getX() + pad, toolbarPos.getY(), 0);
		
		// Add this actor
		super.addActor(new Image(new TextureRegion(new Texture(Gdx.files.internal("EditorScreen/tb_bg.png")))));
		super.addActor(mainTable);
	}
	
	private TextButton generateButton(String s) {
		TextButton button = new TextButton(s, skin);
		return button;
	}
	
	public void setDependence(Stage s) {
		this.related = s;
	}
	
	public void setDependence(Editor s) {
		this.related = s;
	}
}
