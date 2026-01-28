package com.iconnect.erp_ventas.service;

import com.iconnect.erp_ventas.domain.inventario.Producto;
import com.iconnect.erp_ventas.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }
}
