package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public PedidoModelAssembler() {
		super(PedidoController.class,PedidoModel.class);
	}

	@Override
	public PedidoModel toModel(Pedido pedido) {
	    PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
	    modelMapper.map(pedido, pedidoModel);
	    
	    pedidoModel.add(algaLinks.linkToPedidos("pedidos"));
	    if(pedido.podeSerConfirmado()) {
	    	pedidoModel.add(algaLinks.linkToConfirmacaoPedido(pedido.getCodigo(),"confirmar"));
	    	pedidoModel.add(algaLinks.linkToConfirmacaoSMSPedido(pedido.getCodigo(),"confirmarSMS"));
	    }
	    if(pedido.podeSerCancelado()) {
	    	 pedidoModel.add(algaLinks.linkToCancelamentoPedido(pedido.getCodigo(),"cancelar"));
	    }
	    if(pedido.podeSerEntregue()) {
	    	pedidoModel.add(algaLinks.linkToEntregaPedido(pedido.getCodigo(),"entregar"));
	    }
	    
	    pedidoModel.getRestaurante().add(
	            algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));
	    
	    pedidoModel.getCliente().add(
	            algaLinks.linkToUsuario(pedido.getCliente().getId()));
	    
	    pedidoModel.getFormaPagamento().add(
	            algaLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
	    
	    pedidoModel.getEnderecoEntrega().getCidade().add(
	            algaLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));
	    
	    pedidoModel.getItens().forEach(item -> {
	        item.add(algaLinks.linkToProduto(
	                pedidoModel.getRestaurante().getId(), item.getProdutoId(), "produto"));
	    });
	    
	    return pedidoModel;
	}

//	public List<PedidoModel> toCollectionModel(Collection<Pedido> pedidos) {
//		return pedidos.stream()
//				.map(pedido -> toModel(pedido))
//				.collect(Collectors.toList());
//	}
}
