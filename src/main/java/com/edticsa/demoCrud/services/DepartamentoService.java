
package com.edticsa.demoCrud.services;

import com.edticsa.demoCrud.models.Departamento;
import com.edticsa.demoCrud.repository.DepartamentoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DepartamentoService {

    private final DepartamentoRepository repository;

    public DepartamentoService(DepartamentoRepository repository) {
        this.repository = repository;
    }

    public List<Departamento> listar() {
        return repository.findAll();
    }

    public Optional<Departamento> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Departamento guardar(Departamento departamento) {
        return repository.save(departamento);
    }
}
