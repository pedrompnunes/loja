package tw.loja.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tw.loja.data.Permissao;
import tw.loja.data.Utilizador;
import tw.loja.repository.PermissaoRepository;
import tw.loja.repository.UtilizadorRepository;

@Service
public class UtilizadorService {
    @Autowired
    private UtilizadorRepository utilizadorRepository;
    @Autowired
    private PermissaoRepository permissaoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean createUtilizador(String username, String password, String email) {
        if(this.utilizadorRepository.findByUsername(username) != null)
            return false;

        if(password.isEmpty())
            return false;

        Utilizador novo = new Utilizador();
        novo.setUsername(username);
        novo.setPassword(this.passwordEncoder.encode(password));
        novo.setEmail(email);
        novo.setEnable(true);

        this.utilizadorRepository.save(novo);

        Permissao permissao = new Permissao();

        permissao.setCargo("USER");
        permissao.setUsername(username);
        this.permissaoRepository.save(permissao);
        return true;
    }
}