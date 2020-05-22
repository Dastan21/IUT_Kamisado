/**
 * Created by Dastan21
 */

import java.net.URL;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.event.*;
import java.awt.*;

public class KButton extends JButton {
	private Color color;
	private String path;
	private String type;
	private boolean isEnabled;

	/**
	 * Default constructor
	 */
    public KButton() {
		// Color
		setContentAreaFilled(false);
		setOpaque(true);
		update();
		setFocusPainted(false);
		addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent event) {
				if (path != null){
					if (getModel().isRollover()){
						// if (isEnabled)
						if (isEnabled())
							setIcon(new ImageIcon(getClass().getResource(path+type+"_selected.png")));
					} else {
						setIcon(new ImageIcon(getClass().getResource(path+type+".png")));
					}
				}
                if (getModel().isPressed()) setBackground(color);
            }
        });
    }

	/**
	 * Update the button's background color
	 */
	private void update(){
		setBackground(color);
	}

	/**
	 * Set the color of the button
	 * @param color from Color class
	 */
	public void setColor(Color color){
		this.color = color;
		update();
	}

	/**
	 * Set the image's path of the button
	 * @param path string of path
	 */
	public void setPath(String path){
		this.path = path;
	}

	/**
	 * Get the image's path of the button
	 * @return String path
	 */
	public String getPath(){
		return this.path;
	}

	/**
	 * Set the image's type of the button
	 * @param type string of type
	 */
	public void setType(String type){
		this.type = type;
	}

	/**
	 * Get the image's type of the button
	 * @return String type
	 */
	public String getType(){
		return this.type;
	}

	/**
	 * Set the button's state
	 * @param state boolean of isEnabled
	 */
	public void setState(boolean state){
		this.isEnabled = state;
	}
}
