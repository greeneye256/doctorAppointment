import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        MedicalServices medStar = new MedicalServices();

        Clinic small_clinic = new Clinic("Clinic 1", 6);
        Clinic big_clinic = new Clinic("Clinic 2", 10);

        medStar.addClinic(small_clinic);
        medStar.addClinic(big_clinic);

        Utils.addDoctorsToMedicalService(medStar);

        Utils.addPatientsToMedicalService(medStar);

        {
            Utils.addAppointment(medStar.getPatients().get(0), medStar.getDoctors().get(0), small_clinic, LocalDateTime.of(2019, 12, 12, 15, 0));
            Utils.addAppointment(medStar.getPatients().get(1), medStar.getDoctors().get(0), small_clinic, LocalDateTime.of(2019, 10, 22, 18, 0));
            Utils.addAppointment(medStar.getPatients().get(3), medStar.getDoctors().get(5), big_clinic, LocalDateTime.of(2019, 9, 30, 7, 0));
            Utils.addAppointment(medStar.getPatients().get(2), medStar.getDoctors().get(3), small_clinic, LocalDateTime.of(2019, 11, 12, 15, 0));
            Utils.addAppointment(medStar.getPatients().get(4), medStar.getDoctors().get(2), big_clinic, LocalDateTime.of(2020, 1, 15, 9, 0));
            Utils.addAppointment(medStar.getPatients().get(5), medStar.getDoctors().get(4), big_clinic, LocalDateTime.of(2020, 2, 17, 19, 0));
            Utils.addAppointment(medStar.getPatients().get(6), medStar.getDoctors().get(1), small_clinic, LocalDateTime.of(2020, 1, 30, 12, 0));
            Utils.addAppointment(medStar.getPatients().get(7), medStar.getDoctors().get(2), big_clinic, LocalDateTime.of(2019, 11, 13, 13, 0));
        }

        Scanner scanner = new Scanner(System.in);

        boolean stayInLoop = true;
        while (stayInLoop) {
            Utils.printChoicesForMedicalServices();
            switch (Utils.readStringChoice(scanner, "[a-jA-JqQ]", "Enter your choice: ", "Wrong input!")) {
                case "a":
                case "A":
                    medStar.addDoctor(Utils.createDoctorWithScanner(scanner));
                    break;

                case "b":
                case "B":
                    medStar.addPatient(Utils.createPatientWithScanner(scanner));
                    break;

                case "c":
                case "C":
                    //Delete doctor and also all appointments in clinic and all patients appointments with this doctor
                    Doctor doctorToDelete = Utils.selectDoctor(medStar, "delete", scanner);
                    if (doctorToDelete == null) {
                        break;
                    }
                    medStar.deleteDoctor(doctorToDelete);
                    break;
                case "d":
                case "D":
                    Patient patientToDelete = Utils.selectPatient(medStar, "delete", scanner);
                    if (patientToDelete == null) {
                        break;
                    }
                    medStar.deletePatient(patientToDelete);
                    break;

                case "e":
                case "E":

                    Clinic clinicToManage = Utils.selectClinic(medStar.getClinics(), "manage", scanner);
                    if (clinicToManage == null) {
                        break;
                    }

                    boolean stayInClinicManagement = true;
                    while (stayInClinicManagement) {

                        Utils.printChoicesForClinicManagement(clinicToManage);
                        switch (Utils.readStringChoice(scanner, "[aAbBcCqQ]", "Make a choice for the clinic: ", "Wrong input!")) {
                            case "a":
                            case "A":
                                clinicToManage.setCapacity(Integer.parseInt(Utils.readStringChoice(scanner, "^[1-9]\\d*$", "Input clinic new capacity (must be between 6 and 200): ", "Wrong input!")));
                                break;

                            case "b":
                            case "B":
                                clinicToManage.setHasCoffe(!clinicToManage.hasCoffee());
                                break;

                            case "c":
                            case "C":
                                clinicToManage.printAppointments();
                                break;
                            case "q":
                            case "Q":
                                stayInClinicManagement = false;
                                break;
                        }

                    }
                    break;
                case "f":
                case "F":

                    Patient patientToManage = Utils.selectPatient(medStar, "manage", scanner);
                    if (patientToManage == null) {
                        break;
                    }
                    boolean stayInPatientManagement = true;
                    while (stayInPatientManagement) {

                        Utils.printChoicesForPatientManagement(patientToManage);
                        switch (Utils.readStringChoice(scanner, "[aAbBcCdDeEfFgGqQ]", "Select action for patient: ", "Wrong input.Must be a letter from choices above,")) {
                            case "a":
                            case "A":
                                patientToManage.addDisease(Utils.createDisease(scanner));
                                break;
                            case "b":
                            case "B":
                                patientToManage.removeDisease(Utils.diseaseToDelete(patientToManage, scanner));
                                break;
                            case "c":
                            case "C":

                                Doctor doctorForAppointment = Utils.selectDoctor(medStar, "add to appointment", scanner);
                                if (doctorForAppointment == null) {
                                    break;
                                }

                                LocalDate dateForAppointment = Utils.dateForAppointment(doctorForAppointment, scanner);
                                if (dateForAppointment == null) {
                                    break;
                                }

                                if (Utils.clinicsAvailableOnDate(medStar, doctorForAppointment, dateForAppointment).size() == 0){
                                    System.out.println("There is no room in our clinics on this date!");
                                    break;
                                }

                                LocalTime timeForAppointment = Utils.timeForAppointment(doctorForAppointment, dateForAppointment, scanner);

                                if (timeForAppointment == null) {
                                    break;
                                }

                                LocalDateTime localDateTimeForAppointment = LocalDateTime.of(dateForAppointment, timeForAppointment);

                                if (!patientToManage.isFree(localDateTimeForAppointment)) {
                                    System.out.println("You already have an appointment on this interval!");
                                    break;
                                }

                                if (!doctorForAppointment.isFreeOnThisHour(localDateTimeForAppointment)) {
                                    System.out.println("Can't do appointment at this time. Doctor is occupied!");
                                    break;
                                }

                                Clinic clinicForAppointment = Utils.selectClinic(Utils.clinicsAvailableOnDate(medStar, doctorForAppointment, dateForAppointment), "make an appointment for", scanner);

                                if (clinicForAppointment == null) {
                                    break;
                                }

                                Appointment newAppointment = Utils.createAppointment(patientToManage,doctorForAppointment,clinicForAppointment,localDateTimeForAppointment);
                                medStar.addAppointmentToDoctorPatientClinic(newAppointment);

                                break;

                            case "d":
                            case "D":
                                patientToManage.printAppointments();
                                Appointment appointmentToDelete = Utils.selectAppointment(patientToManage.getPatientAppointments(), Utils.chooseId(scanner, "Select the id of appointment you want to delete: ", "Wrong input!"));
                                if (appointmentToDelete == null){
                                    break;
                                }
                                Utils.deleteAppointment(appointmentToDelete);
                                break;
                            case "e":
                            case "E":
                                patientToManage.printAppointments();
                                break;
                            case "q":
                            case "Q":
                                stayInPatientManagement = false;
                                break;
                        }
                    }
                    break;
                case "g":
                case "G":

                    Doctor doctorToManage = Utils.selectDoctor(medStar, "manage", scanner);
                    if (doctorToManage == null) {
                        break;
                    }
                    boolean stayInDoctorManagement = true;
                    while (stayInDoctorManagement) {

                        Utils.printChoicesForDoctorManagement();
                        switch (Utils.readStringChoice(scanner, "[aAbBcqQ]", "Select action for doctor: ", "Wrong input.Must be a letter from choices above,")) {
                            case "a":
                            case "A":
                                doctorToManage.printAppointments();
                                break;
                            case "b":
                            case "B":
                                doctorToManage.printAppointments();
                                Appointment appointmentToDelete = Utils.selectAppointment(doctorToManage.getDoctorAppointments(), Utils.chooseId(scanner, "Select the id of appointment you want to delete: ", "Wrong input!"));
                                if (appointmentToDelete == null){
                                    break;
                                }
                                Utils.deleteAppointment(appointmentToDelete);
                                break;
                            case "q":
                            case "Q":
                                stayInDoctorManagement = false;
                                break;
                        }
                    }
                case "h":
                case "H":
                    medStar.printDoctors();
                    break;
                case "i":
                case "I":
                    medStar.printPatients();
                    break;
                case "j":
                case "J":
                    medStar.printClinics();
                    break;

                case "q":
                case "Q":
                    System.out.println("Have a nice day!");
                    stayInLoop = false;
                    break;
            }
        }
    }
}
