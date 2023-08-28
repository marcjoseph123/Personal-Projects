import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class FinalProject {
    static String formatReportData(String category, List<? extends Person> list) {
        StringBuffer sb = new StringBuffer();
        sb.append("\n" + category);
        sb.append("\n---------------\n");
        for (int i = 0; i < list.size(); i++) {
            sb.append(i + 1 + ". ");
            sb.append(list.get(i));
        }
        return sb.toString();
    }

    public static String menu() {
        String userSelection = "NULL";
        boolean isValid = false;
        Scanner myscanner = new Scanner(System.in);

        System.out.println("\nChoose one of the options\n");

        System.out.println("1- Enter the information a faculty\r\n" + //
                "2- Enter the information of a student\r\n" + //
                "3- Print tuition invoice for a student\r\n" + //
                "4- Print faculty information\r\n" + //
                "5- Enter the information of a staff member\r\n" + //
                "6- Print the information of a staff member\r\n" + //
                "7- Delete a person\r\n" + //
                "8- Exit Program");

        System.out.print("\t\t\nEnter your selction:\t");

        userSelection = myscanner.nextLine();
        isValid = true;
        return userSelection;
    }

    public static void getInformation(int type, ArrayList<Person> personList) {
        String diffrentTypes[] = { "faculty", "student", "staff member" };

        String tempName = "NULL";
        String tempId = "NULL";
        boolean isValidID = false;
        Scanner myscanner = new Scanner(System.in);

        System.out.println("\n\nEnter the " + diffrentTypes[type] + " info:\n ");

        System.out.print("Name of " + diffrentTypes[type] + ":\t");
        tempName = myscanner.nextLine();

        while (!isValidID) {
            System.out.print("ID:\t");
            tempId = myscanner.nextLine();
            isValidID = isIdValid(tempId, personList);
            if (!isValidID) {
                System.out.println(
                        "\n[Invalid ID format or this ID is already taken. Must be LetterLetterDigitDigitDigitDigit]\n");
            }
        }
        if (diffrentTypes[type].equals("faculty")) {
            // call faculty function
            facultySpecificInfo(tempName, tempId, personList);
        } else if (diffrentTypes[type].equals("student")) {
            // call student function
            studentSpecificInfo(tempName, tempId, personList);
        } else {
            // call staff member function
            staffMemberSpeificInfo(tempName, tempId, personList);
        }
    }

    public static boolean isIdValid(String id, ArrayList<Person> personList) {
        boolean isValid = true;

        if (id.length() > 6 || id.length() < 6) {
            // System.out.println("\n[Invalid ID format. Must be
            // LetterLetterDigitDigitDigitDigit]\n");
            return false;
        } else {
            for (int i = 0; i < id.length(); i++) {
                // System.out.println("----" + i);
                if (i < 2) {
                    if (Character.isAlphabetic(id.charAt(i))) {
                        isValid = isValid;
                        // System.out.println(isValid);
                    } else {
                        isValid = false;
                        // System.out.println(isValid);
                    }
                }
                if (i >= 2 && i < id.length()) {
                    if (Character.isDigit(id.charAt(i))) {
                        isValid = isValid;
                        // System.out.println(isValid);
                    } else {
                        isValid = false;
                        // System.out.println(isValid);
                    }
                }
            }

        }
        // System.out.println("FINAL VALUE");
        for (Person person : personList) {
            if (person.getId().equals(id)) {
                isValid = false;
                // System.out.println("\n[This id is already taken]");
                return isValid;
            }
        }
        return isValid;
    }

    public static void studentSpecificInfo(String name, String id, ArrayList<Person> personList) {
        double intGpa = -1;
        String stringGpa = "NULL";
        int intCreditHours = -1;
        String stringCreditHours = "NULL";
        boolean isGpaValid = false;
        boolean isCreditValid = false;
        Scanner myScanner = new Scanner(System.in);

        while (!isGpaValid) {
            try {
                System.out.print("Gpa:\t");
                stringGpa = myScanner.nextLine();
                intGpa = Double.parseDouble(stringGpa);
                if (intGpa < 0) {
                    System.out.println("\n[Gpa >= 0]\n");
                    isGpaValid = false;
                } else {
                    isGpaValid = true;
                }

            } catch (Exception e) {
                System.out.println("\n[Not a valid GPA, enter as format (x.xx)]\n");
                isGpaValid = false;
            }
        }
        while (!isCreditValid) {
            try {
                System.out.print("Credit hours:\t");
                stringCreditHours = myScanner.nextLine();
                intCreditHours = Integer.parseInt(stringCreditHours);
                if (intCreditHours < 0) {
                    System.out.println("\n[Credit Hours >= 0]\n");
                    isCreditValid = false;
                } else {
                    isCreditValid = true;
                }

            } catch (Exception e) {
                System.out.println("\n[Not a valid credit number, enter an int]\n");
                isCreditValid = false;
            }
        }

        Student newStudent = new Student(name, id, intGpa, intCreditHours);
        personList.add(newStudent);

        // System.out.println(newStudent);
        System.out.println("\n[Student added!]\n\n\n");

    }

    public static void facultySpecificInfo(String name, String id, ArrayList<Person> personList) {
        String rank = "NULL";
        String department = "NULL";
        boolean validDepartment = false;
        boolean validRank = false;
        Scanner myScanner = new Scanner(System.in);

        while (!validRank) {
            System.out.print("Rank:\t");
            rank = myScanner.nextLine();
            validRank = isRankValid(rank);
        }

        while (!validDepartment) {
            System.out.print("Department:\t");
            department = myScanner.nextLine();
            validDepartment = isDepartmentValid(department);
        }

        Faculty newFaculty = new Faculty(name, id, department, rank);
        personList.add(newFaculty);

        // System.out.println(newFaculty);
        System.out.println("\n[Faculty added!]\n\n\n");
    }

    public static boolean isDepartmentValid(String department) {
        if (department.toLowerCase().equals("mathematics") || department.toLowerCase().equals("engineering")
                || department.toLowerCase().equals("english")) {
            return true;
        } else {
            System.out.println("\n" + "['" + department + "'" + " is invalid]\n");
            return false;
        }
    }

    public static boolean isRankValid(String rank) {
        if (rank.equals("Professor") || rank.equals("Adjunt")) {
            return true;
        } else {
            System.out.println("\n" + "['" + rank + "'" + " is invalid]\n");
            return false;
        }
    }

    public static void staffMemberSpeificInfo(String name, String id, ArrayList<Person> personList) {
        String department = "NULL";
        String status = "NULL";
        boolean isValidDepartment = false;
        boolean isStatusValid = false;
        Scanner myScanner = new Scanner(System.in);

        while (!isValidDepartment) {
            System.out.print("Department:\t");
            department = myScanner.nextLine();
            isValidDepartment = isDepartmentValid(department);
        }

        while (!isStatusValid) {
            System.out.print("Status, Enter P for Part time, or Enter F for Full Time:\t");
            status = myScanner.nextLine();
            if (status.toLowerCase().equals("p") || status.toLowerCase().equals("f")) {
                if (status.toLowerCase().equals("p")) {
                    status = "Part Time";
                } else {
                    status = "Full Time";
                }
                isStatusValid = true;
            } else {
                System.out.println("\n[Invalid choice]\n");
                isStatusValid = false;
            }
        }
        Person newStaffMember = new Staff(name, id, department, status);
        personList.add(newStaffMember);
        // System.out.println(newStaffMember);

        System.out.println("\n[Staff member added!]\n\n");
    }

    public static void printStudentTuition(ArrayList<Person> personList) {
        String searchThisStudent;
        boolean studentFound = false;
        Scanner myScanner = new Scanner(System.in);

        System.out.print("Enter the student's id:\t");
        searchThisStudent = myScanner.nextLine();

        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getId().equals(searchThisStudent) && personList.get(i) instanceof Student) {
                personList.get(i).print();
                studentFound = true;
                break;
            }
        }
        if (studentFound == false) {
            System.out.println("\n[No student matched!]\n");
        }
    }

    public static void printFacultyInfo(ArrayList<Person> personList) {
        String searchThisFaculty;
        boolean facultyFound = false;
        Scanner myScanner = new Scanner(System.in);

        System.out.print("Enter the Faculty's id:\t");
        searchThisFaculty = myScanner.nextLine();

        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getId().equals(searchThisFaculty) && personList.get(i) instanceof Faculty) {
                personList.get(i).print();
                facultyFound = true;
                break;
            }
        }
        if (facultyFound == false) {
            System.out.println("\n[No Faculty matched!]\n");
        }
    }

    public static void printStaffInfo(ArrayList<Person> personList) {
        String searchThisStaff;
        boolean staffFound = false;
        Scanner myScanner = new Scanner(System.in);

        System.out.print("Enter the staff's id:\t");
        searchThisStaff = myScanner.nextLine();

        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getId().equals(searchThisStaff) && personList.get(i) instanceof Staff) {
                personList.get(i).print();
                staffFound = true;
                break;
            }
        }
        if (staffFound == false) {
            System.out.println("\n[No staff matched!]");
        }
    }

    public static void deletePerson(ArrayList<Person> personList) {
        String searchThisPerson;
        boolean personFound = false;
        Scanner myScanner = new Scanner(System.in);

        System.out.print("Enter the id of the person that you want to delete:\t");
        searchThisPerson = myScanner.nextLine();

        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getId().equals(searchThisPerson) || personList.get(i) instanceof Student) {
                personList.remove(i);
                System.out.println("\n\n[Removed " + searchThisPerson + "]");
                personFound = true;
                break;
            }
        }
        if (personFound == false) {
            System.out.println("Sorry no such person exist.");
        }
    }

    public static void createReport(ArrayList<Person> personList) {
        Scanner myScanner = new Scanner(System.in);
        String userInput = "NULL";
        boolean isValidImput = false;

        ArrayList<Faculty> faculties = new ArrayList<Faculty>();
        ArrayList<Staff> staffs = new ArrayList<Staff>();
        ArrayList<Student> students = new ArrayList<Student>();

        for (Person person : personList) {
            if (person instanceof Faculty)
                faculties.add((Faculty) person);
            else if (person instanceof Staff)
                staffs.add((Staff) person);
            else
                students.add((Student) person);
        }
        while (!isValidImput) {
            System.out.print("Would like to sort your students by descending gpa or name (1 for gpa, 2 for name):\t ");
            userInput = myScanner.nextLine();
            if (userInput.equals("1") || userInput.equals("2")) {
                isValidImput = true;
            } else {
                System.out.println("\n[Invalid Choice]\n");
                isValidImput = false;
            }
        }

        Student.sortKey = userInput;
        Collections.sort(students);

        String output = new String();
        output = output + "\n\t\tReport created on " + DateFormat.getInstance().format(new Date());
        output = output + "\n\t\t************************************\n\n";
        output = output + "Faculty Members\n";
        output = output + "----------------\n";

        // for faculties
        for (int i = 0; i < faculties.size(); i++) {
            output = output + (i + 1) + ". " + faculties.get(i).getFullName() + "\n";
            output = output + "ID: " + faculties.get(i).getId() + "\n";
            output = output + faculties.get(i).getRank() + ", " + faculties.get(i).getDepartment() + "\n\n";
        }

        output = output + "Staff Members\n";
        output = output + "---------------\n";

        // for staff member
        for (int i = 0; i < staffs.size(); i++) {
            output = output + (i + 1) + ". " + staffs.get(i).getFullName() + "\n";
            output = output + "ID: " + staffs.get(i).getId() + "\n";
            output = output + staffs.get(i).getDepartment() + ", " + staffs.get(i).getStatus() + "\n\n";
        }

        output = output + "Students\n";
        output = output + "---------------\n";

        for (int i = 0; i < students.size(); i++) {
            output = output + (i + 1) + ". " + students.get(i).getFullName() + "\n";
            output = output + "ID: " + students.get(i).getId() + "\n";
            output = output + "Gpa: " + students.get(i).getGpa() + "\n";
            output = output + "Credit Hours: " + students.get(i).getNumOfCredits() + "\n\n";
        }

        // System.out.println(output);
        try {
            Files.write(Paths.get("report.txt"), output.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\nReport created and saved on your hard drive!");
    }

    public static void main(String[] args) {
        System.out.println("\t\tWlcome to my Personal Management Program");

        String userSelection;
        int intUserSelection;
        boolean exit = false;
        ArrayList<Person> personList = new ArrayList<Person>();

        while (!exit) {
            userSelection = menu();
            try {
                intUserSelection = Integer.parseInt(userSelection);
                if (intUserSelection == 1) {
                    getInformation(0, personList);
                }
                if (intUserSelection == 2) {
                    getInformation(1, personList);
                }
                if (intUserSelection == 3) {
                    printStudentTuition(personList);
                }
                if (intUserSelection == 4) {
                    printFacultyInfo(personList);
                }
                if (intUserSelection == 5) {
                    getInformation(2, personList);
                }
                if (intUserSelection == 6) {
                    printStaffInfo(personList);
                }
                if (intUserSelection == 7) {
                    deletePerson(personList);
                }
                if (intUserSelection == 8) {
                    exit = true;
                }
                if (intUserSelection < 0 || intUserSelection > 8) {
                    System.out.println("\n\t[INVALID CHOICE CHOOSE 1-8]\n");
                }
            } catch (Exception e) {
                System.out.println("\nInvaid entry-please try again\n");
                exit = false;
            }
        }
        if (exit) {
            String userInput = "NULL";
            boolean isInputValid = false;
            Scanner myScanner = new Scanner(System.in);

            while (!isInputValid) {
                System.out.print("\n\nWould you like to create the report ? (Y/N):\t");
                userInput = myScanner.nextLine();

                if (userInput.toLowerCase().equals("y")) {
                    isInputValid = true;
                    createReport(personList);
                } else if (userInput.toLowerCase().equals("n")) {
                    isInputValid = true;
                    System.out.println("\nGoodbye!");
                } else {
                    System.out.println("\n[That is not a valid choice, please try again]\n");
                    isInputValid = false;
                }
            }
        }
    }
}

class Staff extends Employee {
    private String status;

    public Staff(String fullName, String id, String department, String status) {
        super(fullName, id, department);
        this.status = status;
    }

    public Staff() {
        this.status = "NULL";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void print() {
        String tempDepart;
        if (getDepartment().toLowerCase().equals("engineering")) {
            tempDepart = "Engineering";
        } else if (getDepartment().toLowerCase().equals("mathematics")) {
            tempDepart = "Mathematics";
        } else {
            tempDepart = "English";
        }

        System.out.println("\n--------------------------------------------------\n");
        System.out.println(getFullName() + "\t" + getId());
        System.out.println("\n" + tempDepart + " Department,\t\t\t" + getStatus());
        System.out.println("\n--------------------------------------------------\n\n");
    }

    @Override
    public String type() {
        return "Staff Member";
    }

    public String toString() {
        return super.toString() + status + "]";
    }

}

class Faculty extends Employee {
    private String rank;

    public Faculty(String fullName, String id, String department, String rank) {
        super(fullName, id, department);
        this.rank = rank;
    }

    public Faculty() {
        this.rank = "NULL";
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRank() {
        return rank;
    }

    @Override
    public void print() {
        String tempDepart;
        if (getDepartment().toLowerCase().equals("engineering")) {
            tempDepart = "Engineering";
        } else if (getDepartment().toLowerCase().equals("mathematics")) {
            tempDepart = "Mathematics";
        } else {
            tempDepart = "English";
        }

        System.out.println("\n\n--------------------------------------------------\n");
        System.out.println(getFullName() + "\t\t\t" + getId());
        System.out.println("\n" + tempDepart + " Department,\t" + getRank());
        System.out.println("\n--------------------------------------------------\n\n");
    }

    @Override
    public String type() {
        return "Faculty";
    }

    public String toString() {
        return super.toString() + rank + "]";
    }

}

class Student extends Person implements Comparable<Student> {
    private double gpa;
    private int numOfCredits;
    public static String sortKey;

    public Student(String fullName, String id, double gpa, int numOfCredits) {
        super(fullName, id);
        this.gpa = gpa;
        this.numOfCredits = numOfCredits;
    }

    public Student() {
        super("Null", "Null");
        this.gpa = 0.0;
        this.numOfCredits = 0;
    }

    public double getGpa() {
        return gpa;
    }

    public int getNumOfCredits() {
        return numOfCredits;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public void setNumOfCredits(int numOfCredits) {
        this.numOfCredits = numOfCredits;
    }

    @Override
    public void print() {
        double grandTotal;
        double discount = 0.0;

        System.out.println("--------------------------------------------------\n");
        System.out.println(getFullName() + "\t\t\t" + getId());
        System.out.println("\nCredit Hours: " + getNumOfCredits() + "  ($236.45/credit hour)");
        System.out.println("Fees: $52");

        grandTotal = (numOfCredits * 236.45) + 52;

        if (getGpa() >= 3.85) {
            discount = grandTotal * 0.25;
            grandTotal = grandTotal - discount;
        }
        // String.format("%,.2f", grandTotal);

        System.out.println("Total payment (after discount): $" + String.format("%,.2f", grandTotal) + "\t ($"
                + String.format("%,.2f", discount)
                + " applied)");
        System.out.println("\n--------------------------------------------------\n\n");
    }

    @Override
    public String type() {
        return "Student";
    }

    public String toString() {
        return super.toString() + gpa + " " + numOfCredits + "]";
    }

    @Override
    public int compareTo(Student arg0) {
        if (sortKey.equals("1")) {
            if (getGpa() < arg0.getGpa()) {
                return 1;
            }
            if (getGpa() == arg0.getGpa()) {
                return 0;
            } else {
                return -1;
            }
        } else {
            return arg0.getFullName().compareTo(getFullName());
        }
    }

    public void sortBy(String key) {
        this.sortKey = key;
    }

    public static String getSortKey() {
        return sortKey;
    }

    public static void setSortKey(String sortKey) {
        Student.sortKey = sortKey;
    }

}

abstract class Employee extends Person {
    private String department;

    public Employee(String fullName, String id, String department) {
        super(fullName, id);
        this.department = department;
    }

    public Employee() {
        this.department = "NULL";
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public String toString() {
        return super.toString() + " " + department + " ";
    }

}

abstract class Person {
    private String fullName;
    private String id;

    public Person(String fullName, String id) {
        this.fullName = fullName;
        this.id = id;
    }

    
    public Person() {
        this.fullName = "NULL";
        this.id = "NULL";
    }

    public String getFullName() {
        return fullName;
    }

    public String getId() {
        return id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toString() {
        return "[" + fullName + " " + id + " ";
    }

    public abstract void print();

    public abstract String type();
}