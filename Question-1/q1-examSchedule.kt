import java.time.LocalDateTime
import java.time.Duration


data class Student(
    val id: Int,
    val name: String,
    val specialNeeds: Boolean = false,
    val subjects: List<String>
)

data class Invigilator(
    val id: Int,
    val name: String,
    val availableTimes: List<LocalDateTime>
)

data class Room(
    val id: Int,
    val name: String,
    val isLab: Boolean,
    val capacity: Int
)

data class SubjectRequirement(
    val subject: String,
    val needsLab: Boolean,
    val durationMinutes: Int,
    val minRevisionHours: Long
)

data class Exam(
    val subject: String,
    val students: List<Student>,
    val time: LocalDateTime,
    val room: Room,
    val invigilator: Invigilator,
    val durationMinutes: Int
)


class ExamScheduler(
    private val students: List<Student>,
    private val invigilators: List<Invigilator>,
    private val rooms: List<Room>,
    private val subjectRequirements: Map<String, SubjectRequirement>
) {
    private val scheduledExams = mutableListOf<Exam>()

    fun scheduleExams(startTime: LocalDateTime, endTime: LocalDateTime) {
        val examsToSchedule = students.flatMap { it.subjects }.distinct()
        var currentTime = startTime

        for (subject in examsToSchedule) {
            val req = subjectRequirements[subject] ?: continue
            val subjectStudents = students.filter { it.subjects.contains(subject) }
            val examRoom = rooms.find { it.isLab == req.needsLab && it.capacity >= subjectStudents.size }
                ?: throw Exception("No room available for $subject")
            val invigilator = invigilators.find { inv ->
                inv.availableTimes.any { at -> at == currentTime }
            } ?: throw Exception("No invigilator available at $currentTime")

            val duration = if (subjectStudents.any { it.specialNeeds }) req.durationMinutes + 30 else req.durationMinutes

          
            for (student in subjectStudents) {
                val lastExam = scheduledExams.filter { it.students.contains(student) }
                    .maxByOrNull { it.time }
                if (lastExam != null) {
                    val hoursSinceLastExam = Duration.between(lastExam.time, currentTime).toHours()
                    if (hoursSinceLastExam < req.minRevisionHours) {
                        currentTime = lastExam.time.plusHours(req.minRevisionHours)
                    }
                }
            }

            val exam = Exam(
                subject = subject,
                students = subjectStudents,
                time = currentTime,
                room = examRoom,
                invigilator = invigilator,
                durationMinutes = duration
            )
            scheduledExams.add(exam)

          
            currentTime = currentTime.plusMinutes(duration.toLong()).plusHours(1) 
            if (currentTime > endTime) break
        }
    }

    fun printSchedule() {
        for (exam in scheduledExams) {
            println("Exam: ${exam.subject} at ${exam.time} in ${exam.room.name} with ${exam.invigilator.name}, duration: ${exam.durationMinutes} min")
            val studentNames = exam.students.joinToString { it.name }
            println("Students: $studentNames\n")
        }
    }
}

fun main() {
    val students = listOf(
        Student(1, "Alice", false, listOf("Math", "Biology")),
        Student(2, "Bob", true, listOf("Math", "Physics")),
        Student(3, "Cara", false, listOf("Biology"))
    )
    val invigilators = listOf(
        Invigilator(1, "Mr. Smith", listOf(
            LocalDateTime.of(2025, 6, 1, 9, 0),
            LocalDateTime.of(2025, 6, 1, 14, 0)
        )),
        Invigilator(2, "Ms. Jones", listOf(
            LocalDateTime.of(2025, 6, 1, 9, 0),
            LocalDateTime.of(2025, 6, 1, 14, 0)
        ))
    )
    val rooms = listOf(
        Room(1, "Room 101", false, 30),
        Room(2, "Science Lab", true, 20)
    )
    val subjectRequirements = mapOf(
        "Math" to SubjectRequirement("Math", false, 90, 24),
        "Biology" to SubjectRequirement("Biology", true, 120, 24),
        "Physics" to SubjectRequirement("Physics", true, 120, 24)
    )

    val scheduler = ExamScheduler(students, invigilators, rooms, subjectRequirements)
    scheduler.scheduleExams(
        startTime = LocalDateTime.of(2025, 6, 1, 9, 0),
        endTime = LocalDateTime.of(2025, 6, 7, 16, 0)
    )
    scheduler.printSchedule()
}