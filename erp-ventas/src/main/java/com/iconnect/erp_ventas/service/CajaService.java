package com.iconnect.erp_ventas.service;

import com.iconnect.erp_ventas.domain.core.Moneda;
import com.iconnect.erp_ventas.domain.finanzas.Caja;
import com.iconnect.erp_ventas.repository.CajaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CajaService {

    private final CajaRepository cajaRepository;

    public CajaService(CajaRepository cajaRepository) {
        this.cajaRepository = cajaRepository;
    }

    public List<Caja> findAll() {
        return cajaRepository.findAll();
    }

    public List<Caja> findByMoneda(Moneda moneda) {
        return cajaRepository.findByMoneda(moneda);
    }

}
