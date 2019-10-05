import java.time.LocalDate;
import java.time.LocalDateTime;
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

    String getName() {
        return name;
    }

    int getMaxAppointments() {
        return maxAppointments;
    }

    boolean isFreeOnThisHour(LocalDateTime localDateTime){
        for (Appointment appointment:this.getAppointmentsOnDate(localDateTime.toLocalDate())
        ) {
            if (((localDateTime.toLocalTime().isAfter(appointment.getLocalDateTime().toLocalTime().minusHours(1)))) && ((localDateTime.toLocalTime().isBefore(appointment.getLocalDateTime().toLocalTime().plusHours(1))))){
                return false;
            }
        }
        return true;
    }

    String getPhoneNumber() {
        return phoneNumber;
    }

    int getId() {
        return id;
    }

    List<Appointment> getDoctorAppointments() {
        return doctorAppointments;
    }

    void deleteAppointment(Appointment appointment) {

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

    void addAppointment(Appointment appointment){
        if (appointment == null){
            return;
        }
        this.doctorAppointments.add(appointment);
    }

    void printAppointments(){
        if (this.doctorAppointments.size() == 0 ){
            System.out.println("No appointments for this doctor yet!");
        }
        for (Appointment appointment:this.doctorAppointments
             ) {
            System.out.println(appointment);
        }
    }

    @Override
    public String toString() {
        return "Doctor[id = " + id + ", name = " + name + ", speciality = " + speciality + ", email = " + email + ", phone number = " + phoneNumber + "]";
    }
}
