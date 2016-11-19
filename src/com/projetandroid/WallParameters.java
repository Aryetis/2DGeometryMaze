package com.projetandroid;

import java.io.Serializable;

public class WallParameters implements Serializable {
	/*
	 * Etant donné que l'objet Rectangle de andEngine n'est pas sérialisable,
	 * mais qu'il nous faut quand meme le sauvegarder ...
	 * On va sauvegarder les paramètres qui nous ont servi a sa création
	 * et le recréer à chaque deserialization (création d'objet depuis un fichier)
	 * cf Maze pour les méthodes de serialization et deserialization
	 */
	private static final long serialVersionUID = 777777739; 	
	public float width;
	public float height;
	public float xPos;
	public float yPos;
	public int type;
	
	WallParameters(float xPos, float yPos, float width, float height, int type)
	{
		this.width = width;
		this.height = height;
		this.xPos = xPos;
		this.yPos = yPos;
		this.type = type;
	}
	
}
