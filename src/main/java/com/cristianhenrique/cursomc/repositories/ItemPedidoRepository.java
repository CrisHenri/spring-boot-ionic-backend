package com.cristianhenrique.cursomc.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cristianhenrique.cursomc.domain.ItemPedido;;
//Classe que será capaz de acessar o banco de dados e fazer as consultas nescessárias da tabela cateoria
@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {

}
