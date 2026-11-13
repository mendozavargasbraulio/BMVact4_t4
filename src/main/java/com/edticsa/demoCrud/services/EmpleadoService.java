package com.edticsa.demoCrud.services;

import com.edticsa.demoCrud.dto.EmpleadoRequestDTO;
import com.edticsa.demoCrud.dto.EmpleadoResponseDTO;
import com.edticsa.demoCrud.models.Departamento;
import com.edticsa.demoCrud.models.Empleado;
import com.edticsa.demoCrud.repository.DepartamentoRepository;
import com.edticsa.demoCrud.repository.EmpleadoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final DepartamentoRepository departamentoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository, DepartamentoRepository departamentoRepository) {
        this.empleadoRepository = empleadoRepository;
        this.departamentoRepository = departamentoRepository;
    }

    public Page<EmpleadoResponseDTO> listarPaginado(Pageable pageable) {
        return empleadoRepository.findAll(pageable).map(this::toResponseDTO);
    }

    public Optional<EmpleadoResponseDTO> obtenerPorId(Long id) {
        return empleadoRepository.findById(id).map(this::toResponseDTO);
    }

    public EmpleadoResponseDTO crear(EmpleadoRequestDTO dto) {
        Departamento depto = departamentoRepository.findById(dto.departamentoId())
                .orElseThrow(() -> new IllegalArgumentException("No existe el departamento con ID: " + dto.departamentoId()));

        Empleado empleado = new Empleado();
        empleado.setNombre(dto.nombre());
        empleado.setApellido(dto.apellido());
        empleado.setPuesto(dto.puesto());
        empleado.setDepartamento(depto);

        return toResponseDTO(empleadoRepository.save(empleado));
    }

    public Optional<EmpleadoResponseDTO> actualizar(Long id, EmpleadoRequestDTO dto) {
        return empleadoRepository.findById(id).map(empleado -> {
            Departamento depto = departamentoRepository.findById(dto.departamentoId())
                    .orElseThrow(() -> new IllegalArgumentException("No existe el departamento con ID: " + dto.departamentoId()));

            empleado.setNombre(dto.nombre());
            empleado.setApellido(dto.apellido());
            empleado.setPuesto(dto.puesto());
            empleado.setDepartamento(depto);
            return toResponseDTO(empleadoRepository.save(empleado));
        });
    }

    public boolean eliminar(Long id) {
        if (!empleadoRepository.existsById(id)) {
            return false;
        }
        empleadoRepository.deleteById(id);
        return true;
    }

    private EmpleadoResponseDTO toResponseDTO(Empleado e) {
        return new EmpleadoResponseDTO(
                e.getId(),
                e.getNombre(),
                e.getApellido(),
                e.getPuesto(),
                e.getDepartamento().getId(),
                e.getDepartamento().getNombre()
        );
    }
}