package br.upe.model;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TarefaTableModel extends AbstractTableModel {

    private static final String[] COLUNAS = {"Feita", "Descrição"};

    private final List<Tarefa> todas = new ArrayList<>();
    private final List<Tarefa> visiveis = new ArrayList<>();
    private boolean exibirFinalizadas;

    @Override
    public int getRowCount() {
        return visiveis.size();
    }

    @Override
    public int getColumnCount() {
        return COLUNAS.length;
    }

    @Override
    public String getColumnName(int column) {
        return COLUNAS[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnIndex == 0 ? Boolean.class : String.class;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tarefa tarefa = visiveis.get(rowIndex);
        switch (columnIndex) {
            case 0: return tarefa.isFinalizada();
            case 1: return tarefa.getDescricao();
            default: return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex != 0) {
            return;
        }
        Tarefa tarefa = visiveis.get(rowIndex);
        boolean finalizada = (Boolean) aValue;
        tarefa.setFinalizada(finalizada);
        tarefa.setDataFinalizacao(finalizada ? LocalDate.now() : null);
        atualizarVisiveis();
        fireTableDataChanged();
    }

    private void atualizarVisiveis() {
        visiveis.clear();
        for (Tarefa t : todas) {
            if (exibirFinalizadas || !t.isFinalizada()) {
                visiveis.add(t);
            }
        }
    }

    public void setTarefas(List<Tarefa> tarefas) {
        todas.clear();
        todas.addAll(tarefas);
        atualizarVisiveis();
        fireTableDataChanged();
    }

    public void adicionar(Tarefa tarefa) {
        todas.add(tarefa);
        atualizarVisiveis();
        fireTableDataChanged();
    }

    public void setExibirFinalizadas(boolean exibir) {
        this.exibirFinalizadas = exibir;
        atualizarVisiveis();
        fireTableDataChanged();
    }

    public boolean isExibirFinalizadas() {
        return exibirFinalizadas;
    }


    public List<Tarefa> getTarefas() {
        return Collections.unmodifiableList(todas);
    }
}