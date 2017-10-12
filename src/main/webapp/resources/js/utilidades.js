
function showMessage(){
	   alert("Hello World!");	
	}

function proximoCampo(atual,proximo){  
 var campo = atual.value.split('_').join('');
 var componente = document.getElementById(proximo);
  if(campo.length >= atual.maxLength){ 
     componente.focus();
   }
}

function meuMouseOver(){
    var div = document.getElementById('meulog');
    div.innerHTML = div.innerHTML + 'Você entrou no campo cep ';
}
 
function meuMouseOut(){
    var div = document.getElementById('meulog');
    div.innerHTML = div.innerHTML + 'Você saiu do campo cep ';
}
