package com.me.myopgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MyActor2 extends Actor {
	
	SpriteBatch batch;
	public Texture texture;
	
	public MyActor2() {	
        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("data/crtPanel_1024_128.png"));
	}
	
    public void draw(SpriteBatch batch, float alpha){
        batch.draw(texture,this.getX(),this.getY());
    }
}