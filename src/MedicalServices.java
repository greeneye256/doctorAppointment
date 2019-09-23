import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MedicalServices {
    private List<Clinic> clinics = new ArrayList<>();
    private List<Doctor> doctors = new ArrayList<>();
    private List<Patient> patients = new ArrayList<>();

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public List<Clinic> getClinics() {
        return clinics;
    }

    //finds a patient by phone number

    Patient findPatient(String phoneNumber) {
        for (Patient patient : patients
        ) {
            if (patient.getPhoneNumber().equals(phoneNumber)) {
                return patient;
            }
        }
        System.out.println("No patient with this number.");
        return null;
    }

    //finds doctor by id

    Doctor findDoctor(int id) {
        for (Doctor doctor : doctors
        ) {
            if (doctor.getId() == id) {
                return doctor;
            }
        }
        System.out.println("No doctor with this id.");
        return null;
    }

    List<Clinic> clinicsThatHaveRoomOnDate(LocalDate localDate) {
        List<Clinic> clinicsWithRoomOnDate = new ArrayList<>();
        for (Clinic clinic : clinics
        ) {
            if (clinic.isRoomForAppointment(localDate)) {
                clinicsWithRoomOnDate.add(clinic);
            }
        }
    return clinicsWithRoomOnDate;
    }

    void printList(List<Object> list){
        for (Object obj:list
             ) {
            System.out.println(obj);
        }
    }

    void printDoctors(){
        for (Doctor doctor:doctors
             ) {
            System.out.println(doctor);
        }
    }

    void printPatients(){
        for (Patient patient:patients
             ) {
            System.out.println(patient);
        }
    }

    Clinic selectClinicById(List<Clinic> clinics, int id){
        for (Clinic clinic:clinics
             ) {
            if (clinic.getId() == id){
                return clinic;
            }
        }
        return null;
    }
    void createAppointment(Clinic clinic, Patient patient, Doctor doctor, LocalDate localDate, LocalTime localTime){
        Appointment appointment = new Appointment(patient,doctor, LocalDateTime.of(localDate,localTime));
        clinic.getClinicAppointments().add(appointment);
        patient.getPatientAppointments().add(appointment);
        doctor.getDoctorAppointments().add(appointment);
    }
    void deletePatientAppointment(int id){

        for (Clinic clinic:clinics
             ) {
            for (Appointment appointment:clinic.getClinicAppointments()
                 ) {
                if (appointment.getId() == id){
                    clinic.getClinicAppointments().remove(appointment);
                    return;
                }
            }
        }
    }
}
