# Prog-Assignment: Patient Management System

A Java-based console application designed to manage hospital patient registrations, ward bed allocations, and system status reporting using Object-Oriented Programming (OOP) principles.

---

## Features

* **Patient Management:** Complete CRUD operations (Create, Search, Update, Delete) supporting specialized patient categories (`Inpatient`, `Outpatient`, and `Emergency`).
* **Bed Management:** Interactive allocation and discharge tracking across a 20-bed ward matrix ($4 \times 5$ grid).
* **System Reporting:** Summary metrics generating active patient lists, remaining bed counts, and real-time ward occupancy percentages.
* **Interactive Terminal UI:** Console menu system utilizing `Scanner` inputs for navigation.

---

## Project Structure

```text
Prog-Assignment/
├── src/
│   ├── Main.java              # Main execution loop and menu routing
│   ├── Patient.java           # Base abstract class for patient models
│   ├── Inpatient.java         # Subclass for admitted patients
│   ├── Outpatient.java        # Subclass for outpatient records
│   ├── Emergency.java         # Subclass for emergency cases
│   ├── BedManager.java        # 4x5 Ward matrix tracking & allocation logic
│   └── ReportGenerator.java   # System summary and occupancy metrics
├── test/
│   └── MainTest.java          # Unit testing suite
├── README.md                  # System documentation
└── .gitignore                 # IDE and build output exclusions
