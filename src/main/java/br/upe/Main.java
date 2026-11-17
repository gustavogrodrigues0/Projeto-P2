package br.upe;

import br.upe.model.Aba;
import br.upe.ui.TelaPrincipal;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
import java.io.File;
import java.util.Arrays;
import javax.swing.*;

public class Main {


    public static void main(String[] args) {

        FlatLightLaf.setup();
        Aba abas = new Aba();


        abas.abrirAbasSalvas();
        abas.setSelectedIndex(0);

        JButton btnMais = new JButton("+");
        btnMais.addActionListener(e -> abas.abrirAba(abas.proximoNome()));

        JPanel conteudo = new JPanel(new BorderLayout());
        conteudo.add(btnMais, BorderLayout.NORTH);
        conteudo.add(abas, BorderLayout.CENTER);

        JFrame frame = new JFrame("ToDo");
        frame.setContentPane(conteudo);
        frame.pack();
        frame.setVisible(true);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}