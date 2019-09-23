import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Patient {

    private int id;
    private static int patientCount = 0;
    private String name;
    private List<String> diseases = new ArrayList<>();
    private String phoneNumber;
    private List<Appointment> patientAppointments;

    public Patient(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.id = ++patientCount;
    }

    public int getId() {
        return id;
    }

    //add disease if not already between diseases

    void addDisease(String disease) {
        if (diseases.contains(disease)) {
            System.out.println("The patient is already known with having " + disease + ".");
        } else {
            diseases.add(disease);
        }
    }

    //remove disease from list if is in list

    void removeDisease(String disease) {

        if (diseases.contains(disease)){
            diseases.remove(disease);
        }else {
            System.out.println("The patient does not have " + disease + ".");
        }

        diseases.remove(disease);
    }

    void removeAllDiseases(){
        diseases.clear();

    }

    List<Appointment> getPatientAppointments() {
        return patientAppointments;
    }

    public String getName() {
        return name;
    }

    String getPhoneNumber() {
        return phoneNumber;
    }

    boolean appointmentCanBeDone(LocalDateTime localDateTime){
        for (Appointment appointment:patientAppointments
             ) {
            if (Duration.between(appointment.getLocalDateTime(),localDateTime).toMinutes()<60){
                return false;
            }
        }
        return true;
    }

    void printDiseases() {
        if (diseases.size() == 0) {
            System.out.println("No disease.");
        }
        for (String disease : diseases
        ) {
            System.out.print(" - " + disease + " - ");
        }
    }

    void printAppointments(){
        for (Appointment appointment:patientAppointments
             ) {
            System.out.println(appointment);
        }
    }

    void deleteAppointment(int id){
        for (Appointment appointment:patientAppointments
             ) {
            if (appointment.getId() == id){
                appointment.getClinic().getClinicAppointments().remove(appointment);
                appointment.getDoctor().getDoctorAppointments().remove(appointment);
                patientAppointments.remove(appointment);
                return;
            }
        }
        System.out.println("No such appointmnet!");
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {

        String toStringPrint = "Patient[id = " + id + ", name = " + name + ", disease = ";
        printDiseases();
        return toStringPrint + ", phone number = " + phoneNumber + "]";
    }
}
