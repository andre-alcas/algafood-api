package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;


@ControllerAdvice //anotacao que faz com que todas as exceptions sejam tratadas por aqui, centralizacao
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	public static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. "
	        + "Tente novamente e se o problema persistir, entre em contato "
	        + "com o administrador do sistema.";
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler({ ValidacaoException.class })
	public ResponseEntity<Object> handleValidacaoException(ValidacaoException ex, WebRequest request) {
	    return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(), 
	            HttpStatus.BAD_REQUEST, request);
	}
	
	private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		        
		    ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		    String detail = "Um ou mais campos est??o inv??lidos. Fa??a o preenchimento correto e tente novamente.";
		    
		    List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream()
		            .map(objectError -> {
		                String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
		                
		                String name = objectError.getObjectName();
		                
		                if (objectError instanceof FieldError) {
		                    name = ((FieldError) objectError).getField();
		                }
		                
		                return Problem.Object.builder()
		                    .name(name)
		                    .userMessage(message)
		                    .build();
		            })
		            .collect(Collectors.toList());
		    
		    Problem problem = createProblemBuilder(status, problemType, detail)
		        .userMessage(detail)
		        .objects(problemObjects)
		        .build();
		    
		    return handleExceptionInternal(ex, problem, headers, status, request);
		}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	        HttpHeaders headers, HttpStatus status, WebRequest request) {
	    return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
	    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;		
	    ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
	    String detail = MSG_ERRO_GENERICA_USUARIO_FINAL;

	    // Importante colocar o printStackTrace (pelo menos por enquanto, que n??o estamos
	    // fazendo logging) para mostrar a stacktrace no console
	    // Se n??o fizer isso, voc?? n??o vai ver a stacktrace de exceptions que seriam importantes
	    // para voc?? durante, especialmente na fase de desenvolvimento
	    ex.printStackTrace();
	    
	    Problem problem = createProblemBuilder(status, problemType, detail).build();

	    return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}  
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(
			NoHandlerFoundException ex, HttpHeaders headers,
	        HttpStatus status, WebRequest request) {

	    ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;

	    String detail = String.format("O recurso %s, que voc?? tentou acessar ?? inexistente.", 
	            ex.getRequestURL());

	    Problem problem = createProblemBuilder(status, problemType, detail)
	    		.userMessage(detail)
	    		.build();

	    return handleExceptionInternal(ex, problem, headers, status, request);
	}
	         // 1. MethodArgumentTypeMismatchException ?? um subtipo de TypeMismatchException

			// 2. ResponseEntityExceptionHandler j?? trata TypeMismatchException de forma mais abrangente
			
			// 3. Ent??o, especializamos o m??todo handleTypeMismatch e verificamos se a exception
			//    ?? uma inst??ncia de MethodArgumentTypeMismatchException
			
			// 4. Se for, chamamos um m??todo especialista em tratar esse tipo de exception
			
			// 5. Poder??amos fazer tudo dentro de handleTypeMismatch, mas preferi separar em outro m??todo
		@Override
		protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
		        HttpStatus status, WebRequest request) {
		    
		    if (ex instanceof MethodArgumentTypeMismatchException) {
		        return handleMethodArgumentTypeMismatch(
		                (MethodArgumentTypeMismatchException) ex, headers, status, request);
		    }

		    return super.handleTypeMismatch(ex, headers, status, request);
		}

		private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
		        MethodArgumentTypeMismatchException ex, HttpHeaders headers,
		        HttpStatus status, WebRequest request) {

		    ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;

		    String detail = String.format("O par??metro de URL '%s' recebeu o valor '%s', "
		            + "que ?? de um tipo inv??lido. Corrija e informe um valor compat??vel com o tipo %s.",
		            ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

		    Problem problem = createProblemBuilder(status, problemType, detail)
		    		.userMessage(detail)
		    		.build();

		    return handleExceptionInternal(ex, problem, headers, status, request);
		}
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		
		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		}else if(rootCause instanceof PropertyBindingException ) {
			return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
		}
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = "O corpo da requisi????o est?? inv??lido. Verifique erro de sintaxe.";
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
	
		// Criei o m??todo joinPath para reaproveitar em todos os m??todos que precisam
		// concatenar os nomes das propriedades (separando por ".")
		String path = joinPath(ex.getPath());
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format("A propriedade '%s' n??o existe. "
				+ "Corrija ou remova essa propriedade e tente novamente.", path);

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String path = joinPath(ex.getPath());
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format("A propriedade '%s' recebeu o valor '%s', "
				+ "que ?? de um tipo inv??lido. Corrija e informe um valor compat??vel com o tipo %s.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		String detail = ex.getMessage();
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();
				
//		Problem problem = Problem.builder()
//				.status(status.value())
//				.type("https://algafood.com.br/entidade-nao-encontrada")
//				.title("Entidade n??o encontrada")
//				.detail(detail)
//				.build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
//		Problema problema = Problema.builder()
//				.datahora(LocalDateTime.now())
//				.mensagem(e.getMessage()).build();
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problema);
	}
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		String detail = ex.getMessage();
		ProblemType problemType = ProblemType.ERRO_NEGOCIO;
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
		//return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
//		Problema problema = Problema.builder()
//				.datahora(LocalDateTime.now())
//				.mensagem(e.getMessage()).build();
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problema);
	}
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request){
		HttpStatus status = HttpStatus.CONFLICT;
		String detail = ex.getMessage();
		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
		//return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
//		Problema problema = Problema.builder()
//				.datahora(LocalDateTime.now())
//				.mensagem(e.getMessage()).build();
//		return ResponseEntity.status(HttpStatus.CONFLICT).body(problema);
	}
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (body == null) {
			body = Problem.builder()
					.title(status.getReasonPhrase())
					.status(status.value())
					.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
					.timestamp(OffsetDateTime.now())
					.build();
		} else if (body instanceof String) {
			body = Problem.builder()
					.title((String) body)
					.status(status.value())
					.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
					.timestamp(OffsetDateTime.now())
					.build();
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
		
		return Problem.builder()
				.status(status.value())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.detail(detail)
				.timestamp(OffsetDateTime.now());
	}
	private String joinPath(List<Reference> references) {
		return references.stream()
			.map(ref -> ref.getFieldName())
			.collect(Collectors.joining("."));
	}
}