package ru.tkachenko.buyerassistent.mmk_accept.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tkachenko.buyerassistent.mmk_accept.entity.MmkAcceptRowEntity;

@Repository
public interface MmkAcceptRepository extends JpaRepository<MmkAcceptRowEntity, Long> {

    MmkAcceptRowEntity findFirstBySpecAndPosition(String spec, int position);
}
