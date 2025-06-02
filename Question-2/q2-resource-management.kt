fun main() {
    val resourceManager = ResourceManager()
    resourceManager.addPatient(Patient(1, "Wanjiru", 2))
    resourceManager.addPatient(Patient(2, "Abrahim", 5))
    resourceManager.addPatient(Patient(3, "Wamai", 1))
    resourceManager.addStaff(Staff(1, "Dr. Sharon", "General"))
    resourceManager.addStaff(Staff(2, "Nurse Kimani", "Pediatrics"))
    resourceManager.addEquipment(Equipment(1, "X-Ray Machine", true))
    resourceManager.addEquipment(Equipment(2, "ECG machines", true))


    resourceManager.allocateResources()
}
data class Patient(val id: Int, val name: String, val urgency: Int)
data class Staff(val id: Int, val name: String, val expertise: String)
data class Equipment(val id: Int, val name: String, var available: Boolean)


class ResourceManager {
    private val patients = mutableListOf<Patient>()
    private val staff = mutableListOf<Staff>()
    private val equipment = mutableListOf<Equipment>()

    fun addPatient(patient: Patient) {
        patients.add(patient)
        println("Added patient: ${patient.name} with urgency level ${patient.urgency}")
    }

    fun addStaff(staffMember: Staff) {
        staff.add(staffMember)
        println("Added staff: ${staffMember.name} with expertise in ${staffMember.expertise}")
    }

    fun addEquipment(equip: Equipment) {
        equipment.add(equip)
        println("Added equipment: ${equip.name}")
    }

    fun allocateResources() {
        val sortedPatients = patients.sortedByDescending { it.urgency }

        for (patient in sortedPatients) {
            val availableStaff = staff.firstOrNull { it.expertise == "General" }
            val availableEquip = equipment.firstOrNull { it.available }

            if (availableStaff != null && availableEquip != null) {
                println("Allocating resources for patient: ${patient.name}")
                availableEquip.available = false 
            } else {
                println("No available resources for patient: ${patient.name}")
            }
        }
    }
}