package br.upe.ui;

import br.upe.controller.TarefaControlador;

import javax.swing.*;
import java.awt.event.ActionListener;

public class TelaPrincipal {
    private JPanel pnlMain;
    private JTextField txtDescricaoTarefa;
    private JButton btnAdicionarTarefa;
    private JPanel pnlAdicionar;
    private JTable tblTarefas;
    private JCheckBox chkExibirFinalizadas;
    private JPanel pnlRodape;
    private JButton btnExcluirTarefa;

    private final TarefaControlador controlador;

    public TelaPrincipal(String arquivo) {
        controlador = new TarefaControlador(arquivo);
        controlador.setOnErro(msg -> JOptionPane.showMessageDialog(
                pnlMain, msg, "Erro", JOptionPane.ERROR_MESSAGE));

        tblTarefas.setModel(controlador.getTarefaTableModel());
        tblTarefas.getColumnModel().getColumn(0).setMaxWidth(50);


        ActionListener adicionar = e -> adicionarTarefa();
        btnAdicionarTarefa.addActionListener(adicionar);
        txtDescricaoTarefa.addActionListener(adicionar);

        chkExibirFinalizadas.addActionListener(e ->
                controlador.exibirFinalizadas(chkExibirFinalizadas.isSelected()));

        btnExcluirTarefa.addActionListener(e -> excluirTarefa());
    }

    private void adicionarTarefa() {
        if (controlador.adicionarTarefa(txtDescricaoTarefa.getText())) {
            txtDescricaoTarefa.setText("");
        }
        txtDescricaoTarefa.requestFocusInWindow();
    }

    private void excluirTarefa() {
        int linha = tblTarefas.getSelectedRow();
        if (linha >= 0) {
            controlador.excluirTarefa(linha);
        }
    }

    public JPanel getPnlMain() {
        return this.pnlMain;
    }

    private void createUIComponents() {
        tblTarefas = new JTable();
    }

}