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
	
	public MyButton0(MyScreen0 screen, final String buttonName, Skin skin, String styleName, Vector2 position) {
				
		super(buttonName, skin, styleName);
		
		this.myScreen0 = screen;
		
		clickListener = new ClickListener(){
	            @Override 
	            public void clicked(InputEvent event, float x, float y) {
	            	if(buttonName == "map0")
	            	{
	            		myScreen0.map = new TmxMapLoader().load("map/map0.tmx");
	            		myScreen0.renderer = new OrthogonalTiledMapRenderer(myScreen0.map);
	            		// reset gogogo button, will force new path computation based on the new map
	            		myScreen0.gogogo = false;
	            	}

	            	if(buttonName == "map1")
	            	{
	            		myScreen0.map = new TmxMapLoader().load("map/map1.tmx");
	            		myScreen0.renderer = new OrthogonalTiledMapRenderer(myScreen0.map);
	            		// reset gogogo button, will force new path computation based on the new map
	            		myScreen0.gogogo = false;
	            	}
	            	if(buttonName == "go go go !!!")
	            	{
	            		if (myScreen0.renderer != null)
	            		{
	            			myScreen0.gogogo = true;
	            		}
	            	}
	            };
		};
		this.addListener(clickListener);
        
		this.getLabel().setColor(0, 0, 0, 1);
		this.setWidth(256f);
        this.setHeight(30f);
        
        this.setPosition(position.x,position.y);
	}	
}
