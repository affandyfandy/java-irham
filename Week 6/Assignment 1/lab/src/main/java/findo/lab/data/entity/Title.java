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
@Table(name="titles")
@Data
public class Title {
    @EmbeddedId
    private TitleId id;

    @Column
    private Date toDate;

    @Embeddable
    @Data
    public static class TitleId implements Serializable {
        private Integer empNo;
        private String title;
        private Date fromDate;
    }
}
