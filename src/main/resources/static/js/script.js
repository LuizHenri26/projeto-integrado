const hamBurger = document.querySelector(".toggle-btn");

hamBurger.addEventListener("click", function() {
	document.querySelector("#sidebar").classList.toggle("expand");
});

function formatar(mascara, documento) {
	let i = documento.value.length;
	let saida = '#';
	let texto = mascara.substring(i);
	while (texto.substring(0, 1) != saida && texto.length) {
		documento.value += texto.substring(0, 1);
		i++;
		texto = mascara.substring(i);
	}
}

function preencheCampoAutomaticamente(el) {
	let value = $(el).val();
	$('input[name="membro"]').val(value);
}