package findo.lab.data.entity;

import java.util.Date;
import java.util.Objects;

import findo.lab.dto.TitleDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="titles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(TitleId.class)
public class Title {

    @Id
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="emp_no")
    private Employee employee;

    @Id
    @Column(length = 50)
    private String title;

    @Id
    private Date fromDate;
    
    private Date toDate;

    public TitleDTO toDTO() {
        return TitleDTO.builder()
                .title(this.getTitle())
                .fromDate(this.getFromDate())
                .toDate(this.getToDate())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Title that = (Title) o;
        return employee != null && employee.equals(that.employee) &&
               fromDate != null && fromDate.equals(that.fromDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, fromDate);
    }
}
