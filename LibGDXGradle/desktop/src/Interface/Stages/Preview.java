package Interface.Stages;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import Interface.Grid.GridCell;
import Interface.ImageID;
import Interface.ImageStack;

public class Preview extends Stage{
	private int rowActors;
	private int colActors;
	private Stage related;
	private TableTuple tablePos;
	
	
	/*
	 * Dimensions: 520 x 480
	 */
	public Preview(Viewport v, int viewWidth, int viewHeight, int cellWidth, int cellHeight) {
		super(v);
		this.rowActors = viewWidth/cellWidth - 2;
		this.colActors = viewHeight/cellHeight + 1;
		//this.tablePos = new TableTuple(-160, -400);
		initialise(cellWidth, cellHeight);
	}
	
	private void initialise(int cellWidth, int cellHeight) {
		//super.addActor(new Image(new TextureRegion(new Texture(Gdx.files.internal("EditorScreen/ground3.jpg")))));
		ImageStack[] cells = new ImageStack[rowActors * colActors];
		Table gridTable = new Table();
		
		for(int i = 0; i < rowActors; i++) {
			for(int j = 0; j < colActors; j++) {
				System.out.println("x: " + i + " y: " + j);
				GridCell gc = new GridCell(cellWidth, cellHeight);
				gridTable.add(gc);
				//ImageStack cell = ImageStack(new TextureRegion(new Texture(Gdx.files.internal("EditorScreen/empty.png"))));
				//super.addActor(gc);
				//gridTable.addActor(gc);
			}
			gridTable.row();
		}
		//gridTable.setPosition(tablePos.getX(), tablePos.getY());
		gridTable.top();
		gridTable.setFillParent(true);
		super.addActor(gridTable);
	}
	
	private ImageStack ImageStack(TextureRegion textureRegion) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setDependence(Stage s) {
		this.related = s;
	}
	
	public void setDependence(Editor s) {
		this.related = s;
	}
}
