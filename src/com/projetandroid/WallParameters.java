package com.projetandroid;

import java.io.Serializable;

public class WallParameters implements Serializable {
	/*
	 * Etant donn� que l'objet Rectangle de andEngine n'est pas s�rialisable,
	 * mais qu'il nous faut quand meme le sauvegarder ...
	 * On va sauvegarder les param�tres qui nous ont servi a sa cr�ation
	 * et le recr�er � chaque deserialization (cr�ation d'objet depuis un fichier)
	 * cf Maze pour les m�thodes de serialization et deserialization
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
