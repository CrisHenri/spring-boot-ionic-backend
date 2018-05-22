package com.cristianhenrique.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cristianhenrique.cursomc.domain.Cidade;
import com.cristianhenrique.cursomc.domain.Cliente;
import com.cristianhenrique.cursomc.domain.Endereco;
import com.cristianhenrique.cursomc.domain.enums.TipoCliente;
import com.cristianhenrique.cursomc.dto.ClienteDTO;
import com.cristianhenrique.cursomc.dto.ClienteNewDTO;
import com.cristianhenrique.cursomc.repositories.ClienteRepository;
import com.cristianhenrique.cursomc.repositories.EnderecoRepository;
import com.cristianhenrique.cursomc.services.exceptions.DataIntegrityException;
import com.cristianhenrique.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	//anotação para instanciar automaticamente pelo spring
	@Autowired
	private ClienteRepository repo;
	
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	//pegar a categoria por código
	public Cliente find(Integer id) {
		//objeto contaniner que vai carregar o objeto que for passado do tipo <Cliente>
		//encapsular se objeto está encapsulado ou não. Java 8 para eliminar o NullPointerException
		Optional<Cliente> obj =  repo.findById(id);
		//retorna o objeto ou o nullo
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: "+id
											+", Tipo: "+Cliente.class.getName()));
	}
	
	//Serviço de inserção de dados
		@Transactional
		public Cliente insert (Cliente obj) {
			//garantir que é um novo objeto será salvo para não considerar uma atualização
			obj.setId(null);
			obj = repo.save(obj);
			enderecoRepository.saveAll(obj.getEnderecos());
			return obj;
		}
	
	
	public Cliente update (Cliente obj) {
		Cliente  newObj = find(obj.getId());
		updateData(newObj, obj);
		
		return repo.save(newObj);
	}


	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
		}
	}
	//padrão DTO Data Trasnferin object OBJETO DE TRANSFERENCIA DE DADOS
	public List<Cliente> findAll() {
		
		return repo.findAll();
		
	}
	
	//Esquema de paginação de todos os registros
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction ){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);    
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli =  new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end =  new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		
		if(objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if(objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		
		return cli;
	}
	

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		
	}
}
