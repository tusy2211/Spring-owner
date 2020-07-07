package com.khanhbn.hibernate.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TestValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface test {
    // trường message là bắt buộc, khai báo nội dung sẽ trả về khi field k hợp lệ
    String message() default "url: must start with http://";
    // Cái này là bắt buộc phải có để Hibernate Validator có thể hoạt động
//    Class<?>[] group() default {};
    // Cái này là bắt buộc phải có để Hibernate Validator có thể hoạt động
//    Class<? extends Payload>[] payload() default {};

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
