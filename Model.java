/**
 * Created by Dastan21
 */

import javax.swing.*;
import java.awt.*;

class Model{
	protected final ImageIcon DISABLED = new ImageIcon("Images/disabled.png"); // DEBUG
	// Cases
	protected final String[][] TAB_CASES = new String[][]{
		{"gris", "vert", "rouge", "jaune", "rose", "violet", "bleu", "orange"},
		{"violet", "gris", "jaune", "bleu", "vert", "rose", "orange", "rouge"},
		{"bleu", "jaune", "gris", "violet", "rouge", "orange", "rose", "vert"},
		{"jaune", "rouge", "vert", "gris", "orange", "bleu", "violet", "rose"},
		{"rose", "violet", "bleu", "orange", "gris", "vert", "rouge", "jaune"},
		{"vert", "rose", "orange", "rouge", "violet", "gris", "jaune", "bleu"},
		{"rouge", "orange", "rose", "vert", "bleu", "jaune", "gris", "violet"},
		{"orange", "bleu", "violet", "rose", "jaune", "rouge", "vert", "gris"}
	};
	protected final String[][] TAB_COULEURS = new String[][]{
		{"#CCCCCC", "#61FF59", "#FF493F", "#FFEC51", "#FFC1F3", "#E947FF", "#4744FF", "#FFA947"},
		{"#E947FF", "#CCCCCC", "#FFEC51", "#4744FF", "#61FF59", "#FFC1F3", "#FFA947", "#FF493F"},
		{"#4744FF", "#FFEC51", "#CCCCCC", "#E947FF", "#FF493F", "#FFA947", "#FFC1F3", "#61FF59"},
		{"#FFEC51", "#FF493F", "#61FF59", "#CCCCCC", "#FFA947", "#4744FF", "#E947FF", "#FFC1F3"},
		{"#FFC1F3", "#E947FF", "#4744FF", "#FFA947", "#CCCCCC", "#61FF59", "#FF493F", "#FFEC51"},
		{"#61FF59", "#FFC1F3", "#FFA947", "#FF493F", "#E947FF", "#CCCCCC", "#FFEC51", "#4744FF"},
		{"#FF493F", "#FFA947", "#FFC1F3", "#61FF59", "#4744FF", "#FFEC51", "#CCCCCC", "#E947FF"},
		{"#FFA947", "#4744FF", "#E947FF", "#FFC1F3", "#FFEC51", "#FF493F", "#61FF59", "#CCCCCC"},
	};

	protected final String PATH_COUPS = "Images/Coups/";
	protected final String PATH_TOURS = "Images/Tours/";

	protected final String B_COUP = "B_coup";
	protected final String N_COUP = "N_coup";

	protected String[][] tabTours;
	protected String[][] tabCoups;

	protected int[] joueurs; // État des joueurs :
    protected final int ATTENTE = 0;
    protected final int COUPS = 1;
    protected final int JOUE = 2;
	protected int[] coords;
	protected int[] old_coords;
	protected String old_tour;
	protected String selectedTour;
	protected boolean isFirstRound;

	public Model(){
		newGame();
	}

	protected void newGame(){
		this.joueurs = new int[]{1,0};
		this.isFirstRound = true;
		tabTours = new String[][]{
			{"B_gris", "B_vert", "B_rouge", "B_jaune", "B_rose", "B_violet", "B_bleu", "B_orange"},
			{"", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", ""},
			{"N_orange", "N_bleu", "N_violet", "N_rose", "N_jaune", "N_rouge", "N_vert", "N_gris"}
		};
		tabCoups = new String[8][8];
	}

	public void changeEtatJoueurs(){
		for (int j = 0; j < 2; j++) {
			this.joueurs[j] = (this.joueurs[j]+1)%3;
		}
		if (this.joueurs[0] == this.JOUE)
			this.joueurs[1] = this.ATTENTE;
		if (this.joueurs[1] == this.JOUE)
			this.joueurs[0] = this.ATTENTE;
	}
}
