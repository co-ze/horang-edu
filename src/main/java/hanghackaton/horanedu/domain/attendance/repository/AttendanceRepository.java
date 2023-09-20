package hanghackaton.horanedu.domain.attendance.repository;

import hanghackaton.horanedu.domain.attendance.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
}
