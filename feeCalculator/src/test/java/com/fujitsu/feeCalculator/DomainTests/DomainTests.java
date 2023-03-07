package com.fujitsu.feeCalculator.DomainTests;

import com.fujitsu.feeCalculator.Domain.Enums.ECityName;
import com.fujitsu.feeCalculator.Domain.Enums.EPhenomenonType;
import com.fujitsu.feeCalculator.Domain.Helpers.EnumLabelMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// Tests for EnumLabelMapper
@SpringBootTest
public class DomainTests {

    @Test
    void testPhenomenonTypeMapper() {
        assertMapperReturns("Fog", EPhenomenonType.FOG);
    }

    @Test
    void testPhenomenonTypeMapperMultipleWordStrings() {
        assertMapperReturns("Cloudy with clear spells", EPhenomenonType.CLOUDY_WITH_CLEAR_SPELLS);
    }

    @Test
    void testPhenomenonTypeMapperIncorrectReturnsNoData() {
        assertMapperReturns("Some nonexistent weather", EPhenomenonType.NO_DATA);
    }

    @Test
    void testPhenomenonTypeMapperNullReturnsNoData() {
        assertMapperReturns(null, EPhenomenonType.NO_DATA);
    }

    @Test
    void testPhenomenonTypeMapperEmptyStringReturnsNoData() {
        assertMapperReturns("", EPhenomenonType.NO_DATA);
    }

    void assertMapperReturns(String input, EPhenomenonType expectedValue) {
        EPhenomenonType generated = EnumLabelMapper.getPhenomenonTypeFromString(input);
        assert (generated).equals(expectedValue);
    }

    @Test
    void testCityMapper(){
        ECityName expected1 = EnumLabelMapper.getCityNameFromString("Tallinn-Harku");
        ECityName expected2 = EnumLabelMapper.getCityNameFromString("Tartu-Tõravere");
        ECityName expected3 = EnumLabelMapper.getCityNameFromString("Pärnu");
        ECityName expected4 = EnumLabelMapper.getCityNameFromString("");

        assert(expected1).equals(ECityName.TALLINN);
        assert(expected2).equals(ECityName.TARTU);
        assert(expected3).equals(ECityName.PARNU);
        assert (expected4) == null;

    }

}
