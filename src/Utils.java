import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Utils {
    static void printChoicesForMedicalServices() {
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
        System.out.println("j) Print clinics");
        System.out.println("q) Exit program");
        System.out.println();
    }

    static void printChoicesForClinicManagement(Clinic clinic) {
        System.out.println(clinic);
        System.out.println();
        System.out.println("a) Change capacity");
        if (clinic.hasCoffee()) {
            System.out.println("b) Remove coffee machine");
        } else {
            System.out.println("b) Add a coffee machine");
        }
        System.out.println("c) View appointments");
        System.out.println("q) Exit clinic management");
    }

    static void printChoicesForPatientManagement(Patient patient) {
        System.out.println();
        System.out.println(patient);
        System.out.println();
        System.out.println("a) Add disease");
        System.out.println("b) Delete disease");
        System.out.println("c) Add appointment");
        System.out.println("d) Delete appointment");
        System.out.println("e) Print appointments");
        System.out.println("q) Exit patient management");
        System.out.println();
    }

    static void printChoicesForDoctorManagement() {
        System.out.println();
        System.out.println("a) Print appointments");
        System.out.println("b) Delete appointments");
        System.out.println("q) Quit doctor management");
        System.out.println();

    }

    static String readStringChoice(Scanner scanner, String regex, String choiceMessage, String errorMessage) {
        System.out.print(choiceMessage);
        String result = scanner.nextLine();
        while (!result.matches(regex)) {

            System.out.print(errorMessage + " " + choiceMessage);
            result = scanner.nextLine();

        }
        return result;
    }

    static int chooseId(Scanner scanner, String choiceMessage, String errorMessage) {
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
            }
            return false;
        } catch (Exception e) {
            System.out.println("Wrong input!");
            return false;
        }
    }

    private static boolean isTimeValid(String time) {
        try {
            LocalTime appointmentHour = LocalTime.parse(time);
            if ((appointmentHour.isBefore(LocalTime.of(7, 0))) || appointmentHour.isAfter(LocalTime.of(20, 1))) {
                System.out.println("Must be between 7AM and 20PM!");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("Wrong input!");
            return false;
        }
    }

    static void addDoctorsToMedicalService(MedicalServices medicalServices) {
        medicalServices.addDoctor(new Doctor("Mihai Lucan", "urologie", "mihailucan@gmail.com", "0745344347"));
        medicalServices.addDoctor(new Doctor("Nadim Al Hajjar", "chirurgie generala", "nadimalhajjar@gmail.com", "0735889977"));
        medicalServices.addDoctor(new Doctor("Sorin Andreica", "neonatologie", "sorin_andreica@gmail.com", "0713453423"));
        medicalServices.addDoctor(new Doctor("Natalia Albu", "neurologie pediatrica", "natalia_albu@gmail.com", "0722456567"));
        medicalServices.addDoctor(new Doctor("Nicoleta Velea", "stomatologie generala", "nicoletavelea@gmail.com", "0765883423"));
        medicalServices.addDoctor(new Doctor("Cosmina Tomescu", "hematologie", "cristinatomescu@gmail.com", "0756357468"));
    }

    static void addPatientsToMedicalService(MedicalServices medicalServices) {
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

    static void addAppointment(Patient patient, Doctor doctor, Clinic clinic, LocalDateTime localDateTime) {
        Appointment newAppointment = new Appointment(patient, doctor, clinic, localDateTime);
        clinic.getClinicAppointments().add(newAppointment);
        patient.getPatientAppointments().add(newAppointment);
        doctor.getDoctorAppointments().add(newAppointment);
    }

    static Doctor createDoctorWithScanner(Scanner scanner) {
        System.out.print("Enter doctor name: ");
        String doctorName = scanner.nextLine();
        System.out.print("Enter speciality name: ");
        String speciality = scanner.nextLine();
        String email = readStringChoice(scanner, "^([\\w-.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$", "Enter doctor email: ", "The email is not valid!");
        String doctorPhoneNumber = readStringChoice(scanner, "\\d+", "Enter doctor phone number: ", "Doctor phone number have to contain only digits.");
        return new Doctor(doctorName, speciality, email, doctorPhoneNumber);
    }

    static Patient createPatientWithScanner(Scanner scanner) {
        System.out.print("Enter patient name: ");
        String patientName = scanner.nextLine();
        String patientPhoneNumber = readStringChoice(scanner, "\\d+", "Enter patient phone number: ", "Patient phone number have to contain only digits.");
        return new Patient(patientName, patientPhoneNumber);
    }

    static Doctor selectDoctor(MedicalServices medicalServices, String purpose, Scanner scanner) {
        medicalServices.printDoctors();
        int idOfDoctorToSelect = chooseId(scanner, "Choose doctor by id to " + purpose + ": ", "Wrong input!");
        for (Doctor doctor : medicalServices.getDoctors()
        ) {
            if (doctor.getId() == idOfDoctorToSelect) {
                return doctor;
            }
        }
        System.out.println("No doctor with this id!");
        return null;
    }

    static Patient selectPatient(MedicalServices medicalServices, String purpose, Scanner scanner) {
        medicalServices.printPatients();
        int idOfPatientToSelect = chooseId(scanner, "Choose patient by id to " + purpose + " it: ", "Wrong input!");
        for (Patient patient : medicalServices.getPatients()
        ) {
            if (patient.getId() == idOfPatientToSelect) {
                return patient;
            }
        }
        System.out.println("No patient with this id!");
        return null;
    }

    static Clinic selectClinic(List<Clinic> clinics, String purpose, Scanner scanner) {
        if (clinics.size() == 0) {
            System.out.println("No clinics available!");
            return null;
        }
        for (Clinic clinic : clinics
        ) {
            System.out.println(clinic);
        }
        int idOfClinicToSelect = chooseId(scanner, "Choose a clinic by id to " + purpose + " it: ", "Wrong input!");
        for (Clinic clinic : clinics
        ) {
            if (clinic.getId() == idOfClinicToSelect) {
                return clinic;
            }
        }
        System.out.println("No clinic with this id!");
        return null;
    }

    static String createDisease(Scanner scanner) {
        System.out.print("Enter a disease!");
        return scanner.nextLine();
    }

    static String diseaseToDelete(Patient patient, Scanner scanner) {
        patient.printDiseases();
        System.out.print("Type the disease you want to delete from patient medical record: ");
        return scanner.nextLine();
    }

    static LocalDate dateForAppointment(Doctor doctor, Scanner scanner) {
        System.out.print("Enter date of your appointment(yyyy-mm-dd). Must be between tomorrow and next 6 months: ");
        String inputDate = scanner.nextLine();
        if (!isDateValid(inputDate)) {
            return null;
        }
        LocalDate date = LocalDate.parse(inputDate);
        if (!doctor.isAvailableOnDate(date)) {
            System.out.println("The doctor is busy on " + date + ".");
            return null;
        }
        return date;
    }

    static List<Clinic> clinicsAvailableOnDate(MedicalServices medicalServices, Doctor doctor, LocalDate localDate) {
        List<Clinic> cliniscAvailvableOnDate = new ArrayList<>();
        for (Clinic clinic : medicalServices.getClinics()
        ) {
            if (clinic.isAvailableOnDate(doctor, localDate)) {
                cliniscAvailvableOnDate.add(clinic);
            }
        }
        if (cliniscAvailvableOnDate.size() == 0) {
            System.out.println("Our clinics are full on " + localDate + ". Please try another day.");
        }

        return cliniscAvailvableOnDate;
    }

    private static void printDoctorAvailableHoursForAppointment(Doctor doctor, LocalDate date) {
        if (doctor.getAppointmentsOnDate(date).size() == 0) {
            System.out.println("Doctor is available between 7AM to 8PM.");
        } else {
            List<LocalTime> availableHours = new ArrayList<>();
            String startingHour = "06:00";
            availableHours.add(LocalTime.parse(startingHour));
            String endingHour = "21:00";
            availableHours.add(LocalTime.parse(endingHour));

            for (Appointment appointment : doctor.getAppointmentsOnDate(date)
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
    }

    static LocalTime timeForAppointment(Doctor doctor, LocalDate date, Scanner scanner) {
        printDoctorAvailableHoursForAppointment(doctor, date);

        System.out.print("Enter the time for your appointment in accordance with doctor schedule. (hh:mm): ");
        String time = scanner.nextLine() + ":00";
        if (!isTimeValid(time)) {
            return null;
        }
        return LocalTime.parse(time);
    }

    static Appointment createAppointment(Patient patient, Doctor doctor, Clinic clinic, LocalDateTime localDate) {
        if ((patient == null) || (doctor == null) || (clinic == null) || (localDate == null)){
            return null;
        }
        return new Appointment(patient,doctor,clinic,localDate);
    }

    static void deleteAppointment(Appointment appointment){
        appointment.getPatient().deleteAppointment(appointment);
        appointment.getDoctor().deleteAppointment(appointment);
        appointment.getClinic().deleteAppointment(appointment);
    }

    static Appointment selectAppointment(List<Appointment> appointments, int id){
        for (Appointment appointment:appointments
             ) {
            if (appointment.getId() == id){
                return appointment;
            }
        }
        System.out.println("No such id!");
        return null;
    }
}
