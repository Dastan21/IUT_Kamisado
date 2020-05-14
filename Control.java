/**
 * Created by Dastan21
 */

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.ActionEvent;
import java.awt.*;

public abstract class Control {
    Model model;
    Vue vue;

    public Control(Model model, Vue vue) {
    	this.model = model;
    	this.vue = vue;
    }

	// Lance une nouvelle partie
    protected void newGame(){
		model.newGame();
		initCases();
		initTours();
		initBoutons();
		updateBoutons();
    }

	protected void initCases(){
		KButton btn;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				btn = vue.getBouton(i,j);
				btn.setColor(Color.decode(model.TAB_COULEURS[i][j]));
				btn.setIcon(null);
			}
		}
	}

	protected void initTours(){
		KButton btn0, btn7;
		for (int j = 0; j < 8; j++) {
			btn0 = vue.getBouton(0,j); btn7 = vue.getBouton(7,j);
			btn0.setIcon(new ImageIcon(model.PATH_TOURS+model.tabTours[0][j]+".png"));
			btn7.setIcon(new ImageIcon(model.PATH_TOURS+model.tabTours[7][j]+".png"));
			btn0.setPath(model.PATH_TOURS);
			btn0.setType(model.tabTours[0][j]);
			btn7.setPath(model.PATH_TOURS);
			btn7.setType(model.tabTours[7][j]);
		}
	}

	protected void initBoutons(){
		KButton btn;
		for (int i = 1; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				btn = vue.getBouton(i,j);
				// btn.removeActionListener(vue.tabListeners[i][j]);
				btn.setPath(null);
				btn.setType(null);
			}
		}
	}

	protected void update(){
		// Coups
		resetCoups();
		if (model.joueurs[0] == model.COUPS || model.joueurs[1] == model.COUPS)
			updateCoups();
		// Tours
		if (model.joueurs[0] == model.JOUE || model.joueurs[1] == model.JOUE)
			updateTours();
		model.changeEtatJoueurs();
		// Boutons
		updateBoutons();
		String win = winner();
		if (win != null)
			endGame(win);
	}

	protected void updateBoutons(){
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				KButton btn = vue.getBouton(i,j);
				// System.out.print(model.tabTours[i][j]+" "); // DEBUG
				if ((!model.isFirstRound && model.tabTours[i][j].equals(model.selectedTour) || model.tabCoups[i][j] != null) || (model.isFirstRound && model.tabTours[i][j].startsWith("B_"))){
					if (btn.getActionListeners().length == 0)
						btn.addActionListener(vue.tabListeners[i][j]);
					btn.setState(true);
				} else {
					btn.removeActionListener(vue.tabListeners[i][j]);
					btn.setState(false);
				}
				// System.out.print(btn.getActionListeners().length+" "); // DEBUG
				// if (btn.getActionListeners().length == 0 && model.tabTours[i][j] == ""){
				// 	// btn.setIcon(model.DISABLED); // DEBUG
				// }
			}
			// System.out.println(); // DEBUG
		}
	}

	protected void updateCoups(){
		int[] coords = model.coords;
		model.old_coords = coords;
		model.old_tour = model.tabTours[coords[0]][coords[1]];
		String coup;
		int i, j;
		// Change color of coup
		if (model.joueurs[0] == model.COUPS){
			coup = model.B_COUP;
			// System.out.print("tour blanc : ");
		} else {
			coup = model.N_COUP;
			// System.out.print("tour noir : ");
		}
		// Check the button pressed
		i = coords[0]; j = coords[1];
		// If button not found
		if (coords == null){
			System.out.println("Erreur OutOfBoundsException");
			return;
		}
		// System.out.println("("+coords[0]+","+coords[1]+")");
		int n = 1;
		// HAUT
		while (i-n >= 0 && model.tabTours[i-n][j] == ""){
			model.tabCoups[i-n][j] = coup;
			vue.getBouton(i-n,j).setIcon(new ImageIcon(model.PATH_COUPS+coup+".png"));
			vue.getBouton(i-n,j).setPath(model.PATH_COUPS);
			vue.getBouton(i-n,j).setType(coup);
			n += 1;
		}
		n = 1;
		// BAS
		while (i+n <= 7 && model.tabTours[i+n][j] == ""){
			model.tabCoups[i+n][j] = coup;
			vue.getBouton(i+n,j).setIcon(new ImageIcon(model.PATH_COUPS+coup+".png"));
			vue.getBouton(i+n,j).setPath(model.PATH_COUPS);
			vue.getBouton(i+n,j).setType(coup);
			n += 1;
		}
		n = 1;
		// GAUCHE
		while (j-n >= 0 && model.tabTours[i][j-n] == ""){
			model.tabCoups[i][j-n] = coup;
			vue.getBouton(i,j-n).setIcon(new ImageIcon(model.PATH_COUPS+coup+".png"));
			vue.getBouton(i,j-n).setPath(model.PATH_COUPS);
			vue.getBouton(i,j-n).setType(coup);
			n += 1;
		}
		n = 1;
		// DROITE
		while (j+n <= 7 && model.tabTours[i][j+n] == ""){
			model.tabCoups[i][j+n] = coup;
			vue.getBouton(i,j+n).setIcon(new ImageIcon(model.PATH_COUPS+coup+".png"));
			vue.getBouton(i,j+n).setPath(model.PATH_COUPS);
			vue.getBouton(i,j+n).setType(coup);
			n += 1;
		}
		n = 1;
		// DIAGONALE BAS-DROITE
		while (i+n <= 7 && j+n <= 7 && model.tabTours[i+n][j+n] == ""){
			model.tabCoups[i+n][j+n] = coup;
			vue.getBouton(i+n,j+n).setIcon(new ImageIcon(model.PATH_COUPS+coup+".png"));
			vue.getBouton(i+n,j+n).setPath(model.PATH_COUPS);
			vue.getBouton(i+n,j+n).setType(coup);
			n += 1;
		}
		n = 1;
		// DIAGONALE HAUT-DROITE
		while (i-n >= 0 && j+n <= 7 && model.tabTours[i-n][j+n] == ""){
			model.tabCoups[i-n][j+n] = coup;
			vue.getBouton(i-n,j+n).setIcon(new ImageIcon(model.PATH_COUPS+coup+".png"));
			vue.getBouton(i-n,j+n).setPath(model.PATH_COUPS);
			vue.getBouton(i-n,j+n).setType(coup);
			n += 1;
		}
		n = 1;
		// DIAGONALE BAS-GAUCHE
		while (i+n <= 7 && j-n >= 0 && model.tabTours[i+n][j-n] == ""){
			model.tabCoups[i+n][j-n] = coup;
			vue.getBouton(i+n,j-n).setIcon(new ImageIcon(model.PATH_COUPS+coup+".png"));
			vue.getBouton(i+n,j-n).setPath(model.PATH_COUPS);
			vue.getBouton(i+n,j-n).setType(coup);
			n += 1;
		}
		n = 1;
		// DIAGONALE HAUT-GAUCHE
		while (i-n >= 0 && j-n >= 0 && model.tabTours[i-n][j-n] == ""){
			model.tabCoups[i-n][j-n] = coup;
			vue.getBouton(i-n,j-n).setIcon(new ImageIcon(model.PATH_COUPS+coup+".png"));
			vue.getBouton(i-n,j-n).setPath(model.PATH_COUPS);
			vue.getBouton(i-n,j-n).setType(coup);
			n += 1;
		}
		// Clean 1st round
		if (model.isFirstRound){
			for (j = 0; j < 8; j++) {
				vue.getBouton(0,j).setPath(null);
				if (j != model.old_coords[1])
					vue.getBouton(0,j).setType(null);
			}
			model.isFirstRound = false;
		}
		// Tours fix
		model.selectedTour = null;
		vue.getBouton(model.old_coords[0],model.old_coords[1]).setPath(null);
	}

	protected void updateTours(){
		// Update model/buttons
		int[] old_coords = model.old_coords;
		int[] new_coords = model.coords;
		KButton old_btn = vue.getBouton(old_coords[0],old_coords[1]);
		KButton new_btn = vue.getBouton(new_coords[0],new_coords[1]);
		//new
		new_btn.setPath(model.PATH_TOURS);
		new_btn.setType(model.old_tour);
		model.tabTours[old_coords[0]][old_coords[1]] = "";
		model.tabTours[new_coords[0]][new_coords[1]] = old_btn.getType();
		new_btn.setIcon(new ImageIcon(model.PATH_TOURS+old_btn.getType()+".png"));
		//old
		old_btn.setIcon(null);

		// Select tour
		String tour;
		model.selectedTour = model.TAB_CASES[model.coords[0]][model.coords[1]];
		if (model.joueurs[0] == model.JOUE)
			tour = "N_";
		else
			tour = "B_";
		model.selectedTour = tour + model.selectedTour;
		// Update icons
		KButton btn;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				btn = vue.getBouton(i,j);
				if (!model.tabTours[i][j].equals(model.selectedTour)){
					btn.setPath(null);
					btn.setType(null);
				} else {
					btn.setPath(model.PATH_TOURS);
					btn.setType(model.tabTours[i][j]);
				}
			}
		}
	}

	protected void resetCoups(){
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (model.tabCoups[i][j] != null){
					vue.getBouton(i,j).setIcon(null);
					vue.getBouton(i,j).setPath(null);
					vue.getBouton(i,j).setType(null);
				}
			}
		}
		model.tabCoups = new String[8][8];
	}

	protected String winner(){
		for (int j = 0; j < 8; j++) {
			if (model.tabTours[7][j].startsWith("B_"))
				return "blanc";
			if (model.tabTours[0][j].startsWith("N_"))
				return "noir";
		}
		return null;
	}

	protected void endGame(String win){
		KButton btn;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (model.tabTours[i][j] != ""){
					btn = vue.getBouton(i,j);
					btn.removeActionListener(vue.tabListeners[i][j]);
					btn.setPath(null);
					btn.setType(null);
				}
			}
		}
		vue.creerDialog("Les "+win+"s ont gagné !");
	}
}
