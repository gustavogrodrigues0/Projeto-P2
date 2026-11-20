
package br.upe;

import br.upe.ui.TelaDeListas;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        FlatLightLaf.setup();

        SwingUtilities.invokeLater(() -> {

            TelaDeListas abas = new TelaDeListas();

            abas.abrirAbasSalvas();

            JFrame frame = new JFrame("Lista de Tarefas");

            frame.setContentPane(abas);
            frame.setDefaultCloseOperation(
                    WindowConstants.EXIT_ON_CLOSE
            );

            frame.setSize(850, 550);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
