package hanghackaton.horanedu.attendance.repository;

import hanghackaton.horanedu.attendance.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
}
