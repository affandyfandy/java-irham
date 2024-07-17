package findo.lab.service;

import java.util.Optional;

import findo.lab.data.entity.Title;
import findo.lab.data.entity.Title.TitleId;

public interface TitleService {
    Optional<Title> findById(TitleId id);
    Title save(Title title);
    void deleteById(TitleId id);
}
