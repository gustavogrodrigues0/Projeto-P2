package br.upe.ui;

import br.upe.controller.TarefaControlador;
import br.upe.model.Tarefa;
import br.upe.model.TarefaTableModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TelaPrincipal {
    private JPanel pnlMain;
    private JTextField txtDescricaoTarefa;
    private JButton btnAdicionarTarefa;
    private JPanel pnlAdicionar;
    private JTable tblTarefas;
    private JCheckBox chkExibirFinalizadas;

    private List<Tarefa> tarefas;

    private TarefaControlador controlador;

    public TelaPrincipal() {
        super();
        tarefas = new ArrayList<>();
        btnAdicionarTarefa.addActionListener(( e) -> {
            try {
                adicionarTarefa(txtDescricaoTarefa.getText());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            txtDescricaoTarefa.setText("");
        });
        txtDescricaoTarefa.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        adicionarTarefa(txtDescricaoTarefa.getText());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    txtDescricaoTarefa.setText("");
                }
            }
        });
        chkExibirFinalizadas.addActionListener(e -> {
            boolean selecionado = ((JCheckBox) e.getSource()).isSelected();
            controlador.exibirFinalizadas(selecionado);
        });
    }

    private void adicionarTarefa(String texto) throws IOException {
        Tarefa tarefa = new Tarefa(texto, tarefas.size());
        controlador.adicionarTarefaAtiva(tarefa);
        tblTarefas.revalidate();
        tblTarefas.repaint();
    }

    public JPanel getPnlMain() {
        return this.pnlMain;
    }

    private void createUIComponents() {
        controlador = new TarefaControlador();
        tblTarefas = new JTable(controlador.getTarefaTableModel());
        tblTarefas.getColumnModel().getColumn(0).setMaxWidth(20);

        /*Adiciona um listener ao modelo da tabela para detectar alterações,
         como quando uma tarefa é marcada como finalizada.
         Sempre que ocorre uma modificação, chama o Controller
         para atualizar o arquivo TXT com os dados atuais.*/
        tblTarefas.getModel().addTableModelListener(e -> {
            try {
                controlador.salvarTarefas();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

}
