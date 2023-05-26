package com.algaworks.algafood.api.v1.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.v1.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.api.v1.openapi.controller.PedidoControllerOpenApi;
import com.algaworks.algafood.core.data.PageWrapper;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecs;

@RestController
//@RequestMapping(value = "/pedidos")
@RequestMapping(path="/v1/pedidos")//,produces= MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Autowired
    private PedidoModelAssembler pedidoModelAssembler;

    @Autowired
    private PedidoResumoModelAssembler pedidoResumoModelAssembler;

    @Autowired
    private PedidoInputDisassembler pedidoInputDisassembler;

    @Autowired
	private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

    @Override
	@GetMapping
    public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro,@PageableDefault(size=10) Pageable pageable) {

    	Pageable pageableTraduzido = traduzirPageable(pageable);

    	//Page<Pedido> todosPedidos = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro),pageable);

    	//List<PedidoResumoModel> pedidosModel = pedidoResumoModelAssembler.toCollectionModel(todosPedidos.getContent());
    	//todosPedidos.getContent() contem uma lista de pedidos paginados

    	//Page<PedidoResumoModel> pedidosModelPage = new PageImpl<>(pedidosModel, pageable, todosPedidos.getTotalElements());

    	Page<Pedido> pedidosPage = pedidoRepository.findAll(
				PedidoSpecs.usandoFiltro(filtro), pageableTraduzido);
    	
		pedidosPage = new PageWrapper<>(pedidosPage, pageable);
		
		return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoModelAssembler);
    }

//  @GetMapping
//  public List<PedidoResumoModel> listar() {
//      List<Pedido> todosPedidos = pedidoRepository.findAll();
//
//      return pedidoResumoModelAssembler.toCollectionModel(todosPedidos);
//  }


//    @GetMapping
//    public MappingJacksonValue listar(@RequestParam(required = false) String filtros) {
//        List<Pedido> todosPedidos = pedidoRepository.findAll();
//
//        List<PedidoResumoModel> pedidosModel = pedidoResumoModelAssembler.toCollectionModel(todosPedidos);
//
//        MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel);
//
//        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//        filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//        //filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept("codigo","valorTotal"));
//
//        if(StringUtils.isNotBlank(filtros)) {
//        	filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(filtros.split(",")));
//        }
//
//		pedidosWrapper.setFilters(filterProvider );
//
//        return pedidosWrapper;
//    }

    @Override
	@GetMapping("/{codigoPedido}")
    public PedidoModel buscar(@PathVariable String codigoPedido) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);

        return pedidoModelAssembler.toModel(pedido);
    }

    @Override
	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = pedidoInputDisassembler.toDomainModel(pedidoInput);

            // TODO pegar usuário autenticado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            novoPedido = emissaoPedido.emitir(novoPedido);

            return pedidoModelAssembler.toModel(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    private Pageable traduzirPageable(Pageable pageable) {
    	var mapeamento = Map.of(
    			"codigo","codigo",
    			"nomeCliente","cliente.nome",
    			"nomeRestaurante","restaurante.nome",
    			"valorTotal","valorTotal"
    			);

    	return PageableTranslator.translate(pageable, mapeamento);
    }
}