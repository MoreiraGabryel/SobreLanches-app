package com.sobrelanches.controller;

import com.sobrelanches.model.Produto;
import com.sobrelanches.model.ProdutoRemovido;
import com.sobrelanches.service.ProdutoService;
import com.sobrelanches.repository.ProdutoRemovidoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ProdutoRemovidoRepository removidoRepository;

    public ProdutoController(ProdutoService produtoService, ProdutoRemovidoRepository removidoRepository) {
        this.produtoService = produtoService;
        this.removidoRepository = removidoRepository;
    }

    @GetMapping
    public String listarProdutos(Model model) {
        List<Produto> produtos = produtoService.listar();
        model.addAttribute("produtos", produtos);
        return "produtos";
    }

    @PostMapping
    public String salvar(@ModelAttribute Produto produto) {
        produtoService.salvar(produto);
        return "redirect:/produtos";
    }

    @PostMapping("/{id}/delete")
    public String deletar(@PathVariable Long id, @RequestParam("motivo") String motivo) {
        produtoService.buscarPorId(id).ifPresent(produto -> {
            ProdutoRemovido removido = new ProdutoRemovido(produto.getId(), produto.getNome(), motivo);
            removidoRepository.save(removido);
            produtoService.deletar(id);
        });
        return "redirect:/produtos";
    }
    @GetMapping("/removidos")
    public String listarRemovidos(Model model) {
        model.addAttribute("removidos", removidoRepository.findAll());
        return "produtosRemovidos";
    }

}
