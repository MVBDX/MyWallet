package ir.mvbdx.mywallet.repository;

import ir.mvbdx.mywallet.model.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

}