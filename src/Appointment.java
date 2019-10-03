import java.time.LocalDateTime;

public class Appointment {

    private static int countAppointments = 0;
    private int id;
    private Patient patient;
    private Doctor doctor;
    private LocalDateTime localDateTime;
    private Clinic clinic;

    public Appointment(Patient patient, Doctor doctor, Clinic clinic, LocalDateTime localDate) {
        this.patient = patient;
        this.doctor = doctor;
        this.clinic = clinic;
        this.localDateTime = localDate;
        this.id = ++countAppointments;
    }

    int getId() {
        return id;
    }

    Clinic getClinic() {
        return clinic;
    }

    Doctor getDoctor() {
        return doctor;
    }

    Patient getPatient() {
        return patient;
    }



    LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    @Override
    public String toString() {
        return "Appointment[id = " + this.id + ", date = " + this.localDateTime.toString() + ", clinic = " + this.clinic.getName() + ", doctor = " + this.doctor.getName() + ", patient = " + this.patient.getName();
    }
}
