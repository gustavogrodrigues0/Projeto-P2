
package br.upe.ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TelaDeListas extends JPanel {

    private final DefaultListModel<String> modeloListas;
    private final JList<String> lista;
    private final JPanel pnlConteudo;

    private final Map<String, TelaPrincipal> telas;

    public TelaDeListas() {
        setLayout(new BorderLayout());

        modeloListas = new DefaultListModel<>();
        lista = new JList<>(modeloListas);
        pnlConteudo = new JPanel(new BorderLayout());

        telas = new HashMap<>();


        JPanel pnlLateral = new JPanel(new BorderLayout());
        pnlLateral.setPreferredSize(new Dimension(180, 0));

        JLabel lblTitulo = new JLabel("Minhas listas");
        JButton btnNovaLista = new JButton("Criar nova lista");
        btnNovaLista.setBackground(new Color(0, 134, 221));
        btnNovaLista.setForeground(Color.white);

        pnlLateral.add(lblTitulo, BorderLayout.NORTH);
        pnlLateral.add(new JScrollPane(lista), BorderLayout.CENTER);
        pnlLateral.add(btnNovaLista, BorderLayout.SOUTH);

        add(pnlLateral, BorderLayout.WEST);
        add(pnlConteudo, BorderLayout.CENTER);

        lista.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                exibirLista(lista.getSelectedValue());
            }
        });

        btnNovaLista.addActionListener(e -> criarLista());
    }

    public void abrirAba(String arquivo) {

        if (telas.containsKey(arquivo)) {
            lista.setSelectedValue(arquivo, true);
            return;
        }

        TelaPrincipal tela = new TelaPrincipal(arquivo);

        telas.put(arquivo, tela);
        modeloListas.addElement(arquivo);

        lista.setSelectedValue(arquivo, true);
    }

    private void exibirLista(String nome) {

        pnlConteudo.removeAll();

        if (nome != null && telas.containsKey(nome)) {
            pnlConteudo.add(
                    telas.get(nome).getPnlMain(),
                    BorderLayout.CENTER
            );
        }

        pnlConteudo.revalidate();
        pnlConteudo.repaint();
    }

    private void criarLista() {
        abrirAba(proximoNome());
    }

    private boolean jaAberto(String nome) {
        return telas.containsKey(nome);
    }

    public String proximoNome() {

        int n = 2;
        String nome = "tarefas" + n + ".json";

        while (new File(nome).exists() || jaAberto(nome)) {
            n++;
            nome = "tarefas" + n + ".json";
        }

        return nome;
    }

    public void abrirAbasSalvas() {

        File pasta = new File(".");

        File[] arquivos = pasta.listFiles(
                (dir, nome) -> nome.matches("tarefas\\d*\\.json")
        );

        if (arquivos == null || arquivos.length == 0) {
            abrirAba("tarefas.json");
            return;
        }

        Arrays.sort(arquivos);

        for (File f : arquivos) {
            abrirAba(f.getName());
        }
    }
}
