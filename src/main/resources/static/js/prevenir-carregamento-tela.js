document.getElementById("botao-salvar").addEventListener("click", function(event){
	event.preventDefault();
	
	alert("O botão foi clicado, mas o formulário não foi enviado!")
	
	document.getElementById("formLivro").submit();
});