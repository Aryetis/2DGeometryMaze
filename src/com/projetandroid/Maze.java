package com.projetandroid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import android.net.NetworkInfo.DetailedState;
import android.os.Environment;
import android.util.Log;

/*	
 * Impossible de sérializer des rectangles, pour celà qu'on sérialize/desérialize des Maze avec des Walls vide !!!
 * Que l'on remplit après une éventuelle sérialization 
 * ou déserialisation à l'aide de WallsParametersArrayList
 */

public class Maze implements Serializable{

	private static final long serialVersionUID = 39393939; 	
	public String mazeFilePath;
	public static Maze instance;
	public ArrayList<Wall> Walls;
	public ArrayList<WallParameters> WallsParametersArrayList;
	public ArrayList<HighScore> highscores;
	public float xStart; public float yStart;
	public float xEnd; public float yEnd;

	
	
    public static Maze getSharedInstance() {
        if (instance == null)
            instance = new Maze(0f, 0f, 0f, 0f,"");
        return instance;
    }
	
    Maze(){
    	Walls = new ArrayList<Wall>();
		WallsParametersArrayList = new ArrayList<WallParameters>();
		highscores = new ArrayList<HighScore>();
		this.xStart = 0;
		this.yStart = 0;
		this.xEnd = 0;
		this.yEnd = 0;
		mazeFilePath="";
    }
	Maze(float xStart, float yStart, float xEnd, float yEnd, String MazeCreationFilePath){
		Walls = new ArrayList<Wall>();
		WallsParametersArrayList = new ArrayList<WallParameters>();
		highscores = new ArrayList<HighScore>();
		this.xStart = xStart;
		this.yStart = yStart;
		this.xEnd = xEnd;
		this.yEnd = yEnd;
		mazeFilePath = MazeCreationFilePath;
	}
    public void finishHim()
    {
    	mazeFilePath = null;
    	instance = null;
    	for(Wall w: Walls)
    		w.sprite.detachSelf();
    	Walls=null;
    	WallsParametersArrayList=null;
    	highscores=null;
    }
	
	void addWallParameters(float xPos, float yPos, float width, float height, int type){
		WallsParametersArrayList.add(new WallParameters(xPos, yPos, width, height, type));
	}
	
	void InitializeWalls(){
		//DONT USE BEFORE WRITE SERIALIZATION !!! Rectangle is not serializable
		
		for(WallParameters element: WallsParametersArrayList){
			Walls.add(new Wall( element.xPos, element.yPos,
								element.width, element.height,
								element.type )) ;
		}
	}
		
	public int addHighScore(int count, int nbSwitch) //renvoit la position où insérer
	{
		//on ajoute le score courant dans la arrayList des highscore en respectant l'ordre croissant sur timer/count
		int cpt=0;
		
		if (highscores.isEmpty())
			{highscores.add(new HighScore(count, nbSwitch)); return 0;}

		for(HighScore h: highscores)
		{
			if(h.timer < count) {cpt++; continue ;}
			if(h.timer >= count){highscores.add(cpt, new HighScore(count, nbSwitch)); return cpt;}
		}	
		highscores.add(new HighScore(count, nbSwitch)); 
		return cpt;
	}
	public void serializeMaze(){
		//WARNING IT WILL DELETE "ArrayList<Wall> Walls" CONTENT 
		//Because it's impossible to serialize "rectangle" (see in Wall definition)
		Walls = new ArrayList<Wall>();
		
		File file = new File(mazeFilePath);
		File parentDir = new File(file.getParentFile().getAbsolutePath());
		if (!(parentDir.exists() && parentDir.isDirectory())) {
			parentDir.mkdirs();
		}
		
		try {
			file.createNewFile();
		    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file)); 
		    oos.writeObject(this); 
		    oos.flush(); 
		    oos.close();
		}
	    catch (Exception e){
	    	if (e.getMessage() != null)
	        	Log.v("Serialization Save Error", e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	public static Maze deserializeMaze(String mazeFilePathToDeserialize){
	    try
	    {
			File file = new File(mazeFilePathToDeserialize) ;
			FileInputStream fis = new FileInputStream(file);
		    ObjectInputStream is = new ObjectInputStream(fis);
		    instance = (Maze) is.readObject();
		    is.close(); fis.close();
	    }
	    catch (Exception e){
	    	if (e.getMessage() != null)
	        	Log.v("Serialization Read Error", "Bad .maze file:\n"+e.getMessage());
	    	else
	    		Log.v("Serialization Read Error", "Bad .maze file");
	        e.printStackTrace();
	        return null;
	    }
	    return instance;
	}
	
}
