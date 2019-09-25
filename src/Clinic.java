import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Clinic {

    private int id;
    private static int countClinic = 0;
    private String name;
    private boolean hasCoffee = false;
    private int capacity;
    private List<Appointment> clinicAppointments = new ArrayList<>();

    public Clinic(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.id = ++countClinic;
    }

    String getName() {
        return name;
    }

    boolean hasCoffee() {
        return (this.hasCoffee);
    }

    void setHasCoffe(boolean hasCoffe) {
        this.hasCoffee = hasCoffe;
    }

    List<Appointment> getClinicAppointments() {
        return clinicAppointments;
    }

    int getCapacity() {
        return capacity;
    }

    void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    int getId() {
        return id;
    }

    void printAppointments(){
        for (Appointment appointment:this.clinicAppointments
             ) {
            System.out.println(appointment);
        }
    }

    List<Appointment> getAppointmentsOnDate(LocalDate date){

        List<Appointment> appointmentsOnDate = new ArrayList<>();
        for (Appointment appointment:clinicAppointments
             ) {
            if (appointment.getLocalDateTime().toLocalDate().equals(date)){
                appointmentsOnDate.add(appointment);
            }
        }
        return appointmentsOnDate;
    }

    @Override
    public String toString() {
        return "Clinic[id = " + id + ", name = " + name + ",capacity = " + capacity + ", has coffee = " + hasCoffee + "]";
    }
}
