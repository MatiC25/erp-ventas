package com.iconnect.erp_ventas.service;

import com.iconnect.erp_ventas.domain.inventario.CategoriaDispositivo;
import com.iconnect.erp_ventas.domain.inventario.Modelo;
import com.iconnect.erp_ventas.domain.inventario.Producto;
import com.iconnect.erp_ventas.repository.CategoriaDispositivoRepository;
import com.iconnect.erp_ventas.repository.ProductoRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaDispositivoRepository categoriaDispositivoRepository;


    public ProductoService(ProductoRepository productoRepository, CategoriaDispositivoRepository categoriaDispositivoRepository) {
        this.productoRepository = productoRepository;
        this.categoriaDispositivoRepository = categoriaDispositivoRepository;
    }


    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Optional<Producto> buscarProductoPorUPC(String UPC){
        return productoRepository.findByUPC(UPC);
    }

    public List<CategoriaDispositivo> findAllCategoriaDispositivos() {
        return categoriaDispositivoRepository.findAll();
    }

    public List<Producto> findByModelo(Modelo modelo){
        return productoRepository.findByModelo(modelo);
    }

    public List<String> findVariantesByModelo(Modelo modelo){
        return productoRepository.findVariantesByModelo(modelo);
    }

    public List<String> findColoresByModeloAndVariante(Modelo modelo, String variante){
        return productoRepository.findColoresByModeloAndVariante(modelo, variante);
    }

    public Optional<Producto> findByModeloAndVarianteAndColor(Modelo modelo, String variante, String color){
        return productoRepository.findByModeloAndVarianteAndColor(modelo, variante, color);
    }
}
