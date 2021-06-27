//IMPORT DE JSWING ET AWT POUR LA GUI
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * DialogConfiguration{}; classe décrivant le comportement de la fenetre de dialogue de changement du taux de change
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
public class DialogConfiguration extends JDialog implements ActionListener{
    /**
     * ATTRIBUTS
     */
    public JTextField in;
    public boolean valid;
    protected double test;

    /**
     * CONSTRUCTEUR DE LA CLASSE
     */
    public DialogConfiguration(){
        super(new JFrame(), "Configuration", true);//creation des élements de l'interface
        setLayout(new FlowLayout());
        JLabel tx = new JLabel("Taux de change");
        in = new JTextField(6);
        JButton b = new JButton("Valider");
        b.addActionListener(this);//ajout de l'action listener sur le bouton
        b.setActionCommand("valider");
        JButton bc = new JButton("Annuler");
        bc.addActionListener(this);
        bc.setActionCommand("cancel");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); //Fermer l'application lorsqu'on clique sur le bouton
        add(tx);
        add(in);
        add(b);
        add(bc);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * actionPerformed(); fonction qui gère les actionevent sur la fenetre de dialogue
     * @param e l'actionevent à traiter
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand() == "cancel") {dispose(); valid = false;}
        else if(e.getActionCommand() == "valider"){
            try{test = Double.parseDouble(in.getText());
                valid = true;JOptionPane.showMessageDialog(new JFrame(), "La nouvelle configuration a bien été prise en compte.\n Elle sera actualisée automatiquement lors de la prochaine conversion.", "Succès", JOptionPane.WARNING_MESSAGE); dispose();}
            catch (Exception Ee) {
                JOptionPane.showMessageDialog(new JFrame(), "Entrez un nombre !!!", "Alerte", JOptionPane.ERROR_MESSAGE); valid = false;}
        }


    }






}