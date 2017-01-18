import com.intellij.ide.util.gotoByName.ChooseByNameFilterConfiguration;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by BGADIL-PC on 09/01/2017.
 */
public class mainform {
    private JPanel panelmain;
    private JTextArea paste_lignes;
    private JButton button2;
    private JProgressBar progressBar1;
    private JLabel progress;
    private JComboBox<String> selectpostcode;
    private JButton detecterColonnesButton;
    private JComboBox selectad;
    private JLabel champLigne;


    public mainform() {


        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Runnable runner = new Runnable()
                {
                    public void run()
                    {
                        String[] lines = paste_lignes.getText().split("\\n");
                        String resultQPV = "";
                        int numLines = 0;

                        for (int i = 0; i < lines.length; i++) {
                            numLines++;
                        }



                        for (int i = 1; i < lines.length; i++) {
                            String adresse = "";
                            String CP = "";
                            String[] cells = lines[i].split("\\t");
                            float percent_d = ((float) i / (float) numLines) * 100;
                            int percent = (int) percent_d;

                            progressBar1.setValue(percent);
                            progress.setText(percent + "%");
                            champLigne.setText("En cours : ligne " + i + " " + adresse + " - " + CP);

                            /*  METHODE DE DETECTION AUTOMATIQUE DES CHAMPS

                            for (int j = 0; j < cells.length; j++) {

                                if (CheckAdresse.identify_me(cells[j]) == "CP") {
                                    CP = cells[j];
                                }

                                if (CheckAdresse.identify_me(cells[j]) == "adresse") {
                                    adresse = cells[j];
                                }

                            }
                            */

                            adresse = cells[selectad.getSelectedIndex()];
                            CP = cells[selectpostcode.getSelectedIndex()];


                            String QPV = null;

                            if (adresse == "" || CP == "") {
                                QPV = "erreur sur la ligne";
                            } else {

                                try {
                                    QPV = CheckAdresse.Check(CP, adresse, adresse);
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }


                            resultQPV = resultQPV + QPV + "\n";


                            //System.out.println(i + " " + percent + " " + voie + " " + CP + " " + numero);

                        }

                        progressBar1.setValue(100);
                        progress.setText("100%" + "\n" + "DONE");

                        paste_lignes.setText(resultQPV);
                    }

                };

                Thread t = new Thread(runner, "Code Executer");
                t.start();

            }
        });

        detecterColonnesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                selectpostcode.removeAll();
                selectpostcode.removeAllItems();
                selectad.removeAll();
                selectad.removeAllItems();

                String[] lines = paste_lignes.getText().split("\\n");

                String[] cells = lines[0].split("\\t");

                for (int i = 0; i < cells.length; i++) {
                    selectpostcode.addItem(cells[i]);
                }

                for (int i = 0; i < cells.length; i++) {
                    selectad.addItem(cells[i]);
                }





            }
        });
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("mainform");
        frame.setContentPane(new mainform().panelmain);
        frame.setSize(900, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}

