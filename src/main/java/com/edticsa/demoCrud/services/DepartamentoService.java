package com.edticsa.demoCrud.services;

import com.edticsa.demoCrud.dto.DepartamentoDTO;
import com.edticsa.demoCrud.models.Departamento;
import com.edticsa.demoCrud.repository.DepartamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartamentoService {

    private final DepartamentoRepository departamentoRepository;

    public DepartamentoService(DepartamentoRepository departamentoRepository) {
        this.departamentoRepository = departamentoRepository;
    }

    public List<DepartamentoDTO> listarTodos() {
        return departamentoRepository.findAll().stream()
                .map(d -> new DepartamentoDTO(d.getId(), d.getNombre(), d.getDescripcion()))
                .toList();
    }

    public Optional<DepartamentoDTO> obtenerPorId(Long id) {
        return departamentoRepository.findById(id)
                .map(d -> new DepartamentoDTO(d.getId(), d.getNombre(), d.getDescripcion()));
    }

    public DepartamentoDTO crear(DepartamentoDTO dto) {
        Departamento d = new Departamento();
        d.setNombre(dto.nombre());
        d.setDescripcion(dto.descripcion());
        Departamento guardado = departamentoRepository.save(d);
        return new DepartamentoDTO(guardado.getId(), guardado.getNombre(), guardado.getDescripcion());
    }
}