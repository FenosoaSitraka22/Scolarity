package org.scolarity.entities;


import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
	@Query("select e from  Etudiant e where e.nom like :x")
	public Page<Etudiant> findStudent(@Param("x") String mc,Pageable p);
	@Query("select e from Etudiant e where e.dateNaissance > :x and e.dateNaissance < :y ")
	public Page<Etudiant> cherecheEtudiantEntreDates(@Param("x") Date d1, @Param("y") Date d2, Pageable p);

}
