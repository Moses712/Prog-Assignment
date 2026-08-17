import java.util.ArrayList;
import java.util.List;

public class HospitalSystem {
    private List<Patient> patients;
    private String[][] bedLayout;  // 4x5 layout holding bed codes "B01" - "B20"
    private String[][] bedOccupants; // Holds patient IDs or null if free

    public HospitalSystem() {
        patients = new ArrayList<>();
        bedLayout = new String[4][5];
        bedOccupants = new String[4][5];

        // Initialize 4x5 ward layout (B01 to B20)
        int bedNum = 1;
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 5; c++) {
                bedLayout[r][c] = String.format("B%02d", bedNum++);
                bedOccupants[r][c] = null;
            }
        }
    }

    // --- FEATURE 1: PATIENT MANAGEMENT ---

    public boolean registerPatient(Patient patient) {
        if (searchPatient(patient.getPatientId()) != null) {
            System.out.println("Error: Patient with ID " + patient.getPatientId() + " already exists.");
            return false;
        }
        patients.add(patient);
        System.out.println("Patient registered successfully.");
        return true;
    }

    public Patient searchPatient(String patientId) {
        for (Patient p : patients) {
            if (p.getPatientId().equalsIgnoreCase(patientId)) {
                return p;
            }
        }
        return null;
    }

    public boolean updatePatient(String patientId, String firstName, String lastName, int age, String gender, String condition) {
        Patient p = searchPatient(patientId);
        if (p == null) {
            System.out.println("Patient not found.");
            return false;
        }
        p.setFirstName(firstName);
        p.setLastName(lastName);
        p.setAge(age);
        p.setGender(gender);
        p.setMedicalCondition(condition);
        System.out.println("Patient details updated successfully.");
        return true;
    }

    public boolean deletePatient(String patientId) {
        Patient p = searchPatient(patientId);
        if (p == null) {
            System.out.println("Patient not found.");
            return false;
        }

        // If patient is in a bed, release it first
        if (p instanceof Inpatient) {
            Inpatient inp = (Inpatient) p;
            if (inp.getBedNumber() != null) {
                releaseBed(inp.getBedNumber());
            }
        }

        patients.remove(p);
        System.out.println("Patient removed successfully.");
        return true;
    }

    public void displayAllPatients() {
        if (patients.isEmpty()) {
            System.out.println("No patients registered in the system.");
            return;
        }
        System.out.println("\n--- Registered Patients ---");
        for (Patient p : patients) {
            p.displayDetails();
        }
    }

    // --- FEATURE 2: BED MANAGEMENT ---

    public boolean allocateBed(String patientId, String bedCode, String wardNumber) {
        Patient p = searchPatient(patientId);
        if (p == null) {
            System.out.println("Error: Patient not found.");
            return false;
        }

        if (p.getCategory() != PatientCategory.INPATIENT) {
            System.out.println("Error: Only Inpatients may be allocated a hospital bed.");
            return false;
        }

        Inpatient inpatient = (Inpatient) p;
        if (inpatient.getBedNumber() != null) {
            System.out.println("Error: Patient already has bed " + inpatient.getBedNumber() + " allocated.");
            return false;
        }

        if (getOccupiedBedCount() >= 20) {
            System.out.println("Error: No beds available in the ward.");
            return false;
        }

        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 5; c++) {
                if (bedLayout[r][c].equalsIgnoreCase(bedCode)) {
                    if (bedOccupants[r][c] != null) {
                        System.out.println("Error: Bed " + bedCode + " is already occupied.");
                        return false;
                    }
                    bedOccupants[r][c] = patientId;
                    inpatient.setWardNumber(wardNumber);
                    inpatient.setBedNumber(bedLayout[r][c]);
                    System.out.println("Bed " + bedLayout[r][c] + " successfully allocated to Patient " + patientId);
                    return true;
                }
            }
        }

        System.out.println("Error: Invalid Bed Code entered.");
        return false;
    }

    public boolean releaseBed(String bedCode) {
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 5; c++) {
                if (bedLayout[r][c].equalsIgnoreCase(bedCode)) {
                    String patientId = bedOccupants[r][c];
                    if (patientId == null) {
                        System.out.println("Bed " + bedCode + " is already vacant.");
                        return false;
                    }
                    
                    Patient p = searchPatient(patientId);
                    if (p instanceof Inpatient) {
                        Inpatient inp = (Inpatient) p;
                        inp.setBedNumber(null);
                        inp.setWardNumber(null);
                    }

                    bedOccupants[r][c] = null;
                    System.out.println("Bed " + bedCode + " released successfully upon discharge.");
                    return true;
                }
            }
        }
        System.out.println("Error: Invalid Bed Code.");
        return false;
    }

    public void displayWardLayout() {
        System.out.println("\n--- Complete Ward Layout (4x5) ---");
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 5; c++) {
                String status = (bedOccupants[r][c] == null) ? bedLayout[r][c] : "[OCC]";
                System.out.print(status + "\t");
            }
            System.out.println();
        }
    }

    public void displayAvailableBeds() {
        System.out.println("\n--- Available Beds ---");
        boolean found = false;
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 5; c++) {
                if (bedOccupants[r][c] == null) {
                    System.out.print(bedLayout[r][c] + " ");
                    found = true;
                }
            }
        }
        if (!found) System.out.print("No available beds.");
        System.out.println();
    }

    public void displayOccupiedBeds() {
        System.out.println("\n--- Occupied Beds ---");
        boolean found = false;
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 5; c++) {
                if (bedOccupants[r][c] != null) {
                    System.out.println("Bed " + bedLayout[r][c] + " -> Patient ID: " + bedOccupants[r][c]);
                    found = true;
                }
            }
        }
        if (!found) System.out.println("No beds are currently occupied.");
    }

    public int getOccupiedBedCount() {
        int count = 0;
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 5; c++) {
                if (bedOccupants[r][c] != null) count++;
            }
        }
        return count;
    }

    // --- FEATURE 3: REPORTS ---

    public void generateReports() {
        System.out.println("\n================ HOSPITAL REPORT ================");
        
        System.out.println("\n1. All Registered Patients:");
        displayAllPatients();

        System.out.println("\n2. Ward Beds Overview:");
        displayAvailableBeds();
        displayOccupiedBeds();

        System.out.println("\n3. Ward Statistics:");
        int totalPatients = patients.size();
        int occupiedBeds = getOccupiedBedCount();
        double occupancyPercentage = (occupiedBeds / 20.0) * 100;

        System.out.println("Total Registered Patients: " + totalPatients);
        System.out.println("Total Occupied Beds:       " + occupiedBeds + " / 20");
        System.out.printf("Ward Occupancy Percentage:  %.2f%%\n", occupancyPercentage);
        System.out.println("==================================================");
    }
}
