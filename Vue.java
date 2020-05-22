/**
 * Created by Dastan21
 */

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;

public class Vue extends JFrame {

	protected Model model;

	/* Panels */
	protected JPanel pTout;
	protected JPanel pPlateau;
	protected JPanel pGrille;

	/* Options menu */
	protected JMenuBar barMenuOptions;
	protected JMenu menuOptions;
	protected JMenuItem menuOptionsNP;
	protected JMenuItem menuOptionsAP;

	// Boutons
	protected KButton[][] tabBoutons;
	protected ActionListener[][] tabListeners;

	public Vue(Model model){
		this.model = model;

		initMenu();
		initGrid();
		creerMenu();
		creerVue();
		setTitle("Kamisado");
		setVisible(true);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("Images/kamisado_logo.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initGrid(){
		// Instanciation du tableau de boutons
		tabBoutons = new KButton[8][8];
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				tabBoutons[i][j] = new KButton();
				tabBoutons[i][j].setPreferredSize(new Dimension(64, 64));
			}
		}
		tabListeners = new ActionListener[8][8];
	}

	private void initMenu(){
		// Instanciations des éléments du menu
		barMenuOptions = new JMenuBar();
		menuOptions = new JMenu("Options");
		menuOptionsNP = new JMenuItem("Nouvelle partie");
		menuOptionsAP = new JMenuItem("À propos");
	}

	private void creerVue(){
		/* Instanciations */
		pTout = new JPanel();
		pPlateau = new JPanel();
		pGrille = new JPanel();

		/* Layouts */
		pPlateau.setLayout(new BoxLayout(pPlateau, BoxLayout.Y_AXIS));
		pPlateau.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.black));
		pGrille.setLayout(new GridLayout(8,8));

		/* Boutons sans bord */
		for (KButton[] listBtn : tabBoutons){
			for (KButton btn : listBtn){
				btn.setBorder(BorderFactory.createEmptyBorder());
			}
		}

		/* Assemblages */
		pTout.add(pPlateau);
		pPlateau.add(pGrille);
		for (KButton[] listBtn : tabBoutons){
			for (KButton btn : listBtn){
				pGrille.add(btn);
			}
		}

		setContentPane(pTout);
	}

	private void creerMenu(){
		menuOptions.add(menuOptionsNP);
		menuOptions.addSeparator();
		menuOptions.add(menuOptionsAP);
		barMenuOptions.add(menuOptions);

		setJMenuBar(barMenuOptions);
	}

	public void setButtonControler(ActionListener listener){
		// for (KButton[] listBtn : tabBoutons){
		// 	for (KButton btn : listBtn){
		// 		btn.addActionListener(listener);
		//
		// 	}
		// }
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				tabBoutons[i][j].addActionListener(listener);
				tabListeners[i][j] = listener;
			}
		}
	}

	public void setMenuControler(ActionListener listener){
		menuOptionsNP.addActionListener(listener);
		menuOptionsAP.addActionListener(listener);
	}

	public void creerDialog(String msg){
		JOptionPane dialog = new JOptionPane();
		dialog.showMessageDialog(this, msg);
		JDialog fenDialog = dialog.createDialog(this, msg);
	}

	/* Geters des éléments en interactions */
	public JMenuItem getMenuOptionsNP() { return menuOptionsNP; }
	public JMenuItem getMenuOptionsAP() { return menuOptionsAP; }
	public KButton getBouton(int i, int j) { return tabBoutons[i][j]; }
}
