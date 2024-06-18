import java.util.ArrayList;
import java.util.List;

public class Student {
    // State
    private String name;
    private int age;
    private List<Subject> subjects;
    
    // Constructor
    public Student(String name, int age) {
        this.name = name;
        this.age = age;
        this.subjects = new ArrayList<>();
    }
    
    // Behavior (Method)
    public void learning() {
        System.out.println("Student " + name + " is learning:");
        for (Subject subject : subjects) {
            System.out.println("- " + subject.getName());
        }
        System.out.println();
    }
    
    // Method to add a subject
    public void addSubject(Subject subject) {
        subjects.add(subject);
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public List<Subject> getSubjects() {
        return subjects;
    }
}
