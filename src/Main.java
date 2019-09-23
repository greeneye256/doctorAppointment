import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	    MedicalServices medStar = new MedicalServices();
	    Clinic small_clinic = new Clinic("Clinic 1",6);
	    Clinic big_clinic = new Clinic("Clinic 2",10);
	    medStar.getClinics().add(small_clinic);
	    medStar.getClinics().add(big_clinic);
        Scanner scanner = new Scanner(System.in);
        boolean stayInLoop = true;
        while (stayInLoop){
            printChoicesForMedicalServices();
            switch (readStringChoice(scanner,"[a-gA-GqQ]","Enter your choice: ","Wrong input!")){
                case "a":
                case "A":
                    System.out.print("Enter doctor name: ");
                    String doctorName = scanner.nextLine();
                    System.out.print("Enter speciality name: ");
                    String speciality = scanner.nextLine();
                    String email = readStringChoice(scanner,"^([\\w-.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$","Enter doctor email: ","The email is not valid!");
                    String doctorPhoneNumber = readStringChoice(scanner, "\\d+","Enter doctor phone number: ", "Doctor phone number have to contain only digits.");
                    medStar.getDoctors().add(new Doctor(doctorName,speciality,email,doctorPhoneNumber));
                    break;

                case "b":
                case "B":
                    System.out.print("Enter patient name: ");
                    String patientName = scanner.nextLine();
                    String patientPhoneNumber = readStringChoice(scanner, "\\d+","Enter patient phone number: ", "Patient phone number have to contain only digits.");
                    medStar.getPatients().add(new Patient(patientName,patientPhoneNumber));
                    break;

                case "c":
                case "C":
                    //Delete doctor and also all appointments in clinic and all patients appointments with this doctor
                    medStar.printDoctors();
                    int idDoctorToDelete = chooseId(scanner,"Choose doctor by id to delete it(it will delete all patients appointments with this doctor): ", "Wrong input!");
                    for (Doctor doctor:medStar.getDoctors()
                         ) {
                        if (doctor.getId() == idDoctorToDelete){
                            for (Appointment appointment:doctor.getDoctorAppointments()
                                 ) {
                                doctor.deleteAppointment(appointment.getId());
                            }
//                            for (Clinic clinic:medStar.getClinics()
//                                 ) {
//                                clinic.getClinicAppointments().removeAll(doctor.getDoctorAppointments());
//                            }
//                            for (Patient patient:medStar.getPatients()
//                                 ) {
//                                patient.getPatientAppointments().removeAll(doctor.getDoctorAppointments());
//                            }
                            medStar.getDoctors().remove(doctor);
                            break;
                        }
                    }
                    System.out.println("There is no doctor with this id.");
                    break;
                //Delete a patient by id and all appointments related with this patient
                case "d":
                case "D":
                    medStar.printPatients();
                    int idPatientToDelete = chooseId(scanner,"Choose patient by id to delete it (will delete all appointments related to this patient): ", "Wrong input!");
                    for (Patient patient:medStar.getPatients()
                    ) {
                        if (patient.getId() == idPatientToDelete){
                            for (Appointment appointment:patient.getPatientAppointments()
                                 ) {
                                patient.deleteAppointment(appointment.getId());
                            }
                            medStar.getPatients().remove(patient);
                            return;
                        }
                    }
                    System.out.println("There is no patient with this id.");
                    break;
                case "e":
                case "E":
                    for (Clinic clinic:medStar.getClinics()
                         ) {
                        System.out.println(clinic);
                    }
                    int idClinicToManage = chooseId(scanner,"Choose id for the clinic you want to manage: ", "Wrong input!");
                    Clinic clinicToManage = null;
                    for (Clinic clinic:medStar.getClinics()
                         ) {
                        if (clinic.getId() == idClinicToManage){
                            clinicToManage = clinic;
                            break;
                        }
                    }
                    if (clinicToManage == null){
                        System.out.println("You didn't select any clinic!");
                        break;
                    }
                    boolean stayInClinicManagement = true;
                    while (stayInClinicManagement){

                        System.out.println(clinicToManage);
                        System.out.println();
                        System.out.println("a) Change capacity");
                        if (clinicToManage.hasCoffee()){
                            System.out.println("b) Remove coffee machine");
                        }
                        else {
                            System.out.println("Add a coffee machine");
                        }
                        System.out.println("q) Exit management");
                        String clinicChoice = readStringChoice(scanner,"[aAbBqQ]","Make a choice for the clinic: ","Wrong input!");
                        switch (clinicChoice){
                            case "a":
                            case "A":
                                int newClinicCapacity = Integer.parseInt(readStringChoice(scanner,"^[1-9]\\d*$","Input clinic new capacity (must be between 6 and 200): ", "Wrong input!"));
                                if (newClinicCapacity<6 || newClinicCapacity>200){
                                    System.out.println("The capacity must be between 6 and 200.");
                                    break;
                                }
                                clinicToManage.setCapacity(newClinicCapacity);
                                break;
                            case "b":
                            case "B":
                                if (clinicToManage.hasCoffee()){
                                    clinicToManage.setHasCoffe(false);
                                }
                                else {
                                    clinicToManage.setHasCoffe(true);
                                }
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
                    medStar.printPatients();
                    Patient patientToManage = null;
                    int idPatientToManage = chooseId(scanner,"Choose patient by id to manage it: ", "Wrong input!");
                    for (Patient patient:medStar.getPatients()
                    ) {
                        if (patient.getId() == idPatientToManage){
                            patientToManage = patient;
                            break;
                        }
                    }
                    if (patientToManage == null){
                        System.out.println("No patient for this id!");
                        break;
                    }
                    boolean stayInPatientManagement = true;
                    while (stayInPatientManagement){
                        System.out.println(patientToManage);
                        printChoicesForPatientManagement();
                        String choiceForPatientManagement = readStringChoice(scanner,"[aAbBcCdDeEfFgGqQ]","Select action for patient: ", "Wrong input.Must be a letter from choices above,");
                        switch (choiceForPatientManagement){
                            case "a":
                            case "A":
                                System.out.print("Enter a disease: ");
                                patientToManage.addDisease(scanner.nextLine());
                                break;
                            case "b":
                            case "B":
                                patientToManage.printDiseases();
                                System.out.print("Type the disease you want to delete from patient medical record: ");
                                String diseaseToDelete = scanner.nextLine();
                                patientToManage.removeDisease(diseaseToDelete);
                                break;
                            case "c":
                            case "C":
                                System.out.println("Enter date of your appointment(yyyy-mm-dd). Must be within tomorrow and next 6 months: ");
                                String date = scanner.nextLine();
                                if (!isDateValid(date)){
                                    System.out.println("Wrong date!");
                                    break;
                                }
                                medStar.printDoctors();
                                Doctor doctorForAppointment = null;
                                int idOfDoctor = chooseId(scanner,"Choose the doctor by id for this appointment", "Wrong id.");
                                for (Doctor doctor:medStar.getDoctors()
                                     ) {
                                    if (doctor.getId() == idOfDoctor){
                                        doctorForAppointment = doctor;
                                        break;
                                    }
                                }
                                if (doctorForAppointment == null){
                                    System.out.println("No doctor with this id!");
                                    break;
                                }
                                doctorForAppointment.getAppointmentsOnDate(LocalDate.parse(date));
                                if (!doctorForAppointment.isAvailableOnDate(LocalDate.parse(date))){
                                    System.out.println("Doctor is busy on this date.");
                                    break;
                                }




                        }
                    }

                case "q":
                case "Q":
                    System.out.println("Have a nice day!");
                    stayInLoop = false;
                    break;
            }
        }


    }

    private static void printChoicesForMedicalServices(){
        System.out.println();
        System.out.println("a) Add doctor");
        System.out.println("b) Add patient");
        System.out.println("c) Delete doctor");
        System.out.println("d) Delete patient");
        System.out.println("e) Manage clinic");
        System.out.println("f) Manage patient");
        System.out.println("g) Manage doctor");
        System.out.println("h) Print doctors") ;
        System.out.println("i) Print patients") ;
        System.out.println("q) Exit program") ;
    }
    private static void printChoicesForPatientManagement(){
        System.out.println();
        System.out.println("a) Add disease: ");
        System.out.println("b) Delete disease: ");
        System.out.println("c) Add appointment: ");
        System.out.println("d) Delete appointment: ");
        System.out.println("e) Print appointments: ");
    }
    private static String readStringChoice(Scanner scanner, String regex,String choiceMessage, String errorMessage){
        System.out.print(choiceMessage);
        String result = scanner.nextLine();
        while (!result.matches(regex)){

            System.out.print(errorMessage + " " + choiceMessage);
            result = scanner.nextLine();

        }
        return result;
    }
    private static int chooseId(Scanner scanner,String choiceMessage, String errorMessage){
        System.out.print(choiceMessage);
        while (!scanner.hasNextInt()){

            System.out.print(errorMessage + " " + choiceMessage);
            scanner.next();

        }
        int id = scanner.nextInt();
        scanner.nextLine();
        return id;
    }
    static boolean isDateValid(String date) {
        try {
            LocalDate date1 = LocalDate.parse(date);
            if (date1.isAfter(LocalDate.now()) && date1.isBefore(LocalDate.now().plusMonths(6))) {
                return true;
            } else return false;
        } catch (Exception e) {
            return false;
        }
    }
}
