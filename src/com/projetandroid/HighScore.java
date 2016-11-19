package com.projetandroid;

import java.io.Serializable;

public class HighScore implements Serializable{
	// classe permettant de stocker les scores du joueur
	// les scores sont stock�s dans Maze, donc chaque score est associ� � son niveau
	// un score correspond au nombre de fois que le joueur a changer de monde 
	// et au temps passer pour r�soudre le niveau
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
