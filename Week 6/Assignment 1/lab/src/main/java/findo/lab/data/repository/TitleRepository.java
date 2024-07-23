package findo.lab.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import findo.lab.data.entity.Title;
import findo.lab.data.entity.TitleId;

public interface TitleRepository extends JpaRepository<Title, TitleId> {
}
