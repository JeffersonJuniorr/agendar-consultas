package com.example.clinic.ui.console;

import com.example.clinic.dao.ConsultaDao;
import com.example.clinic.dao.MedicoDao;
import com.example.clinic.dao.PacienteDao;
import com.example.clinic.domain.Consulta;
import com.example.clinic.domain.Medico;
import com.example.clinic.domain.Paciente;
import com.example.clinic.service.AgendaService;
import com.example.clinic.service.MedicoService;
import com.example.clinic.service.PacienteService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class ConsoleMain {
    private final Scanner in = new Scanner(System.in);
    private final DateTimeFormatter PADRAO_DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", new Locale("pt", "BR"));

    private final MedicoService medicoService;
    private final PacienteService pacienteService;
    private final AgendaService agendaService;

    public ConsoleMain() {
        MedicoDao medicoDao = new MedicoDao();
        PacienteDao pacienteDao = new PacienteDao();
        ConsultaDao consultaDao = new ConsultaDao();

        this.medicoService = new MedicoService(medicoDao);
        this.pacienteService = new PacienteService(pacienteDao);
        this.agendaService = new AgendaService(consultaDao);
}
    public void run() {
        while (true) {
            System.out.println("\n===== Clínica FIAP - Agendamento =====");
            System.out.println("1. Cadastrar Médico");
            System.out.println("2. Cadastrar Paciente");
            System.out.println("3. Agendar Consulta");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = Integer.parseInt(in.nextLine().trim());
                switch (opcao) {
                    case 1 -> cadastrarMedico();
                    case 2 -> cadastrarPaciente();
                    case 3 -> agendarConsulta();
                    case 0 -> {
                        System.out.println("Saindo do sistema.");
                        return;
                    }
                    default -> System.err.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.err.println("Por favor, digite um número válido.");
            } catch (Exception e) {
                System.err.println("Ocorreu um erro: " + e.getMessage());
            }
        }
    }

    private void cadastrarMedico() {
        System.out.println("\n--- Cadastro de Médico ---");
        System.out.print("Nome: ");
        String nome = in.nextLine().trim();
        System.out.print("CRM: ");
        String crm = in.nextLine().trim();

        Medico medico = new Medico(null, nome, crm);
        Long id = medicoService.cadastrar(medico);
        System.out.println("Médico cadastrado com sucesso! ID = " + id);
    }

    private void cadastrarPaciente() {
        System.out.println("\n--- Cadastro de Paciente ---");
        System.out.print("Nome: ");
        String nome = in.nextLine().trim();
        System.out.print("Email: ");
        String email = in.nextLine().trim();

        Paciente paciente = new Paciente(null, nome, email);
        Long id = pacienteService.cadastrar(paciente);
        System.out.println("Paciente cadastrado com sucesso! ID = " + id);
    }

    private void agendarConsulta() {
        System.out.println("\n--- Agendamento de Consulta ---");
        System.out.print("ID do paciente (ex.: 1): ");
        long pacienteId = Long.parseLong(in.nextLine().trim());

        System.out.print("ID do médico (ex.: 10): ");
        long medicoId = Long.parseLong(in.nextLine().trim());

        System.out.print("Início da consulta (dd/MM/yyyy HH:mm): ");
        LocalDateTime inicio = LocalDateTime.parse(in.nextLine().trim(), PADRAO_DATA_HORA);

        System.out.print("Duração em minutos (ex.: 30): ");
        int duracaoMin = Integer.parseInt(in.nextLine().trim());
        LocalDateTime fim = inicio.plusMinutes(duracaoMin);

        var consulta = new Consulta(null, pacienteId, medicoId, inicio, fim);
        Long id = agendaService.agendar(consulta);
        System.out.println("Consulta agendada com sucesso! ID = " + id);
    }
}
