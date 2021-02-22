package com.algaworks.oswork.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.oswork.domain.model.Cliente;
import com.algaworks.oswork.domain.repository.ClienteRepository;
import com.algaworks.osworks.domain.exception.NegocioException;

@Service
public class CadastroClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente salvar(Cliente cliente) {
		Cliente clienteExistente = clienteRepository.findByEmail(cliente.getEmail());
		if(clienteExistente != null && clienteExistente.equals(clienteExistente)) {
			throw new NegocioException("ja existe um cliente cadastrado com esse email");
		}

		return clienteRepository.save(cliente);
	}

	public void excluir(Long clienteId) {
		clienteRepository.deleteById(clienteId);
	}
}
