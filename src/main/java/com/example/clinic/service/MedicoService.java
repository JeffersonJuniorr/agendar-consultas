package com.example.clinic.service;

import com.example.clinic.dao.MedicoDao;
import com.example.clinic.domain.Medico;

import java.util.Objects;

public class MedicoService {
    private final MedicoDao medicoDao;

    public MedicoService(MedicoDao medicoDao) {
        this.medicoDao = medicoDao;
    }

    public Long cadastrar(Medico medico) {
        Objects.requireNonNull(medico, "Dados do médico não podem ser nulos.");
        if (medico.getNome().trim().length() < 3) {
            throw new IllegalArgumentException("Nome do médico deve ter ao menos 3 caracteres.");
        }
        if (medico.getCrm().trim().isEmpty()) {
            throw new IllegalArgumentException("CRM é obrigatório.");
        }

        return medicoDao.salvar(medico);
    }
}
