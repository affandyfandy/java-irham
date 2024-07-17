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
@Table(name="dept_manager")
@Data
public class DeptManager {
    @EmbeddedId
    private DeptManagerId id;

    @Column(nullable = false)
    private Date fromDate;

    @Column(nullable = false)
    private Date toDate;

    @Embeddable
    @Data
    public static class DeptManagerId implements Serializable {
        private Integer empNo;
        private String deptNo;
    }
}
