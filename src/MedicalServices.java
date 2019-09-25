
import java.util.ArrayList;
import java.util.List;

class MedicalServices {
    private List<Clinic> clinics = new ArrayList<>();
    private List<Doctor> doctors = new ArrayList<>();
    private List<Patient> patients = new ArrayList<>();

    List<Doctor> getDoctors() {
        return doctors;
    }

    List<Patient> getPatients() {
        return patients;
    }

    List<Clinic> getClinics() {
        return clinics;
    }

    void printDoctors(){
        for (Doctor doctor:doctors
             ) {
            System.out.println(doctor);
        }
    }

    void printClinics(){
        for (Clinic clinic:clinics
             ) {
            System.out.println(clinic);
        }
    }

    void printPatients(){
        for (Patient patient:patients
             ) {
            System.out.println(patient);
        }
    }

    void addDoctor(Doctor doctor){
        this.doctors.add(doctor);
    }
    void addPatient(Patient patient){
        this.patients.add(patient);
    }
}
