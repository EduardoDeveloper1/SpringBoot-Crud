package springboot_crud.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

	@PostMapping(value = "salvar") // Mapeia a URL
	@ResponseBody // Descrição da resposta
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) { // Recebe os dados parasalvar

		Usuario user = usuarioRepository.save(usuario);
		return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "delete") // Mapeia a URL
	@ResponseBody // Descrição da resposta
	public ResponseEntity<String> deletar(@RequestParam Long id) { // Recebe os dados parasalvar

		usuarioRepository.deleteById(id);
		return new ResponseEntity<String>("Usuario deletado com sucesso!", HttpStatus.OK);
	}
}
