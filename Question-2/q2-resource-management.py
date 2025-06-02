from datetime import datetime

class Patient:
    def __init__(self, id, name, urgency, required_specialization, required_equipment_types, arrival_time, available=True):
        self.id = id
        self.name = name
        self.urgency = urgency
        self.required_specialization = required_specialization
        self.required_equipment_types = required_equipment_types 
        self.arrival_time = arrival_time
        self.available = available
class Bed:
    def __init__(self, id, available=True):
        self.id = id
        self.available = available
class Equipment:
    def __init__(self, id, type, available=True):
        self.id = id
        self.type = type
        self.available = available
class Doctor:
    def __init__(self, id, name, specialization, available=True):
        self.id = id
        self.name = name
        self.specialization = specialization
        self.available = available
class AllocationResult:
    def __init__(self, patient, doctor, bed, equipment_dict):
        self.patient = patient
        self.doctor = doctor
        self.bed = bed
        self.equipment_dict = equipment_dict  
def allocate_resources(patients, beds, doctors, equipment_list, surge_threshold=5):
    patients_sorted = sorted(patients, key=lambda p: (-p.urgency, p.arrival_time))
    allocations = []
    unassigned_patients = []
    available_beds = [bed for bed in beds if b.available]
    available_doctors = [doctor for doctor in doctors if doctor.available]
    available_equipment = [equipment for equipment in equipment_list if equipment.available]

    if len(patients_sorted) > surge_threshold:
        print("Surge detected! Activating extra resources if available...")

    for patient in patients_sorted:
        doctors = next((doctor for doctor in available_doctors if doctor.specialization == patient.required_specialization and doctor.available), None)
        if doctors:
            doctor.available = False
            available_doctors.remove(doctors)
        else:
            doctors = None
        bed = available_beds.pop(0) if available_beds else None
        if bed:
            bed.available = False
        equipment_dict = {}
        equipment_assigned = True
        for req_type in patient.required_equipment_types:
            equip = next((equipment for equipment in available_equipment if equipment.type == req_type and equipment.available), None)
            if equip:
                equip.available = False
                available_equipment.remove(equip)
                equipment_dict[req_type] = equip
            else:
                equipment_assigned = False
                equipment_dict[req_type] = None
        if doctor and bed and equipment_assigned:
            allocations.append(AllocationResult(patient, doctor, bed, equipment_dict))
        else:
            if doctor:
                doctor.available = True
                available_doctors.append(doctor)
            if bed:
                bed.available = True
                available_beds.insert(0, bed)
            for etype, equip in equipment_dict.items():
                if equip:
                    equip.available = True
                    available_equipment.append(equip)
            unassigned_patients.append(patient)
    resource_status = {
        "beds": beds,
        "doctors": doctors,
        "equipment": equipment_list,
    }

    return allocations, unassigned_patients, resource_status
if __name__ == "__main__":
    import datetime
    patient1 = Patient(1, "John Doe", 5, "Cardiology", ["ventilator"], arrival_time=datetime.datetime(2025,6,2,8,0,0))
    patient2 = Patient(2, "Hagoes", 10, "TB", ["X-Ray"], arrival_time=datetime.datetime(2025,6,2,8,5,0))
    patient3 = Patient(3, "Marta", 7, "Cardiology", ["x-ray", "Ventilator"], arrival_time=datetime.datetime(2025,6,2,8,10,0))
    patients = [patient1, patient2, patient3]
    bed1 = Bed(1001)
    bed2 = Bed(1002)
    beds = [bed1, bed2]
    doctor1 = Doctor(101, "Dr. alemseged", "Cardiology")
    doctor2 = Doctor(102, "Dr. Lwam", "TB")
    doctors = [doctor1, doctor2]
    equipment1 = Equipment(201, "ventilator")
    equipment2 = Equipment(202, "X-Ray")
    equipment3 = Equipment(203, "Ventilator")
    equipment_list = [equipment1, equipment2, equipment3]
    allocations, unassigned, resources = allocate_resources(patients, beds, doctors, equipment_list, surge_threshold=2)

    