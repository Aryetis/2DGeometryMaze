package com.projetandroid;

import org.andengine.entity.primitive.Rectangle;

public class Wall {
	// un mur est un simple rectangle à dimensions et positions variables
	// un mur noir (type==2||n'importe quel autre valeur différent de 1 ou 0) <=> visible constament
	// un mur vert (type==0) visible uniquement si curWorld==true (dans gameScene)
	// un mur rouge (type==1) visible uniquement si curWorld==false (dans gameScene)
	public int type;
	public Rectangle sprite;
	
    Wall(float xPos, float yPos, float width, float height, int type) {
    	this.type = type;
        sprite = new Rectangle(xPos, yPos, width, height, BaseActivity.getSharedInstance()
        												 .getVertexBufferObjectManager());
        switch (type){	
    	case 0: sprite.setColor(0,1.0f,0);
    			break;
    	case 1:
    			sprite.setColor(1.0f,0,0);
    			break;
    	default:
				sprite.setColor(0f,0f,0f);
				break;        			
    }
    }
}
