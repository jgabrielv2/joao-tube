package br.com.joaotube.service;

import br.com.joaotube.dto.CategoriaInputDto;
import br.com.joaotube.dto.CategoriaResponseDto;
import br.com.joaotube.model.Categoria;
import br.com.joaotube.repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public CategoriaResponseDto criar(CategoriaInputDto input) {
        Categoria c = new Categoria();
        c.setTitulo(input.titulo());
        c.setCor(input.cor());
        categoriaRepository.save(c);
        return new CategoriaResponseDto(c.getId(), c.getTitulo(), c.getCor());
    }

    public List<CategoriaResponseDto> exibirTodos() {
        return categoriaRepository.findAll().stream().map(
                c -> new CategoriaResponseDto(c.getId(), c.getTitulo(), c.getCor())).toList();

    }

    public CategoriaResponseDto exibirPorId(Long id) {
        Categoria c = categoriaRepository.findById(id).orElseThrow(() -> new CategoriaNotFoundException("Não encontrado"));
        return new CategoriaResponseDto(c.getId(), c.getTitulo(), c.getCor());
    }

    @Transactional
    public CategoriaResponseDto editar(Long id, CategoriaInputDto input) {
        Categoria c = categoriaRepository.findById(id).orElseThrow(() -> new CategoriaNotFoundException("Não encontrado"));
        if (input.titulo() != null) {
            c.setTitulo(input.titulo());
        }
        if (input.cor() != null) {
            c.setCor(input.cor());
        }
        categoriaRepository.save(c);
        return new CategoriaResponseDto(c.getId(), c.getTitulo(), c.getCor());
    }

    @Transactional
    public void excluir(Long id) {
        var categoria = categoriaRepository.findById(id).orElseThrow(() -> new CategoriaNotFoundException("Categoria não encontrado com id " + id));
        categoriaRepository.delete(categoria);
    }

}
}
