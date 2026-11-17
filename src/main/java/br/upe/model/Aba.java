
package br.upe.model;

import br.upe.ui.TelaPrincipal;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;

public class Aba extends JTabbedPane {

    public void abrirAba(String arquivo) {
        TelaPrincipal tela = new TelaPrincipal(arquivo);

        addTab(arquivo, tela.getPnlMain());
        setSelectedIndex(getTabCount() - 1);
    }

    private boolean jaAberto(String nome) {
        for (int i = 0; i < getTabCount(); i++) {
            if (getTitleAt(i).equals(nome)) {
                return true;
            }
        }
        return false;
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
