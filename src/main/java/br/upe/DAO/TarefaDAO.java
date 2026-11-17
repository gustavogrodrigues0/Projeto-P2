package br.upe.DAO;

import br.upe.model.Tarefa;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {

    private final String caminho;

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class,
                    (JsonSerializer<LocalDate>) (data, tipo, ctx) -> new JsonPrimitive(data.toString()))
            .registerTypeAdapter(LocalDate.class,
                    (JsonDeserializer<LocalDate>) (json, tipo, ctx) -> LocalDate.parse(json.getAsString()))
            .create();

    public TarefaDAO(String caminho) {
        this.caminho = caminho;
    }

    public List<Tarefa> carregarArquivo() {

        if (!Files.exists(Paths.get(caminho))) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(caminho)) {
            Type tipoTarefa = new TypeToken<List<Tarefa>>() {}.getType();
            List<Tarefa> tarefasCarregadas = gson.fromJson(reader, tipoTarefa);
            return tarefasCarregadas != null ? tarefasCarregadas : new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo: " + caminho, e);
        }
    }

    public void salvarEmArquivo(List<Tarefa> tarefas) {
        try (FileWriter writer = new FileWriter(caminho)) {
            gson.toJson(tarefas, writer);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar o arquivo: " + caminho, e);
        }
    }
}