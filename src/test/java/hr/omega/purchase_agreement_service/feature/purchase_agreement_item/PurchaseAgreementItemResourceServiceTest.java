package hr.omega.purchase_agreement_service.feature.purchase_agreement_item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import hr.omega.purchase_agreement_service.feature.purchase_agreement.PurchaseAgreement;
import hr.omega.purchase_agreement_service.feature.purchase_agreement_item.response.PurchaseAgreementItemResponse;
import hr.omega.purchase_agreement_service.validation.ValidationException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PurchaseAgreementItemResourceServiceTest {

    @Mock
    private PurchaseAgreementItemRepository purchaseAgreementItemRepository;

    @Mock
    private PurchaseAgreementItemMapper purchaseAgreementItemMapper;

    @InjectMocks
    private PurchaseAgreementItemResourceService purchaseAgreementItemResourceService;

    @Test
    void testGetDetailsByPurchaseAgreementId() {
        Long purchaseAgreementId = 1L;
        List<PurchaseAgreementItem> purchaseAgreementItems = List.of(mock(PurchaseAgreementItem.class));
        List<PurchaseAgreementItemResponse> expectedResponses = List.of(mock(PurchaseAgreementItemResponse.class));

        when(purchaseAgreementItemRepository.findByPurchaseAgreement_id(purchaseAgreementId)).thenReturn(purchaseAgreementItems);
        when(purchaseAgreementItemMapper.toResponse(purchaseAgreementItems)).thenReturn(expectedResponses);

        List<PurchaseAgreementItemResponse> result = purchaseAgreementItemResourceService.getDetailsByPurchaseAgreementId(purchaseAgreementId);

        assertEquals(expectedResponses, result);
        verify(purchaseAgreementItemRepository).findByPurchaseAgreement_id(purchaseAgreementId);
        verify(purchaseAgreementItemMapper).toResponse(purchaseAgreementItems);
    }
    @Test
    void testFindByIdFound() {
        Long id = 1L;
        PurchaseAgreementItem item = mock(PurchaseAgreementItem.class);

        when(purchaseAgreementItemRepository.findById(id)).thenReturn(Optional.of(item));

        PurchaseAgreementItem result = purchaseAgreementItemResourceService.findById(id);

        assertEquals(item, result);
        verify(purchaseAgreementItemRepository).findById(id);
    }

    @Test
    void testFindByIdNotFound() {
        Long id = 1L;

        when(purchaseAgreementItemRepository.findById(id)).thenReturn(Optional.empty());

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            purchaseAgreementItemResourceService.findById(id);
        });
        assertEquals("purchaseAgreementItem.notExists", exception.getMessage());
        verify(purchaseAgreementItemRepository).findById(id);
    }

    @Test
    void testDeleteInvalidPurchaseAgreementStatus() {
        Long id = 1L;
        PurchaseAgreementItem item = mock(PurchaseAgreementItem.class);
        PurchaseAgreement mockPurchaseAgreement = mock(PurchaseAgreement.class);

        when(purchaseAgreementItemRepository.findById(id)).thenReturn(Optional.of(item));
        when(item.getPurchaseAgreement()).thenReturn(mockPurchaseAgreement);
        when(mockPurchaseAgreement.getStatus()).thenReturn(PurchaseAgreement.Status.DELIVERED);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            purchaseAgreementItemResourceService.delete(id);
        });
        assertEquals("purchaseAgreementItem.modificationOnlyCreatedAgreement", exception.getMessage());
    }
}