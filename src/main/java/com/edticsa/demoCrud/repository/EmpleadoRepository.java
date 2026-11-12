
package com.edticsa.demoCrud.repository;

import com.edticsa.demoCrud.models.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

}
