import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HospitalSystem system = new HospitalSystem();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== HOSPITAL MANAGEMENT SYSTEM ===");
            System.out.println("1. Patient Management");
            System.out.println("2. Bed Management");
            System.out.println("3. Reports");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");

            int choice = readInt(scanner);

            switch (choice) {
                case 1:
                    patientMenu(system, scanner);
                    break;
                case 2:
                    bedMenu(system, scanner);
                    break;
                case 3:
                    system.generateReports();
                    break;
                case 4:
                    running = false;
                    System.out.println("Exiting System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private static void patientMenu(HospitalSystem system, Scanner scanner) {
        System.out.println("\n--- Patient Management ---");
        System.out.println("1. Register New Patient");
        System.out.println("2. Search Patient");
        System.out.println("3. Update Patient Details");
        System.out.println("4. Delete Patient");
        System.out.println("5. Display All Patients");
        System.out.print("Select choice: ");

        int choice = readInt(scanner);

        switch (choice) {
            case 1:
                System.out.print("Enter Patient ID: ");
                String id = scanner.nextLine();
                System.out.print("Enter First Name: ");
                String fn = scanner.nextLine();
                System.out.print("Enter Last Name: ");
                String ln = scanner.nextLine();
                System.out.print("Enter Age: ");
                int age = readInt(scanner);
                System.out.print("Enter Gender: ");
                String gender = scanner.nextLine();
                System.out.print("Enter Medical Condition: ");
                String cond = scanner.nextLine();

                System.out.println("Select Patient Category:");
                System.out.println("1. Inpatient\n2. Outpatient\n3. Emergency");
                int catChoice = readInt(scanner);

                PatientCategory category;
                if (catChoice == 1) category = PatientCategory.INPATIENT;
                else if (catChoice == 2) category = PatientCategory.OUTPATIENT;
                else category = PatientCategory.EMERGENCY;

                if (category == PatientCategory.INPATIENT) {
                    system.registerPatient(new Inpatient(id, fn, ln, age, gender, cond, null, null));
                } else {
                    system.registerPatient(new Patient(id, fn, ln, age, gender, cond, category));
                }
                break;

            case 2:
                System.out.print("Enter Patient ID to Search: ");
                String searchId = scanner.nextLine();
                Patient found = system.searchPatient(searchId);
                if (found != null) {
                    found.displayDetails();
                } else {
                    System.out.println("Patient not found.");
                }
                break;

            case 3:
                System.out.print("Enter Patient ID to Update: ");
                String upId = scanner.nextLine();
                System.out.print("Enter New First Name: ");
                String ufn = scanner.nextLine();
                System.out.print("Enter New Last Name: ");
                String uln = scanner.nextLine();
                System.out.print("Enter New Age: ");
                int uage = readInt(scanner);
                System.out.print("Enter New Gender: ");
                String ugender = scanner.nextLine();
                System.out.print("Enter New Medical Condition: ");
                String ucond = scanner.nextLine();

                system.updatePatient(upId, ufn, uln, uage, ugender, ucond);
                break;

            case 4:
                System.out.print("Enter Patient ID to Delete: ");
                String delId = scanner.nextLine();
                system.deletePatient(delId);
                break;

            case 5:
                system.displayAllPatients();
                break;

            default:
                System.out.println("Invalid option.");
        }
    }

    private static void bedMenu(HospitalSystem system, Scanner scanner) {
        System.out.println("\n--- Bed Management ---");
        System.out.println("1. Allocate Bed to Inpatient");
        System.out.println("2. Release Bed (Discharge)");
        System.out.println("3. Display Complete Ward Layout");
        System.out.println("4. Display Available Beds");
        System.out.println("5. Display Occupied Beds");
        System.out.print("Select choice: ");

        int choice = readInt(scanner);

        switch (choice) {
            case 1:
                System.out.print("Enter Patient ID: ");
                String pid = scanner.nextLine();
                System.out.print("Enter Ward Number: ");
                String ward = scanner.nextLine();
                System.out.print("Enter Bed Code (e.g. B01 to B20): ");
                String bed = scanner.nextLine();
                system.allocateBed(pid, bed, ward);
                break;

            case 2:
                System.out.print("Enter Bed Code to Release (e.g. B01): ");
                String relBed = scanner.nextLine();
                system.releaseBed(relBed);
                break;

            case 3:
                system.displayWardLayout();
                break;

            case 4:
                system.displayAvailableBeds();
                break;

            case 5:
                system.displayOccupiedBeds();
                break;

            default:
                System.out.println("Invalid option.");
        }
    }

    private static int readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        int val = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return val;
    }
}
