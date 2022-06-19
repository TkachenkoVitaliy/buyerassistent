package ru.tkachenko.buyerassistant.summary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.mmk_accept.entity.MmkAcceptRowEntity;
import ru.tkachenko.buyerassistant.mmk_accept.service.MmkAcceptDBService;
import ru.tkachenko.buyerassistant.utils.RegexUtil;

@Service
public class ProfileParser {
    private final MmkAcceptDBService mmkAcceptDBService;
    private final String DELIMITER = "*";
    private final String PLATE = "Лист";
    private final String COIL = "Рулон";
    private final String STRIP = "Лента";
    private final String ANGLE = "Уголок";
    private final String U_CHANNEL = "Швеллер";
    private final String REBAR_COILS = "Профиль арматурный_моток";
    private final String REBAR = "Профиль арматурный";
    private final String ROUND_BAR = "Круг";
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

        if (productType.equals(REBAR)) {
            return parseRebars(spec, position);
        }

        if (productType.contains(ROUND_BAR)) {
            return parseRoundBars(spec, position);
        }

        if (productType.contains(SPECIAL_SECTIONS)) {
            return parseSpecialSections(spec, position);
        }

        return null;
    }

    private String parsePlates(String spec, int position) {
        String result = null;
        MmkAcceptRowEntity acceptEntity = mmkAcceptDBService.findEntityBySpecAndPosition(spec, position);
        if (acceptEntity != null) {
            String thickness = RegexUtil.doubleToString(acceptEntity.getThickness());
            String width = RegexUtil.doubleToString(acceptEntity.getWidth());
            String length = RegexUtil.doubleToString(acceptEntity.getLength());

            result = "" + thickness + DELIMITER + width + DELIMITER
                    + length;
        }
        return RegexUtil.replaceDotToComma(result);
    }

    private String parseCoilsAndStrips(String spec, int position) {
        String result = null;
        MmkAcceptRowEntity acceptEntity = mmkAcceptDBService.findEntityBySpecAndPosition(spec, position);
        if (acceptEntity != null) {
            String thickness = RegexUtil.doubleToString(acceptEntity.getThickness());
            String width = RegexUtil.doubleToString(acceptEntity.getWidth());
            result = "" + thickness + DELIMITER + width;
        }
        return RegexUtil.replaceDotToComma(result);
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
            if (result != null && !result.equals("") && !result.equals(" ")) return RegexUtil.replaceDelimiter(result);

            String additionalRequirements = acceptEntity.getAdditionalRequirements();
            if (additionalRequirements != null && !additionalRequirements.equals("") && !additionalRequirements.equals(" ")) {
                String firstTwoMeasure = RegexUtil.findRegexInTextAndRemoveUnnecessary(additionalRequirements,
                        firstMeasureRegex, firstMeasureRemovedString);
                String thirdMeasure = RegexUtil.findRegexInTextAndRemoveUnnecessary(additionalRequirements,
                        thirdMeasureRegex, thirdMeasureRemovedString);
                String length = RegexUtil.doubleToString(acceptEntity.getLength());
                result = firstTwoMeasure + DELIMITER + firstTwoMeasure + DELIMITER + thirdMeasure + DELIMITER + length;
            }
        }
        return RegexUtil.replaceDotToComma(result);
    }

    private String parseUChannels(String spec, int position) {
        final String profileNumberRegex = "(Номер профиля горячекатаного проката=[0-9.УВП]{1,5})";
        final String stringForRemove = "Номер профиля горячекатаного проката=";

        String result = null;
        MmkAcceptRowEntity acceptEntity = mmkAcceptDBService.findEntityBySpecAndPosition(spec, position);
        if (acceptEntity != null) {
            result = acceptEntity.getAlterProfile();
            if (result != null && !result.equals("") && !result.equals(" ")) return RegexUtil.replaceDelimiter(result);

            String additionalRequirements = acceptEntity.getAdditionalRequirements();
            if (additionalRequirements != null) {
                String profileNumber = RegexUtil.findRegexInTextAndRemoveUnnecessary(additionalRequirements,
                        profileNumberRegex, stringForRemove);
                String length = RegexUtil.doubleToString( acceptEntity.getLength());
                result = profileNumber + DELIMITER + length;
            }
        }
        return RegexUtil.replaceDotToComma(result);
    }

    private String parseRebarCoils(String spec, int position) {
        final String profileDiameterRegex = "(Номер профиля горячекатаного проката=[0-9]{1,2})";
        final String stringForRemove = "Номер профиля горячекатаного проката=";

        String result = null;
        MmkAcceptRowEntity acceptEntity = mmkAcceptDBService.findEntityBySpecAndPosition(spec, position);
        if(acceptEntity != null) {
            result = acceptEntity.getAlterProfile();
            if(result != null && !result.equals("") && !result.equals(" ")) return result;

            String additionalRequirements = acceptEntity.getAdditionalRequirements();
            if (additionalRequirements != null) {
                result = RegexUtil.findRegexInTextAndRemoveUnnecessary(additionalRequirements,
                        profileDiameterRegex,stringForRemove);
            }
        }
        return RegexUtil.replaceDotToComma(result);
    }

    private String parseRebars(String spec, int position) {
        final String profileDiameterRegex = "(Номер профиля горячекатаного проката=[0-9]{1,2})";
        final String stringForRemove = "Номер профиля горячекатаного проката=";

        String result = null;
        MmkAcceptRowEntity acceptEntity = mmkAcceptDBService.findEntityBySpecAndPosition(spec, position);
        if (acceptEntity != null) {
            result = acceptEntity.getAlterProfile();
            if (result != null && !result.equals("") && !result.equals(" ")) return RegexUtil.replaceDelimiter(result);

            String additionalRequirements = acceptEntity.getAdditionalRequirements();
            if (additionalRequirements != null) {
                String profileDiameter = RegexUtil.findRegexInTextAndRemoveUnnecessary(additionalRequirements,
                        profileDiameterRegex, stringForRemove);
                String length = RegexUtil.doubleToString(acceptEntity.getLength());
                result = profileDiameter + DELIMITER + length;
            }
        }
        return RegexUtil.replaceDotToComma(result);
    }

    private String parseRoundBars(String spec, int position) {

        final String diameterRegex = "(Диаметр=[0-9.,]{1,4})";
        final String stringForRemove = "Диаметр=";

        String result = null;
        MmkAcceptRowEntity acceptEntity = mmkAcceptDBService.findEntityBySpecAndPosition(spec, position);
        if(acceptEntity != null) {
            result = acceptEntity.getAlterProfile();
            if (result != null && !result.equals("") && !result.equals(" ")) return RegexUtil.replaceDelimiter(result);

            String additionalRequirements = acceptEntity.getAdditionalRequirements();
            if (additionalRequirements != null) {
                String diameter = RegexUtil.findRegexInTextAndRemoveUnnecessary(additionalRequirements, diameterRegex,
                        stringForRemove);
                if(acceptEntity.getLength() == 0) {
                    result = diameter;
                } else {
                    String length = RegexUtil.doubleToString(acceptEntity.getLength());
                    result = diameter + DELIMITER + length;
                }
            }
        }
        return RegexUtil.replaceDotToComma(result);
    }

    private String parseSpecialSections(String spec, int position) {
        final String profileNumberRegex = "(Номер профиля горячекатаного проката=)([0-9.xх]{1,35})";
        final String stringForRemove = "Номер профиля горячекатаного проката=";

        String result = null;
        MmkAcceptRowEntity acceptEntity = mmkAcceptDBService.findEntityBySpecAndPosition(spec, position);
        if (acceptEntity != null) {
            result = acceptEntity.getAlterProfile();
            if (result != null && !result.equals("") && !result.equals(" ")) return RegexUtil.replaceDelimiter(result);

            String additionalRequirements = acceptEntity.getAdditionalRequirements();
            if (additionalRequirements != null) {
                String profileNumber = RegexUtil.findRegexInTextAndRemoveUnnecessary(additionalRequirements,
                        profileNumberRegex, stringForRemove);
                String length = RegexUtil.doubleToString(acceptEntity.getLength());
                result = profileNumber + DELIMITER + length;
            }
        }
        return RegexUtil.replaceDotToComma(result);
    }

    public String parseAdditionalReq(String spec, int position) {
        MmkAcceptRowEntity acceptEntity = mmkAcceptDBService.findEntityBySpecAndPosition(spec, position);
        if(acceptEntity != null) {
            String additionalReq = acceptEntity.getAdditionalRequirements();
            if (additionalReq != null && !additionalReq.equals("")) {
                return additionalReq;
            }
        }

        return "";
    }
}
