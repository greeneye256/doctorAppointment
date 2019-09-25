import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        MedicalServices medStar = new MedicalServices();

        Clinic small_clinic = new Clinic("Clinic 1", 6);
        Clinic big_clinic = new Clinic("Clinic 2", 10);

        medStar.getClinics().add(small_clinic);
        medStar.getClinics().add(big_clinic);

        addDoctorsToClinic(medStar);

        addPatientsToClinic(medStar);

        addAppointment(medStar.getPatients().get(0),medStar.getDoctors().get(0),small_clinic,LocalDateTime.of(2019,12,12,15,0));
        addAppointment(medStar.getPatients().get(1),medStar.getDoctors().get(0),small_clinic,LocalDateTime.of(2019,10,22,18,0));
        addAppointment(medStar.getPatients().get(3),medStar.getDoctors().get(5),big_clinic,LocalDateTime.of(2019,9,30,7,0));
        addAppointment(medStar.getPatients().get(2),medStar.getDoctors().get(3),small_clinic,LocalDateTime.of(2019,11,12,15,0));
        addAppointment(medStar.getPatients().get(4),medStar.getDoctors().get(2),big_clinic,LocalDateTime.of(2020,1,15,9,0));
        addAppointment(medStar.getPatients().get(5),medStar.getDoctors().get(4),big_clinic,LocalDateTime.of(2020,2,17,19,0));
        addAppointment(medStar.getPatients().get(6),medStar.getDoctors().get(1),small_clinic,LocalDateTime.of(2020,1,30,12,0));
        addAppointment(medStar.getPatients().get(7),medStar.getDoctors().get(2),big_clinic,LocalDateTime.of(2019,11,13,13,0));

        Scanner scanner = new Scanner(System.in);

        boolean stayInLoop = true;
        while (stayInLoop) {
            printChoicesForMedicalServices();
            switch (readStringChoice(scanner, "[a-iA-IqQ]", "Enter your choice: ", "Wrong input!")) {
                case "a":
                case "A":
                    System.out.print("Enter doctor name: ");
                    String doctorName = scanner.nextLine();
                    System.out.print("Enter speciality name: ");
                    String speciality = scanner.nextLine();
                    String email = readStringChoice(scanner, "^([\\w-.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$", "Enter doctor email: ", "The email is not valid!");
                    String doctorPhoneNumber = readStringChoice(scanner, "\\d+", "Enter doctor phone number: ", "Doctor phone number have to contain only digits.");
                    medStar.getDoctors().add(new Doctor(doctorName, speciality, email, doctorPhoneNumber));
                    break;

                case "b":
                case "B":
                    System.out.print("Enter patient name: ");
                    String patientName = scanner.nextLine();
                    String patientPhoneNumber = readStringChoice(scanner, "\\d+", "Enter patient phone number: ", "Patient phone number have to contain only digits.");
                    medStar.getPatients().add(new Patient(patientName, patientPhoneNumber));
                    break;

                case "c":
                case "C":
                    //Delete doctor and also all appointments in clinic and all patients appointments with this doctor
                    medStar.printDoctors();
                    int idDoctorToDelete = chooseId(scanner, "Choose doctor by id to delete it(it will delete all patients appointments with this doctor): ", "Wrong input!");
                    boolean isDoctorDeleted = false;
                    for (Doctor doctor : medStar.getDoctors()
                    ) {
                        if (doctor.getId() == idDoctorToDelete) {
                            for (Appointment appointment : doctor.getDoctorAppointments()
                            ) {
                                doctor.deleteAppointmentFromClinicAndPatient(appointment);
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
                            isDoctorDeleted = true;
                            break;
                        }
                    }
                    if (!isDoctorDeleted) {
                        System.out.println("There is no doctor with this id.");
                    }
                    break;
                //Delete a patient by id and all appointments related with this patient
                case "d":
                case "D":
                    medStar.printPatients();
                    int idPatientToDelete = chooseId(scanner, "Choose patient by id to delete it (will delete all appointments related to this patient): ", "Wrong input!");
                    for (Patient patient : medStar.getPatients()
                    ) {
                        if (patient.getId() == idPatientToDelete) {
                            for (Appointment appointment : patient.getPatientAppointments()
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
                    for (Clinic clinic : medStar.getClinics()
                    ) {
                        System.out.println(clinic);
                    }
                    int idClinicToManage = chooseId(scanner, "Choose id for the clinic you want to manage: ", "Wrong input!");
                    Clinic clinicToManage = null;
                    for (Clinic clinic : medStar.getClinics()
                    ) {
                        if (clinic.getId() == idClinicToManage) {
                            clinicToManage = clinic;
                            break;
                        }
                    }
                    if (clinicToManage == null) {
                        System.out.println("You didn't select any clinic!");
                        break;
                    }
                    boolean stayInClinicManagement = true;
                    while (stayInClinicManagement) {

                        System.out.println(clinicToManage);
                        System.out.println();
                        System.out.println("a) Change capacity");
                        if (clinicToManage.hasCoffee()) {
                            System.out.println("b) Remove coffee machine");
                        } else {
                            System.out.println("b) Add a coffee machine");
                        }
                        System.out.println("c) View appointments");
                        System.out.println("q) Exit clinic management");
                        String clinicChoice = readStringChoice(scanner, "[aAbBcCqQ]", "Make a choice for the clinic: ", "Wrong input!");
                        switch (clinicChoice) {
                            case "a":
                            case "A":
                                int newClinicCapacity = Integer.parseInt(readStringChoice(scanner, "^[1-9]\\d*$", "Input clinic new capacity (must be between 6 and 200): ", "Wrong input!"));
                                if (newClinicCapacity < 6 || newClinicCapacity > 200) {
                                    System.out.println("The capacity must be between 6 and 200.");
                                    break;
                                }
                                clinicToManage.setCapacity(newClinicCapacity);
                                break;
                            case "b":
                            case "B":
                                if (clinicToManage.hasCoffee()) {
                                    clinicToManage.setHasCoffe(false);
                                } else {
                                    clinicToManage.setHasCoffe(true);
                                }
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
                    medStar.printPatients();
                    Patient patientToManage = null;
                    int idPatientToManage = chooseId(scanner, "Choose patient by id to manage it: ", "Wrong input!");
                    for (Patient patient : medStar.getPatients()
                    ) {
                        if (patient.getId() == idPatientToManage) {
                            patientToManage = patient;
                            break;
                        }
                    }
                    if (patientToManage == null) {
                        System.out.println("No patient for this id!");
                        break;
                    }
                    boolean stayInPatientManagement = true;
                    while (stayInPatientManagement) {
                        System.out.println(patientToManage);
                        printChoicesForPatientManagement();
                        String choiceForPatientManagement = readStringChoice(scanner, "[aAbBcCdDeEfFgGqQ]", "Select action for patient: ", "Wrong input.Must be a letter from choices above,");
                        switch (choiceForPatientManagement) {
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
                                medStar.printDoctors();
                                Doctor doctorForAppointment = null;
                                int idOfDoctor = chooseId(scanner, "Choose the doctor by id for this appointment: ", "Wrong id.");
                                for (Doctor doctor : medStar.getDoctors()
                                ) {
                                    if (doctor.getId() == idOfDoctor) {
                                        doctorForAppointment = doctor;
                                        break;
                                    }
                                }
                                if (doctorForAppointment == null) {
                                    System.out.println("No doctor with this id!");
                                    break;
                                }
                                System.out.print("Enter date of your appointment(yyyy-mm-dd). Must be between tomorrow and next 6 months: ");
                                String posibleDate = scanner.nextLine();
                                if (!isDateValid(posibleDate)) {
                                    break;
                                }
                                LocalDate date = LocalDate.parse(posibleDate);
                                if (!doctorForAppointment.isAvailableOnDate(date)) {
                                    System.out.println("The doctor is busy on " + date + ".");
                                    break;
                                }
                                List<Clinic> clinicsAvailableOnDate = new ArrayList<>();
                                for (Clinic clinic:medStar.getClinics()
                                     ) {
                                    if (doctorForAppointment.getAppointmentsOnDate(date).size() == 2){
                                        if (clinic.hasCoffee()){
                                            clinicsAvailableOnDate.add(clinic);
                                        }
                                    }
                                    else {
                                        if (clinic.getAppointmentsOnDate(date).size()<clinic.getCapacity()){
                                            clinicsAvailableOnDate.add(clinic);
                                        }
                                    }

                                }

                                if (clinicsAvailableOnDate.size() == 0){
                                    System.out.println("Doctor is available but we don't have space in our clinics for your appointment on " + date + "!");
                                    break;
                                }

                                if (doctorForAppointment.getAppointmentsOnDate(date).size() == 0) {
                                    System.out.println("Doctor is available between 7AM to 8PM.");
                                } else {
                                    List<LocalTime> availableHours = new ArrayList<>();
                                    availableHours.add(LocalTime.parse("06:00"));
                                    availableHours.add(LocalTime.parse("21:00"));

                                    for (Appointment appointment : doctorForAppointment.getAppointmentsOnDate(date)
                                    ) {
                                        availableHours.add(appointment.getLocalDateTime().toLocalTime());
                                    }
                                    Collections.sort(availableHours);
                                    System.out.println("Doctor free hours on " + date + ":");
                                    for (int i = 0; i < availableHours.size(); i++) {
                                        if (i <= availableHours.size() - 2) {
                                            if (!(availableHours.get(i).plusHours(1).isAfter(availableHours.get(i + 1).minusHours(1)))) {
                                                if (availableHours.get(i).plusHours(1).equals(availableHours.get(i + 1).minusHours(1))) {
                                                    System.out.println(availableHours.get(i).plusHours(1));
                                                } else {
                                                    System.out.println(availableHours.get(i).plusHours(1) + " - " + availableHours.get(i + 1).minusHours(1));
                                                }
                                            }
                                        }
                                    }
                                }
                                System.out.print("Enter the time for your appointment in accordance with doctor schedule. (hh:mm): ");
                                String time = scanner.nextLine() + ":00";
                                if(!isTimeValid(time)){
                                break;
                                }

                                boolean isOccupied = false;

                                if (!patientToManage.appointmentCanBeDone(LocalDateTime.of(date,LocalTime.parse(time)))){
                                    System.out.println("You already have an appointment on this interval!");
                                    break;
                                }
                                for (Appointment appointment:doctorForAppointment.getAppointmentsOnDate(date)
                                     ) {
                                    if (((LocalTime.parse(time).isAfter(appointment.getLocalDateTime().toLocalTime().minusHours(1)))) && ((LocalTime.parse(time).isBefore(appointment.getLocalDateTime().toLocalTime().plusHours(1))))){
                                        isOccupied = true;
                                        break;
                                    }
                                }
                                if (isOccupied){
                                    System.out.println("Can't do appointment at this time. Doctor is occupied!");
                                    break;
                                }
                                for (Clinic clinic:clinicsAvailableOnDate
                                     ) {
                                    if (clinic.getAppointmentsOnDate(date) == null){
                                        System.out.println(clinic + " - no appointments in this clinic yet");
                                    }
                                    else {
                                        System.out.println(clinic + " - " + clinic.getAppointmentsOnDate(date).size() + " appointments on " + date + ".");
                                    }

                                }

                                int idOfClinic = chooseId(scanner,"Select clinic by id where you want to make the appointment: ","Wrong input!");
                                Clinic appointmentClinic = null;
                                for (Clinic clinic:clinicsAvailableOnDate
                                     ) {
                                    if (clinic.getId() == idOfClinic){
                                        appointmentClinic = clinic;
                                    }
                                }
                                if (appointmentClinic == null){
                                    System.out.println("No such id for clinic!");
                                    break;
                                }

                                Appointment newAppointment = new Appointment(patientToManage,doctorForAppointment,appointmentClinic, LocalDateTime.of(date,LocalTime.parse(time)));
                                appointmentClinic.getClinicAppointments().add(newAppointment);
                                patientToManage.getPatientAppointments().add(newAppointment);
                                doctorForAppointment.getDoctorAppointments().add(newAppointment);
                                break;

                            case "d":
                            case "D":
                                patientToManage.printAppointments();
                                int idOfAppointmentToDelete = chooseId(scanner, "Select the id of appointment you want to delete: ", "Wrong input!");
                                for (Appointment appointment:patientToManage.getPatientAppointments()
                                     ) {
                                    if (appointment.getId() == idOfAppointmentToDelete){
                                        appointment.getPatient().deleteAppointmentFromClinicAndDoctor(appointment);
                                        break;
                                    }
                                }
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

    private static void printChoicesForMedicalServices() {
        System.out.println();
        System.out.println("a) Add doctor");
        System.out.println("b) Add patient");
        System.out.println("c) Delete doctor");
        System.out.println("d) Delete patient");
        System.out.println("e) Manage clinic");
        System.out.println("f) Manage patient");
        System.out.println("g) Manage doctor");
        System.out.println("h) Print doctors");
        System.out.println("i) Print patients");
        System.out.println("q) Exit program");
        System.out.println();
    }

    private static void printChoicesForPatientManagement() {
        System.out.println();
        System.out.println("a) Add disease");
        System.out.println("b) Delete disease");
        System.out.println("c) Add appointment");
        System.out.println("d) Delete appointment");
        System.out.println("e) Print appointments");
        System.out.println("q) Exit patient management");
        System.out.println();
    }

    private static String readStringChoice(Scanner scanner, String regex, String choiceMessage, String errorMessage) {
        System.out.print(choiceMessage);
        String result = scanner.nextLine();
        while (!result.matches(regex)) {

            System.out.print(errorMessage + " " + choiceMessage);
            result = scanner.nextLine();

        }
        return result;
    }

    private static int chooseId(Scanner scanner, String choiceMessage, String errorMessage) {
        System.out.print(choiceMessage);
        while (!scanner.hasNextInt()) {

            System.out.print(errorMessage + " " + choiceMessage);
            scanner.next();

        }
        int id = scanner.nextInt();
        scanner.nextLine();
        return id;
    }

    private static boolean isDateValid(String date) {
        try {
            LocalDate date1 = LocalDate.parse(date);
            if (date1.isAfter(LocalDate.now()) && date1.isBefore(LocalDate.now().plusMonths(6))) {
                return true;
            } else {
                System.out.println("Must be within 6 months!");
            }return false;
        } catch (Exception e) {
            System.out.println("Wrong input!");
            return false;
        }
    }

    private static boolean isTimeValid(String time) {
        try {
            LocalTime appointmentHour = LocalTime.parse(time);
            if ((appointmentHour.isBefore(LocalTime.of(7,0)))||appointmentHour.isAfter(LocalTime.of(20,1))){
                System.out.println("Must be between 7AM and 20PM!");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("Wrong input!");
            return false;
        }
    }

    private static void addDoctorsToClinic(MedicalServices medicalServices){
        medicalServices.addDoctor(new Doctor("Mihai Lucan", "urologie", "mihailucan@gmail.com", "0745344347"));
        medicalServices.addDoctor(new Doctor("Nadim Al Hajjar", "chirurgie generala", "nadimalhajjar@gmail.com", "0735889977"));
        medicalServices.addDoctor(new Doctor("Sorin Andreica", "neonatologie", "sorin_andreica@gmail.com", "0713453423"));
        medicalServices.addDoctor(new Doctor("Natalia Albu", "neurologie pediatrica", "natalia_albu@gmail.com", "0722456567"));
        medicalServices.addDoctor(new Doctor("Nicoleta Velea", "stomatologie generala", "nicoletavelea@gmail.com", "0765883423"));
        medicalServices.addDoctor(new Doctor("Cosmina Tomescu", "hematologie", "cristinatomescu@gmail.com", "0756357468"));
    }

    private static void addPatientsToClinic(MedicalServices medicalServices){
        medicalServices.addPatient(new Patient("Anca Vasiliu", "0754345311"));
        medicalServices.addPatient(new Patient("Septimiu Avram", "0722434354"));
        medicalServices.addPatient(new Patient("George Apostol", "0766467259"));
        medicalServices.addPatient(new Patient("Radu Paraschivescu", "0799125478"));
        medicalServices.addPatient(new Patient("Iulia Precup", "0720930201"));
        medicalServices.addPatient(new Patient("Aurel Tamas", "0745667887"));
        medicalServices.addPatient(new Patient("Laurentiu Naum", "0733888743"));
        medicalServices.addPatient(new Patient("Marin Sorlea", "0776999567"));
        medicalServices.addPatient(new Patient("Florin Salam", "0786775663"));
    }

    private static void addAppointment(Patient patient, Doctor doctor, Clinic clinic, LocalDateTime localDateTime){
        Appointment newAppointment = new Appointment(patient,doctor,clinic,localDateTime);
        clinic.getClinicAppointments().add(newAppointment);
        patient.getPatientAppointments().add(newAppointment);
        doctor.getDoctorAppointments().add(newAppointment);
    }
}
