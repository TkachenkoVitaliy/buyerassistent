package ru.tkachenko.buyerassistant.email.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tkachenko.buyerassistant.email.entity.MailEntity;

import java.util.List;

@Repository
public interface MailRepository extends JpaRepository<MailEntity, Long> {

    public List<MailEntity> findAllByBranchNameOrderById(String branchName);

    public void deleteById(Long id);

    @Query(value = "SELECT * FROM mail_table ORDER BY branch_name, id;",nativeQuery = true)
    public List<MailEntity> findAllOrderByBranchNameId();
}
