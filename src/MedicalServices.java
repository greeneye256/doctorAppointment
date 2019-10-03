
import java.time.LocalDate;
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

    private boolean isDuplicate(Doctor doctorToCheck){
        for (Doctor doctor:this.doctors
             ) {
            if (doctor.getPhoneNumber() == doctorToCheck.getPhoneNumber()){
                return true;
            }
        }
        return false;
    }

    private boolean isDuplicate(Patient patientToCheck){
        for (Patient patient:this.patients
             ) {
            if (patient.getPhoneNumber() == patientToCheck.getPhoneNumber()){
                return true;
            }
        }
        return false;
    }

    public List<Clinic> clinicsAvailableOnDate(Doctor doctorForAppointment, LocalDate dateForAppointment){
        List<Clinic> clinicsAvailableOnDate = new ArrayList<>();
        for (Clinic clinic:this.clinics
        ) {
            if (doctorForAppointment.getAppointmentsOnDate(dateForAppointment).size() == 2){
                if (clinic.hasCoffee()){
                    clinicsAvailableOnDate.add(clinic);
                }
            }
            else {
                if (clinic.getAppointmentsOnDate(dateForAppointment).size()<clinic.getCapacity()){
                    clinicsAvailableOnDate.add(clinic);
                }
            }

        }

        if (clinicsAvailableOnDate.size() == 0){
            System.out.println("Doctor is available but we don't have space in our clinics for your appointment on " + dateForAppointment + "!");
            return null;
        }
        return clinicsAvailableOnDate;
    }

    void addDoctor(Doctor doctor){
        if (!isDuplicate(doctor)){
            this.doctors.add(doctor);
        }
        else {
            System.out.println("This doctor already is in your system!");
        }
    }

    void addPatient(Patient patient){
        if (!isDuplicate(patient)){
            this.patients.add(patient);
        }
        else {
            System.out.println("This patient already is in your system!");
        }
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

    void deleteDoctor(Doctor doctor){
        for (Appointment appointment:doctor.getDoctorAppointments()
        ) {
            appointment.getPatient().deleteAppointment(appointment);
            appointment.getClinic().deleteAppointment(appointment);
        }
        this.doctors.remove(doctor);
    }

    void deletePatient(Patient patient){
        for (Appointment appointment:patient.getPatientAppointments()
             ) {
            appointment.getDoctor().deleteAppointment(appointment);
            appointment.getClinic().deleteAppointment(appointment);
        }
    }

}
