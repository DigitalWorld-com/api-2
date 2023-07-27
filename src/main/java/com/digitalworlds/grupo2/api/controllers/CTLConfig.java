package com.digitalworlds.grupo2.api.controllers;

import com.digitalworlds.grupo2.api.dtos.DTOConfigComing;
import com.digitalworlds.grupo2.api.dtos.DTOCountry;
import com.digitalworlds.grupo2.api.dtos.DTOGenre;
import com.digitalworlds.grupo2.api.services.SVConfig;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api2/config")
@AllArgsConstructor
@Slf4j
public class CTLConfig {

    SVConfig svConfig;

    @GetMapping("/country")
    @ApiOperation("Obtiene las regiones (ISO 3166-1) y sus respectivas banderas")
    public ResponseEntity<DTOCountry[]> getAllCountries() {
        log.info("<<<<< ---- /country ---- >>>>>");
        ResponseEntity<DTOCountry[]> response = ResponseEntity.ok(svConfig.getAllCountries());
        log.info("<//// ---- /country ---- ////>[OK]");
        return response;
    }

    @GetMapping("/genre")
    @ApiOperation("Obtiene los géneros de cinematográficos")
    public ResponseEntity<DTOGenre[]> getAllGenres() {
        log.info("<<<<< ---- /genre ---- >>>>>");
        ResponseEntity<DTOGenre[]> response = ResponseEntity.ok(svConfig.getAllGenres());
        log.info("<//// ---- /genre ---- ////>[OK]");
        return response;
    }

    @GetMapping("/coming/{region}")
    @ApiOperation("Obtiene la configuración (de un país) utilizada para obtener estrenos")
    public ResponseEntity<DTOConfigComing> getConfigComing(@ApiParam(value = "Ej. AR, ES, US, etc.", example = "AR") @PathVariable String region) {
        log.info("<<<<< ---- [GET]/coming/{region} ---- >>>>>");
        ResponseEntity<DTOConfigComing> response = ResponseEntity.ok(svConfig.getConfigComing(region));
        log.info("<//// ---- [GET]/coming/{region} ---- ////>[OK]");
        return response;
    }

    @PostMapping("/coming/{region}")
    @ApiOperation("Crea una configuración (para un país) utilizada para obtener los estrenos")
    public ResponseEntity postConfigComing(@RequestBody DTOConfigComing dtoConfigComing) {
        log.info("<<<<< ---- [POST]/coming/{region} ---- >>>>>");
        ResponseEntity<DTOConfigComing> response = new ResponseEntity(svConfig.postConfigComing(dtoConfigComing), HttpStatus.CREATED);
        log.info("<//// ---- [POST]/coming/{region} ---- ////>[OK]");
        return response;
    }

    @PutMapping("/coming/{region}")
    @ApiOperation("Actualiza la configuración (de un país) utilizada para obtener los estrenos")
    public ResponseEntity putConfigComing(@RequestBody DTOConfigComing dtoConfigComing) {
        log.info("<<<<< ---- [PUT]/coming/{region} ---- >>>>>");
        ResponseEntity<DTOConfigComing> response = ResponseEntity.ok(svConfig.putConfigComing(dtoConfigComing));
        log.info("<//// ---- [PUT]/coming/{region} ---- ////>[OK]");
        return response;
    }

    @DeleteMapping("/coming/{region}")
    @ApiOperation("Elimina la configuración (de un país) utilizada para obtener los estrenos")
    public ResponseEntity deleteGreeting(@PathVariable("region") String region) {
        log.info("<<<<< ---- [PUT]/coming/{region} ---- >>>>>");
        ResponseEntity<DTOConfigComing> response = new ResponseEntity(svConfig.deleteConfigComing(region), HttpStatus.NO_CONTENT);
        log.info("<//// ---- [PUT]/coming/{region} ---- ////>[OK]");
        return response;
    }

}
