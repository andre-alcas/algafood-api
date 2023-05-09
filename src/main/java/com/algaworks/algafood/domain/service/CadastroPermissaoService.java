package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.PermissaoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {

//    private static final String MSG_PERMISSAO_EM_USO
//        = "Permissão de código %d não pode ser removida, pois está em uso";

    @Autowired
    private PermissaoRepository permissaoRepository;

//    @Transactional
//    public Permissao salvar(Permissao permissao) {
//        return permissaoRepository.save(permissao);
//    }

    public Permissao buscarOuFalhar(Long permissaoId) {
        return permissaoRepository.findById(permissaoId)
            .orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
    }
}