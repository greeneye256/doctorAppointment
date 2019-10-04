import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Patient {

    private int id;
    private static int patientCount = 0;
    private String name;
    private List<String> diseases = new ArrayList<>();
    private String phoneNumber;
    private List<Appointment> patientAppointments = new ArrayList<>();

    Patient(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.id = ++patientCount;
    }

    int getId() {
        return id;
    }

    void addDisease(String disease) {
        if (diseases.contains(disease)) {
            System.out.println("The patient is already known with having " + disease + ".");
        } else {
            diseases.add(disease);
        }
    }

    void removeDisease(String disease) {

        if (diseases.contains(disease)){
            diseases.remove(disease);
        }else {
            System.out.println("The patient does not have " + disease + ".");
        }

        diseases.remove(disease);
    }

    List<Appointment> getPatientAppointments() {
        return patientAppointments;
    }

    String getPhoneNumber() {
        return phoneNumber;
    }

    void deleteAppointment(Appointment appointment){
        this.patientAppointments.remove(appointment);
    }

    String getName() {
        return name;
    }

    boolean isFree(LocalDateTime localDateTime){
        for (Appointment appointment:patientAppointments
             ) {
            if (localDateTime.toLocalTime().isAfter(appointment.getLocalDateTime().toLocalTime().minusMinutes(60))&&localDateTime.toLocalTime().isBefore(appointment.getLocalDateTime().toLocalTime().plusMinutes(60))){
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
            System.out.println(disease);
        }
    }

    void printAppointments(){
        if (this.patientAppointments.size() == 0){
            System.out.println("No appointments for this patient yet!");
            return;
        }
        for (Appointment appointment:patientAppointments
             ) {
            System.out.println(appointment);
        }
    }

    void addAppointment(Appointment appointment){
        this.patientAppointments.add(appointment);
    }

    @Override
    public String toString() {

        StringBuilder diseases = new StringBuilder();
        int size = this.diseases.size();
        int count = 1;
        if (this.diseases.size() == 0){
            diseases.append("no disease");
        }else {
            for (String disease:this.diseases
                 ) {
                if (size == count){
                    diseases.append(disease);
                }
                else if (size > count){
                    diseases.append(disease + ", ");
                }
                count++;

            }
        }
        return "Patient[id = " + id + ", name = " + name + ", disease = [" + diseases.toString() + "], phone number = " + phoneNumber + "]";
    }
}
