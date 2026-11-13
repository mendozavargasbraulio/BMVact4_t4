package com.edticsa.demoCrud.controllers;

import com.edticsa.demoCrud.dto.EmpleadoRequestDTO;
import com.edticsa.demoCrud.dto.EmpleadoResponseDTO;
import com.edticsa.demoCrud.services.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    // a) GET para listar con paginación: /api/empleados?page=0&size=5
    @GetMapping
    public ResponseEntity<Page<EmpleadoResponseDTO>> listar(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(empleadoService.listarPaginado(pageable));
    }

    // a) GET para ver un registro individual: /api/empleados/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        return empleadoService.obtenerPorId(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Empleado no encontrado con ID: " + id)));
    }

    // b) POST para crear
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody EmpleadoRequestDTO dto) {
        try {
            EmpleadoResponseDTO creado = empleadoService.crear(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
        }
    }

    // c) PUT para actualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody EmpleadoRequestDTO dto) {
        try {
            return empleadoService.actualizar(id, dto)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(Map.of("error", "Empleado no encontrado con ID: " + id)));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
        }
    }

    // d) DELETE para eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (empleadoService.eliminar(id)) {
            return ResponseEntity.ok(Map.of("mensaje", "Empleado eliminado correctamente"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Empleado no encontrado con ID: " + id));
    }
}