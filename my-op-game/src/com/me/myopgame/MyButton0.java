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
	protected final MyStage0 myStage0;
	
	public MyButton0(MyStage0 stage, final String buttonName, Skin skin, String styleName, Vector2 position) {
				
		super(buttonName, skin, styleName);
		
		this.myStage0 = stage;
		
		clickListener = new ClickListener(){
	            @Override 
	            public void clicked(InputEvent event, float x, float y) {
	            	if(buttonName == "map0")
	            	{
	            		myStage0.mapDefined = true;
	            		myStage0.screen0.map = new TmxMapLoader().load("map/map0.tmx");
	            		myStage0.screen0.renderer = new OrthogonalTiledMapRenderer(myStage0.screen0.map);
	            	}

	            	if(buttonName == "map1")
	            	{
	            		myStage0.mapDefined = true;
	            		myStage0.screen0.map = new TmxMapLoader().load("map/map1.tmx");
	            		myStage0.screen0.renderer = new OrthogonalTiledMapRenderer(myStage0.screen0.map);
	            	}
	            	if(buttonName == "go go go !!!")
	            		if (myStage0.screen0.renderer != null)
	            			myStage0.gogogo = true;
	            	if(buttonName == "reset")
	            		myStage0.reset();
	            };
		};
		this.addListener(clickListener);
        
		this.getLabel().setColor(0, 0, 0, 1);
		this.setWidth(256f);
        this.setHeight(30f);
        
        this.setPosition(position.x,position.y);
	}	
}
