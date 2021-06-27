//import de jswing et awt pour la gui
        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.*;

/**
 * MenuConvertisseur{}; classe décrivant la barre du menu supérieur de l'application.
 *<br>
 *
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation either version 3 of the License, or
 * (at your option) any later version.
 * <br>
 *
 * @author J.BENKEMOUN, A.MOLAEI
 * @version 1.0
 */
public class MenuConvertisseur extends JMenuBar implements ActionListener{

    /**
     * ATTRIBUTS
     */
    public DialogConfiguration dc;

    /**
     * CONSTRUCTEUR PAR DEFAUT
     */
    public MenuConvertisseur(){
        super();
        JMenu fichier = new JMenu("Fichier");
        JMenuItem config = new JMenuItem("Configurer");
        config.setActionCommand("config");
        config.addActionListener(this);
        JMenuItem quit = new JMenuItem("Quitter");
        quit.setActionCommand("quit");
        quit.addActionListener(this);
        JMenu aide = new JMenu("Aide");
        JMenuItem about = new JMenuItem("A propos");
        about.setActionCommand("about");
        about.addActionListener(this);
        add(fichier);
        add(aide);
        fichier.add(config);
        fichier.add(quit);
        aide.add(about);
    }



    /**
     * actionPerformed(); fonction qui gère les events dans le menu
     * @param e l'action a récupérer
     */
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand() == "quit") System.exit(0);
        else if(e.getActionCommand() == "about") JOptionPane.showMessageDialog(new JFrame(), "Convertisseur EUR/USD\n(c)2021 BENKEMOUN MOLAEI", "A propos", 1);
        else if(e.getActionCommand() == "config") {dc = new DialogConfiguration();}

    }
}