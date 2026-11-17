package br.upe;

import br.upe.ui.TelaPrincipal;

import java.awt.*;
import java.io.File;
import java.util.Arrays;
import javax.swing.*;

public class Main {
    private static JTabbedPane abas;
    private static void abrirAba(String arquivo){
        TelaPrincipal tela = new TelaPrincipal(arquivo);
        abas.addTab(arquivo, tela.getPnlMain());
        abas.setSelectedIndex(abas.getTabCount()-1);
    }

    private static boolean jaAberto(String nome) {
        for (int i = 0; i < abas.getTabCount(); i++) {
            if (abas.getTitleAt(i).equals(nome)) {
                return true;
            }
        }
        return false;
    }

    private static String proximoNome() {
        int n = 2;
        String nome = "tarefas"+n+".txt";

        while (new File(nome).exists() || jaAberto(nome)) {
            n++;
            nome = "tarefas"+n+".txt";
        }
        return nome;
    }
    private static void abrirAbaSalvas() {
        File pasta = new File(".");

        File[] arquivos = pasta.listFiles((dir, nome) -> nome.matches("tarefas\\d*\\.txt"));

        if (arquivos == null || arquivos.length == 0) {
            abrirAba("tarefas.txt");
            return;
        }
        Arrays.sort(arquivos);

        for (File f : arquivos) {
            abrirAba(f.getName());
        }
    }
    public static void main(String[] args) {
        abas = new JTabbedPane();

       abrirAbaSalvas();
       abas.setSelectedIndex(0);

        JButton btnMais = new JButton("+");
        btnMais.addActionListener(e -> abrirAba(proximoNome()));

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