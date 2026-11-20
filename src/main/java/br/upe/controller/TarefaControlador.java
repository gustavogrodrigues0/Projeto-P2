package br.upe.controller;

import br.upe.DAO.TarefaDAO;
import br.upe.model.Tarefa;
import br.upe.model.TarefaTableModel;

import java.util.function.Consumer;

public class TarefaControlador {

    // Atributos
    private final TarefaTableModel tarefaTableModel;
    private final TarefaDAO dao;
    private Consumer<String> onErro;

    // Construtor
    public TarefaControlador(String arquivo) {
        dao = new TarefaDAO(arquivo);
        tarefaTableModel = new TarefaTableModel();
        tarefaTableModel.setTarefas(dao.carregarArquivo());

        // Único ponto de gravação: qualquer alteração no modelo é salva.
        tarefaTableModel.addTableModelListener(e -> salvarTarefas());
    }

    // Métodos de negócio
    public boolean adicionarTarefa(String texto) {
        if (texto == null || texto.isBlank()) {
            return false;
        }
        tarefaTableModel.adicionar(new Tarefa(texto));
        return true;
    }

    public void salvarTarefas() {
        try {
            dao.salvarEmArquivo(tarefaTableModel.getTarefas());
        } catch (RuntimeException ex) {
            if (onErro != null) {
                onErro.accept(ex.getMessage());
            } else {
                throw ex;
            }
        }
    }

    public void exibirFinalizadas(boolean exibir) {
        tarefaTableModel.setExibirFinalizadas(exibir);
    }

    public TarefaTableModel getTarefaTableModel() {
        return tarefaTableModel;
    }

    public void setOnErro(Consumer<String> onErro) {
        this.onErro = onErro;
    }


}