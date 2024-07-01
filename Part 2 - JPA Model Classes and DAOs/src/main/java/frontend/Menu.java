package frontend;

import dao.PessoaDAO;
import dao.TarefaDAO;
import models.Pessoa;
import models.Tarefa;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private static PessoaDAO pessoaDAO = new PessoaDAO();
    private static TarefaDAO tarefaDAO = new TarefaDAO();
    private static Scanner scanner = new Scanner(System.in);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        while (true) {
            System.out.println("Menu Principal:");
            System.out.println("1 - Pessoa");
            System.out.println("2 - Tarefa");
            System.out.println("0 - Sair");
            int opcao = scanner.nextInt();
            scanner.nextLine();  // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    menuPessoa();
                    break;
                case 2:
                    menuTarefa();
                    break;
                case 0:
                    System.exit(0);
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void menuPessoa() {
        while (true) {
            System.out.println("Menu Pessoa:");
            System.out.println("1 - Criar");
            System.out.println("2 - Pesquisar por Id");
            System.out.println("3 - Pesquisar por Nome");
            System.out.println("4 - Editar por Id");
            System.out.println("5 - Deletar por Id");
            System.out.println("6 - Listar Todas");
            System.out.println("0 - Voltar");
            int opcao = scanner.nextInt();
            scanner.nextLine();  // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    Pessoa novaPessoa = new Pessoa();
                    System.out.println("Digite o nome:");
                    novaPessoa.setNome(scanner.nextLine());
                    try {
                        pessoaDAO.insert(novaPessoa);
                        System.out.println("Pessoa criada com sucesso.");
                    } catch (Exception e) {
                        System.out.println("Erro ao criar pessoa: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Digite o ID:");
                    int idPesquisa = scanner.nextInt();
                    scanner.nextLine();  // Consumir a quebra de linha
                    try {
                        Pessoa pessoa = pessoaDAO.get(idPesquisa);
                        if (pessoa != null) {
                            System.out.println(pessoa);
                        } else {
                            System.out.println("Pessoa não encontrada.");
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao pesquisar pessoa: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Digite o nome:");
                    String nomePesquisa = scanner.nextLine();
                    try {
                        List<Pessoa> pessoas = pessoaDAO.listByName(nomePesquisa);
                        if (pessoas.isEmpty()) {
                            System.out.println("Nenhuma pessoa encontrada.");
                        } else {
                            for (Pessoa p : pessoas) {
                                System.out.println(p);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao pesquisar pessoas: " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("Digite o ID da pessoa a ser editada:");
                    int idEdicao = scanner.nextInt();
                    scanner.nextLine();  // Consumir a quebra de linha
                    try {
                        Pessoa pessoaEditada = pessoaDAO.get(idEdicao);
                        if (pessoaEditada != null) {
                            System.out.println("Digite o novo nome:");
                            pessoaEditada.setNome(scanner.nextLine());
                            pessoaDAO.update(pessoaEditada);
                            System.out.println("Pessoa editada com sucesso.");
                        } else {
                            System.out.println("Pessoa não encontrada.");
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao editar pessoa: " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Digite o ID da pessoa a ser deletada:");
                    int idDelecao = scanner.nextInt();
                    scanner.nextLine();  // Consumir a quebra de linha
                    try {
                        pessoaDAO.delete(idDelecao);
                        System.out.println("Pessoa deletada com sucesso.");
                    } catch (Exception e) {
                        System.out.println("Erro ao deletar pessoa: " + e.getMessage());
                    }
                    break;
                case 6:
                    try {
                        List<Pessoa> todasPessoas = pessoaDAO.list(100, 0);  // Ajuste os parâmetros conforme necessário
                        if (todasPessoas.isEmpty()) {
                            System.out.println("Nenhuma pessoa encontrada.");
                        } else {
                            for (Pessoa p : todasPessoas) {
                                System.out.println(p);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao listar pessoas: " + e.getMessage());
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void menuTarefa() {
        while (true) {
            System.out.println("Menu Tarefa:");
            System.out.println("1 - Criar");
            System.out.println("2 - Ver todos");
            System.out.println("3 - Ver por Id de Pessoa");
            System.out.println("4 - Editar por Id");
            System.out.println("5 - Deletar por Id");
            System.out.println("0 - Voltar");
            int opcao = scanner.nextInt();
            scanner.nextLine();  // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    Tarefa novaTarefa = new Tarefa();
                    System.out.println("Digite o ID da pessoa relacionada:");
                    int pessoaId = scanner.nextInt();
                    scanner.nextLine();  // Consumir a quebra de linha
                    try {
                        Pessoa pessoa = pessoaDAO.get(pessoaId);
                        if (pessoa == null) {
                            System.out.println("Pessoa não encontrada. Tente novamente.");
                            break;
                        }
                        novaTarefa.setPessoa(pessoa);
                        System.out.println("Digite o título:");
                        novaTarefa.setTitulo(scanner.nextLine());
                        System.out.println("Digite a descrição:");
                        novaTarefa.setDescricao(scanner.nextLine());
                        System.out.println("Digite a data (yyyy-MM-dd):");
                        try {
                            novaTarefa.setData(new java.sql.Date(dateFormat.parse(scanner.nextLine()).getTime()));
                            tarefaDAO.insert(novaTarefa);
                            System.out.println("Tarefa criada com sucesso.");
                        } catch (Exception e) {
                            System.out.println("Formato de data inválido. Tente novamente.");
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao criar tarefa: " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        List<Tarefa> tarefas = tarefaDAO.list(100, 0);  // Ajuste os parâmetros conforme necessário
                        if (tarefas.isEmpty()) {
                            System.out.println("Nenhuma tarefa encontrada.");
                        } else {
                            for (Tarefa t : tarefas) {
                                System.out.println(t);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao listar tarefas: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Digite o ID da pessoa:");
                    int pessoaIdPesquisa = scanner.nextInt();
                    scanner.nextLine();  // Consumir a quebra de linha
                    try {
                        List<Tarefa> tarefasPorPessoa = tarefaDAO.listByPessoaId(pessoaIdPesquisa);
                        if (tarefasPorPessoa.isEmpty()) {
                            System.out.println("Nenhuma tarefa encontrada para esta pessoa.");
                        } else {
                            for (Tarefa t : tarefasPorPessoa) {
                                System.out.println(t);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao listar tarefas: " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("Digite o ID da pessoa:");
                    int pessoaIdEdicao = scanner.nextInt();
                    scanner.nextLine();  // Consumir a quebra de linha
                    try {
                        List<Tarefa> tarefasPorPessoa = tarefaDAO.listByPessoaId(pessoaIdEdicao);
                        if (tarefasPorPessoa.isEmpty()) {
                            System.out.println("Nenhuma tarefa encontrada para esta pessoa.");
                            break;
                        }
                        for (Tarefa t : tarefasPorPessoa) {
                            System.out.println(t);
                        }
                        System.out.println("Digite o ID da tarefa a ser editada:");
                        int idTarefaEdicao = scanner.nextInt();
                        scanner.nextLine();  // Consumir a quebra de linha
                        Tarefa tarefaEditada = tarefaDAO.get(idTarefaEdicao);
                        if (tarefaEditada != null) {
                            System.out.println("Digite o novo título:");
                            tarefaEditada.setTitulo(scanner.nextLine());
                            System.out.println("Digite a nova descrição:");
                            tarefaEditada.setDescricao(scanner.nextLine());
                            System.out.println("Digite a nova data (yyyy-MM-dd):");
                            try {
                                tarefaEditada.setData(new java.sql.Date(dateFormat.parse(scanner.nextLine()).getTime()));
                                tarefaDAO.update(tarefaEditada);
                                System.out.println("Tarefa editada com sucesso.");
                            } catch (Exception e) {
                                System.out.println("Formato de data inválido. Tente novamente.");
                            }
                        } else {
                            System.out.println("Tarefa não encontrada.");
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao editar tarefa: " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Digite o ID da pessoa:");
                    int pessoaIdDelecao = scanner.nextInt();
                    scanner.nextLine();  // Consumir a quebra de linha
                    try {
                        List<Tarefa> tarefasPorPessoa = tarefaDAO.listByPessoaId(pessoaIdDelecao);
                        if (tarefasPorPessoa.isEmpty()) {
                            System.out.println("Nenhuma tarefa encontrada para esta pessoa.");
                            break;
                        }
                        for (Tarefa t : tarefasPorPessoa) {
                            System.out.println(t);
                        }
                        System.out.println("Digite o ID da tarefa a ser deletada:");
                        int idTarefaDelecao = scanner.nextInt();
                        scanner.nextLine();  // Consumir a quebra de linha
                        tarefaDAO.delete(idTarefaDelecao);
                        System.out.println("Tarefa deletada com sucesso.");
                    } catch (Exception e) {
                        System.out.println("Erro ao deletar tarefa: " + e.getMessage());
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}
