package com.example.clinic.service;

import com.example.clinic.dao.PacienteDao;
import com.example.clinic.domain.Paciente;


public class PacienteService {
    private final PacienteDao pacienteDao;

    public PacienteService(PacienteDao pacienteDao) {
        this.pacienteDao = pacienteDao;
    }

    public Long cadastrar(Paciente paciente) {
        return pacienteDao.salvar(paciente);
    }
}
