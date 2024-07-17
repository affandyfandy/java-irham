package findo.lab.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import findo.lab.data.entity.Title;

public interface TitleRepository extends JpaRepository<Title, Title.TitleId> {
}
