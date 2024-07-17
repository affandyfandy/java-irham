package findo.lab.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import findo.lab.data.entity.Title;
import findo.lab.data.entity.Title.TitleId;
import findo.lab.service.TitleService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/titles")
@AllArgsConstructor
public class TitleController {

    private final TitleService titleService;

    @GetMapping
    public ResponseEntity<Title> findTitleById(@RequestBody TitleId id) {
        Optional<Title> titleOpt= titleService.findById(id);

        if(titleOpt.isPresent()) {
            return ResponseEntity.ok(titleOpt.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Title> save(@RequestBody Title title) {
        return ResponseEntity.ok(titleService.save(title));
    }

    @PutMapping
    public ResponseEntity<Title> update(@RequestBody Title title) {
        Optional<Title> titleOpt = titleService.findById(title.getId());
        
        if (titleOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(titleService.save(title));
    }

    @DeleteMapping
    public ResponseEntity<Title> deleteTitle(@RequestBody TitleId id) {
        Optional<Title> titleOpt = titleService.findById(id);

        if (titleOpt.isPresent()) {
            titleService.deleteById(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
