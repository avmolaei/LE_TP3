//import de swing et awt pour la GUI
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//import de Jsoup pour scraper
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
//import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * Convertisseur{}; Moteur principal de l'application.
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
public class Convertisseur extends JFrame{

    /**
     * ATTRIBUTS
     */
    protected double dtx;
    protected int ind;


    /**
     * main(); point d'entrée principal du programme
     * @param args l'argument de ligne de commande
     */
    public static void main(String[] args){

        //on récupère la page des change sur changemangenta.fr
        String url = "http://www.changemagenta.fr/devises/cours/prix.html";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //dans les requetes CSS lors d'une conversion, on récupère un tableau qui contient les taux de change
        Elements links = doc.select(".tableauItem em.hidden-xs");

        //on créé 2 arraylist: value continet les taux de change, et names contient les noms des monnaies. Les
        //arraylist sont plus faciles à utiliser que les tableau, puisqu'on a pas besoin de gérer la taille maximale
        //comme sur un tableau, et on peut simplement les remplir, un peu comme une pile.
        ArrayList<Double> values = new ArrayList<Double>();
        ArrayList<String> names  = new ArrayList<String>();

        //on parcourt tous les éléments du tableau récupéré
        for(Element e : links) {
            //on sépare chaque ligne selon les espace (regex) en un tableau
            String[] tab = e.text().split(" ");

            //on ajoute la 4ème valeur de ce tableau dans la arraylist des valeurs
            String s = tab[3].replace(",",".");
            values.add(Double.parseDouble(s));

            //on ajoute la 2ème valeur de ce tableau dans la arraylist des noms
            String names_str = tab[1].replace(",", ".");
            names.add(names_str);
        }

        //le problème: le tableau de change magenta inclut valeurs max et valeurs min. Aussi, la conversion se fait dans l'autre sens
        //, cad d'une monnaie vers l'euro et non l'inverse. De ce fait, on fait d'abord la moyenne de 2 valeurs consécutives, et on
        //met à l'inverse
        ArrayList<Double> finalvalues = new ArrayList<Double>();
        ArrayList<String> finalnames = new ArrayList<String>();

        for(int i = 0; i<values.size(); i+=2){
            finalvalues.add(1/((values.get(i) + values.get(i+1))/2));
            finalnames.add(names.get(i));
        }

        /*
        System.out.println(finalnames.get(0)+": "+finalvalues.get(0));
        System.out.println(finalnames.get(1)+": "+finalvalues.get(1));
        System.out.println(finalnames.get(3)+": "+finalvalues.get(3)*100);
        System.out.println(finalnames.get(60)+": "+finalvalues.get(60)*100);*/

        //on met nos taux dans un tableau
        double[] taux = new double[4];
        taux[0] = finalvalues.get(0);
        taux[1] = finalvalues.get(1);
        taux[2] = finalvalues.get(3)*100; //le problème ici, c'est que magenta référence le taux de change par rapport à 100JPY, on multiplie donc par 100
        taux[3] = finalvalues.get(60)*100; //idem ici

        Convertisseur c = new Convertisseur();
            c.draw(taux); //on passe notre tableau en argument de draw, méthode qui permet de générer l'interface
        }

    /**
     * primitiveConvert(); convertit le chiffre entré en dollars selon un taux
     * @param eur le taux de change
     * @return la valeur en dollars après la conversion
     */
    public double primitiveConvert(double eur){
        return (int)(dtx*eur*100.0)/100.0;

    }

    /**
     * getDialogValue(); récupère la valuer rentrée dans la config dialog
     * @return la valeur entrée
     */
    public double getDialogValue(MenuConvertisseur mc){
        if(mc.dc.valid) return Double.parseDouble(mc.dc.in.getText());
        return dtx;
    }

    /**
     * draw(); coeur de la génération de la fenetre de l'application
     */
    public void draw(double[] taux){
        //INITIALISATION de l'interface
        dtx = 1.2;
        ind  = 0;
        MenuConvertisseur mc = new MenuConvertisseur();
        JFrame f = new JFrame("Convertisseur EUR/USD 2.0");
        JPanel pan = new JPanel();
        ImageIcon icon = new ImageIcon("amogus.png");
        f.setIconImage(icon.getImage());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setSize(300, 200);
        JLabel euro = new JLabel("Euro");
        JTextField input = new JTextField(6);
        JButton b = new JButton("Convertir");
        String[] curr = {"USD", "GBP", "JPY", "MAD"};
        JComboBox liste = new JComboBox<String>(curr);
        liste.setPreferredSize(new Dimension(100, 20));
        JLabel monnaie = new JLabel("Monnaie");
        JTextField output = new JTextField(6);
        output.setEditable(false);
        JLabel tx = new JLabel("taux de conversion: 1euro = " + dtx + curr[ind]);
        //BOUTON
        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    if(mc.dc != null) dtx = getDialogValue(mc); //arrondi 0.01
                    tx.setText("taux de conversion: 1euro = " + dtx + curr[ind]);
                    String txt = String.valueOf(primitiveConvert(Double.parseDouble(input.getText())));
                    output.setText(txt);
                }
                catch(Exception Ee) {JOptionPane.showMessageDialog(new JFrame(), "Entrez un nombre !!!", "Alerte", JOptionPane.ERROR_MESSAGE);}
            }
        });
        liste.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent Ee){
                switch((String)liste.getSelectedItem()){ //ici, au lieu d'utiliser le taux settable manuellement, on utilise notre tableau passé en param
                    case "USD": ind = 0; dtx = taux[0]; break;
                    case "GBP": ind = 1; dtx = taux[1]; break;
                    case "JPY": ind = 2; dtx = taux[2]; break;
                    case "MAD": ind = 3; dtx = taux[3]; break;
                    default: ind = 0; break;
                }
                tx.setText("taux de conversion: 1euro = " + dtx + (String)liste.getSelectedItem());


            }
        });
        //AJOUT A LA FENETRE
        pan.add(euro, BorderLayout.CENTER);
        pan.add(input, BorderLayout.CENTER);
        pan.add(monnaie, BorderLayout.CENTER);
        pan.add(liste);
        pan.add(output, BorderLayout.CENTER);
        pan.add(tx, BorderLayout.CENTER);
        pan.add(b, BorderLayout.SOUTH);
        //f.pack();
        f.setLayout(new GridLayout(2, 3));
        f.setJMenuBar(mc);
        f.setContentPane(pan);
        f.setVisible(true);
    }












}
