package ru.tkachenko.buyerassistent.summary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistent.mmk_accept.entity.MmkAcceptRowEntity;
import ru.tkachenko.buyerassistent.mmk_accept.service.MmkAcceptDBService;
import ru.tkachenko.buyerassistent.utils.RegexUtil;

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
        if (productType.contains(PLATE)) {
            return parsePlates(spec, position);
        }

        if (productType.contains(COIL) || productType.contains(STRIP)) {
            return parseCoilsAndStrips(spec, position);
        }

        if (productType.contains(ANGLE)) {
            return parseAngles(spec, position);
        }

        if (productType.contains(U_CHANNEL)) {
            return parseUChannels(spec, position);
        }

        if (productType.contains(REBAR_COILS)) {
            return parseRebarCoils(spec, position);
        }

        //TODO realize default method for non-standart productType

        //TODO return String
        return null;
    }

    private String parsePlates(String spec, int position) {
        String result = null;
        MmkAcceptRowEntity acceptEntity = mmkAcceptDBService.findEntityBySpecAndPosition(spec, position);
        if (acceptEntity != null) {
            result = "" + acceptEntity.getThickness() + DELIMITER + acceptEntity.getWidth() + DELIMITER
                    + acceptEntity.getLength();
        }
        return result;
    }

    private String parseCoilsAndStrips(String spec, int position) {
        String result = null;
        MmkAcceptRowEntity acceptEntity = mmkAcceptDBService.findEntityBySpecAndPosition(spec, position);
        if (acceptEntity != null) {
            result = "" + acceptEntity.getThickness() + DELIMITER + acceptEntity.getWidth();
        }
        return result;
    }

    private String parseAngles(String spec, int position) {
        final String firstMeasureRegex = "(Ширина полки=[0-9]{1,3})";
        final String firstMeasureRemovedString = "Ширина полки=";
        final String thirdMeasureRegex = "(Толщина полки профиля=[0-9]{1,2})";
        final String thirdMeasureRemovedString = "Толщина полки профиля=";

        String result = null;
        MmkAcceptRowEntity acceptEntity = mmkAcceptDBService.findEntityBySpecAndPosition(spec, position);
        if (acceptEntity != null) {
            result = acceptEntity.getAlterProfile();
            if (result != null) return RegexUtil.replaceDelimiter(result);

            String additionalRequirements = acceptEntity.getAdditionalRequirements();
            if (additionalRequirements != null) {
                String firstTwoMeasure = RegexUtil.findRegexInTextAndRemoveUnnecessary(additionalRequirements,
                        firstMeasureRegex, firstMeasureRemovedString);
                String thirdMeasure = RegexUtil.findRegexInTextAndRemoveUnnecessary(additionalRequirements,
                        thirdMeasureRegex, thirdMeasureRemovedString);
                String length = String.valueOf(acceptEntity.getLength());
                result = firstTwoMeasure + DELIMITER + firstTwoMeasure + DELIMITER + thirdMeasure + DELIMITER + length;
            }
        }
        return result;
    }

    private String parseUChannels(String spec, int position) {
        final String profileNumberRegex = "(Номер профиля горячекатаного проката=)([0-9.УВП]{1,5})";
        final String stringForRemove = "Номер профиля горячекатаного проката=";

        String result = null;
        MmkAcceptRowEntity acceptEntity = mmkAcceptDBService.findEntityBySpecAndPosition(spec, position);
        if (acceptEntity != null) {
            result = acceptEntity.getAlterProfile();
            if (result != null) return RegexUtil.replaceDelimiter(result);

            String additionalRequirements = acceptEntity.getAdditionalRequirements();
            if (additionalRequirements != null) {
                String profileNumber = RegexUtil.findRegexInTextAndRemoveUnnecessary(additionalRequirements,
                        profileNumberRegex, stringForRemove);
                String length = String.valueOf(acceptEntity.getLength());
                result = profileNumber + DELIMITER + length;
            }
        }
        return result;
    }

    private String parseRebarCoils(String spec, int position) {

        String result = null;



        return result;
    }
}
