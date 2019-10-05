import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class DoctorTest {
    private Clinic clinic = new Clinic("Clinica", 13);
    private Doctor doctorForTesting;
    private Patient patient1 = new Patient("Anca Vasiliu", "0754345311");
    private Patient patient2 = new Patient("Septimiu Avram", "0722434354");
    private Patient patient3 = new Patient("George Apostol", "0766467259");

    private void addAppointmentsForDoctor() {
        doctorForTesting.addAppointment(new Appointment(patient1, doctorForTesting, clinic, LocalDateTime.of(2019, 11, 1, 11, 0)));
        doctorForTesting.addAppointment(new Appointment(patient2, doctorForTesting, clinic, LocalDateTime.of(2019, 11, 1, 13, 0)));
        doctorForTesting.addAppointment(new Appointment(patient3, doctorForTesting, clinic, LocalDateTime.of(2019, 12, 1, 15, 0)));
        doctorForTesting.addAppointment(new Appointment(patient1, doctorForTesting, clinic, LocalDateTime.of(2019, 12, 3, 16, 0)));
        doctorForTesting.addAppointment(new Appointment(patient2, doctorForTesting, clinic, LocalDateTime.of(2019, 11, 1, 17, 0)));
        doctorForTesting.addAppointment(new Appointment(patient3, doctorForTesting, clinic, LocalDateTime.of(2019, 12, 5, 18, 0)));
        doctorForTesting.addAppointment(new Appointment(patient1, doctorForTesting, clinic, LocalDateTime.of(2019, 11, 1, 19, 0)));
        doctorForTesting.addAppointment(new Appointment(patient2, doctorForTesting, clinic, LocalDateTime.of(2019, 11, 1, 20, 0)));
    }

    @Before
    public void setDoctorForTesting() {
        doctorForTesting = new Doctor("Georgescu Simion", "stomatolog", "georgescu_simion@email.com", "07463232821");
    }

    @Test
    public void testIsAvailableOnHour() {
        //given
        addAppointmentsForDoctor();

        //then
        assertFalse(doctorForTesting.isFreeOnThisHour(LocalDateTime.of(2019, 11, 1, 11, 0)));
        assertTrue(doctorForTesting.isFreeOnThisHour(LocalDateTime.of(2019, 11, 1, 12, 0)));
        assertTrue(doctorForTesting.isFreeOnThisHour(LocalDateTime.of(2019, 12, 1, 12, 0)));
        assertFalse(doctorForTesting.isFreeOnThisHour(LocalDateTime.of(2019, 11, 1, 12, 1)));
        assertFalse(doctorForTesting.isFreeOnThisHour(LocalDateTime.of(2019, 11, 1, 11, 59)));
    }

    @Test
    public void testAppointmentIsAdded() {
        //given
        addAppointmentsForDoctor();
        Appointment appointmentToAdd = new Appointment(patient1, doctorForTesting, clinic, LocalDateTime.of(2019, 12, 12, 13, 0));
        Appointment secondAppointmentToAdd = null;
        int sizeOfAppointmentsListBeforeAddingAppointment = doctorForTesting.getDoctorAppointments().size();
        //when
        doctorForTesting.addAppointment(appointmentToAdd);
        doctorForTesting.addAppointment(secondAppointmentToAdd);
        //then
        assertEquals(doctorForTesting.getDoctorAppointments().size(), sizeOfAppointmentsListBeforeAddingAppointment + 1);
        assertTrue(doctorForTesting.getDoctorAppointments().contains(appointmentToAdd));
    }

    @Test
    public void isAppointmentDeleted() {
        //given
        addAppointmentsForDoctor();
        Appointment appointmentToDelete = new Appointment(patient1, doctorForTesting, clinic, LocalDateTime.of(2019, 12, 12, 13, 0));
        doctorForTesting.addAppointment(appointmentToDelete);
        int numberOfAppointmentsBeforeDeletion = doctorForTesting.getDoctorAppointments().size();
        //when
        doctorForTesting.deleteAppointment(appointmentToDelete);
        //then
        assertFalse(doctorForTesting.getDoctorAppointments().contains(appointmentToDelete));
        assertEquals(numberOfAppointmentsBeforeDeletion - 1, doctorForTesting.getDoctorAppointments().size());
    }

    @Test
    public void testDatesFromAppointmentsOnDate() {
        //given
        List<Appointment> appointmentsOnDate;
        List<Appointment> restOfAppointments;
        addAppointmentsForDoctor();
        LocalDate date = LocalDate.of(2019, 11, 1);
        int numberOfAppointments = doctorForTesting.getDoctorAppointments().size();

        //when

        appointmentsOnDate = doctorForTesting.getAppointmentsOnDate(date);
        restOfAppointments = doctorForTesting.getDoctorAppointments();
        restOfAppointments.removeAll(appointmentsOnDate);

        //then

        for (Appointment appointment : appointmentsOnDate
        ) {
            TestCase.assertEquals(appointment.getLocalDateTime().toLocalDate(), date);
        }
        for (Appointment appointment : restOfAppointments
        ) {
            assertNotSame(appointment.getLocalDateTime().toLocalDate(), date);
        }
        assertEquals(appointmentsOnDate.size() + restOfAppointments.size(), numberOfAppointments);
    }
}
