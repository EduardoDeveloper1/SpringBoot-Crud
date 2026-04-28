package springboot_crud.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import springboot_crud.Repository.UsuarioRepository;
import springboot_crud.model.Usuario;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping("/{name}")
	public String greetingText(@PathVariable String name) {
		return "Hello World! " + name + "!";
	}

	@GetMapping("/olamundo/{nome}")
	public String retornaTexto(@PathVariable String nome) {

		Usuario usuario = new Usuario();
		usuario.setNome(nome);

		usuarioRepository.save(usuario);

		return "Olá mundo: " + nome;
	}

	@GetMapping("/listartodos")
	public ResponseEntity<List<Usuario>> listaUsuario() {

		List<Usuario> usuarios = usuarioRepository.findAll();

		return ResponseEntity.ok(usuarios);
	}
}
