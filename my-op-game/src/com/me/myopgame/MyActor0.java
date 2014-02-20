package com.me.myopgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MyActor0 extends Actor {
	
	SpriteBatch batch;
	public Texture texture;
	
	public MyActor0() {	
        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("data/blackActor0_32_32.png"));
        this.setName("actor0");
	}
	
    public void draw(SpriteBatch batch, float alpha){
        batch.draw(texture,this.getX(),this.getY());
    }
}