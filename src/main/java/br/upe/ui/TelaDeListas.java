
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
        btnNovaLista.setForeground(Color.WHITE);

        JButton btnExcluirLista = new JButton("Excluir lista");
        btnExcluirLista.setBackground(Color.RED);
        btnExcluirLista.setForeground(Color.WHITE);

        // Painel para organizar os dois botões
        JPanel pnlBotoes = new JPanel(new GridLayout(2, 1, 0, 5));

        pnlBotoes.add(btnNovaLista);
        pnlBotoes.add(btnExcluirLista);

        pnlLateral.add(lblTitulo, BorderLayout.NORTH);
        pnlLateral.add(new JScrollPane(lista), BorderLayout.CENTER);
        pnlLateral.add(pnlBotoes, BorderLayout.SOUTH);

        add(pnlLateral, BorderLayout.WEST);
        add(pnlConteudo, BorderLayout.CENTER);

        lista.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                exibirLista(lista.getSelectedValue());
            }
        });

        btnNovaLista.addActionListener(e -> criarLista());

        btnExcluirLista.addActionListener(e -> excluirLista());
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

    private void excluirLista() {

        String nome = lista.getSelectedValue();

        if (nome == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecione uma lista para excluir.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        File arquivo = new File(nome);

        if (arquivo.exists() && !arquivo.delete()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Não foi possível excluir o arquivo.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        int indice = lista.getSelectedIndex();

        telas.remove(nome);
        modeloListas.removeElement(nome);

        if (modeloListas.isEmpty()) {
            lista.clearSelection();
        } else {
            lista.setSelectedIndex(
                    Math.min(indice, modeloListas.size() - 1)
            );
        }
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
