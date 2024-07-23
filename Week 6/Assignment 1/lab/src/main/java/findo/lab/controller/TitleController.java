package findo.lab.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import findo.lab.data.entity.TitleId;
import findo.lab.dto.TitleDTO;
import findo.lab.service.TitleService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/titles")
@AllArgsConstructor
public class TitleController {

    private final TitleService titleService;

    @GetMapping
    public ResponseEntity<TitleDTO> findTitleById(@RequestBody TitleId id) {
        Optional<TitleDTO> titleOpt= titleService.findById(id);

        if(titleOpt.isPresent()) {
            return ResponseEntity.ok(titleOpt.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<TitleDTO> save(@RequestBody TitleDTO title) {
        return ResponseEntity.ok(titleService.save(title));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TitleDTO> update(@PathVariable("id") TitleId id, @RequestBody TitleDTO title) {
        return ResponseEntity.ok(titleService.update(id, title));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TitleDTO> deleteTitle(@PathVariable("id") TitleId id) {
        titleService.deleteById(id);
        return ResponseEntity.notFound().build();
    }
}
