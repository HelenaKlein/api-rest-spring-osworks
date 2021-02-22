package com.algaworks.oswork.api.controller;



import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.oswork.domain.model.Cliente;
import com.algaworks.oswork.domain.repository.ClienteRepository;
import com.algaworks.oswork.domain.service.CadastroClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	
	@Autowired
	private CadastroClienteService cadastroCliente;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@GetMapping
	public List<Cliente> listar() {
		
		return clienteRepository.findAll();
	}
	

	@GetMapping("/{clienteId}")
	public Cliente buscar(@PathVariable Long clienteId) {
		Optional<Cliente> cliente = clienteRepository.findById(clienteId); 
		
		return cliente.orElse(null);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar (@Valid @RequestBody Cliente cliente) {
		return cadastroCliente.salvar(cliente);
	}
	
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar (@Valid @PathVariable Long clienteId, @RequestBody Cliente cliente){
		if(!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		cliente.setId(clienteId);
		cliente = cadastroCliente.salvar(cliente);
		return ResponseEntity.ok(cliente);
	}
	
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> remover(@PathVariable Long clienteId){
		
	
		if(!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		
		cadastroCliente.excluir(clienteId);
		
		return ResponseEntity.noContent().build();
	}
	
	
	
}
