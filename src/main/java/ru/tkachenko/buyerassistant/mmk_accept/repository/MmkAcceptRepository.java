package ru.tkachenko.buyerassistant.mmk_accept.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tkachenko.buyerassistant.mmk_accept.entity.MmkAcceptRowEntity;

import java.util.List;

@Repository
public interface MmkAcceptRepository extends JpaRepository<MmkAcceptRowEntity, Long> {

    MmkAcceptRowEntity findFirstBySpecAndPosition(String spec, int position);
}
