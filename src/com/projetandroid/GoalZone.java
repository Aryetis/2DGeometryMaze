package com.projetandroid;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;



public class GoalZone {
	//classe représentant la zone d'arrivée, zone à atteindre avec la bille
	
	public Rectangle sprite;
    public static GoalZone instance;
    Camera mCamera;
    boolean moveable;
 
    public static GoalZone getSharedInstance() {
        if (instance == null)
            instance = new GoalZone();
        return instance;
    }
    public static GoalZone getSharedInstance(float xEnd, float yEnd) {
        if (instance == null)
            instance = new GoalZone(xEnd, yEnd);
        return instance;
    }
	public void finishHim() //tout comme bille, pas de finalize a cause du garbageCollector
    {
		sprite.detachSelf();
    }
    
    private GoalZone() {
    	moveable = true;
        sprite = new Rectangle(60, 60, 30, 30, BaseActivity.getSharedInstance()
            .getVertexBufferObjectManager());
        sprite.setColor(0.75f,0,0.75f);
 
        mCamera = BaseActivity.getSharedInstance().mCamera;
        sprite.setPosition(mCamera.getWidth() / 2 - sprite.getWidth() / 2,
            mCamera.getHeight() /2 - sprite.getHeight() /2);
    }
    private GoalZone(float xSpawn, float ySpawn) {
    	moveable = true;
        sprite = new Rectangle(xSpawn, ySpawn, 30, 30, BaseActivity.getSharedInstance()
            .getVertexBufferObjectManager());
        sprite.setColor(0.75f,0,0.75f);
 
        mCamera = BaseActivity.getSharedInstance().mCamera;
    }
	
}
