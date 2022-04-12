package ru.tkachenko.buyerassistent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tkachenko.buyerassistent.entity.MmkAcceptRowEntity;

import java.util.List;

@Repository
public interface MmkAcceptRepository extends JpaRepository<MmkAcceptRowEntity, Long> {

    MmkAcceptRowEntity findFirstBySpecAndPosition(String spec, int position);
}
