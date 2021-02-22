package com.algaworks.oswork.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.oswork.api.model.Comentario;
import com.algaworks.oswork.domain.model.Cliente;
import com.algaworks.oswork.domain.model.OrdemServico;
import com.algaworks.oswork.domain.model.StatusOrdemServico;
import com.algaworks.oswork.domain.repository.ClienteRepository;
import com.algaworks.oswork.domain.repository.ComentarioRepository;
import com.algaworks.oswork.domain.repository.OrdemServicoRepository;
import com.algaworks.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.osworks.domain.exception.NegocioException;

@Service
public class GestaoOrdemServicoService {
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	ComentarioRepository comentarioRepository;
	
	public OrdemServico criar(OrdemServico ordemServico) {
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId()).orElseThrow(() -> new NegocioException("Cliente nao encontrado"));
		
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		ordemServico.setCliente(cliente);
		return ordemServicoRepository.save(ordemServico);
	}
	
	public void finalizar(Long ordemServicoId) {
		OrdemServico ordemServico = buscar(ordemServicoId);
		ordemServico.finalizar();
		ordemServicoRepository.save(ordemServico);
	}

	
	public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
		OrdemServico ordemServico = buscar(ordemServicoId);
		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);
		
		return comentarioRepository.save(comentario);
	}
	

	private OrdemServico buscar(Long ordemServicoId) {
		return ordemServicoRepository.findById(ordemServicoId).orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de servico nao encontrada"));
	}
}
