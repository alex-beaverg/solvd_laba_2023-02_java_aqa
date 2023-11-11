package org.example.hospital.structure;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.hospital.custom_exceptions.*;
import org.example.hospital.data.Creator;
import org.example.hospital.people.Address;
import org.example.hospital.people.Diagnosis;
import org.example.hospital.people.Employee;
import org.example.hospital.people.Patient;
import org.example.hospital.util.RequestMethods;

public class GeneralActions {
    public static final Logger LOGGER_LN;
    public static final Logger LN_LOGGER_LN;
    public static final Logger LOGGER_TO_CONSOLE_AND_FILE;

    static {
        LOGGER_LN = LogManager.getLogger("InsteadOfSOUT_ln");
        LN_LOGGER_LN = LogManager.getLogger("ln_InsteadOfSOUT_ln");
        LOGGER_TO_CONSOLE_AND_FILE = LogManager.getLogger(GeneralActions.class);
    }

    public static Employee chooseDoctorFromList(Hospital hospital) {
        int index = 1;
        LN_LOGGER_LN.info("All doctors in the hospital:");
        for (Employee doctor: hospital.getEmployeesBySpecialistClass(2)) {
            LOGGER_LN.info("[" + index + "] - " + doctor.getPersonToPrintInList());
            index++;
        }
        int answer;
        do {
            try {
                answer = RequestMethods.requestingInfoWithChoice("Enter number of doctor to choose him: ", index - 1);
                break;
            } catch (EmptyInputException | MenuItemNumberOutOfBoundsException e) {
                LOGGER_TO_CONSOLE_AND_FILE.error(e.getMessage());
            } catch (NumberFormatException e) {
                LOGGER_TO_CONSOLE_AND_FILE.error("[NumberFormatException]: Entered data is not a number!");
            }
        } while (true);
        Employee doctor = hospital.getEmployeesBySpecialistClass(2).get(answer - 1);
        LOGGER_LN.info("Doctor " + doctor.getFullName() + " was chosen");
        return doctor;
    }

    public static Patient findExistPatient(Hospital hospital) {
        String fullName;
        do {
            try {
                fullName = RequestMethods.requestingInfoString("\nEnter patient full name for searching: ");
                break;
            } catch (EmptyInputException | StringFormatException e) {
                LOGGER_TO_CONSOLE_AND_FILE.error(e.getMessage());
            }
        } while (true);
        for (Patient existPatient: hospital.getPatients()) {
            if ((existPatient.getFullName()).equalsIgnoreCase(fullName)) {
                LOGGER_LN.info("Patient " + existPatient.getFullName() + " was found and chosen");
                return existPatient;
            }
        }
        LOGGER_LN.info("Patient " + fullName + " was not found. Try it again");
        return null;
    }

    public static Patient registerNewPatient(Hospital hospital){
        Patient newPatient = Creator.setPatient();
        Address address = Creator.setAddress();
        LN_LOGGER_LN.info("Registration of a new patient");
        do {
            try {
                newPatient.setFirstName(RequestMethods.requestingInfoString("Enter your first name: "));
                break;
            } catch (EmptyInputException | StringFormatException e) {
                LOGGER_TO_CONSOLE_AND_FILE.error(e.getMessage());
            }
        } while (true);
        do {
            try {
                newPatient.setLastName(RequestMethods.requestingInfoString("Enter your last name: "));
                break;
            } catch (EmptyInputException | StringFormatException e) {
                LOGGER_TO_CONSOLE_AND_FILE.error(e.getMessage());
            }
        } while (true);
        do {
            try {
                newPatient.setAge(getAgeFromConsole());
                break;
            } catch (AgeException e) {
                LOGGER_TO_CONSOLE_AND_FILE.error(e.getMessage());
            }
        } while (true);
        do {
            try {
                address.setCity(RequestMethods.requestingInfoString("Enter your city: "));
                break;
            } catch (EmptyInputException | StringFormatException e) {
                LOGGER_TO_CONSOLE_AND_FILE.error(e.getMessage());
            }
        } while (true);
        do {
            try {
                address.setStreet(RequestMethods.requestingInfoString("Enter your street: "));
                break;
            } catch (EmptyInputException | StringFormatException e) {
                LOGGER_TO_CONSOLE_AND_FILE.error(e.getMessage());
            }
        } while (true);
        do {
            try {
                address.setHouseNumber(RequestMethods.requestingInfoInt("Enter your house number: "));
                break;
            } catch (EmptyInputException | NegativeNumberException e) {
                LOGGER_TO_CONSOLE_AND_FILE.error(e.getMessage());
            } catch (NumberFormatException e) {
                LOGGER_TO_CONSOLE_AND_FILE.error("[NumberFormatException]: Entered data is not a number!");
            }
        } while (true);
        do {
            try {
                address.setFlatNumber(RequestMethods.requestingInfoInt("Enter your flat number: "));
                break;
            } catch (EmptyInputException | NegativeNumberException e) {
                LOGGER_TO_CONSOLE_AND_FILE.error(e.getMessage());
            } catch (NumberFormatException e) {
                LOGGER_TO_CONSOLE_AND_FILE.error("[NumberFormatException]: Entered data is not a number!");
            }
        } while (true);
        newPatient.setAddress(address);
        hospital.addPatient(newPatient);
        LOGGER_LN.info("New patient (" + newPatient.getFullName() + ") was registered");
        return newPatient;
    }

