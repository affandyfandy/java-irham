package findo.lab.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="departments")
public class Department {
    @Id
    @Column(length = 4, columnDefinition = "CHAR(4)")
    private String deptNo;

    @Column(nullable = false, unique = true, length = 40)
    private String deptName;
}
