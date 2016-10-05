package com.Nadir.Digital.JeuDeLaVie.Vue;

import javax.swing.JFileChooser;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;

import com.Nadir.Digital.JeuDeLaVie.Model.GameOfLife;
import com.Nadir.Digital.JeuDeLaVie.Vue.Controller.GameController;

public class VueHelper {

	public static Color color;
	
	public static Slider slider1,slider2;
	public static Button clearButton(Composite options,GameController gameController){
		Button button = new Button(options, SWT.PUSH);
		button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		button.setText("Clear");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				gameController.clear();
			}
		});
		return button;
	}
	public static Scale zoomScale(Composite options,GameVue vue){
		VueHelper.createLabel(options, "Zoom: ");
		Scale scale = new Scale(options, SWT.HORIZONTAL);
		scale.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		scale.setSize(100, 40);
		scale.setMinimum(1);
		scale.setMaximum(100);
		scale.setSelection(30);
		scale.setPageIncrement(10);
		scale.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				GameVue.SCALE = scale.getSelection() /10; // 0 = 1 -------- 100- 10 -- 50---- 5 
				try {
					slider1.setMaximum(GameOfLife.SIZE/GameVue.SCALE);
					slider2.setMaximum(GameOfLife.SIZE/GameVue.SCALE);
				} catch (Exception e1) {	
					GameVue.SCALE = 1;
					slider1.setMaximum(2);
					slider2.setMaximum(1);
				}
				
				
				vue.update();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		

		return scale;
	}
	public static Scale createScale(Composite options) {
		VueHelper.createLabel(options, "Vitesse: ");
		Scale scale = new Scale(options, SWT.HORIZONTAL);
		scale.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		scale.setSize(100, 40);
		scale.setMinimum(1);
		scale.setMaximum(100);
		scale.setSelection(30);
		scale.setPageIncrement(10);
		scale.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				GameVue.TIMER_INTERVAL = scale.getSelection() * 10;
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		

		return scale;
	}

	public static Button createThreadButton(Composite options, String text, int type, Runnable runnable) {
		Button button = new Button(options, SWT.PUSH);
		button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		button.setText(text);

		button.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				(options.getDisplay()).timerExec(type, runnable);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		return button;
	}

	public static Label createLabel(Composite options, String text) {
		Label label = new Label(options, SWT.NONE);
		label.setText(text);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		return label;
	}

	public static Canvas createCanvas(Composite canvasComposite, PaintListener paint, MouseListener editMode,
			MouseMoveListener editMode2) {
		
		Canvas canvas = new Canvas(canvasComposite, SWT.BORDER);
		canvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		canvas.addPaintListener(paint);
		canvas.addMouseListener(editMode);
		canvas.addMouseMoveListener(editMode2);
		 
		

		return canvas;
	}
	public static void createSliders(Composite canvasComposite){
		slider1 = new Slider(canvasComposite, SWT.VERTICAL);
		slider1.setLayoutData(new GridData(SWT.END, SWT.FILL, false, true, 1, 2));
		slider1.setMaximum(100);
		slider1.setMinimum(0);
		slider2 = new Slider(canvasComposite, SWT.HORIZONTAL);
		slider2.setLayoutData(new GridData(SWT.FILL, SWT.END, true, false, 2, 1));
		slider2.setMaximum(100);
		slider2.setMinimum(0);
		slider1.setIncrement(1);
		slider2.setIncrement(1);
		
	}
	

	public static List creatLsitConfigurations(Composite options, GameController gameController) {
		List listConfigurations = new List(options, SWT.NONE | SWT.READ_ONLY);
		String configurations[] = new String[] { "Bloc", "Bateau", "Crapaud", "Pentadecathlon", "Clignotant", "Pahre",
				"Planeur", "Vaisseau léger", "Vaisseau moyen", "Canon à planeur" };

		for (String string : configurations) {
			listConfigurations.add(string);
		}

		listConfigurations.setFont(new Font(options.getDisplay(), "Arial", 14, SWT.NORMAL));
		listConfigurations.setBackground(new Color(options.getDisplay(), new RGB(230, 230, 230)));
		listConfigurations.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		listConfigurations.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				gameController.setConfigurations(listConfigurations.getSelectionIndex());

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		return listConfigurations;
	}

	public static Menu createMenu(Shell shell, Canvas canvas, GameVue gamevue, GameController gameController) {

		Menu menu = new Menu(shell, SWT.BAR);

		MenuItem optionFichier = new MenuItem(menu, SWT.CASCADE);
		optionFichier.setText("Fichier");

		Menu menuFichier = new Menu(shell, SWT.DROP_DOWN);

		MenuItem ouvrir = new MenuItem(menuFichier, SWT.CASCADE);
		ouvrir.setText("Ouvrir");
		ouvrir.setAccelerator(SWT.CTRL | 'o');
		MenuItem save = new MenuItem(menuFichier, SWT.CASCADE);
		save.setText("Enrigistrer");
		save.setAccelerator(SWT.CTRL | 's');
		MenuItem sep = new MenuItem(menuFichier, SWT.SEPARATOR);
		MenuItem fermer = new MenuItem(menuFichier, SWT.CASCADE);
		fermer.setText("Fermer");

		MenuItem optionAffichage = new MenuItem(menu, SWT.CASCADE);
		optionAffichage.setText("Affichage");

		Menu menuAffichge = new Menu(shell, SWT.DROP_DOWN);

		MenuItem changeColor = new MenuItem(menuAffichge, SWT.CASCADE);
		changeColor.setText("Couleur des cellules");
		MenuItem changeBackground = new MenuItem(menuAffichge, SWT.CASCADE);
		changeBackground.setText("Couleur du fond");

		optionFichier.setMenu(menuFichier);
		optionAffichage.setMenu(menuAffichge);
		MenuItem optionAide = new MenuItem(menu, SWT.CASCADE);
		optionAide.setText("?");
		shell.setMenuBar(menu);

		String direcTory = new JFileChooser().getFileSystemView().getDefaultDirectory().getAbsolutePath()
				+ "/JeuDeLaVie";
		ouvrir.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				FileDialog fileDialog = new FileDialog(shell);
				fileDialog.setFilterExtensions(new String[] { "*.jdv" });

				fileDialog.setFilterPath(direcTory);

				gameController.loadConfiguration(fileDialog.open());

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		save.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				FileDialog fileDialog = new FileDialog(shell, SWT.SAVE);
				fileDialog.setFilterExtensions(new String[] { "*.jdv" });
				fileDialog.setFilterPath(direcTory);
				gameController.saveConfiguration(fileDialog.open());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		changeColor.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				ColorDialog colorDialog = new ColorDialog(shell);
				colorDialog.setRGB(color.getRGB());
				RGB couleur = colorDialog.open();

				if (couleur != null) {
					color = new Color(shell.getDisplay(), couleur);
				}
				gamevue.update();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		changeBackground.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				ColorDialog colorDialog = new ColorDialog(shell);
				colorDialog.setRGB(color.getRGB());
				RGB couleur = colorDialog.open();
				if (couleur != null) {
					gamevue.changebackGroundColor(couleur);
				}
				gamevue.update();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		fermer.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				shell.dispose();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		optionAide.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				MessageDialog.openInformation(shell, "About Author",
						"Provided By Nadir Belarrouci , All rights Resrverd\nFor more informations contacts us on: "
								+ "\n       Facebook: www.facebook.com/nazarinoos\n       Gmail: nadir.belarrouci@gmail.com \n       Phone number : +213696917970");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		return menu;
	}

}
