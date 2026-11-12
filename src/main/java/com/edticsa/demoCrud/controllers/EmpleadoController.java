
package com.edticsa.demoCrud.controllers;

import com.edticsa.demoCrud.models.Empleado;
import com.edticsa.demoCrud.models.Departamento;
import com.edticsa.demoCrud.services.EmpleadoService;
import com.edticsa.demoCrud.services.DepartamentoService;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;
    private final DepartamentoService departamentoService;

    public EmpleadoController(
            EmpleadoService empleadoService,
            DepartamentoService departamentoService) {
        this.empleadoService = empleadoService;
        this.departamentoService = departamentoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("empleados", empleadoService.listar());
        return "empleados";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("empleado", new Empleado());
        model.addAttribute("departamentos", departamentoService.listar());
        model.addAttribute("accion", "/empleados/crear");
        return "empleado-form";
    }

    @PostMapping("/crear")
    public String crear(
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String puesto,
            @RequestParam Long departamentoId) {

        Empleado empleado = new Empleado();
        empleado.setNombre(nombre);
        empleado.setApellido(apellido);
        empleado.setPuesto(puesto);

        Departamento departamento = new Departamento();
        departamento.setId(departamentoId);
        empleado.setDepartamento(departamento);

        empleadoService.guardar(empleado);

        return "redirect:/empleados";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {

        Empleado empleado = empleadoService.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Empleado no encontrado"));

        model.addAttribute("empleado", empleado);
        model.addAttribute("departamentos", departamentoService.listar());
        model.addAttribute("accion", "/empleados/actualizar/" + id);

        return "empleado-form";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(
            @PathVariable Long id,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String puesto,
            @RequestParam Long departamentoId) {

        Empleado empleado = empleadoService.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Empleado no encontrado"));

        empleado.setNombre(nombre);
        empleado.setApellido(apellido);
        empleado.setPuesto(puesto);

        Departamento departamento = new Departamento();
        departamento.setId(departamentoId);
        empleado.setDepartamento(departamento);

        empleadoService.guardar(empleado);

        return "redirect:/empleados";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        empleadoService.eliminar(id);
        return "redirect:/empleados";
    }
}
