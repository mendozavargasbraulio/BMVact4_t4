
package com.edticsa.demoCrud.services;

import com.edticsa.demoCrud.models.Empleado;
import com.edticsa.demoCrud.models.Departamento;
import com.edticsa.demoCrud.repository.EmpleadoRepository;
import com.edticsa.demoCrud.repository.DepartamentoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final DepartamentoRepository departamentoRepository;

    public EmpleadoService(
            EmpleadoRepository empleadoRepository,
            DepartamentoRepository departamentoRepository) {
        this.empleadoRepository = empleadoRepository;
        this.departamentoRepository = departamentoRepository;
    }

    public List<Empleado> listar() {
        return empleadoRepository.findAll();
    }

    public Optional<Empleado> buscarPorId(Long id) {
        return empleadoRepository.findById(id);
    }

    public Empleado guardar(Empleado empleado) {
        if (empleado.getDepartamento() == null ||
                empleado.getDepartamento().getId() == null) {
            throw new IllegalArgumentException("Debe seleccionar un departamento");
        }

        Long departamentoId = empleado.getDepartamento().getId();

        Departamento departamento = departamentoRepository.findById(departamentoId)
                .orElseThrow(() -> new IllegalArgumentException("Departamento no encontrado"));

        empleado.setDepartamento(departamento);

        return empleadoRepository.save(empleado);
    }

    public void eliminar(Long id) {
        empleadoRepository.deleteById(id);
    }
}
