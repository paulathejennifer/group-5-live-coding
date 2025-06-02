class PriorityQueue{
    constructor(){
 this.items= [];
    }
    enqueue(patient, priority){
        const element = {patient, priority};
        let added = false;
        for(let i = 0; i < this.items.length; i++){
            if (priority > this.items[i].priority){
                this.items.splice(i, 0, element);
                break;
            }
        }

        if(!added)this.items.push(element)
    }

    dequeue(){
        return this.items.shift()
    }

    isEmpty(){
        return this.items.length === 0;
    }

    peek(){
        return this.items[0];
    }
   

}

class Patient{
    constructor(id, urgencyLevel, arrivalTime, needs, requiredEquipment){
        this.id = id;
        this.urgencyLevel = urgencyLevel;
        this.arrivalTime = arrivalTime;
        this.needs = needs;
        this.requiredEquipment = requiredEquipment;
        this.assigned = false;
    }
}

class Bed{
    constructor(id, type, available= true){
        this.id = id;
        this.type = type;
        this.available = available;
    }
}

class Staff{
    constructor(id, name, specialization, available= true){
        this.id = id;
        this.name= name;
        this.specialization = specialization;
        this.available = available;
    }
}

class Equipment{
    constructor(id, type, available=true){
        this.id = id;
        this.type = type;
        this.available = available;
    }
}

class ResourceManager{
    constructor(){
    this.patients = [];
    this.beds = [];
    this.staff = [];
    this.equipment = [];
    this.waitingQueue = new PriorityQueue()
    }
    addPatient(patient) {
    this.patients.push(patient);
    this.waitingQueue.enqueue(patient, this.scorePatient(patient));

}

addBeds(bed){
    this.beds.push(bed)
}

addStaff(staff){
    this.staff.push(staff)

}

addEquipment(equipment){
    this.equipment.push(equipment);
}

scorePatient(patient){
    const now = Date.now()
    const waitingTime = (now - patient.arrivalTime)/1000
    const urgencyScore = 6 - patient.urgencyLevel;
    return urgencyScore * 1000 + waitingTime
    
}

allocateResources(){
    const allocations = []
    while(!this.waitingQueue.isEmpty()){

        const {patient} = this.waitingQueue.dequeue();
        if(patient.assigned)continue;
        const bed = this.findAvailableBed(patient);
        const staff = this.findAvailableStaff(patient);
        const equipment = this.findAvailableEquipment(patient);
        
if(bed && staff && equipment){
bed.available =false;
staff.available = false;
equipment.forEach(eq => (eq.available= false));
patient.assigned = true;
allocations.push({
    patientId: patient.id,
    bedId: bed.id,
    staffId: this.staff.id,
    equipmentIds: equipment.map(eq => eq.id)

});

}else{
    this.waitingQueue.enqueue(patient, this.scorePatient(this.patient));
    break;
}
}


return allocations;
}

findAvailableBed(patient){
    return this.beds.find(bed => bed.available && (bed.type === 'ICU' || bed.type === 'General'));
}

findAvailableStaff(patient){
    return this.staff.find(
        staff => staff.available && staff.specialization === patient.needs.specialization
    );
}

findAvailableEquipment(patient){
    if (!patient.requiredEquipment || patient.requiredEquipment.length === 0)return [];
    const found = [];
    for (const equipType of patient.requiredEquipment){
        const eq= this.equipment.find(e => e.available && e.type === equipType)
        if(!eq) return null;
        found.push(eq)
    }
    return found
}
handleSpikes(){
    this.beds.forEach(bed => (bed.available = true));
    this.staff.forEach(staff => (staff.available = true));
    this.equipment.forEach(eq => (eq.available = true));
}

}

const waiyakiHospital = new ResourceManager();
waiyakiHospital.addBeds(new Bed(1, "ICU"));
waiyakiHospital.addBeds(new Bed(2, "General"));
waiyakiHospital.addStaff(new Staff(1, "Dr. Pauline", "cardiology"));
waiyakiHospital.addStaff(new Staff(2, "Nurse Mutai", "general"));
waiyakiHospital.addEquipment(new Equipment(1, "ECG"));
waiyakiHospital.addEquipment(new Equipment(2, "X-ray"));
waiyakiHospital.addPatient(new Patient(1, 2, Date.now() - 10000, { specialization: "cardiology" }, ["X-ray"]));
waiyakiHospital.addPatient(new Patient(2, 4, Date.now() - 50000, { specialization: "general" }, ["ECG"]));

const allocations=waiyakiHospital.allocateResources();
console.log(allocations);

waiyakiHospital.handleSpikes();








