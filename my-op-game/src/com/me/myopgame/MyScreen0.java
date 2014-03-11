package com.me.myopgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MyScreen0 implements Screen{
	
	protected MyOpGame game;
    protected MyStage0 stage0;
    protected TiledMap map;
    protected OrthogonalTiledMapRenderer renderer;
    protected Skin skin;
    
    public MyScreen0(MyOpGame game) {
    	
    	// allocate skin
    	skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        // link screen to game
    	this.game = game;
    	    	
    	// allocate stage
    	this.stage0 = new MyStage0(this);
    }
    
    @Override
	public void render(float delta) {
    	
    	this.stage0.render(delta);
    }

	@Override
	public void resize(int width, int height) {
		return;		
	}

	@Override
	public void show() {
		return;
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
		return;
	}

	@Override
	public void resume() {
		return;
	}

	@Override
	public void dispose() {
		this.stage0.dispose();
		this.map.dispose();
		this.renderer.dispose();
	}
}
