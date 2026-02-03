package com.sobrelanches.controller;

import com.sobrelanches.model.Cliente;
import com.sobrelanches.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/cadastro")
    public String cadastroForm(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cadastroClientes";
    }

    @PostMapping("/cadastro")
    public String cadastrar(@ModelAttribute Cliente cliente, Model model) {
        cliente.setEmail(cliente.getEmail().toLowerCase());
        cliente.setSenha(passwordEncoder.encode(cliente.getSenha()));
        clienteRepository.save(cliente);
        model.addAttribute("sucesso", "Cadastro realizado com sucesso. Faça login.");
        return "login";
    }

    @GetMapping("/esqueci-senha")
    public String esqueciSenhaForm() {
        return "esqueciSenha";
    }

    @PostMapping("/esqueci-senha")
    public String processaEsqueciSenha(@RequestParam String email, Model model) {
        model.addAttribute("mensagem", "Se o e-mail existir, instruções foram enviadas.");
        return "login";
    }
}
