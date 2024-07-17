package findo.lab.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import findo.lab.data.entity.Title;
import findo.lab.data.entity.Title.TitleId;
import findo.lab.data.repository.TitleRepository;
import findo.lab.service.TitleService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TitleServiceImpl implements TitleService {
    
    private final TitleRepository titleRepository;

    @Override
    public Optional<Title> findById(TitleId id) {
        return titleRepository.findById(id);
    }

    @Override
    public Title save(Title title) {
        return titleRepository.save(title);
    }

    @Override
    public void deleteById(TitleId id) {
        titleRepository.deleteById(id);
    }
}
