package com.projetandroid;

import java.io.Serializable;

public class HighScore implements Serializable{
	// classe permettant de stocker les scores du joueur
	// les scores sont stockés dans Maze, donc chaque score est associé à son niveau
	// un score correspond au nombre de fois que le joueur a changer de monde 
	// et au temps passer pour résoudre le niveau
	private static final long serialVersionUID = 1996740502174663739L;
	public int timer;
	public int nbSwitch;

	
	public HighScore(){
		timer=0;
		nbSwitch=0;
	}
	public HighScore(int t, int n){
		timer=t;
		nbSwitch=n;
	}
	
}
