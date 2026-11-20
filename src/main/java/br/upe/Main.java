
package br.upe;

import br.upe.model.Aba;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        FlatLightLaf.setup();

        SwingUtilities.invokeLater(() -> {

            Aba abas = new Aba();

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
