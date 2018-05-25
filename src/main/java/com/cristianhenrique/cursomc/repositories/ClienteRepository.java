package com.cristianhenrique.cursomc.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cristianhenrique.cursomc.domain.Cliente;
//Classe que será capaz de acessar o banco de dados e fazer as consultas nescessárias da tabela cateoria
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	@Transactional(readOnly=true)
	Cliente findByEmail(String email);
}
