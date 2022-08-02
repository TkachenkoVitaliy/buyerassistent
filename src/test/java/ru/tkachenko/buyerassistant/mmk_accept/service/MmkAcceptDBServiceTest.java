package ru.tkachenko.buyerassistant.mmk_accept.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tkachenko.buyerassistant.mmk_accept.entity.MmkAcceptRowEntity;
import ru.tkachenko.buyerassistant.mmk_accept.exception.AcceptParseException;
import ru.tkachenko.buyerassistant.mmk_accept.repository.MmkAcceptRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class MmkAcceptDBServiceTest {

    @Mock
    MmkAcceptRepository mockedRepository;

    @InjectMocks
    MmkAcceptDBService mmkAcceptDBService;

    String spec = "qweasd";
    int pos = 2;


    @Test
    void addUniqueEntityWithNullInput() throws AcceptParseException {
        mmkAcceptDBService.addUniqueEntity(null);
        verifyNoInteractions(mockedRepository);
    }

    @Test
    void addUniqueEntityWithEmptyEntity() throws AcceptParseException {
        MmkAcceptRowEntity mmkAcceptRowEntity = new MmkAcceptRowEntity();
        mmkAcceptDBService.addUniqueEntity(mmkAcceptRowEntity);
        verifyNoInteractions(mockedRepository);
    }

    @Test
    void addUniqueEntityWithCorrectEntity() throws AcceptParseException {

        ArgumentCaptor<MmkAcceptRowEntity> mmkAcceptRowEntityArgumentCaptor
                = ArgumentCaptor.forClass(MmkAcceptRowEntity.class);

        MmkAcceptRowEntity mmkAcceptRowEntity = new MmkAcceptRowEntity(
                spec,
                pos,
                "eqwe",
                "trett",
                12.2,
                323.3,
                123.6,
                "yuiyui",
                32.4,
                12,
                "asdfgfg"
        );

        mmkAcceptDBService.addUniqueEntity(mmkAcceptRowEntity);

        verify(mockedRepository).findFirstBySpecAndPosition(mmkAcceptRowEntity.getSpec(),
                mmkAcceptRowEntity.getPosition());

        verify(mockedRepository).save(mmkAcceptRowEntityArgumentCaptor.capture());

        MmkAcceptRowEntity value = mmkAcceptRowEntityArgumentCaptor.getValue();
        assertThat(value).isEqualTo(mmkAcceptRowEntity);
    }

    @Test
    void addUniqueEntityWithNullFieldSpec() throws AcceptParseException {
        MmkAcceptRowEntity mmkAcceptRowEntity = new MmkAcceptRowEntity(
                null,
                pos,
                "eqwe",
                "trett",
                12.2,
                323.3,
                123.6,
                "yuiyui",
                32.4,
                12,
                "asdfgfg"
        );
        assertThatThrownBy(() -> mmkAcceptDBService.addUniqueEntity(mmkAcceptRowEntity))
                .isInstanceOf(AcceptParseException.class)
                .hasMessageContaining("Can't parse AcceptFile because file" +
                        " contains row with Specification number "
                        + "or position number - blank value");
    }

    @Test
    void findEntityBySpecAndPositionWithCorrectInput() {
        mmkAcceptDBService.findEntityBySpecAndPosition(spec, pos);
        verify(mockedRepository).findFirstBySpecAndPosition(spec, pos);
    }
}