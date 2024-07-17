package findo.lab.data.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="salaries")
@Data
public class Salary {
    @EmbeddedId
    private SalaryId id;

    @Column(nullable = false)
    private Integer salary;

    @Column(nullable = false)
    private Date toDate;

    @Embeddable
    @Data
    public static class SalaryId implements Serializable {
        private Integer empNo;
        private Date fromDate;
    }
}
