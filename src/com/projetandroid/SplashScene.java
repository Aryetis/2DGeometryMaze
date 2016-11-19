package com.projetandroid;

import java.io.File;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;

import com.projetandroid.R;

import android.os.Environment;

public class SplashScene extends Scene{

	static BaseActivity activity;

	public SplashScene() {
		
		// on créer notre première scène dans laquelle on fixe la couleur du fond d'écran
		// et affiche deux texte auxquels on affecte une animation (translation sur l'axe X)
		setBackground(new Background(0.09804f, 0.6274f, 0));
		activity = BaseActivity.getSharedInstance();
		Text title1 = new Text(0, 0, activity.mFont, activity.getString(R.string.title_1), activity.getVertexBufferObjectManager());
		Text title2 = new Text(0, 0, activity.mFont, activity.getString(R.string.title_2), activity.getVertexBufferObjectManager());		

		title1.setPosition(-title1.getWidth(), activity.mCamera.getHeight() / 2);
		title2.setPosition(activity.mCamera.getWidth(), activity.mCamera.getHeight() / 2);

		attachChild(title1);
		attachChild(title2);

		title1.registerEntityModifier(new MoveXModifier(1, title1.getX(), activity.mCamera.getWidth() / 2 - title1.getWidth()));
		title2.registerEntityModifier(new MoveXModifier(1, title2.getX(), activity.mCamera.getWidth() / 2));
		//doesnt wait end of translation ! so we can loadRessources

		/*************************************************************  
		 *    SERIALIZATION DE FICHIERS .MAZE A LA MAIN POUR EXEMPLES *
		 **************************************************************/		    

		String MazeCreationFilePath = Environment.getExternalStorageDirectory().getPath()+File.separator+"" +
				"gmData"+File.separator+"maps"+File.separator+"Test.maze" ;
		File fMaze = new File(MazeCreationFilePath);
		if(!fMaze.exists())
		{
			/*
			 * On créer un objet maze auquel on ajoute des Walls "à la main"
			 * que l'on sérialise, celui ci sera eventuellement chargé par la suite
			 * dans le menu de chargement selon l'utilisateur 
			 */
			Maze tmp1 = new Maze(110f, 300f, 400f, 350f, MazeCreationFilePath) ;
			tmp1.addWallParameters(100f, 220f, 50f, 10f, 0) ;
			tmp1.addWallParameters(140f, 260f, 50f, 10f, 1) ;
			tmp1.addWallParameters(190f, 300f, 50f, 10f, 2) ;
			tmp1.serializeMaze();
		}

		MazeCreationFilePath = Environment.getExternalStorageDirectory().getPath()+File.separator+"" +
				"gmData"+File.separator+"maps"+File.separator+"Level1.maze" ;
		File fMaze2 = new File(MazeCreationFilePath);
		if(!fMaze2.exists())
		{
			Maze tmp2 = new Maze(110f, 300f, 600f, 350f, MazeCreationFilePath) ;
			tmp2.addWallParameters(10f, 10f, 780f, 10f, 2) ;
			tmp2.addWallParameters(10f, 460f, 780f, 10f, 2) ;
			tmp2.addWallParameters(10f, 10f, 10f, 460f, 2) ;
			tmp2.addWallParameters(780f, 10f, 10f, 460f, 2) ;

			tmp2.addWallParameters(200f, 20f, 20f, 440f, 0) ;
			tmp2.addWallParameters(250f, 20f, 20f, 440f, 1) ;

			tmp2.addWallParameters(420f, 20f, 40f, 200f, 2) ;
			tmp2.addWallParameters(420f, 260f, 40f, 200f, 2) ;	
			tmp2.addWallParameters(10f, 50f, 10f, 80f, 2) ;
			tmp2.addWallParameters(780f, 10f, 10f, 460f, 2) ;
			tmp2.serializeMaze();
		}
		/*********************************************/	    				

		loadResources();
	}

	public void loadResources(){

		//Loading ressources (only check for .maze for now ... but maybe sprites and sounds later)
		String sdCardPath = Environment.getExternalStorageDirectory().getPath() ; //sans '/' a la fin
		File dir = new File(sdCardPath+File.separator+"gmData"+File.separator+"maps"+File.separator);
		if (!(dir.exists() && dir.isDirectory())) {
			dir.mkdirs();
		}
		String[] dirContent = dir.list(); //completePath 
		for(String element: dirContent){
			activity.avaibleMaps.add(sdCardPath+File.separator+"gmData"+File.separator+"maps"+File.separator+element);
		}


		DelayModifier dMod = new DelayModifier(2){
			@Override
			protected void onModifierFinished(IEntity pItem) {
				activity.setCurrentScene(new MainMenuScene());
			}
		};
		registerEntityModifier(dMod);

	}

}