import java.time.LocalDate;
import java.time.LocalDateTime;

public class Appointment {

    private static int countAppointments = 0;
    private int id;
    private Patient patient;
    private Doctor doctor;
    private LocalDateTime localDateTime;
    private Clinic clinic;

    public Appointment(Patient patient, Doctor doctor, LocalDateTime localDate) {
        this.patient = patient;
        this.doctor = doctor;
        this.localDateTime = localDate;
        this.id = ++countAppointments;
    }

    public int getId() {
        return id;
    }

    Clinic getClinic() {
        return clinic;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }



    LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
