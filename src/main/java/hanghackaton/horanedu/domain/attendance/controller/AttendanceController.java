//package hanghackaton.horanedu.domain.attendance.controller;
//
//import hanghackaton.horanedu.domain.attendance.service.AttendanceService;
//import hanghackaton.horanedu.common.dto.ResponseDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/attendance")
//public class AttendanceController {
//    private final AttendanceService attendanceService;
//
//    @GetMapping
//    public ResponseDto<List<String>> getAttendanceList() {
//        return attendanceService.getAttendanceList();
//    }
//
//    @PostMapping
//    public ResponseDto<String> attendanceCheck(@RequestParam(name = "day") String date) {
//        return attendanceService.attendanceCheck(date);
//    }
//}
