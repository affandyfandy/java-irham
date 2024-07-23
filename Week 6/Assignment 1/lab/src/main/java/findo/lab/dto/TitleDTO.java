package findo.lab.dto;

import java.util.Date;

import findo.lab.data.entity.Title;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TitleDTO {
    private String title;
    private Date fromDate;
    private Date toDate;

    public Title toEntity() {
        return Title.builder()
                .title(this.getTitle())
                .fromDate(this.getFromDate())
                .toDate(this.getToDate())
                .build();
    }
}