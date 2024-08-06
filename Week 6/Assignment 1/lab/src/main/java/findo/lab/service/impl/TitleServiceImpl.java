package findo.lab.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import findo.lab.data.entity.Title;
import findo.lab.data.entity.TitleId;
import findo.lab.data.repository.TitleRepository;
import findo.lab.dto.TitleDTO;
import findo.lab.service.TitleService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TitleServiceImpl implements TitleService {
    
    private final TitleRepository titleRepository;

    @Override
    public Optional<TitleDTO> findById(TitleId id) {
        return titleRepository.findById(id).map(Title::toDTO);
    }

    @Override
    public TitleDTO save(TitleDTO title) {
        Title body = title.toEntity();
        body = titleRepository.save(body);
        return body.toDTO();
    }

    @Override
    public TitleDTO update(TitleId id, TitleDTO title) {
        Title body = titleRepository.findById(id).get();
        body.setTitle(title.getTitle());
        body.setToDate(title.getToDate());
        body = titleRepository.save(body);
        return body.toDTO();
    }

    @Override
    public void deleteById(TitleId id) {
        titleRepository.deleteById(id);
    }
}
