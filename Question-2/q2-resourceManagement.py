class Patient:
    def __init__(self, id, name, urgency, required_specialization, available=True):
        self.id = id
        self.name = name
        self.urgency = urgency
        self.required_specialization = required_specialization
        self.available = available
class Bed:
    def __init__(self, id, available=True):
        self.id = id
        self.available = available
class Equipment:
    def __init__(self, id, type, available=True):
        self.id = id
        self.available = available
        self.type = type
def assign_patients_to_doctors(patients, doctors):
    patients.sort(key=lambda x: x.urgency, reverse=True)
    assignments = {}
    unassigned_patients = []
    for patient in patients:
        doctor = next((d for d in doctors if d.available and d.specialization == patient.required_specialization), None)
        if doctor:
            assignments[patient] = doctor
            doctor.available = False
        else:
            unassigned_patients.append(patient)
    return assignments, unassigned_patients
def assign_beds_to_patients(patients, beds):
    assignments = {}
    available_beds = [b for b in beds if b.available]
    for patient in patients:
        bed = available_beds.pop(0) if available_beds else None
        if bed:
            assignments[patient] = bed
            bed.available = False
        else:
            assignments[patient] = None
    return assignments
def assign_equipments_to_patients(patients, equipment_list, required_type):
    assignments = {}
    available_equipments = [e for e in equipment_list if e.available and e.type == required_type]
    for patient in patients:
        equipment = available_equipments.pop(0) if available_equipments else None
        if equipment:
            assignments[patient] = equipment
            equipment.available = False
        else:
            assignments[patient] = None
    return assignments
patient1 = Patient(1, "John Doe", 5, "Cardiology")
patient2 = Patient(2, "Hagoes", 10, "TB")
patients = [patient1, patient2]
bed1 = Bed(1001)
bed2 = Bed(1002)
beds = [bed1, bed2]
bed_assignments = assign_beds_to_patients(patients, beds)
for patient, bed in bed_assignments.items():
    bed_info = f"Bed {bed.id}" if bed else "No bed"
    print(f"Patient {patient.name} assigned to {bed_info}")