package com.example.clinic.ui.swing;

import com.example.clinic.dao.ConsultaDao;
import com.example.clinic.dao.MedicoDao;
import com.example.clinic.dao.PacienteDao;
import com.example.clinic.domain.Consulta;
import com.example.clinic.domain.Medico;
import com.example.clinic.domain.Paciente;
import com.example.clinic.service.AgendaService;
import com.example.clinic.service.MedicoService;
import com.example.clinic.service.PacienteService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class SwingMain {
    private final DateTimeFormatter PADRAO_DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", new Locale("pt", "BR"));

    private final MedicoService medicoService;
    private final PacienteService pacienteService;
    private final AgendaService agendaService;

    public SwingMain() {
        MedicoDao medicoDao = new MedicoDao();
        PacienteDao pacienteDao = new PacienteDao();
        ConsultaDao consultaDao = new ConsultaDao();

        this.medicoService = new MedicoService(medicoDao);
        this.pacienteService = new PacienteService(pacienteDao);
        this.agendaService = new AgendaService(consultaDao);
    }

    public void run() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}

        JFrame frame = new JFrame("Clínica FIAP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(450, 350));

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Agendar Consulta", createConsultaPanel(frame));
        tabbedPane.addTab("Cadastrar Médico", createMedicoPanel(frame));
        tabbedPane.addTab("Cadastrar Paciente", createPacientePanel(frame));

        frame.setContentPane(tabbedPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createMedicoPanel(JFrame frame) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtNome = new JTextField(20);
        JTextField txtCrm = new JTextField(20);
        JButton btnSalvar = new JButton("Salvar Médico");

        int row = 0;
        c.gridx = 0; c.gridy = row; c.weightx = 0;
        panel.add(new JLabel("Nome:"), c);
        c.gridx = 1; c.gridy = row++; c.weightx = 1.0;
        panel.add(txtNome, c);

        c.gridx = 0; c.gridy = row; c.weightx = 0;
        panel.add(new JLabel("CRM:"), c);
        c.gridx = 1; c.gridy = row++; c.weightx = 1.0;
        panel.add(txtCrm, c);

        c.gridx = 0; c.gridy = row; c.gridwidth = 2; c.weightx = 0;
        panel.add(btnSalvar, c);

        btnSalvar.addActionListener(e -> {
            try {
                Medico medico = new Medico(null, txtNome.getText(), txtCrm.getText());
                Long id = medicoService.cadastrar(medico);
                JOptionPane.showMessageDialog(frame, "Médico salvo com sucesso! ID = " + id, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                txtNome.setText("");
                txtCrm.setText("");
                txtNome.requestFocusInWindow();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private JPanel createPacientePanel(JFrame frame) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtNome = new JTextField(20);
        JTextField txtEmail = new JTextField(20);
        JButton btnSalvar = new JButton("Salvar Paciente");

        int row = 0;
        c.gridx = 0; c.gridy = row; c.weightx = 0;
        panel.add(new JLabel("Nome:"), c);
        c.gridx = 1; c.gridy = row++; c.weightx = 1.0;
        panel.add(txtNome, c);

        c.gridx = 0; c.gridy = row; c.weightx = 0;
        panel.add(new JLabel("Email:"), c);
        c.gridx = 1; c.gridy = row++; c.weightx = 1.0;
        panel.add(txtEmail, c);

        c.gridx = 0; c.gridy = row; c.gridwidth = 2; c.weightx = 0;
        panel.add(btnSalvar, c);

        btnSalvar.addActionListener(e -> {
            try {
                Paciente paciente = new Paciente(null, txtNome.getText(), txtEmail.getText());
                Long id = pacienteService.cadastrar(paciente);
                JOptionPane.showMessageDialog(frame, "Paciente salvo com sucesso! ID = " + id, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                txtNome.setText("");
                txtEmail.setText("");
                txtNome.requestFocusInWindow();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private JPanel createConsultaPanel(JFrame frame) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtPaciente = new JTextField(15);
        txtPaciente.setToolTipText("Ex.: 1");
        JTextField txtMedico = new JTextField(15);
        txtMedico.setToolTipText("Ex.: 10");
        JTextField txtInicio = new JTextField(15);
        txtInicio.setToolTipText("Formato: dd/MM/yyyy HH:mm");
        JTextField txtDuracao = new JTextField(15);
        txtDuracao.setToolTipText("Duração em minutos (ex.: 30)");
        JButton btnAgendar = new JButton("Agendar");

        int row = 0;
        c.gridx = 0; c.gridy = row; c.weightx = 0;
        panel.add(new JLabel("ID Paciente:"), c);
        c.gridx = 1; c.gridy = row++; c.weightx = 1.0;
        panel.add(txtPaciente, c);

        c.gridx = 0; c.gridy = row; c.weightx = 0;
        panel.add(new JLabel("ID Médico:"), c);
        c.gridx = 1; c.gridy = row++; c.weightx = 1.0;
        panel.add(txtMedico, c);

        c.gridx = 0; c.gridy = row; c.weightx = 0;
        panel.add(new JLabel("Início (dd/MM/yyyy HH:mm):"), c);
        c.gridx = 1; c.gridy = row++; c.weightx = 1.0;
        panel.add(txtInicio, c);

        c.gridx = 0; c.gridy = row; c.weightx = 0;
        panel.add(new JLabel("Duração (min):"), c);
        c.gridx = 1; c.gridy = row++; c.weightx = 1.0;
        panel.add(txtDuracao, c);

        c.gridx = 0; c.gridy = row; c.gridwidth = 2; c.weightx = 0;
        panel.add(btnAgendar, c);

        btnAgendar.addActionListener(ev -> {
            try {
                long pacienteId = Long.parseLong(txtPaciente.getText().trim());
                long medicoId = Long.parseLong(txtMedico.getText().trim());
                LocalDateTime inicio = LocalDateTime.parse(txtInicio.getText().trim(), PADRAO_DATA_HORA);
                int duracaoMin = Integer.parseInt(txtDuracao.getText().trim());
                LocalDateTime fim = inicio.plusMinutes(duracaoMin);
                var consulta = new Consulta(null, pacienteId, medicoId, inicio, fim);

                Long id = agendaService.agendar(consulta);
                JOptionPane.showMessageDialog(frame, "Consulta agendada com sucesso! ID = " + id, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                txtPaciente.setText("");
                txtMedico.setText("");
                txtInicio.setText("");
                txtDuracao.setText("");
                txtPaciente.requestFocusInWindow();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao agendar consulta:\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        return panel;
    }

}
