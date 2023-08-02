package com.digitalworlds.grupo2.api.controllers;

import com.digitalworlds.grupo2.api.dtos.DTOCountry;
import com.digitalworlds.grupo2.api.dtos.DTOGenre;
import com.digitalworlds.grupo2.api.services.SVInfo;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api2/info")
@AllArgsConstructor
@Slf4j
public class CTLInfo {

    private SVInfo svInfo;

    @GetMapping("/country")
    @ApiOperation("Obtiene las regiones (ISO 3166-1) y sus respectivas banderas")
    public ResponseEntity<DTOCountry[]> getAllCountries() {
        log.info("<<<<< ---- /country ---- >>>>>");
        ResponseEntity<DTOCountry[]> response = ResponseEntity.ok(svInfo.getAllCountries());
        log.info("<//// ---- /country ---- ////>[OK]");
        return response;
    }

    @GetMapping("/genre")
    @ApiOperation("Obtiene los géneros de cinematográficos")
    public ResponseEntity<DTOGenre[]> getAllGenres() {
        log.info("<<<<< ---- /genre ---- >>>>>");
        ResponseEntity<DTOGenre[]> response = ResponseEntity.ok(svInfo.getAllGenres());
        log.info("<//// ---- /genre ---- ////>[OK]");
        return response;
    }

}