    private static int getAgeFromConsole() throws AgeException {
        do {
            try {
                int age = RequestMethods.requestingInfoInt("Enter your age: ");
                if (age > 122) {
                    throw new AgeException("[AgeException]: Age can not be more than 122 years");
                }
                return age;
            } catch (EmptyInputException | NegativeNumberException e) {
                LOGGER_TO_CONSOLE_AND_FILE.error(e.getMessage());
            } catch (NumberFormatException e) {
                LOGGER_TO_CONSOLE_AND_FILE.error("[NumberFormatException]: Entered data is not a number!");
            }
        } while (true);
    }

    public static Patient choosePatient(Hospital hospital) {
        int index = 1;
        LN_LOGGER_LN.info("All patients in the hospital:");
        for (Patient existPatient: hospital.getPatients()) {
            LOGGER_LN.info("[" + index + "] - " + existPatient.getPersonToPrintInList());
            index++;
        }
        int answer;
        do {
            try {
                answer = RequestMethods.requestingInfoWithChoice("Enter number of patient to choose him: ", index - 1);
                break;
            } catch (EmptyInputException | MenuItemNumberOutOfBoundsException e) {
                LOGGER_TO_CONSOLE_AND_FILE.error(e.getMessage());
            } catch (NumberFormatException e) {
                LOGGER_TO_CONSOLE_AND_FILE.error("[NumberFormatException]: Entered data is not a number!");
            }
        } while (true);
        LOGGER_LN.info("Patient (" + hospital.getPatients().get(answer - 1).getFullName() + ") was chosen");
        return hospital.getPatients().get(answer - 1);
    }

    public static Diagnosis getDiagnose(Hospital hospital, Patient patient, int number) {
        switch (number) {
            case (1) -> {
                Diagnosis diagnosis = Diagnosis.FLU;
                for (Department department : hospital.getDepartments()) {
                    if (department.getDiseasesType().equals(diagnosis.getType())) {
                        patient.setDepartment(department);
                        patient.setDoctor(department.getRandomEmployeeBySpecialistClass(2));
                        patient.setNurse(department.getRandomEmployeeBySpecialistClass(1));
                        department.addPatient(patient);
                        return diagnosis;
                    }
                }
            }
            case (2) -> {
                Diagnosis diagnosis = Diagnosis.COVID;
                for (Department department : hospital.getDepartments()) {
                    if (department.getDiseasesType().equals(diagnosis.getType())) {
                        patient.setDepartment(department);
                        patient.setDoctor(department.getRandomEmployeeBySpecialistClass(2));
                        patient.setNurse(department.getRandomEmployeeBySpecialistClass(1));
                        department.addPatient(patient);
                        return diagnosis;
                    }
                }
            }
            case (3) -> {
                Diagnosis diagnosis = Diagnosis.BONE_FRACTURE;
                for (Department department : hospital.getDepartments()) {
                    if (department.getDiseasesType().equals(diagnosis.getType())) {
                        patient.setDepartment(department);
                        patient.setDoctor(department.getRandomEmployeeBySpecialistClass(2));
                        patient.setNurse(department.getRandomEmployeeBySpecialistClass(1));
                        department.addPatient(patient);
                        return diagnosis;
                    }
                }
            }
            default -> {
                Diagnosis diagnosis = Diagnosis.UNKNOWN;
                for (Department department : hospital.getDepartments()) {
                    if (department.getDiseasesType().equals(diagnosis.getType())) {
                        patient.setDepartment(department);
                        patient.setDoctor(department.getRandomEmployeeBySpecialistClass(2));
                        patient.setNurse(department.getRandomEmployeeBySpecialistClass(1));
                        department.addPatient(patient);
                        return diagnosis;
                    }
                }
            }
        }
        return null;
    }
}
