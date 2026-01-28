package com.iconnect.erp_ventas.service;

import com.iconnect.erp_ventas.domain.usuarios.Cliente;
import com.iconnect.erp_ventas.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;

    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }
}
