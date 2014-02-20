package com.me.myopgame;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MyButton0 extends TextButton {
	
	ClickListener clickListener;
	protected final MyScreen0 myScreen0;
	
	public MyButton0(MyScreen0 screen, final String mapName, Skin skin, String styleName, Vector2 position) {
				
		super(mapName, skin, styleName);
		
		this.myScreen0 = screen;
		
		clickListener = new ClickListener(){
	            @Override 
	            public void clicked(InputEvent event, float x, float y) {
	            	if(mapName == "map0")
	            	{
	            		myScreen0.map = new TmxMapLoader().load("map/map0.tmx");
	            		myScreen0.renderer = new OrthogonalTiledMapRenderer(myScreen0.map);
	            	}

	            	if(mapName == "map1")
	            	{
	            		myScreen0.map = new TmxMapLoader().load("map/map1.tmx");
	            		myScreen0.renderer = new OrthogonalTiledMapRenderer(myScreen0.map);
	            	}
	            };
		};
		this.addListener(clickListener);
        
		this.setWidth(256f);
        this.setHeight(20f);
        
        this.setPosition(position.x,position.y);
	}	
}
