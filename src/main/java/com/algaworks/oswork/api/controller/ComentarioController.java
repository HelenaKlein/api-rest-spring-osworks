package com.algaworks.oswork.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.oswork.api.model.Comentario;
import com.algaworks.oswork.api.model.ComentarioInput;
import com.algaworks.oswork.api.model.ComentarioModel;
import com.algaworks.oswork.domain.model.OrdemServico;
import com.algaworks.oswork.domain.repository.OrdemServicoRepository;
import com.algaworks.oswork.domain.service.GestaoOrdemServicoService;
import com.algaworks.osworks.domain.exception.EntidadeNaoEncontradaException;

@RestController
@RequestMapping("/ordens-servico/{ordemServicoId}/comentarios")
public class ComentarioController {
	
	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServico;
	
	@Autowired
	OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ComentarioModel adicionar(@PathVariable Long ordemServicoId, @Valid @RequestBody  ComentarioInput comentarioInput) {
		Comentario comentario = gestaoOrdemServico.adicionarComentario(ordemServicoId,comentarioInput.getDescricao());
		return toModel(comentario);
	}
	
	@GetMapping
	public List<ComentarioModel> listar(@PathVariable Long ordemServicoId){
		OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId).orElseThrow(() -> new EntidadeNaoEncontradaException("ordem de servico nao encontrada"));
		
		return toCollectionModel(ordemServico.getComentarios());
		
	}
	
	private ComentarioModel toModel(Comentario comentario) {
		return modelMapper.map(comentario, ComentarioModel.class);
	}
	
	private List<ComentarioModel> toCollectionModel(List<Comentario> comentarios) {
		return comentarios.stream().map(comentario -> toModel(comentario)).collect(Collectors.toList());
	}
}
