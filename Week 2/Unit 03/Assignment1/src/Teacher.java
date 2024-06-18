public class Teacher {
    // State
    private String name;
    private int age;
    private Subject subject;
    
    // Constructors
    
    // Constructor to create Teacher with name and age
    public Teacher(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    // Constructor to create Teacher with subject
    public Teacher(String name, int age, Subject subject) {
        this.name = name;
        this.age = age;
        this.subject = subject;
    }
    
    // Behavior (Method)
    public void teaching() {
        if (subject != null) {
            System.out.println("Teacher " + name + " teaching " + subject.getName() + " for Class " + subject.getClassId());
        } else {
            System.out.println("Teacher " + name + " teaching");
        }
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
    
    public Subject getSubject() {
        return subject;
    }
    
    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}