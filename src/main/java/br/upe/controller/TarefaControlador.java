package br.upe.controller;

import br.upe.DAO.TarefaDAO;
import br.upe.model.Tarefa;
import br.upe.model.TarefaTableModel;

import javax.swing.event.TableModelListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TarefaControlador {

    // Atributos
    private TarefaTableModel tarefaTableModel;
    private TarefaDAO dao;

    //Construtor
    public TarefaControlador(String arquivo) {
        tarefaTableModel = new TarefaTableModel();
        dao = new TarefaDAO(arquivo);

        List<Tarefa> lista = dao.carregarArquivo();

        for (Tarefa t : lista){
            if (t.isFinalizada()) {
                tarefaTableModel.getTarefasFinalizadas().add(t);
            } else {
                tarefaTableModel.getTarefasAtivas().add(t);
            }
        }
    }

    //Metodos de negocio
    public void adicionarTarefaAtiva(Tarefa tarefa) throws IOException {
        this.tarefaTableModel.getTarefasAtivas().add(tarefa);
        salvarTarefas();
    }

    public void salvarTarefas() throws IOException {

        List<Tarefa> lista = new ArrayList<>();

        lista.addAll(tarefaTableModel.getTarefasAtivas());
        lista.addAll(tarefaTableModel.getTarefasFinalizadas());

        dao.salvarEmArquivo(lista);
    }

    public void exibirFinalizadas(boolean exibir) {
        tarefaTableModel.setExibirFinalizadas(exibir);
    }


    //Getter e Setters

    public TarefaTableModel getTarefaTableModel() {
        return tarefaTableModel;
    }

    public void setTarefaTableModel(TarefaTableModel tarefaTableModel) {
        this.tarefaTableModel = tarefaTableModel;
    }

}