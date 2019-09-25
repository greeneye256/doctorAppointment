import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Doctor {

    private String name;
    private String speciality;
    private static int doctorCount = 0;
    private int id;
    private String email;
    private String phoneNumber;
    private List<Appointment> doctorAppointments = new ArrayList<>();
    private int maxAppointments = 2;

    public Doctor(String name, String speciality, String email, String phoneNumber) {
        this.name = name;
        this.speciality = speciality;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.id = ++doctorCount;
    }

    public String getName() {
        return name;
    }

    public void setMaxAppointments(int maxAppointments) {
        this.maxAppointments = maxAppointments;
    }

    public int getId() {
        return id;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public List<Appointment> getDoctorAppointments() {
        return doctorAppointments;
    }

    void deleteAppointment(Appointment appointment) {

        appointment.getClinic().getClinicAppointments().remove(appointment);
        appointment.getPatient().getPatientAppointments().remove(appointment);
        doctorAppointments.remove(appointment);

    }

    List<Appointment> getAppointmentsOnDate(LocalDate localDate) {
        List<Appointment> appointmentsOnDate = new ArrayList<>();
        for (Appointment appointment : doctorAppointments
        ) {
            if (appointment.getLocalDateTime().toLocalDate().isEqual(localDate)) {
                appointmentsOnDate.add(appointment);
            }
        }
        return appointmentsOnDate;
    }

    //Verify if the doctor is available on a certain hour so appointment can be made. The distance between appointments
    // has to be minimum 60 minutes

    boolean isAvailableOnHour(LocalDate localDate, LocalTime localTime) {
        for (Appointment appointment : getAppointmentsOnDate(localDate)
        ) {
            if (Duration.between(appointment.getLocalDateTime().toLocalTime(), localTime).toMinutes() < 60) {
                return false;
            }
        }
        return true;
    }
    //Verify if the doctor is available on a certain day so an appointment can be made. Also checks if the doctor
    // has coffee in all clinics so he can consult 3 patients in that day.

    boolean isAvailableOnDate(LocalDate localDate) {
        List<Appointment> appointmentsOnDate = getAppointmentsOnDate(localDate);
        int numberOfCoffes = 0;
        for (Appointment appointment : appointmentsOnDate
        ) {
            if (appointment.getClinic().hasCoffee()) {
                numberOfCoffes++;
            }
        }

        if (appointmentsOnDate.size() > maxAppointments) {
            return false;
        }
        if (appointmentsOnDate.size() == maxAppointments) {
            return (appointmentsOnDate.size() == numberOfCoffes);
        }
        return true;
    }

    @Override
    public String toString() {
        return "Doctor[id = " + id + ", name = " + name + ", speciality = " + speciality + ", email = " + email + ", phone number = " + phoneNumber + "]";
    }
}
