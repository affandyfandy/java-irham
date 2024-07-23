package findo.lab.service;

import java.util.Optional;

import findo.lab.data.entity.TitleId;
import findo.lab.dto.TitleDTO;

public interface TitleService {
    Optional<TitleDTO> findById(TitleId id);
    TitleDTO save(TitleDTO title);
    TitleDTO update(TitleId id, TitleDTO title);
    void deleteById(TitleId id);
}
