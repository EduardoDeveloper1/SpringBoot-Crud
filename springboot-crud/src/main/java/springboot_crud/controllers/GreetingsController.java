package springboot_crud.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@GetMapping("listartodos")
	public ResponseEntity<List<Usuario>> listaUsuario() {
		List<Usuario> usuarios = usuarioRepository.findAll();

		if (usuarios.isEmpty()) {
			return ResponseEntity.noContent().build(); // Retorna 204 se não houver usuários
		}

		return ResponseEntity.ok(usuarios);
	}

	@PostMapping("salvar")
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {
		// Exemplo de segurança: sanitização e validação já feita com @Valid
		Usuario user = usuarioRepository.save(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}

	@DeleteMapping("delete")
	public ResponseEntity<String> deletar(@PathVariable Long id) {
		if (!usuarioRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
		}

		usuarioRepository.deleteById(id);
		return ResponseEntity.ok("Usuário deletado com sucesso!");
	}

	@GetMapping("buscarPorID")
	public ResponseEntity<Usuario> buscarPorID(@PathVariable Long id) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);

		if (usuario.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok(usuario.get());
	}

	@PutMapping("atualizar")
	@ResponseBody
	public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario) {
		if (usuario.getId() == null) {
			return new ResponseEntity<Usuario>(usuario, (HttpStatus.OK));
		}
		Usuario user = usuarioRepository.saveAndFlush(usuario);
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}

	@GetMapping("buscarPorNome")
	@ResponseBody
	public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "name") String nome) {
		List<Usuario> usuarios = usuarioRepository.buscarPorNome(nome.trim().toUpperCase());

		if (usuarios.isEmpty()) {
			return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
	}
}