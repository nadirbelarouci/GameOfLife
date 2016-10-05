package com.Nadir.Digital.JeuDeLaVie.Vue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.Nadir.Digital.JeuDeLaVie.Model.GameOfLife;
import com.Nadir.Digital.JeuDeLaVie.Model.Observer;
import com.Nadir.Digital.JeuDeLaVie.Vue.Controller.GameController;

public class GameVue implements Observer {

	/**
	 * La classe GameVue joue le role de Vue dans notre MVC , elle va alterer
	 * communiquer avec notre controlleur qui va alterer le model de se changer
	 * , ce dernier va notifier notre vue si il ya des changements
	 * 
	 */
	// la varibale static "pressed" sert a contenir le button pressé par la
	// souris , gauche/droite sinon sa valeur est 0
	public static int pressed = 0;

	// TIMER_INTERVAL define la temps entre chaque appelle du thread de
	// l'animation
	public static int TIMER_INTERVAL = 500;
	
	//SCALE define la taille de chaque carré quand va déssiner sur le canvas;
	public static int SCALE = 1;

	// l'objet Display pour gerer la pile des evenements
	private Display display;
	// fenetre
	private Shell shell;
	// le canvas qui va etre la tbleau sur qu'on va dessiner sur
	private Canvas canvas;

	// controleur du jeu
	private GameController gameController;

	/**
	 * la creation de l'interface graphique ,
	 * 
	 * @param gameController
	 */
	public GameVue(GameController gameController) {

		// initialisation du controlleur
		this.gameController = gameController;
		this.gameController.getGameModel().setObserver(this);

		display = new Display();
		shell = new Shell(display);
		shell.setSize(820, 670);
		shell.setLocation(200, 30);
		shell.setLayout(new GridLayout(2, false));
		shell.setText("Jeu de la vie");

		// l'initialisation du thread pour l'animation
		PaintThread runnable = new PaintThread();

		VueHelper.color = new Color(display, new RGB(0, 0, 168));
		// creation de la barre menu
		VueHelper.createMenu(shell, canvas, this, gameController);

		Composite options = new Composite(shell, SWT.BORDER);
		options.setLayoutData(new GridData(SWT.BEGINNING, SWT.FILL, false, true, 1, 1));
		options.setLayout(new GridLayout());

		// creation des composant de Panel options
		VueHelper.createThreadButton(options, "Start", TIMER_INTERVAL, runnable);
		VueHelper.createThreadButton(options, "Stop", -1, runnable);
		VueHelper.clearButton(options, gameController);

		VueHelper.createScale(options);
		VueHelper.zoomScale(options, this);
		VueHelper.createLabel(options, "Configurations: ");
		VueHelper.creatLsitConfigurations(options, gameController);

		Composite canvasComposite = new Composite(shell, SWT.NONE);
		canvasComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		canvasComposite.setLayout(new GridLayout(2, false));

		// creation de canvas
		canvas = VueHelper.createCanvas(canvasComposite, new Paint(), new ClickEditMode(), new MoveEditMode());
		VueHelper.createSliders(canvasComposite);

		// ouveture de la fenetre

		shell.open();
		// la boucle des evenements
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		// libérations de ressources
		display.timerExec(-1, runnable);
		display.dispose();

	}

	/**
	 * une class interne qui est implimante l'interface Runnable dans le but de
	 * appeler la méthode animate celle de l'objet gameController
	 * 
	 */
	class PaintThread implements Runnable {

		public void run() {
			gameController.animate();
			display.timerExec(TIMER_INTERVAL, this);
		}

	}

	/**
	 * une class interne qui est implimante l'interface PaintListener dans le
	 * but de redessiner si des changement sont effectuer par le model
	 * 
	 */
	
	class Paint implements PaintListener {

		
		@Override
		public void paintControl(PaintEvent e) {
			
			// onrecupere la matrice de Model
			int matrix[][] = gameController.getGameModel().getMatrix();
			e.gc.setBackground(VueHelper.color);
			int n = VueHelper.slider1.getSelection()*SCALE;
			int m = VueHelper.slider2.getSelection()*SCALE;
			for (int i=n; i < GameOfLife.SIZE; i++) {
				for (int j= m; j <  GameOfLife.SIZE; j++) {
					
						// si il ya une cellule vivante on affiche un carré bleu
						if (matrix[i][j] == 1)
							e.gc.fillRectangle((j-m)*SCALE, (i-n)*SCALE-m, SCALE, SCALE);
				}
			}

		}

	}

	/**
	 * une class interne qui est implimante l'interface MouseListener dans le
	 * but de crrer un cellule ou bien la supprimer elle jour le role d'un mode
	 * editeur
	 */
	class ClickEditMode implements MouseListener {
		@Override
		public void mouseDoubleClick(MouseEvent e) {
		}

		@Override
		public void mouseDown(MouseEvent e) {
			int y = VueHelper.slider1.getSelection()*SCALE;
			int x = VueHelper.slider2.getSelection()*SCALE;
			gameController.createCell((e.y/SCALE),(e.x/SCALE)+x, e.button);
			pressed = e.button;

		}

		@Override
		public void mouseUp(MouseEvent e) {
			pressed = 0;
		}
	}

	class MoveEditMode implements MouseMoveListener {
		@Override
		public void mouseMove(MouseEvent e) {
			int y = VueHelper.slider1.getSelection()*SCALE;
			int x = VueHelper.slider2.getSelection()*SCALE;
			if (pressed > 0){
			
				gameController.createCell((e.y/SCALE),(e.x/SCALE)+x, pressed);
			}
				
		}

	}

	@Override
	public void update() {

		canvas.redraw();
	}

	public void changebackGroundColor(RGB rgb) {
		canvas.setBackground(new Color(display, rgb));
	}

}
