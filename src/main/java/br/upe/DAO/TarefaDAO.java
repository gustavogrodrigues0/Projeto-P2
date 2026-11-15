package br.upe.DAO;

import br.upe.model.Tarefa;
import br.upe.model.TarefaTableModel;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {


    public List<Tarefa> carregarArquivo() {
        List<Tarefa> lista = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(
                new FileReader("tarefas.txt"))) {

            String linha;

            while ((linha = leitor.readLine()) != null) {
                String[] dados = linha.split(";");

                String descricao = dados[0];

                LocalDate dataCriacao = LocalDate.parse(dados[1]);

                LocalDate dataFinalizacao = dados[2].equals("null")
                        ? null
                        : LocalDate.parse(dados[2]);

                LocalDate dataVencimento = dados[3].equals("null")
                        ? null
                        : LocalDate.parse(dados[3]);

                boolean finalizada = Boolean.parseBoolean(dados[4]);
                int ordem = Integer.parseInt(dados[5]);

                Tarefa t = new Tarefa(descricao, ordem);

                t.setDataCriacao(dataCriacao);
                t.setDataFinalizacao(dataFinalizacao);
                t.setDataVencimento(dataVencimento);
                t.setFinalizada(finalizada);

                lista.add(t);
            }

        } catch (FileNotFoundException e) {
            return lista;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar tarefas", e);
        }

        return lista;
    }


    public void salvarEmArquivo(List<Tarefa> tarefas) throws IOException {

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter("tarefas.txt"))) {

            for (Tarefa t : tarefas) {
                escritor.write(
                        t.getDescricao() + ";" +
                                t.getDataCriacao() + ";" +
                                t.getDataFinalizacao() + ";" +
                                t.getDataVencimento() + ";" +
                                t.isFinalizada() + ";" +
                                t.getOrdem()
                );

                escritor.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar tarefas", e);
        }
    }
}
