public class App {
    public static void main(String[] args) throws Exception {
        // Create Subjects
        Subject math = new Subject("Mathematics");
        math.setClassId("Class 1");
        Subject science = new Subject("Science");
        science.setClassId("Class 1");
        
        // Create Teacher
        Teacher tam = new Teacher("Tam", 30, math);
        
        // Create Student
        Student john = new Student("John", 15);
        john.addSubject(math);
        john.addSubject(science);
        
        // Print information
        tam.teaching();
        john.learning();
    }
}
