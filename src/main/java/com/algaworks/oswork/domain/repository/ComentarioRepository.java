package com.algaworks.oswork.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.oswork.api.model.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long>{
	

}
