package com.projetandroid;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import android.util.Log;



public class LoadingLevelMenu extends MenuScene implements IOnMenuItemClickListener{

	BaseActivity activity;
	Maze loaded;
	
	
	public LoadingLevelMenu() {
		super(BaseActivity.getSharedInstance().mCamera);
		activity = BaseActivity.getSharedInstance();
//		loaded = Maze.getSharedInstance();
loaded = new Maze();

		setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		
		int nbrLvl = activity.avaibleMaps.size() ;
		IMenuItem[] Levels = new IMenuItem[nbrLvl] ;
		int cpt = 0;
		for(String element: activity.avaibleMaps)
		{
			Levels[cpt] = new TextMenuItem(cpt, activity.mFont, element, activity.getVertexBufferObjectManager());
			Levels[cpt].setPosition(mCamera.getWidth()/2 - Levels[cpt].getWidth()/2, cpt*(Levels[cpt].getHeight() + mCamera.getHeight()/20));
			addMenuItem(Levels[cpt]);
			cpt++;
		}
		
		setOnMenuItemClickListener(this);
	}
	
	@Override
	public boolean onMenuItemClicked(MenuScene arg0, IMenuItem arg1, float arg2, float arg3) {

		    String mazeFilePath = activity.avaibleMaps.get(arg1.getID());
		    loaded = Maze.deserializeMaze(mazeFilePath);
		    if (loaded != null)
		    	activity.setCurrentScene(new GameScene( loaded ));	    
		    //else la sérialization s'est mal passée (sans doute un mauvais fichier .maze, donc on attends que l'utilisateur charge un bon .maze
	    return false;
	}

}
