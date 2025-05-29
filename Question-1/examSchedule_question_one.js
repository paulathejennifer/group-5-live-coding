const students = [
    { id: 1, name: "Rigbe", subjects: ["NYJ", "UI/UX", "Kotlin"], specialRequirements: "extraTime" },
    { id: 2, name: "Birhanu", subjects: ["NYJ", "DAS", "Python"], specialRequirements: null },
    { id: 3, name: "Jenny", subjects: ["UI/UX", "DAS", "Kotlin"], specialRequirements: "separateClass" }
];
const exams = [
    { subject: "NYJ", duration: 2, type: "written", resource: "classroom", students: [1, 2] },
    { subject: "UI/UX", duration: 2, type: "practical", resource: "lab", students: [1, 3] },
    { subject: "DAS", duration: 2, type: "practical", resource: "lab", students: [2, 3] },
];
const facilities = [
    { id: "Lovelace", type: "classroom", capacity: 30, available: true },
    { id: "Ada", type: "classroom", capacity: 30, available: true },
    { id: "AnitaB", type: "lab", capacity: 20, available: true }
];
const invigilators = [
    { id: 1, name: "Ms.Hunter", availability: ["Monday", "Tuesday", "Wednesday"], expertise: "written", assigned: [] },
    { id: 2, name: "Ms. Sharon", availability: ["Tuesday", "Wednesday", "Thursday"], expertise: "practical", assigned: [] }
];
const examDays = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"];
const slotsPerDay = ["Morning", "Afternoon"];
let schedule = [];
function hasStudentConflict(studentId, day, slot) {
    return schedule.some(sch =>
        sch.day === day && sch.slot === slot && sch.exam.students.includes(studentId)
    );
}
function hasEnoughRevisionTime(studentId, indexOfDay, examDays) {
    const prevDay = examDays[indexOfDay - 1];
    const nextDay = examDays[indexOfDay + 1];
    const hasExamPrevDay = prevDay && schedule.some(sch =>
        sch.day === prevDay && sch.exam.students.includes(studentId)
    );
    const hasExamNextDay = nextDay && schedule.some(sch =>
        sch.day === nextDay && sch.exam.students.includes(studentId)
    );
    return !(hasExamPrevDay && hasExamNextDay);
}
function findAvailableFacility(exam, day, slot) {
    return facilities.find(faci =>
        faci.type === exam.resource &&
        faci.available &&
        !schedule.some(sch => sch.day === day && sch.slot === slot && sch.facility.id === faci.id
        )
    );
}
function findAvailableInvigilator(exam, day) {
    return invigilators.find(i => i.availability.includes(day) && i.expertise === exam.type && !i.assigned.includes(day)
    );
}
function scheduleExams() {
    let examsToSchedule = [...exams];
    for (let indexOfDay = 0; indexOfDay < examDays.length; indexOfDay++) {
        const day = examDays[indexOfDay];
        for (let slot of slotsPerDay) {
            for (let i = 0; i < examsToSchedule.length; i++) {
                const exam = examsToSchedule[i];
                const hasConflict = exam.students.some(studentId =>
                    hasStudentConflict(studentId, day, slot)
                );
                const hasRevisionTime = exam.students.every(studentId =>
                    hasEnoughRevisionTime(studentId, indexOfDay, examDays)
                );
                const facility = findAvailableFacility(exam, day, slot);
                const invigilator = findAvailableInvigilator(exam, day);
                if (!hasConflict && hasRevisionTime && facility && invigilator) {
                    schedule.push({
                        day, slot, exam, facility, invigilator
                    });
                    invigilator.assigned.push(day);
                    examsToSchedule.splice(i, 1);
                    i--;
                }
            }
        }
    }
    if (examsToSchedule.length > 0) {
        console.log( examsToSchedule);
    } else {
        console.log("All exams scheduled correctly");
    }
}
function applySpecialAccommodations() {
    schedule.forEach(sch => {
        const examStudents = sch.exam.students;
        examStudents.forEach(studentId => {
            const student = students.find(student => student.id === studentId);
            if (student.specialRequirements === "extraTime") {
                sch.exam.duration += 0.5;
                console.log(`Added extra time for ${student.name} in ${sch.exam.subject}`);
            } else if (student.specialRequirements === "separateRoom") {
                sch.facility = { ...sch.facility, id: `${sch.facility.id}-Separate` };
                console.log(`Assigned separate class for ${student.name} in ${s.exam.subject}`);
            }
        });
    });
}
scheduleExams();
applySpecialAccommodations();
schedule.forEach(sch => {
    console.log(`${sch.day} ${sch.slot}: ${sch.exam.subject} in ${sch.facility.id}, invigilated by ${sch.invigilator.name}`);
});
 
const usr={
    name:'Jen',
    age:22
};
const user={
    name:"Andrew",
    location:"Philadephia"
};
const a={...usr};
console.log(a);
const merged={...usr,...user}
console.log(merged)
