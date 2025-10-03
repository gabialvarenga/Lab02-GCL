package lab02.br.locadora.service;

import lab02.br.locadora.dto.AuthResponse;
import lab02.br.locadora.dto.LoginRequest;
import lab02.br.locadora.dto.RegisterRequest;
import lab02.br.locadora.model.*;
import lab02.br.locadora.repository.UsuarioRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            CustomUserDetailsService userDetailsService
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    public AuthResponse register(RegisterRequest request) {
        // Verificar se o email já existe
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        // Criar usuário conforme o tipo
        Usuario usuario;
        switch (request.getRole()) {
            case CLIENTE:
                Cliente cliente = new Cliente();
                cliente.setCpf(request.getCpf());
                cliente.setRg(request.getRg());
                cliente.setEndereco(request.getEndereco());
                cliente.setProfissao(request.getProfissao());
                usuario = cliente;
                break;
            case BANCO:
                Banco banco = new Banco();
                banco.setCnpj(request.getCnpj());
                banco.setRazaoSocial(request.getRazaoSocial());
                banco.setEndereco(request.getEndereco());
                banco.setTelefone(request.getTelefone());
                usuario = banco;
                break;
            case EMPRESA:
                Empresa empresa = new Empresa();
                empresa.setCnpj(request.getCnpj());
                empresa.setRazaoSocial(request.getRazaoSocial());
                empresa.setEndereco(request.getEndereco());
                empresa.setTelefone(request.getTelefone());
                empresa.setAreaAtuacao(request.getAreaAtuacao());
                usuario = empresa;
                break;
            case ATENDENTE:
                // Para atendentes, criar apenas o usuário base
                // Você pode criar uma classe Atendente se necessário
                throw new RuntimeException("Registro de atendentes não implementado");
            default:
                throw new RuntimeException("Tipo de usuário inválido");
        }

        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        usuario.setRole(request.getRole());

        usuarioRepository.save(usuario);

        UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getEmail());
        String jwtToken = jwtService.generateToken(userDetails);

        return new AuthResponse(
                jwtToken,
                usuario.getEmail(),
                usuario.getNome(),
                usuario.getRole().name()
        );
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getEmail());
        String jwtToken = jwtService.generateToken(userDetails);

        return new AuthResponse(
                jwtToken,
                usuario.getEmail(),
                usuario.getNome(),
                usuario.getRole().name()
        );
    }
}
