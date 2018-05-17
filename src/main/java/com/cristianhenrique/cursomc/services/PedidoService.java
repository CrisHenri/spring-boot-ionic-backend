package com.cristianhenrique.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cristianhenrique.cursomc.domain.Pedido;
import com.cristianhenrique.cursomc.repositories.PedidoRepository;
import com.cristianhenrique.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	//anotação para instanciar automaticamente pelo spring
	@Autowired
	private PedidoRepository repo;
	//pegar a categoria por código
	public Pedido find(Integer id) {
		//objeto contaniner que vai carregar o objeto que for passado do tipo <Pedido>
		//encapsular se objeto está encapsulado ou não. Java 8 para eliminar o NullPointerException
		Optional<Pedido> obj =  repo.findById(id);
		//retorna o objeto ou o nullo
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: "+id
											+", Tipo: "+Pedido.class.getName()));
	}

}
