package hr.omega.purchase_agreement_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"hr.omega.purchase_agreement_service.feature"})
public class PurchaseAgreementServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(PurchaseAgreementServiceApplication.class, args);
  }
}
