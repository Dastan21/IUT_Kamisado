/**
 * Created by Dastan21
 */

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

public class ControlButton extends Control implements ActionListener{

    public ControlButton(Model model, Vue vue){
    	super(model, vue);
    	vue.setButtonControler(this);
		newGame();
    }

    public void actionPerformed(ActionEvent event) {
		model.coords = checkEvent(event);
		update();
	}

	protected int[] checkEvent(ActionEvent event){
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (vue.getBouton(i,j) == event.getSource())
					return new int[]{i,j};
			}
		}
		return null;
	}
}
