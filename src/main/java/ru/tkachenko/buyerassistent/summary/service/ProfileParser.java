package ru.tkachenko.buyerassistent.summary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistent.mmk_accept.entity.MmkAcceptRowEntity;
import ru.tkachenko.buyerassistent.mmk_accept.service.MmkAcceptDBService;

@Service
public class ProfileParser {
    private final MmkAcceptDBService mmkAcceptDBService;
    private final String DELIMITER = "*";
    private final String PLATE = "Лист";
    private final String COIL = "Рулон";
    private final String STRIP = "Лента";
    private final String ANGLE = "Уголок";
    private final String U_CHANNEL = "Швеллер";
    private final String ROUND_BAR = "Круг";
    private final String REBAR = "Профиль арматурный";
    private final String REBAR_COILS = "Профиль арматурный_моток";
    private final String SPECIAL_SECTIONS = "Спецпрофиль";


    @Autowired
    public ProfileParser(MmkAcceptDBService mmkAcceptDBService) {
        this.mmkAcceptDBService = mmkAcceptDBService;
    }

    public String parse(String productType, String spec, int position) {
        if(productType.contains(PLATE)) {
            return parsePlates(spec, position);
        }

        if(productType.contains(COIL) || productType.contains(STRIP)) {
            return parseCoilsAndStrips(spec, position);
        }

        if(productType.contains(ANGLE)) {

        }

        //TODO realize default method for non-standart productType

        //TODO return String
        return null;
    }

    private String parsePlates(String spec, int position) {
        String result = null;
        MmkAcceptRowEntity acceptEntity = mmkAcceptDBService.findEntityBySpecAndPosition(spec, position);
        if(acceptEntity != null) {
            result = "" + acceptEntity.getThickness() + DELIMITER + acceptEntity.getWidth() + DELIMITER
                    + acceptEntity.getLength();
        }
        return result;
    }

    private String parseCoilsAndStrips(String spec, int position) {
        String result = null;
        MmkAcceptRowEntity acceptEntity = mmkAcceptDBService.findEntityBySpecAndPosition(spec, position);
        if(acceptEntity != null) {
            result = "" + acceptEntity.getThickness() + DELIMITER + acceptEntity.getWidth();
        }
        return result;
    }

    private String parseAngles (String spec, int position) {
        String result = null;
        MmkAcceptRowEntity acceptEntity = mmkAcceptDBService.findEntityBySpecAndPosition(spec, position);
    }

}
