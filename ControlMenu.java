/**
 * Created by Dastan21
 */

import javax.swing.*;
import java.awt.event.*;

public class ControlMenu extends Control implements ActionListener{

    public ControlMenu(Model model, Vue vue){
    	super(model, vue);
    	vue.setMenuControler(this);
    }

    public void actionPerformed(ActionEvent event) {
    	if (event.getSource() == vue.getMenuOptionsNP()) { newGame(); }
    	if (event.getSource() == vue.getMenuOptionsAP()) { vue.creerDialog("Projet réalisé par Loïc DEGRANGE - S2A1"); }
    }
}
