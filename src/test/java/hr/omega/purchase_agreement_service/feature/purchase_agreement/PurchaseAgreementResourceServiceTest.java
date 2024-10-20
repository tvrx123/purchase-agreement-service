package hr.omega.purchase_agreement_service.feature.purchase_agreement;

import hr.omega.purchase_agreement_service.feature.purchase_agreement.repository.PurchaseAgreementRepository;
import hr.omega.purchase_agreement_service.feature.purchase_agreement.request.PurchaseAgreementRequest;
import hr.omega.purchase_agreement_service.feature.purchase_agreement.request.StatusChangeRequest;
import hr.omega.purchase_agreement_service.feature.purchase_agreement.response.PurchaseAgreementResponse;
import hr.omega.purchase_agreement_service.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PurchaseAgreementResourceServiceTest {

  @Mock private PurchaseAgreementRepository purchaseAgreementRepository;

  @Mock private PurchaseAgreementMapper purchaseAgreementMapper;

  @InjectMocks private PurchaseAgreementResourceService purchaseAgreementResourceService;

  @Test
  public void testFindById_whenExists() {
    Long id = 1L;
    PurchaseAgreement purchaseAgreement = mock(PurchaseAgreement.class);

    when(purchaseAgreementRepository.findById(id)).thenReturn(Optional.of(purchaseAgreement));

    PurchaseAgreement result = purchaseAgreementResourceService.findById(id);

    assertNotNull(result);
    assertEquals(purchaseAgreement, result);
  }

  @Test
  public void testFindById_whenNotExists() {
    Long id = 1L;

    when(purchaseAgreementRepository.findById(id)).thenReturn(Optional.empty());

    ValidationException exception =
        assertThrows(
            ValidationException.class,
            () -> {
              purchaseAgreementResourceService.findById(id);
            });

    assertEquals("purchaseAgreement.notExists", exception.getMessage());
  }

  @Test
  public void testSave() {
    PurchaseAgreementRequest request = mock(PurchaseAgreementRequest.class);
    PurchaseAgreement purchaseAgreement = mock(PurchaseAgreement.class);
    PurchaseAgreementResponse response = mock(PurchaseAgreementResponse.class);

    LocalDate advanceDate = LocalDate.of(2024, 1, 1);
    LocalDate deliveryTerm = LocalDate.of(2024, 2, 1);

    when(purchaseAgreementMapper.toEntity(request)).thenReturn(purchaseAgreement);
    when(purchaseAgreement.getAdvanceDate()).thenReturn(advanceDate);
    when(purchaseAgreement.getDeliveryTerm()).thenReturn(deliveryTerm);
    when(purchaseAgreementMapper.toResponse(purchaseAgreement)).thenReturn(response);
    when(purchaseAgreementRepository.save(purchaseAgreement)).thenReturn(purchaseAgreement);

    PurchaseAgreementResponse result = purchaseAgreementResourceService.save(request);

    assertNotNull(result);
    verify(purchaseAgreementRepository).save(purchaseAgreement);
    verify(purchaseAgreementMapper).toResponse(purchaseAgreement);
  }

  @Test
  public void testDelete_whenStatusCreated() {
    Long id = 1L;
    PurchaseAgreement purchaseAgreement = mock(PurchaseAgreement.class);

    when(purchaseAgreementRepository.findById(id)).thenReturn(Optional.of(purchaseAgreement));
    when(purchaseAgreement.getStatus()).thenReturn(PurchaseAgreement.Status.CREATED);

    purchaseAgreementResourceService.delete(id);

    verify(purchaseAgreement).setDeletedFlag(true);
    verify(purchaseAgreementRepository).save(purchaseAgreement);
  }

  @Test
  public void testDelete_whenStatusNotCreated() {
    Long id = 1L;
    PurchaseAgreement purchaseAgreement = mock(PurchaseAgreement.class);

    when(purchaseAgreementRepository.findById(id)).thenReturn(Optional.of(purchaseAgreement));
    when(purchaseAgreement.getStatus()).thenReturn(PurchaseAgreement.Status.ORDERED);

    ValidationException exception =
        assertThrows(
            ValidationException.class,
            () -> {
              purchaseAgreementResourceService.delete(id);
            });

    assertEquals("purchaseAgreement.deletionOnlyCreated", exception.getMessage());
  }

  @Test
  public void testChangeStatus_whenInvalidStatus() {
    Long id = 1L;
    StatusChangeRequest statusChangeRequest = mock(StatusChangeRequest.class);
    PurchaseAgreement purchaseAgreement = mock(PurchaseAgreement.class);

    when(purchaseAgreementRepository.findById(id)).thenReturn(Optional.of(purchaseAgreement));
    when(statusChangeRequest.getStatus()).thenReturn("INVALID");

    ValidationException exception =
        assertThrows(
            ValidationException.class,
            () -> {
              purchaseAgreementResourceService.changeStatus(id, statusChangeRequest);
            });

    assertEquals("purchaseAgreement.statusNotValid", exception.getMessage());
  }
}
