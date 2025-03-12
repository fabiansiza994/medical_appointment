package com.fmsp.medical_appointment.repository.jpa.mysql;

import com.fmsp.medical_appointment.entity.core.mysql.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
}
