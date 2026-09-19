package com.techsolutions.chamados.service;

import com.techsolutions.chamados.model.Chamado;
import com.techsolutions.chamados.repository.ChamadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChamadoService {

    private final ChamadoRepository repository;

    public ChamadoService(ChamadoRepository repository) {
        this.repository = repository;
    }

    public Chamado cadastrar(Chamado chamado) {
        chamado.setId(null); // garante que o banco gere o id
        return repository.save(chamado);
    }

    public List<Chamado> listarTodos() {
        return repository.findAll();
    }

    public Optional<Chamado> buscarPorId(Integer id) {
        return repository.findById(id);
    }

    public Optional<Chamado> atualizar(Integer id, Chamado dados) {
        return repository.findById(id).map(existente -> {
            existente.setTitulo(dados.getTitulo());
            existente.setDescricao(dados.getDescricao());
            existente.setPrioridade(dados.getPrioridade());
            existente.setSolicitante(dados.getSolicitante());
            existente.setStatus(dados.getStatus());
            return repository.save(existente);
        });
    }

    public boolean remover(Integer id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
