package br.com.empresa.padaria.validations;

import br.com.empresa.padaria.repositories.CategoryRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class NameConstraintImpl implements ConstraintValidator<NameConstraint, String> {

   @Autowired
   private CategoryRepository repository;

    @Override
    public void initialize(NameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {

        boolean existsByName = repository.existsByName(name);

        return !existsByName;
    }
}
